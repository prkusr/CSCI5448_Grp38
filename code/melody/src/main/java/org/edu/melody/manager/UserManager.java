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

	private String createSession(int userId, Class type){
		String sessionId = UUID.randomUUID().toString();
		
		sessions.add(sessionId);
		
		User user = UserFactory.createUser(type);
		user.setUserId(userId);
		usrDao.loadUserProfile(user);
		usersSessionsMap.put(sessionId, user);
		return sessionId;
	}
	
	public UserManager(){
		sessions = new ArrayList<String>();
		usersSessionsMap = new HashMap<String, User>(); 
	}
	
	public void signUp(String userName, String password, String email, long cellNo, Class type) {
		
		User user = UserFactory.createUser(type);
		user.setUserName(userName);
		user.setEmail(email);
		user.setCellNumber(cellNo);
		usrDao.saveUserData(user, password, type);
		
	}	
	
	public String signIn(String userName, String password, Class type){
		
		String sessionId;
		Integer userId = usrDao.checkIfUserExist(userName, password);
		
		if (userId != null){
			sessionId = createSession(userId, type);
			logger.debug("Created session for user: ["+userName+"].");
			return sessionId;
		}
		
		return null;
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
		
	
	public void logoutUser(String sessionId){
		
		User user = usersSessionsMap.get(sessionId);
		String userName = user.getUserName();
		usersSessionsMap.remove(sessionId);
		sessions.remove(sessionId);		
		logger.debug("Logged Out user: ["+userName+"].");
	}
	
}
