package com.izavasconcelos.videostore.rent;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.izavasconcelos.videostore.model.Movie;
import com.izavasconcelos.videostore.model.Rent;
import com.izavasconcelos.videostore.model.User;
import com.izavasconcelos.videostore.repository.MovieRepository;
import com.izavasconcelos.videostore.repository.RentRepository;
import com.izavasconcelos.videostore.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RentServiceTest {
  private RentRepository mockRentRepository;
  private UserRepository mockUserRepository;
  private MovieRepository mockMovieRepository;
  private RentService rentService;

  @BeforeEach
  public void setup() {
    mockRentRepository = mock(RentRepository.class);
    mockUserRepository = mock(UserRepository.class);
    mockMovieRepository = mock(MovieRepository.class);

    rentService = new RentService(mockRentRepository,
                                  mockUserRepository,
                                  mockMovieRepository);
  }

  @Test
  public void shouldRentAnAvailableMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.emptyList());

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.saveRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() + 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.rentMovie(rentMock);

    assertTrue(result.isPresent());

    assertAll(
        () -> assertEquals(rentMock.getMovieId(), result.get().getMovieId()),
        () -> assertEquals(rentMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(userMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(movieMock.getId(), result.get().getMovieId()));

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, times(1)).saveRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, times(1)).update(movieMock.getUnavailable() + 1, movieMock.getId());
  }

  @Test
  public void shouldNotRentADuplicatedMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.singletonList(rentMock));

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.saveRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() + 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.rentMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).saveRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() + 1, movieMock.getId());
  }

  @Test
  public void shouldNotRentAMovieWithUserLoggedOff() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    userMock.setLogged(false);

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.emptyList());

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.saveRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() + 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.rentMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).saveRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() + 1, movieMock.getId());
  }

  @Test
  public void shouldNotRentAUnavailableMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    movieMock.setAvailable(1);
    movieMock.setUnavailable(1);

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.emptyList());

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.saveRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() + 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.rentMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).saveRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() + 1, movieMock.getId());
  }

  @Test
  public void shouldNotRentANonexistentMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.emptyList());

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.empty());

    when(mockRentRepository.saveRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() + 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.rentMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).saveRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() + 1, movieMock.getId());
  }

  @Test
  public void shouldDevolveARentedMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.singletonList(rentMock));

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.deleteRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() - 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.devolveMovie(rentMock);

    assertTrue(result.isPresent());

    assertAll(
        () -> assertEquals(rentMock.getMovieId(), result.get().getMovieId()),
        () -> assertEquals(rentMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(userMock.getEmail(), result.get().getEmail()),
        () -> assertEquals(movieMock.getId(), result.get().getMovieId()));

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, times(1)).deleteRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, times(1)).update(movieMock.getUnavailable() - 1, movieMock.getId());
  }

  @Test
  public void shouldNotDevolveANotRentedMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.emptyList());

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.deleteRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() - 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.devolveMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).deleteRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() - 1, movieMock.getId());
  }

  @Test
  public void shouldNotDevolveAMovieWithUserLoggedOff() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    userMock.setLogged(false);

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.singletonList(rentMock));

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.of(movieMock));

    when(mockRentRepository.deleteRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() - 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.devolveMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).deleteRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() - 1, movieMock.getId());
  }

  @Test
  public void shouldNotDevolveANonexistentMovie() {
    Rent rentMock = buildRent();
    User userMock = buildUser();
    Movie movieMock = buildMovie();

    when(mockRentRepository.findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId()))
        .thenReturn(Collections.singletonList(rentMock));

    when(mockUserRepository.findByEmail(rentMock.getEmail())).thenReturn(Optional.of(userMock));

    when(mockMovieRepository.findById(rentMock.getMovieId())).thenReturn(Optional.empty());

    when(mockRentRepository.deleteRent(rentMock.getEmail(), rentMock.getMovieId())).thenReturn(1);

    when(mockMovieRepository.update(movieMock.getUnavailable() - 1, movieMock.getId()))
        .thenReturn(1);

    Optional<Rent> result = rentService.devolveMovie(rentMock);

    assertFalse(result.isPresent());

    verify(mockRentRepository, times(1))
        .findByEmailAndMovieId(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockUserRepository, times(1)).findByEmail(rentMock.getEmail());

    verify(mockMovieRepository, times(1)).findById(rentMock.getMovieId());

    verify(mockRentRepository, never()).deleteRent(rentMock.getEmail(), rentMock.getMovieId());

    verify(mockMovieRepository, never()).update(movieMock.getUnavailable() - 1, movieMock.getId());
  }

  private Rent buildRent() {

    return Rent.builder()
        .email("vasconcelos.izas@gmail.com")
        .movieId(13L)
        .build();
  }

  private User buildUser() {

    return User.builder()
        .id(13L)
        .email("vasconcelos.izas@gmail.com")
        .name("Izabela")
        .password("123456")
        .logged(true)
        .build();
  }

  private Movie buildMovie() {

    return Movie.builder()
        .id(13L)
        .title("Filme 1")
        .director("Axl Rose")
        .available(4)
        .unavailable(2)
        .build();
  }
}