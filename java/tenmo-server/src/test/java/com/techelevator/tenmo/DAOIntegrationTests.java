package com.techelevator.tenmo;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.channels.NonReadableChannelException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

class DAOIntegrationTests {

	private static SingleConnectionDataSource dataSource;
	private TransferDAO dao;
	private UserDAO userDao;
	private int tester1;
	private int tester2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		dataSource.destroy();
	}

	@BeforeEach
	void setUp() throws Exception {
		dao = new TransferSqlDAO(dataSource);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userDao = new UserSqlDAO(jdbcTemplate);

		userDao.create("tester1", "password");
		tester1 = userDao.findIdByUsername("tester1");

		userDao.create("tester2", "password");
		tester2 = userDao.findIdByUsername("tester2");
	}

	@AfterEach
	void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	void customer_1_has_balance_of_1000() {

		double actualResult = dao.getBalance(tester1).getBalance();
		assertEquals(1000, actualResult, 0.0);
	}

	@Test
	void creating_new_user_increases_size() {

		int expectedResult = userDao.findAll().size() + 1;

		userDao.create("tester", "testing");
		int actualResult = userDao.findAll().size();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void transfers_increase_balance() {
		int fromUser = 1;
		int toUser = 2;
		double amount = 25.00;

		dao.sendFunds(fromUser, toUser, amount);
		double expectedResult = 1025.00;
		double actualResult = dao.getBalance(toUser).getBalance();

		assertEquals(expectedResult, actualResult);

	}

	@Test
	void get_double_balance_for_user_based_on_user_id() {
		double expectedResult = 1000.00;
		int userId = 1;
		double actualResult = dao.getBalanceDouble(userId);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void get_transfers_by_user_id() {
		int fromUser = 1;
		int toUser = 2;
		double amount = 25.00;

		int expectedResult = dao.getAllTransfersById(fromUser).size() + 1;
		dao.sendFunds(fromUser, toUser, amount);
		int actualResult = dao.getAllTransfersById(fromUser).size();

		assertEquals(expectedResult, actualResult);
	}

}
