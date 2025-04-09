package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.WatchListItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface WatchListRepository extends CrudRepository<WatchListItem, Long> {
    List<WatchListItem> findByAccountId(Long accountId);
    @Transactional
    void deleteByAccountIdAndMovieId (Long accountId, Long movieId);
}
