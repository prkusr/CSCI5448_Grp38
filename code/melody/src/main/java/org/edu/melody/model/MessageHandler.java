package org.edu.melody.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageHandler {
	
	protected String message;
	protected String from;
	protected String subject;

	public void buildMessage(MessageHandler mes) {

	}

	public boolean send(MessageHandler mes) {
		return false;

	}

	public void addMessageHeader(MessageHandler mes) {

	}
}
