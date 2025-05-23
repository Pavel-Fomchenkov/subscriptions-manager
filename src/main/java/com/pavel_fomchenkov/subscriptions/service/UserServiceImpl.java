package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.UserDTO;
import com.pavel_fomchenkov.subscriptions.mapper.UserMapper;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.model.User;
import com.pavel_fomchenkov.subscriptions.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final SubscriptionService subscriptionService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        User newUser = repository.save(mapper.mapDTOToUser(userDTO));
        return mapper.mapToUserDTO(newUser);
    }

    @Override
    public User getById(Long id) {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException e) {
            logger.error("Ошибка получения сущности, User with id {} was not found.", id);
            throw e;
        }
    }

    @Override
    @Transactional
    public User edit(Long id, User user) {
        User userFromBD = getById(id);
        userFromBD.setUsername(user.getUsername());

        Collection<Subscription> subs = user.getSubscriptions();
        Collection<Subscription> subsFromBD = userFromBD.getSubscriptions();

        List<Long> subIdsToDelete = new ArrayList<>(); // collecting ids to avoid java.util.ConcurrentModificationException
        subsFromBD.forEach(subBd -> {
            if (!subs.contains(subBd)) {
                subIdsToDelete.add(subBd.getId());
            }
        });
        subIdsToDelete.forEach(subId -> deleteSubscription(id, subId));

        subs.forEach(sub -> {
            if (!subsFromBD.contains(sub)) {
                addSubscription(id, sub);
            }
        });
        userFromBD.setSubscriptions(user.getSubscriptions());
        return repository.save(userFromBD);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getById(id).getSubscriptions().forEach(s -> subscriptionService.decreaseUserCount(s.getId()));
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public User addSubscription(Long id, Subscription subscription) {
        try {
            if (subscription != null) {
                User userFromBD = getById(id);
                if (!userFromBD.getSubscriptions().contains(subscription)) {
                    subscriptionService.increaseUserCount(subscription.getId());
                }
                userFromBD.getSubscriptions().add(subscription);
                return repository.save(userFromBD);
            } else {
                throw new RuntimeException("Добавляемая подписка не может быть null");
            }
        } catch (RuntimeException e) {
            logger.error("Ошибка добавления подписки, {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Collection<Subscription> getSubscriptions(Long userId) {
        return getById(userId).getSubscriptions();
    }

    @Override
    @Transactional
    public void deleteSubscription(Long id, Long subId) {
        User userFromBD = getById(id);
        Collection<Subscription> subscriptions = userFromBD.getSubscriptions();
        if (subscriptions.stream().anyMatch(sub -> subId.equals(sub.getId()))) {
            subscriptionService.decreaseUserCount(subId);
            subscriptions.removeIf(subscription -> subscription.getId().equals(subId));
        }
        userFromBD.setSubscriptions(subscriptions);
        repository.save(userFromBD);
    }
}
