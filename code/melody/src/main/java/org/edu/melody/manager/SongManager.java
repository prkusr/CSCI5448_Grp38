package org.edu.melody.manager;

import java.util.List;

import org.edu.melody.model.Song;

import lombok.Data;

@Data
public class SongManager {

	private List<Song> trendingSongs;
	private List<Song> recentSongs;
	private List<Song> highRatedSongs;

	public void updateRecentSongs(List<Integer> songIds) {
//		Song
	}

	public void calculateScores() {

	}

	public void updateTrendingSongs() {

	}

	public boolean updateNewRating(int songId) {
		return false;
	}
}
