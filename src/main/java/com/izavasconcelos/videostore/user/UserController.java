package com.izavasconcelos.videostore.user;

import javax.validation.Valid;
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
  public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest requestBody) {
    return userMapper
        .toResponse(userService.create(userMapper.toEntity(requestBody)))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }

  @PutMapping("/login")
  public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest requestBody) {
    return userMapper
        .toResponse(userService.login(requestBody.getEmail(), requestBody.getPassword()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/logout")
  public ResponseEntity<UserResponse> logout(@RequestBody @Valid LogoutRequest requestBody) {
    return userMapper
        .toResponse(userService.logout(requestBody.getEmail()))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
