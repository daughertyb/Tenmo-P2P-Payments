package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Balance;

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

}
