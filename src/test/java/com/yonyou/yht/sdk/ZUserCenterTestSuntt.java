package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.yht.sdkutils.PropertyUtil;

public class ZUserCenterTestSuntt {
	ObjectMapper mapper= new ObjectMapper();
	

@Before
	public void  beforeZIdtest(){
	  	System.setProperty("yht.load.order","2");
		String path="zidtestsdk.properties";
	//	String authfile="market.properties";
		String authfile="uculture.properties";
		String oauthfile="oauth2_dd.properties";
		Properties p = PropertyUtil.loadFile(path);
		Properties p2 = PropertyUtil.loadFile(authfile);
		Properties p3 = PropertyUtil.loadFile(oauthfile);
		setEnv(p);
		setEnv(p2);
		setEnv(p3);
		System.out.println("#############finished before");
	}

	private void setEnv(Properties p) {
		Enumeration e = p.propertyNames();
		String name=null;
		while(e.hasMoreElements()){
			name=(String)e.nextElement();
			System.setProperty(name,p.getProperty(name));
		}
	}

	
	@Test
	/* 
	 * 根据登录名判断用户是否存在
	 * 正常情况的测试
	 * 登录名输入分别使用userCode、userMobile、userEmail并且能查出数据的正确的值；输入一个不存在的值。
	 * 存在的值需要满足能查出一个用户和多个用户两种情况
	 * 这样满足参数是 登录名(userCode或userMobile或userEmail)这三种情况
	 * 返回值是 flag	String    0，表示用户不存在       1，用户存在且有一个用户      2，用户存在且存在多个用户
	*/
	public void isUserExistTest() {
		String [] loginName={"YHT-2017081401-code","18611286701","jlccstt@126.com","随便乱输入的内容haha"};
		for(int j=0;j<4;j++){
		String msg = UserCenter.isUserExist(loginName[j]);
		System.out.println(msg);
		}
	}

	@Test
	/* 
	 * 根据登录名获取用户信息
	 * 正常情况的测试
	 * 登录名输入正确的值，分别测试code、邮箱、手机号三种情况
	*/
	public void getUserByLoginNameTest() {
		String [] LoginName={"YHT-18810039018","suntt@yonyou.com","18810039018"};
		for(int i=0;i<LoginName.length;i++){
			String msg = UserCenter.getUserByLoginName(LoginName[i]);
			System.out.println(msg);
			JsonNode node = Utils.getJson(mapper, msg);
			Assert.assertTrue(node.get("user").get("userCode").asText().equals("YHT-18810039018"));
			Assert.assertTrue(node.get("user").get("userName").asText().equals("孙婷婷"));
		}
		
	}
	
	@Test
	/* 根据用户ID获取用户信息
	 * 正常流程测试
	*/
	public void getUserByIdTest() throws JsonProcessingException, IOException{

		String msg1=UserCenter.generateAccessToken("18810039018", "yonyou11", false);
		System.out.println(msg1);
		
		String user1="suntt@yonyou.com";
		String user2="jlccstt@163.com";
		String userId1=UserCenterUtil.getUserIdByLoginName(user1);
		
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=UserCenter.getUserById(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("user").get("userCode").asText().equals("YHT-18810039018"));
		
	}
	
	
	@Test
	/* 
	 * 根据联系方式及类型查询用户列表
	 * 正常情况的测试
	*/
	public void getUserByContactTest() throws JsonProcessingException, IOException {
		String [][] users={{"18611286701","mobile"},{"yixixinzi@126.com","email"}};
		for(int j=0;j<users.length;j++){
		String msg = UserCenter.getUserByContact(users[j][0],users[j][1]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
		Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
		Assert.assertTrue(node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));
		
		}
	}
	
	@Test  
	/* 绑定手机
	 * 正常情况的测试
	*/
	
	public void bindMobileTest() throws JsonProcessingException, IOException {
		//绑定手机
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.bindMobile(userId, "18801282841");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//校验绑定后的手机是否正确(因为加密，所以有星号)
		String msg0 = UserCenter.getUserById(userId);
		System.out.println(msg0);
		JsonNode node0 = mapper.readTree(msg0);
		Assert.assertTrue(node0.get("status").asInt() == 1);
		Assert.assertTrue(node0.get("user").get("userMobile").asText().equals("188****2841"));
		
		//还原手机，为下次执行脚本准备
		String msg1 = UserCenter.bindMobile(userId, "18611286701");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
	}
	
	
	@Test  
	/* 绑定邮箱
	 * 正常情况的测试
	 * sdk.properties里
	 * client.credential.path=uculture.properties--可以执行这个用户
	 * client.credential.path=authfileyht.txt--不允许执行，会报400 		
	*/
	
	public void bindMailTest() throws JsonProcessingException, IOException {
		//绑定邮箱
//		String userName = "18611286701"; 
		String userName = "52888888"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.bindEmail(userId, "stt20180314100@test1988.com");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//校验绑定后的邮箱是否正确(因为加密，所以有星号)
		String msg0 = UserCenter.getUserById(userId);
		System.out.println(msg0);
		JsonNode node0 = mapper.readTree(msg0);
		Assert.assertTrue(node0.get("status").asInt() == 1);
		Assert.assertTrue(node0.get("user").get("userEmail").asText().equals("stt201803141*****@test1988.com"));
		
		//还原邮箱，为下次执行脚本准备
		String msg1 = UserCenter.bindEmail(userId, "jlccstt@126.com");
//		String msg1 = UserCenter.bindEmail(userId, "yixixinzi@126.com");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
	}
	

