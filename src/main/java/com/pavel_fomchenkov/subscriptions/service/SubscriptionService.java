package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO create(SubscriptionDTO subscriptionDTO);

    Subscription getById(Long id);

    void increaseUserCount(Long id);

    void decreaseUserCount(Long id);

    List<Subscription> top3();
}
