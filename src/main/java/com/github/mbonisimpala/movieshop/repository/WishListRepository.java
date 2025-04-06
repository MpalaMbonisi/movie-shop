package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.WishList;
import org.springframework.data.repository.CrudRepository;

public interface WishListRepository extends CrudRepository<WishList, Long> {
}
