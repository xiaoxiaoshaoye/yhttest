package com.yonyou.yht.sdk;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.yht.sdk.NCUserCenter;

public class UserCenterTest0527Line {
	ObjectMapper mapper = new ObjectMapper();


	@Test
	/*
	 * NCUserCenter.createNcProject(userId,
				data.toJSONString());
	 * 给NC设置默网关
	 * maName 项目名称 必选
	 * maId 项目ID 必选，字符串
	 * maUrl 可选
	 * ncIp 网关地址 必选
	 * dsname nc数据源 必选
	 * accountCode nc账套 如01 可选
	 * ncVersion NC5或者NC6 必选
	 * mobileList 管理员手机号列表  分隔符 可选
	 * emailList 管理员邮箱列表  分隔符 可选
	 * tenantId 关联的租户 可选
	 * 
	 * 
	 */

	public void createNcProjecTest() throws JsonProcessingException, IOException {


		Map<String, Object> user1 = new HashMap<String, Object>();
		user1.put("maName", "matest");
		user1.put("maId", "12");
		user1.put("maUrl", "http://172.20.3.22");
		user1.put("esbIp", "172.20.3.22");
		user1.put("dsName", "gold");
		user1.put("accountCode", "01");
		user1.put("ncVersion", null);
		user1.put("mobileList", null);
		user1.put("emailList", null);
		user1.put("tenantId", "fcsfnljk");
		user1.put("defaultMa", false);
		String userId = "03a1a6c1-c653-4b68-8090-8620a858577a";

		// 给NC设置默网关
		String msg0 = NCUserCenter.createNcProject(userId, JSONObject.toJSONString(user1));
		System.out.println("给NC设置默网关"+msg0);
		JsonNode node0 = mapper.readTree(msg0);
		//		Assert.assertTrue(node0.get("status").asInt() == 1);
		//		Assert.assertTrue(node0.get("user").get("userEmail").asText().equals("test041305@test1988.com"));

		//		

	}

	@Test
	/*
	 * 绑定手机 正常情况的测试
	 */

	public void bindMobileTest() throws JsonProcessingException, IOException {
		//		// 绑定手机(国内手机)
		//		String userName = "18611286701";
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "03a1a6c1-c653-4b68-8090-8620a858577a";
		String msg = UserCenter.bindMobile(userId, "17903888888");
		System.out.println("绑定手机"+msg);
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
		//17901888888
	
	}

	@Test
	/*
	 * 绑定邮箱 正常情况的测试 sdk.properties里
	 * client.credential.path=uculture.properties--可以执行这个用户
	 * client.credential.path=authfileyht.txt--不允许执行，会报400
	 */

	public void bindMailTest() throws JsonProcessingException, IOException {
		// 绑定邮箱	
		//		String userId = "89343fe8-cd2f-4c63-bc20-5a2532533693";
		String userId = "03a1a6c1-c653-4b68-8090-8620a858577a";
		String msg = UserCenter.bindEmail(userId,"test190530@test1988.com");//修改邮箱新的邮箱可以正常登录
		System.out.println("绑定邮箱"+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 校验绑定后的邮箱是否正确(因为加密，所以有星号)
//		String msg0 = UserCenter.getUserById(userId);
//		System.out.println(msg0);
//		JsonNode node0 = mapper.readTree(msg0);
//		Assert.assertTrue(node0.get("status").asInt() == 1);
//		Assert.assertTrue(node0.get("user").get("userEmail").asText().equals("test041305@test1988.com"));


	}

	//    /rest/user/signPrivacyProtocol

	/***
	    * 根据友户通userid和租户id获取NC用户信息
	 * 
	 * userId 用户id 
	 * tenantId 租户id
	 * 如果maId不传，取默认网关maId
	 */

	@Test
	public void  getNCInfoByUserIdAndTenantIdTest() throws JsonProcessingException, IOException {

		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));
		//idtest
		//		String userid = "9813276c-c399-4f93-bb99-4da2a953533c";
		//		String tenantId = "ppt16e6o";
		//euc
		//		String userid = "914fbb03-e01d-4def-a2ce-7e84393eec33";
		//		String tenantId = "hc6h4yne";
		//		String  maId = "";

