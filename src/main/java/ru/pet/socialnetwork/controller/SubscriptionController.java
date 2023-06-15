package ru.pet.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.model.Subscription;
import ru.pet.socialnetwork.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Подписки",
        description = "Контроллер для работы с записями, содержащими " +
                "информацию о подписке и статусе дружбы")
public class SubscriptionController extends GenericController<Subscription, SubscriptionDTO> {

    public SubscriptionController(SubscriptionService subscriptionService) {
        service = subscriptionService;
    }


    @Override
    @Operation(summary = "Создать подписку",
            description = "Позволяет подписаться на пользователя и создать заявку в друзья")
    public ResponseEntity<SubscriptionDTO> create(@RequestBody SubscriptionDTO subscriptionDTO) {
        if (isDataIncorrect(subscriptionDTO)) {
            return ResponseEntity.badRequest().build();
        }
        subscriptionDTO.setUserId(getAuthenticatedUserId());

        return super.create(subscriptionDTO);
    }

    @Override
    @Operation(hidden = true)
    public ResponseEntity<SubscriptionDTO> update(Long id, SubscriptionDTO dto) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    @Operation(summary = "Удалить подписку",
            description = "Позволяет удалить существующую подписку с указанным id, " +
                    "то есть удалить из друзей и/или отписаться от пользователя")
    public ResponseEntity<SubscriptionDTO> delete(@Parameter(description = "id подписки") Long id) {
        SubscriptionDTO existingSubscriptionDTO;
        if (id == null || (existingSubscriptionDTO = service.getById(id)) == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingSubscriptionDTO.getUserId().equals(getAuthenticatedUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return super.delete(id);
    }

    @GetMapping("/getReceivedRequests")
    @Operation(summary = "Получить входящие запросы в друзья",
            description = "Позволяет получить список поступивших заявок в друзья")
    public ResponseEntity<List<SubscriptionDTO>> getReceivedRequests() {

        return ResponseEntity.ok(
                ((SubscriptionService) service)
                        .getReceivedRequests(getAuthenticatedUserId())
        );
    }


    @GetMapping("/accept")
    @Operation(summary = "Принять заявку в друзья",
            description = "Позволяет одобрить заявку в друзья по id подписки")
    public ResponseEntity<SubscriptionDTO> accept(
            @RequestParam("id") @Parameter(description = "id подписки") Long id
    ) {
        SubscriptionDTO subscriptionDTO;
        if (id == null || (subscriptionDTO = service.getById(id)) == null) {
            return ResponseEntity.notFound().build();
        }
        if (!getAuthenticatedUserId().equals(subscriptionDTO.getTargetUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ((SubscriptionService) service).accept(subscriptionDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/reject")
    @Operation(summary = "Отклонить заявку в друзья",
            description = "Позволяет отклонить заявку в друзья по id подписки")
    public ResponseEntity<SubscriptionDTO> reject(
            @RequestParam("id") @Parameter(description = "id подписки") Long id
    ) {
        SubscriptionDTO subscriptionDTO;
        if (id == null || (subscriptionDTO = service.getById(id)) == null) {
            return ResponseEntity.notFound().build();
        }
        if (!getAuthenticatedUserId().equals(subscriptionDTO.getTargetUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ((SubscriptionService) service).reject(subscriptionDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getFriends")
    @Operation(summary = "Получить список друзей",
            description = "Позволяет получить список подписок на друзей")
    public ResponseEntity<List<SubscriptionDTO>> getFriends() {

        return ResponseEntity.ok(
                ((SubscriptionService) service).getFriends(getAuthenticatedUserId())
        );
    }


    @GetMapping("/getNotFriends")
    @Operation(summary = "Получить список подписок, кроме друзей",
            description = "Позволяет получить список подписок на пользователей, не являющихся друзьями")
    public ResponseEntity<List<SubscriptionDTO>> getNotFriends() {

        return ResponseEntity.ok(
                ((SubscriptionService) service).getNotFriends(getAuthenticatedUserId()));
    }


    private boolean isDataIncorrect(SubscriptionDTO subscriptionDTO) {
        return subscriptionDTO == null
                || subscriptionDTO.getTargetUserId() == null;
    }
}
