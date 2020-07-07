package com.yonyou.yht.sdk;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

//import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import java.io.File;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.yht.sdk.UserCenter;
//import com.yonyou.yht.sdkutils.sign;

//import com.yonyou.yht.sdkutils.PropertyUtil;
public class UserCenterTestDJLine {
	
	
	ObjectMapper mapper = new ObjectMapper();
	@Test
	/*
	 * 企业员工信息新增员工标签SDK接口--正常测试流程
	 * public static String setUserTag(String userId, String tag,String key,String value)

                       参数： userId 用户ID
            tag "ENTERPRISE_STAFF" 标签常量类型(固定值)
            key "enterpriseId" 标识企业ID
            value JSON对象 具体信息包含{"enterpriseId":"05c08e78-de5b-4071-9bb4-2c2621bc24d6","enterpriseName":"用友集团","staffMobile":"13621031586","staffEmail":"fengqpc@yonyou.com"}

	 */
	public void setUserTagTest() throws JsonProcessingException, IOException {
		//		String userName = "17701888888";
		String userName = "17603888888";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		System.out.println(userId);
		//	    String userId = "83fbf75e-9a72-46f5-a040-4f21c2b74f5a";
		String tag = "ENTERPRISE_STAFF";
		String key = "00ba871a-f45f-4263-a29f-f0f9d98e2f68";
		//		String value = "{\"enterpriseId\":\"05c08e78-de5b-4071-9bb4-2c2621bc24d6\",\"enterpriseName\":\"用友集团\",\"staffMobile\":\"13621031586\",\"staffEmail\":\"fengqpc@yonyou.com\"}";
		String value = "{\"enterpriseId\":\"05c08e78-de5b-4071-9bb4-2c2621bc24d6\",\"enterpriseName\":\"用友集团\",\"staffMobile\":\"17610888888\",\"staffEmail\":\"test121001@test1988.com\"}";


		String msg = UserCenter.setUserTag(userId, tag, key, value);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("user").get("userCode").asText().equals("YHT-18810039018"));
	}


	/*
	 * 
	 *企业员工信息删除员工标签SDK接口 --正常测试流程
	 public static String deleteUserTag(String userId, String tag,String key)；
                  参数：userId 用户ID
          tag ENTERPRISE_STAFF 标签类型固定值
          key "enterpriseId" 标识企业ID
	 */
	@Test
	public void deleteUserTagTest() throws JsonProcessingException, IOException {

		String userName = "17603888888";//17701888888
		
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		System.out.println(userId);
		//	    String userId = "83fbf75e-9a72-46f5-a040-4f21c2b74f5a";
		String tag = "ENTERPRISE_STAFF";
		String key = "00ba871a-f45f-4263-a29f-f0f9d98e2f68";
		String msg = UserCenter.deleteUserTag(userId, tag, key);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").toString() == "标签不存在，无法删除");   

	}
	/*
	 * 
	 *批量增加用户(最多1000条）SDK接口 --正常测试流程
	 public static String simpleAddUsers(String jsonStr)，或者simpleAddUsers(String jsonStr, String sysid)
                  参数：sysId string 系统编码，表示是属于那个应用的用户（例如：ipu，hr_cloud）
          jsonStr UserVO 用户信息，userMobile，userEmail必须传字段，不可都传空字符串，至少一个字段有值；可选userPassword，
                              密码可不传，不传的话会被设置为默认密码；需要传systId,应用标识。
                              格式如下
         {"users":
         [
         {"userCode":"ptest1",
         "userPassword":"tanyh*123",
         "userMobile":"13412343431",
         "userName":"ddsp1",
         "userEmail":"ptest1@yonyou.com"
         },
         {"userMobile":"13412343432",
         "userName":"aasaw",
         "userEmail":"ptest2@yonyou.com"
         }
         ],
         "sysId":"ipu"
         }
	 */
	@Test
	public void simpleAddUsersTest() throws JsonProcessingException, IOException {
		String userStr = UserCenter.getUserByCode("");
		String  jsonStr = "{\"users\":[{\"userCode\":\"19986888888\",\"userPassword\":\"qwaszx12\",\"userMobile\":\"19986888888\",\"userName\":\"dd01\",\"userEmail\":\"\"},"
				+ "{\"userMobile\":\"\",\"userName\":\"aasaw\",\"userEmail\":\"\"}],\"sysId\":\"ipu\"}";
		String sysid = "ipu";      

		String msg = UserCenter.simpleAddUsers(jsonStr, sysid);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	//	@Test
	//	public void simpleAddUsersTest2() throws JsonProcessingException, IOException {
	//		String  jsonStr = "{\"users\":[{\"userCode\":\"ptest1\",\"userPassword\":\"tanyh*123\",\"userMobile\":\"13412343431\",\"userName\":\"ddsp1\",\"userEmail\":\"ptest1@yonyou.com\"},{\"userMobile\":\"13412343432\",\"userName\":\"aasaw\",\"userEmail\":\"ptest2@yonyou.com\"}],\"sysId\":\"ipu\"}";
	//		      
	//		
	//		String msg = UserCenter.simpleAddUsers(jsonStr, "ipu");
	//		System.out.println(msg);
	//		JsonNode node = mapper.readTree(msg);
	//		Assert.assertTrue(node.get("msg").has(fieldName));
	//	}

	/**
	 * public static String quickAddUsers(String jsonStr, String sysid)，或者quickAddUsers(String jsonStr, String sysid, boolean showUser)

	 * 
	 * 
	 *   参数：sysId string 系统编码，表示是属于那个应用的用户（例如：ipu，hr_cloud）
          jsonStr UserVO 用户信息，userMobile，userEmail必须传字段，不可都传空字符串，至少一个字段有值；可选userPassword，
                              密码可不传，不传的话会被设置为默认密码；需要传systId,应用标识。
          showUser boolean 在返回数据中，是否显示用户信息。true显示，false不显示，默认是不显示                  
                              格式如下
         {"users":
         [
         {"userCode":"ptest1",
         "userPassword":"tanyh*123",
         "userMobile":"13412343431",
         "userName":"ddsp1",
         "userEmail":"ptest1@yonyou.com"
         },
         {"userMobile":"13412343432",
         "userName":"aasaw",
         "userEmail":"ptest2@yonyou.com"
         }
         ],
         "sysId":"ipu"
         }
	 */
	@Test
	public void quickAddUsersTest() throws JsonProcessingException, IOException {
		String userStr = UserCenter.getUserByCode("");
		String  jsonStr = "{\"users\":[{\"userCode\":\"test121703@yonyou.com\",\"userPassword\":\"qwaszx12\",\"userMobile\":\"19991888888\",\"userName\":\"ddsp1\",\"userEmail\":\"test121703@yonyou.com\"}],\"sysId\":\"ipu\"}";
//		
//		String  jsonStr = "{\"users\":[{\"userCode\":\"test121703@yonyou.com\",\"userPassword\":\"qwaszx12\",\"userMobile\":\"19991888888\",\"userName\":\"ddsp1\",\"userEmail\":\"test121703@yonyou.com\"},"
//				+ "{\"userMobile\":\"19992888888\",\"userName\":\"aasaw\",\"userEmail\":\"test121704@yonyou.com\"}],\"sysId\":\"ipu\"}";
		String sysid = "";
		Boolean showUser = false;

		String msg = UserCenter.quickAddUsers(jsonStr, sysid, showUser);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	/**
	 * String cancelUserAccount(String userId,String accessToken,String code)
	 * userId String 用户id
	 * accessToken String accesstoken
	 * code String 验证码（短信或邮箱验证）
	 */

	@Test
	public void cancelUserAccountTest() throws JsonProcessingException, IOException {
		//		   String userid = "7b2ae5ba-be9f-4a3c-bc15-5fff0530ea85";//17604888888", "qwaszx12"
		String userid = "7b2ae5ba-be9f-4a3c-bc15-5fff0530ea85";//18883287362", "1234567a"
		//leiqh@yonyou.com    18883287362
		//
		//				// 本测试方法的代码
		//				String accessToken = UserCenter.genAccessTokenBySSOToken(ssotoken);
		String accessToken = UserCenter.generateAccessToken("656897109@qq.com", "1234qwer", true);
		//		   String accessToken = UserCenter.generateAccessToken("18883287362", "1234567a", true);
		System.out.println("hahahh"+accessToken);
		//		   String code = "190102";	
		//		   String msg1 = UserCenter.cancelUserAccount(userid, "fda85d73-6715-433c-8d20-5b574b7452f1", code);
		//		   System.out.println(msg1);
		//		   JsonNode node1 = mapper.readTree(msg1);
		//		   Assert.assertTrue(node1.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("status").asInt() == 1);

	}

	//code:cZo0vk
	/***
	 * 根据授权码获取accesstoken
	 * 
	 * 
	 */

	@Test
	public void  getAccessTokenByCodeTest() throws JsonProcessingException, IOException {

		String accessToken = UserCenter.getAccessTokenByCode("13gvPM");
		System.out.println(accessToken);


	}

	/***
	 * 根据ID和租户ID获取NC用户信息
	 * 
	 * userId 用户id 
	 * tenantId 租户id
	 * 
	 */

	@Test
	public void  getNCInfoByUserIdAndTenantIdTest() throws JsonProcessingException, IOException {

		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));
		//idtest
		//		String userid = "9813276c-c399-4f93-bb99-4da2a953533c";
		//		String tenantId = "ppt16e6o";
		//euc
		String userid = "914fbb03-e01d-4def-a2ce-7e84393eec33";
		String tenantId = "hc6h4yne";
		String msg = UserCenter.getNCInfoByUserIdAndTenantId(userid, tenantId);

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
		Assert.assertTrue(node.get("status").asInt() == 1);
		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}

	/***
	 * 批量修改用户手机号和邮箱
	 * public static String updateContacts(String systemCode, String jsonStr);
	 * systemCode 系统编码，表示是属于那个应用的用户（例如：ipu，hr_cloud）  可以为空
	 * jsonStr 用户信息，yhtUserId, userMobile，userEmail,userMobileCountrycode(国家编码)
       yhtUserId：被修改账号的用户id userMobile，userEmail:要修改为的手机号和邮箱
必须传字段，不可都传空字符串，
	 * 
	 */ 
	@Test
	public void UpdateUsersTest() {
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Map<String, String> user1 = new HashMap<String, String>();
		Map<String, String> user2 = new HashMap<String, String>();
		Map<String, String> user3 = new HashMap<String, String>();
		//        user1.put("yhtUserId", "e35ac485-5661-4d0e-b259-10a6fad6046a");
		//        user1.put("userName", null);
		//        user1.put("userEmail", "651693429@qq.com");
		//        user1.put("userMobile", "13621031586");
		//        user1.put("userMobileCountrycode", "86");
		user2.put("yhtUserId", "4840e052-79cd-43a8-ab96-2ad821c5106c");
		user2.put("userEmail", "wuyunbo@yonyou.com");
		user2.put("userName", "吴运波");
		user2.put("userMobile", "17517888888");
		user2.put("userMobileCountrycode", "86");

		//        user3.put("yhtUserId", "udn_49436");
		//        user3.put("userName", "李水波");
		//        user3.put("userMobile", "18330032829");
		//        user3.put("userEmail", "vincent.li@takchungroup.com");
		//        user3.put("userMobileCountrycode", "86");

		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> users = new ArrayList<Object>();
		//   users.add(user1);
		users.add(user2);
		// users.add(user3);
		params.put("users", users);
		String jsonStr = SDKUtils.getJsonStr(params);
		System.out.println(jsonStr);
		String msg = UserCenter.updateContacts(null, jsonStr);
		System.out.println(msg);
	}
	@Test
	public void  updateContactsTest() throws JsonProcessingException, IOException {
		//17501888888  84c228ab-09cd-41d4-ab98-8ab6cc66a038
		String systemCode = "aps";

		String jsonStr = "{\"users\":[{\"yhtUserId\":\"84c228ab-09cd-41d4-ab98-8ab6cc66a038\",\"userMobile\":\"17507888888\",\"userEmail\":\"test031101@yonyou.com\",\"\r\n" + 
				"\r\n" + 
				"userMobileCountrycode\":\"86\"}";


		String msg = UserCenter.updateContacts(systemCode, jsonStr);

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
		//		Assert.assertTrue(node.get("status").asInt() == 1);
		//		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}

	/***
	 * 营销云及u8c租户信息的设置
	 * setUserCurrentTenant(btmpToken, map);
	 * btmpToken 临时token 
	 * map 
	 * 
	 */
	@Test
	public void  setUserCurrentTenantTest() throws JsonProcessingException, IOException {
		//17501888888  84c228ab-09cd-41d4-ab98-8ab6cc66a038
		String btmpToken = "btta812c256-2a1f-4476-a86a-f20e8b7d47d8";
		Map<String, String> map = new HashMap<String, String>();
		map.put("tenantId", "chm0norp");
		String msg = UserCenter.setUserCurrentTenant(btmpToken, map);

		//		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
		//		Assert.assertTrue(node.get("status").asInt() == 1);
		//		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}


	/***
	 * 营销云及u8c租户信息的读取
	 * getUserByToken(btmpToken);
	 * btmpToken 临时token 
	 * 
	 */
	//	@Test
	//	public void  getUserByTokenTest() throws JsonProcessingException, IOException {
	//		//17501888888  84c228ab-09cd-41d4-ab98-8ab6cc66a038
	//		String btmpToken = "btta812c256-2a1f-4476-a86a-f20e8b7d47d8";
	//		String msg = UserCenter.getUserByToken(btmpToken);
	//		
	//		System.out.println(msg);
	////		JsonNode node = mapper.readTree(msg);
	////		System.out.println("********"+node);
	////		System.out.println("********"+node.get("status").asInt());
	////		Assert.assertTrue(node.get("status").asInt() == 1);
	////		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));
	//		
	//	}

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
		String msg = UserCenter.searchUserByContact(mobileList,null);

		System.out.println(msg);
		//		JsonNode node = mapper.readTree(msg);
		//		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
		//		Assert.assertTrue(node.get("status").asInt() == 1);
		//		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}

	/**
	UserCenter.getNCCLoginToken(String tenantId, String ncVersion, String token)
	参数说明：
	tenantId   租户id
	ncVersion   erp版本
	token      友户通accessToken

	返回值：
	{"status":0,"msg":"参数错误，未找到token"}
	{"status":1,"token":"0f07211b58cf05583d0502dc4ee1b989"}
	 **/
	@Test
	public void  getNCCLoginTokenTest() throws JsonProcessingException, IOException {

		//生成yht的accesstoken
		String username = "test1108a@test1988.com";//
		String Password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);

		String msg = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		//		String msg1 = UserCenter.createAccessToken(arg0, arg1, arg2, arg3)

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		JSONObject resultJson = JSONObject.fromObject(msg);
		System.	out.println(resultJson);
		
		String data = resultJson.getString("data");
		JSONObject dataJson = JSONObject.fromObject(data);
		String yhtAccessToken = dataJson.getString("access_token");
		System.out.println(yhtAccessToken);
//		//用yht的token换ncc的临时token
		String ret = UserCenter.getNCCLoginToken("v5jb3kcp","nccloud",yhtAccessToken);
		System.	out.println(ret);

		//		System.out.println(msg); 
      //返回 {"status":0,"msg":"缺少参数","code":"400"}20190506
	}
	//	/***
	//	 * 营销云及u8c租户信息的读取
	//	 * getUserByToken(btmpToken);
	//	 * btmpToken 临时token 
	//	 * 
	//	 */
	//	@Test
	//	public void  getUserByTokenTest() throws JsonProcessingException, IOException {
	//		//17501888888  84c228ab-09cd-41d4-ab98-8ab6cc66a038
	//	
	//	}


//	@Test
	/*
	 * 为用户产生自动登录Token 正常情况--接口废弃
	 */

//	public void genTokenByUserIdTest() throws JsonProcessingException, IOException {
//
//		String userName = "test1108a@test1988.com";//15210142172
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
//
//		String msg = UserCenter.genTokenByUserId(userId);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//
//	}

	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的
	 */
	public void generateAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String username = "test1108a@test1988.com";//
		String Password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);

		String msg = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		//		String msg1 = UserCenter.createAccessToken(arg0, arg1, arg2, arg3)

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
    
	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的
	 */
	public void generateAccessTokenTest01() throws JsonProcessingException, IOException, InterruptedException {

		String username = "17601888888";
		String password = "yonyou@1988";
		String md5Password = SDKUtils.encodeUsingMD5(password);
		String shaPassword = SDKUtils.encodeUsingSHA(password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);
		String msg = UserCenter.generateAccessToken(username, password, true);
		//		String msg1 = UserCenter.createAccessToken(arg0, arg1, arg2, arg3)

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
	}



	@Test
	/*
	 * 根据accesstoken获取临时token 正常情况的测试
	 */
	public void genTokenByAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String username = "15210142172";
		String Password = "yonyou@1988";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);

		String accessToken = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		System.out.println(accessToken);
		String msg = UserCenter.genTokenByAccessToken(accessToken);
		System.out.println("获取access_token、refresh_token:" + msg);
		JsonNode node1 = mapper.readTree(msg);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		//		String accessToken = node1.get("data").get("access_token").asText();

		// 根据accesstoken获取临时token
		//		String msg2 = UserCenter.genTokenByAccessToken(accessToken);
		//		System.out.println("获取access_token、refresh_token:" + msg2);
		//		JsonNode node2 = mapper.readTree(msg2);
		//		Assert.assertTrue(node2.get("status").asInt() == 1);

	}
  
	@Test
	/*
	 * 根据accesstoken获取用户信息 正常情况的测试
	 */
	public void getUserByTokenTestdd() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken
