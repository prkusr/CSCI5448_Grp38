package org.edu.melody.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.UserDao;
import org.edu.melody.model.*;

import java.util.UUID;

public class UserManager {
	
	private static final Logger logger = LogManager.getLogger(UserManager.class);
	
	private List<String> sessions;
	private HashMap<String, User> usersSessionsMap;
	private UserDao usrDao = new UserDao();

	public UserManager(){
		sessions = new ArrayList<String>();
		usersSessionsMap = new HashMap<String, User>(); 
	}
	
	public void createSession(int userId, Class type){
		String uniqueID = UUID.randomUUID().toString();
		
		sessions.add(uniqueID);
		
		User user = UserFactory.createUser(type);
		user.setUserId(userId);
		usrDao.loadUserProfile(user);
		usersSessionsMap.put(uniqueID, user);
	}
	
	public boolean isUserLoggedIn(String sessionId){
		
		for (int i=0; i<sessions.size(); i++){
			if(sessions.get(i).equals(sessionId))
				return true;
		}
		return false;
	}
	
	public User getUserInfo(String sessionId){
		return usersSessionsMap.get(sessionId);
	}
	
	public boolean verifyCredentials(String userName, String password, Class type){
		
		Integer userId = usrDao.checkIfUserExist(userName, password);
		if (userId != null){
			createSession(userId, type);
			logger.debug("Created session for user: ["+userName+"].");
			return true;
		}
		
		return false;
	}
	
	public void logoutUser(String sessionId){
		
		User user = usersSessionsMap.get(sessionId);
		String userName = user.getUserName();
		usersSessionsMap.remove(sessionId);
		sessions.remove(sessionId);		
		logger.debug("Logged Out user: ["+userName+"].");
	}
	
}
