package org.edu.melody.model;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.PaymentDao;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.Data;
@Data
public class TextMessageHandler extends MessageHandler {
	
	protected long receiverNumber;
	protected int otp;
	public static final String ACCOUNT_SID = "AC8af8feedded5bc53aef9816363048148";
	static final String AUTH_TOKEN = "b32e8259c0fa9a98b7fa555baad094ae";
	private static final Logger logger = LogManager.getLogger(TextMessageHandler.class);
	public void sendMessage(String m){
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber("+1"+this.receiverNumber), // to
                new PhoneNumber(this.getFrom()), // from
                m)
       .create();
		logger.info("Successful SMS");

	}
	public void addMessageHeader(){
		Random rnd = new Random();

		int n = 10000 + rnd.nextInt(90000);
		this.otp=n;
		

		this.setMessage("This is your OTP:"+n);
	}
	public boolean send(){
		try{
			logger.info("Starting SMS");
			addMessageHeader();
			logger.info(this.getMessage());
			this.setFrom("+18583675731");
			sendMessage(this.getMessage());
			//this.set
			return true;	
		}catch(Exception e){
		return false;
		}
	}
}
