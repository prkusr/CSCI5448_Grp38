package org.edu.melody.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class Controller {

	@RequestMapping(value = "app")
	public String index() {
		return "Welcome to melody application";
	}

}