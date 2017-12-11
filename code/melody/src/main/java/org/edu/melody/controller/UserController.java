package org.edu.melody.controller;

import org.apache.logging.log4j.LogManager;
import org.edu.melody.model.User;

import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Customer;
import org.edu.melody.model.Artist;
import org.edu.melody.manager.UserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController extends Controller {

	static final Logger logger = LogManager.getLogger(UserController.class);
	
	UserManager usrManager = new UserManager(); 

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam("name") String userName, @RequestParam("pwd") String password,
			@RequestParam(value = "type", defaultValue = "c", required = true) String userType) {
		
		Class usrType;
		if (userType.equals("c"))
			usrType = Customer.class;
		else
			usrType = Artist.class;
		return usrManager.signIn(userName, password, usrType);
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public void signUp(@RequestParam("name") String userName, @RequestParam("pwd") String password,
			@RequestParam(value = "type", required = true) String userType,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "cellnum", required = true) long cellNum) {
		
		Class usrType;
		if (userType.equals("c"))
			usrType = Customer.class;
		else
			usrType = Artist.class;
		usrManager.signUp(userName, password, email, cellNum, usrType);
	}
	
	
	@RequestMapping(value = "signout", method = RequestMethod.GET)
	public void signOut(@RequestParam("sessionId") String sessionId) {
				
		usrManager.signOutUser(sessionId);
	}
	
	@RequestMapping(value = "getuser", method = RequestMethod.GET)
	public User getUserInfo(@RequestParam("sessionId") String sessionId) {
				
		return usrManager.getUserInfo(sessionId);
		
	}
}
