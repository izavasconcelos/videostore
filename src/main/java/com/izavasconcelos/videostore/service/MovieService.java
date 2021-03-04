package com.izavasconcelos.videostore.service;

import com.izavasconcelos.videostore.model.Movie;
import com.izavasconcelos.videostore.repository.MovieRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Transactional
  public List<Movie> findByTitle(String title) {
    return movieRepository.findByTitle(title);
  }

  @Transactional
  public List<Movie> findAll() {
    return movieRepository.findAll();
  }
}
