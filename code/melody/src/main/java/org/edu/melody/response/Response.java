package org.edu.melody.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.edu.melody.model.Plan;
import org.edu.melody.model.Artist;
import org.edu.melody.model.User;
import org.edu.melody.model.Customer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	int errorCode;
	String errorStr;
	String message;
	
	User user;
	Customer customer;
	Artist artist;
	List<Plan> plans;
	Plan plan;
	
	public void setError(int errCode, String errStr){
		this.errorCode = errCode;
		this.errorStr = errStr;
	}
}
