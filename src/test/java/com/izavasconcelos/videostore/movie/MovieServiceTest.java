package com.izavasconcelos.videostore.movie;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.izavasconcelos.videostore.model.Movie;
import com.izavasconcelos.videostore.repository.MovieRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieServiceTest {

  private MovieRepository mockMovieRepository;
  private MovieService movieService;

  @BeforeEach
  public void setup() {
    mockMovieRepository = mock(MovieRepository.class);
    movieService = new MovieService(mockMovieRepository);
  }

  @Test
  void shouldReturnAListWithMoviesAvailable() {
    List<Movie> itemsMock = buildAvailableMovies();

    when(mockMovieRepository.findAll()).thenReturn(itemsMock);

    List<Movie> result = movieService.findAll();

    verify(mockMovieRepository, times(1)).findAll();

    assertEquals(itemsMock.size(), result.size());

    assertAll(
        () -> assertEquals(itemsMock.get(0).getId(), result.get(0).getId()),
        () -> assertEquals(itemsMock.get(0).getTitle(), result.get(0).getTitle()),
        () -> assertEquals(itemsMock.get(0).getDirector(), result.get(0).getDirector()),
        () -> assertEquals(itemsMock.get(0).getAvailable(), result.get(0).getAvailable()),
        () -> assertEquals(itemsMock.get(0).getUnavailable(), result.get(0).getUnavailable()));

    assertAll(
        () -> assertEquals(itemsMock.get(1).getId(), result.get(1).getId()),
        () -> assertEquals(itemsMock.get(1).getTitle(), result.get(1).getTitle()),
        () -> assertEquals(itemsMock.get(1).getDirector(), result.get(1).getDirector()),
        () -> assertEquals(itemsMock.get(1).getAvailable(), result.get(1).getAvailable()),
        () -> assertEquals(itemsMock.get(1).getUnavailable(), result.get(1).getUnavailable()));
  }

  @Test
  void shouldReturnAListWithoutMoviesAvailable() {
    List<Movie> itemsMock = buildUnavailableMovie();

    when(mockMovieRepository.findAll()).thenReturn(itemsMock);

    List<Movie> result = movieService.findAll();

    verify(mockMovieRepository, times(1)).findAll();

    assertEquals(0, result.size());
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldFoundAMovieByTitle() {
    List<Movie> itemsMock = Collections.singletonList(buildAvailableMovies().get(1));
    String title = itemsMock.get(0).getTitle();

    when(mockMovieRepository.findByTitle(title)).thenReturn(itemsMock);

    List<Movie> result = movieService.findByTitle(title);

    verify(mockMovieRepository, times(1)).findByTitle(title);

    assertEquals(1, result.size());

    assertAll(
        () -> assertEquals(itemsMock.get(0).getId(), result.get(0).getId()),
        () -> assertEquals(itemsMock.get(0).getTitle(), result.get(0).getTitle()),
        () -> assertEquals(itemsMock.get(0).getDirector(), result.get(0).getDirector()),
        () -> assertEquals(itemsMock.get(0).getAvailable(), result.get(0).getAvailable()),
        () -> assertEquals(itemsMock.get(0).getUnavailable(), result.get(0).getUnavailable()));
  }

  @Test
  void shouldNotFoundAMovieByTitle() {
    List<Movie> itemsMock = buildAvailableMovies();
    String title = itemsMock.get(0).getTitle();
    String otherTitle = "A Invalid title";

    when(mockMovieRepository.findByTitle(title)).thenReturn(itemsMock);

    List<Movie> result = movieService.findByTitle(otherTitle);

    verify(mockMovieRepository, times(1)).findByTitle(otherTitle);

    assertEquals(0, result.size());
    assertNotEquals(title, otherTitle);
  }

  private List<Movie> buildAvailableMovies() {
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

    return Arrays.asList(movie1, movie2);
  }

  private List<Movie> buildUnavailableMovie() {
    Movie movie1 =
        Movie.builder()
            .id(50L)
            .title("Metallica")
            .director("James")
            .available(5)
            .unavailable(5)
            .build();

    return Collections.singletonList(movie1);
  }
}
