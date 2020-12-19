package com.techelevator.tenmo.model;

public class Transfer {

	private int fromUser;
	private int ToUser;
	private double amount;
	private int transferTypeId;
	private int transferId;
	private int transferStatusId;
	private String transferTypeDesc;
	private String transferStatusDesc;
	
	
	
	
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
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public int getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	public void setTransferTypeDesc(String string) {
		this.transferTypeDesc = transferTypeDesc;
		
	}
	public void setTransferStatusDesc(String string) {
		this.transferStatusDesc = transferStatusDesc;
		
	}
	

	

}
