package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
