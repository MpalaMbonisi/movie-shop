package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.exception.AccountNotFoundException;
import com.github.mbonisimpala.movieshop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(long id) {
        return unwrapAccount(accountRepository.findById(id), id);
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }

    static Account unwrapAccount(Optional<Account> entity, long id){
        if(entity.isPresent()) return entity.get();
        else throw new AccountNotFoundException(id);
    }
}
