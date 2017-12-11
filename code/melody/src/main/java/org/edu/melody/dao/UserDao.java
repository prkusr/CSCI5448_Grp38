package org.edu.melody.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.*;

public class UserDao {
	
	private static final Logger logger = LogManager.getLogger(UserDao.class);
	Connection conn = null;
    
	public UserDao(){
		try{
			
		
			conn = DriverManager
			          .getConnection("jdbc:postgresql://localhost:5432/melody",
			          "postgres", "ooad");
		} catch (Exception e) {		       
			logger.error(e.getClass().getName()+": "+e.getMessage());		       
		    }
		logger.info("Opened database successfully");	
		
	}
	
	public void loadUserProfile(User user){
	
		try {
	       Class.forName("org.postgresql.Driver");	       
	       
	       Statement stmt = null;
	       stmt = conn.createStatement();
	       String query = "SELECT * FROM Users WHERE userId = "+String.valueOf(user.getUserId());
	       ResultSet rs = stmt.executeQuery(query);
	       if ( rs.next() ) {
	    	   user.setActive(true);
	    	   user.setEmail(rs.getString("email"));
	    	   user.setUserName(rs.getString("userName"));
	    	   user.setSessionCreatedTime(new Date());
	    	   logger.debug(rs.getString("userName")+" "+rs.getString("email"));
	       }
	    } catch (Exception e) {	       
	       logger.error(e.getClass().getName()+": "+e.getMessage());	       
	    }
	    	
	}
	
	public Integer checkIfUserExist(String userName, String password){
		
		try {
	       Class.forName("org.postgresql.Driver");	       
	       
	       Statement stmt = conn.createStatement();	       
	       String query = "SELECT userId FROM Users WHERE password = crypt('"+password+"', password)"+
	    		   			" AND userName = '"+userName+"'";
	       ResultSet rs = stmt.executeQuery( query );
	       if ( rs.next()) {
        	 return (new Integer(rs.getInt("userId")));
	       }
	    } catch (Exception e) {	       
	    	logger.error(e.getClass().getName()+": "+e.getMessage());	       
	    }
	    return null;
	}
}
