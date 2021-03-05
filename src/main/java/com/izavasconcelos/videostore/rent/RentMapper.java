package com.izavasconcelos.videostore.rent;

import com.izavasconcelos.videostore.model.Rent;
import org.springframework.stereotype.Component;

@Component
public class RentMapper {

  public Rent toEntity(final RentRequest request) {
    final Rent rent = new Rent();

    rent.setEmail(request.getEmail());
    rent.setMovieId(request.getMovieId());

    return rent;
  }
}
