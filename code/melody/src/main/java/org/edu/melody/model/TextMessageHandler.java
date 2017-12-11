package org.edu.melody.model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TextMessageHandler extends MessageHandler {
	
	private long receiverNumber;
	public static final String ACCOUNT_SID = "AC8af8feedded5bc53aef9816363048148";
	static final String AUTH_TOKEN = "9c319bfa6f551a352893f0ed488108fd";
	
	public void sendMessage(String m){
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber("+17205464358"), // to
                new PhoneNumber("+18583675731 "), // from
                m)
       .create();

	}
	public void addMessageHeader(){
		
	}
	public boolean send(){
		return false;
	}
}
