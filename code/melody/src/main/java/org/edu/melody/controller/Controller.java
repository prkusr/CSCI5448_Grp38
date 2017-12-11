package org.edu.melody.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
/*
 * Common class for the functions which are common to other controllers. Ex :
 * /artist/login or /customer/login or /admin/login
 */
public class Controller {
	
	@RequestMapping(value = "app")
	public String index() {
		return "Welcome to melody application";
	}

	@RequestMapping(value = "err")
	public String error() {
		return "Welcome to melody application";
	}

}