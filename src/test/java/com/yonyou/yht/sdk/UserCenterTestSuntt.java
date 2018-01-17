package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import yhttest.UserCenterUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.entity.UserInfo;
// xiezhengnan 6e3e49a2-aa13-4ed5-ad4b-557bd6d0e7a8
// test_001 3d5aae38-8be1-4bdd-866a-bf29a3540e8c
// test_002 8693be50-42a5-413b-a27e-59ded6dbd6a1

// caojj1@yonyou.com b5298ade-5c7e-4880-8521-f2542c2a6dab
// daixd@yonyou.com 906c8e75-2da4-473f-b14d-4ed8acaaab4f

public class UserCenterTestSuntt {
	ObjectMapper mapper;
	
	@Before
	public void init() {
		mapper = new ObjectMapper();
	}




	
	@Test
	/* 根据用户ID获取用户信息
	 * 正常流程测试
	*/
	public void getUserByIdTest() throws JsonProcessingException, IOException{

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
	 * 根据用户ID数组获取用户信息
	 * 正常情况的测试
	 * 用户ID数组输入正确的值
	*/
	public void getUserByPksTest() throws JsonProcessingException, IOException {
		String user1="suntt@yonyou.com";
		String user2="jlccstt@163.com";
		String userId1=UserCenterUtil.getUserIdByLoginName(user1);
		String userId2=UserCenterUtil.getUserIdByLoginName(user2);
		String [] userId = new String[] {userId1, userId2};
		String msg = UserCenter.getUserByPks(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	/* 
	 * 根据用户Code数组获取用户信息
	 * 正常情况的测试
	 * 用户Code数组输入正确的值
	*/
	public void getUserByCodesTest() throws JsonProcessingException, IOException {
		String userCode1="YHT-stt2017080201code";
		String userCode2="YHT-stt2017080701code";
		String [] userCodes = new String[] {userCode1, userCode2};
		String msg = UserCenter.getUserByCodes(userCodes);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
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
			Assert.assertTrue(node.get("user").get("userName").asText().equals("YHT-18810039018"));
		}
		
	}
	
	@Test
	/* 
	 * 根据登录名，用户SysID获取用户信息，针对冲突用户
	 * 正常情况的测试
	 * 登录名、用户SysID输入正确的值
	*/
	public void getUserByLoginNameSidTest() throws JsonProcessingException, IOException {
		String LoginName="jlccstt@126.com";
		String Sid="aps5";
		String msg = UserCenter.getUserByLoginName(LoginName, Sid,null);
		// UserCenter.getUserByLoginName(userName, sysid, secretKey);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	/* 
	 * 判断手机号格式是否正确
	 * 正常情况的测试
	 * 手机号输入正确的值
	*/
	public void isMobileTest() throws JsonProcessingException, IOException {
		//正确的手机号
		String Mobile="18810039018";
		String msg = UserCenter.isMobile(Mobile);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("flag").asInt() == 1);
		
		//不正确的手机号
		String Mobile1="157";
		String msg1 = UserCenter.isMobile(Mobile1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("flag").asInt() == 0);
	}
	
	@Test
	/* 
	 * 判断用户编码是否已经存在
	 * 正常情况的测试
	 * 用户编码输入一个存在的值，一个符合规则的不存在的值
	*/
	public void isUserCodeExistTest() {
		String [] userCode={"YHT-2017081401-code","YHT-2017081401"};
		for(int j=0;j<2;j++){
		String msg = UserCenter.isUserCodeExist(userCode[j]);
		System.out.println(msg);
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
		 * 根据登录名和Sysid判断用户是否存在
		 * 正常情况的测试
		 * 登录名输入分别使用userCode、userMobile、userEmail并且能查出数据的正确的值；输入一个不存在的值。
		 * Sysid前三个是存在的值，第四个是不存在的值
		 * 这样满足参数是 登录名(userCode或userMobile或userEmail)这三种情况
		 * 返回值是 flag	String    0，表示用户不存在       1，用户存在且有一个用户     
		*/
		public void isUserExist2Test() {
			String [] loginName={"YHT-2017081401-code","18611286701","jlccstt@163.com","随便乱输入的内容haha"};
			String [] sysid={"YHT","market","aps","随便乱输入的内容haha"};
			for(int j=0;j<4;j++){
			String msg = UserCenter.isUserExist(loginName[j],sysid[j]);
			System.out.println(msg);
			}
	}
		
		@Test
		/* 
		 * 根据userCode、userMobile、userEmail判断是否能够增加用户
		 * 正常情况的测试
		 * 参数都没有输入值是在异常测试用例里
		 * 返回值是 flag	String 
		 * 如果为0，表示手机号和邮箱不能同时为空；
		 * 如果为1，表示用户账号已经存在且手机号一致，用户已存在；
		 * 如果为2，表示用户账号已经存在且邮箱一致，用户已存在；
		 * 如果为3，表示用户账号已经存在且手机号,邮箱一致，用户已存在；
		 * 如果为4，表示用户账号已存在；
		 * 如果为5，表示手机号已存在；
		 * 如果为6，表示邮箱已存在；
		 * 如果为7，表示用户账号,手机号,邮箱都不存在，可以创建新用户。
		 * String [][] value数组里前三个是userCode、userMobile、userEmail参数
		 * 第四个是返回值应该返回的 flag的值，由于后面Assert判断
		*/
		public void isUserExist3Test() throws JsonProcessingException, IOException {			
			String [][] value={
					{"YHT-18810039018","","","0"},
					{"","18810039018","","5"},
					{"","","suntt@yonyou.com","6"},
					{"","18867896789","","7"},
					{"","","suntt123456789@yonyou.com","7"},
					{"","18810039018","suntt@yonyou.com","5"},
					{"","18867896789","suntt@yonyou.com","6"},
					{"","18810039018","suntt123456789@yonyou.com","5"},
					{"12345678900000","18810039018","suntt@yonyou.com","5"},
					{"YHT-18810039018","18867896789","","4"},
					{"YHT-18810039018","","suntt123456789@yonyou.com","4"},
					{"YHT-18810039018","18810039018","suntt123456789@yonyou.com","1"},
					{"YHT-18810039018","18867896789","suntt@yonyou.com","2"},
					{"YHT-18810039018","18810039018","suntt@yonyou.com","3"}
					};			
			for(int j=0;j<14;j++){
			String msg = UserCenter.isUserExist(value[j][0],value[j][1],value[j][2]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String i=value[j][3];
			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(i));
			}
	}
	
		
		
		
		
		
		@Test
		/* 
		 * 批量根据userCode、userMobile、userEmail判断是否能够增加用户
		 * 正常情况的测试
		 */
		public void isUsersExistTest() throws JsonProcessingException, IOException {		
			Map<String, Object> params = new HashMap<String, Object>();			
			List<Object> users = new ArrayList<Object>();			
			String [][] value={
					{"YHT-18810039018","","","4"},
					{"","18810039018","","5"},
					{"","","suntt@yonyou.com","6"},
					{"","18867896789","","7"},
					{"","","suntt123456789@yonyou.com","7"},
					{"","18810039018","suntt@yonyou.com","5"},
					{"","18867896789","suntt@yonyou.com","6"},
					{"","18810039018","suntt123456789@yonyou.com","5"},
					{"12345678900000","18810039018","suntt@yonyou.com","5"},
					{"YHT-18810039018","18867896789","","4"},
					{"YHT-18810039018","","suntt123456789@yonyou.com","4"},
					{"YHT-18810039018","18810039018","suntt123456789@yonyou.com","1"},
					{"YHT-18810039018","18867896789","suntt@yonyou.com","2"},
					{"YHT-18810039018","18810039018","suntt@yonyou.com","3"}
					};
			for(int j=0;j<14;j++){
			Map<String, String> user = new HashMap<String, String>() ;
			user.put("userCode",value[j][0]);
			user.put("userMobile", value[j][1]);
			user.put("userEmail", value[j][2]);
			users.add(user);
			}			
			params.put("users", users);
			String jsonStr = Utils.getJsonStr(mapper, params);
			System.out.println(jsonStr);
			String msg = UserCenter.isUsersExist(jsonStr);		
			System.out.println(msg);
			
//			for(int j=0;j<1;j++){
//			JsonNode node = mapper.readTree(msg);
//			String i=value[j][3];
//			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(i));
//			}
	       }
		
		
		
		@Test
		/* 
		 *根据用户id数组和用户名称查找用户
		 *定义一个用户二维数组，第一列是用户ID，第二列是为了给测试自己看用户名是否包含s
		 *这样做出20个数据，前19个包含s，这样执行结果能出现分页。最后一个执行查不出来，因为不包括s
		*/
		public void searchUserTest() throws JsonProcessingException, IOException {
			String [][] users={{"cac3b9f8-f7f0-455f-bf44-7b899865271d","stt2017080201@chacuo.net"},
					{"273ccd32-749c-451c-8784-9d9dff3c3160","stt2017080202@chacuo.net"},
					{"c22847e3-9d9c-4f8c-8fac-47fa0975220e","stt2017080701@chacuo.net"},
					{"07576a6f-235f-4f6f-8823-17742ef86f8d","stt2017081401@chacuo.net"},
					{"c4ac871c-52ad-4a48-a63e-c12eedb5628c","stt2017081401@chacuo.net"},
					{"37fbe5c9-7a95-423c-b070-401febadb02d","stt2017081601@chacuo.net"},
					{"5ad292d7-fa00-468b-bff4-83dbf96bc94e","stt2017081602@chacuo.net"},
					{"6c6888bb-71b1-4198-843a-c29e4f1235b7","stt2017081603@chacuo.net"},
					{"65bae085-1eee-47c9-8ee5-2d81889935fe","stt2017081604@chacuo.net"},
					{"9964da9d-83af-4b91-81b0-46d245f8f699","stt2017081701@chacuo.net"},
					{"b2830637-035b-4885-bb95-b576bb312e8b","stt2017081801@chacuo.net"},
					{"0f4a4cde-8e15-414b-ad86-a14509ad8dad","stt2017082101@chacuo.net"},
					{"3531cf1f-e6ed-4e08-81dc-d5da90dde699","stt2017082201@chacuo.net"},
					{"868d2718-4723-4504-aa03-a773918c2fdb","suntt@yonyou.com"},
					{"555efe32-1447-4f86-96cc-a972d440ea0b","suntt1026@126.com"},
					{"c1265234-fd39-4124-b00b-dcad33b6a3c2","stt851207@126.com"},
					{"afd48191-a519-486d-b60a-dedb0a163f1b","stt851207@163.com"},
					{"c53dad14-620e-4bdf-b0ea-c9eeccc57486","jlccstt@163.com"},
					{"47624900baea","jlccstt@126.com"},
					{"488e6137-e684-4d40-86ea-a619d43c5a50","18611286701@chacuo.net"}};	
			String [] pks=new String[users.length];			
			for(int i=0;i<users.length;i++){
				pks[i]=users[i][0];
			}
			String  userName="stt";
			String msg = UserCenter.searchUser(pks,userName);
			System.out.println(msg);
			
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("users").get("totalElements").asInt() == 17);
			
	    }
		
		
		@Test
		/* 
		 *根据用户id数组和用户名称查找用户（需sysid,secretKey）
		 *定义一个用户二维数组，第一列是用户ID，第二列是为了给测试自己看用户名是否包含s
		 *第二列也是用于对比返回值里邮箱是否解密了（没有星号，邮箱都完整显示了）
		 *sysid,secretKey是控制查询出的结构是否加密的，对数据条数没有影响
		*/
		public void searchUser1Test() throws JsonProcessingException, IOException {
			String [][] users={{"52f1decd-78b3-4f08-ab5b-935f00da50b8","stt2017083101@chacuo.net"},
					{"488e6137-e684-4d40-86ea-a619d43c5a50","18611286701@chacuo.net"}};	
			String [] pks=new String[users.length];			
			for(int i=0;i<users.length;i++){
				pks[i]=users[i][0];
			}
			String  userName="stt";
			String  userEmail=users[0][1];
			
			//有下面两行，控制台输出的结果就是解密的，邮箱能看全
			String  sysid="market";
			String  secretKey="d977a56a7584b02b";
			String msg = UserCenter.searchUser(pks,userName,sysid,secretKey);
			System.out.println(msg);			
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("users").get("totalElements").asInt() == 1);
			Assert.assertTrue(node.get("users").get("content").get(0).get("userEmail").asText().equals(userEmail));

		}
		
		@Test
		/* 
		 *根据用户id数组和用户名称查找用户（需sysid,secretKey并支持分页）
		 *sysid,secretKey是开发给的，直接用就可以
		 *searchuser  排序条件：auto、user_code、name
		 *如果不传值auto是默认值，auto的排序方式是usercode
		 *定义一个用户二维数组，第一列是用户ID，第二列是为了给测试自己看用户名是否包含s
		 *这样做出10个数据，前9个包含s，这样执行结果能出现分页。最后一个执行查不出来，因为不包括s
		*/
		public void searchUser2Test() throws JsonProcessingException, IOException {
			String [][] users={{"42a8c1fe-ad9a-4345-b792-cd9b1af92efe","stt01@chacuo.net"},
					{"0b340921-1f59-43b9-a811-737f106e33cc","stt02@chacuo.net"},
					{"0b5661a4-983d-4465-baf0-f5d81e10456c","stt03@chacuo.net"},
					{"17ced497-f36b-454a-8185-dca7bd192dad","stt04@chacuo.net"},
					{"ccb5683a-911b-4565-a71e-004e0a0795ce","stt05@chacuo.net"},
					{"1475cd17-c32a-4d17-b7f6-affbf28ce1eb","stt06@chacuo.net"},
					{"60fb35c7-8631-4c72-995d-676e4a4bc303","stt07@chacuo.net"},
					{"d0a9da3f-be11-4766-b5f1-6d0b7b5ff4f2","stt08@chacuo.net"},
					{"996b718e-4551-453f-af4b-2b6ddab46680","stt09@chacuo.net"},
					{"488e6137-e684-4d40-86ea-a619d43c5a50","18611286701@chacuo.net"}};	
			String [] pks=new String[users.length];			
			for(int i=0;i<users.length;i++){
				pks[i]=users[i][0];
			}
			String  ps="4";
			String  pn="3";
			String  sortType="name";
			String  userName="stt";
			String  sysid="market";
			String  secretKey="d977a56a7584b02b";
			
			String msg = UserCenter.searchUser(pks,userName,ps,pn,sortType,"",sysid,secretKey);
			System.out.println(msg);			
			JsonNode node = mapper.readTree(msg);
			//一共9条数据，每页4个，一共3页，最后一页
			Assert.assertTrue(node.get("users").get("totalPages").asInt() == 3);
			Assert.assertTrue(node.get("users").get("totalElements").asInt() == 9);
			Assert.assertFalse(node.get("users").get("first").asBoolean());
			Assert.assertFalse(node.get("users").get("firstPage").asBoolean());
			Assert.assertTrue(node.get("users").get("last").asBoolean());
			Assert.assertTrue(node.get("users").get("lastPage").asBoolean());
			Assert.assertTrue(node.get("users").get("numberOfElements").asInt() == 1);			
			Assert.assertTrue(node.get("users").get("size").asInt() == 4);
			
			//查出来的元素是第9个，因为把stt09@chacuo.net的昵称改称了1-stt09-name，其他都是stt01至stt08
			//所以按昵称排序，第一个是stt09@chacuo.net，第二个是stt01@chacuo.net，最后一个就是stt08@chacuo.net
			Assert.assertTrue(node.get("users").get("content").get(0).get("userEmail").asText().equals("stt08@chacuo.net"));
			
			String  sortType1="user_code";
			String  pn1="1";
			String  msg1 = UserCenter.searchUser(pks,userName,ps,pn1,sortType1,"",sysid,secretKey);
			System.out.println(msg1);	
			JsonNode node1 = mapper.readTree(msg1);
			//一共9条数据，每页4个，第一页
			Assert.assertTrue(node1.get("users").get("first").asBoolean());
			Assert.assertTrue(node1.get("users").get("firstPage").asBoolean());
			Assert.assertFalse(node1.get("users").get("last").asBoolean());
			Assert.assertFalse(node1.get("users").get("lastPage").asBoolean());
			Assert.assertTrue(node1.get("users").get("numberOfElements").asInt() == 4);		
			//按code排序，stt01@chacuo.net是第一个
			Assert.assertTrue(node1.get("users").get("content").get(0).get("userEmail").asText().equals("stt01@chacuo.net"));			
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
		/* 
		 * 根据联系方式查询用户列表
		 * 正常情况的测试
		*/
		public void getUserByContactsTest() throws JsonProcessingException, IOException {
			String [][] users={{"18611286701","yixixinzi@126.com"}};
			for(int j=0;j<users.length;j++){
			String msg = UserCenter.getUserByContacts(users[j][0],users[j][1]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
			Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
			Assert.assertTrue(node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));
			
			}
		}
		
		
		@Test
		/* 
		 * 据登录名模糊查询用户列表（支持分页）
		 * 正常情况的测试
		*/
		public void searchUserByNameTest() throws JsonProcessingException, IOException {


			String msg = UserCenter.searchUserByName("stt","3","1","userCode");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);	
		}
		
		
		@Test
		/* 
		 * 据登录名模糊查询用户列表
		 * 正常情况的测试
		*/
		public void searchUserByName1Test() throws JsonProcessingException, IOException {


			String msg = UserCenter.searchUserByName("stt");
			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
//			Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
//			Assert.assertTrue(node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));
//			
		
		}
		
		
		@Test
		/* 
		 * 根据字符串模糊查询用户列表（前缀匹配、支持分页）
		 * 正常情况的测试
		*/
		public void searchUserByFilterTest() throws JsonProcessingException, IOException {

			//默认是第一页，一页20条，因为数据多，肯定不是第一页，肯定是满页20条
			String msg = UserCenter.searchUserByFilter("stt");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		
			Assert.assertTrue(node.get("users").get("first").asBoolean());
			Assert.assertTrue(node.get("users").get("firstPage").asBoolean());
			Assert.assertFalse(node.get("users").get("lastPage").asBoolean());
			Assert.assertTrue(node.get("users").get("numberOfElements").asInt() == 20);			
			Assert.assertTrue(node.get("users").get("size").asInt() == 20);

			
			//每页显示5条数据，显示第二页，按userName排序
			String msg1 =UserCenter.searchUserByFilter("stt", 5, 2, "name");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);		
			Assert.assertFalse(node1.get("users").get("first").asBoolean());
			Assert.assertFalse(node1.get("users").get("firstPage").asBoolean());
			Assert.assertFalse(node1.get("users").get("lastPage").asBoolean());
			Assert.assertTrue(node1.get("users").get("numberOfElements").asInt() == 5);			
			Assert.assertTrue(node1.get("users").get("size").asInt() == 5);
			
			//每页显示5条数据，显示第二页，默认排序，即按userCode排序
			String msg2 =UserCenter.searchUserByFilter("stt", 5, 2, "auto");
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);		
			Assert.assertFalse(node2.get("users").get("first").asBoolean());
			Assert.assertFalse(node2.get("users").get("firstPage").asBoolean());
			Assert.assertFalse(node2.get("users").get("lastPage").asBoolean());
			Assert.assertTrue(node2.get("users").get("numberOfElements").asInt() == 5);			
			Assert.assertTrue(node2.get("users").get("size").asInt() == 5);
		}
		
		
		
