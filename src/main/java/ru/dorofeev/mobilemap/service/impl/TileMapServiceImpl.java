package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.dorofeev.mobilemap.repository.TailMapRepository;
import ru.dorofeev.mobilemap.service.interf.TileMapService;
import ru.dorofeev.mobilemap.utils.AuxiliaryUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TileMapServiceImpl implements TileMapService {

    private final TailMapRepository tailMapRepository;

    @Value("${file.storage.tile-map.location}")
    private String directoryToSave;

    @Value("${file.extension.tile-map}")
    private final List<String> EXTENSIONS;

    @Override
    public void upload(MultipartFile file, String name) {

        tailMapRepository.save(TileMap.builder()
                .url(AuxiliaryUtils.savingFile(directoryToSave, file, EXTENSIONS, false))
                .name(name)
                .fileName(file.getOriginalFilename())
                .build());
    }

    @Override
    public void update(UUID id, String name, MultipartFile file) {
        Optional<TileMap> byId = tailMapRepository.findById(id);

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

            tailMapRepository.save(updatedTileMap);
        } else {
            log.info("IN upload() - Не найден архив тайлов карты с ID: {}", id);
            throw new GeneralErrorException(String.format("Не найден архив тайлов карты с ID: %s", id));
        }
    }


    @Transactional
    @Override
    public void deleteById(UUID id) {
        Optional<TileMap> byId = tailMapRepository.findById(id);

        if (byId.isPresent()) {
            String urlToFile = byId.get().getUrl();
            AuxiliaryUtils.deleteFile(urlToFile);

            tailMapRepository.deleteById(id);
            log.debug("IN deleteById() - Тайл карты с ID: {} удален из базы данных!", id);
        } else {
            log.info("IN deleteById() - Не удалось найти и удалить тайлы карты с ID: {} из базы данных!", id);
        }
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        Optional<TileMap> byName = tailMapRepository.getTailMapByName(name);

        if (byName.isPresent()) {
            String urlToFile = byName.get().getUrl();
            AuxiliaryUtils.deleteFile(urlToFile);

            tailMapRepository.deleteByName(name);
            log.debug("IN deleteByName() - Удалены тайлы карт с name: {} из базы данных!", name);
        } else {
            log.info("IN deleteByName() - Не удалось найти и удалить тайлы карты с именем: {} из базы данных!", name);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFileById(UUID id) throws IOException {
        Optional<TileMap> foundFile = tailMapRepository.findById(id);

        if (foundFile.isPresent()) {
            log.info("IN getFileById() - Найдены тайлы карты с ID: {}", id);
            return getFile(foundFile.get());
        } else {
            log.info("IN getFileById() - Не найдены тайлы карты с ID: {}", id);
            return ResponseEntity.status(204).body(new ByteArrayResource(new byte[0]));
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFileByName(String name) throws IOException {
        Optional<TileMap> foundFile = tailMapRepository.getTailMapByName(name);

        if (foundFile.isPresent()) {
            log.info("IN getFileById() - Найдены тайлы карты с name: {}", name);
            return getFile(foundFile.get());
        } else {
            log.info("IN getFileById() - Не найдены тайлы карты с name: {}", name);
            return ResponseEntity.status(204).body(new ByteArrayResource(new byte[0]));
        }
    }

    @Override
    public List<TileMap> getAll() {
        return tailMapRepository.findAll();
    }

    @Override
    public TileMap getById(UUID id) {
        Optional<TileMap> byId = tailMapRepository.findById(id);

        if (byId.isPresent()) {
            log.info("IN getById() - Тайл карты с ID: {} найден!", id);
            return byId.get();
        } else {
            log.info("IN getById() - Тайл карты с ID: {} не найден!", id);
            return new TileMap();
        }
    }

    @Override
    public TileMap getByName(String name) {
        Optional<TileMap> byName = tailMapRepository.getTailMapByName(name);

        if (byName.isPresent()) {
            log.info("IN getByName() - Тайл карты с name: {} найден!", name);
            return byName.get();
        } else {
            log.info("IN getByName() - Тайл карты с name: {} не найден!", name);
            return new TileMap();
        }
    }

    @Override
    public List<TileMap> getAllByName(String name) {
        return tailMapRepository.findAllByNameIsContainingIgnoreCase(name);
    }

    /**
     * Получение файла для скачивания
     *
     * @param foundFile данные скачиваемого файла.
     * @return файл.
     * @throws IOException
     */
    private ResponseEntity<Resource> getFile(TileMap foundFile) throws IOException {
        File file = new File(foundFile.getUrl());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file.getAbsoluteFile()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()))
                .contentLength(file.length())
                .body(resource);
    }
}
