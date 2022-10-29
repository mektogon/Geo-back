package ru.dorofeev.mobilemap.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность аутентификации")
public class AuthenticationRequestDto {
    @Schema(description = "Логин", example = "Login")
    private String username;

    @Schema(description = "Пароль", example = "Password")
    private String password;
}
