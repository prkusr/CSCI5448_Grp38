package org.edu.melody.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.edu.melody.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestService {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<User> isServiceRunning() {

		List<User> testUsers = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			User u = User.builder().email("Test" + i + 1).isActive(i % 2 == 0).userId(new Random().nextInt()).build();
			testUsers.add(u);
		}
		return testUsers;
	}
}
