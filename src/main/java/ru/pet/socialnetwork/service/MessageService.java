package ru.pet.socialnetwork.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pet.socialnetwork.dto.MessageDTO;
import ru.pet.socialnetwork.mapper.MessageMapper;
import ru.pet.socialnetwork.model.Message;
import ru.pet.socialnetwork.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService extends GenericService<Message, MessageDTO> {

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        repository = messageRepository;
        mapper = messageMapper;
    }



    public List<MessageDTO> getAllByUserIds(List<Long> ids) {
        return mapper.toDTOs(
                ((MessageRepository) repository)
                        .findAllByUserIdInAndTargetUserIdIn(
                                ids, ids,
                                Sort.by(Sort.Direction.DESC, "createdWhen"))
        );
    }
}
