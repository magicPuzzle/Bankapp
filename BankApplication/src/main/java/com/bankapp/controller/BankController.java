package com.bankapp.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankapp.model.Account;
import com.bankapp.service.AccountService;

@Controller
public class BankController {
	
	@Autowired
	private AccountService accountService;

	/**
	 *
	 * Take the user to the Main window to the app
	 * @param model
	 * @return
	 */
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.findAccountByUsername(username);
		model.addAttribute("account", account);
		return "dashboard";
	}

	/**
	 * Shows the form to register
	 * @return
	 */
	@GetMapping("/register")
	public String showRegisterForm() {
		return "register";
	}

	/**
	 *
	 * Use to register a person as user with password in the data base, and be able to login
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@PostMapping("/register")
	public String registerAccount(@RequestParam String username,@RequestParam String password, Model model ) {
		try {
			 accountService.registerAccount(username, password);
			return "redirect:/login";
		} catch(RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}

	/**
	 *
	 * Use to login in the app, an be able to see your current balance and operate
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	/**
	 *
	 * Use to add an amount of money to the users account
	 * @param amount
	 * @return
	 */
	@PostMapping("/deposit")
	public String deposit(@RequestParam BigDecimal amount) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.findAccountByUsername(username);
		accountService.deposit(account, amount);
		return "redirect:/dashboard";
	}

	/**
	 * Use to extract money from the account
	 *
	 * @param amount
	 * @param model
	 * @return
	 */
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam BigDecimal amount, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.findAccountByUsername(username);
		
		try {
			accountService.withdraw(account, amount);
		}catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("account", account);
			return "dashboard";
		}
		
		return "redirect:/dashboard";
	}

	/**
	 * An user can see the history of deposits, withdrawal, transfers made
	 * @param model
	 * @return
	 */
	@GetMapping("/transactions")
	public String transactionHistory(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountService.findAccountByUsername(username);
		model.addAttribute("transactions", accountService.getTransactionHistory(account));
		return "transactions";
	}


	/**
	 * A user transfer an a amount of money to another user account
	 * @param toUsername
	 * @param amount
	 * @param model
	 * @return
	 */
	@PostMapping("/transfer")
	public String transferAmount(@RequestParam String toUsername,@RequestParam BigDecimal amount, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account fromAccount = accountService.findAccountByUsername(username);
		
		try {
			accountService.transferAmount(fromAccount, toUsername, amount);
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("account", fromAccount);
		}
		
		return "redirect:/dashboard";
	}
	
	
}
