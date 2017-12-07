package org.edu.melody.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
	String name;
	long id;
	LocalDateTime creationTime;
	List<Song> songs;
}
