package com.izavasconcelos.videostore.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.izavasconcelos.videostore.model.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

  private UserMapper userMapper;

  @BeforeEach
  public void setup() {
    userMapper = new UserMapper();
  }

  @Test
  public void shouldMapperUserToResponse() {
    final User user = User.builder()
            .email("axl@gmail.com")
            .name("Axl Rose")
            .password("198500")
            .logged(true)
            .build();

    Optional<UserResponse> userResponse = userMapper.toResponse(Optional.of(user));

    assertEquals(user.getEmail(), userResponse.get().getEmail());
    assertEquals(user.getLogged(), userResponse.get().getLogged());
  }

  @Test
  public void shouldConvertRequestToUserEntity() {
    final UserRequest userRequest = createRequest();

    User user = userMapper.toEntity(userRequest);

    assertAll(
        () -> assertEquals(user.getEmail(), userRequest.getEmail()),
        () -> assertEquals(user.getName(), userRequest.getName()),
        () -> assertEquals(user.getPassword(), userRequest.getPassword()));
  }

  private UserRequest createRequest() {
    return UserRequest.builder()
        .email("ozzyosbourne@gmail.com")
        .name("Ozzy")
        .password("666666")
        .build();
  }
}
