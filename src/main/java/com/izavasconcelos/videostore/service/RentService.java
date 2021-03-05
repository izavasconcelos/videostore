package com.izavasconcelos.videostore.service;

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
  public Optional<String> rentMovie(Rent entity) {

    boolean isDuplicated =
        rentRepository
            .findByEmailAndMovieId(entity.getEmail(), entity.getMovieId())
            .isEmpty();
//  tá ambiguo aqui **
    boolean isUserLogin =
        userRepository
            .findByEmail(entity.getEmail())
            .filter(User::getLogin)
            .isPresent();

    Optional<Movie> movie = movieRepository.findById(entity.getMovieId());

    boolean isMovieAvailable = movie
            .filter(m -> m.getAvailable() > m.getUnavailable())
            .isPresent();

    if (isMovieAvailable && isUserLogin && !isDuplicated) {
      rentRepository.saveRent(entity.getEmail(), entity.getMovieId());
      movieRepository.update(movie.get().getUnavailable()+1, entity.getMovieId());
      return Optional.of("{\n\"message\": \"movie successfully rented\" \n}");
    }

    return Optional.of("{\n\"message\": \"movie unavailable or user not found\" \n}");
  }

  @Transactional
  public Optional<String> devolveMovie(Rent entity) {
    boolean findEmail =
        rentRepository
            .findByEmailAndMovieId(entity.getEmail(), entity.getMovieId())
            .stream()
            .anyMatch(email -> email.getEmail().equals(entity.getEmail()));

    boolean isUserLogin =
        userRepository
            .findByEmail(entity.getEmail())
            .filter(User::getLogin)
            .isPresent();

    Optional<Movie> movie = movieRepository.findById(entity.getMovieId());

    boolean isMovieAvailable = movie
        .filter(m -> m.getAvailable() > m.getUnavailable())
        .isPresent();

    if (isMovieAvailable && isUserLogin && findEmail) {
      rentRepository.saveRent(entity.getEmail(), entity.getMovieId());
      movieRepository.update(movie.get().getUnavailable()+1, entity.getMovieId());
      return Optional.of("{\n\"message\": \"movie successfully rented\" \n}");
    }

    return Optional.of("{\n\"message\": \"movie unavailable or user not found\" \n}");
  }
}
