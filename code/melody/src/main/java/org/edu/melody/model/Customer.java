package org.edu.melody.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
	private PlayList playlist;
	private List<Song> userHistory;
	private Plan plan;
	private List<Song> recommendations;
	private List<Song> purchase;
	
	public void addPlaylist(Playlist playlist) {
		
	}
	
	public void updatePlaylist(Playlist playlist) {
		// TODO : The code to update the payment
	}
	
	public void deletePlaylist(Playlist playlist) {
		// TODO : The code to update the payment
	}
	
	public void changePlan(Plan plan) {
		// TODO : The code to update the payment
	}

	public void updatePlayHistory() {
		// TODO : The code to update the payment
	}

	public boolean insertCreditCardDetails() {
		// TODO : The code to update the payment
	}
	
	public boolean updateRecommendations() {
		// TODO : The code to update the payment
	}

	public boolean sharePlaylist(Playlist) {
		// TODO : The code to update the payment
	}
	
	public boolean buySong(Song song) {
		// TODO : The code to update the payment
	}
	@Override
	public String toString() {
		return new StringBuilder("User{").append("loggedIn" + isUserLoggedIn()).toString();
	}
}
