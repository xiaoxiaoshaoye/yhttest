package com.yonyou.yht.sdk;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.EnterpriseCenterUtil;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.enterprise.sdk.UserGroupCenterUtil;
import com.yonyou.yht.entity.UserGroup;

// group1
// group2
// group3 ecbca9b6-2017-46a2-bf83-3e6c8ac45a0e eid_003
// gropp5 b1ff1a68-6ea1-4b80-84f1-76acf59582de 9d8db50f-57cd-4d9c-b53f-55bde8b05258
public class UserGroupCenterTest {
	ObjectMapper mapper;

	@Before
	public void init() {
		mapper = new ObjectMapper();
	}

	@Test
	public void addTest() {
		UserGroup userGroup = new UserGroup();

		String groupName = "shiczgroup_001";
		userGroup.setGroupName(groupName);
		
		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		userGroup.setEnterpriseId(enterpriseId);
		
		String msg = UserGroupCenter.add(userGroup);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	public void addUser2GroupTest() {
//		// group5
//		String groupId = "b1ff1a68-6ea1-4b80-84f1-76acf59582de";
//		// test_002
//		String userId = "8693be50-42a5-413b-a27e-59ded6dbd6a1";
		
		String userName = "test_001";
//		String userMobile = "13700000001";
//		UserCenterUtil.addUser(userName, userMobile);
		String userId = UserCenterUtil.getUserIdByLoginName(userName);

		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
//		String enterpriseId = null;
		
		String groupName = "group_001";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		System.out.println(groupId);
		String msg = UserGroupCenter.addUser2Group(groupId, userId);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
	}

	@Test
	public void addUser2GroupTest2() {

		String userName = "shicztest_002";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);

		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
//		String enterpriseId = null;
		
		String groupName = "shiczgroup_001";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		System.out.println(groupId);
		String msg = UserGroupCenter.addUser2Group(groupId, userId);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
	}
	
	@Test
	public void listUserGroupTest() {
//		String enterpriseId = "eid_003";
		// cyy
//		String enterpriseId = "317b383a-f286-473f-82c3-45fcfd14228e";
		// mytest
//		String enterpriseId = "9d8db50f-57cd-4d9c-b53f-55bde8b05258";
		String enterpriseId = null;
//		String enterpriseId = "";
		String msg = UserGroupCenter.listUserGroup(enterpriseId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
//		Assert.assertTrue(node.get("groupList"));
		
	}
	
	@Test
	public void listGroupByUserIdTest() {
//		String userName = "test_001";
		String userName = "shicztest_001";
//		String userMobile = "13700000001";
//		UserCenterUtil.addUser(userName, userMobile);
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		System.out.println(userId);
		String msg = UserGroupCenter.listGroupByUserId(userId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	public void updateTest() {
		UserGroup userGroup = new UserGroup();
		String groupName = "group4";
		String enterpriseId = "eid_004";
		userGroup.setGroupName(groupName);
		userGroup.setEnterpriseId(enterpriseId);
		String msg = UserGroupCenter.add(userGroup);
		System.out.println(msg);
		Map<String, Object> m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
		
		userGroup = UserGroupCenterUtil.getGroupByName(enterpriseId, groupName);
		String groupName2 = "group5";
		userGroup.setGroupName(groupName2);
		msg = UserGroupCenter.update(userGroup);
		System.out.println(msg);
		m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
		
		String groupId = userGroup.getGroupId();
		msg = UserGroupCenter.delete(groupId);
		System.out.println(msg);
		m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
		
		userGroup = UserGroupCenterUtil.getGroupByName(enterpriseId, groupName);
		Assert.assertNull(userGroup);
	}
	
	@Test
	public void deleteTest() {
		String groupName = "group4";
		String enterpriseId = "eid_004";
		UserGroup userGroup = null;
		
//		String msg = null;
//		Map<String, Object> m = null;
		
		userGroup = UserGroupCenterUtil.getGroupByName(enterpriseId, groupName);
		Assert.assertNull(userGroup);
		
		userGroup = new UserGroup();
		userGroup.setGroupName(groupName);
		userGroup.setEnterpriseId(enterpriseId);
		String msg = UserGroupCenter.add(userGroup);
		System.out.println(msg);
		Map<String, Object> m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
		
		userGroup = UserGroupCenterUtil.getGroupByName(enterpriseId, groupName);
		Assert.assertNotNull(userGroup);
		
		String groupId = userGroup.getGroupId();
		msg = UserGroupCenter.delete(groupId);
		System.out.println(msg);
		m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
		
		userGroup = UserGroupCenterUtil.getGroupByName(enterpriseId, groupName);
		Assert.assertNull(userGroup);
	}
	
	@Test
	public void searchUserTest() {
		String userName = "test";
		String enterpriseId = null;
		String groupName = "group1";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		String msg = UserGroupCenter.searchUser(groupId, userName);
		System.out.println(msg);
		// TODO userName 有_
	}
	
	@Test
	public void searchUserPageTest() {
		String userName = "test";
		String enterpriseId = null;
		String groupName = "group1";
		String pageNumber = "1";
		String pageSize = "3";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		String msg = UserGroupCenter.searchUser(groupId, userName, pageNumber, pageSize);
		System.out.println(msg);
		
	}
	
	@Test
	public void removeUserFromGroupTest() {
		String userName = "test_002";
//		String userMobile = "13700000001";
//		UserCenterUtil.addUser(userName, userMobile);
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String enterpriseId = null;
		String groupName = "group1";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		System.out.println(groupId);
		String msg = UserGroupCenter.removeUserFromGroup(groupId, userId);
		System.out.println(msg);
	}
	
	@Test
	public void removeUserFromGroupTest2() {
		String userName = "shicztest_002";
//		String userMobile = "13700000001";
//		UserCenterUtil.addUser(userName, userMobile);
		String userId = UserCenterUtil.getUserIdByLoginName(userName);

		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		
		String groupName = "shiczgroup_001";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		System.out.println(groupId);
		String msg = UserGroupCenter.removeUserFromGroup(groupId, userId);
		System.out.println(msg);
	}
	
	@Test
	public void listUserTest() {
		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		String groupName = "shiczgroup_001";
		String groupId = UserGroupCenterUtil.getGroupIdByName(enterpriseId, groupName);
		String msg = UserGroupCenter.listUser(groupId);
		System.out.println(msg);
	}
}
