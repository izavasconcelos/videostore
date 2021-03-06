package com.izavasconcelos.videostore.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieResponse {

  private Long id;
  private String title;
  private String director;
  private Integer totalAvailable;
}
