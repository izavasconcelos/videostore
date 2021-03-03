package com.izavasconcelos.videostore.service;

import static java.util.stream.Collectors.toList;

import com.izavasconcelos.videostore.model.Movie;
import com.izavasconcelos.videostore.movie.MovieResponse;
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

  public List<MovieResponse> convertToMovieResponse(List<Movie> movieList) {
    return movieList.stream()
        .map(
            movie ->
                MovieResponse.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .director(movie.getDirector())
                    .totalAvailable(movie.getAvailable() - movie.getUnavailable())
                    .build())
        .collect(toList());
  }
}
