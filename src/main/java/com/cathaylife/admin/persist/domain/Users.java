package com.cathaylife.admin.persist.domain;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private List<User> users;
	
	public List<User> getUsers(){
		if ( users == null ){
			users = new ArrayList<>();
		}
		
		return users;
	}
	
}
