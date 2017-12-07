package org.edu.melody.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.edu.melody.model.Customer.CustomerBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends User {
	private int numberOfAlbums;
	private float monthlyPayment;
	private List<Song>	songs;
	private Payment account;
	
	public void modifyAccount(Payment directDep) {
		// TODO : The code to update the payment
	}
}
