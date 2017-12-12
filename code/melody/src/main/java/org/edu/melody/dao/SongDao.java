package org.edu.melody.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Song;

public class SongDao extends AbstractDAO {
	private static final Logger logger = LogManager.getLogger(SongDao.class);

	public List<Song> getRecentSongs() {
		List<Song> recentSongs = new ArrayList<>();
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM songs order by releasedate desc limit 200";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				recentSongs.add(getSongFromResultSet(rs));
			}
		} catch (Exception e) {
			logger.error("Unable to get recent songs", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Unable to close statement", e);
			}
		}
		return recentSongs;
	}

	public List<Song> searchSongs(String name, String genre, String artist, int limit) {
		List<Song> songs = new ArrayList<>();
		Statement stmt = null;

		try {
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM songs";
			// name ILIKE '%" + name + "%' order by releasedate";
			StringBuilder q = new StringBuilder(query);
			boolean isWhere = false;

			if (!isStringEmpty(name)) {
				q.append(" where ").append("name ILIKE '%").append(name).append("%'");
				isWhere = true;
			}
			if (!isStringEmpty(genre)){
				q.append(isWhere ? " and " : " where ").append("genre ILIKE '%").append(genre).append("%'");
				isWhere = true;
			}
			if (!isStringEmpty(artist)){
				q.append(isWhere ? " and " : " where ").append("artistname = '").append(artist).append("'");
				isWhere = true;
			}
			q.append(" order by releasedate");
			if (limit > 0)
				q.append(" limit ").append(limit);

			query = q.toString();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				songs.add(getSongFromResultSet(rs));
			}
		} catch (Exception e) {
			logger.error("Unable to get recent songs", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Unable to close statement", e);
			}
		}
		return songs;
	}

	public static Song getSongFromResultSet(ResultSet rs) {
		try {
			return Song.builder().artistId(rs.getInt("artistid")).cost(rs.getDouble("cost"))
					.format(rs.getString("format")).genre(rs.getString("genre")).id(rs.getInt("songid"))
					.link(rs.getString("songpath")).name(rs.getString("name"))
					// .releaseDate(
					// LocalDateTime.parse(rs.getString("releasedate"),
					// DateTimeFormatter.ISO_LOCAL_DATE_TIME))
					.build();
		} catch (SQLException e) {
			logger.debug("Error while buiding Song from database", e);
		}
		return null;
	}

	public void test() {
		// TODO Auto-generated method stub
		try {
			getRecentSongs();
			logger.info("Test DaTABASE song");

		} catch (Exception e) {
			logger.error("Unble to db", e);
		}
	}

	public List<Song> getRecommendations() {
		List<Song> reccomendations = new ArrayList<>();
		Statement stmt = null;
		try {
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM songs order by random() limit " + ThreadLocalRandom.current().nextInt(15, 51);
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				reccomendations.add(getSongFromResultSet(rs));
			}
		} catch (Exception e) {
			logger.error("Unable to get recent songs", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Unable to close statement", e);
			}
		}
		return reccomendations;
	}
}
