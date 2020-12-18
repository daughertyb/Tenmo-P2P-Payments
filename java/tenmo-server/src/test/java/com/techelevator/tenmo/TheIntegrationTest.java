package com.techelevator.tenmo;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.model.Balance;

class TheIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private TransferDAO dao;

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
		dao = new TransferSqlDAO(dataSource) {
			
			@Override
			public void sendFunds(int fromUser, int toUser, double amount) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Balance getBalance(int userId) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@AfterEach
	void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	void customer_1_has_balance_of_1000() {
		
		double actualResult = dao.getBalance(1).getBalance();
		assertEquals(1001, actualResult, 0.0);
	}

}
