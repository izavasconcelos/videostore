package com.izavasconcelos.videostore.repository;

import com.izavasconcelos.videostore.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByEmailAndPassword(String email, String password);

  @Modifying
  @Query(value = " update users  set login = :login where email = :email ", nativeQuery = true)
  int updateLogin(@Param("email") String email, @Param("login") boolean login);
}
