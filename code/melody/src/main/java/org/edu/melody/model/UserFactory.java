package org.edu.melody.model;

public class UserFactory {
	
	public User createUser(int type){
		if(type ==1){
			return new Customer();
		} else {
			return new Artist();
		}
	}
}