//		String Password = "qwaszx12";
//		String shaPassword = SDKUtils.encodeUsingSHA(Password);
//		String md5Password = SDKUtils.encodeUsingMD5(Password);
//
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("username", "15210142172");
//		params.put("shaPassword", shaPassword);
//		params.put("md5Password", md5Password);
//
//		String msg = UserCenter.createSSOToken(params);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		String ssotoken = node.get("data").asText();
//
//		// 获取accesstoken
//		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		String accessToken = node1.get("accesstoken").asText();
//
//		// 本测试方法的代码
//		String msg2 = UserCenter.getUserByToken(accessToken);
//		System.out.println(" 根据accesstoken获取用户信息 "+msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
		String username = "test1108a@test1988.com";//15210142172
		String Password = "qwaszx12";//
		   String tenantId = "o2hgbgdt";//apitest0403 
		   String orgId = "6666";
		String msg = UserCenter.getU8cSystemAccessToken(tenantId, orgId);
		System.out.println(msg);
		JsonNode node1 = mapper.readTree(msg);
		//String accessToken = node1.get("accesstoken").asText();
		String accessToken = node1.get("data").get("access_token").asText();
		System.out.println(accessToken);
		// 本测试方法的代码
		String msg2 = UserCenter.getUserByToken(accessToken);
		System.out.println(" 根据accesstoken获取用户信息 "+msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	@Test
	/*
	 * 检测AccessToken是否过期 正常情况的测试
	 */
	public void isAccessTokenValidTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取access_token、refresh_token

		String username = "15210142172";
		String Password = "yonyou@1988";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String msg = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
		//		String msg = UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
		System.out.println("获取access_token、refresh_token:" + msg);
		JsonNode node1 = mapper.readTree(msg);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String accessToken = node1.get("data").get("access_token").asText();

		boolean msg1 = UserCenter.isAccessTokenValid(accessToken);
		System.out.println(msg1);
		Assert.assertTrue(msg1);

		// 销毁accessToken
		//		String msg2 = UserCenter.destroyAccessToken(accessToken);
		//		System.out.println(msg2);
		//		JsonNode node2 = mapper.readTree(msg2);
		//		Assert.assertTrue(node2.get("status").asInt() == 1);

		// 等1分钟，此时access_token失效
		//		Thread.sleep(60000);
		//		String a = UserCenter.getUserByToken("7c07e50c-eb91-4c0a-8be3-274bdfad88da");
		//		System.out.println(a);
		//		boolean msg3 = UserCenter.isAccessTokenValid(accessToken);
		//		System.out.println(msg3);
		//		Assert.assertFalse(msg3);

	}
	@Test
	/*
	 * 根据accesstoken获取用户信息 正常情况的测试
	 */
	public void getUserByTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken
		String Password = "yonyou@1988";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "15210142172");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String ssotoken = node.get("data").asText();

		// 获取accesstoken--本测试方法需要这个参数
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		String accessToken = node1.get("accesstoken").asText();

		// 本测试方法的代码
		String msg2 = UserCenter.getUserByToken(accessToken);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 销毁accesstoken 正常情况的测试
	 */
	public void destroyAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken
		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "test1108a@test1988.com");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String ssotoken = node.get("data").asText();

		// 获取accesstoken--本测试方法需要这个参数
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		String accessToken = node1.get("accesstoken").asText();
		System.out.println(accessToken);
		// 销毁前，可以获取用户信息
