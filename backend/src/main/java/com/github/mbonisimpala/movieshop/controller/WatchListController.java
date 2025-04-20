package com.github.mbonisimpala.movieshop.controller;

import com.github.mbonisimpala.movieshop.entity.WatchListItem;
import com.github.mbonisimpala.movieshop.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    WatchListService watchListService;

    @PostMapping("/account/{accountId}/movie/{movieId}")
    public ResponseEntity<WatchListItem> saveWatchListItem(@PathVariable Long accountId, @PathVariable Long movieId){
        return new ResponseEntity<>(watchListService.saveWatchListItem(accountId, movieId), HttpStatus.CREATED);
    }

    @DeleteMapping("/account/{accountId}/movie/{movieId}")
    public ResponseEntity<HttpStatus> deleteWatchListItem(@PathVariable Long accountId, @PathVariable Long movieId){
        watchListService.deleteWatchListItem(accountId, movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/account/{id}/all")
    public ResponseEntity<List<WatchListItem>> getAllWatchListItems(@PathVariable Long id){
        return new ResponseEntity<>(watchListService.getAllWatchListItems(id), HttpStatus.OK);
    }

}
