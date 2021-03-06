package com.izavasconcelos.videostore.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.izavasconcelos.videostore.exceptions.UserException;
import com.izavasconcelos.videostore.model.User;
import com.izavasconcelos.videostore.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

public class UserServiceTest {

  private UserRepository mockUserRepository;
  private UserService userService;

  @BeforeEach
  public void setup() {
    mockUserRepository = mock(UserRepository.class);
    userService = new UserService(mockUserRepository);
  }

  @Test
  public void shouldToCreateANewUser() {
    User userMock = buildUser();

    when(mockUserRepository.save(userMock)).thenReturn(userMock);

    Optional<User> result = userService.create(userMock);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    verify(mockUserRepository, times(1)).save(userMock);

    assertTrue(result.isPresent());

    assertAll(
        () -> assertEquals(userMock.getId(), result.get().getId()),
        () -> assertEquals(userMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(userMock.getName(), result.get().getName()),
        () -> assertEquals(userMock.getPassword(), result.get().getPassword()),
        () -> assertEquals(userMock.getLogged(), result.get().getLogged()));
  }

  @Test
  public void shouldTryToCreateAnExistingUser() {
    User userMock = buildUser();

    when(mockUserRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockUserRepository.save(userMock)).thenReturn(userMock);

    Optional<User> result = userService.create(userMock);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    verify(mockUserRepository, never()).save(userMock);

    assertFalse(result.isPresent());
  }

  @Test
  public void shouldTryToCreateUserWithInvalidPassword() {
    User userMock = buildUser();

    userMock.setPassword("123");

    when(mockUserRepository.save(userMock)).thenReturn(userMock);

    Optional<User> result = userService.create(userMock);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    verify(mockUserRepository, never()).save(userMock);

    assertFalse(result.isPresent());
  }

  @Test
  public void shouldThrowExceptionWhenCreateNewUser() {
    User userMock = buildUser();

    when(mockUserRepository.save(userMock)).thenThrow(UserException.class);

    assertThrows(UserException.class, () -> userService.create(userMock));
  }

  @Test
  public void shouldFoundAnExistingUserByEmail() {
    User userMock = buildUser();

    when(mockUserRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));

    Optional<User> result = userService.findByEmail(userMock.getEmail());

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    assertAll(
        () -> assertEquals(userMock.getId(), result.get().getId()),
        () -> assertEquals(userMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(userMock.getName(), result.get().getName()),
        () -> assertEquals(userMock.getPassword(), result.get().getPassword()),
        () -> assertEquals(userMock.getLogged(), result.get().getLogged()));
  }

  @Test
  public void shouldNotFoundANonExistentUserByEmail() {
    User userMock = buildUser();

    Optional<User> result = userService.findByEmail(userMock.getEmail());

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    assertFalse(result.isPresent());
  }

  @Test
  public void shouldLoginAnExistingUser() {
    User userMock = buildUser();

    when(mockUserRepository.updateLogin(userMock.getEmail(), userMock.getPassword(), true))
        .thenReturn(1);

    when(mockUserRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));

    Optional<User> result = userService.login(userMock.getEmail(), userMock.getPassword());

    verify(mockUserRepository, times(1))
        .updateLogin(userMock.getEmail(), userMock.getPassword(), true);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    assertAll(
        () -> assertEquals(userMock.getId(), result.get().getId()),
        () -> assertEquals(userMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(userMock.getName(), result.get().getName()),
        () -> assertEquals(userMock.getPassword(), result.get().getPassword()),
        () -> assertEquals(userMock.getLogged(), result.get().getLogged()));
  }

  @Test
  public void shouldNotLoginANonExistingUser() {
    User userMock = buildUser();

    when(mockUserRepository.updateLogin(userMock.getEmail(), userMock.getPassword(), true))
        .thenReturn(0);

    Optional<User> result = userService.login(userMock.getEmail(), userMock.getPassword());

    verify(mockUserRepository, times(1))
        .updateLogin(userMock.getEmail(), userMock.getPassword(), true);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    assertFalse(result.isPresent());
  }

  @Test
  public void shouldLogoutAnExistingUser() {
    User userMock = buildUser();

    when(mockUserRepository.updateLogout(userMock.getEmail(), false)).thenReturn(1);

    when(mockUserRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));

    Optional<User> result = userService.logout(userMock.getEmail());

    verify(mockUserRepository, times(1)).updateLogout(userMock.getEmail(), false);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    assertAll(
        () -> assertEquals(userMock.getId(), result.get().getId()),
        () -> assertEquals(userMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(userMock.getName(), result.get().getName()),
        () -> assertEquals(userMock.getPassword(), result.get().getPassword()),
        () -> assertEquals(userMock.getLogged(), result.get().getLogged()));
  }

  @Test
  public void shouldNotLogoutANonExistingUser() {
    User userMock = buildUser();

    when(mockUserRepository.updateLogout(userMock.getEmail(), false)).thenReturn(0);

    Optional<User> result = userService.logout(userMock.getEmail());

    verify(mockUserRepository, times(1)).updateLogout(userMock.getEmail(), false);

    verify(mockUserRepository, times(1)).findByEmail(userMock.getEmail());

    assertFalse(result.isPresent());
  }

  private User buildUser() {

    return User.builder()
        .id(13L)
        .email("vasconcelos.izas@gmail.com")
        .name("Izabela")
        .password("123456")
        .logged(true)
        .build();
  }
}
