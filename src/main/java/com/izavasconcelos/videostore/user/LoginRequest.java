package com.izavasconcelos.videostore.user;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginRequest {

  @NotNull(message = "Email is required")
  @Length(max = 100, message = "Email can not have more then 100 characters")
  private String email;

  @NotNull(message = "Password is required")
  @Length(max = 20, message = "Password can not have more then 20 characters")
  private String password;
}