		String tenantId= "up09w179";
		String userId = "a3e79a9e-e287-4c4c-882c-b2faf0c703b1";//17601888888
		//		String maId = "1zQp4_M-4lFbTg_B3mGCVN";
		String maId = "4InAp_ipAoVa1v4NxTL1gB";
//		String msg = UserCenter.getNCInfoByUserIdAndTenantId(userId, tenantId);//旧接口
				String msg1 =  UserCenter.getNCInfoByUserIdAndTenantIdWithMaID(userId, tenantId, maId);//新接口

		System.out.println("根据友户通userid和租户id获取NC用户信息"+msg1);
		//		System.out.println(msg1);
		JsonNode node = mapper.readTree(msg1);
		Assert.assertTrue(node.get("status").asInt() == 1);
		//		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}

	/*
	根据ncuid和租户编码tenantCode获取友户通userid和tenantid
	String getYhtUserIdAndTenantId(String ncuid, String tenantCode)
	与的关系，所有条件满足才可以查询出结果
	 */

	@Test
	public void  getYhtUserIdAndTenantIdTest() throws JsonProcessingException, IOException {

		

		String tenantCode = "76ba4f35-42fd-45a8-9b44-87f1553cb7fb";

		//		String maId = "1zQp4_M-4lFbTg_B3mGCVN";
		String maId = "4InAp_ipAoVa1v4NxTL1gB";
		String ncUserId = "1001B110000000001SE1";

		String msg = UserCenter.getYhtUserIdAndTenantId(ncUserId, tenantCode);//原来的接口

		String msg1 = UserCenter.getYhtUserIdAndTenantIdWithMaId(ncUserId, tenantCode,maId);//新接口

		//		System.out.println("根据ncuid和租户编码tenantCode获取友户通userid和tenantid"+msg);
		System.out.println("根据ncuid和租户编码tenantCode获取友户通userid和tenantid"+msg1);
		JsonNode node = mapper.readTree(msg1);
		Assert.assertTrue(node.get("status").asInt() == 1);


	}



	/*
	根据ncusercode、租户编码tenantCode、网关maId 获取友户通userid和tenantid接口
	String getYhtUserIdAndTenantIdByNCUserCode(String ncusercode, String tenantCode, String maId)
	修改需求：支持maId不传情况取默认网关maId
	与原接口相同不传maId时使用默认ma，返回值与以前相同
	 */
	@Test
	public void  getYhtUserIdAndTenantIdByNCUserCodeTest() throws JsonProcessingException, IOException {

		//		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));
		String tenantCode = "76ba4f35-42fd-45a8-9b44-87f1553cb7fb";
		String ncUserCode = "02404";
		//		String maId = "1zQp4_M-4lFbTg_B3mGCVN";
		String maId = "4InAp_ipAoVa1v4NxTL1gB";
		String msg = UserCenter.getYhtUserIdAndTenantIdByNCUserCode(ncUserCode, tenantCode, maId);

		System.out.println("根据ncusercode、租户编码tenantCode、网关maId 获取友户通userid和tenantid"+msg);
		JsonNode node = mapper.readTree(msg);
		//		System.out.println("********"+node);
		//		System.out.println("********"+node.get("status").asInt());
		Assert.assertTrue(node.get("status").asInt() == 1);
		System.out.println(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd :HH:mm:ss"));

	}




	@Test
	/*
	 * 15、修改密码 正常情况的测试 因为需要发送验证码，次用例每次执行都要重新获ts
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
		String userName = "17601888888";
		String userId = "17bd202b-ae14-49a7-bc63-62b7ee7df978";
		// 旧密码
		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		// 新密码
		String Passwordnew = "qwaszx12";
		String shaNewPassword = SDKUtils.encodeUsingSHA(Passwordnew);
		String md5NewPassword = SDKUtils.encodeUsingMD5(Passwordnew);
		String verification = "190530";//根据填写的手机获取验证码,例如忘记密码,
		String contact = userName;
		// 获取sid
		String msg1 = UserCenter.validateCode("mobile", userId, verification, "");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String sid = node1.get("sid").asText();
		System.out.println(sid);


		//		String node1 = UserCenter.validateCode("mobile", userId, code, contact);
		//		String sid = node1.get("sid").asText();
		//		System.out.println("dis+++++"+sid);


		//修改密码
		String msg2 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
		System.out.println("修改密码"+msg2);
		//		JsonNode node2 = mapper.readTree(msg2);



	}


	@Test
	/*
	 * 通过accessToken获取nccRefreshToken sha值加签
	 * accessToken:可以为空
	 * tenantId
       resCode
	 */
	public void getNCCRefreshTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String username = "17901888888";
		String Password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String tenantId = "l2k2qvpr";//l2k2qvpr ，pn8uaxpu
		String resCode = "NCC";

		//		String accessToken = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
		//		System.out.println(accessToken);
		String msg = UserCenter.createAccessToken("api", "18800114772", "gzq963852741", 60 + "");
		JsonNode node1 = mapper.readTree(msg);
		String accessToken = node1.get("data").get("access_token").asText();
		System.out.println(accessToken);
		String msg1 = UserCenter.getNCCRefreshToken(accessToken, tenantId, resCode);
		System.out.println("通过accessToken获取nccRefreshToken"+msg1);
		JsonNode node2 = mapper.readTree(msg1);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		//		

	}

