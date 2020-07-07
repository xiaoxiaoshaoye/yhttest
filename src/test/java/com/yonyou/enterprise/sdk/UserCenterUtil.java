package com.yonyou.enterprise.sdk;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.entity.UserInfo;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdk.Utils;

public class UserCenterUtil {

	static ObjectMapper mapper = new ObjectMapper();

	public static UserInfo getUserByLoginName(String userName) {
		String msg = UserCenter.getUserByLoginName(userName);
		System.out.println(msg);
		Map<String, Object> m = Utils.getMap(mapper, msg);
		Assert.assertTrue(m.get("status").toString().equals("1"));
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) m.get("user");
		if (user.get("userId") == null) {
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId((String) user.get("userId"));
		userInfo.setUserCode((String) user.get("userCode"));
		userInfo.setUserMobile((String) user.get("userMobile"));
		userInfo.setUserName((String) user.get("userName"));
		userInfo.setUserAvator((String) user.get("userAvator"));
		userInfo.setStatus((Integer) user.get("status"));
		userInfo.setRegisterDate((String) user.get("registerDate"));
		return userInfo;
	}

	public static String getUserIdByLoginName(String userName) {
		UserInfo user = getUserByLoginName(userName);
		Assert.assertNotNull(user);
		return user.getUserId();
	}

	public static UserInfo addUser(String userCode, String userMobile) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tenantId", "00000000");
		params.put("userCode", userCode);
		params.put("userName", userCode);
		params.put("userMobile", userMobile);
		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("user").get("userCode").equals(userCode));
		Map<String, Object> m = Utils.getMap(mapper, msg);
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) m.get("user");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId((String) user.get("userId"));
		userInfo.setUserCode((String) user.get("userCode"));
		userInfo.setUserMobile((String) user.get("userMobile"));
		userInfo.setUserName((String) user.get("userName"));
		userInfo.setUserAvator((String) user.get("userAvator"));
		userInfo.setStatus((Integer) user.get("status"));
		userInfo.setRegisterDate((String) user.get("registerDate"));
		return userInfo;
	}
}
