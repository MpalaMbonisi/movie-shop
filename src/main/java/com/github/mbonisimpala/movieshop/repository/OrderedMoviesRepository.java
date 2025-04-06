package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.OrderedMovies;
import org.springframework.data.repository.CrudRepository;

public interface OrderedMoviesRepository extends CrudRepository<OrderedMovies, Long> {
}
