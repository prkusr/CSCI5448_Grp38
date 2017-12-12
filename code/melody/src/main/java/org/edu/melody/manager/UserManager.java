package org.edu.melody.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.AbstractDAO;
import org.edu.melody.dao.SongDao;
import org.edu.melody.dao.UserDao;
import org.edu.melody.model.*;
import org.edu.melody.response.Response;

import java.util.UUID;

public class UserManager {

	private static final Logger logger = LogManager.getLogger(UserManager.class);

	private List<String> sessions;
	private HashMap<String, User> usersSessionsMap;
	private UserDao usrDao = new UserDao();

	private String createSession(int userId, Class type, Response resp){
		String sessionId = UUID.randomUUID().toString();

		sessions.add(sessionId);

		User user = UserFactory.createUser(type);
		user.setUserId(userId);
		usrDao.loadUserProfile(user, resp);
		usersSessionsMap.put(sessionId, user);
		return sessionId;
	}

	public UserManager() {
		sessions = new ArrayList<String>();
		usersSessionsMap = new HashMap<String, User>();
	}
	
	public void signUp(String userName, String password, String email, long cellNo, Class type, Response resp) {

		User user = UserFactory.createUser(type);
		user.setUserName(userName);
		user.setEmail(email);
		user.setCellNumber(cellNo);
		usrDao.saveUserData(user, password, type, resp);		
	}	
	
	public String signIn(String userName, String password, Class type, Response resp){
		
		String sessionId;
		Integer userId = usrDao.checkIfUserExist(userName, password, resp);
		
		if (userId != null){
			sessionId = createSession(userId, type, resp);
			logger.debug("Created session for user: ["+userName+"].");
			return sessionId;
		} else {
			logger.error("Username/password doesn't match: ["+userName+"].");
			resp.setMessage("Username/password doesn't match: ["+userName+"].");
		}

		return null;
	}

	public boolean isUserLoggedIn(String sessionId) {
		return sessions.contains(sessionId);
	}

	public User getUserInfo(String sessionId) {
		if (isUserLoggedIn(sessionId))
			return usersSessionsMap.get(sessionId);
		else
			logger.error("User not loggedIn.");
		return null;
	}

	public void signOutUser(String sessionId) {

		User user = usersSessionsMap.get(sessionId);
		String userName = user.getUserName();
		usersSessionsMap.remove(sessionId);
		sessions.remove(sessionId);
		logger.debug("Logged Out user: [" + userName + "].");
	}
	
	public void updateAccDetails(long accNum, long routingNum, String bankName, String bankAddr, Response resp) {
		
		
		
	}

	public int createPlaylist(String playListName, List<Integer> songIds, long userId) {
		if (!AbstractDAO.isStringEmpty(playListName) && songIds != null && !songIds.isEmpty())
			return usrDao.createPlaylist(playListName, songIds, userId);
		return -1;
	}

	public void updatePlaylist(String name, List<Integer> songIds, long userId, long playListId) {
		if (!AbstractDAO.isStringEmpty(name) || (songIds != null && !songIds.isEmpty())) {
			usrDao.updatePlaylist(name, songIds, userId, playListId);
		}
	}

}
