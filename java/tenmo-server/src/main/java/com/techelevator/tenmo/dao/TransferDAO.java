package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	public Balance getBalance(int userId);

	public void sendFunds(int fromUser, int toUser, double amount);

	public double getBalanceDouble(int userId);

	List<Transfer> getAllTransfersById(int userId);

	public void requestFunds(int fromUser, int toUser, double amount);

	List<Transfer> viewPendingRequests(int userId);

	
}
