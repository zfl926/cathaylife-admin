package com.cathaylife.admin.service.weixin.management;

import java.util.List;

import com.cathaylife.admin.persist.domain.Groups;
import com.cathaylife.admin.persist.domain.Users;

/**
 * add/update/delete group for users
 * @author Administrator
 *
 */
public interface IUserManagement {
	/**
	 * @param groupName
	 * @return
	 */
	public boolean createGroup(String groupName);
	/**
	 * @param groupId
	 * @param newName
	 * @return
	 */
	public boolean renameGroup(String groupId, String newName);
	/**
	 * @param groupId
	 * @return
	 */
	public boolean deleteGroup(String groupId);
	/**
	 * @return
	 */
	public Groups getGroups();
	/**
	 * @param openIds
	 * @return
	 */
	public Users getUsers(List<String> openIds);
}
