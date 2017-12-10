package org.edu.melody.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.MelodyApplication;
import org.edu.melody.dao.UserProfile;
import org.edu.melody.model.Customer;
import org.edu.melody.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestService {

	private static final Logger logger = LogManager.getLogger(TestService.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> isServiceRunning() {

		List<User> testUsers = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Customer cust =  Customer.builder().isActive(i % 2 == 0).userId(new Random().nextInt()).build();
			testUsers.add(cust);
		}
		UserProfile usr = new UserProfile();
		logger.debug("Printed the data...........");
		usr.loadUserProfile();
		return testUsers;
	}
	
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public List<User> checkIsUserExist() {

		List<User> testUsers = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Customer cust =  Customer.builder().email("Test" + i + 1).isActive(i % 2 == 0).userId(new Random().nextInt()).build();
			testUsers.add(cust);
		}
		UserProfile usr = new UserProfile();
		usr.loadUserProfile();
		return testUsers;
	}
}
