package com.bankapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal amount;
	private String type;
	private LocalDateTime timestamp;


	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	public Transaction() {}

	/**
	 * Creates the object transaction depends on the type
	 * @param amount
	 * @param type
	 * @param timestamp
	 * @param account
	 */
	public Transaction(BigDecimal amount, String type, LocalDateTime timestamp, Account account) {
		super();
		this.amount = amount;
		this.type = type;
		this.timestamp = timestamp;
		this.account = account;
	}

	/**
	 * Return the transaction id
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the transaction id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	

}