	/*
	 * 12.	登录校验接口
	 * 
	 * public static String verifyUser(String userName, String userPassword, String systemId)
	（明文）
	public static String verifyUserUsingEncodePwd(String userName, String md5Password, String shaPassword,String sysId);
	（密文）
	 * userName	String	登录名（必填）
	       md5Password	String	md5加密后的密码，并且用cas RSA公钥加密(如果传入norsa参数，则不用rsa加密）（必填）
	       shaPassword	String	sha-1加密后的密码，并且用cas RSA公钥加密(如果传入norsa参数，则不用rsa加密）（必填）
	       sysId	String	应用编码，标识应用的（必填）
	       clientId	String	客户端id，可选（非必填）
	       clientSecretSha	String	client_secret sha-1加密后HEX编码，（非必填）
	       tid	String	设备号，可选（非必填）
	 */
	public void verifyUserTest() throws JsonProcessingException, IOException, InterruptedException {

		// String userName="stt2018030101@test1988.com";
		// String Password="weakp123";
		// String shaPassword=SDKUtils.encodeUsingSHA(Password);
		// String md5Password=SDKUtils.encodeUsingMD5(Password);
		// String sysId="yht";

		String userName = "15210142172";
		//		String Password = "qwer4321";
		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String sysId = "yht";


		//		String msg4 = UserCenter.verifyUser(userName, Password, sysId, "aa");
		String msg4 = UserCenter.verifyUser(userName, Password, sysId);
		System.out.println("登录校验"+msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 6.	批量增加用户（最多50个） 
	 * 正常情况的测试
	 */
	public void addUsersTest() throws JsonProcessingException, IOException {

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode = date.format(new Date());
		System.out.println(userCode);
		String systemCode = "aps";

		//增加51，应该增加不上
		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> users = new ArrayList<Object>();
		for(int i=0;i<51;i++){			
			Map<String, String> user = new HashMap<String, String>();
			//			user.put("userCode", userCode);
			user.put("userName", userCode + i + "name");
			user.put("userEmail", userCode + i + "@test1989.com");
			users.add(user);
		}		
		params.put("users", users);
		String jsonStr = Utils.getJsonStr(mapper, params);
		System.out.println(jsonStr);

		String msg = UserCenter.addUsers(systemCode, jsonStr);
		System.out.println("批量增加用户（最多50个）"+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("每次最多添加50个用户"));

	}

	@Test
	/*
	 * 增加单个用户 -正常情况测试
	 */

	public void addUserTest() throws JsonProcessingException, IOException {


		//		for(int i=0;i<3;i++){
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode = date.format(new Date());
		System.out.println(userCode);
		//		

		params.put("userCode", "052222"+"code");
		params.put("userName", "05222"+"name");
		params.put("userMobile", "052222");
		String msg = UserCenter.addUser(params);
		System.out.println("增加单个用户"+msg);
		JsonNode node = mapper.readTree(msg);
		//			Assert.assertTrue(node.get("status").asInt() == 1);
		//		}
	}


	@Test
	/*
	 * 根据激活码激活用户 开发把用户设置成未激活，并生成一个激活码
	 * 
	 */
	public void activeUserByCodeTest() throws JsonProcessingException, IOException {

		String msg = UserCenter.activeUserByCode("17901888888", "784814");
		System.out.println("根据激活码激活用户"+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		//		String msg1 = UserCenter.activeUserByCode("stt2017092001@chacuo.net", "325195");
		//		System.out.println(msg1);
		//		JsonNode node1 = mapper.readTree(msg1);
		//		Assert.assertTrue(node1.get("status").asInt() == 1);
	}

	
	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的--参数多的
	 */
	public void generateAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String username = "17901888888";
		String Password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);

		String msg = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		//String msg1 = UserCenter.createAccessToken(arg0, arg1, arg2, arg3)

		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
    
	@Test
	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的--参数少的
	 */
	public void generateAccessTokenTest01() throws JsonProcessingException, IOException, InterruptedException {

		String username = "17901888888";
		String password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(password);
		String shaPassword = SDKUtils.encodeUsingSHA(password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);
		String msg = UserCenter.generateAccessToken(username, password, true);
		//		String msg1 = UserCenter.createAccessToken(arg0, arg1, arg2, arg3)

		System.out.println("根据密码获取accesstoken"+msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
	}



	@Test
	/*
	  * 根据accesstoken获取临时token 正常情况的测试
	 */
	public void genTokenByAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String username = "17901888888";
		String Password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		//		System.out.println(md5Password);
		//		System.out.println(shaPassword);

		String accessToken = UserCenter.generateAccessToken(null,username, md5Password, shaPassword,null, false);
		System.out.println(accessToken);
		String msg = UserCenter.genTokenByAccessToken(accessToken);
		System.out.println("根据accesstoken获取临时token"+msg);
//		System.out.println("获取access_token、refresh_token:" + msg);
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
	 * 检测AccessToken是否过期 正常情况的测试
	 */
	public void isAccessTokenValidTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取access_token、refresh_token

		String username = "17901888888";
		String Password = "qwaszx12";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String msg = UserCenter.generateAccessToken(username, md5Password, shaPassword, false);
		//		String msg = UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
//		System.out.println("获取access_token、refresh_token:" + msg);
		JsonNode node1 = mapper.readTree(msg);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String accessToken = node1.get("data").get("access_token").asText();

		boolean msg1 = UserCenter.isAccessTokenValid(accessToken);
		System.out.println(" 检测AccessToken是否过期 "+msg1);
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
		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "17901888888");
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

		// 根据accesstoken获取用户信息
		String msg2 = UserCenter.getUserByToken(accessToken);
		System.out.println("根据accesstoken获取用户信息"+msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据用户名密码创建ssotoken(uclient) 正常情况的测试
	 */
	public void createSSOTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		String Password = "qwaszx12";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "17901888888");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg1 = UserCenter.createSSOToken(params);
		System.out.println("根据用户名密码创建ssotoken"+msg1);
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
		params.put("username", "17901888888");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String ssotoken = node.get("data").asText();

		// 根据ssotoken获取accessToken
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println("根据ssotoken获取accessToken"+msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
	}

	
	@Test
	/*
	 * 根据验证码获取accessToken(uclient) 正常情况的测试
	 * generateAccessTokenByValidateCode(String userName, String validateCode)

	 */
	public void generateAccessTokenByValidateCodeTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取ssotoke年
		String userName = "17901888888";
		String validateCode = "753625";

		String msg = UserCenter.generateAccessTokenByValidateCode(userName, validateCode, false);
		System.out.println("根据验证码获取accessToken"+msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/*
	 * 创建accessToken
	 * 
	 */
	public void createAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

//		String username = "17901888888";
//		String Password = "qwaszx12";
//		String md5Password = SDKUtils.encodeUsingMD5(Password);
//		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String tenantId = "l2k2qvpr";//l2k2qvpr ，pn8uaxpu
		String resCode = "NCC";

		String msg = UserCenter.createAccessToken("api", "", "gzq963852741", 60 + "");
		JsonNode node1 = mapper.readTree(msg);
		String accessToken = node1.get("data").get("access_token").asText();
//		System.out.println("创建accessToken"+accessToken);
		String msg1 = UserCenter.getNCCRefreshToken(accessToken, tenantId, resCode);
		System.out.println("创建accessToken"+msg1);
		JsonNode node2 = mapper.readTree(msg1);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		//		

	}

}
