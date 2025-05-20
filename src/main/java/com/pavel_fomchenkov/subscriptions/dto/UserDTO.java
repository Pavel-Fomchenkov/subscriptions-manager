package com.pavel_fomchenkov.subscriptions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    @NotNull
    private String username;
}
