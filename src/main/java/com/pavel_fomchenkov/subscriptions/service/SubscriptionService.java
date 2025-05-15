package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO create(SubscriptionDTO subscriptionDTO);
    void delete(Long user_id, Long sub_id);

    Subscription getById(Long id);

    void increaseUserCount(Long id);

    void decreaseUserCount(Long id);

    List<Subscription> top3();
}
