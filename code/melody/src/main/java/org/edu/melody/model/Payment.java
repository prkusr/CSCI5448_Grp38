package org.edu.melody.model;

import lombok.Data;

@Data
public abstract class Payment {
	protected long id;
	protected double amount;

	public abstract boolean isValid();

	public void makePayment(Payment payment) {
		
	}
	
	public void processRefund(Payment payment){
		
	}
	
	public void chargeUser(double amount){
		
	}
}
