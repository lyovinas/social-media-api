package ru.pet.socialnetwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pet.socialnetwork.model.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO пользователя")
public class UserDTO extends GenericDTO {

    @Schema(description = "Адрес электронной почты")
    private String email;

    @Schema(description = "Имя пользователя")
    private String login;

    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Роль", accessMode = Schema.AccessMode.READ_ONLY)
    private Role role;
}
