package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.dto.UserDTO;
import com.pavel_fomchenkov.subscriptions.mapper.UserMapper;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.model.User;
import com.pavel_fomchenkov.subscriptions.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final SubscriptionService subscriptionService;

    @Override
    public UserDTO create(UserDTO userDTO) {
        User newUser = repository.save(mapper.mapDTOToUser(userDTO));
        return mapper.mapToUserDTO(newUser);
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " was not found."));
    }

    @Override
    public User edit(Long id, User user) {
        User userFromBD = getById(id);
        userFromBD.setUsername(user.getUsername());
        userFromBD.setSubscriptions(user.getSubscriptions());
        return repository.save(userFromBD);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public User addSubscription(Long id, Subscription subscription) {
        if (subscription != null) {
            User userFromBD = getById(id);
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
    public void deleteSubscription(Long id, Long subId) {
        User userFromBD = getById(id);
        userFromBD.getSubscriptions().removeIf(subscription -> subscription.getId().equals(subId));
        repository.save(userFromBD);
    }
}
