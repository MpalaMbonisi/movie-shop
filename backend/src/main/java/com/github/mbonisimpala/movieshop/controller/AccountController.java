package com.github.mbonisimpala.movieshop.controller;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/{email}")
    public ResponseEntity<Long> getAccount(@PathVariable String email){
        return new ResponseEntity<>(accountService.getAccountIdByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> saveAccount(@RequestBody @Valid Account account){
        accountService.addAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
