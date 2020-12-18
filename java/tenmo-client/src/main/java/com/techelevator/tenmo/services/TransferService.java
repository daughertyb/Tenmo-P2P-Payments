package com.techelevator.tenmo.services;

import java.io.Console;
import java.security.Principal;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.UserDataHandler;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Balance;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class TransferService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL = "http://localhost:8080/";
	private final RestTemplate restTemplate = new RestTemplate();
	private Balance balance = new Balance();
	private User user = new User();

	public Balance getBalance() {

		try {
			balance = restTemplate.exchange(BASE_URL + "get-balance", HttpMethod.GET, makeAuthEntity(), Balance.class)
					.getBody();

			System.out.println("Your current account balance is: $" + balance.getBalance());
		} catch (RestClientException e) {
			System.out.println("Error Retrieving Balance");
		}

		return balance;

	}

	public User[] getAllUsers() {
		User[] users = null;

		try {
			users = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
			System.out.println("Please select a user");
			for (User user : users) {
				System.out.println(user.getId() + " | " + user.getUsername());

			}
		}

		catch (ResourceAccessException ex) {
			System.out.println("Error getting users.");
		}

		return users;
	}

	public void sendFunds(AuthenticatedUser currentUser) {
		User[] users = null;
		Transfer transfer = new Transfer();

		try {
			Scanner scanner = new Scanner(System.in);
			users = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
			System.out.println("-------------------------------------------\r\n" + "Users\r\n" + "ID\t\tName\r\n"
					+ "-------------------------------------------");
			for (User user : users) {
				if (user.getId() != currentUser.getUser().getId()) {
					System.out.println(user.getId() + "\t\t" + user.getUsername());
				}
			}
			System.out
					.print("-------------------------------------------\r\n" + "Enter ID of user you are sending to: ");
			transfer.setToUser(Integer.parseInt(scanner.nextLine()));
			transfer.setFromUser(currentUser.getUser().getId());
			if (transfer.getToUser() != 0) {
				System.out.print("Enter amount: ");
				try {
					transfer.setAmount(Double.parseDouble(scanner.nextLine()));
				} catch (NumberFormatException e) {
					System.out.println("Error when entering amount");
				}
				try {
					String output = restTemplate.exchange(BASE_URL + "send", HttpMethod.POST,
							makeAuthEntityWithBody(transfer), String.class).getBody();
				} catch (RestClientException e) {
					System.out.println();
					System.out.println("Insufficient Funds");
				}
			}
		} catch (Exception e) {
			System.out.println("Bad input");
		}

	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

	private HttpEntity makeAuthEntityWithBody(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(transfer, headers);
		return entity;
	}

}
