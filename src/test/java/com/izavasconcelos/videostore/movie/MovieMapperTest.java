package com.izavasconcelos.videostore.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.izavasconcelos.videostore.model.Movie;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieMapperTest {

  private MovieMapper movieMapper;

  @BeforeEach
  public void setup() {
    movieMapper = new MovieMapper();
  }

  @Test
  public void shouldMapperMovieListToResponse() {
    Movie movie1 =
        Movie.builder()
            .id(1L)
            .title("Filme 1")
            .director("Axl Rose")
            .available(1)
            .unavailable(0)
            .build();

    Movie movie2 =
        Movie.builder()
            .id(2L)
            .title("Filme 2")
            .director("Lita Ford")
            .available(3)
            .unavailable(1)
            .build();

    List<MovieResponse> result = movieMapper.toResponse(Arrays.asList(movie1, movie2));

    assertEquals(movie1.getId(), result.get(0).getId());
    assertEquals(movie1.getTitle(), result.get(0).getTitle());
    assertEquals(movie1.getDirector(), result.get(0).getDirector());
    assertEquals((movie1.getAvailable() - movie1.getUnavailable()), result.get(0).getTotalAvailable());

    assertEquals(movie2.getId(), result.get(1).getId());
    assertEquals(movie2.getTitle(), result.get(1).getTitle());
    assertEquals(movie2.getDirector(), result.get(1).getDirector());
    assertEquals((movie2.getAvailable() - movie2.getUnavailable()), result.get(1).getTotalAvailable());
  }
}
