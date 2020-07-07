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
//import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.context.log.InvocationInfoProxy;
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
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Table.Cell;
import com.yonyou.enterprise.sdk.UserCenterUtil;
//import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.yht.entity.SimpleUserInfo;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.entity.*;
import com.yonyou.yht.sdkutils.YhtClientPropertyUtil;
import com.yonyou.yht.sdkutils.sign.SignUtils;;

//import com.yonyou.yht.sdkutils.sign.SignUtils;


//import com.yonyou.yht.sdkutils.PropertyUtil;
public class UserCenterTestDJ1 {


	static ObjectMapper mapper = new ObjectMapper();
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
//		String  jsonStr = "{\"users\":[{\"userCode\":\"ptest1\",\"userPassword\":\"qwaszx12\",\"userMobile\":\"17802888888\",\"userName\":\"奥地利0615\",\"userEmail\":\"test041101@yonyou.com\",\"userMobileCountrycode\":\"43\"},"
//				+ "{\"userMobile\":\"13412343432\",\"userName\":\"aasaw\",\"userEmail\":\"ptest2@yonyou.com\"}],\"sysId\":\"ipu\"}";
		String  jsonStr = "{\"users\":"
				+ "[{\"userCode\":\"ptest1\",\"userPassword\":\"qwaszx12\",\"userMobile\":\"484888888\",\"userName\":\"比利时0615\",\"userEmail\":\"test041101@yonyou.com\",\"userMobileCountrycode\":\"32\"},"
				+ "{\"userMobile\":\"13412343432\",\"userName\":\"aasaw\",\"userEmail\":\"ptest2@yonyou.com\"}],"
				+ "\"sysId\":\"ipu\"}";
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
		String  userStr = UserCenter.getUserByCode("");
		String  jsonStr = "{\"users\":[{\"userCode\":\"ptest1\",\"userPassword\":\"tanyh*123\",\"userMobile\":\"13412343432\",\"userName\":\"ddsp1\",\"userEmail\":\"ptest1@yonyou.com\"},{\"userMobile\":\"484888888\",\"userName\":\"比利时0615\",\"userEmail\":\"ptest2@yonyou.com\",\"userMobileCountrycode\":\"32\"}],\"sysId\":\"ipu\"}";
		String sysid = "";
		Boolean showUser = false;

		String msg = UserCenter.quickAddUsers(jsonStr, sysid, showUser);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
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
//		String accessToken = UserCenter.generateAccessToken("656897109@qq.com", "1234qwer", true);
		String accessToken = UserCenter.generateAccessToken("15210142172", "yonyou@1988", true);
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

