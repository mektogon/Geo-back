package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev.mobilemap.model.base.Video;
import ru.dorofeev.mobilemap.service.interf.VideoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController implements AbstractFileController<Video> {

    private final VideoService videoService;

    @PostMapping()
    @Override
    public void upload(@RequestParam("video") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        videoService.upload(file, id);
    }

    @PatchMapping()
    @Override
    public void update(@RequestBody Video file) {
        videoService.update(file);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        videoService.deleteById(id);
    }

    @GetMapping()
    @Override
    public List<Video> getAll() {
        return videoService.getAll();
    }

    @GetMapping("/getInfoById/{id}")
    @Override
    public Optional<Video> getInfoById(@PathVariable UUID id) {
        return videoService.findById(id);
    }

    @GetMapping("/getAllInfoByName/{name}")
    @Override
    public List<Video> getAllInfoByName(@PathVariable String name) {
        return videoService.findAllInfoByName(name);
    }


    @GetMapping("/{id}")
    @Override
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return videoService.getFileById(id);
    }

}
