package com.izavasconcelos.videostore.service;

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
  public Boolean validateLogin(String email, String password) {

    Optional<User> user = userRepository.findByEmailAndPassword(email, password);

    if (user.isPresent() && !user.get().getLogin()) {
      int updateLogin = userRepository.updateLogin(email, true);
      return updateLogin == 1;
    }
    return false;
  }

  @Transactional
  public Boolean logout(String email) {
    int rows = userRepository.updateLogin(email, false);
    return rows == 1;
  }
}
