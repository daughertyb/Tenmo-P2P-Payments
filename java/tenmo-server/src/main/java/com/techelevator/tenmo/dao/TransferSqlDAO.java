package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public double getBalanceDouble(int userId) {

		String sql = "SELECT balance FROM accounts WHERE user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

		double resultDouble = 0.00;

		if (results.next()) {
			resultDouble = results.getDouble("balance");
		}
		return resultDouble;
	}

	@Override
	public Balance getBalance(int userId) {

		String sql = "SELECT balance FROM accounts WHERE user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

		Balance userBalance = new Balance();

		if (results.next()) {
			userBalance.setBalance(results.getDouble("balance"));

		}
		return userBalance;
	}

	@Override
	public void sendFunds(int fromUser, int toUser, double amount) {

		double senderCurrentBalance = this.getBalance(fromUser).getBalance();
		double recepientCurrentBalance = this.getBalance(toUser).getBalance();

		double newSenderBalance = senderCurrentBalance - amount;
		double newRecepientBalance = recepientCurrentBalance + amount;

		String sqlSubtract = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		String sqlAdd = "UPDATE accounts SET balance = ? WHERE account_id = ?";

		jdbcTemplate.update(sqlSubtract, newSenderBalance, fromUser);
		jdbcTemplate.update(sqlAdd, newRecepientBalance, toUser);

		String sqlTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
				+ " VALUES (2, 2, ?, ?, ?)";

		jdbcTemplate.update(sqlTransfer, fromUser, toUser, amount);

	}
	
	@Override
	public void requestFunds(int fromUser, int toUser, double amount) {

		String sqlRequest = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
				+ " VALUES (1, 1, ?, ?, ?)";

		jdbcTemplate.update(sqlRequest, toUser, fromUser, amount);

	}
	
	@Override
	public List<Transfer> viewPendingRequests(int userId) {
		List<Transfer> list = new ArrayList<Transfer>();
		String sqlPendingRequests = "SELECT * FROM transfers WHERE transfer_status_id = 1 AND account_from = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlPendingRequests, userId);
		System.out.println(userId + "**************************************************************************************");
		while (results.next()) {
			Transfer transfer = new Transfer();
			transfer.setTransferId(results.getInt("transfer_id"));
			transfer.setTransferTypeId(results.getInt("transfer_type_id"));
			transfer.setTransferStatusId(results.getInt("transfer_status_id"));
			transfer.setFromUser(results.getInt("account_From"));
			transfer.setToUser(results.getInt("account_to"));
			transfer.setAmount(results.getDouble("amount"));

			list.add(transfer);
		}
		return list;
	
		
	}

	@Override
	public List<Transfer> getAllTransfersById(int userId) {
		List<Transfer> list = new ArrayList<>();
		String sql = "SELECT * FROM transfers WHERE account_from = ? OR account_to = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);

		while (results.next()) {
			Transfer transfer = new Transfer();
			transfer.setTransferId(results.getInt("transfer_id"));
			transfer.setTransferTypeId(results.getInt("transfer_type_id"));
			transfer.setTransferStatusId(results.getInt("transfer_status_id"));
			transfer.setFromUser(results.getInt("account_From"));
			transfer.setToUser(results.getInt("account_to"));
			transfer.setAmount(results.getDouble("amount"));

			list.add(transfer);
		}
		return list;
	}

}
