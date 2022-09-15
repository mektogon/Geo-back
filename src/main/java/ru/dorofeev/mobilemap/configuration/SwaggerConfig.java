package ru.dorofeev.mobilemap.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Mobile-map")
                                .version("1.0.0")
                                .description("REST-API проект, посвященный разработке серверной части " +
                                        "для мобильного приложения навигации по достопримечательностям " +
                                        "Чуйского тракта.")
                                .contact(
                                        new Contact()
                                                .email("Mektogon385@mail.ru")
                                                .url("https://github.com/mektogon")
                                                .name("Maxim Dorofeev")
                                )
                );
    }
}
