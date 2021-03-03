package com.izavasconcelos.videostore.controller;

import com.izavasconcelos.videostore.service.UserService;
import com.izavasconcelos.videostore.user.UserMapper;
import com.izavasconcelos.videostore.user.UserRequest;
import com.izavasconcelos.videostore.user.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
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
  public UserResponse create(@RequestBody UserRequest requestBody) {
    return userMapper.toResponse(userService.create(userMapper.toEntity(requestBody)));
  }
}
