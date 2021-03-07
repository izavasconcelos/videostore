package com.izavasconcelos.videostore.repository;

import com.izavasconcelos.videostore.model.Rent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends PagingAndSortingRepository<Rent, Long> {

  List<Rent> findByEmailAndMovieId(String email, Long movieId);

  Optional<Rent> findById(Long id);

  @Modifying
  @Query(
      value = " INSERT INTO rented_movie (email, id_movie) VALUES(:email, :movieId) ",
      nativeQuery = true)
  int saveRent(@Param("email") String email,
               @Param("movieId") Long movieId);

  @Modifying
  @Query(
      value = " DELETE FROM rented_movie WHERE email=:email AND id_movie=:movieId ",
      nativeQuery = true)
  int deleteRent(@Param("email") String email,
                 @Param("movieId") Long movieId);
}