//		String msg2 = UserCenter.getUserByToken(accessToken);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
//
//		// 本测试方法的代码，销毁
		String msg3 = UserCenter.destroyAccessToken(accessToken);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
//
//		// 销毁后，再获取用户信息应该获取不到
//		String msg4 = UserCenter.getUserByToken(accessToken);
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 0);

	}

	@Test
	/*
	 * 刷新accesstoken 正常情况的测试 access_token一分钟失效、refresh_token三分钟失效
	 * 这个用户需要sdk.properties里oauth2file.path=oauth2_dd.properties
	 * oauth2_dd.properties这个文件里client_id=37
	 */
	public void refreshAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取access_token、refresh_token

		// 获取access_token、refresh_token

		String username = "15210142172";
		String Password = "yonyou@1988";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String msg = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
		//		String msg1 = UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
		System.out.println("获取access_token、refresh_token:" + msg);
		JsonNode node1 = mapper.readTree(msg);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String accessToken = node1.get("data").get("access_token").asText();
		String refreshToken = node1.get("data").get("refresh_token").asText();

		// 获取用户信息
		String msg2 = UserCenter.getUserByToken(accessToken);
		System.out.println("第一次获取用户信息：" + msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);

		// access_token没有失效，此时刷新应该报错
		String msg3 = UserCenter.refreshAccessToken(refreshToken);
		System.out.println("access_token没有失效，此时刷新不成功：" + msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 5);
		Assert.assertTrue(node3.get("msg").asText().equals("Access Token  does not Timeout"));

		// 获取用户信息
		String msg4 = UserCenter.getUserByToken(accessToken);
		System.out.println("第二次获取用户信息：" + msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 1);

		// 等1分钟，此时access_token失效
		Thread.sleep(60000);

		// access_token已失效，此时刷新应该成功
		String msg5 = UserCenter.refreshAccessToken(refreshToken);
		System.out.println("access_token已失效，此时刷新应该成功:" + msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt() == 1);

		// 刷新后获取新的access_token
		String accessTokenNew = node5.get("data").get("access_token").asText();
		String refreshTokenNew = node5.get("data").get("refresh_token").asText();

		// access_token已失效，此时还用这个值获取用户信息失败
		String msg6 = UserCenter.getUserByToken(accessToken);
		System.out.println("access_token已失效，此时还用这个值获取用户信息失败：" + msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt() == 0);
		Assert.assertTrue(node6.get("msg").asText().equals("can not find user by accesstoken "));

		// 使用刷新后新获取的access_token，可以获取用户信息
		String msg7 = UserCenter.getUserByToken(accessTokenNew);
		System.out.println(msg7);
		JsonNode node7 = mapper.readTree(msg7);
		Assert.assertTrue(node7.get("status").asInt() == 1);

		// 等2分钟，即一共等了3分钟，此时refreshToken失效
		Thread.sleep(120000);

		// 第一次获取的refresh_Token失效，此时刷新失败
		String msg8 = UserCenter.refreshAccessToken(refreshToken);
		System.out.println("refresh_Token失效，此时刷新失败：" + msg8);
		JsonNode node8 = mapper.readTree(msg8);
		Assert.assertTrue(node8.get("status").asInt() == 0);
		Assert.assertTrue(node8.get("msg").asText().equals(
				"refresh accesstoken error,can not find accesstoken from refreshtoken,may be refresh token have used already"));

		// 第二次获取的refresh_Token有效，此时刷新成功
		String msg9 = UserCenter.refreshAccessToken(refreshTokenNew);
		System.out.println("第二次获取的refresh_Token有效，此时刷新成功：" + msg9);
		JsonNode node9 = mapper.readTree(msg9);
		Assert.assertTrue(node9.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 根据用户名密码创建ssotoken(uclient) 正常情况的测试
	 */
	public void createSSOTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String Password = "yonyou@1988";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "15210142172");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg1 = UserCenter.createSSOToken(params);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据ssotoken获取accessToken(uclient) 正常情况的测试
	 */
	public void genAccessTokenBySSOTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken
		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "test1108a@test1988.com");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String ssotoken = node.get("data").asText();
		System.out.println(ssotoken);
     
		// 本测试方法的代码
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据accessToken获取ouathToken(uclient) 正常情况的测试
	 */
	public void genOuathTokenByAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken
		String Password = "qwaszx12";//test1108a@test1988.com
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "test1108a@test1988.com");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String ssotoken = node.get("data").asText();

		// 获取accesstoken--
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		String accessToken = node1.get("accesstoken").asText();

		String msg2 = UserCenter.genOuathTokenByAccessToken(accessToken);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据验证码获取accessToken(uclient) 正常情况的测试
	 * generateAccessTokenByValidateCode(String userName, String validateCode)

	 */
	public void generateAccessTokenByValidateCodeTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken--本测试方法需要这个参数
		String userName = "15210142172";
		String validateCode = "1213";

		String msg = UserCenter.generateAccessTokenByValidateCode(userName, validateCode, false);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	@Test
	/*
	 * 批量增加用户 正常情况的测试
	 */
	public void addUsersTest() throws JsonProcessingException, IOException {

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode = date.format(new Date());

		String systemCode = "yht";
		int i = 1;
		Map<String, String> user1 = new HashMap<String, String>();
		user1.put("userCode", userCode + "code");
		user1.put("userName", userCode + "name");
		user1.put("userEmail", userCode + "@chacuo.net");

		Map<String, String> user2 = new HashMap<String, String>();
		user2.put("userCode", userCode + i + "code");
		user2.put("userName", userCode + i + "name");
		user2.put("userEmail", userCode + i + "@chacuo.net");

		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> users = new ArrayList<Object>();
		users.add(user1);
//		users.add(user2);
		params.put("users", users);
		String jsonStr = Utils.getJsonStr(mapper, params);
		System.out.println(jsonStr);
		String msg = UserCenter.addUsers(systemCode, jsonStr);
		System.out.println("批量增加用户 "+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("保存成功"));

	}
	@Test
	/*
	 * 添加单个用户
	 * 
	 */
	public void addUserTest() throws JsonProcessingException, IOException, InterruptedException {

		// 
		String userCode = "YHT20191108bbbb";
		String userName = "test1108dd@test1988.com";
		String userEmail = "test1108dd@test1988.com";
		String userMobile = "19931888888";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", userCode);
		params.put("userName", userName + "name");
		//params.put("userEmail", userEmail);
		params.put("userMobile", userMobile);
		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	/*
	 * 登录校验 正常情况的测试
	 */
	public void verifyUserTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "15210142172";
//		String userName = "15210142172";
//		String Password = "qwaszx12"; idtest
		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String sysId = "yht";

		// 估计把密码输入错误，让用户锁定
//		for (int i = 0; i < 6; i++) {
//			String msg0 = UserCenter.verifyUser(userName, "00", sysId);
//			System.out.println(msg0);
//			JsonNode node0 = mapper.readTree(msg0);
////			Assert.assertTrue(node0.get("status").asInt() == 0);
//			// Assert.assertTrue(node0.get("msg").asText().equals("用户名或密码错误"));
//		}

		// 锁定用户登录，给友好提示
		String msg = UserCenter.verifyUser(userName, Password, sysId);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 0);
