package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.UserDTO;
import com.pavel_fomchenkov.subscriptions.mapper.UserMapper;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.model.User;
import com.pavel_fomchenkov.subscriptions.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final SubscriptionService subscriptionService;

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        User newUser = repository.save(mapper.mapDTOToUser(userDTO));
        return mapper.mapToUserDTO(newUser);
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " was not found."));
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
