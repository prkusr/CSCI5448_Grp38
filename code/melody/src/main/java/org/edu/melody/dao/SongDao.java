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

public class SongDao extends AbstractDAO {
	private static final Logger logger = LogManager.getLogger(SongDao.class);
	

	List<Song> getRecentSongs() {
		try {
			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM SONGS WHERE songid = " + String.valueOf("1");
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())  
				logger.info(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
				
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


	public void test() {
		// TODO Auto-generated method stub
try {
			getRecentSongs();
			//loadUserProfile(user);
			logger.info("Test DaTABASE song");

		} catch (Exception e) {
			logger.error("Unble to db", e);
		}
	}
}
