package com.izavasconcelos.videostore.controller;

import com.izavasconcelos.videostore.movie.MovieMapper;
import com.izavasconcelos.videostore.movie.MovieResponse;
import com.izavasconcelos.videostore.service.MovieService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/video-store")
public class VideoStoreController {
  private final MovieService movieService;
  private final MovieMapper movieMapper;

  public VideoStoreController(MovieService movieService,
                              MovieMapper movieMapper) {

    this.movieService = movieService;
    this.movieMapper = movieMapper;
  }

  @GetMapping
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<MovieResponse> findAll(@RequestParam("title") String title) {

    return movieMapper.toResponse(movieService.findByTitle(title));
  }
}
