package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String username);
}
