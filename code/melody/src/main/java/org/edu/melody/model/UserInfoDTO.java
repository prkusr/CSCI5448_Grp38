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

public class UserInfoDTO {
	String name;
	String pwd;
	String type;
	String email;
	long cellNum;
}
