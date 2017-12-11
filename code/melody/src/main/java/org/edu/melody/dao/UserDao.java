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
			
			Class.forName("org.postgresql.Driver");	  
			conn = DriverManager
			          .getConnection("jdbc:postgresql://localhost:5432/melody",
			          "postgres", "ooad");
		} catch (Exception e) {		       
			logger.error(e.getClass().getName()+": "+e.getMessage());		       
		    }
		logger.info("Opened database successfully");	
		
	}
	
	public void saveUserData(User user, String password, Class type) {
		try {
 	       
		       Statement stmt = null;
		       stmt = conn.createStatement();
		       String query = "SELECT * FROM Users WHERE userName = "+String.valueOf(user.getUserName());
		       ResultSet rs = stmt.executeQuery(query);
		       stmt.close();
		       if ( rs.next() ) {
		    	   
		    	   stmt = conn.createStatement();
			       query = "UPDATE Users "
			    		   + " SET email = "+ user.getEmail()
			    		   + " , cellNo = " + user.getCellNumber();
			       if(password.length() > 0)
			    	   query += " , password = crypt('"+password+"', gen_salt('md5'))";
			       
			       query += " WHERE userName = "+user.getUserName();
			       
			       stmt.executeQuery(query);
			       
		    	   logger.debug("Updated user information for: "+rs.getString("userName"));		    	   
		       } else {
		    	   
		    	   // New User
		    	   int userType = 0;
		    	   if(type.equals(Customer.class)){
		    		   userType = 2;
			   		} else {
			   			userType = 3;
			   		}
		    	   stmt = conn.createStatement();
			       query = "INSERT INTO Users(userName, password, email, cellNo, type)"
			    		   + "VALUES("+ user.getUserName()+ user.getEmail()
			    		   + " , " + password 
			    		   + " , " + user.getEmail()
			    		   + " , " + user.getCellNumber()
			    		   + " , " + String.valueOf(userType) + ")";
			       
			       stmt.executeQuery(query);
			       logger.debug("Created new user: "+rs.getString("userName"));			       
		       }
		       stmt.close();
	    } catch (Exception e) {	       
	       logger.error("Error in saving User Data... : "+e.getClass().getName()+": "+e.getMessage());	       
	    }
	}
	
	public void loadUserProfile(User user){
	
		try {
	            	       
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
