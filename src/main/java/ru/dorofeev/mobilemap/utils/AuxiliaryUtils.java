package ru.dorofeev.mobilemap.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class AuxiliaryUtils {
    public static String SavingFile(String directoryToSave, MultipartFile file, List<String> extension) {
        String ext = Objects.requireNonNull(file.getContentType()).split("/")[1];

        if (!checkExtensionFile(ext, extension)) {
            log.error("Тип загружаемого файла {} не удовлетворяет требованиям!", file.getName());
            throw new RuntimeException("Тип загружаемого файла " + file.getName() + " не удовлетворяет требованиям!");
        }

        String name = String.format("%s%s%s", UUID.randomUUID(), ".", ext);
        Path fullPathToSave = Paths.get(directoryToSave + File.separator + name);

        try {
            Files.copy(file.getInputStream(), fullPathToSave, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("");
            throw new RuntimeException(e);
        }

        return fullPathToSave.toString();
    }

    private static boolean checkExtensionFile(String extCurrentFile, List<String> extension) {
        for (var ext : extension) {
            if (ext.equals(extCurrentFile.toLowerCase())) {
                log.info("");
                return true;
            }
        }
        log.info("");
        return false;
    }

}
