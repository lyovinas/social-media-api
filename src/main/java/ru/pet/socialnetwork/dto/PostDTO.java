package ru.pet.socialnetwork.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "DTO поста")
public class PostDTO extends GenericDTO {

    @Schema(description = "id пользователя, создавшего пост",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "Заголовок поста")
    private String title;

    @Schema(description = "Текст поста")
    private String text;

    @Schema(description = "Файл изображения, прикреплённого к посту")
    private MultipartFile imageFile;
}
