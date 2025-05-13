package com.pavel_fomchenkov.subscriptions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionDTO {
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
}
