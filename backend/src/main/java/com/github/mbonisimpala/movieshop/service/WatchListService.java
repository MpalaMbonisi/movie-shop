package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.WatchListItem;

import java.util.List;

public interface WatchListService {

    List<WatchListItem> getAllWatchListItems(long accountId);
    void deleteWatchListItem(long accountId, long movieId);
    WatchListItem saveWatchListItem (long accountId, long movieId);

}
