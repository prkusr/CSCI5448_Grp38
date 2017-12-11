package org.edu.melody.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Song;

public class SongDao {
	private static final Logger logger = LogManager.getLogger(SongDao.class);
	Connection conn = null;
	private static final String jdbc = "jdbc:postgresql://localhost:5432/melody";
	private static final String db = "postgres";
	private static final String pass = "ooad";

	public SongDao() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(jdbc, db, pass);
			} catch (Exception e) {
				logger.error(e.getClass().getName() + ": " + e.getMessage());
			}
			logger.info("Opened database successfully");
		}
	}

	List<Song> getRecentSongs() {
		try {
			Class.forName("org.postgresql.Driver");
			Statement stmt = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM Users WHERE userId = " + String.valueOf("12");
			ResultSet rs = stmt.executeQuery(query);
//			if (rs.next()) {
//				user.setActive(true);
//				user.setEmail(rs.getString("email"));
//				user.setUserName(rs.getString("userName"));
//				user.setSessionCreatedTime(new Date());
//				logger.debug(rs.getString("userName") + " " + rs.getString("email"));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Song>();
	}
}
