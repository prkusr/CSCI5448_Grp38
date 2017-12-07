package org.edu.melody.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {

	private int id;
	private int artistId;
	private double cost;
	private String name;
	private String format;
	private String genre;
	private String link; // There should be URL in java need to check
	private int rating;
	private int noOfViews;
	private LocalDateTime releaseDate;

}
