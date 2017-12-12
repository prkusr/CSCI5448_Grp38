package org.edu.melody.model;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SetupDDDTO {
	private long accountNo;
	private long routingNumber;
	private String bankName;
	private String bankAddress;
	String sessionId;
	

}
