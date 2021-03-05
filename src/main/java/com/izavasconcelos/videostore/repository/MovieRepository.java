package com.izavasconcelos.videostore.repository;

import com.izavasconcelos.videostore.model.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

  List<Movie> findByTitle(String title);

  List<Movie> findAll();

  @Modifying
  @Query(value = " update movies set unavailable = :unavailable where id = :movieId ", nativeQuery = true)
  int update(@Param("unavailable") int unavailable, @Param("movieId") Long movieId);
}
