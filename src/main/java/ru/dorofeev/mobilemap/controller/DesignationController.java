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
import ru.dorofeev.mobilemap.model.base.Designation;
import ru.dorofeev.mobilemap.service.interf.DesignationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/designation")
@RequiredArgsConstructor
public class DesignationController implements AbstractFileController<Designation> {
    private final DesignationService designationService;

    @PostMapping()
    @Override
    public void upload(@RequestParam("designation") MultipartFile[] file, @RequestParam(value = "geoId", required = false) UUID id) {
        designationService.upload(file);
    }

    @PutMapping()
    @Override
    public void update(@RequestBody Designation file) {
        designationService.update(file);
    }

    @DeleteMapping("/deleteById/{id}")
    @Override
    public void deleteById(@PathVariable UUID id) {
        designationService.deleteById(id);
    }

    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name) {
        designationService.deleteByName(name);
    }

    @GetMapping()
    @Override
    public List<Designation> getAll() {
        return designationService.getAll();
    }

    @GetMapping("/getInfoById/{id}")
    @Override
    public Optional<Designation> getInfoById(@PathVariable UUID id) {
        return designationService.findById(id);
    }

    @GetMapping("/getAllInfoByName/{name}")
    @Override
    public List<Designation> getAllInfoByName(@PathVariable String name) {
        return designationService.findAllInfoByName(name);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<byte[]> getFileById(@PathVariable UUID id) throws IOException {
        return designationService.getFileById(id);
    }
}