//		Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));

//		String msg1 = UserCenter.verifyUser(userName, Password, sysId, null, null);
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
////		Assert.assertTrue(node1.get("status").asInt() == 0);
//		Assert.assertTrue(node1.get("msg").asText().equals("用户被锁定"));

		String msg2 = UserCenter.verifyUserUsingEncodePwd(userName, md5Password, shaPassword, sysId);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 0);
//		Assert.assertTrue(node2.get("msg").asText().equals("用户被锁定"));

		String msg3 = UserCenter.verifyUserUsingEncodePwd(userName, md5Password, shaPassword, sysId, null, null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 0);
//		Assert.assertTrue(node3.get("msg").asText().equals("用户被锁定"));

		// 等待15分钟，用户解锁
//		Thread.sleep(900000);

		// 解锁后，登录成功
//		String msg4 = UserCenter.verifyUser(userName, Password, sysId, "aa");
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据用户ID获取用户信息 正常流程测试
	 */
	public void getUserByIdTest() throws JsonProcessingException, IOException {
		//String userName = "15210142172";//用户名就是姓名、昵称
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
//		String userId = "6e1d9cc0-e944-4268-834a-d556dd838151";//idtest
//		String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";//euc
		//String userId = "4ef10bce-ee59-4d13-b4c3-730e69cd976c";
		String userId = "bc95ed3d-180b-440b-b05d-b3d17f5c7367";
		String msg = UserCenter.getUserById(userId);
		System.out.println("根据用户ID获取用户信息"+msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		Assert.assertTrue(node.get("user").get("userCode").asText().equals("YHT-706-7261541673334871"));
       
	}
	@Test
	/*
	 * 根据用户ID签署用户隐私权
	 */
	public void signPrivacyProtocolTest() throws JsonProcessingException, IOException {
//		String userId = "6e1d9cc0-e944-4268-834a-d556dd838151";//idtest
//		String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";//euc
		//String userId = "4ef10bce-ee59-4d13-b4c3-730e69cd976c";
		String userId = "bc95ed3d-180b-440b-b05d-b3d17f5c7367";
		
		String msg = UserCenter.signPrivacyProtocol(userId);
	
		System.out.println("根据用户ID签署用户隐私权"+msg);
		
	   JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		Assert.assertTrue(node.get("user").get("userCode").asText().equals("YHT-706-7261541673334871"));
       
	}
	

	@Test
	/*
	 * 根据激活码激活用户 开发把15210142172设置成未激活，并生成一个激活码
	 * 第二个参数是激活码
	 * 

        String手机号mobile
        String激活码 activeCode
	 * 
	 */
	public void activeUserByCodeTest() throws JsonProcessingException, IOException {

		String msg = UserCenter.activeUserByCode("17601888888", "yonyou@1988");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

//		String msg1 = UserCenter.activeUserByCode("stt2017092001@chacuo.net", "325195");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
	}
   
	@Test
	/*
	 * 激活用户
        String手机号mobilegetUserByID
        String激活码 activeCode
	 * 
	 */
	public void activateUserTest() throws JsonProcessingException, IOException {

		String userContact = "17701888888";
		String userpwd = "yonyou@1988"; //
		String pwd = SDKUtils.encodeUsingSHA(userpwd);
		System.out.println(pwd);
		String code = "190627";//验证码，忘记密码获取
		String msg = UserCenter.activateUser(userContact, pwd, code);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 激活用户
        String手机号mobilegetUserByID
        String激活码 activeCode
	 * 
	 */
	public void ISNC65Test() throws JsonProcessingException, IOException {

		
		
		String msg = NCUserCenter.isNC65("17501888888");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 15、修改密码，重置密码 正常情况的测试 因为需要发送验证码，次用例每次执行都要重新获ts
	 * 在修改邮箱页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里 ts的值就是key
	 * 图像验证码的值就是code 这个用例每次只能执行一次，再次执行，只能重新获取
	 * 
	 * 修改密码
	 * 
	 * userId	String	用户id（必填）
       shaPassword	String	旧密码的sha-1值（必填）
       md5Password	Striing	旧密码的md5值（必填）
       shaNewPassword	String	新密码的sha-1值（必填）
       md5NewPassword	String	新密码的md5值（必填）
       reset	boolean	是否是重置密码（必填）
       sid	String	验证码校验接口返回的sid（必填）（友户通接口-验证手机验证码）
       status	int	请求的状态，如果为0，表示失败，如果为1，表示成功
       
	 */
	public void modifypasswordTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//旧密码错误、新密码不一致，使用过最近5次的密码
//		String userName = "17615888888";
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "1c097ac5-0b76-4b2a-a171-3c01474096b0";//19707888888
		//String userId = "6a89e531-3533-4def-99b1-56d51085d29c";// laodu@test1988.com
		// 旧密码
		String Password = "yonyou@1988";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		// 新密码
		String Passwordnew = "qwer4321";
		String shaNewPassword = SDKUtils.encodeUsingSHA(Passwordnew);
		String md5NewPassword = SDKUtils.encodeUsingMD5(Passwordnew);
		

		// 发送验证码
//		String msg11 = UserCenter.sendcode(contact, type, key, code);
		
		String code = "191108";//手机获取的验证码,例如忘记密码
		String contact = "19707888888";

		// 获取sid
				String msg1 = UserCenter.validateCode("mobile", userId, code, "");
				//String msg1 = UserCenter.validateCode("email", userId, code, contact);
				System.out.println(msg1);
				JsonNode node1 = mapper.readTree(msg1);
//				Assert.assertTrue(node1.get("status").asInt() == 1);
				String sid = node1.get("sid").asText();
				System.out.println(sid);
		
		
//		String node1 = UserCenter.validateCode("mobile", userId, code, contact);
//		String sid = node1.get("sid").asText();
//		System.out.println("dis+++++"+sid);
		
		
		//修改密码
		String msg2 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
		String msg10String = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid, null);
		
		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
		//修改密码
//		String msg3 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
//		System.out.println(msg3);
//		JsonNode node2 = mapper.readTree(msg3);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		
//		String sysId = "yht";
//		Date dt = new Date();
//		SimpleDateFormat matter1 = new SimpleDateFormat("yyMMdd");
//		String verification = matter1.format(dt);
//		System.out.println(verification);

		// 先使用正确密码登录一次，让错误密码从1开始计算。
//		String msg00 = UserCenter.verifyUser(userName, Password, sysId);
//		System.out.println(msg00);
//		JsonNode node00 = mapper.readTree(msg00);
//		Assert.assertTrue(node00.get("status").asInt() == 1);
//
//		// 估计把密码输入错误，让用户锁定
//		for (int i = 0; i < 6; i++) {
//			String msg0 = UserCenter.verifyUser(userName, "00", sysId);
//			System.out.println(msg0);
//			JsonNode node0 = mapper.readTree(msg0);
//			Assert.assertTrue(node0.get("status").asInt() == 0);
//			// Assert.assertTrue(node0.get("msg").asText().equals("用户名或密码错误"));
//		}
//
//		// 锁定用户登录，给友好提示
//		String msg = UserCenter.verifyUser(userName, Password, sysId);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 0);
//		Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));
//
//		// 发送验证码
//		String contact = "stt2019011601@test1988.com";
//		String type = "email";
//		String key = "1547636580000827082";
//		String code = "7KM4";
//
//		String msg11 = UserCenter.sendcode(contact, type, key, code);
//		System.out.println(msg11);
//		JsonNode node11 = mapper.readTree(msg11);
//		Assert.assertTrue(node11.get("status").asInt() == 1);
//
//		// 获取sid
//		String msg1 = UserCenter.validateCode("email", userId, verification, "");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//		String sid = node1.get("sid").asText();
//		System.out.println(sid);
//
//		// 修改密码
//		String msg2 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
//
//		// 登录
//		String msg3 = UserCenter.verifyUser(userName, Passwordnew, sysId);
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 1);

	}
	
	
	@Test
	/*
	 * 15(2)重置密码 正常情况的测试 因为需要发送验证码，次用例每次执行都要重新获ts
	 * 在修改邮箱页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里 ts的值就是key
	 * 图像验证码的值就是code 这个用例每次只能执行一次，再次执行，只能重新获取
	 * 
	 * 重置密码
	 */
	public void resetPasswordTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "15210142172";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		// 旧密码
		String Password = "yonyou123";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		// 新密码
		String Passwordnew = "yonyou44";
		String shaNewPassword = SDKUtils.encodeUsingSHA(Passwordnew);
		String md5NewPassword = SDKUtils.encodeUsingMD5(Passwordnew);

		String sysId = "yht";
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyMMdd");
		String verification = matter1.format(dt);
		System.out.println(verification);

