package org.edu.melody.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.*;

public class UserDao extends AbstractDAO {

	private static final Logger logger = LogManager.getLogger(UserDao.class);

	public void saveUserData(User user, String password, Class type) {
		try {

			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM Users WHERE userName = '" + user.getUserName()+"'";
			ResultSet rs = stmt.executeQuery(query);			
			if (rs.next()) {

				stmt = getConnection().createStatement();
				String updateStmt = "UPDATE Users " + " SET email = '" + user.getEmail() + "' , cellNo = " + String.valueOf(user.getCellNumber());
				if (password.length() > 0)
					updateStmt += " , password = crypt('" + password + "', gen_salt('md5'))";

				updateStmt += " WHERE userName = '" + user.getUserName()+"'";

				stmt.executeUpdate(updateStmt);

				logger.debug("Updated user information for: " + rs.getString("userName"));
			} else {

				// New User
				int userType = 0;
				if (type.equals(Customer.class)) {
					userType = 2;
				} else {
					userType = 3;
				}
				stmt = getConnection().createStatement();
				String insertStmt = "INSERT INTO Users(userName, password, email, cellNo, type)" 
						+ "VALUES('" + user.getUserName() +"'"
						+ ", " + "crypt('" + password + "', gen_salt('md5'))"
						+ ", '" + user.getEmail() + "'" 
						+ " , " + String.valueOf(user.getCellNumber())
						+ " , " + String.valueOf(userType) + ")";
				logger.debug("Query: " + insertStmt);
				stmt.execute(insertStmt);
				logger.debug("Created new user: " + user.getUserName());
			}
			stmt.close();
		} catch (Exception e) {			
			logger.error("Error in saving User Data... : " + e.getClass().getName() + ": " + e.getMessage()+"\n"+e.getStackTrace());
		}
	}

	public void loadUserProfile(User user) {

		try {

			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM Users WHERE userId = " + String.valueOf(user.getUserId());
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				user.setActive(true);
				user.setEmail(rs.getString("email"));
				user.setUserName(rs.getString("userName"));
				user.setSessionCreatedTime(new Date());
				logger.debug(rs.getString("userName") + " " + rs.getString("email"));
			}
		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage()+"\n"+e.getStackTrace());
		}

	}

	public Integer checkIfUserExist(String userName, String password) {

		try {

			Statement stmt = getConnection().createStatement();
			String query = "SELECT userId FROM Users WHERE password = crypt('" + password + "', password)"
					+ " AND userName = '" + userName + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				logger.info(new Integer(rs.getInt("userId")));
				return (new Integer(rs.getInt("userId")));
			}
		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage()+"\n"+e.getStackTrace());
		}
		return null;
	}

	public void test() {
		try {
			
			//loadUserProfile(user);
			logger.info("Test DaTABASE");

		} catch (Exception e) {
			logger.error("Unble to db", e);
		}
		

	}
}
