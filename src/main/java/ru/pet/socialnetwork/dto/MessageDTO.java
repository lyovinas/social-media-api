package ru.pet.socialnetwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO сообщения")
public class MessageDTO extends GenericDTO {

    @Schema(description = "id пользователя - отправителя сообщения",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "id пользователя - получателя сообщения")
    private Long targetUserId;

    @Schema(description = "Текст сообщения")
    private String text;
}
