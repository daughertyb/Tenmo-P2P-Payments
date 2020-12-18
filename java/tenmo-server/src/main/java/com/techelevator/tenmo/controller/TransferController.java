package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

	@Autowired
	TransferDAO transferDao;

	@Autowired
	UserDAO userDao;

	@RequestMapping(path = "/get-balance", method = RequestMethod.GET)
	public Balance getBalance(Principal principal) {

		int id = userDao.findIdByUsername(principal.getName());
		Balance balance = transferDao.getBalance(id);

		return balance;

	}

	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getAllUsers() {

		List<User> findAllUsers = userDao.findAll();
		return findAllUsers;

	}
	

	@RequestMapping(path = "/send", method = RequestMethod.POST)
	public Transfer sendFunds(@RequestBody Transfer transfer) {

		Transfer returnStatus = new Transfer();

		int userId = transfer.getFromUser();
		double balance = transferDao.getBalanceDouble(userId);

		if (transfer.getAmount() < balance) {
			
			transferDao.sendFunds(transfer.getFromUser(), transfer.getToUser(), transfer.getAmount());
		} else {
			System.out.println("Insufficient Funds");

		}

		return returnStatus;
	}

}
