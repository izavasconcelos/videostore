package com.izavasconcelos.videostore.user;

import com.izavasconcelos.videostore.model.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public Optional<UserResponse> toResponse(final Optional<User> user) {

    return user.map(u ->
            UserResponse.builder()
                .email(u.getEmail())
                .logged(u.getLogged())
                .build());
  }

  public User toEntity(final UserRequest request) {

    return User.builder()
        .email(request.getEmail())
        .name(request.getName())
        .password(request.getPassword())
        .logged(true)
        .build();
  }
}
