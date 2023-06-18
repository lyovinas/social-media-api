package ru.pet.socialnetwork.service;

import ru.pet.socialnetwork.dto.MessageDTO;
import ru.pet.socialnetwork.model.Message;
import ru.pet.socialnetwork.model.User;

import java.util.List;

public interface MessageTestData {

    Message MESSAGE_1 = new Message(new User(), new User(), "message_1");
    Message MESSAGE_2 = new Message(new User(), new User(), "message_2");
    List<Message> MESSAGES = List.of(MESSAGE_1, MESSAGE_2);

    MessageDTO MESSAGE_DTO_1 = new MessageDTO(1L, 2L, "message_1");
    MessageDTO MESSAGE_DTO_2 = new MessageDTO(1L, 2L, "message_2");
    List<MessageDTO> MESSAGE_DTOS = List.of(MESSAGE_DTO_1, MESSAGE_DTO_2);
}
