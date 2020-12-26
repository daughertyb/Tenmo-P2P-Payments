package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.InsufficientFundsException;
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

	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	List<Transfer> getAllTransfersById(Principal principal) {

		int id = userDao.findIdByUsername(principal.getName());

		List<Transfer> listAllTransfers = transferDao.getAllTransfersById(id);
		return listAllTransfers;
	}

	@RequestMapping(path = "/send", method = RequestMethod.POST)
	public void sendFunds(@RequestBody Transfer transfer) throws InsufficientFundsException {

		int userId = transfer.getFromUser();
		double balance = transferDao.getBalanceDouble(userId);

		if (transfer.getAmount() < balance) {

			transferDao.sendFunds(transfer.getFromUser(), transfer.getToUser(), transfer.getAmount());
		} else {
			throw new InsufficientFundsException();

		}

	}

	@RequestMapping(path = "/request", method = RequestMethod.POST)
	public void requestFunds(@RequestBody Transfer transfer) {

		int userId = transfer.getFromUser();
		double balance = transferDao.getBalanceDouble(userId);

		transferDao.requestFunds(transfer.getFromUser(), transfer.getToUser(), transfer.getAmount());
		
	}
	
	@RequestMapping(path = "/view-pending-transfers", method = RequestMethod.GET)
	List<Transfer> viewPendingTransfers(@RequestBody Transfer transfer) {

		int userId = transfer.getFromUser();

		List<Transfer> pendingTransfers = transferDao.viewPendingRequests(userId);
		
		return pendingTransfers;
	}

}
