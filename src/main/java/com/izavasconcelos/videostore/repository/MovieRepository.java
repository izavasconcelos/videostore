package com.izavasconcelos.videostore.repository;

import com.izavasconcelos.videostore.model.Movie;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

  List<Movie> findByTitle(String title);

  List<Movie> findAll();
}
