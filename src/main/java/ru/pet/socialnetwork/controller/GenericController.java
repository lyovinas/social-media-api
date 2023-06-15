package ru.pet.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.pet.socialnetwork.dto.GenericDTO;
import ru.pet.socialnetwork.model.GenericModel;
import ru.pet.socialnetwork.service.GenericService;
import ru.pet.socialnetwork.service.userdetails.CustomUserDetails;

@SecurityRequirement(name = "Bearer Authentication")
public abstract class GenericController<E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;



//    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<D>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }

    @Operation(summary = "Создать запись",
            description = "Позволяет добавить новую запись")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> create(@RequestBody D newDTO) {
        D createdDTO = service.create(newDTO);
        return createdDTO != null
                ? ResponseEntity.ok(createdDTO)
                : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Обновить запись",
            description = "Позволяет заменить запись на переданную")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> update(
            @RequestParam("id") @Parameter(description = "id обновляемой записи") Long id,
            @RequestBody D dto
    ) {
        D updatedDTO = service.update(id, dto);
        return updatedDTO != null
                ? ResponseEntity.ok(updatedDTO)
                : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Удалить запись",
            description = "Позволяет удалить запись по id")
    @DeleteMapping("/delete")
    public ResponseEntity<D> delete(
            @RequestParam("id") @Parameter(description = "id удаляемой записи") Long id
    ) {
        return service.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    protected Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }
}
