package com.pavel_fomchenkov.subscriptions.controller;

import com.pavel_fomchenkov.subscriptions.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService service;
//    CREATE
//    READ
//    UPDATE
//    DELETE
}

/*      POST /users - создать пользователя – передаем User
        GET /users/{id} - получить информацию о пользователе – передаем id
        PUT /users/{id} - обновить пользователя – передаем User и id
        DELETE /users/{id} - удалить пользователя – передаем id
        POST /users/{id}/subscriptions - добавить подписку – передаем id пользователя и Sub
        GET /users/{id}/subscriptions - получить подписки пользователя – передаем id польз-я
        DELETE /users/{id}/subscriptions/{sub_id} - удалить подписку – передаем 2 id
        GET /subscriptions/top - получить ТОП-3 популярных подписок – ничего не передаем*/
