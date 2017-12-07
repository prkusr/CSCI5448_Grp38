package org.edu.melody.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class DirectDeposit extends Payment {
	private long accountNo;
	private long routingNumber;
	private String bankName;
	private String bankAddress;
	
	public int sendOTP(int receiverNum){
		return 0;
	}
	
	public boolean insertPaymentDetails(){
		// PaymentDao.insertPayment();
		return false;
	}
	
	@Override
	public boolean isValid() {
		return false;
	}
	
	
}
