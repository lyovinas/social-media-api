package ru.pet.socialnetwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO аутентификации")
public class LoginDTO {

    @Schema(description = "Имя пользователя")
    private String login;

    @Schema(description = "Пароль")
    private String password;
}
