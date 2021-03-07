package com.izavasconcelos.videostore.rent;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentRequest {

  @NotNull(message = "Email is required")
  @Length(max = 100, message = "Email can not have more then 100 characters")
  private String email;

  @NotNull(message = "Id movie is required")
  private Long movieId;
}