		//生成授权码 --页面操作 code=mdClr2
		//RNGMV3 15210142172
		String accessToken = UserCenter.getAccessTokenByCode("vDvRiw");
		System.out.println(accessToken);


	}

	/***
	 * 根据友户通ID和租户ID获取NC用户信息
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
		String btmpToken = "bttbFY5Q0Z5SW0zYUNZeDlrenBseldjcEdVRFZYWnJjVEIrR252a3N2bTRHZS9uUFVEbXdzYytpcTdjcUdTYi85Z3I2UjcvVHJtNUxHdU9JK1V6T1pPMTdlK3orYlIrbVd1YVNNMnVrejdWUU09X19ldWMueW9ueW91Y2xvdWQuY29t__6bb36a85b063f1af99ce3376489ba8ae_1588080365304";//15210142172 //btta812c256-2a1f-4476-a86a-f20e8b7d47d8
		Map<String, String> map = new HashMap<String, String>();
		map.put("tenantId", "mcg8v7m0"); //u8cdaily环境 简版员工1025--测试1
		String msg = UserCenter.setUserCurrentTenant(btmpToken, map);
		

		//		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
				Assert.assertTrue(node.get("status").asInt() == 1);
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
		String username = "19901888888";//17946888888  预发环境 19901888888
		String Password = "yonyou@1988";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		System.out.println(md5Password);
		System.out.println(shaPassword);

		String result = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		//		String result = UserCenter.generateAccessToken("15210142172", "yonyou@1988", false);
		JSONObject resultJson = JSONObject.fromObject(result);
		System.	out.println(resultJson);
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
	 * 只有特定的加签文件可以调用
	 */

	public void genTokenByUserIdTest() throws JsonProcessingException, IOException {

		String userName = "19901888888";// 测试环境：16703888888  预发环境 19901888888
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.genTokenByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
  
	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的
	 * 返回AccessToken的接口同时返回User信息
	 */
	public void generateAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {
   
		//		String username = "15210142172";
		//String username = "16301888888";
		String Password = "yonyou@1988";
		String username = "15210142172"; // 测试环境： 17803888888 预发环境：19901888888 已经注销的用户 
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);
//		String msg = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
//				String msg = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		String msg = UserCenter.generateAccessToken(username, Password, false);
		//		String msg1 = UserCenter.createAccessToken(arg0, arg1, arg2, arg3)

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
				//String accessToken = node1.get("accesstoken").asText();
//				String accessToken = node.get("data").get("access_token").asText();
//				System.out.println(accessToken);
//		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的
	 */
	public void generateAccessTokenTest01() throws JsonProcessingException, IOException, InterruptedException {

		String username = "17601888888";//测试环境 ：17601888888 预发环境：19901888888
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

		//String username = "15210142172";
		String username = "16703888888";//已注销用户
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
		//		String Password = "qwer1234";
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

		// 获取accesstoken
		//String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		//		String username = "16703888888";
		//		String Password = "yonyou@1988";
		//		 String tenantId = "ypmi9gbr";
		//		   String orgId = "6666";
		//		String msg = UserCenter.getU8cSystemAccessToken(tenantId, orgId);
		//		System.out.println(msg);
		//		JsonNode node1 = mapper.readTree(msg);
		//		//String accessToken = node1.get("accesstoken").asText();
		//		String accessToken = node1.get("data").get("access_token").asText();
		//		System.out.println(accessToken);
		// 本测试方法的代码
//		String accessToken = "bttekNPQjVkM1QyWHJnbjVXbGVxUHkxRDJMUkltZk0yUVdsa244YWpuQUJJYkdNRTArdHlGbWN5RmJWY3Q5SCtiUStJeGlmWjJHczNlcTRkRTh0blhNM3BxNmxPZGN5RTBqMW4za2toM1Q1RGs9Xzk5ZWE3NjU1LTAwYTItNGJkYS1iMjNjLTE5YWRlMzdlYTU3NF9pZHRlc3QueXl1YXAuY29tXzE1NzU5NDMwMDI5ODY";
		String accessToken = "2dad9e01-1850-4dca-aebf-354559563e04";
		String version_accessToken = "2dad9e01-1850-4dca-aebf-354559563e04__9da765949a7f59686b0bc5ef1388f48c_1587625581732";
		String msg2 = UserCenter.getUserByToken(version_accessToken);
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

		String username = "16703888888";
		String Password = "dujuan10";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String msg = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
		//		String msg = UserCenter.generateAccessToken("2017101701@chacuo.net", "yonyou11", false);
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
		//		String Password = "yonyou@1988";
		//		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		String md5Password = SDKUtils.encodeUsingMD5(Password);
		//
		//		Map<String, String> params = new HashMap<String, String>();
		//		params.put("username", "16703888888");
		//		params.put("shaPassword", shaPassword);
		//		params.put("md5Password", md5Password);
		//
		//		String msg = UserCenter.createSSOToken(params);
		//		System.out.println(msg);
		//		JsonNode node = mapper.readTree(msg);
		//		String ssotoken = node.get("data").asText();
		//
		//		// 获取accesstoken--本测试方法需要这个参数
		//		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		//		System.out.println(msg1);
		//		JsonNode node1 = mapper.readTree(msg1);
		//		String accessToken = node1.get("accesstoken").asText();

		// 本测试方法的代码
		String accessToken = "bttdCsyTkhJbzZ0cThLRWpKQVE2b012bVpYaGM0N0dDc0sxRTZBS2xUSmlMaW9LL2xlLzJMVi9YUVdFN2pRb1JNNWhUVmVWZWl0QnhSTW5wbks0d3lDbkhFckJlQ1ViRFZSTFJyNGFLMWpxbTA9__1573610983414";
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
//		String Password = "yonyou11";
//		String shaPassword = SDKUtils.encodeUsingSHA(Password);
//		String md5Password = SDKUtils.encodeUsingMD5(Password);
//
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("username", "2017101701@chacuo.net");
//		params.put("shaPassword", shaPassword);
//		params.put("md5Password", md5Password);
//
//		String msg = UserCenter.createSSOToken(params);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		String ssotoken = node.get("data").asText();

		// 获取accesstoken--本测试方法需要这个参数
//		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		String accessToken = node1.get("accesstoken").asText();
//
//		// 销毁前，可以获取用户信息
//		String msg2 = UserCenter.getUserByToken(accessToken);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);

		// 本测试方法的代码，销毁
//		String msg3 = UserCenter.destroyAccessToken("amZiYnY5MXktMTU3NDMxNzk5MTQ2MF82MzY4ZmQ0Yi0xNTZhLTQyZGUtYmY5OC0wYWRiMWJmMDFiZmZfaWR0ZXN0Lnl5dWFwLmNvbQ..");
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 1);

		// 销毁后，再获取用户信息应该获取不到
		String msg4 = UserCenter.getUserByToken("amZiYnY5MXktMTU3NDMxNzk5MTQ2MF82MzY4ZmQ0Yi0xNTZhLTQyZGUtYmY5OC0wYWRiMWJmMDFiZmZfaWR0ZXN0Lnl5dWFwLmNvbQ..");
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

		String username = "16703888888";//test0926d@test1988.com
		String Password = "yonyou@1988";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String msg = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
		//		String msg1 = UserCenter.generateAccessToken("2017101701@chacuo.net", "yonyou11", false);
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
		params.put("username", "16703888888");
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
		params.put("username", "16703888888");
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
		params.put("username", "16703888888");
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

		// 获取ssotoken
		String userName = "16703888888";
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
		user2.put("userName", userCode + i + "比利时0615");
		user2.put("user", userCode + i + "@163.com");
		user2.put("userMobileCountrycode", "32");
		user2.put("userMobile", "484888888");

		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> users = new ArrayList<Object>();
		users.add(user1);
		users.add(user2);
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
//		String userCode = "YHT20191108aaaa";
//		String userName = "16703888888";
//		String userEmail = "test1108cc@test1988.com";
//		String userMobile = "19930888888";
		
		//欧盟国家
		String userCode = "YHT20200612aa";
		String userName = "奥地利0613";
		String userMobile = "19940888888";
		String countryCode = "43";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", userCode);
		params.put("userName", userName + "name");
		//params.put("userEmail", userEmail);
		params.put("userMobile", userMobile);
		params.put("userMobileCountrycode", countryCode);
		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}


	@Test
	/*
	 * 登录校验 正常情况的测试--登录接口
	 * 用户名或密码为空、输入错误
	 * sysid-表示用户的注册来源
	 */
	public void verifyUserTest() throws JsonProcessingException, IOException, InterruptedException {

		//String userName = "15210142172";
		//String userName = "16301888888";
		//String userName = "0925a@test1988.com";  //sysid：aps 0925a@test1988.com 用户已注销
		//String userName = "test0909b@test1988.com";
		//String userName = "16703888888";
		String userName = "u8c_system-a462-d5a66e3e6724";//默认用户
		 
		//		String Password = "qwaszx12"; idtest
		String Password = "yonyou@1988";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		//String sysId = "aps";
		String sysId = "yht";
		//登录统一校验
		//1、
		//			System.out.println(msg0);
		// 估计把密码输入错误，让用户锁定
		//		for (int i = 0; i < 6; i++) {
		//					String msg0 = UserCenter.verifyUser(userName, Password, sysId);
		//					System.out.println(msg0);
		//			JsonNode node0 = mapper.readTree(msg0);
		////			Assert.assertTrue(node0.get("status").asInt() == 0);
		//			// Assert.assertTrue(node0.get("msg").asText().equals("用户名或密码错误"));
		//		}
		//2、
		// 锁定用户登录，给友好提示
				String msg = UserCenter.verifyUser(userName, Password, sysId);
				System.out.println(msg);
				JsonNode node = mapper.readTree(msg);
				Assert.assertTrue(node.get("status").asInt() == 0);
		//		Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));
		//3、
		//		String msg1 = UserCenter.verifyUser(userName, Password, sysId, null, null);
		//		System.out.println(msg1);
		//JsonNode node1 = mapper.readTree(msg1);
		//		Assert.assertTrue(node1.get("status").asInt() == 0);
		//		Assert.assertTrue(node1.get("msg").asText().equals("用户被锁定"));
		//4、
		//		String msg2 = UserCenter.verifyUserUsingEncodePwd(userName, md5Password, shaPassword, sysId);
		//		System.out.println(msg2);
		//		JsonNode node2 = mapper.readTree(msg2);
		//		Assert.assertTrue(node2.get("status").asInt() == 0);
		//		Assert.assertTrue(node2.get("msg").asText().equals("用户被锁定"));
		////5、
		//		String msg3 = UserCenter.verifyUserUsingEncodePwd(userName, md5Password, shaPassword, sysId, null, null);
		//		System.out.println(msg3);
		//JsonNode node3 = mapper.readTree(msg3);
		//		Assert.assertTrue(node3.get("status").asInt() == 0);
		//		Assert.assertTrue(node3.get("msg").asText().equals("用户被锁定"));

		// 等待15分钟，用户解锁
		//		Thread.sleep(900000);
		//6、
		//		// 解锁后，登录成功
//		String msg4 = UserCenter.verifyUser(userName, Password, sysId, "aa");
//		System.out.println(msg4);
		//		JsonNode node4 = mapper.readTree(msg4);
		//		Assert.assertTrue(node4.get("status").asInt() == 1);
	}

