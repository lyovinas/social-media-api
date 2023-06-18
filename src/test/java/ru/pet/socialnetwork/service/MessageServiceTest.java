package ru.pet.socialnetwork.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import ru.pet.socialnetwork.dto.MessageDTO;
import ru.pet.socialnetwork.mapper.MessageMapper;
import ru.pet.socialnetwork.model.Message;
import ru.pet.socialnetwork.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest extends GenericServiceTest<Message, MessageDTO> {

    public MessageServiceTest() {
        mapper = Mockito.mock(MessageMapper.class);
        repository = Mockito.mock(MessageRepository.class);
        service = new MessageService((MessageRepository) repository, (MessageMapper) mapper);
    }

    @Test
    void getAllByUserIds() {
        List<Long> ids = List.of(1L, 2L);
        Mockito.when(((MessageRepository) repository).findAllByUserIdInAndTargetUserIdIn(
                        ids, ids, Sort.by(Sort.Direction.DESC, "createdWhen"))
                )
                .thenReturn(MessageTestData.MESSAGES);
        Mockito.when(mapper.toDTOs(MessageTestData.MESSAGES)).thenReturn(MessageTestData.MESSAGE_DTOS);
        List<MessageDTO> messages = ((MessageService) service).getAllByUserIds(ids);
        assertEquals(MessageTestData.MESSAGE_DTOS, messages);
    }

    @Test
    @Override
    void getById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(MessageTestData.MESSAGE_1));
        Mockito.when(mapper.toDTO(MessageTestData.MESSAGE_1)).thenReturn(MessageTestData.MESSAGE_DTO_1);
        MessageDTO messageDTO = service.getById(1L);
        assertEquals(MessageTestData.MESSAGE_DTO_1, messageDTO);
    }

    @Test
    @Override
    void create() {
        Mockito.when(repository.save(MessageTestData.MESSAGE_1)).thenReturn(MessageTestData.MESSAGE_1);
        Mockito.when(mapper.toDTO(MessageTestData.MESSAGE_1)).thenReturn(MessageTestData.MESSAGE_DTO_1);
        Mockito.when(mapper.toEntity(MessageTestData.MESSAGE_DTO_1)).thenReturn(MessageTestData.MESSAGE_1);
        MessageDTO messageDTO = service.create(MessageTestData.MESSAGE_DTO_1);
        assertEquals(MessageTestData.MESSAGE_DTO_1, messageDTO);
    }

    @Test
    @Override
    void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(MessageTestData.MESSAGE_1));
        Mockito.when(repository.save(MessageTestData.MESSAGE_1)).thenReturn(MessageTestData.MESSAGE_1);
        Mockito.when(mapper.toDTO(MessageTestData.MESSAGE_1)).thenReturn(MessageTestData.MESSAGE_DTO_1);
        Mockito.when(mapper.toEntity(MessageTestData.MESSAGE_DTO_1)).thenReturn(MessageTestData.MESSAGE_1);
        MessageDTO messageDTO = service.update(1L, MessageTestData.MESSAGE_DTO_1);
        assertEquals(MessageTestData.MESSAGE_DTO_1, messageDTO);
    }

    @Test
    @Override
    void delete() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        boolean deleted = service.delete(1L);
        assertTrue(deleted);
    }
}