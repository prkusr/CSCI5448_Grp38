package org.edu.melody.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.edu.melody.model.Customer.CustomerBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Artist extends User {
	private int numberOfAlbums;
	private float monthlyPayment;
	private List<Song>	songs;
	private DirectDeposit directDeposit;
	
	public void modifyAccount(long accNum, long routingNum, String bankName, String bankAddr) {
		this.directDeposit.setAccountNo(accNum);
		this.directDeposit.setRoutingNumber(routingNum);
		this.directDeposit.setBankName(bankName);
		this.directDeposit.setBankAddress(bankAddr);
	}
}
