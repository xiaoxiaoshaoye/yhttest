package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	/*根据用户ID获取用户信息
	 * 正常流程测试
	*/
	
	public void getUserLoginLogTest() {
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.getUserLoginLog(userId);
		System.out.println(msg);
	}
	
	
	@Test
	public void getUserByIdTest() throws JsonProcessingException, IOException{
		String userName="stt2017080201@chacuo.net";
		String userCode="YHT-stt2017080201code";
		String userId=UserCenterUtil.getUserIdByLoginName(userName);
		String msg=UserCenter.getUserById(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		JsonNode user = node.get("user");
		Assert.assertNotNull(user.get("userCode"));
		Assert.assertTrue(user.get("userCode").asText().equals(userCode));
	}

	

	
	@Test
	/* 
	 * 根据用户ID数组获取用户信息
	 * 正常情况的测试
	 * 用户ID数组输入正确的值
	*/
	public void getUserByPksTest() {
		String user1="suntt@yonyou.com";
		String user2="jlccstt@163.com";
		String userId1=UserCenterUtil.getUserIdByLoginName(user1);
		String userId2=UserCenterUtil.getUserIdByLoginName(user2);
		String [] userId = new String[] {userId1, userId2};
		String msg = UserCenter.getUserByPks(userId);
		System.out.println(msg);
	}
	
	@Test
	/* 
	 * 根据用户Code数组获取用户信息
	 * 正常情况的测试
	 * 用户Code数组输入正确的值
	*/
	public void getUserByCodesTest() {
		String userCode1="YHT-stt2017080201code";
		String userCode2="YHT-stt2017080701code";
		String [] userCodes = new String[] {userCode1, userCode2};
		String msg = UserCenter.getUserByCodes(userCodes);
		System.out.println(msg);
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
	public void getUserByLoginNameSidTest() {
		String LoginName="jlccstt@163.com";
		String Sid="aps9";
		String msg = UserCenter.getUserByLoginName(LoginName, Sid, null);
		// UserCenter.getUserByLoginName(userName, sysid, secretKey);
		System.out.println(msg);
	}
	
	@Test
	/* 
	 * 判断手机号格式是否正确
	 * 正常情况的测试
	 * 手机号输入正确的值
	*/
	public void isMobileTest() {
		String Mobile="18810039018";
		String msg = UserCenter.isMobile(Mobile);
		System.out.println(msg);
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
//			//Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(i));
//			}
	       }
		
		
		
		@Test
		/* 
		 *根据用户id数组和用户名称查找用户
		 *定义一个用户二维数组，第一列是用户ID，第二列是为了给测试自己看用户名是否包含s
		 *这样做出22个数据，前21个包含s，这样执行结果能出现分页。最后一个执行查不出来，因为不包括s
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
					{"cd52859c-83fa-4d58-8490-5a582cce9a72","stt2017082301@chacuo.net"},
					{"d387bf68-df9d-450e-a8e7-0eb0d57cdad5","stt2017082401@chacuo.net"},
					{"868d2718-4723-4504-aa03-a773918c2fdb","suntt@yonyou.com"},
					{"555efe32-1447-4f86-96cc-a972d440ea0b","suntt1026@126.com"},
					{"c1265234-fd39-4124-b00b-dcad33b6a3c2","stt851207@126.com"},
					{"afd48191-a519-486d-b60a-dedb0a163f1b","stt851207@163.com"},
					{"466249001234","jlccstt@163.com"},
					{"47624900baea","jlccstt@126.com"},
					{"488e6137-e684-4d40-86ea-a619d43c5a50","18611286701@chacuo.net"}};	
			String [] pks=new String[users.length];			
			for(int i=0;i<users.length;i++){
				pks[i]=users[i][0];
			}
			String  userName="s";
			String msg = UserCenter.searchUser(pks,userName);
			System.out.println(msg);
			
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("users").get("totalElements").asInt() == 21);
			
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
			String  userName="s";
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
			String  userName="s";
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
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
//			Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
//			Assert.assertTrue(node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));
//			
		
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
		 * 批量导入用户
		 * 正常情况的测试
		 * 这个接口不用了，调用应该代码就报错了
		*/
		public void addAppUsersTest()  {
			
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
			String msg = UserCenter.addAppUsers(systemCode, jsonStr);
			System.out.println(msg);
		
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
			
//			//修改昵称
//			String key="userName";
//			String value=d+"name";			
//			String msg = UserCenter.updateUserProperties(userId,key,value);
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt()==1);
//			Assert.assertTrue(node.get("msg").asText().equals("修改用户属性成功"));
//
//			//修改性别
//			String key1="sex";
//			String value1="1";		
//			String msg1 = UserCenter.updateUserProperties(userId,key1,value1);
//			System.out.println(msg1);
//			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt()==1);
//			Assert.assertTrue(node1.get("msg").asText().equals("修改用户属性成功"));
//
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
//
//			
			//修改地址
			String key3="address";
			String value3="1-11-1";		
			String msg3 = UserCenter.updateUserProperties(userId,key3,value3);
			System.out.println(msg3);
			JsonNode node3 = mapper.readTree(msg3);
			Assert.assertTrue(node3.get("status").asInt()==1);
			Assert.assertTrue(node3.get("msg").asText().equals("修改用户属性成功"));

//
//			//修改userCode
//			String key4="userCode";
//			String value4=d+"Code";			
//			String msg4 = UserCenter.updateUserProperties(userId,key4,value4);
//			System.out.println(msg4);
//			JsonNode node4 = mapper.readTree(msg4);
//			Assert.assertTrue(node4.get("status").asInt()==1);
//			Assert.assertTrue(node4.get("msg").asText().equals("修改用户属性成功"));


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
			params.put("address", "1-11-2-哈哈w");
			params.put("birthday", "2010年9月6日");
			params.put("userCode", d+"code");
			
			String msg = UserCenter.updateUserMultiProperties(userId,params);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==1);
			Assert.assertTrue(node.get("successmsg").get(0).asText().equals("'birthday'属性修改成功"));
			Assert.assertTrue(node.get("successmsg").get(1).asText().equals("'sex'属性修改成功"));
			Assert.assertTrue(node.get("successmsg").get(2).asText().equals("'userName'属性修改成功"));
			Assert.assertTrue(node.get("successmsg").get(3).asText().equals("'userCode'属性修改成功"));
		
		}
		
		
		
		
}



