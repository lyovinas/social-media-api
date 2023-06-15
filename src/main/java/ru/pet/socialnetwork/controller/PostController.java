package ru.pet.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.socialnetwork.dto.PostDTO;
import ru.pet.socialnetwork.model.Post;
import ru.pet.socialnetwork.service.PostService;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "Посты",
        description = "Контроллер для работы с постами пользователей")
public class PostController extends GenericController<Post, PostDTO> {

    public PostController(PostService postService) {
        service = postService;
    }


    @Override
    @Operation(summary = "Создать пост",
            description = "Позволяет создать новый пост с возможностью прикрепить изображение")
    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> create(@ModelAttribute PostDTO postDTO) {
        if (isDataIncorrect(postDTO)) {
            return ResponseEntity.badRequest().build();
        }
        postDTO.setUserId(getAuthenticatedUserId());
        PostDTO createdPostDTO = service.create(postDTO);
        return createdPostDTO != null
                ? ResponseEntity.ok(createdPostDTO)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @Override
    @Operation(summary = "Изменить пост",
            description = "Позволяет изменить существующий пост с указанным id")
    @PostMapping(value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> update(
            @Parameter(description = "id поста") Long id,
            @ModelAttribute PostDTO postDTO
    ) {
        if (id == null || isDataIncorrect(postDTO)) {
            return ResponseEntity.badRequest().build();
        }
        PostDTO existingPostDTO = service.getById(id);
        if (existingPostDTO == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingPostDTO.getUserId().equals(getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        postDTO.setUserId(existingPostDTO.getUserId());
        return super.update(id, postDTO);
    }


    @Override
    @Operation(summary = "Удалить пост",
            description = "Позволяет удалить существующий пост с указанным id")
    public ResponseEntity<PostDTO> delete(@Parameter(description = "id поста") Long id) {
        PostDTO existingPostDTO;
        if (id == null || (existingPostDTO = service.getById(id)) == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingPostDTO.getUserId().equals(getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return service.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @Operation(summary = "Получить посты пользователя",
            description = "Позволяет получить все посты пользователя с заданным id," +
                    "отсортированные по времени создания постов, с поддержкой пагинации.")
    @GetMapping(value = "/getAllByUserId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostDTO>> getAllByUserId(
            @RequestParam("id") @Parameter(description = "id пользователя") Long userId,
            @RequestParam(value = "page", defaultValue = "1")
            @Parameter(description = "Номер страницы") int page,
            @RequestParam(value = "size", defaultValue = "2")
            @Parameter(description = "Количество элементов на странице") int size
    ) {
        PageRequest pageRequest = PageRequest.of(
                page - 1, size, Sort.by(Sort.Direction.DESC, "createdWhen"));

        return ResponseEntity.ok(((PostService) service).getAllByUserId(userId, pageRequest));
    }


    @Operation(summary = "Получить изображение",
            description = "Позволяет получить изображение, прикреплённое к посту с заданным id")
    @GetMapping(value = "/getImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    private void getImage(HttpServletResponse response,
                          @Parameter(description = "id поста") @RequestParam("postId") Long postId
    ) {
        File file = ((PostService) service).getImageFile(postId);
        if (file != null && file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            try (OutputStream outputStream = response.getOutputStream();
                 FileInputStream inputStream = new FileInputStream(file)) {
                IOUtils.copy(inputStream, outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Operation(summary = "Получить ленту активности пользователя",
            description = "Позволяет получить для аутентифицированного пользователя список постов " +
                    "от пользователей, на которых он подписан," +
                    "отсортированный по времени создания постов, с поддержкой пагинации.")
    @GetMapping(value = "/getFeedOfPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostDTO>> getFeedOfPosts(
            @RequestParam(value = "page", defaultValue = "1")
            @Parameter(description = "Номер страницы") int page,
            @RequestParam(value = "size", defaultValue = "2")
            @Parameter(description = "Количество элементов на странице") int size
    ) {
        PageRequest pageRequest = PageRequest.of(
                page - 1, size, Sort.by(Sort.Direction.DESC, "createdWhen"));

        return ResponseEntity.ok(((PostService) service)
                .getFeedOfPosts(getAuthenticatedUserId(), pageRequest));
    }


    private boolean isDataIncorrect(PostDTO postDTO) {
        return postDTO == null
                || postDTO.getTitle() == null
                || postDTO.getText() == null;
    }
}
