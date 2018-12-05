package com.yonyou.yht.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.yht.entity.UserInfo;
// xiezhengnan 6e3e49a2-aa13-4ed5-ad4b-557bd6d0e7a8
// test_001 3d5aae38-8be1-4bdd-866a-bf29a3540e8c
// test_002 8693be50-42a5-413b-a27e-59ded6dbd6a1

// caojj1@yonyou.com b5298ade-5c7e-4880-8521-f2542c2a6dab
// daixd@yonyou.com 906c8e75-2da4-473f-b14d-4ed8acaaab4f

public class UserCenterTest {

	ObjectMapper mapper;

	@Before
	public void init() {
		mapper = new ObjectMapper();
	}

	public UserInfo getUserByLoginName(String userName) {
		String msg = UserCenter.getUserByLoginName(userName);
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

	@Test
	public void t1() {
		// String userName = "13716968294";
		String userName = "shicz@ufida.com.cn";
		String msg = UserCenter.getUserByLoginName(userName);
		System.out.println(msg);
	}

	@Test
	public void isMobileTest() throws IOException {
		String tel = "13700000000";
		String msg = UserCenter.isMobile(tel);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("flag").asInt() == 1);

		tel = "1371234567";
		msg = UserCenter.isMobile(tel);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("flag").asInt() == 0);

