package org.edu.melody.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreditCard extends Payment {

	private long cardNo;
	private int cvv;
	private String issuer;
	private LocalDateTime expiry;
	private String nameOnCard;
	private String billingAddress;

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setup() {
		return false;
	}

	public boolean addCard(Payment payment) {
		return false;
	}

	public boolean modifyBillingAddress() {
		return false;
	}
}
