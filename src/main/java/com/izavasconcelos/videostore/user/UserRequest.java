package com.izavasconcelos.videostore.user;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  @NotNull(message = "Email is required")
  @Length(max = 100, message = "Email can not have more the 100 characters")
  private String email;

  @NotNull(message = "Name is required")
  @Length(max = 150, message = "Name can not have more the 150 characters")
  private String name;

  @NotNull(message = "Password is required")
  @Length(min = 6, max = 20, message = "Password can't have less then 6 and more the 20 characters")
  private String password;
}
