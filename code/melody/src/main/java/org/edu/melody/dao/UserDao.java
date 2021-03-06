package org.edu.melody.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.*;
import org.edu.melody.response.Response;

public class UserDao extends AbstractDAO {

	private static final Logger logger = LogManager.getLogger(UserDao.class);

	public void saveUserData(User user, String password, Class type, Response resp) {

		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM Users WHERE userName = '" + user.getUserName() + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String updateStmt = "UPDATE Users " + " SET email = '" + user.getEmail() + "' , cellNo = "
						+ String.valueOf(user.getCellNumber());
				if (password.length() > 0)
					updateStmt += " , password = crypt('" + password + "', gen_salt('md5'))";

				updateStmt += " WHERE userName = '" + user.getUserName() + "'";

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
				// stmt = getConnection().createStatement();
				String insertStmt = "INSERT INTO Users(userName, password, email, cellNo, type)" + "VALUES('"
						+ user.getUserName() + "'" + ", " + "crypt('" + password + "', gen_salt('md5'))" + ", '"
						+ user.getEmail() + "'" + " , " + String.valueOf(user.getCellNumber()) + " , "
						+ String.valueOf(userType) + ")";
				// logger.debug("Query: " + insertStmt);
				stmt.execute(insertStmt);
				logger.debug("Created new user: " + user.getUserName());
			}
			stmt.close();
		} catch (Exception e) {
			logger.error("Error in saving User Data... : " + e.getClass().getName() + ": " + e.getMessage() + "\n"
					+ e.getStackTrace());
			resp.setError(1, "Error in saving User Data... : " + e.getClass().getName() + ": " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Unable to close statement", e);
			}
		}
	}

	public void loadUserProfile(User user, Response resp) {

		try {

			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM Users WHERE userId = " + String.valueOf(user.getUserId());
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				if ((rs.getInt("status") & 1) == 1)
					resp.setError(1, "[" + rs.getString("userName") + "] account is temporarily disabled.");
				else {
					user.setEmail(rs.getString("email"));
					user.setUserName(rs.getString("userName"));
					user.setSessionCreatedTime(new Date());
					user.setCellNumber(rs.getLong("cellno"));
					user.setAdmin((rs.getInt("type") == 1) ? true : false);
				}
			}
		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage() + "\n" + e.getStackTrace());
			resp.setError(1, "Error in loading user profile: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void flipActivationStatusForUser(String userName, Response resp) {
		try {

			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "UPDATE Users SET status = (CASE status WHEN 1 THEN 0 WHEN 0 THEN 1 END) WHERE userName = '"
					+ userName + "'";
			int rowUpdateCount = stmt.executeUpdate(query);
			if (rowUpdateCount < 1) {
				resp.setError(1, "Could find the username [" + userName + "] in DB.");
			}
		} catch (Exception e) {
			String errStr = "Error in changing activation status for user: [" + userName + "] - "
					+ e.getClass().getName() + ": " + e.getMessage();
			logger.error(errStr + "\n" + e.getStackTrace());
			resp.setError(1, errStr);
		}
	}

	public Integer checkIfUserExist(String userName, String password, Response resp) {

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
			logger.error(e.getClass().getName() + ": " + e.getMessage() + "\n" + e.getStackTrace());
			resp.setError(1, "Error in checking for user: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	public int createPlaylist(String playListName, List<Integer> songIds, long userId) {
		Statement stmt = null;
		int playlistId = -1;
		try {

			stmt = getConnection().createStatement();
			String query = "Insert into playlist(playlistname,userid,creationdate) values('" + playListName + "',";
			StringBuilder q = new StringBuilder(query);
			q.append(userId).append(",");
			q.append("current_date)");

			query = q.toString();

			playlistId = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

			query = "INSERT INTO playlistsongsassoc VALUES";
			q = new StringBuilder(query);

			for (Integer songId : songIds) {
				q.append("(").append(playlistId).append(",").append(songId).append("),");
			}
			query = q.toString();
			// Remove last character
			query = query.substring(0, query.length() - 1);

			stmt.executeUpdate(query);

		} catch (Exception e) {
			logger.debug("Unable to create playlist", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.debug("Error while closing statment", e);
			}
		}
		return playlistId;
	}

	public void updatePlaylist(String name, List<Integer> songIds, long userId, long playListId) {
		Statement stmt = null;
		try {

			stmt = getConnection().createStatement();
			String query;

			if (!AbstractDAO.isStringEmpty(name)) {
				query = "update playlist set playlistname = '" + name + "'";
				stmt.executeUpdate(query);
			}

			if (songIds != null && !songIds.isEmpty()) {
				query = "Delete from playlistsongsassoc where playlistid = " + playListId;
				stmt.executeUpdate(query);
				query = "INSERT INTO playlistsongsassoc VALUES";
				StringBuilder q = new StringBuilder(query);
				for (Integer songId : songIds) {
					q.append("(").append(playListId).append(",").append(songId).append("),");
				}
				query = q.toString();
				// Remove last character
				query = query.substring(0, query.length() - 1);
				stmt.executeUpdate(query);
			}
		} catch (Exception e) {
			logger.debug("Unable to create playlist", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.debug("Error while closing statment", e);
			}
		}
	}
}
