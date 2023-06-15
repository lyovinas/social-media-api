package ru.pet.socialnetwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class GenericDTO {

    @Schema(description = "Идентификатор записи", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Дата/время создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdWhen;
}
