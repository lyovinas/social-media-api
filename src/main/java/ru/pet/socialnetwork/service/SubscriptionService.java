package ru.pet.socialnetwork.service;

import org.springframework.stereotype.Service;
import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.mapper.SubscriptionMapper;
import ru.pet.socialnetwork.model.Subscription;
import ru.pet.socialnetwork.repository.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionService extends GenericService<Subscription, SubscriptionDTO> {

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               SubscriptionMapper subscriptionMapper) {
        repository = subscriptionRepository;
        mapper = subscriptionMapper;
    }


    public List<SubscriptionDTO> getReceivedRequests(Long userId) {
        return mapper.toDTOs(((SubscriptionRepository) repository)
                .findAllByTargetUserIdAndFriendlyIsNull(userId)
        );
    }

    public void accept(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = mapper.toEntity(subscriptionDTO);
        subscription.setFriendly(true);
        repository.save(subscription);

        SubscriptionDTO friendSubscriptionDTO = new SubscriptionDTO();
        friendSubscriptionDTO.setUserId(subscriptionDTO.getTargetUserId());
        friendSubscriptionDTO.setTargetUserId(subscriptionDTO.getUserId());
        friendSubscriptionDTO.setFriendly(true);
        create(friendSubscriptionDTO);
    }

    public void reject(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = mapper.toEntity(subscriptionDTO);
        subscription.setFriendly(false);
        repository.save(subscription);
    }

    public List<SubscriptionDTO> getFriends(Long userId) {
        return mapper.toDTOs(
                ((SubscriptionRepository) repository)
                        .findAllByUserIdAndFriendlyIsTrue(userId)
        );
    }

    public List<SubscriptionDTO> getNotFriends(Long userId) {
        return mapper.toDTOs(
                ((SubscriptionRepository) repository)
                        .findAllByUserIdAndFriendlyIsFalse(userId)
        );
    }

    public List<SubscriptionDTO> getAllByUserId(Long userId) {
        return mapper.toDTOs(
                ((SubscriptionRepository) repository)
                        .findAllByUserId(userId)
        );
    }
}
