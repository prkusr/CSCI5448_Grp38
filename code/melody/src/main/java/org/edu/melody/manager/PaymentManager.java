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
	public static Map<Long, Long> uiotp = new HashMap<>();
	private static Map<Long, DirectDeposit> otpToDd = new HashMap<>();

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
			uiotp.put(usr.getUserId(), ad.getOtp());
			otpToDd.put(ad.getOtp(), dp);
		}

	}

	public boolean validOTP(User usr, long entotp) {
		return uiotp.get(usr.getUserId()) != null && uiotp.get(usr.getUserId()) == entotp;
	}

	public boolean insertDDFromOTP(User usr, long entotp) {
		boolean success = false;
		if (validOTP(usr, entotp)) {
			try {
				payDao.setupDirectDeposit(usr, otpToDd.get(entotp));
				success = true;
			} catch (Exception e) {
				logger.error("Error while validating the OTP and saving DD details", e);
			}
		}
		return success;
	}
	
	public void updateDirectDepostAcc(long userId, DirectDeposit dd){
		payDao.updateDirectDeposit(userId, dd);
	}
}
