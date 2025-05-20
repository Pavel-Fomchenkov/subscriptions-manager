package com.pavel_fomchenkov.subscriptions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @JsonProperty("subscriptions")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_subscriptions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    private Collection<Subscription> subscriptions = new HashSet<>();
}
