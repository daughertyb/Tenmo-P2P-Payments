package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;

public interface TransferDAO {

	public Balance getBalance(int userId);

	public void sendFunds(int fromUser, int toUser, double amount);

	public double getBalanceDouble(int userId);

}
