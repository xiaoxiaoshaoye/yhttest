package com.yonyou.yht.sdk;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import yhttest.EnterpriseCenterUtil;
import yhttest.UserCenterUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// CYY 317b383a-f286-473f-82c3-45fcfd14228e
// mytest 9d8db50f-57cd-4d9c-b53f-55bde8b05258
public class EnterpriseCenterTest {

	ObjectMapper mapper;

	@Before
	public void init() {
		mapper = new ObjectMapper();
	}

	@Test
	public void listUserTest() {
		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		System.out.println(enterpriseId);
		String msg = EnterpriseCenter.listUser(enterpriseId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	public void searchTest() {
		String enterpriseName = "mytest";
		String msg = EnterpriseCenter.search(enterpriseName);
		System.out.println(msg);
//		JsonNode node = getJson(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		Assert.assertTrue(node.get("enterprises").size() > 0);
//		Assert.assertTrue(node.get("enterprises").get(0).get("enterName").asText().startsWith("CYY"));
	}
	
	@Test
	public void listEnterpriseTest() {
//		String userId = "6e3e49a2-aa13-4ed5-ad4b-557bd6d0e7a8";
		String userId = "906c8e75-2da4-473f-b14d-4ed8acaaab4f";
		String msg = EnterpriseCenter.listEnterprise(userId);
		System.out.println(msg);
	}
	
	@Test
	public void listUserPageTest() {
		String enterpriseId = "9d8db50f-57cd-4d9c-b53f-55bde8b05258";
		int pageSize = 1;
		int pageNumber = 3;
		String sortType = "";
		String msg = EnterpriseCenter.listUser(enterpriseId, pageNumber, pageSize, sortType);
		System.out.println(msg);
	}
	
	@Test
	public void searchUserTest() {
		String enterpriseName = "mytest";
//		String enterpriseId = "9d8db50f-57cd-4d9c-b53f-55bde8b05258";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		System.out.println(enterpriseId);
		String userName = "shicztest_003";
//		String userName = "@yonyou.com";
		String msg = EnterpriseCenter.searchUser(enterpriseId, userName);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	public void searchUserTest2() {
		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		System.out.println(enterpriseId);
		String userName = "shicztest_";
//		String userName = "137";
//		String userName = "@yonyou.com";
		String sortType = "auto";
		int pageSize = 10;
		int pageNumber = 1;
		String msg = EnterpriseCenter.searchUser(enterpriseId, userName, pageNumber, pageSize, sortType);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	public void addTest() {
		Map<String, String> params = new HashMap<String, String>();
		String enterName = "en_001";
		params.put("enterName", enterName);
		params.put("enterType", "6b2732ac-ad81-f1b9-e148-0b760f7d03f9");
		params.put("industry", "网站");
		params.put("ispartner", "true");
		params.put("enterAddress", "北京市/东城区");
		params.put("enterScale", "100");
		params.put("enterLegalrepre", "001");
		params.put("enterDetailaddress", "abc");
		params.put("superiorEnter", "");
		params.put("contactName", "test_002");
		params.put("contactEmail", "test_002@yonyou.com");
		params.put("contactMobile", "1370000002");
		params.put("registerTime", "2017-04-07 11:00:00");
		params.put("lastTime", "");
		params.put("auditStatus", "1");
		params.put("logoPath", "");
		String msg = EnterpriseCenter.addEnter(params);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	public void addUser2EnterTest() {
		String enterpriseName = "mytest";
		String enterpriseId = EnterpriseCenterUtil.getEnterpriseIdByName(enterpriseName);
		String userName = "shicztest_003";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = EnterpriseCenter.addUser2Enter(enterpriseId, userId);
		System.out.println(msg);
	}
}
