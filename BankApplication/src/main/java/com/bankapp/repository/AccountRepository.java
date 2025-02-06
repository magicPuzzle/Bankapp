package com.bankapp.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


import com.bankapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	/**
	 * Find an account user by his/her name
	 * @param username
	 * @return
	 */
	Optional<Account> findByUsername(String username);

}
