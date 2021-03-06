package com.izavasconcelos.videostore.controller;

import static org.springframework.http.ResponseEntity.status;

import com.izavasconcelos.videostore.service.UserService;
import com.izavasconcelos.videostore.user.LoginRequest;
import com.izavasconcelos.videostore.user.LogoutRequest;
import com.izavasconcelos.videostore.user.UserMapper;
import com.izavasconcelos.videostore.user.UserRequest;
import com.izavasconcelos.videostore.user.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody UserRequest requestBody) {
    return userMapper
        .toResponse(userService.create(userMapper.toEntity(requestBody)))
        .map(ResponseEntity::ok)
        .orElseGet(() -> status(409).build());
  }

  @PutMapping("/login")
  public ResponseEntity<UserResponse> login(@RequestBody LoginRequest requestBody) {
    return userMapper
        .toResponse(userService.login(requestBody.getEmail(), requestBody.getPassword()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/logout")
  public ResponseEntity<UserResponse> logout(@RequestBody LogoutRequest requestBody) {
    return userMapper
        .toResponse(userService.logout(requestBody.getEmail()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
