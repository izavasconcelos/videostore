package com.izavasconcelos.videostore.user;

import com.izavasconcelos.videostore.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserResponse toResponse(final User user) {
    final UserResponse response = new UserResponse();

    response.setId(user.getId());
    response.setEmail(user.getEmail());
    response.setName(user.getName());
    response.setLogin(user.getLogin());
    response.setMovieId(user.getMovieId());

    return response;
  }

  public User toEntity(final UserRequest request) {
    final User user = new User();

    user.setEmail(request.getEmail());
    user.setName(request.getName());
    user.setPassword(request.getPassword());
    user.setMovieId(0L);
    user.setLogin(true);

    return user;
  }
}
