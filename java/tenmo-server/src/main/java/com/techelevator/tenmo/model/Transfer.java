package com.techelevator.tenmo.model;

public class Transfer {

	private int fromUser;
	private int ToUser;
	private double amount;
	private int transferTypeId;
	private int transferId;
	private int transferStatusId;

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

	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;

	}

}
