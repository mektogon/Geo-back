package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.exception.generalerror.GeneralErrorException;
import ru.dorofeev.mobilemap.model.base.TileMap;
import ru.dorofeev.mobilemap.model.dto.TileCoordinateDto;
import ru.dorofeev.mobilemap.model.dto.TilePointDto;
import ru.dorofeev.mobilemap.repository.TileMapRepository;
import ru.dorofeev.mobilemap.service.interf.OpenStreetMapAdapter;
import ru.dorofeev.mobilemap.service.interf.TileMapService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TileMapServiceImpl implements TileMapService {

    private final TileMapRepository tileMapRepository;
    private final OpenStreetMapAdapter adapter;

    @Value("${file.storage.tile-map.location}")
    private String directoryToSave;

    @Value("${file.extension.tile-map}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile file, String name) {

        tileMapRepository.save(TileMap.builder()
                .url(AuxiliaryUtils.savingFile(directoryToSave, file, EXTENSIONS, false))
                .name(name)
                .fileName(file.getOriginalFilename())
                .isMain(Boolean.FALSE)
                .build());
    }

    @Override
    public void update(UUID id, String name, MultipartFile file) {
        Optional<TileMap> byId = tileMapRepository.findById(id);

        if (byId.isPresent()) {
            TileMap updatedTileMap = byId.get();

            if (name != null) {
                updatedTileMap.setName(name);
                log.debug("IN update() - Обновлено наименование тайлов карты с ID: {}", id);
            }

            if (file != null) {
                updatedTileMap.setFileName(file.getOriginalFilename());
                AuxiliaryUtils.deleteFile(updatedTileMap.getUrl());
                updatedTileMap.setUrl(AuxiliaryUtils.savingFile(directoryToSave, file, EXTENSIONS, false));

                log.debug("IN update() - Обновлен архив тайлов карты с ID: {}", id);

            }

            tileMapRepository.save(updatedTileMap);
        } else {
            log.warn("IN upload() - Не найден архив тайлов карты с ID: {}", id);
            throw new GeneralErrorException(String.format("Не найден архив тайлов карты с ID: %s", id));
        }
    }


    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<TileMap> byId = tileMapRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.deleteFile(urlToFile);

            tileMapRepository.deleteById(id);
            log.debug("IN deleteById() - Тайл карты с ID: {} удален из базы данных!", id);
        } else {
            log.warn("IN deleteById() - Не удалось найти и удалить тайлы карты с ID: {} из базы данных!", id);
        }
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        Optional<TileMap> byName = tileMapRepository.getTailMapByName(name);

        if (byName.isPresent()) {
            String urlToFile = byName.get().getUrl();
            AuxiliaryUtils.deleteFile(urlToFile);

            tileMapRepository.deleteByName(name);
            log.debug("IN deleteByName() - Удалены тайлы карт с name: {} из базы данных!", name);
        } else {
            log.warn("IN deleteByName() - Не удалось найти и удалить тайлы карты с именем: {} из базы данных!", name);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFileById(UUID id) {
        Optional<TileMap> foundFile = tileMapRepository.findById(id);

        if (foundFile.isPresent()) {
            return getFile(foundFile.get());
        } else {
            log.warn("IN getFileById() - Не найдены тайлы карты с ID: {}", id);
            return ResponseEntity.status(204).body(new ByteArrayResource(new byte[0]));
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFileByName(String name) {
        Optional<TileMap> foundFile = tileMapRepository.getTailMapByName(name);

        if (foundFile.isPresent()) {
            return getFile(foundFile.get());
        } else {
            log.warn("IN getFileById() - Не найдены тайлы карты с name: {}", name);
            return ResponseEntity.status(204).body(new ByteArrayResource(new byte[0]));
        }
    }

    @Override
    public ResponseEntity<Resource> downloadMainTileMap() {
        List<TileMap> mainTileMapList = tileMapRepository.findAllByIsMainIsTrue();

        if (mainTileMapList.size() > 0) {
            return getFile(mainTileMapList.get(0));
        } else {
            log.warn("IN getFileById() - Не найдены тайлы карты с isMain = true");
            return ResponseEntity.status(204).body(new ByteArrayResource(new byte[0]));
        }
    }

    @Override
    public ResponseEntity<String> createTilesByPoints(List<TilePointDto> points) {
        UUID nameCurrentArchive = UUID.randomUUID();
        String targetPath = String.format("%s/%s", directoryToSave, nameCurrentArchive);
        String archivePath = String.format("%s.zip", targetPath);

        boolean mkdir = new File(targetPath).mkdir();

        if (mkdir) {
            points.forEach(
                    coordinate -> {
                        try {
                            Resource file = adapter.getTileByPoints(
                                    coordinate.getZ(),
                                    coordinate.getX(),
                                    coordinate.getY()
                            ).getBody();

                            if (file != null) {
                                FileUtils.writeByteArrayToFile(
                                        new File(String.format(
                                                "%s/%s_%s_%s.png",
                                                targetPath, coordinate.getZ(), coordinate.getX(), coordinate.getY())
                                        ),
                                        file.getInputStream().readAllBytes()
                                );
                            } else {
                                log.error("IN getTilesByCoordinates() - Не удалось получить и сохранить тайл с координатами Z: {} X: {} Y: {}",
                                        coordinate.getZ(), coordinate.getX(), coordinate.getY()
                                );
                            }
                        } catch (Exception e) {
                            try {
                                FileUtils.deleteDirectory(new File(targetPath));
                            } catch (IOException ex) {
                                log.error("IN getTilesByCoordinates() - Ошибка при удаление директории: {}. Сообщение с ошибкой: {} ", targetPath, ex.getMessage());
                            }
                            log.error("IN getTilesByCoordinates() - Ошибка при получение тайла с координатами Z: {} X: {} Y: {}. Сообщение с ошибкой: {}",
                                    coordinate.getZ(), coordinate.getX(), coordinate.getY(), e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
            );


            try (
                    FileOutputStream fos = new FileOutputStream(archivePath);
                    ZipOutputStream zipOut = new ZipOutputStream(fos);
            ) {
                File fileToZip = new File(targetPath);
                AuxiliaryUtils.zipFile(fileToZip, fileToZip.getName(), zipOut);
            } catch (IOException e) {
                log.error("IN getTilesByCoordinates() - Ошибка при сохранение элемента (ов) в zip-архив!");
                throw new RuntimeException(e);
            } finally {
                try {
                    FileUtils.deleteDirectory(new File(targetPath));
                } catch (IOException e) {
                    log.error("IN getTilesByCoordinates() - Ошибка при удаление директории: {}. Сообщение с ошибкой: {} ", targetPath, e.getMessage());
                }
            }

            UUID id = tileMapRepository.save(TileMap.builder()
                    .url(archivePath)
                    .name(String.valueOf(nameCurrentArchive))
                    .fileName(String.format("%s.zip", nameCurrentArchive))
                    .isMain(Boolean.FALSE)
                    .build()).getId();

            return ResponseEntity.ok(String.format("{\"id\": \"%s\"}", id));
        }

        return ResponseEntity.internalServerError().body("Error! Failed to create!");
    }

    @Override
    public ResponseEntity<String> createTilesByCoordinates(TileCoordinateDto coordinates) {

        int minScale = coordinates.getMinScale();
        int maxScale = coordinates.getMaxScale();

        if (minScale > maxScale) {
            throw new GeneralErrorException(String.format("Ошибка! minScale: %s > maxScale: %s", minScale, maxScale));
        }

        double upperLongitude = Double.parseDouble(coordinates.getUpperLongitude());
        double upperLatitude = Double.parseDouble(coordinates.getUpperLatitude());
        double lowerLongitude = Double.parseDouble(coordinates.getLowerLongitude());
        double lowerLatitude = Double.parseDouble(coordinates.getLowerLatitude());

        List<TilePointDto> tilePoints = new ArrayList<>();
        for (int zoom = maxScale; zoom >= minScale; zoom--) {
            String upperPoint = AuxiliaryUtils.convertCoordinateToPoint(zoom, upperLatitude, upperLongitude);
            String lowerPoint = AuxiliaryUtils.convertCoordinateToPoint(zoom, lowerLatitude, lowerLongitude);
            int upperX = Integer.parseInt(upperPoint.substring(upperPoint.indexOf("/") + 1, upperPoint.lastIndexOf("/")));
            int upperY = Integer.parseInt(upperPoint.substring(upperPoint.lastIndexOf("/") + 1));
            int lowerX = Integer.parseInt(lowerPoint.substring(lowerPoint.indexOf("/") + 1, lowerPoint.lastIndexOf("/")));
            int lowerY = Integer.parseInt(lowerPoint.substring(lowerPoint.lastIndexOf("/") + 1));

            for (int x = upperX; x <= lowerX; x++) {
                for (int y = upperY; y <= lowerY; y++) {
                    tilePoints.add(new TilePointDto(zoom, x, y));
                }
            }
        }

        return createTilesByPoints(tilePoints);
    }

    @Override
    public boolean makeTilesIsMain(UUID id) {
        Optional<TileMap> byId = tileMapRepository.findById(id);

        if (byId.isPresent()) {
            List<TileMap> list = tileMapRepository.findAllByIsMainIsTrue();
            list.forEach(tile -> tile.setIsMain(Boolean.FALSE));

            TileMap tileMap = byId.get();
            tileMap.setIsMain(Boolean.TRUE);

            list.add(tileMap);
            tileMapRepository.saveAll(list);
            return true;
        } else {
            log.warn("IN makeArchiveMain() - Не удалось найти тайлы карты с id: {}", id);
            return false;
        }
    }

    @Override
    public List<TileMap> getAll() {
        return tileMapRepository.findAll();
    }

    @Override
    public TileMap getById(UUID id) {
        Optional<TileMap> byId = tileMapRepository.findById(id);

        if (byId.isPresent()) {
            log.debug("IN getById() - Тайл карты с ID: {} найден!", id);
            return byId.get();
        } else {
            log.warn("IN getById() - Тайл карты с ID: {} не найден!", id);
            return new TileMap();
        }
    }

    @Override
    public TileMap getByName(String name) {
        Optional<TileMap> byName = tileMapRepository.getTailMapByName(name);

        if (byName.isPresent()) {
            log.debug("IN getByName() - Тайл карты с name: {} найден!", name);
            return byName.get();
        } else {
            log.warn("IN getByName() - Тайл карты с name: {} не найден!", name);
            return new TileMap();
        }
    }

    @Override
    public List<TileMap> getAllByName(String name) {
        return tileMapRepository.findAllByNameIsContainingIgnoreCase(name);
    }

    /**
     * Получение файла для скачивания
     *
     * @param foundFile данные скачиваемого файла.
     * @return файл.
     */
    private ResponseEntity<Resource> getFile(TileMap foundFile) {
        File file = new File(foundFile.getUrl());
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file.getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            log.error("IN getFile - Ошибка при получение файла: {}!", foundFile.getName());
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()))
                .contentLength(file.length())
                .body(resource);
    }
}
