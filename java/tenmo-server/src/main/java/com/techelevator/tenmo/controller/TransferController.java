package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Balance;
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

}
