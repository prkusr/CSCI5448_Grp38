package org.edu.melody.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetupDDDTO {
	private long accountNo;
	private long routingNumber;
	private String bankName;
	private String bankAddress;
	private String sessionId;

}
