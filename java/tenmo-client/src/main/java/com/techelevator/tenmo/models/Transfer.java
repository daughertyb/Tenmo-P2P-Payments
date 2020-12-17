package com.techelevator.tenmo.models;

public class Transfer {

	private int fromUser;
	private int ToUser;
	private double amount;


	public int getFromUser() {
		return fromUser;
	}

	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}

	public int getToUser() {
		return ToUser;
	}

	public void setToUser(int toUser) {
		ToUser = toUser;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
