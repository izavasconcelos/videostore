package com.izavasconcelos.videostore.movie;

import static java.util.stream.Collectors.toList;

import com.izavasconcelos.videostore.model.Movie;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

  public List<MovieResponse> toResponse(final List<Movie> movies) {

    List<Movie> movieList =
        movies.stream()
            .filter(movie -> movie.getAvailable() > movie.getUnavailable())
            .collect(toList());

    return movieList.stream()
        .map(
            movie ->
                MovieResponse.builder()
                    .id(movie.getId())
                    .tilte(movie.getTitle())
                    .director(movie.getDirector())
                    .totalAvailable(movie.getAvailable() - movie.getUnavailable())
                    .build())
        .collect(toList());
  }
}