	@Test
	/* 
	 * 更新用户属性
	 * 正常情况的测试
	*/
	public void updateUserPropertiesTest() throws JsonProcessingException, IOException {
		String userName = "stt2017080301@chacuo.net"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);			
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d =date.format(new Date());
		
		//修改昵称
		String key="userName";
		String value=d+"name";			
		String msg = UserCenter.updateUserProperties(userId,key,value);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("修改用户属性成功"));

		//修改性别
		String key1="sex";
		String value1="1";		
		String msg1 = UserCenter.updateUserProperties(userId,key1,value1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);
		Assert.assertTrue(node1.get("msg").asText().equals("修改用户属性成功"));

		//修改出生日期
		SimpleDateFormat date2 =new SimpleDateFormat("yyyy年MM月dd日");
		String d2 =date.format(new Date());
		String key2="birthday";
		String value2="2017年08月08日";		
		String msg2 = UserCenter.updateUserProperties(userId,key2,value2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==1);
		Assert.assertTrue(node2.get("msg").asText().equals("修改用户属性成功"));

		
		//修改地址
		String key3="address";
		String value3="1-11-21-";		
		String msg3 = UserCenter.updateUserProperties(userId,key3,value3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==1);
		Assert.assertTrue(node3.get("msg").asText().equals("修改用户属性成功"));


		//修改userCode
		String key4="userCode";
		String value4=d+"Code";			
		String msg4 = UserCenter.updateUserProperties(userId,key4,value4);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==1);
		Assert.assertTrue(node4.get("msg").asText().equals("修改用户属性成功"));


	}
	
	
	@Test
	/* 
	 * 更新用户多个属性
	 * 正常情况的测试
	*/		
	
	
	public void updateUserMultiPropertiesTest() throws JsonProcessingException, IOException {

		String userId = UserCenterUtil.getUserIdByLoginName("stt2017080301@chacuo.net");			
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d =date.format(new Date());
		     
		Map<String, String> params = new HashMap<String, String>();

		params.put("userName", d+"name");
		params.put("sex", "0");
		params.put("address", "1-11-2-1");
		params.put("birthday", "2017年08月08日");
		params.put("userCode", d+"code");
		
		String msg = UserCenter.updateUserMultiProperties(userId,params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("successmsg").get(0).asText().equals("'address'属性修改成功"));
		Assert.assertTrue(node.get("successmsg").get(1).asText().equals("'sex'属性修改成功"));
		Assert.assertTrue(node.get("successmsg").get(2).asText().equals("'userName'属性修改成功"));
		Assert.assertTrue(node.get("successmsg").get(3).asText().equals("'userCode'属性修改成功"));
	
	}
	@Test  
	/* 根据用户ID向该用户手机发送消息
	 * 正常情况的测试
	*/
	
	public void sendMsgByMobileTest() throws JsonProcessingException, IOException {
		
		//获取用户ID
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//获取六位随机数
		int radomInt = new Random().nextInt(999999);
		System.out.println(radomInt);
		
		//三个参数
		//String msg = UserCenter.sendMsgByMobile(userId, "【用友网络】验证码："+radomInt+"，（用友客服绝对不会索要该验证码，切勿告诉他人），请在页面输入完成验证。");
		//String msg = UserCenter.sendMsgByMobile(userId,  "您申请的  XXXXX 企业认证，已通过审核。相关权益已开放，您可登录系统完成后续操作。");
		String msg = UserCenter.sendMsgByMobile(userId,"123456","rvxuv2Ge");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);		
		
		//两个参数
		String msg1 = UserCenter.sendMsgByMobile(userId,  "您申请的  XXXXX 企业认证，已通过审核。相关权益已开放，您可登录系统完成后续操作。");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);		
	}

	
	
	@Test  
	/* 根据用户ID向该用户邮箱发送消息
	 * 正常情况的测试
	*/
	
	public void sendMsgByEmailTest() throws JsonProcessingException, IOException {
		
		//获取用户ID
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//获取六位随机数
		int radomInt = new Random().nextInt(999999);
		System.out.println(radomInt);
		String msg = UserCenter.sendMsgByEmail(userId, "【用友网络】验证码："+radomInt+",测试“根据用户ID向该用户邮箱发送消息”接口");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);					
	}
	
	
	@Test
	/* 
	 * 非用户发送手机或邮箱验证码
	 * 正常情况的测试
	 * 在注册页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里
	 * ts的值就是key
	 * 图像验证码的值就是code
	 * 这个用例每次只能执行一次，再次执行，只能重新获取
	*/			
		public void sendcodeTest() throws JsonProcessingException, IOException {

		String contact="stt851026@163.com";
		String type="email";
		String key="1527557621000558310";
		String code="9FF7";
					
		String msg = UserCenter.sendcode(contact,type,key,code);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==1);
	}
}
