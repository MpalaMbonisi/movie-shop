package com.github.mbonisimpala.movieshop.controller;

import com.github.mbonisimpala.movieshop.entity.OrderedMovie;
import com.github.mbonisimpala.movieshop.service.OrderedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mylist")
public class MyListController {

    @Autowired
    OrderedMovieService orderedMovieService;

    @PostMapping("/account/{accountId}/movie/{movieId}")
    public ResponseEntity<OrderedMovie> saveOrderedMovie(@PathVariable Long accountId, @PathVariable Long movieId){
        return new ResponseEntity<>(orderedMovieService.saveOrderedMovie(accountId, movieId), HttpStatus.CREATED);
    }

    @GetMapping("/account/{id}/all")
    public ResponseEntity<List<OrderedMovie>> getAllOrderedMovies(@PathVariable Long id){
        return new ResponseEntity<>(orderedMovieService.getAllOrderedMovies(id), HttpStatus.OK);
    }

}
