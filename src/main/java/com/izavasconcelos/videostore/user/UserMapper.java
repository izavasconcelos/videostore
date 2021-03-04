package com.izavasconcelos.videostore.user;

import com.izavasconcelos.videostore.model.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public Optional<UserResponse> toResponse(final Optional<User> user) {

    return user.map(
        u ->
            UserResponse.builder()
                .id(u.getId())
                .email(u.getEmail())
                .name(u.getName())
                .login(u.getLogin())
                .build());
  }

  public User toEntity(final UserRequest request) {
    final User user = new User();

    user.setEmail(request.getEmail());
    user.setName(request.getName());
    user.setPassword(request.getPassword());
    user.setLogin(true);

    return user;
  }
}
