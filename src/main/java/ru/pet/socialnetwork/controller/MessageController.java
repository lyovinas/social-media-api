package ru.pet.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.socialnetwork.dto.MessageDTO;
import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.model.Message;
import ru.pet.socialnetwork.service.MessageService;
import ru.pet.socialnetwork.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/messages")
@Tag(name = "Сообщения",
        description = "Контроллер для работы с сообщениями, отправляемыми друзьями друг другу")
public class MessageController extends GenericController<Message, MessageDTO> {

    private final SubscriptionService subscriptionService;



    public MessageController(MessageService messageService, SubscriptionService subscriptionService) {
        service = messageService;
        this.subscriptionService = subscriptionService;
    }



    @Override
    @Operation(summary = "Отправить сообщение",
            description = "Позволяет создать новое сообщение для друга")
    public ResponseEntity<MessageDTO> create(@RequestBody MessageDTO messageDTO) {
        if (isDataIncorrect(messageDTO)) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = getAuthenticatedUserId();
        List<Long> friendsIds = subscriptionService.getFriends(userId)
                .stream()
                .map(SubscriptionDTO::getTargetUserId)
                .toList();
        if (!friendsIds.contains(messageDTO.getTargetUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        messageDTO.setUserId(userId);
        return super.create(messageDTO);
    }


    @Override
    @Operation(summary = "Изменить сообщение",
            description = "Позволяет изменить существующее сообщение с указанным id")
    public ResponseEntity<MessageDTO> update(
            @Parameter(description = "id сообщения") Long id,
            @RequestBody MessageDTO messageDTO
    ) {
        if (id == null || isDataIncorrect(messageDTO)) {
            return ResponseEntity.badRequest().build();
        }
        MessageDTO existingMessageDTO = service.getById(id);
        if (existingMessageDTO == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingMessageDTO.getUserId().equals(getAuthenticatedUserId())
                || !existingMessageDTO.getTargetUserId().equals(messageDTO.getTargetUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        messageDTO.setUserId(existingMessageDTO.getUserId());
        return super.update(id, messageDTO);
    }


    @Override
    @Operation(summary = "Удалить сообщение",
            description = "Позволяет удалить существующее сообщение с указанным id")
    public ResponseEntity<MessageDTO> delete(@Parameter(description = "id сообщения") Long id) {
        MessageDTO existingMessageDTO;
        if (id == null || (existingMessageDTO = service.getById(id)) == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingMessageDTO.getUserId().equals(getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return super.delete(id);
    }


    @GetMapping("/getConversation")
    @Operation(summary = "Получить переписку",
            description = "Позволяет получить список сообщений аутентифицированного пользователя " +
                    "в переписке с пользователем с указанным id")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @Parameter(description = "id собеседника") Long targetUserId
    ) {
        Long userId = getAuthenticatedUserId();
        return targetUserId != null
                ? ResponseEntity.ok(((MessageService) service).getAllByUserIds(List.of(userId, targetUserId)))
                : ResponseEntity.badRequest().build();
    }


    private boolean isDataIncorrect(MessageDTO messageDTO) {
        return messageDTO == null
                || messageDTO.getTargetUserId() == null
                || messageDTO.getText() == null;
    }
}
