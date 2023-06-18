package ru.pet.socialnetwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO подписки")
public class SubscriptionDTO extends GenericDTO {

    @Schema(description = "id пользователя - инициатора заявки",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "id пользователя - объекта подписки")
    private Long targetUserId;

    @Schema(description = "Флаг наличия/принятия/отклонения заявки в друзья",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean friendly;
}
