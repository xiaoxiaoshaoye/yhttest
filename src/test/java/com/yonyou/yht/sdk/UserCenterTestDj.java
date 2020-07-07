package com.yonyou.yht.sdk;


import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

//import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONObject;
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
//import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.yht.sdk.UserCenter;


//import com.yonyou.yht.sdkutils.PropertyUtil;
public class UserCenterTestDj {
	
	
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
		String  jsonStr = "{\"users\":[{\"userCode\":\"ptest1\",\"userPassword\":\"qwaszx12\",\"userMobile\":\"17802888888\",\"userName\":\"dd01\",\"userEmail\":\"test041101@yonyou.com\"},{\"userMobile\":\"13412343432\",\"userName\":\"aasaw\",\"userEmail\":\"ptest2@yonyou.com\"}],\"sysId\":\"ipu\"}";
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
		String  jsonStr = "{\"users\":[{\"userCode\":\"ptest1\",\"userPassword\":\"tanyh*123\",\"userMobile\":\"13412343432\",\"userName\":\"ddsp1\",\"userEmail\":\"ptest1@yonyou.com\"},{\"userMobile\":\"13412343433\",\"userName\":\"aasaw\",\"userEmail\":\"ptest2@yonyou.com\"}],\"sysId\":\"ipu\"}";
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
	 * 根据友互通ID和租户ID获取NC用户信息
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
		String result = UserCenter.generateAccessToken("15210142172", "yonyou@1988", false);
		JSONObject resultJson = JSONObject.fromObject(result);
		String data = resultJson.getString("data");
		JSONObject dataJson = JSONObject.fromObject(data);
		String yhtAccessToken = dataJson.getString("access_token");
		//用yht的token换ncc的临时token
		String ret = UserCenter.getNCCLoginToken("v5jb3kcp","nccloud",yhtAccessToken);
		System.	out.println(ret);

		//		System.out.println(msg);

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


	@Test
	/*
	 * 为用户产生自动登录Token 正常情况的测试
	 */

	public void genTokenByUserIdTest() throws JsonProcessingException, IOException {

		String userName = "15210142172";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);

		String msg = UserCenter.genTokenByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的
	 */
	public void generateAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String username = "15210142172";
		String Password = "yonyou@1988";
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
	public void getUserByTokenTest01() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoken
		String Password = "qwaszx12";
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

		// 获取accesstoken
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		String accessToken = node1.get("accesstoken").asText();

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
		String Password = "yonyou11";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "stt2017101701@chacuo.net");
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

		// 销毁前，可以获取用户信息
		String msg2 = UserCenter.getUserByToken(accessToken);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);

		// 本测试方法的代码，销毁
		String msg3 = UserCenter.destroyAccessToken(accessToken);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);

		// 销毁后，再获取用户信息应该获取不到
		String msg4 = UserCenter.getUserByToken(accessToken);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 0);

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

		// 获取ssotoken--本测试方法需要这个参数
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
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 0);
//		Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));

		String msg1 = UserCenter.verifyUser(userName, Password, sysId, null, null);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 0);
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
		Thread.sleep(900000);

		// 解锁后，登录成功
		String msg4 = UserCenter.verifyUser(userName, Password, sysId, "aa");
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据用户ID获取用户信息 正常流程测试
	 */
	public void getUserByIdTest() throws JsonProcessingException, IOException {
		String userName = "15210142172";//用户民就是姓名、昵称
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
//		String userId = "6e1d9cc0-e944-4268-834a-d556dd838151";//idtest
		String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";
		String msg = UserCenter.getUserById(userId);
		System.out.println("根据用户ID获取用户信息"+msg);
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
	

}
