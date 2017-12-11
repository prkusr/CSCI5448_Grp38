package org.edu.melody.model;


public class UserFactory {
	
	public static User createUser(Class type){
		if(type.equals(Customer.class)){
			return new Customer();
		} else {
			return new Artist();
		}
	}
}