//		// 先使用正确密码登录一次，让错误密码从1开始计算。
//		String msg00 = UserCenter.verifyUser(userName, Password, sysId);
//		System.out.println(msg00);
//		JsonNode node00 = mapper.readTree(msg00);
//		Assert.assertTrue(node00.get("status").asInt() == 1);
//
//		// 估计把密码输入错误，让用户锁定
//		for (int i = 0; i < 6; i++) {
//			String msg0 = UserCenter.verifyUser(userName, "00", sysId);
//			System.out.println(msg0);
//			JsonNode node0 = mapper.readTree(msg0);
//			Assert.assertTrue(node0.get("status").asInt() == 0);
//			// Assert.assertTrue(node0.get("msg").asText().equals("用户名或密码错误"));
//		}
//
//		// 锁定用户登录，给友好提示
//		String msg = UserCenter.verifyUser(userName, Password, sysId);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 0);
//		Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));

		// 发送验证码
//		String contact = "15210142172";
//		String type = "mobile";
//		String key = "1553061268000201824";
//		String code = "S74A";
//
//		String msg11 = UserCenter.sendcode(contact, type, key, code);
//		System.out.println(msg11);
//		JsonNode node11 = mapper.readTree(msg11);
//		Assert.assertTrue(node11.get("status").asInt() == 1);

		// 获取sid--
		String code = UserCenter.sendPhoneMessage(userId);//获取手机验证码
		System.out.println(code);
		String contact = userName;

