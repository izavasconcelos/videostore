package com.izavasconcelos.videostore.rent;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.izavasconcelos.videostore.model.Rent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RentMapperTest {

  private RentMapper rentMapper;

  @BeforeEach
  public void setup() {
    rentMapper = new RentMapper();
  }

  @Test
  public void shouldConvertRequestToRentEntity() {
    final RentRequest rentRequest = createRequest();

    Rent rent = rentMapper.toEntity(rentRequest);

    assertAll(
        () -> assertEquals(rent.getEmail(), rentRequest.getEmail()),
        () -> assertEquals(rent.getMovieId(), rentRequest.getMovieId()));
  }

  private RentRequest createRequest() {

    return RentRequest.builder()
        .email("ozzyosbourne@gmail.com")
        .movieId(13L)
        .build();
  }
}
