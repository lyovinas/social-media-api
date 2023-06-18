package ru.pet.socialnetwork.service;

import ru.pet.socialnetwork.dto.SubscriptionDTO;
import ru.pet.socialnetwork.model.Subscription;

import java.util.List;

public interface SubscriptionTestData {

    Subscription SUBSCRIPTION_1 = new Subscription();
    Subscription SUBSCRIPTION_2 = new Subscription();
    List<Subscription> SUBSCRIPTIONS = List.of(SUBSCRIPTION_1, SUBSCRIPTION_2);

    SubscriptionDTO SUBSCRIPTION_DTO_1 = new SubscriptionDTO(null, 1L, null);
    SubscriptionDTO SUBSCRIPTION_DTO_2 = new SubscriptionDTO(null, 2L, null);
    List<SubscriptionDTO> SUBSCRIPTION_DTOS = List.of(SUBSCRIPTION_DTO_1, SUBSCRIPTION_DTO_2);
}
