package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.dto.UserDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.model.User;

import java.util.Collection;

public interface UserService {
    UserDTO create(UserDTO userDTO);

    User getById(Long id);

    User edit(Long id, User user);

    void delete(Long id);

    User addSubscription(Long id, Subscription subscription);

    Collection<Subscription> getSubscriptions(Long userId);

    void deleteSubscription(Long id, Long subId);
}
