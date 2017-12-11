package org.edu.melody.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
	protected long userId;
	protected String userName;
	protected boolean isActive;
	protected Date sessionCreatedTime;
	protected String email;
	private boolean loggedIn;

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
		return new StringBuilder("User{").append("loggedIn" + isUserLoggedIn()).toString();
	}
}
