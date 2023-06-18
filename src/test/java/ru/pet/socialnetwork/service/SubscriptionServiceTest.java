package ru.pet.socialnetwork.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.mapper.SubscriptionMapper;
import ru.pet.socialnetwork.model.Subscription;
import ru.pet.socialnetwork.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionServiceTest extends GenericServiceTest<Subscription, SubscriptionDTO> {

    public SubscriptionServiceTest() {
        mapper = Mockito.mock(SubscriptionMapper.class);
        repository = Mockito.mock(SubscriptionRepository.class);
        service = new SubscriptionService((SubscriptionRepository) repository, (SubscriptionMapper) mapper);
    }


    @Test
    void getReceivedRequests() {
        Mockito.when(((SubscriptionRepository) repository).findAllByTargetUserIdAndFriendlyIsNull(1L))
                .thenReturn(SubscriptionTestData.SUBSCRIPTIONS);
        Mockito.when(mapper.toDTOs(SubscriptionTestData.SUBSCRIPTIONS))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTOS);
        List<SubscriptionDTO> dtoList = ((SubscriptionService) service).getReceivedRequests(1L);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTOS, dtoList);
    }

    @Test
    void accept() {
        Mockito.when(mapper.toEntity(SubscriptionTestData.SUBSCRIPTION_DTO_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_1);

        assertNull(SubscriptionTestData.SUBSCRIPTION_1.getFriendly());
        ((SubscriptionService) service).accept(SubscriptionTestData.SUBSCRIPTION_DTO_1);
        assertTrue(SubscriptionTestData.SUBSCRIPTION_1.getFriendly());
        SubscriptionTestData.SUBSCRIPTION_1.setFriendly(null);
    }

    @Test
    void reject() {
        Mockito.when(mapper.toEntity(SubscriptionTestData.SUBSCRIPTION_DTO_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_1);

        assertNull(SubscriptionTestData.SUBSCRIPTION_1.getFriendly());
        ((SubscriptionService) service).reject(SubscriptionTestData.SUBSCRIPTION_DTO_1);
        assertFalse(SubscriptionTestData.SUBSCRIPTION_1.getFriendly());
    }

    @Test
    void getFriends() {
        Mockito.when(((SubscriptionRepository) repository).findAllByUserIdAndFriendlyIsTrue(1L))
                .thenReturn(SubscriptionTestData.SUBSCRIPTIONS);
        Mockito.when(mapper.toDTOs(SubscriptionTestData.SUBSCRIPTIONS))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTOS);
        List<SubscriptionDTO> dtoList = ((SubscriptionService) service).getFriends(1L);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTOS, dtoList);
    }

    @Test
    void getNotFriends() {
        Mockito.when(((SubscriptionRepository) repository).findAllByUserIdAndFriendlyIsFalse(1L))
                .thenReturn(SubscriptionTestData.SUBSCRIPTIONS);
        Mockito.when(mapper.toDTOs(SubscriptionTestData.SUBSCRIPTIONS))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTOS);
        List<SubscriptionDTO> dtoList = ((SubscriptionService) service).getNotFriends(1L);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTOS, dtoList);
    }

    @Test
    void getAllByUserId() {
        Mockito.when(((SubscriptionRepository) repository).findAllByUserId(1L))
                .thenReturn(SubscriptionTestData.SUBSCRIPTIONS);
        Mockito.when(mapper.toDTOs(SubscriptionTestData.SUBSCRIPTIONS))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTOS);
        List<SubscriptionDTO> dtoList = ((SubscriptionService) service).getAllByUserId(1L);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTOS, dtoList);
    }

    @Test
    @Override
    void getById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(SubscriptionTestData.SUBSCRIPTION_1));
        Mockito.when(mapper.toDTO(SubscriptionTestData.SUBSCRIPTION_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTO_1);
        SubscriptionDTO subscriptionDTO = service.getById(1L);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTO_1, subscriptionDTO);
    }

    @Test
    @Override
    void create() {
        Mockito.when(repository.save(SubscriptionTestData.SUBSCRIPTION_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_1);
        Mockito.when(mapper.toDTO(SubscriptionTestData.SUBSCRIPTION_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTO_1);
        Mockito.when(mapper.toEntity(SubscriptionTestData.SUBSCRIPTION_DTO_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_1);
        SubscriptionDTO subscriptionDTO = service.create(SubscriptionTestData.SUBSCRIPTION_DTO_1);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTO_1, subscriptionDTO);
    }

    @Test
    @Override
    void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(SubscriptionTestData.SUBSCRIPTION_1));
        Mockito.when(repository.save(SubscriptionTestData.SUBSCRIPTION_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_1);
        Mockito.when(mapper.toDTO(SubscriptionTestData.SUBSCRIPTION_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_DTO_1);
        Mockito.when(mapper.toEntity(SubscriptionTestData.SUBSCRIPTION_DTO_1))
                .thenReturn(SubscriptionTestData.SUBSCRIPTION_1);
        SubscriptionDTO subscriptionDTO = service.update(1L, SubscriptionTestData.SUBSCRIPTION_DTO_1);
        assertEquals(SubscriptionTestData.SUBSCRIPTION_DTO_1, subscriptionDTO);
    }

    @Test
    @Override
    void delete() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        boolean deleted = service.delete(1L);
        assertTrue(deleted);
    }
}