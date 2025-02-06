package com.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	/**
	 * Find the list of transactions made by user with his/her id
	 * @param accountId
	 * @return
	 */
	List<Transaction> findByAccountId(Long accountId);

}
