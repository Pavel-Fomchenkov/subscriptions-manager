package com.pavel_fomchenkov.subscriptions.repository;

import com.pavel_fomchenkov.subscriptions.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query(value = "SELECT s FROM Subscription s ORDER BY s.userCount DESC FETCH FIRST 3 ROWS ONLY")
    List<Subscription> top3();
}
