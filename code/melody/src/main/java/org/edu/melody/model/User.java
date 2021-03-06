package org.edu.melody.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public abstract class User {
	protected long userId;
	protected String userName;
	protected boolean isActive;
	protected Date sessionCreatedTime;
	protected long cellNumber;
	protected String email;
	private boolean loggedIn;
	private boolean isAdmin;	
	
	public void updatePayment() {
		// TODO : The code to update the payment
	}

	public void modifyActivation(boolean isActive) {
		// TODO : The code to update the payment
	}

	public void sendNotification() {
		// TODO : The code to update the payment
	}

	public boolean isUserLoggedIn() {
		return this.loggedIn == true;
	}

	@Override
	public String toString() {
		return new StringBuilder("User {").append("["+userName+"]").append(" : ["+String.valueOf(cellNumber)+"]").append(" : ["+email+"] }").toString();
	}
}
