package org.edu.melody.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController extends Controller {

	static final Logger logger = LogManager.getLogger(UserController.class);

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public Customer login(@RequestParam("name") String userName, @RequestParam("pwd") String password,
			@RequestParam(value = "type", defaultValue = "c", required = false) String userType) {
		return Customer.builder().email("test@123").isActive(false).userId(123).test(userType).build();
	}
}
