package com.cathaylife.admin.persist.domain;

import java.util.ArrayList;
import java.util.List;

public class Groups {
	private List<Group> groups;
	
	public List<Group> getGroups(){
		if ( groups == null ){
			groups = new ArrayList<>();
		}
		
		return groups;
	}
}