		tel = "10012345678";
		msg = UserCenter.isMobile(tel);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("flag").asInt() == 0);
	}

	@Test
	public void isUserCodeExistTest() throws IOException {
		String userCode = "xiezhengnan";
		String msg = UserCenter.isUserCodeExist(userCode);
		JsonNode node = mapper.readTree(msg);
		System.out.println(msg);
		Assert.assertTrue(node.get("flag").asInt() == 1);

		userCode = "abc";
		msg = UserCenter.isUserCodeExist(userCode);
		node = mapper.readTree(msg);
		System.out.println(msg);
		Assert.assertTrue(node.get("flag").asInt() == 0);
	}

	@Test
	public void getUserByLoginNameTest() throws IOException {
		String userName = "13621333820";
		String userCode = "xiezhengnan";
		String msg = UserCenter.getUserByLoginName(userName);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		JsonNode user = node.get("user");
		Assert.assertNotNull(user.get("userCode"));
		Assert.assertTrue(user.get("userCode").asText().equals(userCode));

		userName = "xiezhengnan_1";
		msg = UserCenter.getUserByLoginName(userName);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("user").size() == 0);
	}

	@Test
	public void isUserExistTest() throws IOException {
		// String userCode = "xiezhengnan";
		String userCode = "xiezhengnan_1";
		String userMobile = "13621333820_1";
		String userEmail = "";
		String msg = UserCenter.isUserExist(userCode, userMobile, userEmail);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 7);

		userCode = "xiezhengnan_1";
		userMobile = "13621333820";
		userEmail = "";
		msg = UserCenter.isUserExist(userCode, userMobile, userEmail);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 5);

		userCode = "xiezhengnan";
		userMobile = "13621333820_1";
		userEmail = "";
		msg = UserCenter.isUserExist(userCode, userMobile, userEmail);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 4);

		userCode = "xiezhengnan";
		userMobile = "13621333820";
		userEmail = "";
		msg = UserCenter.isUserExist(userCode, userMobile, userEmail);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		userCode = "xiezhengnan";
		userMobile = "";
		userEmail = "";
		msg = UserCenter.isUserExist(userCode, userMobile, userEmail);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
	}

	/**
	 * 根据登录名和Sysid判断用户是否存在
	 * 
	 * @throws IOException
	 */
	@Test
	public void isUserExistTest2() throws IOException {
		String userName = "shicztest_008";
		String sysid = "00000000";
		String msg = UserCenter.isUserExist(userName, sysid);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("flag").asInt() == 1);

		userName = "shicztest_007";
		sysid = "00000000";
		msg = UserCenter.isUserExist(userName, sysid);
		System.out.println(msg);
		node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("flag").asInt() == 0);
	}

	@Test
	public void addUserTest() {
		Map<String, String> params = new HashMap<String, String>();
		// String userCode = "test_002";
		// params.put("tenantId", "00000000");
		// params.put("userCode", userCode);
		// params.put("userName", userCode);
		// params.put("userMobile", "13700000002");
		String index = String.format("%03d", 10);
		System.out.println(index);
		String userCode = "shicztest_" + index;
		params.put("sysId", "00000000");
		params.put("userCode", userCode);
		params.put("userName", userCode);
		params.put("userMobile", "13700000" + index);
		params.put("userEmail", "shicztest_" + index + "@yonyou.com");
		params.put("userPassword", userCode);

		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("user").get("userCode").asText().equals(userCode));
	}

	@Test
	public void addUserTest2() {
		Map<String, String> params = new HashMap<String, String>();
		int index = 6;
		String userCode = "sdk中文测试_shicztest_00" + index;
		params.put("tenantId", "00000000");
		params.put("userCode", userCode);
		params.put("userName", userCode);
		params.put("userMobile", "1370000100" + index);
		params.put("userEmail", "shicztest_00" + index + "@yonyou.com");

		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("user").get("userCode").asText().equals(userCode));
	}

	@Test
	public void profileTest() {
		String userName = "test_001";
		UserInfo user = getUserByLoginName(userName);
		String userId = user.getUserId();
		String sysId = "test";
		String msg = UserCenter.getProfile(userId, sysId);
		System.out.println(msg);
		Map<String, Object> node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("0"));

		String value = "123";
		String newProfile = "{a:" + value + "}";
		String sysUserId = "";
		msg = UserCenter.setProfile(userId, sysId, sysUserId, newProfile);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));

		msg = UserCenter.getProfile(userId, sysId);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(Utils.getMap(mapper, node.get("msg").toString()).get("a").toString().equals(value));

		msg = UserCenter.removeProfile(userId, sysId);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));

		msg = UserCenter.getProfile(userId, sysId);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("0"));
	}

	@Test
	public void tagTest() {
		String userName = "test_001";
		UserInfo user = getUserByLoginName(userName);
		String userId = user.getUserId();
		String tag = "tag_01";
		String msg = UserCenter.isTagExist(userId, tag);
		System.out.println(msg);
		Map<String, Object> node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("0"));

		msg = UserCenter.setTag(userId, tag);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("1"));

		msg = UserCenter.isTagExist(userId, tag);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("1"));

		String newTag = "tag_02";
		msg = UserCenter.modifyTag(userId, tag, newTag);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("1"));

		msg = UserCenter.isTagExist(userId, tag);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("0"));

		msg = UserCenter.isTagExist(userId, newTag);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("1"));

		msg = UserCenter.removeTag(userId, newTag);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("1"));

		msg = UserCenter.isTagExist(userId, newTag);
		System.out.println(msg);
		node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertTrue(node.get("flag").toString().equals("0"));

	}
	//
	// @Test
	// public void setTagTest() {
	// String userName = "test_001";
	// UserInfo user = getUserByLoginName(userName);
	// String userId = user.getUserId();
	// String tag = "tag_01";
	// String msg = UserCenter.setTag(userId, tag);
	// System.out.println(msg);
	// Assert.assertNotNull(msg);
	// Map<String, Object> node = Utils.getMap(mapper, msg);
	// Assert.assertTrue(node.get("status").toString().equals("1"));
	// }
	//
	// @Test
	// public void modifyTagTest() {
	// String userName = "test_001";
	// UserInfo user = getUserByLoginName(userName);
	// String userId = user.getUserId();
	// String oldTag = "tag_01";
	// String newTag = "tag_02";
	// String msg = UserCenter.modifyTag(userId, oldTag, newTag);
	// System.out.println(msg);
	// Assert.assertNotNull(msg);
	// Map<String, Object> node = Utils.getMap(mapper, msg);
	// Assert.assertTrue(node.get("status").toString().equals("1"));
	// }
	//
	// @Test
	// public void removeTagTest() {
	// String userName = "test_001";
	// UserInfo user = getUserByLoginName(userName);
	// String userId = user.getUserId();
	// String tag = "tag_02";
	// String msg = UserCenter.removeTag(userId, tag);
	// System.out.println(msg);
	// Assert.assertNotNull(msg);
	// Map<String, Object> node = Utils.getMap(mapper, msg);
	// Assert.assertTrue(node.get("status").toString().equals("1"));
	// }

	@Test
	public void getUserByCodeTest() {
		String userCode = "test_006";
		String msg = UserCenter.getUserByCode(userCode);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	public void getUserByIdTest() {
		// String userId = "906c8e75-2da4-473f-b14d-4ed8acaaab4f";
		String userId = "06db845c-d260-4044-b3d1-82784a74b384";
		String msg = UserCenter.getUserById(userId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	public void searchUserByNameTest() {
		String name = "shicztest_00";
		String msg = UserCenter.searchUserByName(name);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertNotNull(node.get("users"));
	}

	@Test
	public void searchUserByNamePageTest() {
		String name = "yhttest3";
		String pageSize = "20";
		String pageNumber = "1";
		String sortType = "name";
		String msg = UserCenter.searchUserByName(name, pageSize, pageNumber, sortType);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
		Assert.assertNotNull(node.get("users"));
	}

	@Test
	public void getUserByPksTest() {
		String[] pks = new String[] { "3d5aae38-8be1-4bdd-866a-bf29a3540e8c", "8693be50-42a5-413b-a27e-59ded6dbd6a1" };
		String msg = UserCenter.getUserByPks(pks);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
	}

	@Test
	public void getUserByCodesTest() {
		String[] codes = new String[] { "test_001", "test_002" };
		String msg = UserCenter.getUserByCodes(codes);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		Map<String, Object> node = Utils.getMap(mapper, msg);
		Assert.assertTrue(node.get("status").toString().equals("1"));
	}

	@Test
	public void addUsersTest() {
		String systemCode = "test";
		Map<String, String> user1 = new HashMap<String, String>();
		String userCode1 = "test_006";
		user1.put("tenantId", "00000000");
		user1.put("userCode", userCode1);
		user1.put("userName", userCode1);
		user1.put("userMobile", "13700000006");
		Map<String, String> user2 = new HashMap<String, String>();
		String userCode2 = "test_004";
		user2.put("tenantId", "00000000");
		user2.put("userCode", userCode2);
		user2.put("userName", userCode2);
		user2.put("userMobile", "13700000004");
		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> users = new ArrayList<Object>();
		users.add(user1);
		users.add(user2);
		params.put("users", users);
		String jsonStr = Utils.getJsonStr(mapper, params);
		System.out.println(jsonStr);
		String msg = UserCenter.addUsers(systemCode, jsonStr);
		System.out.println(msg);

	}

	@Test
	public void searchUserTest() {
		String userName1 = "test_001";
		String userName2 = "test_002";
		String userName3 = "test_003";
		String id1 = getUserByLoginName(userName1).getUserId();
		String id2 = getUserByLoginName(userName2).getUserId();
		String id3 = getUserByLoginName(userName3).getUserId();

		String[] pks = new String[] { id1, id2 };
		String msg = UserCenter.searchUser(pks, userName1);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 1);
		Assert.assertTrue(node.get("users").get("content").get(0).get("userId").asText().equals(id1));

		msg = UserCenter.searchUser(pks, userName2);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 1);
		Assert.assertTrue(node.get("users").get("content").get(0).get("userId").asText().equals(id2));

		Assert.assertNotNull(id3);

		msg = UserCenter.searchUser(pks, userName3);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 0);
	}

	@Test
	public void searchUserPageTest() {
		String userName1 = "test_001";
		String userName2 = "test_002";
		String userName3 = "test_003";
		String id1 = getUserByLoginName(userName1).getUserId();
		String id2 = getUserByLoginName(userName2).getUserId();
		String id3 = getUserByLoginName(userName3).getUserId();

		String[] pks = new String[] { id1, id2, id3 };
		String userName = "test";
		String ps = "2";
		String pn = "1";
		// String sortType = "auto";
		String sortType = "name";
		String isIn = "true";
		String sysid = "test";
		String secretKey = "";
		String msg = UserCenter.searchUser(pks, userName, ps, pn, sortType, isIn, sysid, secretKey);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 3);
		Assert.assertTrue(node.get("users").get("numberOfElements").asInt() == 2);

		pn = "2";
		msg = UserCenter.searchUser(pks, userName, ps, pn, sortType, isIn, sysid, secretKey);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 3);
		Assert.assertTrue(node.get("users").get("numberOfElements").asInt() == 1);
	}

	@Test
	public void searchUserNotInTest() {
		String userName1 = "shicztest_001";
		String userName2 = "shicztest_002";
		String id1 = getUserByLoginName(userName1).getUserId();
		String id2 = getUserByLoginName(userName2).getUserId();

		String[] pks = new String[] { id1, id2 };
		String msg = UserCenter.searchUser(pks, userName1);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 1);
		Assert.assertTrue(node.get("users").get("content").get(0).get("userId").asText().equals(id1));

		msg = UserCenter.searchUserNotIn(pks, userName1);
		System.out.println(msg);
		Assert.assertNotNull(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 0);

	}

	@Test
	public void sendcodeTest() {
		// String contact = "13716968294";
		// String type = "mobile";
		String contact = "shicz@yonyou.com";
		String type = "email";
		String key = "1495615240000";
		String code = "z38r";
		String msg = UserCenter.sendcode(contact, type, key, code);
		System.out.println(msg);

	}

	@Test
	public void validateCode() {
		// String contact = "13716968294";
		// String type = "mobile";
		String contact = "shicz@yonyou.com";
		String type = "email";
		String code = "174090";
		String msg = UserCenter.validateCode(contact, type, code);
		System.out.println(msg);
	}

	@Test
	public void sendMsgByEmailTest() {
		String userName = "shicz@yonyou.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String massage = "massage test";
		String title = "title test";
		String msg = UserCenter.sendMsgByEmail(userId, massage, title);
		// String msg = UserCenter.sendMsgByEmail(userId, massage);
		System.out.println(msg);
	}

	@Test
	public void backendLogoutTest() {
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.backendLogout(userId);
		System.out.println(msg);
	}

	@Test
	public void updateUserMultiPropertiesTest() {
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("userName", "施成章");
		properties.put("sex", "0");
		properties.put("address", "1-11-2");
		properties.put("birthday", "1990年1月3日");
		properties.put("industry", "3");
		properties.put("position", "4");
		String msg = UserCenter.updateUserMultiProperties(userId, properties);
		System.out.println(msg);
	}

	@Test
	public void getUserByContactTest() {
		// String email = "shicz@yonyou.com";
		String email = "shicz@ufida.com.cn";
		String mobile = "13716968294";
		String contact = email;
		String type = "mobile";
		// String type = "email";
		if (type.equals("mobile")) {
			contact = mobile;
		}
		String msg = UserCenter.getUserByContact(contact, type);
		System.out.println(msg);
	}

	@Test
	public void modifyContactTest() {
		// String userName = "shicz@yonyou.com";
		String userName = "shicz@ufida.com.cn";
		// String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		// String contact = "shicz@ufida.com.cn";
		String contact = "13716968294";
		String sid = "05130b16f94b5263263d580a56d706c6";
		String msg = UserCenter.modifyContact(userId, contact, sid);
		System.out.println(msg);
	}

	@Test
	public void generateOauthTokenTest() {
		// String userName = "shicz@yonyou.com";
		// String userName = "shicz@ufida.com.cn";
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String clientId = "2";
		String clientSecret = "2bf34a073bdbcf0d026d9a54279ead7d";
		String scope = "7";
		String msg = UserCenter.generateOauthToken(userId, clientId, clientSecret, scope);
		System.out.println(msg);
	}

	@Test
	public void checkOauthTokenTest() {
		String accessToken = "e409ec5a-bc9b-422f-9097-dc93682a8bc9";
		String msg = UserCenter.checkOauthToken(accessToken);
		System.out.println(msg);
	}

	@Test
	public void getUserByTokenTest() {
		String accessToken = "e409ec5a-bc9b-422f-9097-dc93682a8bc9";
		String msg = UserCenter.getUserByToken(accessToken);
		System.out.println(msg);
	}

	@Test
	public void verifyHSYUserTest() {
		String tenantCode = "";
		String name = "shicztest_009";
		String psw = "shicztest_009";
		String msg = UserCenter.verifyHSYUser(tenantCode, name, psw);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		psw = "shicztest_009_err";
		msg = UserCenter.verifyHSYUser(tenantCode, name, psw);
		System.out.println(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
	}

	/**
	 * 根据租户ID，用户名，密码获取accesstoken
	 */
	@Test
	public void createAccessTokenTest() {
		String tenantId = "";
		String userName = "shicztest_009";
		String password = "shicztest_009";
		String clientId = "2";

		String msg = UserCenter.createAccessToken(tenantId, userName, password, clientId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("data").has("access_token"));

		password = "shicztest_009_err";

		msg = UserCenter.createAccessToken(tenantId, userName, password, clientId);
		System.out.println(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("code").asInt() == 400);
	}

	@Test
	public void createAccessTokenByValidateCodeTest() {
		String sysId = "";
		String userName = "13716968294";
		String clientId = "2";
		String validateCode = "701141";
		String msg = UserCenter.createAccessTokenByValidateCode(sysId, userName, validateCode, clientId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("data").has("access_token"));
		System.out.println(node.get("data").get("access_token").asText());
	}

	@Test
	public void isUserActivateTest() {
		String loginname = "13716968294";
		String msg = UserCenter.isUserActivate(loginname);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("active"));

		loginname = "13716968295";
		msg = UserCenter.isUserActivate(loginname);
		System.out.println(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("notexits"));

		loginname = "";
		msg = UserCenter.isUserActivate(loginname);
		System.out.println(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("empty"));

		loginname = "shicz@ufida.com.cn";
		msg = UserCenter.isUserActivate(loginname);
		System.out.println(msg);
		node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("code").asText().equals("0"));
	}

	@Test
	public void activateUserTest() {
		String userContact = "shicz@ufida.com.cn";
		String userpwd = "12345678";
		userpwd = DigestUtils.sha1Hex(userpwd);
		String code = "827438";
		String msg = UserCenter.activateUser(userContact, userpwd, code);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	public void activateUserTest2() {
		String olduserContact = "13716968294";
		String userContact = "shicz@ufsoft.com.cn";
		String userpwd = "12345678";
		userpwd = DigestUtils.sha1Hex(userpwd);
		String code = "641125";
		String userId = UserCenterUtil.getUserIdByLoginName(olduserContact);
		boolean changecontactFlag = true;
		String msg = UserCenter.activateUser(userContact, userpwd, code, userId, changecontactFlag);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	public void getConflictUserByIdTest() {
		// String userName = "13716968294";
		// String userName = "shicz@ufida.com.cn";
		// String userName = "shicz";
		String userName = "shicz@yonyou.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.getConflictUserById(userId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.has("users"));
		System.out.println(node.get("users").size() == 2);
	}

	@Test
	public void getUserLoginLogTest() {
		String userName = "13716968294";
		// String userName = "shicz@ufida.com.cn";
		// String userName = "shicz";
		// String userName = "shicz@yonyou.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		System.out.println(userId);
		String msg = UserCenter.getUserLoginLog(userId + "00000");
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

}
