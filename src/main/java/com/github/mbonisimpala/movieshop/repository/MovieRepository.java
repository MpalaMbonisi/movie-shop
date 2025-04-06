package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
