package com.pavel_fomchenkov.subscriptions.controller;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Подписки")
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping
    @Operation(summary = "Добавление новой подписки в базу данных",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Укажите наименование и описание подписки"))
    public ResponseEntity<SubscriptionDTO> create(@RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO newSubscription = service.create(subscriptionDTO);
        return ResponseEntity.ok(newSubscription);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение подписки по id")
    public ResponseEntity<Subscription> getSubscription(@Parameter(description = "Идентификатор подписки") @PathVariable(name = "id") Long id) {
        Subscription subscription = service.getById(id);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/subscriptions/top")
    @Operation(summary = "Получение 3 наиболее популярных подписок")
    public ResponseEntity<List<Subscription>> top3() {
        return ResponseEntity.ok(service.top3());
    }

}
