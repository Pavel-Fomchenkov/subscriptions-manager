package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.mapper.SubscriptionMapper;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final SubscriptionMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO) {
        Subscription newSubscription = repository.save(mapper.mapDTOToSubscription(subscriptionDTO));
        return mapper.mapToSubscriptionDTO(newSubscription);
    }

    @Override
    public Subscription getById(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscription with id " + id + " was not found."));
        } catch (EntityNotFoundException e) {
            logger.error("Ошибка получения сущности, {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void increaseUserCount(Long id) {
        Subscription subscription = getById(id);
        subscription.setUserCount(subscription.getUserCount() + 1);
        repository.save(subscription);
    }

    @Override
    @Transactional
    public void decreaseUserCount(Long id) {
        Subscription subscription = getById(id);
        if (subscription.getUserCount() <= 1) {
            subscription.setUserCount(0);
        } else {
            subscription.setUserCount(subscription.getUserCount() - 1);
        }
        repository.save(subscription);
    }

    @Override
    public List<Subscription> top3() {
        return repository.top3();
    }
}
