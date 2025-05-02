package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;

public interface AccountService {

    Account getAccount(Long id);
    Account getAccountByEmail(String email);
    Account addAccount(Account account);
    void deleteAccount(long id);

}
