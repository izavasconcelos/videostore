package com.izavasconcelos.videostore.rent;

import com.izavasconcelos.videostore.model.Movie;
import com.izavasconcelos.videostore.model.Rent;
import com.izavasconcelos.videostore.model.User;
import com.izavasconcelos.videostore.repository.MovieRepository;
import com.izavasconcelos.videostore.repository.RentMovieRepository;
import com.izavasconcelos.videostore.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentService {

  private static final  Integer ONE_MOVIE = 1;
  private final RentMovieRepository rentRepository;
  private final UserRepository userRepository;
  private final MovieRepository movieRepository;

  public RentService(
      RentMovieRepository rentRepository,
      UserRepository userRepository,
      MovieRepository movieRepository) {
    this.rentRepository = rentRepository;
    this.userRepository = userRepository;
    this.movieRepository = movieRepository;
  }

  @Transactional
  public Optional<Rent> rentMovie(Rent entity) {

    boolean isNotDuplicated =
        rentRepository
            .findByEmailAndMovieId(entity.getEmail(), entity.getMovieId())
            .isEmpty();

    boolean isUserLogin =
        userRepository
            .findByEmail(entity.getEmail())
            .filter(User::getLogged)
            .isPresent();

    Optional<Movie> movie = movieRepository.findById(entity.getMovieId());

    boolean isMovieAvailable = movie
            .filter(m -> m.getAvailable() > m.getUnavailable())
            .isPresent();

    if (isMovieAvailable && isUserLogin && isNotDuplicated) {
      rentRepository.saveRent(entity.getEmail(), entity.getMovieId());
      movieRepository.update(movie.get().getUnavailable() + ONE_MOVIE, entity.getMovieId());
      return Optional.of(entity);
    }

    return Optional.empty();
  }

  @Transactional
  public Optional<Rent> devolveMovie(Rent rent) {
    boolean isNotRented =
        rentRepository
            .findByEmailAndMovieId(rent.getEmail(), rent.getMovieId())
            .isEmpty();

    boolean isUserLogin =
        userRepository
            .findByEmail(rent.getEmail())
            .filter(User::getLogged)
            .isPresent();

    Optional<Movie> movie = movieRepository.findById(rent.getMovieId());

    boolean movieExists = movie
        .filter(m -> m.getUnavailable() > 0)
        .isPresent();

    if (movieExists && isUserLogin && !isNotRented) {
      rentRepository.deleteRent(rent.getEmail(), rent.getMovieId());
      movieRepository.update(movie.get().getUnavailable() - ONE_MOVIE, rent.getMovieId());
      return Optional.of(rent);
    }

    return Optional.empty();
  }
}
