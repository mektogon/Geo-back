package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Photo;
import ru.dorofeev.mobilemap.service.interf.PhotoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
public class PhotoController implements AbstractFileController<Photo> {

    private final PhotoService photoService;

    @PostMapping()
    @Override
    public void upload(@RequestParam("image") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        photoService.upload(file, id);
    }

    @PutMapping()
    @Override
    public void update(@RequestBody Photo file) {
        photoService.update(file);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        photoService.deleteById(id);
    }

    @GetMapping()
    @Override
    public List<Photo> getAll() {
        return photoService.getAll();
    }

    @GetMapping("/getInfoById/{id}")
    @Override
    public Optional<Photo> getInfoById(@PathVariable UUID id) {
        return photoService.findById(id);
    }

    @GetMapping("/getAllInfoByName/{name}")
    @Override
    public List<Photo> getAllInfoByName(@PathVariable String name) {
        return photoService.findAllInfoByName(name);
    }


    @GetMapping("/{id}")
    @Override
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return photoService.getFileById(id);
    }

}
