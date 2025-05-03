package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.entity.WatchListItem;
import com.github.mbonisimpala.movieshop.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchListServiceImp implements WatchListService {

    @Autowired
    WatchListRepository watchListRepository;

    @Autowired
    AccountServiceImp accountService;

    @Autowired
    MovieServiceImp movieService;

    @Override
    public WatchListItem saveWatchListItem(long accountId, long movieId) {
        Account account = accountService.getAccount(accountId);
        Movie movie = movieService.getMovie(movieId);
        WatchListItem watchListItem = new WatchListItem(account, movie);
        return watchListRepository.save(watchListItem);
    }

    @Override
    public List<WatchListItem> getAllWatchListItems(long accountId) {
        return watchListRepository.findByAccountId(accountId);
    }

    @Override
    public void deleteWatchListItem(long accountId, long movieId) {
        watchListRepository.deleteByAccountIdAndMovieId(accountId, movieId);
    }
}
