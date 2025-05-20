package com.pavel_fomchenkov.subscriptions.repository;

import com.pavel_fomchenkov.subscriptions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