		@Test
		/* 
		 * 增加用户
		 * 正常情况的测试
		*/
		public void addUserTest() throws JsonProcessingException, IOException {

			Map<String, String> params = new HashMap<String, String>();
			SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String userCode =date.format(new Date());
			params.put("userCode", userCode+"code");
			params.put("userName", userCode+"name");
			params.put("userEmail", userCode+"@chacuo.net");		
			String msg = UserCenter.addUser(params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);
			
		}	
		
		
		
		@Test
		/* 
		 * SDK只读方法增加超时重试机制 
		 * 需要在sdk.properties里设置
		 * yht.connect.timeout=5
		 * yht.api.get.retry=true
		 * yht.user.base.url=https://www.dsafsadfsdafsdafsadfsdaf.com
		 * 这个url是随便写的，就是为了找不到
		 * 这样执行下面方法的时候，控制台会报错，并且会5秒执行一次
		 * 20170920

		*/
		public void retryTest() {
			String msg = UserCenter.getUserAvatar("8562c249-0b63-449c-a687-7cfa2ddcab7f");
			System.out.println(msg);
			
		}
		
		
		
		
		@Test
		/* 
		 * 根据激活码激活用户
		 * 开发把186119286701设置成未激活，并生成一个激活码

		*/
		public void activeUserByCodeTest() throws JsonProcessingException, IOException {

			String msg = UserCenter.activeUserByCode("18611286701","784814");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);
			
