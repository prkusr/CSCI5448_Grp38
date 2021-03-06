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
	private String name;
	private long id;
	private LocalDateTime creationTime;
	private List<Song> songs;
}
