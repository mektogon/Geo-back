package ru.dorofeev.mobilemap.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Mobile-map")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("")
                                                .url("")
                                                .name("Dorofeev Maxim")
                                )
                );
    }
}
