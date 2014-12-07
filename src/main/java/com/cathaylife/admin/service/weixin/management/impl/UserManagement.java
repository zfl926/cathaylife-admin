package com.cathaylife.admin.service.weixin.management.impl;

import java.util.List;

import com.cathaylife.admin.persist.domain.Groups;
import com.cathaylife.admin.persist.domain.Users;
import com.cathaylife.admin.service.weixin.management.IUserManagement;

public class UserManagement implements IUserManagement {

	@Override
	public boolean createGroup(String groupName) {
		return false;
	}

	@Override
	public boolean renameGroup(String groupId, String newName) {
		return false;
	}

	@Override
	public boolean deleteGroup(String groupId) {
		return false;
	}

	@Override
	public Groups getGroups() {
		return null;
	}

	@Override
	public Users getUsers(List<String> openIds) {
		return null;
	}

}
