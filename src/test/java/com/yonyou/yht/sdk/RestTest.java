package com.yonyou.yht.sdk;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.utils.CasClientPropertyUtil;
import com.yonyou.yht.utils.sign.SignUtils;

import net.sf.json.JSONObject;
import yhttest.UserCenterUtil;

public class RestTest {
	String baseurl;
	String casurl;
	ObjectMapper mapper;
	@Before
	public void init() {
		baseurl = CasClientPropertyUtil.getPropertyByKey("yht.user.base.url");
		casurl = CasClientPropertyUtil.getPropertyByKey("cas.url");
		mapper = new ObjectMapper();
	}

	@Test
	public void sendcodeTest() {
		String url = baseurl + "/user/sendcode";
//		String userName = "13716968294";
		// String userName = "shicz@yonyou.com";
		String userName = "shicz@ufida.com.cn";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> params = new HashMap<String, String>();
//		String type = "mobile";
		String type = "email";
		params.put("type", type);
		params.put("userId", userId);
		params.put("sysid", "yht");

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		System.out.println(msg);
	}

	@Test
	public void validatecodeTest() {
		String url = baseurl + "/user/validatecode";
//		String userName = "13716968294";
//		String userName = "shicz@yonyou.com";
		String userName = "shicz@ufida.com.cn";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> params = new HashMap<String, String>();
//		String type = "mobile";
		String type = "email";
		params.put("type", type);
		params.put("userId", userId);
		params.put("code", "551255");

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		System.out.println(msg);
	}

	@Test
	public void modifypasswordTest() {
		String url = baseurl + "/user/modifypassword";
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("shaPassword", "");
		params.put("md5Password", "");
		params.put("shaNewPassword", "");
		params.put("md5NewPassword", "");
		params.put("reset", "true");
		params.put("sid", "");

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		System.out.println(msg);
	}

	@Test
	public void bindMailTest() {
		String url = baseurl + "/user/bindMail";
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("email", "shicz@ufida.com");
		params.put("sysid", "");

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		System.out.println(msg);
	}

	@Test
	public void bindMobileTest() {
		String url = baseurl + "/user/bindMobile";
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("mobile", "");

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		System.out.println(msg);
	}

	@Test
	public void logoutBackendTest() {
		String url = casurl + "/v1/logout/backend";
		String userName = "13716968294";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("sysId", "yht");

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		System.out.println(msg);
	}
	
	@Test
	public void loginlogTest() {
		String url = baseurl + "/userlogin/loginlog";
		String userName = "13716968294";
//		String userName = "shicz@ufida.com.cn";
//		String userName = "shicz@yonyou.com";
//		String userName = "shicz";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		System.out.println(userId);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);

		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(params);
		String jsonStr = jsonObj.toString();
		System.out.println(jsonStr);
		String msg = SignUtils.signAndPost(url, jsonStr);
		Assert.assertNotNull(msg);
		System.out.println(msg);
		
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.has("loginlog"));
	}
}
