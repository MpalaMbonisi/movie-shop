package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.exception.AccountNotFoundException;
import com.github.mbonisimpala.movieshop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder byCryptPasswordEncoder;

    @Override
    public Account addAccount(Account account) {
        account.setPassword(byCryptPasswordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Long getAccountIdByEmail(String email) {
        return unwrapAccount(accountRepository.findByEmail(email), email).getId();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return unwrapAccount(accountRepository.findByEmail(email), email);
    }


    @Override
    public Account getAccount(Long id) {
        return unwrapAccount(accountRepository.findById(id), id.toString());
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }

    static Account unwrapAccount(Optional<Account> entity, String email){
        if(entity.isPresent()) return entity.get();
        else throw new AccountNotFoundException(email);
    }
}
