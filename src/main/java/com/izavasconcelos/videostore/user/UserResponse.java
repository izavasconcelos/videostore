package com.izavasconcelos.videostore.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

  private Long id;
  private String email;
  private String name;
  private Boolean login;
  private Long movieId;
}
