package com.smallsquare_plus.modules.user.web.controller;

import com.smallsquare_plus.modules.user.application.UserService;
import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<Long> signup(@RequestBody UserSignupReqDTO reqDTO) {
        Long userId = userService.signup(reqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
public class UserController {
}