//		// 获取sid
		String sid = UserCenter.validateCode("mobile", userId, code, contact);
//		String msg1 = UserCenter.validateCode("mobile", userId, verification, "");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//		String sid = node1.get("sid").asText();
		System.out.println(sid);

		// 重置密码
		String msg2 = UserCenter.resetPassword(userId, shaNewPassword, md5NewPassword, sid);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);

		// 登录
		String msg3 = UserCenter.verifyUser(userName, Passwordnew, sysId);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);

	}
	
	@Test
	/*
	 * 8.	修改用户属性
	 * 修改用户属性，可修改以下属性：userName、sex、address、birthday、userCode
            其中sex为数字0和1,0为男，1为女，其他为字符串
	 */
	public void updateUserPropertiesTest() throws JsonProcessingException, IOException {
		String userName = "17601888888";//登录账号
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "c7ba2271-d55d-4ddd-8c68-5e0e20bffb11";
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d = date.format(new Date());

		// 修改昵称
//		String key = "userName";
//		String value = d + "name";
//		String msg = UserCenter.updateUserProperties(userId, key, value);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		Assert.assertTrue(node.get("msg").asText().equals("修改昵称成功"));

		// 修改性别
//		String key1 = "sex";
//		String value1 = "1";
//		String msg1 = UserCenter.updateUserProperties(userId, key1, value1);
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//		Assert.assertTrue(node1.get("msg").asText().equals("修改性别成功"));

		// 修改出生日期
//		SimpleDateFormat date2 = new SimpleDateFormat("yyyy年MM月dd日");
//		String d2 = date.format(new Date());
//		String key2 = "birthday";
//		String value2 = "2019年08月08日";
//		String msg2 = UserCenter.updateUserProperties(userId, key2, value2);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
//		Assert.assertTrue(node2.get("msg").asText().equals("修改出生日期成功"));

		// 修改地址
		String key3 = "address";
		String value3 = "1-11-21-";
		String msg3 = UserCenter.updateUserProperties(userId, key3, value3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		Assert.assertTrue(node3.get("msg").asText().equals("修改地址成功"));

		// 修改userCode
//		String key4 = "userCode";
//		String value4 = d + "Code";
//		String msg4 = UserCenter.updateUserProperties(userId, key4, value4);
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 1);
//		Assert.assertTrue(node4.get("msg").asText().equals("修改userCode成功"));

	}
	
	@Test
	/*
	 * 绑定手机 正常情况的测试
	 */

	public void bindMobileTest() throws JsonProcessingException, IOException {
//		// 绑定手机(国内手机)
//		String userName = "18611286701";
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "6bafa31d-5165-4e0f-867e-e59ec25c2263"; //17616888888
		String msg = UserCenter.bindMobile(userId, "16602888888");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 校验绑定后的手机是否正确(因为加密，所以有星号)
		String msg0 = UserCenter.getUserById(userId);
//		System.out.println(msg0);
		JsonNode node0 = mapper.readTree(msg0);
		Assert.assertTrue(node0.get("status").asInt() == 1);
		Assert.assertTrue(node0.get("user").get("userMobile").asText().equals("17619888888"));

//		// 绑定手机（台湾手机）
//		String msg00 = UserCenter.bindMobile(userId, "0988888889", "886");
//		System.out.println(msg00);
//		JsonNode node00 = mapper.readTree(msg00);
//		Assert.assertTrue(node00.get("status").asInt() == 1);
//
//		// 还原手机，为下次执行脚本准备
//		String msg1 = UserCenter.bindMobile(userId, "18611286701");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
	}
	
	@Test
	/*
	 * 绑定邮箱 正常情况的测试 sdk.properties里
	 * client.credential.path=uculture.properties--可以执行这个用户
	 * client.credential.path=authfileyht.txt--不允许执行，会报400
	 */

	public void bindMailTest() throws JsonProcessingException, IOException {
		// 绑定邮箱
		// String userName = "18611286701";
//		String userName = "52888888";
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
//		String userId = "89343fe8-cd2f-4c63-bc20-5a2532533693";
		String userId = "6bafa31d-5165-4e0f-867e-e59ec25c2263"; //17616888888
		String msg = UserCenter.bindEmail(userId, "test19050703@test1988.com");//修改邮箱新的邮箱可以正常登录
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 校验绑定后的邮箱是否正确(因为加密，所以有星号)
		String msg0 = UserCenter.getUserById(userId);
//		System.out.println(msg0);
		JsonNode node0 = mapper.readTree(msg0);
		Assert.assertTrue(node0.get("status").asInt() == 1);
		Assert.assertTrue(node0.get("user").get("userEmail").asText().equals("test041305@test1988.com"));

//		// 还原邮箱，为下次执行脚本准备
//		String msg1 = UserCenter.bindEmail(userId, "jlccstt@126.com");
//		// String msg1 = UserCenter.bindEmail(userId, "yixixinzi@126.com");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 修改手机号或邮箱 正常情况的测试
	 */
	public void modifyContactTest() throws JsonProcessingException, IOException, InterruptedException {

		// "stt2018011801@test1988.com"是企业应用授权用户，只有读写功能，不能修改手机号邮箱。ID是"183c1252-b602-43b1-87f2-16b24b4d4a9a"

		// String userId =
		// UserCenterUtil.getUserIdByLoginName("stt2018011802@test1988.com");
		// String userId ="183c1252-b602-43b1-87f2-16b24b4d4a9a";

//		Date dt = new Date();
//		SimpleDateFormat matter1 = new SimpleDateFormat("yyMMdd");
//		String verification = matter1.format(dt);
//		String verification = "";//可以通过忘记密码，输入手机号来获取真实的验证码
//		String[][] data = {
//				{ "stt2018011801@test1988.com", "183c1252-b602-43b1-87f2-16b24b4d4a9a", "17610888888",
//						"st2018011801@test1988.com", "企业应用授权只读用户" },
//				{ "18802888888", "34000897-01c3-4a76-832a-54e635e3947b", "17611888888", "s2018011801@test1988.com",
//						"nc导入用户" },
//				{ "stt2018011802@test1988.com", "087b526f-392a-4deb-9a06-9cf348fa0307", "17612888888",
//						"st2018011802@test1988.com", "可以修改手机号、邮箱用户" },
//				{ "还原数据                                     ", "087b526f-392a-4deb-9a06-9cf348fa0307", "17613888888",
//						"stt2018011802@test1988.com", "还原数据" } };
//
//		Thread.sleep(60000);

//		for (int i = 0; i < data.length; i++) {
//
//			System.out.println(
//					"------------------------------------" + data[i][4] + "-----------------------------------------");

//			String userId = data[i][1];
//			String Phone = data[i][2];
//			String email = data[i][3];
		String userId = "6bafa31d-5165-4e0f-867e-e59ec25c2263"; //17616888888
		String Phone = "17616888888";
		String email = "test19050703@test1988.com";
		String verification = "698493";//可以通过忘记密码，输入手机号来获取真实的验证码
			// 发送验证码
			// String msg = UserCenter.sendEmailMessage(userId);
			// System.out.println(msg);

			// 发邮件
//			String msg = UserCenter.sendValidateCode(userId, "email");
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);
//			Assert.assertTrue(node.get("msg").asText().equals("邮件发送成功"));

			// 获取sid
			String msg0 = UserCenter.validateCode("email", userId, verification, "");
			System.out.println(msg0);
			JsonNode node0 = mapper.readTree(msg0);
			Assert.assertTrue(node0.get("status").asInt() == 1);
			String sid = node0.get("sid").asText();
			System.out.println("sid"+sid);

			// 修改手机号
			String msg1 = UserCenter.modifyContact(userId, Phone, sid);
//			System.out.println(msg1);
			// JsonNode node1 = mapper.readTree(msg1);
			// Assert.assertTrue(node1.get("status").asInt()==1);
			// Assert.assertTrue(node1.get("msg").asText().equals("邮件发送成功"));

			// 发送验证码
			// String msg10 = UserCenter.sendEmailMessage(userId);
			// System.out.println(msg10);

			// 发邮件
//			String msg10 = UserCenter.sendValidateCode(userId, "email");
//			System.out.println(msg10);
//			JsonNode node10 = mapper.readTree(msg);
//			Assert.assertTrue(node10.get("status").asInt() == 1);
//			Assert.assertTrue(node10.get("msg").asText().equals("邮件发送成功"));
//
//			// 获取sid
//			String msg11 = UserCenter.validateCode("email", userId, verification, "");
//			System.out.println(msg11);
//			JsonNode node11 = mapper.readTree(msg11);
//			Assert.assertTrue(node11.get("status").asInt() == 1);
//			String sid11 = node11.get("sid").asText();
//			System.out.println(sid11);
//
//			// 修改邮箱
//			String msg12 = UserCenter.modifyContact(userId, email, sid);
//			System.out.println(msg12);
			// JsonNode node2 = mapper.readTree(msg2);
			// Assert.assertTrue(node2.get("status").asInt()==1);
			// Assert.assertTrue(node2.get("msg").asText().equals("短信发送成功"));

		}

