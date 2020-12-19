package com.techelevator.tenmo.models;

public class Transfer {

	private int fromUser;
	private int ToUser;
	private int transferId;
	private int transferTypeId;
	private String transferTypeDesc;
	private int transferStatusId;
	private String transferStatusDesc;
	private double amount;


	public String getTransferTypeDesc() {
		return transferTypeDesc;
	}

	public void setTransferTypeDesc(String transferTypeDesc) {
		this.transferTypeDesc = transferTypeDesc;
	}

	public String getTransferStatusDesc() {
		return transferStatusDesc;
	}

	public void setTransferStatusDesc(String transferStatusDesc) {
		this.transferStatusDesc = transferStatusDesc;
	}

	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public int getTransferTypeId() {
		return transferTypeId;
	}

	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public int getTransferStatusId() {
		return transferStatusId;
	}

	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}

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
