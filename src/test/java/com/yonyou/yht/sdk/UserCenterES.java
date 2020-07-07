package com.yonyou.yht.sdk;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.portable.InputStream;

//import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
//import com.yonyou.cloud.auth.sdk.client.utils.codec.SignUtils;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.xb.xsdschema.OpenAttrs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Table.Cell;
import com.yonyou.enterprise.sdk.UserCenterUtil;
//import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.context.log.InvocationInfoProxy;
import com.yonyou.yht.entity.SimpleUserInfo;
import com.yonyou.yht.sdk.UserCenter;
//import com.yonyou.yht.sdkutils.sign.SignUtils;


//import com.yonyou.yht.sdkutils.PropertyUtil;
public class UserCenterES {


	static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	/*
	 * 根据用户id数组和用户名称查找用户
	 */
	public void searchUserTest() throws JsonProcessingException, IOException {

		String[][] users = { { "fc8a5064-8338-4f9c-83a0-2f0fd5588224", "HRC15210142172" },
				{ "36926e6a-fc81-4070-bbdd-5a299be7ef74", "HRC17802888888" },
				{ "04883d02-6eb6-4f52-a9ad-8a0a913501fe", "HRC17803888888" },
				 };
		String[] pks = new String[users.length];
		for (int i = 0; i < users.length; i++) {
			pks[i] = users[i][0];
		}
		String userName = "8888888";
		String msg = UserCenter.searchUser(pks, userName);
		System.out.println(msg);

		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 16);

	}
	@Test
	/*
	 * 据登录名模糊查询用户列表（支持分页） 正常情况的测试
	 */
	public void searchUserByNameTest() throws JsonProcessingException, IOException {

		String msg = UserCenter.searchUserByName("1521014", "3", "1", "mail");
		System.out.println("据登录名模糊查询用户列表（支持分页） "+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据字符串模糊查询用户列表（前缀匹配、支持分页） 正常情况的测试
	 */
	public void searchUserByFilterTest() throws JsonProcessingException, IOException {

		// 默认是第一页，一页20条，因为数据多，肯定不是一页，肯定是满页20条
		String msg = UserCenter.searchUserByFilter("杜娟");
		System.out.println("根据字符串模糊查询用户列表（前缀匹配、支持分页）"+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("first").asBoolean());
		Assert.assertTrue(node.get("users").get("firstPage").asBoolean());
		Assert.assertFalse(node.get("users").get("lastPage").asBoolean());
		Assert.assertTrue(node.get("users").get("numberOfElements").asInt() == 20);
		Assert.assertTrue(node.get("users").get("size").asInt() == 20);

		// 每页显示5条数据，显示第二页，按userName排序
//		String msg1 = UserCenter.searchUserByFilter("stt", 5, 2, "name");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree("根据字符串模糊查询用户列表（前缀匹配、支持分页）"+msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//		Assert.assertFalse(node1.get("users").get("first").asBoolean());
//		Assert.assertFalse(node1.get("users").get("firstPage").asBoolean());
//		Assert.assertFalse(node1.get("users").get("lastPage").asBoolean());
//		Assert.assertTrue(node1.get("users").get("numberOfElements").asInt() == 5);
//		Assert.assertTrue(node1.get("users").get("size").asInt() == 5);

		// 每页显示5条数据，显示第二页，默认排序，即按userCode排序
//		String msg2 = UserCenter.searchUserByFilter("stt", 5, 2, "auto");
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
//		Assert.assertFalse(node2.get("users").get("first").asBoolean());
//		Assert.assertFalse(node2.get("users").get("firstPage").asBoolean());
//		Assert.assertFalse(node2.get("users").get("lastPage").asBoolean());
//		Assert.assertTrue(node2.get("users").get("numberOfElements").asInt() == 5);
//		Assert.assertTrue(node2.get("users").get("size").asInt() == 5);
//
//		// 每页显示5条数据，显示第二页，按userCode排序
//		String msg3 = UserCenter.searchUserByFilter("stt", 5, 2, "userCode");
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 1);
//		Assert.assertFalse(node3.get("users").get("first").asBoolean());
//		Assert.assertFalse(node3.get("users").get("firstPage").asBoolean());
//		Assert.assertFalse(node3.get("users").get("lastPage").asBoolean());
//		Assert.assertTrue(node3.get("users").get("numberOfElements").asInt() == 5);
//		Assert.assertTrue(node3.get("users").get("size").asInt() == 5);
	}
	
	/***
	 * 用户批量查询
	 * searchUserByContact;
	 * String mobileList
	 * String emailList
	 * 
	 */
	@Test
	public void  searchUserByContactTest() throws JsonProcessingException, IOException {
		//17501888888  84c228ab-09cd-41d4-ab98-8ab6cc66a038
		//手机号和邮箱不是同一个账号显示两条数据
		String mobileList = "17501888888,17613888888,17614888888";//"18121221242,13612421243,18330032819"
		String emailList = "test19031202@test1988.com";
		//		String msg = UserCenter.searchUserByContact(null,null);
		String msg = UserCenter.searchUserByContact(emailList,null);
		System.out.println(msg);
		//		JsonNode node = mapper.readTree(msg);
		//		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
		//		Assert.assertTrue(node.get("status").asInt() == 1);
		//		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}


}


