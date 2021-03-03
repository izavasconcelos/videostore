package com.izavasconcelos.videostore.repository;

import com.izavasconcelos.videostore.model.Movie;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

  @Query(
      " select * from movies "
          + " where title = :title ")
  List<Movie> findByTitle(@Param("title") String title);
}