//	}

	@Test
	/*
	 * 绑定手机 正常情况的测试
	 * 
	 * 请求参数 

      srcuserId	String	被合并或被舍弃的用户id
     destuserId	String	合并的目标用户id
     mobileuserid	Striing	destuserid的用户的手机号使用哪个用户的手机号(只能取值srcuserid，destuserid，null）
     emailuserid	String	destuserid的用户的邮箱使用哪个用户的邮箱(只能取值srcuserid，destuserid，null）
      pswuserid	String	
   destuserid的用户的密码使用哪个用户的密码(只能取值srcuserid，destuserid，null）；

       注意，这个可能会导致destuser的usercode发生变化，因为默认的加密方法是带着usercode一起的sysid
 

String	发起操作的应用代码
	 * 
	 */

	public void mergeDoubleUserTest() throws JsonProcessingException, IOException {
//		// 绑定手机(国内手机)
          //17820888888/test061402@test1988.com   7e8aa7b8-3324-40fd-b11c-cb3b8baa5474  舍弃用户
		//17822888888/test061401@test1988.com    4ef10bce-ee59-4d13-b4c3-730e69cd976c 被合并
		
		//17825888888   7b205567-3baf-43cc-ae3d-6b7d8de7cb70
		String srcuserId = "7e8aa7b8-3324-40fd-b11c-cb3b8baa5474"; //17616888888
		String destuserId = "7b205567-3baf-43cc-ae3d-6b7d8de7cb70";
		String mobileuserid = "7b205567-3baf-43cc-ae3d-6b7d8de7cb70";
		String emailuserid = "7b205567-3baf-43cc-ae3d-6b7d8de7cb70";
		String pswuserid = "qwaszx12";
		String msg = UserCenter.mergeDoubleUser(srcuserId, destuserId, mobileuserid, emailuserid, pswuserid);
		
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);;
       
		
	}
	
	@Test
	/*
	 * YonSuite默认用户token获取接口 正常情况的测试  -线上环境的租户即可
	 * tenantId：
	 * orgId：可以随意传不验证
	 * 接口返回token的有效期，有效期内重复调用接口返回相同的token相当于续期，如果过了有效期后调用接口，返回新的token
	 */
	public void getU8cSystemAccessTokenTest() throws JsonProcessingException, IOException {

		   String tenantId = "o2hgbgdt";//apitest0403 
		   String orgId = "6666";
		   
           String  msg = UserCenter.getU8cSystemAccessToken(tenantId, orgId);  
           JsonNode node1 = mapper.readTree(msg);
           System.out.println(node1);
           String accessToken = node1.get("data").get("access_token").asText();
   		   System.out.println(accessToken);
           //String  msg = UserCenter.getU8cSystemAccessToken(tenantId, orgId); 租户id为空
   		  // return accessToken;

	}
	@Test
	/*
	 * 根据用户id数组和用户名称查找用户
	 */
	public void searchUserTest() throws JsonProcessingException, IOException {
		   
		String[][] users = { { "cac3b9f8-f7f0-455f-bf44-7b899865271d", "stt2017080201@chacuo.net" },
				{ "273ccd32-749c-451c-8784-9d9dff3c3160", "stt2017080202@chacuo.net" },
				{ "c22847e3-9d9c-4f8c-8fac-47fa0975220e", "stt2017080701@chacuo.net" },
				{ "07576a6f-235f-4f6f-8823-17742ef86f8d", "stt2017081401@chacuo.net" },
				{ "c4ac871c-52ad-4a48-a63e-c12eedb5628c", "stt2017081401@chacuo.net" },
				{ "37fbe5c9-7a95-423c-b070-401febadb02d", "stt2017081601@chacuo.net" },
				{ "5ad292d7-fa00-468b-bff4-83dbf96bc94e", "stt2017081602@chacuo.net" },
				{ "6c6888bb-71b1-4198-843a-c29e4f1235b7", "stt2017081603@chacuo.net" },
				{ "65bae085-1eee-47c9-8ee5-2d81889935fe", "stt2017081604@chacuo.net" },
				{ "9964da9d-83af-4b91-81b0-46d245f8f699", "stt2017081701@chacuo.net" },
				{ "b2830637-035b-4885-bb95-b576bb312e8b", "stt2017081801@chacuo.net" },
				{ "0f4a4cde-8e15-414b-ad86-a14509ad8dad", "stt2017082101@chacuo.net" },
				{ "3531cf1f-e6ed-4e08-81dc-d5da90dde699", "stt2017082201@chacuo.net" },
				{ "868d2718-4723-4504-aa03-a773918c2fdb", "suntt@yonyou.com" },
				{ "555efe32-1447-4f86-96cc-a972d440ea0b", "suntt1026@126.com" },
				{ "c1265234-fd39-4124-b00b-dcad33b6a3c2", "stt851207@126.com" },
				{ "afd48191-a519-486d-b60a-dedb0a163f1b", "stt851207@163.com" },
				{ "488e6137-e684-4d40-86ea-a619d43c5a50", "18611286701" } };
		String[] pks = new String[users.length];
		for (int i = 0; i < users.length; i++) {
			pks[i] = users[i][0];
		}
		String userName = "stt";
		String msg = UserCenter.searchUser(pks, userName);
		System.out.println(msg);

		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 16);

	}
	@Test
	/*
	 * 根据联系方式及类型查询单个用户列表 正常情况的测试
	 */
	public void getUserByContactTest() throws JsonProcessingException, IOException {
		String[][] users = {  {"17937888888","mobile"} };//，{ "15210142172", "mobile" },{ "651693429@qq.com", "email" },
		for (int j = 0; j < users.length; j++) {
			String msg = UserCenter.getUserByContact(users[j][0], users[j][1]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
			Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
			Assert.assertTrue(
					node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));

		}
	}
     
	@Test
	/*
	 * 根据联系方式查询用户列表 正常情况的测试
	 */
	public void getUserByContactsTest() throws JsonProcessingException, IOException {
		String[][] users = { { "17937888888" ,""} };//, {"15210142172",""}
		for (int j = 0; j < users.length; j++) {
			String msg = UserCenter.getUserByContacts(users[j][0], users[j][1]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
			Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
			Assert.assertTrue(
					node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));

		}
		
	}
   
	//1、封禁接口调用样例
	  @Test
	    public void testManagerBan(){
	      Map<String,String> data = new HashMap<String,String>();
	      // 5e2c8681-081d-4001-8ada-7f89b6ceb94f  16301888888
//	      String userId ="637a188a-5edb-44d4-b38c-b48909366e1d";
//	      String mobile ="15890832158";
////	      String email ="656897109sdfs@qq.com";
//	      String userId ="a508eb68-819a-4af6-a2ea-d8c9aa94f595";
//	      String mobile ="15890832158";
//	      String email ="656897109sdfs@qq.com";

	     // String userId ="";
//	      String mobile ="16301888888";
	      //String email ="";
	      //异常测试-参数为空或是输入的参数
	      String mobile ="";
	      String email ="";
	      String userId ="5e2c8681-081d-4001-8ada-7f89b6ceb94f";
	      
	      data.put("userId", userId);
	      data.put("mobile", mobile);
	      data.put("email", email);
	      String managerBaseUrl="http://10.3.4.84:8880/yht-manager";
	    // String managerBaseUrl="http://localhost:8080/yht-manager";
	      String url =managerBaseUrl+"/rest/manager/banUser";
//	      String str = SignUtils.signAndPost(url, JSON.toJSONString(data));
	      String str = com.yonyou.yht.sdkutils.sign.SignUtils.signAndPost(url, JSON.toJSONString(data));
	      System.out.println(str);
	    }

	  //正式环境的managerBaseUrl=http://10.3.4.84:8880/yht-manager/
	 /* 2、调用UserCenter.verifyUserUsingEncodePwd("15890832158", SDKUtils.encodeUsingMD5("yonyou@1988"), SDKUtils.encodeUsingSHA("yonyou@1988"), "yht");
	  或者 UserCenter.generateAccessToken("15890832158", "yonyou@1988", false); 已封禁的用户提示：该账号已被封禁，请联系运营人员处理
	 页面端登录：也提示用户已被封禁
	 */
	
}
