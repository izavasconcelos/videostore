package com.izavasconcelos.videostore.service;

import com.izavasconcelos.videostore.exceptions.UserException;
import com.izavasconcelos.videostore.model.User;
import com.izavasconcelos.videostore.repository.UserRepository;
import java.util.List;
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
  public User create(final User user) {
    try {
      return userRepository.save(user);
    } catch (DbActionExecutionException e) {
      throw new UserException("Failed to save new User", e);
    }
  }
}
