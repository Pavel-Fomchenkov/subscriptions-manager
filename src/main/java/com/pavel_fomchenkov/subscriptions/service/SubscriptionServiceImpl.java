package com.pavel_fomchenkov.subscriptions.service;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.mapper.SubscriptionMapper;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;
    private final SubscriptionMapper mapper;

    @Override
    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO) {
        Subscription newSubscription = repository.save(mapper.mapDTOToSubscription(subscriptionDTO));
        return mapper.mapToSubscriptionDTO(newSubscription);
    }

    @Override
    public void delete(Long user_id, Long sub_id) {
//        Subscription subscription = repository.findById(sub_id).orElseThrow();
//        repository.findById(sub_id).ifPresent(sub -> sub.);
    }

    @Override
    public Subscription getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscription with id " + id + " was not found."));
    }
}
