package com.pavel_fomchenkov.subscriptions.controller;

import com.pavel_fomchenkov.subscriptions.dto.SubscriptionDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Подписки")
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping
    @Operation(summary = "Добавление новой подписки в базу данных")
    public ResponseEntity<SubscriptionDTO> create(@RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO newSubscription = service.create(subscriptionDTO);
        return ResponseEntity.ok(newSubscription);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение подписки по id")
    public ResponseEntity<Subscription> getSubscription(@PathVariable Long id) {
        Subscription subscription = service.getById(id);
        return ResponseEntity.ok(subscription);
    }
//   TODO          GET /subscriptions/top - получить ТОП-3 популярных подписок – ничего не передаем*/

}
