package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.WatchList;
import org.springframework.data.repository.CrudRepository;

public interface WatchListRepository extends CrudRepository<WatchList, Long> {
}
