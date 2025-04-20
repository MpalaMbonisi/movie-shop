package com.github.mbonisimpala.movieshop.controller;

import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        return new ResponseEntity<>(movieService.getMovie(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<List<Movie>> getAllMoviesByGenre(@PathVariable Long id){
        return new ResponseEntity<>(movieService.getAllMoviesByGenre(id), HttpStatus.OK);
    }

}
