package com.pavel_fomchenkov.subscriptions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_id_seq")
    @SequenceGenerator(name = "subscription_id_seq", sequenceName = "subscription_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description", unique = true, nullable = false)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subscriptions")
    private Collection<User> users = new HashSet<>();
}
