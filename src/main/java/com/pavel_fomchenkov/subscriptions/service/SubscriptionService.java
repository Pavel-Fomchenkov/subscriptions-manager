package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;

public interface SubscriptionService {
    SubscriptionDTO create(SubscriptionDTO subscriptionDTO);
    void delete(Long user_id, Long sub_id);

    Subscription getById(Long id);
}
