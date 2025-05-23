package com.pavel_fomchenkov.subscriptions.controller;

import com.pavel_fomchenkov.subscriptions.dto.UserDTO;
import com.pavel_fomchenkov.subscriptions.model.Subscription;
import com.pavel_fomchenkov.subscriptions.model.User;
import com.pavel_fomchenkov.subscriptions.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService service;

    //    CREATE
    @PostMapping()
    @Operation(summary = "Создание нового пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Укажите имя пользователя"))
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = service.create(userDTO);
        return ResponseEntity.ok(newUser);
    }

    //    READ
    @GetMapping("/{id}")
    @Operation(summary = "Получение данных пользователя по id")
    public ResponseEntity<User> getUser(@Parameter(description = "Идентификатор пользователя") @PathVariable(name = "id") Long id) {
        User user = service.getById(id);
        return ResponseEntity.ok(user);
    }

    //    UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Нужны полные данные пользователя, старые будут удалены")
    )
    public ResponseEntity<User> edit(@Parameter(description = "Идентификатор пользователя") @PathVariable(name = "id") Long id, @RequestBody User user) {
        return ResponseEntity.ok(service.edit(id, user));
    }

    //    DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя")
    public ResponseEntity<Void> delete(@Parameter(description = "Идентификатор пользователя") @PathVariable(name = "id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/subscriptions")
    @Operation(summary = "Добавление подписки пользователю",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Достаточно id подписки")
    )
    public ResponseEntity<User> addSubscription(@Parameter(description = "Идентификатор пользователя") @PathVariable(name = "id") Long id, @RequestBody Subscription subscription) {
        return ResponseEntity.ok(service.addSubscription(id, subscription));
    }

    @GetMapping("/{id}/subscriptions")
    @Operation(summary = "Получение списка подписок пользователя")
    public ResponseEntity<Collection<Subscription>> getSubscriptions(@Parameter(description = "Идентификатор пользователя") @PathVariable(name = "id") Long user_id) {
        return ResponseEntity.ok(service.getSubscriptions(user_id));
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    @Operation(summary = "Удаление подписки у пользователя")
    public ResponseEntity<Void> deleteSubscription(@Parameter(description = "Идентификатор пользователя") @PathVariable(name = "id") Long id, @Parameter(description = "Идентификатор подписки") @PathVariable(name = "sub_id") Long sub_id) {
        service.deleteSubscription(id, sub_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


