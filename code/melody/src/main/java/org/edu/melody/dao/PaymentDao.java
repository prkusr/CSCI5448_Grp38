package org.edu.melody.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.CreditCard;
import org.edu.melody.model.DirectDeposit;
import org.edu.melody.model.User;

public class PaymentDao extends AbstractDAO {
	private static final Logger logger = LogManager.getLogger(PaymentDao.class);

	public void setupDirectDeposit(User usr, DirectDeposit dp) {
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			long uid = usr.getUserId();
			String query = "INSERT INTO DirectDepositDetails VALUES(" + uid + ",'" + String.valueOf(dp.getAccountNo())
					+ "','" + String.valueOf(dp.getRoutingNumber()) + "','" + String.valueOf(dp.getBankName()) + "','"
					+ String.valueOf(dp.getBankAddress()) + "')";

			stmt.execute(query);
		} catch (Exception e) {
			logger.error("Error in saving User Data... ");
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateDirectDeposit(long userId, DirectDeposit dp) {
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();			
			String query = "UPDATE DirectDepositDetails SET "
					+ " accountNo = '" + String.valueOf(dp.getAccountNo())+"', "
					+ " routingNo = '" + String.valueOf(dp.getRoutingNumber())+"', "
					+ " bankName = '" + String.valueOf(dp.getBankName())+"', "
					+ " bankAddr = '" + String.valueOf(dp.getBankAddress())+"' "			
					+ " WHERE userId = "+userId;

			stmt.execute(query);
		} catch (Exception e) {
			logger.error("Error in updating direct deposit data for userId ["+userId+"]. ");
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void SetupCreditCard(User usr, CreditCard dp) {
		try {
			Statement stmt = null;
			stmt = getConnection().createStatement();
			long uid = usr.getUserId();
			/*
			 * long uid=1; CreditCard dp= new CreditCard(); dp.setCardNo(12345);
			 * dp.setCvv(123); dp.setIssuer("Chase");
			 * dp.setExpiry(LocalDateTime.now());
			 * dp.setBillingAddress("Mrine Court");
			 */
			String query = "INSERT INTO CreditCardDetails VALUES(" + uid + ",'" + String.valueOf(dp.getCardNo())
					+ "',crypt('" + String.valueOf(dp.getCvv()) + "',gen_salt('md5')),'"
					+ String.valueOf(dp.getIssuer()) + "'," + String.valueOf(dp.getExpiry().getMonthValue()) + ","
					+ String.valueOf(dp.getExpiry().getYear()) + ",'" + dp.getNameOnCard() + "','"
					+ dp.getBillingAddress() + "')";
			// logger.entry(query);

			stmt.execute(query);
		} catch (Exception e) {
			logger.error("Error in saving User Data... ");

		}

	}

	public void DebitUser(User usr, double amount) {
		try {
			Statement stmt = null;
			stmt = getConnection().createStatement();
			long uid = usr.getUserId();
			// long uid=19;
			String query = "INSERT INTO PaymentDetails(userId,amount,paymentDate,comments) values(" + uid + ","
					+ String.valueOf(amount) + ",'" + String.valueOf(LocalDate.now()) + "'," + "'Success')";
			stmt.execute(query);
		} catch (Exception e) {

		}
	}

	public void CreditUser(User usr, double amount) {
		try {
			Statement stmt = null;
			stmt = getConnection().createStatement();
			long uid = usr.getUserId();
			// long uid=19;
			String query = "INSERT INTO PaymentDetails(userId,amount,paymentDate,comments) values(" + uid + ","
					+ String.valueOf(-1.0 * amount) + ",'" + String.valueOf(LocalDate.now()) + "',"
					+ "'SuccessArtist')";
			stmt.execute(query);
		} catch (Exception e) {

		}
	}

	public float debitSong(User usr, int sng) {
		try {
			Statement stmt = null;
			float cst = 0.0f;
			stmt = getConnection().createStatement();
			// long uid=usr.getUserId();
			// long uid=19;
			String query = "SELECT cost from Songs where songid=" + sng;
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				cst = (float) rs.getFloat(1);
			DebitUser(usr, cst);
			return cst;

		} catch (Exception e) {

		}
		return 0;
	}
	
}
