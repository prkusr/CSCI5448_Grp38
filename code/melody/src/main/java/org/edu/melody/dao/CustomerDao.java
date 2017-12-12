package org.edu.melody.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Song;

public class CustomerDao extends AbstractDAO{
	//All database operation here
	
	private static final Logger logger = LogManager.getLogger(CustomerDao.class);
	public void buySong(Song sng){
		try{
			//check user loggged in
		Statement stmt = null;
		int id=sng.getId();
		id=1;
		stmt = getConnection().createStatement();
		String query = "SELECT cost FROM SONGS WHERE songid = " + String.valueOf(id);
		
		ResultSet rs = stmt.executeQuery(query);
		if (rs.next()) {
			logger.info("Cost of song is" + rs.getFloat(1));
		}
		else{
			logger.info("Song does not exist in our database :("); 
		}
		}
		catch(Exception e){
			
		}
	}
}
