package ru.pet.socialnetwork.repository;

import ru.pet.socialnetwork.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends GenericRepository<Subscription> {

    List<Subscription> findAllByTargetUserIdAndFriendlyIsNull(Long userId);

    List<Subscription> findAllByUserIdAndFriendlyIsTrue(Long userId);

    List<Subscription> findAllByUserIdAndFriendlyIsFalse(Long userId);

    List<Subscription> findAllByUserId(Long userId);
}
