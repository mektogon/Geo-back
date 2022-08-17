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
import ru.dorofeev.mobilemap.model.base.Audio;
import ru.dorofeev.mobilemap.service.interf.AudioService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class AudioController implements AbstractFileController<Audio> {
    private final AudioService audioService;

    @PostMapping()
    @Override
    public void upload(@RequestParam("audio") MultipartFile[] file, @RequestParam("geoId") UUID id) {
        audioService.upload(file, id);
    }

    @PutMapping()
    @Override
    public void update(@RequestBody Audio file) {
        audioService.update(file);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        audioService.deleteById(id);
    }

    @GetMapping()
    @Override
    public List<Audio> getAll() {
        return audioService.getAll();
    }

    @GetMapping("/getInfoById/{id}")
    @Override
    public Optional<Audio> getInfoById(@PathVariable UUID id) {
        return audioService.findById(id);
    }

    @GetMapping("/getAllInfoByName/{name}")
    @Override
    public List<Audio> getAllInfoByName(@PathVariable String name) {
        return audioService.findAllInfoByName(name);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return audioService.getFileById(id);
    }

}