//	@Test(expected = ArithmeticException.class)
	@Test
	/*
	 * 根据用户ID获取用户信息 正常流程测试
	 *敏感用户用户名正常显示，不显示 *****
	 */
	public  void getUserByIdTest() throws JsonProcessingException, IOException {
		//String userName = "15210142172";//用户民就是姓名、昵称
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//String userId = "c2e6b853-1d2e-47df-af97-bd0aaef6af01";//idtest 17820888888
		//String userId = "954041a5-1ea6-49f6-95fd-f7f5740d7f62";//17701888888
		//		String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";
		//String userId = "7d1fef4b-a33d-4d43-b99e-e99a2c6a1389";//16501888888
		String userId = "72eb07a0-d501-4a5a-9a8f-523ee731a502";// idtest 15210142172
		//String userId = "66a2d80a-36b1-4686-a1e1-43d8388c8d59";// 13439026212
//		String userId = "811dc8fb-f126-46db-b0b0-f0684175802a";// 16703888888
		//String userId = "4eff0eaf-5490-470e-ae9c-4c0f5fba03cf";// 16703888888
		String msg = UserCenter.getUserById(userId);
		System.out.println("根据用户ID获取用户信息"+msg);
		JsonNode node = mapper.readTree(msg);
		//		Assert.assertTrue(node.get("status").asInt() == 1);
//				Assert.assertTrue(node.get("user").get("signPrivacyProtocol").asText().equals("true"));

	}
	@Test
	/*
	 * 根据用户ID签署用户隐私权
	 *  "status" : 1,  已经签收协议
	 */
	public void signPrivacyProtocolTest() throws JsonProcessingException, IOException {
		//String userId = "6e1d9cc0-e944-4268-834a-d556dd838151";//idtest
		//		String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";//euc
		//String userId = "4ef10bce-ee59-4d13-b4c3-730e69cd976c";
		//String userId = "a508eb68-819a-4af6-a2ea-d8c9aa94f595";//16301888888
		String userId = "7ec69833-7bde-40f8-b236-2d35a4ba19f8";//16703888888
		String msg = UserCenter.signPrivacyProtocol(userId);

		System.out.println("根据用户ID签署用户隐私权"+msg);

		JsonNode node = mapper.readTree(msg);
		//		Assert.assertTrue(node.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("user").get("userCode").asText().equals("YHT-706-7261541673334871"));

	}
	@Test
	/*
	 * 根据激活码激活用户 开发把17601888888设置成未激活，并生成一个激活码
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

		//		String msg1 = UserCenter.activeUserByCode("2017092001@chacuo.net", "325195");
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
		String code = "190926";//验证码，忘记密码获取
		String msg = UserCenter.activateUser(userContact, pwd, code);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 *校验是否是nc65用户
	 *用nc65密码登录的是nc65用户
	 *不用nc65密码登录的不是nc65用户
	 * 
	 */
	public void ISNC65Test() throws JsonProcessingException, IOException {
		String msg = NCUserCenter.isNC65("17766776688");
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
      最近5次使用的密码：是从最新的一次往前数5次 
       		1	2	3
1	17971888888/qwaszx12	不支持修改为正在使用的密码"	"不支持修改为最近5次曾使用的密码",	
2	17971888888/qwaszx123	"status" : 1, 修改成功	不支持修改为最近5次曾使用的密码",	
3	17971888888/qwaszx1234	"status" : 1, 修改成功	不支持修改为最近5次曾使用的密码",	
4	17971888888/qwaszx1235	"status" : 1, 修改成功	不支持修改为最近5次曾使用的密码",	
5	17971888888/qwer1234	"status" : 1, 修改成功	不支持修改为最近5次曾使用的密码",	
6	17971888888/qwer12345	"status" : 1, 修改成功	不支持修改为正在使用的密码"	"不支持修改为最近5次曾使用的密码",
7	17971888888/qwer123456	"status" : 1, 修改成功		

	 */
	public void modifypasswordTest() throws JsonProcessingException, IOException, InterruptedException {

		//旧密码错误、新密码不一致，使用过最近5次内的密码
//				String userName = "15210142172";  
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//String userId = "7d1fef4b-a33d-4d43-b99e-e99a2c6a1389";//16501888888
		//String userId = "fdb14be6-8f78-4dd5-836f-9d75056f32c4";//19707888888
		//String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";//15210142172
		//String userId = "fdb14be6-8f78-4dd5-836f-9d75056f32c4";//19707888888
		//		String userId = "9c8cbb1f-efe0-44cc-b022-40c371d7a2b2";//17971888888 
		String userId = "6c685928-36d9-4cc6-b6c3-4af0a48fd2f88";//16703888888 
		// 旧密码
		String Password = "yonyou@1988"; //1-qwaszx12    2-qwaszx123 3-qwaszx1234   4-qwaszx12345
		//5-qwer1234 6-qwer12345 7-qwer123456  8-
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		System.out.println(shaPassword);
		System.out.println(md5Password);
		
		// 新密码
		String Passwordnew = "qwaszx12";
		String shaNewPassword = SDKUtils.encodeUsingSHA(Passwordnew);
		String md5NewPassword = SDKUtils.encodeUsingMD5(Passwordnew);
   

		// 发送验证码
//				String msg11 = UserCenter.sendcode(contact, type, key, code);
//
		String verification = "191108";//手机获取的验证码,例如忘记密码
//			String contact = userName;
//
//		//		
//		// 获取sid
		String msg1 = UserCenter.validateCode("mobile", userId, verification, "");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		//				Assert.assertTrue(node1.get("status").asInt() == 1);
		String sid = node1.get("sid").asText();
		System.out.println(sid);
//		//修改密码
		String msg2 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
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
		//		String contact = "2019011601@test1988.com";
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
	public void modifypasswordTestdd() throws JsonProcessingException, IOException, InterruptedException {

		//旧密码错误、新密码不一致，使用过最近5次内的密码
		String userId = "33d893bb-bc01-43eb-9701-c1f71a650942";
		//19939888888  预发环境 19901888888 33d893bb-bc01-43eb-9701-c1f71a650942
		String operatorId = userId;
		String Password = "yonyou@1988"; //1-qwaszx12    2-qwaszx123 3-qwaszx1234   4-qwaszx12345
		//5-qwer1234 6-qwer12345 7-qwer123456  8-
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);
//		System.out.print(shaPassword);
		System.out.print(md5Password);
		// 新密码
		String Passwordnew = "yonyou11";
		String shaNewPassword = SDKUtils.encodeUsingSHA(Passwordnew);
		String md5NewPassword = SDKUtils.encodeUsingMD5(Passwordnew);


		// 发送验证码
		//		String msg11 = UserCenter.sendcode(contact, type, key, code);

//		String verification = "191213";//手机获取的验证码,例如忘记密码
//		//		String contact = userName;
//
//		//		
//		// 获取sid
//		String msg1 = UserCenter.validateCode("mobile", userId, verification, "");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		//				Assert.assertTrue(node1.get("status").asInt() == 1);
//		String sid = node1.get("sid").asText();
//		System.out.println(sid);
//		//修改密码
//		String msg2 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
//		System.out.println(msg2);
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

		String userName = "16703888888";
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
		//		String code = UserCenter.sendPhoneMessage(userId);//获取手机验证码
		//		System.out.println(code);
		String contact = "16703888888";
		String code = "190926";
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
            true:同步员工信息 flase:不同步员工信息
	 */
	public void updateUserPropertiesTest() throws JsonProcessingException, IOException {
		String userName = "16703888888";//登录账号 
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "d61e7450-cea7-4fd5-b8d1-a0dd4a2c7182";//19937888888
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d = date.format(new Date());

		// 修改昵称  -没有操作人
		//				String key = "userName";
		//String value = d + "name";
		//				String value = "1113test";
		//				// 原来的接口：updateUserProperties(userId, key, value);
		//				String msg = UserCenter.updateUserProperties(userId, key, value, true);
		//				System.out.println(msg);
		//				JsonNode node = mapper.readTree(msg);
		//				Assert.assertTrue(node.get("status").asInt() == 1);
		//				Assert.assertTrue(node.get("msg").asText().equals("修改昵称成功"));

		// 修改性别 -没有记录
		//				String key1 = "sex";
		//				String value1 = "1";
		//				String msg1 = UserCenter.updateUserProperties(userId, key1, value1);
		//				System.out.println(msg1);
		//				JsonNode node1 = mapper.readTree(msg1);
		//				Assert.assertTrue(node1.get("status").asInt() == 1);
		//				Assert.assertTrue(node1.get("msg").asText().equals("修改性别成功"));

		// 修改出生日期 -没有操作人
		//				SimpleDateFormat date2 = new SimpleDateFormat("yyyy年MM月dd日");
		//				String d2 = date.format(new Date());
		//				String key2 = "birthday";
		//				String value2 = "2019年08月08日";
		//				String msg2 = UserCenter.updateUserProperties(userId, key2, value2);
		//				System.out.println(msg2);
		//				JsonNode node2 = mapper.readTree(msg2);
		//				Assert.assertTrue(node2.get("status").asInt() == 1);
		//				Assert.assertTrue(node2.get("msg").asText().equals("修改出生日期成功"));

		// 修改地址-没有操作人
		String key3 = "address";
		String value3 = "1-11-1-";
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

		//修改



	}
	@Test
	public void updateUserPropertiesTestdd() throws JsonProcessingException, IOException {
		String userName = "16703888888";//登录账号 
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "d61e7450-cea7-4fd5-b8d1-a0dd4a2c7182";//19939888888
		String operatorId = userId;
		String operatorName = "1113test";
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d = date.format(new Date());

//		// 修改昵称  -没有操作人
//						String key = "userName";
//		//				String value = d + "name";
//						String value = "1114test";
//						
//						// 原来的接口：updateUserProperties(userId, key, value);
//						String msg = UserCenter.updateUserProperties(userId, key, value, operatorId, operatorName);
//						System.out.println(msg);
//						JsonNode node = mapper.readTree(msg);
//						Assert.assertTrue(node.get("status").asInt() == 1);
//						Assert.assertTrue(node.get("msg").asText().equals("修改昵称成功"));

		// 修改性别 -没有记录
//						String key1 = "sex";
//						String value1 = "0";
//						String msg1 = UserCenter.updateUserProperties(userId, key1, value1,operatorId,operatorName);
//						System.out.println(msg1);
//						JsonNode node1 = mapper.readTree(msg1);
//						Assert.assertTrue(node1.get("status").asInt() == 1);
//						Assert.assertTrue(node1.get("msg").asText().equals("修改性别成功"));

		// 修改出生日期 -没有操作人
//						SimpleDateFormat date2 = new SimpleDateFormat("yyyy年MM月dd日");
//						String d2 = date.format(new Date());
//						String key2 = "birthday";
//						String value2 = "2019年11月14日";
//						String msg2 = UserCenter.updateUserProperties(userId, key2, value2,operatorId,operatorName);
//						System.out.println(msg2);
//						JsonNode node2 = mapper.readTree(msg2);
//						Assert.assertTrue(node2.get("status").asInt() == 1);
//						Assert.assertTrue(node2.get("msg").asText().equals("修改出生日期成功"));

		// 修改地址-没有操作人
//				String key3 = "address";
//				String value3 = "1-22-1-";
//				String msg3 = UserCenter.updateUserProperties(userId, key3, value3,operatorId,operatorName);
//				System.out.println(msg3);
//				JsonNode node3 = mapper.readTree(msg3);
//				Assert.assertTrue(node3.get("status").asInt() == 1);
//				Assert.assertTrue(node3.get("msg").asText().equals("修改地址成功"));
//
//		//		// 修改userCode
//		String key4 = "userCode";
//		String value4 = d + "Code";
//		String msg4 = UserCenter.updateUserProperties(userId, key4, value4,operatorId,operatorName);
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 1);
//		Assert.assertTrue(node4.get("msg").asText().equals("修改userCode成功"));
		//	本接口不支持修改邮编和时区	
		//		//修改邮编 postCode  "postCode":"113",
//						String key5 = "postCode";
//						String value5 = "113";
//						String msg5 = UserCenter.updateUserProperties(userId, key5, value5,operatorId,operatorName);
//						System.out.println(msg5);
//						JsonNode node5 = mapper.readTree(msg5);
//						Assert.assertTrue(node5.get("status").asInt() == 1);
//						Assert.assertTrue(node5.get("msg").asText().equals("修改postCode成功"));
		//				
		//		
		//     	// 修改时区 timeZone
//						String key6 = "timeZone";
//						String value6 = "UTC+11";
//						String msg6 = UserCenter.updateUserProperties(userId, key6, value6,operatorId,operatorName);
//						System.out.println(msg6);
//						JsonNode node6 = mapper.readTree(msg6);
//						Assert.assertTrue(node6.get("status").asInt() == 1);
//						Assert.assertTrue(node6.get("msg").asText().equals("修改timeZone成功"));	
//						

	}

	@Test
	/*
	 * 绑定手机 正常情况的测试
	 * 
	 */

	public void bindMobileTest() throws JsonProcessingException, IOException {
		//		// 绑定手机(国内手机)
		//		String userName = "18611286701";
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//		String userId = "be82aa00-8f53-4fd3-bf86-0cdc9b26a4be";
		String userId = "7ec69833-7bde-40f8-b236-2d35a4ba19f8";//16703888888
		String msg = UserCenter.bindMobile(userId, "16582888888");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 校验绑定后的手机是否正确(因为加密，所以有星号)
		String msg0 = UserCenter.getUserById(userId);
		System.out.println(msg0);
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
	public void bindMobileTestdd() throws JsonProcessingException, IOException {
		//		// 绑定手机(国内手机)
		//		String userName = "18611286701";
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//		String userId = "be82aa00-8f53-4fd3-bf86-0cdc9b26a4be";
		String userId = "d61e7450-cea7-4fd5-b8d1-a0dd4a2c7182";//19937888888
		String operatorId = userId;
		String operatorName = "11136test";
		String msg = UserCenter.bindMobile(userId, "19939888888",operatorId,operatorName);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);



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
		//String userId = "be82aa00-8f53-4fd3-bf86-0cdc9b26a4be";
		String userId = "7ec69833-7bde-40f8-b236-2d35a4ba19f8";//16703888888
		String msg = UserCenter.bindEmail(userId, "test0926a@test1988.com");//修改邮箱新的邮箱可以正常登录
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 校验绑定后的邮箱是否正确(因为加密，所以有星号)
		String msg0 = UserCenter.getUserById(userId);
		System.out.println(msg0);
		JsonNode node0 = mapper.readTree(msg0);
		Assert.assertTrue(node0.get("status").asInt() == 1);
		Assert.assertTrue(node0.get("user").get("userEmail").asText().equals("test041305@test1988.com"));

		//		// 还原邮箱，为下次执行脚本准备
		//		String msg1 = UserCenter.bindEmail(userId, "jlcc@126.com");
		//		// String msg1 = UserCenter.bindEmail(userId, "yixixinzi@126.com");
		//		System.out.println(msg1);
		//		JsonNode node1 = mapper.readTree(msg1);
		//		Assert.assertTrue(node1.get("status").asInt() == 1);

	}
	@Test

	public void bindMailTestdd() throws JsonProcessingException, IOException {
		// 绑定邮箱
		String userId = "d61e7450-cea7-4fd5-b8d1-a0dd4a2c7182";//19937888888
		String operatorId = userId;
		String operatorName = "11136test";
		String msg = UserCenter.bindEmail(userId, "test1113c@test1988.com",operatorId,operatorName);//修改邮箱新的邮箱可以正常登录
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	@Test
	/*
	 * 修改手机号或邮箱 正常情况的测试
	 */
	public void modifyContactTest() throws JsonProcessingException, IOException, InterruptedException {

		// "2018011801@test1988.com"是企业应用授权用户，只有读写功能，不能修改手机号邮箱。ID是"183c1252-b602-43b1-87f2-16b24b4d4a9a"

		// String userId =
		// UserCenterUtil.getUserIdByLoginName("2018011802@test1988.com");
		// String userId ="183c1252-b602-43b1-87f2-16b24b4d4a9a";

		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyMMdd");
		//		String verification = matter1.format(dt);
		String verification = "";//可以通过忘记密码，输入手机号来获取真实的验证码
		String[][] data = {
				{ "2018011801@test1988.com", "183c1252-b602-43b1-87f2-16b24b4d4a9a", "17610888888",
					"st2018011801@test1988.com", "企业应用授权只读用户" },
				{ "18802888888", "34000897-01c3-4a76-832a-54e635e3947b", "17611888888", "s2018011801@test1988.com",
				"nc导入用户" },
				{ "2018011802@test1988.com", "087b526f-392a-4deb-9a06-9cf348fa0307", "17612888888",
					"st2018011802@test1988.com", "可以修改手机号、邮箱用户" },
				{ "还原数据                                     ", "087b526f-392a-4deb-9a06-9cf348fa0307", "17613888888",
						"2018011802@test1988.com", "还原数据" } };

		Thread.sleep(60000);

		for (int i = 0; i < data.length; i++) {

			System.out.println(
					"------------------------------------" + data[i][4] + "-----------------------------------------");

			String userId = data[i][1];
			String Phone = data[i][2];
			String email = data[i][3];
			// 发送验证码
			// String msg = UserCenter.sendEmailMessage(userId);
			// System.out.println(msg);

			// 发邮件
			String msg = UserCenter.sendValidateCode(userId, "email");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
			Assert.assertTrue(node.get("msg").asText().equals("邮件发送成功"));

			// 获取sid
			String msg0 = UserCenter.validateCode("email", userId, verification, "");
			System.out.println(msg0);
			JsonNode node0 = mapper.readTree(msg0);
			Assert.assertTrue(node0.get("status").asInt() == 1);
			String sid = node0.get("sid").asText();
			System.out.println(sid);

			// 修改手机号
			String msg1 = UserCenter.modifyContact(userId, Phone, sid);
			System.out.println(msg1);
			// JsonNode node1 = mapper.readTree(msg1);
			// Assert.assertTrue(node1.get("status").asInt()==1);
			// Assert.assertTrue(node1.get("msg").asText().equals("邮件发送成功"));

			// 发送验证码
			// String msg10 = UserCenter.sendEmailMessage(userId);
			// System.out.println(msg10);

			// 发邮件
			String msg10 = UserCenter.sendValidateCode(userId, "email");
			System.out.println(msg10);
			JsonNode node10 = mapper.readTree(msg);
			Assert.assertTrue(node10.get("status").asInt() == 1);
			Assert.assertTrue(node10.get("msg").asText().equals("邮件发送成功"));

			// 获取sid
			String msg11 = UserCenter.validateCode("email", userId, verification, "");
			System.out.println(msg11);
			JsonNode node11 = mapper.readTree(msg11);
			Assert.assertTrue(node11.get("status").asInt() == 1);
			String sid11 = node11.get("sid").asText();
			System.out.println(sid11);

			// 修改邮箱
			String msg12 = UserCenter.modifyContact(userId, email, sid);
			System.out.println(msg12);
			// JsonNode node2 = mapper.readTree(msg2);
			// Assert.assertTrue(node2.get("status").asInt()==1);
			// Assert.assertTrue(node2.get("msg").asText().equals("短信发送成功"));

		}

	}


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
		//17820888888  c2e6b853-1d2e-47df-af97-bd0aaef6af01 舍弃用户
		//17821888888/test061401@test1988.com    5097a60c-f38f-459b-92e2-bb05841fdb8b 被合并
		String srcuserId = "5097a60c-f38f-459b-92e2-bb05841fdb8b"; //17616888888
		String destuserId = "c2e6b853-1d2e-47df-af97-bd0aaef6af01";
		String mobileuserid = "c2e6b853-1d2e-47df-af97-bd0aaef6af01";
		String emailuserid = "c2e6b853-1d2e-47df-af97-bd0aaef6af01";
		String pswuserid = "qwaszx12";
		//		String sysid = "yht";
		String msg = UserCenter.mergeDoubleUser(srcuserId, destuserId, mobileuserid, emailuserid, pswuserid);

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);;


	}
	@Test
	/*
	 * 根据联系方式及类型查询单个用户列表 正常情况的测试
	 */
	public void getUserByContactTest() throws JsonProcessingException, IOException {
		String[][] users = {  {"16582888888","mobile"} };//，{ "15210142172", "mobile" },{ "651693429@qq.com", "email" },
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
		String[][] users = { { "13621031586" ,"651693429@qq.com"} };//, {"15210142172",""} 16582888888,test0926a@test1988.com
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

	@Test
	/*
	 * YonSuite默认用户token获取接口 正常情况的测试
	 * tenantId：
	 * orgId：可以随意传不验证
	 * 接口返回token的有效期，有效期内重复调用接口返回相同的token相当于续期，如果过了有效期后调用接口，返回新的token
	 */
	public void getU8cSystemAccessTokenTest() throws JsonProcessingException, IOException {

		String tenantId = "c7rf65vc"; //测试环境租户id :cpmi9gbr  预发环境 租户id：c7rf65vc
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

		String[][] users = { { "cac3b9f8-f7f0-455f-bf44-7b899865271d", "2017080201@chacuo.net" },
				{ "273ccd32-749c-451c-8784-9d9dff3c3160", "2017080202@chacuo.net" },
				{ "c22847e3-9d9c-4f8c-8fac-47fa0975220e", "2017080701@chacuo.net" },
				{ "07576a6f-235f-4f6f-8823-17742ef86f8d", "2017081401@chacuo.net" },
				{ "c4ac871c-52ad-4a48-a63e-c12eedb5628c", "2017081401@chacuo.net" },
				{ "37fbe5c9-7a95-423c-b070-401febadb02d", "2017081601@chacuo.net" },
				{ "5ad292d7-fa00-468b-bff4-83dbf96bc94e", "2017081602@chacuo.net" },
				{ "6c6888bb-71b1-4198-843a-c29e4f1235b7", "2017081603@chacuo.net" },
				{ "65bae085-1eee-47c9-8ee5-2d81889935fe", "2017081604@chacuo.net" },
				{ "9964da9d-83af-4b91-81b0-46d245f8f699", "2017081701@chacuo.net" },
				{ "b2830637-035b-4885-bb95-b576bb312e8b", "2017081801@chacuo.net" },
				{ "0f4a4cde-8e15-414b-ad86-a14509ad8dad", "2017082101@chacuo.net" },
				{ "3531cf1f-e6ed-4e08-81dc-d5da90dde699", "2017082201@chacuo.net" },
				{ "868d2718-4723-4504-aa03-a773918c2fdb", "suntt@yonyou.com" },
				{ "555efe32-1447-4f86-96cc-a972d440ea0b", "suntt1026@126.com" },
				{ "c1265234-fd39-4124-b00b-dcad33b6a3c2", "851207@126.com" },
				{ "afd48191-a519-486d-b60a-dedb0a163f1b", "851207@163.com" },
				{ "488e6137-e684-4d40-86ea-a619d43c5a50", "18611286701" } };
		String[] pks = new String[users.length];
		for (int i = 0; i < users.length; i++) {
			pks[i] = users[i][0];
		}
		String userName = "201708";
		String msg = UserCenter.searchUser(pks, userName);
		System.out.println(msg);

		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 16);

	}
	//1、封禁接口调用样例
	@Test
	public void testManagerBan(){
		Map<String,String> data = new HashMap<String,String>();
		// a508eb68-819a-4af6-a2ea-d8c9aa94f595  16301888888
		//	      String userId ="637a188a-5edb-44d4-b38c-b48909366e1d";
		//	      String mobile ="15890832158";
		////	      String email ="656897109sdfs@qq.com";
		//	      String userId ="a508eb68-819a-4af6-a2ea-d8c9aa94f595";
		//	      String mobile ="15890832158";
		//	      String email ="656897109sdfs@qq.com";
		//	      String userId ="122333333";
		//	      String mobile ="16301888888";
		//String email ="";

		//异常测试-参数为空或是输入的参数
		String mobile ="";
		String email ="";
		String userId ="cd98db00-b1dc-4cd1-bc94-d56c12db5ee6";
		//cd98db00-b1dc-4cd1-bc94-d56c12db5ee6 cc33f49f-a513-49bb-abdb-5b6d6d685299
		data.put("userId", userId);
		data.put("mobile", mobile);
		data.put("email", email);
		String managerBaseUrl="http://idtest-manager.test.app.yyuap.com/yht-manager";
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
	@Test
	/*
	 * 获取应用authfile信息接口 正常情况的测试
		 sysId String 应用名称
		 小助手提交应用授权申请的应用
		 授权应用-只有通过这个应用授权的应用才可以获取授权文件
	 */
	public void getAuthFileBySysIdTest() throws JsonProcessingException, IOException {
		String sysId = "bigfusion"; //控制台添加的应用提示 --当前用户没有在融合助手提交应用授权申请"
		//String sysId = "NCCICBCSaaSTR"; //助手申请的应用，未审核
		//String sysId = "test0909a";//小助手申请的应用，已审核
		//String sysId = null; //小
		//String sysId = "NCCICBCSaaSTR";
		String  msg = UserCenter.getAuthFileBySysId(sysId);
		JsonNode node1 = mapper.readTree(msg);
		System.out.println(node1);


	}

	//1.1.3根据登录名模糊查询用户列表
	@Test
	/*
	 * 据登录名模糊查询用户列表（支持分页） 正常情况的测试
	 * name,	String	用户名（可以是帐号，用户邮箱或者手
	         机号），必须是数字或字母开头
	      pageSize	String	页面大小
	      pageNumber	String	第几页
	      sortType	String	排序值，默认值为“auto”,此时按“userCode”字段排序，当值为“name”时按“userName”字段排序，不支持其他字段的排
	序
	 */
	public void isUserListExistTest() throws JsonProcessingException, IOException {

		//			
		//			//1.读取Excel文档对象
		//	        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream("/Users/juandu/Desktop/用户模板.xls"));
		//	        //2.获取要解析的表格（第一个表格）
		//	        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		//	        //获得最后一行的行号
		//	        int lastRowNum = sheet.getLastRowNum();
		//	        List<SimpleUserInfo> userList = new ArrayList<SimpleUserInfo>();
		//	        for (int i = 1; i <= lastRowNum; i++) {//遍历每一行
		//	            //3.获得要解析的行
		//	            HSSFRow row = sheet.getRow(i);
		//	        	   
		////	        	   HSSFCell cell = sheet.getRow(i).getCell(j);
		//	        	     String nameString =  new String();
		//					 String emailString =  new String();
		//					 String phoneString =  new String();
		//					 String userIdString = new String();
		//	        	     nameString =  sheet.getRow(i).getCell(0).toString();
		//	        	     userIdString = sheet.getRow(i).getCell(1).toString();
		//	        	     phoneString = sheet.getRow(i).getCell(2).toString();
		//	        	     System.out.println(phoneString);
		//	        	     emailString = sheet.getRow(i).getCell(3).toString();
		//	        	     
		////                   if (cell != null) {
		////                       System.out.println(cell);
		//                       
		//                        userList.add(new SimpleUserInfo(nameString,userIdString,phoneString,emailString));
		//                        
		////                   }
		//				
		////			 }

		//	        }
		//	       
		//
		//			for(int i = 0 ; i < userList.size() ; i++) {
		//				System.out.println("一共"+userList.get(i)+"用户");
		//				}
		//			
		//			String msg = UserCenter.isUserListExist(userList);
		//			



		//			List<SimpleUserInfo> userList = new ArrayList<SimpleUserInfo>();
		//			
		//			for(Integer i = 0;i<101;i+=10) {
		//				 String rowString =String.valueOf(i);
		//				 String nameString =  new String();
		//				 String emailString =  new String();
		//				 String phoneString =  new String();
		//				 String userIdString = new String();
		////				  for(Integer j = 0;i<1001;i++) {
		//					  SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//						String t = date.format(new Date());
		//						String sysCustomerId = t;
		//						String name = "测试01dd科技有限公司" + t;
		//						nameString = name;
		//						
		//						String email = t+"@test1988.com";
		//						emailString = email;
		//						
		//						String phone = "1791"+i.toString() + "888888";
		//						phoneString = phone;
		//						
		//						String userId = "YHT-1295508-129596215230944048"+i.toString();
		//						userIdString = userId;
		////				   }   
		//				 
		//				 userList.add(new SimpleUserInfo(rowString,userIdString,null,null));
		//				 userList.add(new SimpleUserInfo(rowString+1,userIdString,phoneString,null));
		//				 userList.add(new SimpleUserInfo(rowString+2,userIdString,null,emailString));
		//				 userList.add(new SimpleUserInfo(rowString+3,userIdString,phoneString,emailString));
		//				 userList.add(new SimpleUserInfo(rowString+4,userIdString,phoneString,emailString));
		//				 userList.add(new SimpleUserInfo(rowString+5,null,phoneString,null));
		//				 userList.add(new SimpleUserInfo(rowString+6,null,null,emailString));
		//				 userList.add(new SimpleUserInfo(rowString+7,userIdString,null,emailString));
		//				 userList.add(new SimpleUserInfo(rowString+8,null,phoneString,null));
		//				 userList.add(new SimpleUserInfo(rowString+9,null,null,emailString));
		//				 userList.add(new SimpleUserInfo(rowString+10,null,null,null));
		//			 }
		//			
		//			for(int i = 0 ; i < userList.size() ; i++) {
		//				System.out.println("一共"+userList.get(i)+"用户");
		//				}
		//			
		//			String msg = UserCenter.isUserListExist(userList);
		//		
		//			System.out.println("据登录名模糊查询用户列表（支持分页） "+msg);
		//			JsonNode node = mapper.readTree(msg);
		//			Assert.assertTrue(node.get("status").asInt() == 1);
		//		 
		//		}

		try (FileInputStream is = new FileInputStream("/Users/juandu/Desktop/用户模板.xls");
				Workbook book = new HSSFWorkbook(is);) {
			Sheet sheet = book.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			List<SimpleUserInfo> userList = new ArrayList<SimpleUserInfo>();

			for (int i = 1; i <= lastRowNum; i++) {//遍历每一行
				//3.获得要解析的行

				String nameString = new String();
				String emailString = new String();
				String phoneString = new String();
				String userIdString = new String();
				nameString = getExcelString(sheet, i, 0);
				userIdString = getExcelString(sheet, i, 1);
				phoneString = getExcelString(sheet, i, 2);
				emailString = getExcelString(sheet, i, 3);
				userList.add(new SimpleUserInfo(nameString, userIdString, phoneString, emailString));
			}

			System.out.println("一共" + userList.size() + "用户");

			String msg = com.yonyou.yht.sdk.UserCenter.isUserListExist(userList);
			System.out.println(msg);
		} catch (IOException e) {
			System.out.println("出错了" + e.getMessage());
		}
	}
	private String getExcelString(Sheet sheet, int i, int j) {
		String str = sheet.getRow(i).getCell(j).toString();
		return "null".equals(str.trim()) ? null : str;
	}
	public void loginWithThirdCodeTest() throws JsonProcessingException, IOException {
//		String sysId = "bigfusion"; //控制台添加的应用提示 --当前用户没有在融合助手提交应用授权申请"
//	     
//		String  msg = UserCenter.loginWithThirdCode(code, type);


	}
	
	@Test
	/**
	 * 绑定友户通用户-sdk无接口
	 * 
	 * 第三方类型枚举：WECHAT_APPLET（微信小程序）, DINGDING_APPLET（钉钉小程序），USPACE_APPLET(友空间小程序)
	 * 
	 * */
	public void bindYhtUserTest() throws JsonProcessingException, IOException {
//		String sysId = "bigfusion"; //控制台添加的应用提示 --当前用户没有在融合助手提交应用授权申请"
//	     

//        String userId = "b33974ec-2967-484a-bb2a-07726f258249";
//        String type = "USPACE_APPLET";
//        String tenantId = "";
//        String clientId = "27";
//		String  msg = UserCenter.getOpenIdByYhtUserId(userId, type, tenantId, clientId);
//
//		System.out.println(msg);
		
	}
	@Test
	/**
	 *解除用户绑定-sdk无接口
	 * 第三方类型枚举：WECHAT_APPLET（微信小程序）, DINGDING_APPLET（钉钉小程序），USPACE_APPLET(友空间小程序)
	 * 
	 * */
	public void unbindYhtUserTest() throws JsonProcessingException, IOException {
//		String sysId = "bigfusion"; //控制台添加的应用提示 --当前用户没有在融合助手提交应用授权申请"
//	     

        String userId = "b33974ec-2967-484a-bb2a-07726f258249";
        String type = "USPACE_APPLET";
        String tenantId = "";
        String clientId = "27";
		String  msg = UserCenter.getOpenIdByYhtUserId(userId, type, tenantId, clientId);

		System.out.println(msg);
		
	}

	@Test
	/**
	 * 根据友户通用户ID获取OpenID接口
	 * 第三方类型枚举：WECHAT_APPLET（微信小程序）, DINGDING_APPLET（钉钉小程序），USPACE_APPLET(友空间小程序)
	 * 
	 * */
	public void getOpenIdByYhtUserIdTest() throws JsonProcessingException, IOException {
//		String sysId = "bigfusion"; //控制台添加的应用提示 --当前用户没有在融合助手提交应用授权申请"
//	     

//		InvocationInfoProxy.setLocale("zh_CN"); //接口简体中文

//		InvocationInfoProxy.setLocale("zh_TW");//接口繁体
//		
		InvocationInfoProxy.setLocale("en");//接口英文
        String userId = "6c685928-36d9-4cc6-b6c3-4af0a48fd2f8";//6c685928-36d9-4cc6-b6c3-4af0a48fd2f8
        //b33974ec-2967-484a-bb2a-07726f258249  19940888888
        String type = "WECHAT_APPLET";//USPACE_APPLET  WECHAT_APPLET
        String tenantId = "";
        String clientId = "27";
		String  msg = UserCenter.getOpenIdByYhtUserId(userId, type, tenantId, clientId);

		System.out.println(msg);
		
	}
	@Test
	/**
	 * 获取u8c系统用户的token
	 * 
	 * */
	public void getU8cSystemUserAccessTokenTest() throws JsonProcessingException, IOException {

//		InvocationInfoProxy.setLocale("zh_CN"); //接口简体中文

//		InvocationInfoProxy.setLocale("zh_TW");//接口繁体
//		
		InvocationInfoProxy.setLocale("en");//接口英文
       
        String tenantId = "21212";
        String orgId = "1212121";
		String  msg = UserCenter.getU8cSystemAccessToken(tenantId, orgId);

		System.out.println(msg);
		
	}
	@BeforeClass
	public static void beforeTest() {
		System.out.println("hahahhahahhahah");
		
	}
	@Test
	/**
	 * 获取国家编码
	 * 
	 * */
	public void getCountryCodeTest() throws JsonProcessingException, IOException {

//		InvocationInfoProxy.setLocale("zh_CN"); //接口简体中文

//		InvocationInfoProxy.setLocale("zh_TW");//接口繁体
		
		InvocationInfoProxy.setLocale("en");//接口英文
       
        String locale = "qw";
       
		String  msg = UserCenter.getCountrycode(locale);

		System.out.println(msg);
		
	}
	
	//新增YS类型
	//getThirdLoginCode
	@Test
	public void getThirdLoginCodeTest() throws JsonProcessingException,IOException{
//		
		OutsideUser user = new OutsideUser();
        user.setThirdUcId("ygkbid2c");//thirdUcId，唯一的  xnmbwslf(ddTest'0526) kf3pajz6 
        //u8cdaily环境 thirdUcId
        //线上环境 ygkbid2c  
        user.setUserId("1002");
//        user.setMobile("17801123456");
        user.setEmail("test061602@test1988.com");
        user.setUserCode("qqe");
        user.setUserName("0523aa");//username还是按照新用户处理
		String msg = UserCenter.getThirdLoginCode(user);
		System.out.println(msg); 
		
	}
	
	
}