			String msg1 = UserCenter.activeUserByCode("stt2017092001@chacuo.net","325195");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);
		}
		
		
		
		@Test
		/* 
		 * 批量增加用户
		 * 正常情况的测试
		*/
		public void addUsersTest() throws JsonProcessingException, IOException  {
			
			SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String userCode =date.format(new Date());
			
			String systemCode = "yht";
			int i=1;
			Map<String, String> user1 = new HashMap<String, String>();
			user1.put("userCode", userCode+"code");
			user1.put("userName", userCode+"name");
			user1.put("userEmail", userCode+"@chacuo.net");	
			
			Map<String, String> user2 = new HashMap<String, String>();
			user2.put("userCode", userCode+i+"code");
			user2.put("userName", userCode+i+"name");
			user2.put("userEmail", userCode+i+"@chacuo.net");	
			
			Map<String, Object> params = new HashMap<String, Object>();
			List<Object> users = new ArrayList<Object>();
			users.add(user1);
			users.add(user2);
			params.put("users", users);
			String jsonStr = Utils.getJsonStr(mapper, params);
			System.out.println(jsonStr);
			String msg = UserCenter.addUsers(systemCode, jsonStr);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);
			Assert.assertTrue(node.get("msg").asText().equals("保存成功"));

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

//			//修改出生日期
//			SimpleDateFormat date2 =new SimpleDateFormat("yyyy年MM月dd日");
//			String d2 =date.format(new Date());
//			String key2="birthday";
//			String value2="2017年8月8日";		
//			String msg2 = UserCenter.updateUserProperties(userId,key2,value2);
//			System.out.println(msg2);
//			JsonNode node2 = mapper.readTree(msg2);
//			Assert.assertTrue(node2.get("status").asInt()==1);
//			Assert.assertTrue(node2.get("msg").asText().equals("修改用户属性成功"));

			
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
//			params.put("birthday", "2010年9月6日");
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
		/* 
		 * 非用户发送手机或邮箱验证码
		 * 正常情况的测试
		 * 在注册页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里
		 * ts的值就是key
		 * 图像验证码的值就是code
		 * 这个用例每次只能执行一次，再次执行，只能重新获取
		*/			
			public void sendcodeTest() throws JsonProcessingException, IOException {

			String contact="stt2017120405@chacuo.net";
			String type="email";
			String key="1512372939000713288";
			String code="CXPM";
						
			String msg = UserCenter.sendcode(contact,type,key,code);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
		//	Assert.assertTrue(node.get("status").asInt()==1);
		}
		
		
		
		@Test
		/* 
		 * 给已有用户发送手机或邮箱验证码
		 * 发短信
		 * 正常情况的测试
		*/			
			public void sendPhoneMessageTest() throws JsonProcessingException, IOException, InterruptedException {

			//等待一分钟，因为上两个方法执行完，就执行了发短信、发邮件，一分钟内只能发一次
			Thread.sleep(60000);
			
//			//真实手机号
//			String userName = "18810039018"; 
//			String userId = UserCenterUtil.getUserIdByLoginName(userName);					
//			String msg = UserCenter.sendPhoneMessage(userId);
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt()==1);
//			Assert.assertTrue(node.get("msg").asText().equals("短信发送成功"));		
			
			//测试手机号
			String userName1 = "18803888888"; 
			String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);					
			String msg1 = UserCenter.sendPhoneMessage(userId1);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);
			Assert.assertTrue(node1.get("msg").asText().equals("测试账号不需要发送"));	
			
			SimpleDateFormat date =new SimpleDateFormat("yyMMdd");
			String t =date.format(new Date());
			System.out.println(t);
					
			String msg11 = validateCode("mobile",userId1,"171204",userName1);
			System.out.println(msg11);
			
			
		}
		






		private String validateCode(String string, String userId1, String string2, String userName1) {
			// TODO Auto-generated method stub
			return null;
		}





		@Test
		/* 
		 * 给已有用户发送手机或邮箱验证码
		 * 发邮件
		 * 正常情况的测试
		*/			
			public void sendEmailMessageTest() throws JsonProcessingException, IOException, InterruptedException {

			//等待一分钟，因为上两个方法执行完，就执行了发短信、发邮件，一分钟内只能发一次
			Thread.sleep(60000);
			
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);		
			String msg = UserCenter.sendEmailMessage(userId);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);	
			Assert.assertTrue(node.get("msg").asText().equals("邮件发送成功"));
		}
		
		@Test
		/* 
		 * 给已有用户发送手机或邮箱验证码
		 * 两个参数的发短信和发邮件
		 * 正常情况的测试
		*/			
			public void sendValidateCodeTest() throws JsonProcessingException, IOException, InterruptedException {

			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);		

			//等待一分钟，因为上两个方法执行完，就执行了发短信、发邮件，一分钟内只能发一次
			Thread.sleep(60000);

			//发邮件
			String msg1 = UserCenter.sendValidateCode(userId,"email");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);
			Assert.assertTrue(node1.get("msg").asText().equals("邮件发送成功"));
			
			//发短信
			String msg2 = UserCenter.sendValidateCode(userId,"mobile");
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);
			Assert.assertTrue(node2.get("msg").asText().equals("短信发送成功"));
			
		}		
		
		

		
		@Test
		/* 
		 * 单点登出
		 * 正常情况的测试
		 * tip的值yht-manager_是固定的，后门的ST-36-9qIaHTUkU4tg7kEgK5fU-cas01.example.org是用F12获取的
		 * 开发在应用配置里系统名称“yht-manager”,其他信息是selfendpoint=true
		 * 使用idtest的后台管理的地址http://172.20.14.138:8880/yht-manager/    登录
		 * 用F12，在network里，queryRole.do -->  XHR  -->  Cookies-->
		 * Name是yht_username对应的Value的值，复制出来，只要前面.org部分  
		*/			
			public void logoutWithTidTest() throws JsonProcessingException, IOException, InterruptedException {
			
			String tid = "yht-manager_ST-36-9qIaHTUkU4tg7kEgK5fU-cas01.example.org";
			String msg1 = UserCenter.logoutWithTid(tid);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt()==1);			
		}
		
		
		@Test
		/* 
		 * 验证accesstoken
		 * 正常情况的测试
		*/			
			public void checkOauthTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取ssotoken--本测试方法需要
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 

			Map<String, String> params=new  HashMap<String, String>();
			params.put("username","18810039018");
			params.put("shaPassword",shaPassword);
			params.put("md5Password",md5Password);
						
			String msg = UserCenter.createSSOToken(params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String ssotoken=node.get("data").asText();
			
			//本获取accesstoken--本测试方法需要这个参数
			String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);			
			String accesstoken= node1.get("accesstoken").asText();
			
			//本方法的测试开始，验证accesstoken
			String msg2 = UserCenter.checkOauthToken(accesstoken);		
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);	
			Assert.assertTrue(node1.get("status").asInt()==1);		
			
			
			
		}
		
		
		
		@Test
		/* 
		 * 根据accesstoken获取用户信息
		 * 正常情况的测试
		*/			
			public void getUserByTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取ssotoken
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 

			Map<String, String> params=new  HashMap<String, String>();
			params.put("username","18810039018");
			params.put("shaPassword",shaPassword);
			params.put("md5Password",md5Password);
						
			String msg = UserCenter.createSSOToken(params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String ssotoken=node.get("data").asText();
			
			//获取accesstoken--本测试方法需要这个参数
			String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			String accessToken=node1.get("accesstoken").asText();
			
			//本测试方法的代码
			String msg2 = UserCenter.getUserByToken(accessToken);
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);			
		}
		
		
		@Test
		/* 
		 * 销毁accesstoken
		 * 正常情况的测试
		*/			
			public void destroyAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取ssotoken
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 

			Map<String, String> params=new  HashMap<String, String>();
			params.put("username","stt2017101701@chacuo.net");
			params.put("shaPassword",shaPassword);
			params.put("md5Password",md5Password);
						
			String msg = UserCenter.createSSOToken(params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String ssotoken=node.get("data").asText();
			
			//获取accesstoken--本测试方法需要这个参数
			String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			String accessToken=node1.get("accesstoken").asText();
			
			//销毁前，可以获取用户信息
			String msg2 = UserCenter.getUserByToken(accessToken);
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);	
			
			//本测试方法的代码，销毁
			String msg3 = UserCenter.destroyAccessToken(accessToken);
			System.out.println(msg3);
			JsonNode node3 = mapper.readTree(msg3);
			Assert.assertTrue(node3.get("status").asInt()==1);			
			
			//销毁后，再获取用户信息应该获取不到
			String msg4 = UserCenter.getUserByToken(accessToken);
			System.out.println(msg4);
			JsonNode node4 = mapper.readTree(msg4);
			Assert.assertTrue(node4.get("status").asInt()==0);		
			
		}
		
		
		
		@Test
		/* 
		 * 刷新accesstoken
		 * 正常情况的测试
		 * access_token一分钟失效、refresh_token三分钟失效
		*/			
			public void refreshAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取access_token、refresh_token
			String msg1=UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
			System.out.println("获取access_token、refresh_token:"+msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);	
			String accessToken=node1.get("data").get("access_token").asText();
			String refreshToken=node1.get("data").get("refresh_token").asText();
			
			//获取用户信息
			String msg2 = UserCenter.getUserByToken(accessToken);
			System.out.println("第一次获取用户信息："+msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);	
			
			//access_token没有失效，此时刷新应该报错
			String msg3 = UserCenter.refreshAccessToken(refreshToken);
			System.out.println("access_token没有失效，此时刷新不成功："+msg3);
			JsonNode node3 = mapper.readTree(msg3);
			Assert.assertTrue(node3.get("status").asInt()==5);	
			Assert.assertTrue(node3.get("msg").asText().equals("Access Token  does not Timeout"));	
			
			//获取用户信息
			String msg4 = UserCenter.getUserByToken(accessToken);
			System.out.println("第二次获取用户信息："+msg4);
			JsonNode node4 = mapper.readTree(msg4);
			Assert.assertTrue(node4.get("status").asInt()==1);	
			
			//等1分钟，此时access_token失效
			Thread.sleep(60000);
			
			//access_token已失效，此时刷新应该成功
			String msg5 = UserCenter.refreshAccessToken(refreshToken);
			System.out.println("access_token已失效，此时刷新应该成功:"+msg5);
			JsonNode node5 = mapper.readTree(msg5);
			Assert.assertTrue(node5.get("status").asInt()==1);	
			
			//刷新后获取新的access_token
			String accessTokenNew=node5.get("data").get("access_token").asText();
			String refreshTokenNew=node5.get("data").get("refresh_token").asText();
			
			//access_token已失效，此时还用这个值获取用户信息失败
			String msg6 = UserCenter.getUserByToken(accessToken);
			System.out.println("access_token已失效，此时还用这个值获取用户信息失败："+msg6);
			JsonNode node6 = mapper.readTree(msg6);
			Assert.assertTrue(node6.get("status").asInt()==0);	
			Assert.assertTrue(node6.get("msg").asText().equals("can not find user by accesstoken "));	
			
			//使用刷新后新获取的access_token，可以获取用户信息
			String  msg7 = UserCenter.getUserByToken(accessTokenNew);
			System.out.println(msg7);
			JsonNode node7 = mapper.readTree(msg7);
			Assert.assertTrue(node7.get("status").asInt()==1);	
			
			//等2分钟，即一共等了3分钟，此时refreshToken失效
			Thread.sleep(120000);
			
			//第一次获取的refresh_Token失效，此时刷新失败
			String msg8 = UserCenter.refreshAccessToken(refreshToken);
			System.out.println("refresh_Token失效，此时刷新失败："+msg8);
			JsonNode node8 = mapper.readTree(msg8);
			Assert.assertTrue(node8.get("status").asInt()==0);	
			Assert.assertTrue(node8.get("msg").asText().equals("refresh accesstoken error,can not find accesstoken from refreshtoken,may be refresh token have used already"));	
			
			//第二次获取的refresh_Token有效，此时刷新成功
			String msg9 = UserCenter.refreshAccessToken(refreshTokenNew);
			System.out.println("第二次获取的refresh_Token有效，此时刷新成功："+msg9);
			JsonNode node9 = mapper.readTree(msg9);
			Assert.assertTrue(node9.get("status").asInt()==1);	
			
		}
		
		
		@Test
		/* 
		 * 根据密码获取accesstoken
		 * 正常情况的测试
		 * 文档里有链接的
		*/			
			public void generateAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			String username="18810039018";
			String Password="yonyou11";
			String md5Password=SDKUtils.encodeUsingMD5(Password); 
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
		
			
			String msg = UserCenter.generateAccessToken(username,md5Password,shaPassword,false);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);	
		}
		
		@Test
		/* 
		 * 根据accesstoken获取临时token
		 * 正常情况的测试
		*/			
			public void genTokenByAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取access_token
			String msg1=UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
			System.out.println("获取access_token、refresh_token:"+msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);	
			String accessToken=node1.get("data").get("access_token").asText();			
			
			//根据accesstoken获取临时token
			String msg2=UserCenter.genTokenByAccessToken(accessToken);
			System.out.println("获取access_token、refresh_token:"+msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);	
			
			}
		
		
		
		@Test
		/* 
		 * 根据用户名密码创建ssotoken(uclient)
		 * 正常情况的测试
		*/			
			public void createSSOTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 

			Map<String, String> params=new  HashMap<String, String>();
			params.put("username","18810039018");
			params.put("shaPassword",shaPassword);
			params.put("md5Password",md5Password);
			

			String msg1 = UserCenter.createSSOToken(params);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);			
		}
		
		
		@Test
		/* 
		 * 根据ssotoken获取accessToken(uclient)
		 * 正常情况的测试
		*/			
			public void genAccessTokenBySSOTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取ssotoken--本测试方法需要这个参数
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 

			Map<String, String> params=new  HashMap<String, String>();
			params.put("username","18810039018");
			params.put("shaPassword",shaPassword);
			params.put("md5Password",md5Password);
						
			String msg = UserCenter.createSSOToken(params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String ssotoken=node.get("data").asText();
			
			//本测试方法的代码
			String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);			
		}
		
		
		@Test
		/* 
		 * 根据accessToken获取ouathToken(uclient)
		 * 正常情况的测试
		*/			
			public void genOuathTokenByAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {
			
			//获取ssotoken
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 

			Map<String, String> params=new  HashMap<String, String>();
			params.put("username","18810039018");
			params.put("shaPassword",shaPassword);
			params.put("md5Password",md5Password);
						
			String msg = UserCenter.createSSOToken(params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String ssotoken=node.get("data").asText();
			
			//获取accesstoken--本测试方法需要这个参数
			String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			String accessToken=node1.get("accesstoken").asText();
			
			//本测试方法的代码
			String msg2 = UserCenter.genOuathTokenByAccessToken(accessToken);
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==1);			
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
			String userName = "18611286701"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.bindEmail(userId, "346797015@qq.com");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
			
			//校验绑定后的邮箱是否正确(因为加密，所以有星号)
			String msg0 = UserCenter.getUserById(userId);
			System.out.println(msg0);
			JsonNode node0 = mapper.readTree(msg0);
			Assert.assertTrue(node0.get("status").asInt() == 1);
			Assert.assertTrue(node0.get("user").get("userEmail").asText().equals("3467970*****@qq.com"));
			
			//还原邮箱，为下次执行脚本准备
			String msg1 = UserCenter.bindEmail(userId, "yixixinzi@126.com");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt() == 1);
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
		/* 上传用户头像
		 * 正常情况的测试
		*/
		
		public void uploadUserAvatorTest() throws JsonProcessingException, IOException {
			
			//上传头像
			String userName = "18611286701"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.uploadUserAvator(userId, "F:\\手持身份证合照.png");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
			
			//还原头像，为下次自动化准备
			String msg1 = UserCenter.uploadUserAvator(userId, "F:\\头像.png");
			System.out.println(msg1);						
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
			//String msg = UserCenter.sendMsgByMobile(userId, "【用友网络】验证码："+radomInt+"，（用友客服绝对不会索要该验证码，切勿告诉他人），请在页面输入完成验证。");
			String msg = UserCenter.sendMsgByMobile(userId,  "您申请的  XXXXX 企业认证，已通过审核。相关权益已开放，您可登录系统完成后续操作。");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);					
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
		/* 根据用户ID获取用户标签
		 * 正常情况的测试
		*/
		
		public void getTagsTest() throws JsonProcessingException, IOException {
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.getTags(userId);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
		}
		
		
		@Test  
		/* 根据标签获取用户ID
		 * 正常情况的测试
		*/
		
		public void getUserIdsByTagTest() throws JsonProcessingException, IOException {

//			String msg = UserCenter.getUserIdsByTag("健康");
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);
//			
//			String msg1 = UserCenter.getUserIdsByTag("健康","1","1");
//			System.out.println(msg1);
//			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt() == 1);
			
			
		}
		
		
		
		@Test  
		/* 为用户产生自动登录Token
		 * 正常情况的测试
		*/
		
		public void genTokenByUserIdTest() throws JsonProcessingException, IOException {
			
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			
			String msg = UserCenter.genTokenByUserId(userId);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
				
		}
		
		
		@Test  
		/* 判断用户是否存在某个标签
		 * 正常情况的测试
		*/
		
		public void isTagExistTest() throws JsonProcessingException, IOException {
			
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			
			//存在的标签
			String msg = UserCenter.isTagExist(userId,"健康");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
			
			//不存在的标签
			String msg1 = UserCenter.isTagExist(userId,"随便乱输入的内容哈哈");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt() == 1);
		}
		
		@Test  
		/* 为用户设置标签&移除用户标签
		 * 正常情况的测试
		*/
		
		public void setTagTest() throws JsonProcessingException, IOException {
			//设置标签
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.setTag(userId,"冰雪聪明");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			//移除标签
			Assert.assertTrue(node.get("status").asInt() == 1);
			String msg1 = UserCenter.removeTag(userId,"冰雪聪明");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt() == 1);
		}
		
		
		@Test  
		/* 修改用户标签
		 * 正常情况的测试
		*/
		
		public void modifyTagTest() throws JsonProcessingException, IOException {
			//修改标签
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.modifyTag(userId,"阳光","阳光宝贝");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			//还原标签
			Assert.assertTrue(node.get("status").asInt() == 1);
			String msg1 = UserCenter.modifyTag(userId,"阳光宝贝","阳光");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt() == 1);
		}
		
		
		@Test  
		/* 查询用户的Profile
		 * 正常情况的测试
		*/
		
		public void getProfileTest() throws JsonProcessingException, IOException {
			String userName = "18810039018"; 
//			String userId = UserCenterUtil.getUserIdByLoginName(userName);
//			String msg = UserCenter.getProfile(userId,"market");
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);
		}
		
		
		@Test  
		/* 查询用户合并事件
		 * 正常情况的测试
		*/
		
		public void listMergeEventTest() throws JsonProcessingException, IOException {
			String msg = UserCenter.listMergeEvent("2017-10-01","2017-10-21","");
			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);
		}
		
		
		
		@Test  
		/* 查询用户是否待激活
		 * 正常情况的测试
		*/
		
		public void isUserActivateTest() throws JsonProcessingException, IOException {
			
			//激活的
			String msg = UserCenter.isUserActivate("18810039018");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 0);
			Assert.assertTrue(node.get("msg").asText().equals("active"));
			
			//待激活
			String msg1 = UserCenter.isUserActivate("suntt1026@126.com");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt() == 1);

			
		}
		
		
		@Test  
		/* 查询用户冲突用户
		 * 正常情况的测试
		*/
		
		public void getConflictUserByIdTest() throws JsonProcessingException, IOException {
					
			String userName1 = "jlccstt@126.com"; 
//			String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
//			String msg1 = UserCenter.getConflictUserById(userId1);
//			System.out.println(msg1);
//			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt() == 1);			
		}
		
		
		
		@Test
		/* 
		 * 用户事件查询(根据时间段、sysid)
		 * 正常情况的测试
		 */
		public void searchEventByTimeTest() {
			String [][] value={
					{"2017-01-01","2017-08-18","yht","6","1","ts"}
					};			
			for(int j=0;j<1;j++){
			String msg =  UserEventCenter.searchEventByTime(value[j][0], value[j][1], value[j][2], Integer.valueOf(value[j][3]), Integer.valueOf(value[j][4]), value[j][5]);
			System.out.println(msg);
			}
		}
		
		
		@Test
		/* 
		 * 根据第三方账号创建用户
		 * 正常情况的测试
		*/
		public void createUserByThirdTest() throws JsonProcessingException, IOException {

//			//根据qq创建用户
//			Map<String, String> params = new HashMap<String, String>();
//			SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
//			String d =date.format(new Date());
//			params.put("openid", d);
//			String msg = UserCenter.createUserByThird(params,"qq");
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt()==1);
//			
//			//根据微信创建用户
//			Map<String, String> params1 = new HashMap<String, String>();
//			params1.put("unionid", d);
//			String msg1 = UserCenter.createUserByThird(params1,"wechat");
//			System.out.println(msg1);
//			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt()==1);
			
		}	
		
		@Test  
		/* 获取用户实名认证状态
		 * 正常情况的测试
		*/
		
		public void getPersonVerifyStatusTest() throws JsonProcessingException, IOException {
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			//第一列是异常描述
			//第二列参数userID
			//第三列返回值status
			//第四列返回值verifystatus
			//第五列返回值msg
			String [][] value={				
					{"实名认证通过",UserCenterUtil.getUserIdByLoginName("18810039018"),"1","success"},
					{"实名认证中",UserCenterUtil.getUserIdByLoginName("jlccstt@126.com"),"1","processing"},
					{"实名认证未通过",UserCenterUtil.getUserIdByLoginName("jlccstt@163.com"),"1","refuse"},
					{"未实名认证",UserCenterUtil.getUserIdByLoginName("18611286701"),"1","unprocess"},
			};	
			
			//返回值有msg的异常情况
			for(int j=0;j<4;j++){
			String msg = UserCenter.getPersonVerifyStatus(value[j][1]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][2]));
			Assert.assertTrue(node.get("verifystatus").asText().equals(value[j][3]));
			}
					
		}
		
		@Test  
		/* 用户ID获取用户头像URL地址
		 * 正常情况的测试
		*/
		
		public void getUserAvatarTest() throws JsonProcessingException, IOException {
			
			//上传过头像的
			String userName = "18611286701"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.getUserAvatar(userId);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
			Assert.assertTrue(node.get("avatar").asText().equals("https://cdn.yonyoucloud.com/dev/yht//style/images/avatar/488e6137-e684-4d40-86ea-a619d43c5a50/avatar.png"));

			//未上传头像
			String userName1 = "jlccstt@163.com"; 
			String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
			String msg1 = UserCenter.getUserAvatar(userId1);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt() == 1);
			Assert.assertTrue(node1.get("avatar").asText().equals("https://cdn.yonyoucloud.com/dev/yht/style/images/user.jpg"));
		}
		
		
		
		
		@Test  
		/* 获取用户登录信息
		 * 正常情况的测试
		*/
		
		public void getUserLoginLogTest() throws JsonProcessingException, IOException {
			String userName = "18810039018"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			String msg = UserCenter.getUserLoginLog(userId);
			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);
		}
		
		
		
		@Test
		/* 
		 * 与UClient集成实现U8C用户活跃度记录接口
		 * 正常情况的测试
		*/	
		public void testU8cLoginLog() throws JsonProcessingException, IOException{
			String msg = UserCenter.recordU8cLoginLog("18810039018","172.20.44.29",""+System.currentTimeMillis());
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		
		}
		
		
		
