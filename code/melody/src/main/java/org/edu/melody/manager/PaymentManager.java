package org.edu.melody.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.PaymentDao;
import org.edu.melody.model.DirectDeposit;
import org.edu.melody.model.TextMessageHandler;
import org.edu.melody.model.User;

public class PaymentManager {
	private static final Logger logger = LogManager.getLogger(PaymentManager.class);
	private PaymentDao payDao = new PaymentDao();
	public static Map<Integer, Integer> uiotp = new HashMap<>();
	private static Map<Integer, DirectDeposit> otpToDd = new HashMap<>();

	// Insert details, check validity, insert to database
	public void saveDDandSendOtp(User usr, long accountNo, long routingNumber, String bankName, String bankAddress) {
		DirectDeposit dp = DirectDeposit.builder().accountNo(accountNo).bankAddress(bankAddress).bankName(bankName)
				.routingNumber(routingNumber).build();
		// User wds = userManager.getUserInfo(sessionId);
		TextMessageHandler ad = new TextMessageHandler();
		long rcnum = usr.getCellNumber();
		ad.setReceiverNumber(rcnum);
		boolean sms = ad.send();

		if (sms == true) {
			uiotp.put((int) usr.getUserId(), ad.getOtp());
			otpToDd.put(ad.getOtp(),dp);
		}
		

	}
	public boolean validOTP(User usr, int entotp){
		if (uiotp.get(usr.getUserId())==entotp){
			return true;
		}
		return false;
	}
	public boolean addtodb(User usr, int entotp){
		if (validOTP(usr, entotp)){
			try{
				payDao.SetupDirectDeposit(usr, otpToDd.get(entotp));
			return true;
			}catch(Exception e){
				return false;
			}
		}
		return false;
		
		
	}
}
