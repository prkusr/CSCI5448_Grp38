package org.edu.melody.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.manager.SongManager;
import org.edu.melody.model.Song;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/search")
public class SearchController extends Controller {

	static final Logger logger = LogManager.getLogger(SearchController.class);
	private static SongManager songManager = new SongManager();

	// @RequestMapping(value = "", method = RequestMethod.GET, params = "name")
	// public List<Song> searchByName(@RequestParam("name") String song) {
	// return songManager.searchSongs(song, null, null);
	// }
	//
	// @RequestMapping(value = "", method = RequestMethod.GET, params =
	// "artist")
	// public List<Song> searchByArtist(@RequestParam("artist") String artist) {
	// return songManager.searchSongs(null, null, artist);
	// }
	//
	// @RequestMapping(value = "", method = RequestMethod.GET, params = "date")
	// public List<Song> searchByDate(@RequestParam("date") String date) {
	// return songManager.searchSongs(null, date, null);
	// }

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Song> search(@RequestParam(value = "genre", defaultValue = "") String genre,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "artist", defaultValue = "") String artist,
			@RequestParam(value = "limit", defaultValue = "0") String limit) {
		return songManager.searchSongs(name, genre, artist, Integer.valueOf(limit));
	}

	@RequestMapping(value = "recent")
	public List<Song> recentSongs() {
		return new SongManager().getRecentSongs();

	}
}
