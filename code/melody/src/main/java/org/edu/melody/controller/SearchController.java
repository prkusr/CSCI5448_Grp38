package org.edu.melody.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Song;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/search")
public class SearchController extends Controller {

	static final Logger logger = LogManager.getLogger(SearchController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Song search(@RequestParam("song") String song) {
		
		return null;
	}
}