//		@Test
//		/* 
//		 * 快捷发送短信验证码
//		 * 正常情况的测试
//		 * 友文化特有，在接口文档里不显示
//		 * sdk.properties里改成client.credential.path=uculture.properties
//		 * 原值是client.credential.path=authfile_yht.txt
//		 * 同时把本地对应的authfile_yht.txt文件备份到其他地方，把uculture.properties放入即可
//		*/	
//		public void simpleSendCodeTest1() throws JsonProcessingException, IOException{
//
//			String jsonStr ="{\"mobile\":\"18810039018\", \"sysid\":\"uculture\"}";
//			String msg = UserCenter.simpleSendCode(jsonStr);
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt()==1);		
//		}

		
		
		
		@Test
		/* 
		 * 绑定手机时发短信不用图形校验码
		 * 正常情况的测试
		 * 友文化特有，在接口文档里不显示
		 * sdk.properties里改成client.credential.path=uculture.properties
		 * 原值是client.credential.path=authfile_yht.txt
		 * 同时把本地对应的authfile_yht.txt文件备份到其他地方，把uculture.properties放入即可
		*/	
		public void simpleSendCodeTest() throws JsonProcessingException, IOException{
			String jsonStr ="{\"mobile\":\"18810039018\", \"sysid\":\"uculture\"}";
			String msg = UserCenter.simpleSendCode(jsonStr);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		
		}
		
		
		
		
		@Test
		/* 
		 * 绑定微信账户
		 * 正常情况的测试
		 * 18810039018的ID是868d2718-4723-4504-aa03-a773918c2fdb
		 * openid、unionid随便输入，目前我们不能校验
		 * 友文化特有，在接口文档里不显示
		 * sdk.properties里改成client.credential.path=uculture.properties
		 * 原值是client.credential.path=authfile_yht.txt
		 * 同时把本地对应的authfile_yht.txt文件备份到其他地方，把uculture.properties放入即可
		*/	
		public void bindUserTest() throws JsonProcessingException, IOException{
			//绑定qq
			String jsonStr ="{\"type\":\"qq\", \"userid\":\"868d2718-4723-4504-aa03-a773918c2fdb\",\"openid\":\"aa\", \"unionid\":\"aa\"}";
			String msg = UserCenter.bindUser(jsonStr);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		

			
			//绑定微信
			String jsonStr1 ="{\"type\":\"wechat\", \"userid\":\"868d2718-4723-4504-aa03-a773918c2fdb\",\"openid\":\"aa\", \"unionid\":\"aa\"}";
			String msg1 = UserCenter.bindUser(jsonStr1);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==1);	

		}
		
		
		
		@Test
		/* 
		 * 登录校验
		 * 正常情况的测试
		*/	
		public void verifyUserTest() throws JsonProcessingException, IOException, InterruptedException{

			
			String userName="18611286701";
			String Password="yonyou11";
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			String md5Password=SDKUtils.encodeUsingMD5(Password); 
			String sysId="market";
			
			//估计把密码输入错误，让用户锁定
			for(int i=0;i<6;i++){
				String msg0 = UserCenter.verifyUser(userName,"00",sysId);
				System.out.println(msg0);
				JsonNode node0 = mapper.readTree(msg0);
				Assert.assertTrue(node0.get("status").asInt()==0);	
				Assert.assertTrue(node0.get("msg").asText().equals("用户名或密码错误"));
			}
			
			//锁定用户登录，给友好提示
			String msg = UserCenter.verifyUser(userName,Password,sysId);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==0);	
			Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));
			
			String msg1 = UserCenter.verifyUser(userName, Password, sysId, null, null);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
			Assert.assertTrue(node1.get("status").asInt()==0);	
			Assert.assertTrue(node1.get("msg").asText().equals("用户被锁定"));
			
			String msg2 = UserCenter.verifyUserUsingEncodePwd(userName, md5Password, shaPassword, sysId);
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
			Assert.assertTrue(node2.get("status").asInt()==0);	
			Assert.assertTrue(node2.get("msg").asText().equals("用户被锁定"));
			
			String msg3 = UserCenter.verifyUserUsingEncodePwd(userName, md5Password, shaPassword, sysId, null, null);
			System.out.println(msg3);
			JsonNode node3 = mapper.readTree(msg3);
			Assert.assertTrue(node3.get("status").asInt()==0);	
			Assert.assertTrue(node3.get("msg").asText().equals("用户被锁定"));
			
			
			//等待15分钟，用户解锁
			Thread.sleep(900000);
			
			//解锁后，登录成功
			String msg4 = UserCenter.verifyUser(userName,Password,sysId);
			System.out.println(msg4);
			JsonNode node4 = mapper.readTree(msg4);
			Assert.assertTrue(node4.get("status").asInt()==1);	
			
		}
		

		

		@Test
		/* 
		 * 获取nc项目的nc用户
		 * 正常情况的测试
		 * 7是NC的代表，L代表long 
		 * 设置yht.user.base.url =http://172.20.4.206:8080/rest   能查出一个数据   
		*/	
		public void listUserInNcProjectTest() throws JsonProcessingException, IOException{
			String msg = UserCenter.listUserInNcProject(7L,0,10);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		
		}

		
		@Test
		/* 
		 * 根据旧用户ID批量查询新用户信息
		 * 正常情况的测试
		 */	
		public void getUsersByOldIdsTest() throws JsonProcessingException, IOException{
			//前三个执行后结果是一样的，是jlccstt@126.com的合并前的三个ID，执行后查到新的信息。后面两个是运华给的。
			String [] oldUserids={"56624900baea","57624900baea","66624900baea","uspace_78796","uspace_79097"};		
			String msg = UserCenter.getUsersByOldIds(oldUserids);
			System.out.println(msg);
			JsonNode node = Utils.getJson(mapper, msg);
			Assert.assertTrue(node.get("status").asInt()==1);	
			
		}
	
		
		@Test  
		/* 更新用户Ca配置
		 * 正常情况的测试
		*/
		
		public void updateCaConfTest() throws JsonProcessingException, IOException {
			String userName = "18611286701"; 
			String userId = UserCenterUtil.getUserIdByLoginName(userName);
			//第一列是测试点
			//第二列参数userID
			//第三列返回值使用uKey
			//第四列返回值免密码登录(目前是预留字段，没作用)
			//第五列返回值status
			String [][] value={				
					{"1",userId,"false","false","1"},
//					{"2",userId,"false","true","1"},
//					{"3",userId,"true","false","1"},
//					{"4",userId,"true","true","1"},
			};	
			
			//返回值有msg的异常情况
			for(int j=0;j<1;j++){
			String msg = UserCenter.updateCaConf(value[j][1],Boolean.parseBoolean(value[j][2]),Boolean.parseBoolean(value[j][3]));
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][4]));
			Assert.assertTrue(node.get("ca").get("useUkey").asText().equals(value[j][2]));
			Assert.assertTrue(node.get("ca").get("noPassword").asText().equals(value[j][3]));
			}
					
		}
		
		@Test
		/* 
		 * 根据用户名密码获取uKey登录token
		 * 正常情况的测试
		*/	
		public void getUkeyRandomOrAccessTokenTest() throws JsonProcessingException, IOException{
			
			//第一列是测试点
			//第二列参数userID
			//第三列返回值使用uKey
			//第四列返回值免密码登录(目前是预留字段，没作用)
			//第五列返回值status
			String [][] value={				
//					{"用户","18810039018","1"},
					{"ukey用户","liudwc@yonyou.com","2"},
			};	
			
			String Password="liudwc123";
			String md5Password=SDKUtils.encodeUsingMD5(Password); 
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			
			for(int i=0;i<1;i++){
			String msg = UserCenter.getUkeyRandomOrAccessToken(value[i][1],md5Password,shaPassword,"",true);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value[i][2]));		
			
			}
		}
		
		
		@Test
		/* 
		 * 使用ukey签名token登录
		 * 正常情况的测试
		 * data 是getUkeyRandomOrAccessToken执行结果status为2时的值，即ukey用户才是2
		 * sign 是前台接口返回的结果，下面是接口测试代码的代码。
		 * 		var sign = window.YHTTool.sign("7437431d-2412-4b0f-b26d-0607beb3a948");
		 *		alert(sign);
		 *		window.console && console.log(sign);
		*/	
		public void uKeyLoginTest() throws JsonProcessingException, IOException{

			String msg = UserCenter.uKeyLogin("251af43c-d42f-4d0f-a509-3eb7a965518b","Lliav+IaVlY5CzgO125jfZ+P17rLWC766cZBFkol7z+Q5BHpBhgUeB5kB8J529jWL6OOjYWT+WUmkBlkjtXshmXHhCrcdqOOIMLHPYiYAjPkY1wTryhTbBAL0/GZckrHc3+zbCrUzS3HtxKNs6U3FxBS1i8VAA74rpLe8+Cufz/g4dgJxWXZs36DQFxzrcdNoY3MbZ4U+PxeGsTo12QBW+y9LEJb/Q3n17j71oRwdrUIt8u+9reRid9zLZduCEBrb8HvRTmUji9d3QepvI9rsXALTZ+qgD3tDINiOM9ZcRXw6U/IoKfiies2gzZXUBM6q7hPYjhiv483mzwf6FDdAw==",true);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		
			
		}
		
		@Test
		/* 
		 * 解密Ukey加密的数据
		 * 正常情况的测试
		 * encryptedStr  这个是前台加密方法：window.YHTTool.encrypt(data)，返回密文
		 * 用hfs.exe工具 ，加入yhtsec_test-suntt.html，在浏览器输入yhtsec_test-suntt.html，用户F12看
		 * 这个html里		
		 * var s = window.YHTTool.encrypt("123");
		 *alert(s);
		 *window.console && console.log(s);
		*/	
		public void decryptTest() throws JsonProcessingException, IOException{
			String encryptedStr="24da29af05c912748ef9a74558046c50P0R/8XI7FQyPSzWo0Bm39VdEq+m3ot7vrl62/c1ND7EJN7KmwBHtgJgLRGCR6gVweimzyY3Jzy6PlnOZKjCFbvEHhh0NvhCovsQpawQ2EjeIHbdVAHmpDCxVd4thKtACE5oghAEzO59IlLBVFHFDZepGLEqNRGNz1QP0D64EYq8=";
			String msg = UserCenter.decrypt(encryptedStr);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);		
			Assert.assertTrue(node.get("data").asText().equals("123"));	
		}
		
		
		@Test
		/* 
		 * 根据后端临时token获取后端登录Token
		 * 正常情况的测试
		 * btmpToken 让开发帮生成,这个值只能用一次
		 * 此用例为一次性用例
		*/	
		public void getBackSsoTokenByBTmpTokenTest() throws JsonProcessingException, IOException{
			String btmpToken="22cf91af-e1b7-46f1-a761-ee6e4294ea34";
			String msg = UserCenter.getBackSsoTokenByBTmpToken(btmpToken);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);	
			String accessToken= node.get("data").get("access_token").asText();	
			String msg1 = UserCenter.getUserByToken("a33ec817-cc52-4a37-9a29-be604af51b36");
			System.out.println(msg1);
			
		}
		
		
		
		@Test
		/* 
		 * 检测AccessToken是否过期
		 * 正常情况的测试
		*/	
		public void isAccessTokenValidTest() throws JsonProcessingException, IOException, InterruptedException{


			//获取access_token、refresh_token
			String msg=UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
			System.out.println("获取access_token、refresh_token:"+msg);
			JsonNode node1 = mapper.readTree(msg);
			Assert.assertTrue(node1.get("status").asInt()==1);	
			String accessToken=node1.get("data").get("access_token").asText();
			
			
			boolean msg1 = UserCenter.isAccessTokenValid(accessToken);
			System.out.println(msg1);
			Assert.assertTrue(msg1);
		
//			//销毁accessToken
//			String msg2 = UserCenter.destroyAccessToken(accessToken);
//			System.out.println(msg2);
//			JsonNode node2 = mapper.readTree(msg2);
//			Assert.assertTrue(node2.get("status").asInt()==1);			
			
			//等1分钟，此时access_token失效 
			Thread.sleep(60000);
//			String a= UserCenter.getUserByToken("7c07e50c-eb91-4c0a-8be3-274bdfad88da");
//			System.out.println(a);
			boolean msg3 = UserCenter.isAccessTokenValid(accessToken);
			System.out.println(msg3);
			Assert.assertFalse(msg3);
			
		}
		
		@Test
		public void isAccessTokenValidTest2() throws JsonProcessingException, IOException, InterruptedException{

			
			String a= UserCenter.getUserByToken("7c07e50c-eb91-4c0a-8be3-274bdfad88da");
			System.out.println(a);
		}
		
}



