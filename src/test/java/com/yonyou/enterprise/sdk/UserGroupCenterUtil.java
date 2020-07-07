package com.yonyou.enterprise.sdk;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.entity.UserGroup;
import com.yonyou.yht.sdk.UserGroupCenter;
import com.yonyou.yht.sdk.Utils;

public class UserGroupCenterUtil {

	static ObjectMapper mapper = new ObjectMapper();

	// public static UserGroup getGroupByName(String enterpriseId, String
	// groupName) {
	// String msg = UserGroupCenter.listUserGroup(enterpriseId);
	// System.out.println(msg);
	// Assert.assertNotNull(msg);
	// Map<String, Object> m = Utils.getMap(mapper, msg);
	// Assert.assertTrue("1".equals(m.get("status").toString()));
	//// List<UserGroup> groups = Utils.getList(mapper, m.get("groupList")
	//// .toString(), UserGroup.class);
	// List<UserGroup> groups = Utils.getObject(mapper, m.get("groupList")
	// .toString(), new TypeReference<List<UserGroup>>() {
	// });
	// for (UserGroup group : groups) {
	// if (group.getGroupName().equals(groupName)) {
	// return group;
	// }
	// }
	// return null;
	// }

	public static UserGroup getGroupByName(String enterpriseId, String groupName) {
		String msg = UserGroupCenter.listUserGroup(enterpriseId);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> m = Utils.getMap(mapper, msg);
		Assert.assertTrue("1".equals(m.get("status").toString()));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> groups = (List<Map<String, Object>>) m.get("groupList");
		for (Map<String, Object> group : groups) {
			if (group.get("groupName").equals(groupName)) {
				UserGroup ug = new UserGroup();
				ug.setGroupId((String) group.get("groupId"));
				ug.setSysId((String) group.get("sysId"));
				ug.setEnterpriseId((String) group.get("enterpriseId"));
				ug.setGroupName(groupName);
				ug.setPara1((String) group.get("para1"));
				ug.setPara2((String) group.get("para2"));
				ug.setPara3((String) group.get("para3"));
				ug.setPara4((String) group.get("para4"));
				ug.setPara5((String) group.get("para5"));
				ug.setPara6((Integer) group.get("para6"));
				ug.setPara7((Integer) group.get("para7"));
				ug.setPara8((Long) group.get("para8"));
				ug.setPara9((Long) group.get("para9"));
				ug.setRegisterDate((String) group.get("registerDate"));
				return ug;
			}
		}
		return null;
	}

	public static String getGroupIdByName(String enterpriseId, String groupName) {
		UserGroup group = getGroupByName(enterpriseId, groupName);
		Assert.assertNotNull(group);
		return group.getGroupId();
	}

	public static UserGroup add(String groupName, String enterpriseId) {
		UserGroup userGroup = new UserGroup();

		userGroup.setGroupName(groupName);
		if (enterpriseId != null) {
			userGroup.setEnterpriseId(enterpriseId);
		}
		String msg = UserGroupCenter.add(userGroup);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		return Utils.getObject(mapper, node.get("usergroup").asText(), UserGroup.class);
	}
}
