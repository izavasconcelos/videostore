package com.izavasconcelos.videostore.user;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class LogoutRequest {

  @NotNull(message = "Email is required")
  @Length(max = 100, message = "Email can not have more the 100 characters")
  private String email;
}
