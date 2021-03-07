package com.izavasconcelos.videostore.rent;

import static org.mockito.Mockito.mock;

import com.izavasconcelos.videostore.repository.MovieRepository;
import com.izavasconcelos.videostore.repository.RentRepository;
import com.izavasconcelos.videostore.repository.UserRepository;
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
  public void shouldThrowExceptionWhenCreateNewUser() {

  }
}
