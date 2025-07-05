package com.smallsquare_plus.modules.user.web.controller;

import com.smallsquare_plus.modules.user.application.UserService;
import com.smallsquare_plus.modules.user.infrastructure.auth.model.CustomUserDetails;
import com.smallsquare_plus.modules.user.web.dto.request.UserUpdateReqDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserInfoResDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserLoginReqDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserLogoutReqDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserLoginResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserUpdateResDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<UserLoginResDTO> login(@RequestBody UserLoginReqDTO reqDTO) {
        UserLoginResDTO resDto = userService.login(reqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody UserLogoutReqDTO reqDTO) {
        userService.logout(reqDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/")
    public ResponseEntity<UserInfoResDTO> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInfoResDTO resDTO = userService.me(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(resDTO);
    }

    @PatchMapping("/")
    public ResponseEntity<UserUpdateResDTO> updateMe(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @RequestBody UserUpdateReqDTO reqDTO) {
        UserUpdateResDTO resDTO = userService.updateMe(userDetails.getUserId(), reqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resDTO);
    }
}
