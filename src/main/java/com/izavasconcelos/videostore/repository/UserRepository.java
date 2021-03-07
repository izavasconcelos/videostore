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

  @Modifying
  @Query(
      value = " update users  set logged = :logged where email = :email and password = :password ",
      nativeQuery = true)
  int updateLogin(@Param("email") String email,
                  @Param("password") String password,
                  @Param("logged") boolean logged);

  @Modifying
  @Query(
      value = " update users  set logged = :logged where email = :email ",
      nativeQuery = true)
  int updateLogout(@Param("email") String email,
                   @Param("logged") boolean logged);
}
