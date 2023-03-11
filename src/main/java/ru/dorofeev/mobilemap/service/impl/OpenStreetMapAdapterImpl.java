package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dorofeev.mobilemap.service.interf.OpenStreetMapAdapter;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenStreetMapAdapterImpl implements OpenStreetMapAdapter {

    @Value("${openstreetmap.url}")
    private String pathToOpenStreetMap;

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<Resource> getTileByPoints(int zoom, int x, int y) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "map_backend");  // value can be whatever

        ResponseEntity<Resource> response = restTemplate.exchange(
                String.format("%s/%s/%s/%s.png", pathToOpenStreetMap, zoom, x, y),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Resource.class
        );

        return response;
    }
}
