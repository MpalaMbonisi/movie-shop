package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
