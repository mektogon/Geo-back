package ru.dorofeev.mobilemap.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dorofeev.mobilemap.model.entity.GeographicalObject;
import ru.dorofeev.mobilemap.repository.GeographicalObjectRepository;

import java.util.List;

@Configuration
public class GeographicalObjectConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(GeographicalObjectRepository geographicalObjectRepository) {
        return args -> {
            geographicalObjectRepository.saveAll(List.of(
                    GeographicalObject.builder()
                        .name("Тест1")
                        .latitude("1111")
                        .longitude("1111")
                        .description("Описание1")
                        .build(),

                    GeographicalObject.builder()
                            .name("Тест2")
                            .latitude("2222")
                            .longitude("2222")
                            .description("Описание2")
                            .build(),
                    GeographicalObject.builder()
                            .name("Тест3")
                            .latitude("3333")
                            .longitude("3333")
                            .description("Описание3")
                            .build()
            ));
        };
    }
}
