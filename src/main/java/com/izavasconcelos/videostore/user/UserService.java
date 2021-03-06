package com.izavasconcelos.videostore.user;

import com.izavasconcelos.videostore.exceptions.UserException;
import com.izavasconcelos.videostore.model.User;
import com.izavasconcelos.videostore.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public Optional<User> create(User user) {

    if (findByEmail(user.getEmail()).isPresent()) {
      return Optional.empty();
    }

    try {
      return Optional.of(userRepository.save(user));
    } catch (DbActionExecutionException e) {
      throw new UserException("Failed to save new User", e);
    }
  }

  @Transactional
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public Optional<User> login(String email, String password) {

    userRepository.updateLogin(email, password,true);

    return findByEmail(email);
  }

  @Transactional
  public Optional<User> logout(String email) {

    userRepository.updateLogout(email, false);

    return findByEmail(email);
  }
}
