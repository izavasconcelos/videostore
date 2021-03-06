package com.izavasconcelos.videostore.movie;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {
  private final MovieService movieService;
  private final MovieMapper movieMapper;

  public MovieController(MovieService movieService,
                         MovieMapper movieMapper) {

    this.movieService = movieService;
    this.movieMapper = movieMapper;
  }

  @GetMapping("/search")
  public List<MovieResponse> searchByTitle(@RequestParam("title") String title) {
    return movieMapper.toResponse(movieService.findByTitle(title));
  }

  @GetMapping
  public List<MovieResponse> findAllAvailable() {
    return movieMapper.toResponse(movieService.findAll());
  }
}
