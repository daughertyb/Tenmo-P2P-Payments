package com.techelevator.tenmo.services;

import java.security.Principal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Balance;

public class TransferService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL = "http://localhost:8080/";
	private final RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser currentUser = new AuthenticatedUser();
	private Balance balance = new Balance();

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

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
