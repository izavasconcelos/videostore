package com.izavasconcelos.videostore.rent;

import com.izavasconcelos.videostore.model.Rent;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rent")
public class RentController {

  private final RentService rentService;
  private final RentMapper rentMapper;

  public RentController(RentService rentService, RentMapper rentMapper) {
    this.rentService = rentService;
    this.rentMapper = rentMapper;
  }

  @PostMapping
  public ResponseEntity<Rent> createRent(@RequestBody @Valid RentRequest requestBody) {
    return rentService.rentMovie(rentMapper.toEntity(requestBody))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }

  @DeleteMapping
  public ResponseEntity<Rent> deleteRent(@RequestBody @Valid RentRequest requestBody) {
    return rentService.devolveMovie(rentMapper.toEntity(requestBody))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }
}
