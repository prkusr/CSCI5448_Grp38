package org.edu.melody.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.commons.collections4.CollectionUtils;
import org.edu.melody.dao.SongDao;
import org.edu.melody.model.Song;

public class SongManager {

	private static final Logger logger = LogManager.getLogger(SongManager.class);
	private static List<Song> trendingSongs;
	private static List<Song> recentSongs;
	private static List<Song> highRatedSongs;
	private SongDao songDao = new SongDao();

	public List<Song> getRecentSongs() {
		if (recentSongs == null || recentSongs.isEmpty()) {
			recentSongs = songDao.getRecentSongs();
		}
		return recentSongs;
	}

	public List<Song> searchSongs(String name, String genre, String artist, int limit) {
		List<Song> songs = new ArrayList<>();
		try {
			// Make unified for release date,search key.
			// Guard clause in DAO. But bad idea
			List<Song> allSongs = songDao.searchSongs(name, genre, artist, limit);
			// Sort using Java 8 lambdas
			// allSongs.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));

			List<Song> containsList = new ArrayList<>();
			allSongs.forEach((song) -> {
				if (song.getName().toLowerCase().startsWith(name.toLowerCase()))
					songs.add(song);
				else if (song.getName().toLowerCase().contains(name.toLowerCase())) {
					containsList.add(song);
				}
			});
			songs.addAll(containsList);
		} catch (Exception e) {
			logger.debug("Unable to get search results", e);
		}
		return songs;
	}

	public void updateRecentSongs(List<Integer> songIds) {

	}

	public void calculateScores() {

	}

	public void updateTrendingSongs() {

	}

	public boolean updateNewRating(int songId) {
		return false;
	}

	public List<Song> getRecommendations() {
		return songDao.getRecommendations();
	}
}
