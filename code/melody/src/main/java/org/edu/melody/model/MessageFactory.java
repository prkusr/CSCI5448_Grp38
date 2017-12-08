package org.edu.melody.model;

public class MessageFactory {
	public MessageHandler CreateMessage(int type){
		return new MessageHandler();
		
	}
}
