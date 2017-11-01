package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


public class UserCenterExceptionTestSuntt {

	ObjectMapper mapper;
	
	@Before
	public void init() {
		mapper = new ObjectMapper();
	}

	@Test  
	/*获取用户登录信息
	 * 异常情况的测试
	 * userId未空或错误时应该不能查出数据
	*/
	
	public void getUserLoginLogExceptionTest() throws JsonProcessingException, IOException  {
		
		//参数为空
		String msg = UserCenter.getUserLoginLog("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("没有登录日志"));
		
		//参数是随便输入的值
		String msg1 = UserCenter.getUserLoginLog("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("没有登录日志"));
		
		//参数是null
		String msg2 = UserCenter.getUserLoginLog(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("msg").asText().equals("没有登录日志"));
	}
	
	@Test
	/*根据用户ID获取用户信息
	 * 异常情况的测试
	 * 用户ID为空或错误时应该不能查出数据
	*/
	public void getUserByIdExceptionTest() throws JsonProcessingException, IOException  {
		
		//参数为空
		String msg = UserCenter.getUserById("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));
		
		//参数是随便输入的值
		String msg1 = UserCenter.getUserById("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("未找到用户"));
		
		//参数是null
		String msg2 = UserCenter.getUserById(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
	}
	
	@Test
	/* 
	 * 根据用户ID数组获取用户信息
	 * 异常情况的测试
	 * 用户ID数组为空或错误时应该不能查出数据
	*/
	public void getUserByPksExceptionTest()  {
		String [] pks=new String [] {"",""};
		String [] pks1=new String [] {"随便乱输入的内容哈哈","随便乱输入的内容haha"};
		String  msg = UserCenter.getUserByPks(pks);
		String  msg1 = UserCenter.getUserByPks(pks1);
		System.out.println(msg);
		System.out.println(msg1);
	}
	
	@Test
	/* 
	 * 根据用户Code数组获取用户信息
	 * 异常情况的测试
	 * 用户Code数组输入空或者不存在的值
	*/
	public void getUserByCodesExceptionTest() {

		String [] userCodes=new String [] {"",""};
		String [] userCodes1=new String [] {"随便乱输入的内容哈哈","随便乱输入的内容haha"};
		String msg = UserCenter.getUserByCodes(userCodes);
		String msg1 = UserCenter.getUserByCodes(userCodes1);
		System.out.println(msg);
		System.out.println(msg1);
	}
	
	@Test
	/* 
	 * 根据登录名获取用户信息
	 * 异常情况的测试
	 * 登录名输入空、不存在的值、null
	*/

	public void getUserByLoginNameExceptionTest() {
		String [] LoginName={"","随便乱输入的内容haha",null};
		String [] m={"用户名称不能为空","未找到用户随便乱输入的内容haha","用户名称不能为空"};
		for(int i=0;i<LoginName.length;i++){
			String msg = UserCenter.getUserByLoginName(LoginName[i]);
			System.out.println(msg);
			JsonNode node = Utils.getJson(mapper, msg);
 		    Assert.assertTrue(node.get("msg").asText().equals(m[i]));
		}
		
	}
	
	@Test
	/* 
	 * 根据登录名，用户SysID获取用户信息，针对冲突用户
	 * 异常情况的测试
	 * 登录名、用户SysID输入空或者不存在的值
	*/
	public void getUserByLoginNameSidExceptionTest() {
		String text="随便乱输入的内容haha";
		String msg = UserCenter.getUserByLoginName("", "", "");
		String msg1 = UserCenter.getUserByLoginName(text, text, text);
		System.out.println(msg);
		System.out.println(msg1);
	}

	@Test
	/* 
	 * 判断手机号格式是否正确
	 * 正常情况的测试
	 * 手机号输入正确的值
	*/
	public void isMobileExceptionTest() {
		String Mobile="";
		String Mobile1="1";
		String Mobile2="10000000000";
		String Mobile3="1880000000a";
		String msg = UserCenter.isMobile(Mobile);
		String msg1 = UserCenter.isMobile(Mobile1);
		String msg2 = UserCenter.isMobile(Mobile2);
		String msg3 = UserCenter.isMobile(Mobile3);
		System.out.println(msg);
		System.out.println(msg1);
		System.out.println(msg2);
		System.out.println(msg3);
		
	}
	
	@Test
	/* 
	 * 判断用户编码是否已经存在
	 * 异常情况的测试
	 * 用户编码输入一个不符合规则的值，和不输入值
	*/
	public void isUserCodeExistExceptionTest() {
		String [] userCode={"随便乱输入的内容haha",""};
		for(int j=0;j<2;j++){
		String msg = UserCenter.isUserCodeExist(userCode[j]);
		System.out.println(msg);
		}
	}
	
	
	@Test
	/* 
	 * 根据登录名判断用户是否存在
	 * 异常情况的测试
	 * 登录名不输入值
	*/
	public void isUserExistExceptionTest() {
		String loginName="";
		String msg = UserCenter.isUserExist(loginName);
		System.out.println(msg);

	}
	
	@Test
	/* 
	 * 根据登录名和Sysid判断用户是否存在
	 * 异常情况的测试
	 * 登录名和Sysid不输入值时给友好提示;
	 * 登录名为空，Sysid正确的值，给友好提示；
	 * 登录名有正确的值，Sysid为空时，能查出数据，与public static String isUserExist(String loginName)效果一样
	*/
	public void isUserExist2ExceptionTest() {
		String [] loginName={"","","jlccstt@163.com"};
		String [] sysid={"","market",""};
		for(int j=0;j<3;j++){
		String msg = UserCenter.isUserExist(loginName[j],sysid[j]);
		System.out.println(msg);
		}
	}
	
	@Test
	/* 
	 * 根据userCode、userMobile、userEmail判断是否能够增加用户
	 * 异常情况的测试
	 * 参数都不输入值
	*/
	public void isUserExist3ExceptionTest() {			
		String msg = UserCenter.isUserExist("","","");
		System.out.println(msg);
}
	
	@Test
	/* 
	 * 用户事件查询(根据时间段、sysid)
	 * 异常情况的测试
	 * 参数都不输入值
	 */
	public void searchEventByTimeExceptionTest() {

			String [][] value={
					{"","","","0","0",""},
					{"2017-01-01","2017-08-18","yht","-1","1","ts"},
					{"2017-01-01","2017-08-18","yht","1","-1","ts"},
					{"2017-01-01","2017-08-18","yht","1000000000","100","ts"},
					{"2017-01-01","2017-08-18","yht","100","1000000000","ts"},
					{"aa","2017-08-18","yht","6","1","ts"},
					{"2017-01-01","aa","yht","6","1","ts"},
					{"2017-01-01","2017-08-18","随便乱输入的内容","6","1","ts"},
					{"2017-01-01","2017-08-18","yht","6","1","随便乱输入的内容"}
					};			
			for(int j=0;j<9;j++){
			String msg =  UserEventCenter.searchEventByTime(value[j][0], value[j][1], value[j][2], Integer.valueOf(value[j][3]), Integer.valueOf(value[j][4]), value[j][5]);
			System.out.println(msg);
			}
		}	
	
	@Test
	/* 
	 * 批量根据userCode、userMobile、userEmail判断是否能够增加用户
	 * 异常情况的测试
	 * 参数都不输入
	 */
	public void isUsersExistExceptionTest() throws JsonProcessingException, IOException {		
		Map<String, Object> params = new HashMap<String, Object>();			
		List<Object> users = new ArrayList<Object>();			
		
		Map<String, String> user = new HashMap<String, String>() ;
		user.put("userCode","");
		user.put("userMobile", "");
		user.put("userEmail","");
		users.add(user);
				
		params.put("users", users);
		String jsonStr = Utils.getJsonStr(mapper, params);
		System.out.println(jsonStr);
		String msg = UserCenter.isUsersExist(jsonStr);		
		System.out.println(msg);
		

       }
	
	
	@Test
	/* 
	 *根据用户id数组和用户名称查找用户
	 *异常测试
	*/
	public void searchUserExceptionTest() throws JsonProcessingException, IOException {
		//一个是随便输入的不存在的值，这样查询出来的元素总个数是0
		String []pks={"随便乱输入的内容haha"};
		String  userName="s";
		String msg = UserCenter.searchUser(pks,userName);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 0);
		
		//用户ID数组里是3个正确的值，用户名没有输入值，这样查询出来的元素总个数是3
		String [][] users={{"cac3b9f8-f7f0-455f-bf44-7b899865271d","stt2017080201@chacuo.net"},
				{"273ccd32-749c-451c-8784-9d9dff3c3160","stt2017080202@chacuo.net"},
				{"488e6137-e684-4d40-86ea-a619d43c5a50","18611286701@chacuo.net"}};	
		String [] pks1=new String[users.length];			
		for(int i=0;i<users.length;i++){
			pks1[i]=users[i][0];
		}
		String  userName1="";
		String msg1 = UserCenter.searchUser(pks1,userName1);
		System.out.println(msg1);
		
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("users").get("totalElements").asInt() == 3);
		
		//参数都为空，应该给友好提示，因为ID数据应该不能为空，用户名可以为空。
		String []pks2={""};
		String  userName2="";
		String msg2 = UserCenter.searchUser(pks2,userName2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("msg").asText().equals("Userids不能为空"));
	
		//参数都为null，给友好提示
		String msg3 = UserCenter.searchUser(null,null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("msg").asText().equals("pks不能为空"));
	
    }
	@Test
	/* 
	 *根据用户id数组和用户名称查找用户（需sysid,secretKey）
	 *sysid,secretKey是开发给的，直接用就可以
	 *异常测试
	*/
	public void searchUser1ExceptionTest() throws JsonProcessingException, IOException {
		//用户id数组是随便输入的不存在的值，这样查询出来的元素总个数是0
		String []pks={"随便乱输入的内容haha"};
		String  userName="s";
		String  sysid="market";
		String  secretKey="d977a56a7584b02b";
		
		String msg = UserCenter.searchUser(pks,userName,sysid,secretKey);
		System.out.println(msg);
		
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 0);
		
		//用户ID数组里是2个正确的值，sysid,secretKey是正确的解密值，用户名没有输入值，这样查询出来的元素总个数是2
		//返回值里邮箱是解密的
		String [][] users={{"cac3b9f8-f7f0-455f-bf44-7b899865271d","stt2017080201@chacuo.net"},
				{"488e6137-e684-4d40-86ea-a619d43c5a50","18611286701@chacuo.net"}};	
		String  userEmail=users[0][1];
		String [] pks1=new String[users.length];			
		for(int i=0;i<users.length;i++){
			pks1[i]=users[i][0];
		}
		String msg1 = UserCenter.searchUser(pks1,"",sysid,secretKey);	
		System.out.println(msg1);
		
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("users").get("totalElements").asInt() == 2);
		
		//参数都为空，应该给友好提示，因为ID数据应该不能为空，用户名可以为空。
		String []pks2={""};
		String msg2 = UserCenter.searchUser(pks2,"","","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("msg").asText().equals("Userids不能为空"));
		//参数都为null，给友好提示
		String msg3 = UserCenter.searchUser(null,null,null,null);
		System.out.println(msg3);
		
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("msg").asText().equals("pks不能为空"));
		
		//sysid,secretKey随便乱输入，查出来的数据是加密的，邮箱有星号
		String msg4 = UserCenter.searchUser(pks1,"s","随便乱输入的内容haha","随便乱输入的内容haha");	
		System.out.println(msg4);
		
		String  userEmail1="stt20170802*****@chacuo.net";
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("users").get("totalElements").asInt() == 1);
		Assert.assertTrue(node4.get("users").get("content").get(0).get("userEmail").asText().equals(userEmail1));
	
	
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
	public void searchUser2ExceptionTest() throws JsonProcessingException, IOException {
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
		
		String msg = UserCenter.searchUser(pks,"",ps,pn,"","",sysid,secretKey);
		System.out.println(msg);			
		JsonNode node = mapper.readTree(msg);
		//因为查询条件是空，就查出全部数据，10条数据，一页4条，第三页就是2条，默认排序，就是按code排序，最后一页第二条数据就是stt09@chacuo.net
		Assert.assertTrue(node.get("users").get("numberOfElements").asInt() == 2);	
		Assert.assertTrue(node.get("users").get("content").get(1).get("userEmail").asText().equals("stt09@chacuo.net"));			
		
		//参数都为空，应该给友好提示，因为ID数据应该不能为空，用户名可以为空。
		String []pks2={""};
		String msg2 = UserCenter.searchUser(pks2,"","","","","","","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("msg").asText().equals("Userids不能为空"));
		
		//参数都为null，给友好提示
		String msg3 = UserCenter.searchUser(null,null,null,null,null,null,null,null);
		System.out.println(msg3);		
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("msg").asText().equals("pks不能为空"));
	
		//pks有值，其他都是null,查出全部数据
		String msg4 = UserCenter.searchUser(pks,null,null,null,null,null,null,null);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg);
		Assert.assertTrue(node4.get("users").get("totalElements").asInt() == 10);
		
		//pks有值，其他都是空,查出全部数据
		String msg5 = UserCenter.searchUser(pks,"","","","","","","");
		System.out.println(msg4);
		JsonNode node5 = mapper.readTree(msg);
		Assert.assertTrue(node4.get("users").get("totalElements").asInt() == 10);
	
	}		
	
	@Test
	/* 
	 * 根据联系方式及类型查询用户列表
	 * 异常情况的测试
	*/
	public void getUserByContactExceptionTest() throws JsonProcessingException, IOException {
		String [][] users={
				{"18855559876","mobile"},
				{"abcdteg1abcdteg2abcdteg3@yonyou.com","email"},
				{"",""},
				{null,null},
				{"随便乱输入的内容haha","mobile"},
				{"随便乱输入的内容haha","email"},
				{"随便乱输入的内容haha","随便乱输入的内容haha"}
				};
		for(int j=0;j<users.length;j++){
		String msg = UserCenter.getUserByContact(users[j][0],users[j][1]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
//		Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
//		Assert.assertTrue(node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));
//		
		}
	
	}
	
	
	@Test
	/* 
	 * 根据联系方式查询用户列表
	 * 异常情况的测试
	*/
	public void getUserByContactsExceptionTest() throws JsonProcessingException, IOException {
		String [][] users={
				{"18611286701","abcdteg1abcdteg2abcdteg3@yonyou.com"},
				{"18855559876","yixixinzi@126.com"},
				{"18855559876","abcdteg1abcdteg2abcdteg3@yonyou.com"},
				{null,null},
				{"",""},
				{"18611286701","随便乱输入的内容haha"},
				{"随便乱输入的内容haha","yixixinzi@126.com"},
				{"随便乱输入的内容haha","随便乱输入的内容haha"},
				{"18810039018","yixixinzi@126.com"},
				};
		for(int j=0;j<users.length;j++){
		String msg = UserCenter.getUserByContacts(users[j][0],users[j][1]);
		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("users").get(0).get("userCode").asText().equals("YHT-18611286701-code"));
//		Assert.assertTrue(node.get("users").get(0).get("userName").asText().equals("YHT-18611286701-name"));
//		Assert.assertTrue(node.get("users").get(0).get("userId").asText().equals("488e6137-e684-4d40-86ea-a619d43c5a50"));
//		
		}
	}
	
	
	@Test
	/* 
	 * 根据字符串模糊查询用户列表（前缀匹配、支持分页）
	 * 异常情况的测试
	*/
	public void searchUserByFilterExceptionTest() throws JsonProcessingException, IOException {

//		//字符串参数为空，数据为0
//		String msg = UserCenter.searchUserByFilter("",0,0,"");
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);	
//		Assert.assertTrue(node.get("msg").asText().equals("过滤条件不能为空"));
//		
//		//filter不可以是一位的字母
//		String msg1 = UserCenter.searchUserByFilter("s",5,2,"");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt()==0);	
//		Assert.assertTrue(node1.get("msg").asText().equals("过滤字符串格式不正确"));
//		
//		//filter不可以是一位的数字
//		String msg2 = UserCenter.searchUserByFilter("8",5,2,"");
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt()==0);	
//		Assert.assertTrue(node2.get("msg").asText().equals("过滤字符串格式不正确"));
//		
//		//不可以是以1开头的两位数字
//		String msg3 = UserCenter.searchUserByFilter("18",5,2,"");
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt()==0);	
//		Assert.assertTrue(node3.get("msg").asText().equals("过滤字符串格式不正确"));
//
//		//过滤字符串随便输入值，这样查出来的数据就是0
//		String msg4 = UserCenter.searchUserByFilter("随便乱输入的内容haha",5,1,"");
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt()==1);	
//		Assert.assertTrue(node4.get("users").get("numberOfElements").asInt()==0);	
//		
		//排序是随便输入的值，应该是""  null   name    auto  这四种，其他值都不正确
		String msg5 = UserCenter.searchUserByFilter("stt",5,1,"随便乱输入的内容haha");
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		//Assert.assertTrue(node5.get("status").asInt()==0);	
		
		//pageSize页面大小、pageNumber第几页：应该控制是正整数
		String msg6 = UserCenter.searchUserByFilter("stt",-1,-1,"随便乱输入的内容haha");
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		//Assert.assertTrue(node6.get("status").asInt()==0);	
//		
//		//过滤字符串是null
//		String msg7 = UserCenter.searchUserByFilter(null,5,1,"");
//		System.out.println(msg7);
//		JsonNode node7 = mapper.readTree(msg7);
//		Assert.assertTrue(node7.get("status").asInt()==0);	
//		Assert.assertTrue(node7.get("msg").asText().equals("过滤条件不能为空"));
//
//		//过滤字符串是stt,其他参数都是null,因为stt的数据多，查出肯定超过20条，所以肯定是满页数据
//		String msg8 = UserCenter.searchUserByFilter("stt",null,null,null);
//		System.out.println(msg8);
//		JsonNode node8 = mapper.readTree(msg8);
//		Assert.assertTrue(node8.get("status").asInt()==1);	
//		Assert.assertTrue(node8.get("numberOfElements").asInt()==20);

	}
	
	
	@Test
	/* 
	 * 增加用户
	 * 异常情况的测试
	*/
	public void addUserExceptionTest() throws JsonProcessingException, IOException {

		//账号已存在
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode =date.format(new Date());
		params.put("userCode", "YHT-18810039018");
		params.put("userName", userCode+"name");
		params.put("userEmail", userCode+"@chacuo.net");		
		//params.put("userEmail", "suntt@yonyou.com");	
		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("YHT-18810039018 用户帐号已经存在"));
		
		
		//邮箱已存在
		SimpleDateFormat date1 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode1 =date1.format(new Date());
		params.put("userCode", userCode1+"code");
		params.put("userName", userCode1+"name");
		params.put("userEmail", "suntt@yonyou.com");	
		String msg1 = UserCenter.addUser(params);
		System.out.println(msg1);			
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("suntt@yonyou.com 邮箱已存在"));
	
		//手机号已存在
		Map<String, String> params2 = new HashMap<String, String>();
		SimpleDateFormat date2 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode2 =date2.format(new Date());
		params2.put("userCode", userCode2+"code");
		params2.put("userName", userCode2+"name");
		params2.put("userMobile", "18810039018");	
		String msg2 = UserCenter.addUser(params2);
		System.out.println(msg2);			
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("18810039018 手机号已存在"));
	
		//手机号和邮箱不能同时为空
		Map<String, String> params3 = new HashMap<String, String>();
		SimpleDateFormat date3 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode3 =date3.format(new Date());
		params3.put("userCode", userCode3+"code");
		params3.put("userName", userCode3+"name");	
		String msg3 = UserCenter.addUser(params3);
		System.out.println(msg3);			
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("手机号和邮箱不能同时为空"));

		//userCode不传值
		Map<String, String> params4 = new HashMap<String, String>();
		SimpleDateFormat date4 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode4 =date4.format(new Date());
		params4.put("userName", userCode4+"name");
		params4.put("userEmail", userCode4+"@chacuo.net");		
		String msg4 = UserCenter.addUser(params4);
		System.out.println(msg4);			
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==1);
		
		
		//userName不传值
		SimpleDateFormat date5 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode5 =date5.format(new Date());
		params.put("userCode", userCode5+"code");
		params.put("userEmail", userCode5+"@chacuo.net");	
		String msg5 = UserCenter.addUser(params);
		System.out.println(msg5);			
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==1);
	
		
		//userCode的值是随便乱输入的内容
		Map<String, String> params6 = new HashMap<String, String>();
		SimpleDateFormat date6 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode6 =date6.format(new Date());
		params6.put("userCode", userCode6+"随便乱输入的内容haha");
		params6.put("userName", userCode6+"name");
		params6.put("userEmail", userCode6+"@chacuo.net");		
		String msg6 = UserCenter.addUser(params6);
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==1);
	
		//name的值是敏感值，现在存成了**功
		Map<String, String> params7 = new HashMap<String, String>();
		SimpleDateFormat date7 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode7 =date7.format(new Date());
		params7.put("userCode", userCode7+"code");
		params7.put("userName", "法轮功");
		params7.put("userEmail", userCode7+"@chacuo.net");		
		String msg7 = UserCenter.addUser(params7);
		System.out.println(msg7);
		JsonNode node7 = mapper.readTree(msg7);
		Assert.assertTrue(node7.get("status").asInt()==1);
		
		//userCode超长,应该给友好提示
		Map<String, String> params8 = new HashMap<String, String>();
		SimpleDateFormat date8 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode8 =date8.format(new Date());
		params8.put("userCode", userCode8+"0000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
		params8.put("userName", userCode8+"name");
		params8.put("userEmail", userCode8+"@chacuo.net");		
		String msg8 = UserCenter.addUser(params8);
		System.out.println(msg8);
		JsonNode node8 = mapper.readTree(msg8);
		Assert.assertTrue(node8.get("status").asInt()==0);
		Assert.assertTrue(node8.get("msg").asText().equals("帐号格式不正确"));

		
		//userName超长,应该给友好提示
		Map<String, String> params9 = new HashMap<String, String>();
		SimpleDateFormat date9 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode9 =date9.format(new Date());
		params9.put("userCode", userCode9+"code");
		params9.put("userName", userCode9+"0000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
		params9.put("userEmail", userCode9+"@chacuo.net");		
		String msg9 = UserCenter.addUser(params9);
		System.out.println(msg9);
		JsonNode node9 = mapper.readTree(msg9);
		Assert.assertTrue(node9.get("status").asInt()==0);
		//Assert.assertTrue(node9.get("msg").asText().equals("用户名不能超过64个字符"));

		//userEmail格式不正确
		Map<String, String> params10 = new HashMap<String, String>();
		SimpleDateFormat date10 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode10 =date10.format(new Date());
		params10.put("userCode", userCode10+"code");
		params10.put("userName", userCode10+"name");
		params10.put("userEmail", "随便乱输入的内容haha");		
		String msg10 = UserCenter.addUser(params10);
		System.out.println(msg10);
		JsonNode node10 = mapper.readTree(msg10);
		Assert.assertTrue(node10.get("status").asInt()==0);
		Assert.assertTrue(node10.get("msg").asText().equals("邮箱格式不正确"));

		//userMobile格式不正确
		Map<String, String> params11 = new HashMap<String, String>();
		SimpleDateFormat date11 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode11 =date11.format(new Date());
		params11.put("userCode", userCode11+"code");
		params11.put("userName", userCode11+"name");
		params11.put("userMobile", "随便乱输入的内容haha");		
		String msg11 = UserCenter.addUser(params11);
		System.out.println(msg11);
		JsonNode node11 = mapper.readTree(msg11);
		Assert.assertTrue(node11.get("status").asInt()==0);
		Assert.assertTrue(node11.get("msg").asText().equals("手机号格式不正确"));

		//userEmail、userMobile的值为""
		Map<String, String> params12 = new HashMap<String, String>();
		SimpleDateFormat date12 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode12 =date12.format(new Date());
		params12.put("userEmail", "");	
		params12.put("userMobile", "");
		String msg12 = UserCenter.addUser(params12);
		System.out.println(msg12);
		JsonNode node12 = mapper.readTree(msg12);
		Assert.assertTrue(node12.get("status").asInt()==0);
		Assert.assertTrue(node12.get("msg").asText().equals("手机号和邮箱不能同时为空"));
		
		//userEmail的、userMobile值为null
		Map<String, String> params13 = new HashMap<String, String>();
		SimpleDateFormat date13 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode13 =date13.format(new Date());
		params13.put("userEmail", null);	
		params13.put("userMobile", null);	
		String msg13 = UserCenter.addUser(params13);
		System.out.println(msg13);
		JsonNode node13 = mapper.readTree(msg13);
		Assert.assertTrue(node13.get("status").asInt()==0);
		Assert.assertTrue(node13.get("msg").asText().equals("手机号和邮箱不能同时为空"));

		
	}	
	
	
	@Test
	/* 
	 * 批量增加用户
	 * 异常情况的测试
	*/
	public void addUsersExceptionTest() throws JsonProcessingException, IOException  {
		
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode =date.format(new Date());		
		String systemCode = "yht";	    

//		//第一个用户的邮箱格式不正确，第二个正确，执行结果整体失败，第一个提示邮箱格式不正确，第二个能增加成功
//		Map<String, String> user11 = new HashMap<String, String>();
//		user11.put("userEmail", "随便乱输入的内容haha");			
//		Map<String, String> user12 = new HashMap<String, String>();
//		user12.put("userEmail", userCode+1+"@chacuo.net");			
//		Map<String, Object> params1 = new HashMap<String, Object>();
//		List<Object> users = new ArrayList<Object>();
//		users.add(user11);
//		users.add(user12);
//		params1.put("users", users);
//		String jsonStr = Utils.getJsonStr(mapper, params1);
//		System.out.println(jsonStr);
//		String msg = UserCenter.addUsers(systemCode, jsonStr);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);
//		Assert.assertTrue(node.get("msg").asText().equals("保存失败"));
//		Assert.assertTrue(node.get("errors").get(0).asText().equals("用户1:邮箱格式不正确"));
//		Assert.assertTrue(node.get("users").get(0).get("userEmail").asText().equals(userCode+1+"@chacuo.net"));
//
//		//第一个用户的手机格式不正确，第二个正确，执行结果整体失败，第一个提示邮箱格式不正确，第二个能增加成功
//		Map<String, String> user21 = new HashMap<String, String>();
//		user21.put("userMobile", "随便乱输入的内容haha");			
//		Map<String, String> user22 = new HashMap<String, String>();
//		user22.put("userEmail", userCode+2+"@chacuo.net");			
//		Map<String, Object> params2 = new HashMap<String, Object>();
//		List<Object> users2 = new ArrayList<Object>();
//		users2.add(user21);
//		users2.add(user22);
//		params2.put("users", users2);
//		String jsonStr2 = Utils.getJsonStr(mapper, params2);
//		System.out.println(jsonStr2);
//		String msg2 = UserCenter.addUsers(systemCode, jsonStr2);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt()==0);
//		Assert.assertTrue(node2.get("msg").asText().equals("保存失败"));
//		Assert.assertTrue(node2.get("errors").get(0).asText().equals("用户1:手机号格式不正确"));
//		Assert.assertTrue(node2.get("users").get(0).get("userEmail").asText().equals(userCode+2+"@chacuo.net"));
//
//		//第一个用户的手机格式不正确，第二个邮箱格式不正确
//		Map<String, String> user31 = new HashMap<String, String>();
//		user31.put("userMobile", "随便乱输入的内容haha");			
//		Map<String, String> user32 = new HashMap<String, String>();
//		user32.put("userEmail", "随便乱输入的内容haha");			
//		Map<String, Object> params3 = new HashMap<String, Object>();
//		List<Object> users3 = new ArrayList<Object>();
//		users3.add(user31);
//		users3.add(user32);
//		params3.put("users", users3);
//		String jsonStr3 = Utils.getJsonStr(mapper, params3);
//		System.out.println(jsonStr3);
//		String msg3 = UserCenter.addUsers(systemCode, jsonStr3);
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt()==0);
//		Assert.assertTrue(node3.get("msg").asText().equals("保存失败"));
//		Assert.assertTrue(node3.get("errors").get(0).asText().equals("用户1:手机号格式不正确"));
//		Assert.assertTrue(node3.get("errors").get(1).asText().equals("用户2:邮箱格式不正确"));
//
//		//第一个用户的手机格式不正确，第二个手机号和邮箱都为空
//		Map<String, String> user41 = new HashMap<String, String>();
//		user41.put("userMobile", "随便乱输入的内容haha");			
//		Map<String, String> user42 = new HashMap<String, String>();
//	
//		Map<String, Object> params4 = new HashMap<String, Object>();
//		List<Object> users4 = new ArrayList<Object>();
//		users4.add(user41);
//		users4.add(user42);
//		params4.put("users", users4);
//		String jsonStr4 = Utils.getJsonStr(mapper, params4);
//		System.out.println(jsonStr4);
//		String msg4 = UserCenter.addUsers(systemCode, jsonStr4);
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt()==0);
//		Assert.assertTrue(node4.get("msg").asText().equals("保存失败"));
//		Assert.assertTrue(node4.get("errors").get(0).asText().equals("用户1:手机号格式不正确"));
//		Assert.assertTrue(node4.get("errors").get(1).asText().equals("用户2:手机号和邮箱不能同时为空"));
//
//		
//		  //只有一个正确的用户
//		  Map<String, String> user51 = new HashMap<String, String>();
//		  user51.put("userEmail", userCode+5+"@chacuo.net");   		 
//		  Map<String, Object> params5 = new HashMap<String, Object>();
//		  List<Object> users5 = new ArrayList<Object>();
//		  users5.add(user51);
//		  params5.put("users", users5);
//		  String jsonStr5 = Utils.getJsonStr(mapper, params5);
//		  System.out.println(jsonStr5);
//		  String msg5 = UserCenter.addUsers(systemCode, jsonStr5);
//		  System.out.println(msg5);
//		  JsonNode node5 = mapper.readTree(msg5);
//		  Assert.assertTrue(node5.get("status").asInt()==1);
//		  Assert.assertTrue(node5.get("msg").asText().equals("保存成功"));
//   	  Assert.assertTrue(node5.get("users").get(0).get("userEmail").asText().equals(userCode+5+"@chacuo.net"));
//
//	
		
	    //用户信息格式不正确，是随便输入的内容
	    String msg6 = UserCenter.addUsers(systemCode, "随便乱输入的内容haha");
	    System.out.println(msg6);
	    JsonNode node6 = mapper.readTree(msg6);
	    Assert.assertTrue(node6.get("status").asInt()==0);
	    Assert.assertTrue(node6.get("msg").asText().equals("保存失败"));

	    
//	     //systemCode是随便输入的内容
//	    Map<String, String> user71 = new HashMap<String, String>();
//	    user71.put("userEmail", userCode+7+"@chacuo.net");      
//	    Map<String, Object> params7 = new HashMap<String, Object>();
//	    List<Object> users7 = new ArrayList<Object>();
//	    users7.add(user71);
//	    params7.put("users", users7);
//	    String jsonStr7 = Utils.getJsonStr(mapper, params7);
//	    System.out.println(jsonStr7);
//	    String msg7 = UserCenter.addUsers("随便乱输入的内容haha", jsonStr7);
//	    System.out.println(msg7);
//	    JsonNode node7 = mapper.readTree(msg7);
//	    Assert.assertTrue(node7.get("status").asInt()==1);
//	    Assert.assertTrue(node7.get("msg").asText().equals("保存成功"));
//	    Assert.assertTrue(node7.get("users").get(0).get("userEmail").asText().equals(userCode+7+"@chacuo.net"));
//	
//
//	     //参数都是空
//	     String msg8 = UserCenter.addUsers("", "");
//	     System.out.println(msg8);
//	     JsonNode node8 = mapper.readTree(msg8);
//	     Assert.assertTrue(node8.get("status").asInt()==0);
//	     Assert.assertTrue(node8.get("msg").asText().equals("系统编码不能为空"));	     
//
//	     
//	     
//	   //参数都是null
//	     String msg9 = UserCenter.addUsers(null, null);
//	     System.out.println(msg9);
//	     JsonNode node9 = mapper.readTree(msg9);
//	     Assert.assertTrue(node9.get("status").asInt()==0);
//	     Assert.assertTrue(node9.get("msg").asText().equals("系统编码不能为空"));	
//	     
//	     
	}	
	
	
	
	
	
	
	@Test
	/* 
	 * 根据激活码激活用户
	 * 

	*/
	public void activeUserByCodeExceptionTest() throws JsonProcessingException, IOException {
		
		//手机号正确，验证码错误
		String msg = UserCenter.activeUserByCode("18611286701","78481");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		
		//手机号不存在
		String msg1 = UserCenter.activeUserByCode("随便乱输入的内容haha","784814");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("请输入正确的手机号或者邮箱"));
		
		//参数都是空字符串
		String msg2 = UserCenter.activeUserByCode("","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("手机号或者邮箱不能为空"));

		
		//参数都是null
		String msg3 = UserCenter.activeUserByCode(null,null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("手机号或者邮箱不能为空"));

		
		//邮箱正确，验证码错误
		String msg4 = UserCenter.activeUserByCode("stt2017092001@chacuo.net","784814");
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		
		
		
		
		
	}
	
	
	
	@Test
	/* 
	 * 更新用户属性
	 * 异常情况的测试
	*/
	public void updateUserPropertiesExceptionTest() throws JsonProcessingException, IOException {
		String userName = "stt2017080301@chacuo.net"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);			
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d =date.format(new Date());
		
		//参数值随便输入
		//现在提示“修改用户属性失败”，应该是更友好的提示“修改用户属性失败，属性不正确”
//		String key="随便乱输入的内容haha";
//		String value="随便乱输入的内容haha";			
//		String msg = UserCenter.updateUserProperties(userId,key,value);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);
//		Assert.assertTrue(node.get("msg").asText().equals("修改用户属性失败，属性不正确"));
//		
		
		//修改性别，性别随便输入不正确的值
		//现在报错
		String key1="sex";
		String value1="随便乱输入的内容haha";		
		String msg1 = UserCenter.updateUserProperties(userId,key1,value1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("修改用户属性失败，属性值不正确"));

		//修改出生日期,日期随便输入不正确的值
		//现在status" : 1,提示“修改用户属性成功”。应该status" : 0，提示“修改用户属性失败，属性值不正确”
		String key2="birthday";
		String value2="随便乱输入的内容haha";		
		String msg2 = UserCenter.updateUserProperties(userId,key2,value2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("修改用户属性失败，属性值不正确"));
		
		//修改地址，地址随便输入不正确的值
		//现在报错
		String key3="address";
		String value3="随便乱输入的内容haha";		
		String msg3 = UserCenter.updateUserProperties(userId,key3,value3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("修改用户属性失败，属性值不正确"));

		//属性和属性值为""
		//现在提示"用户ID不能为空",应该是"属性和属性值不能为空"
		String msg4 = UserCenter.updateUserProperties(userId,"","");
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals("属性和属性值不能为空"));
		
		//属性和属性值为null
		//现在提示"用户ID不能为空",应该是"属性和属性值不能为空"
		String msg5 = UserCenter.updateUserProperties(userId,null,null);
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("属性和属性值不能为空"));
		
		//用户ID为空	
		String msg6 = UserCenter.updateUserProperties("","birthday","2017年8月8日");
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("用户ID不能为空"));
		
		//用户ID为空	
		String msg7 = UserCenter.updateUserProperties(null,"birthday","2017年8月8日");
		System.out.println(msg7);
		JsonNode node7 = mapper.readTree(msg7);
		Assert.assertTrue(node7.get("status").asInt()==0);
		Assert.assertTrue(node7.get("msg").asText().equals("用户ID不能为空"));
		
	}
	
	@Test
	/* 
	 * 更新用户多个属性
	 * 异常情况的测试
	*/		
	
	
	public void updateUserMultiPropertiesExceptionTest() throws JsonProcessingException, IOException {

		String userId = UserCenterUtil.getUserIdByLoginName("stt2017080301@chacuo.net");			
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d =date.format(new Date());
		
		//属性值随便输入，性别、地址、出生日期格式不正确
		//现在提示“修改用户属性失败”，应该给更友好的提示，例如日期格式不正确等这样的提示。		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", d+"随便乱输入的内容haha");
		params.put("sex", "随便乱输入的内容haha");
		params.put("address", "随便乱输入的内容haha");
		params.put("birthday", "随便乱输入的内容haha");
		params.put("userCode", d+"随便乱输入的内容haha");		
		String msg = UserCenter.updateUserMultiProperties(userId,params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);		
		
		//属性值都是空
		//现在提示“修改用户属性失败”，应该给更友好的提示，为什么失败		
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("userName", "");
		params1.put("sex", "");
		params1.put("address", "");
		params1.put("birthday", "");
		params1.put("userCode", "");		
		String msg1 = UserCenter.updateUserMultiProperties(userId,params1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//属性值都是null
		//现在"status" : "1",errmsg、successmsg的信息都是空，但实际属性值没有变化，应该给友好提示
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("userName",null);
		params2.put("sex",null);
		params2.put("address",null);
		params2.put("birthday",null);
		params2.put("userCode",null);		
		String msg2 = UserCenter.updateUserMultiProperties(userId,params2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);		
		
		//属性是空
		//现在提示''属性修改失败，应该是提示属性不能为空
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("","随便乱输入的内容haha");		
		String msg3 = UserCenter.updateUserMultiProperties(userId,params3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
				
		//属性是null
		//现在是"flag":0,"status":0，应该提示属性不能为null
		Map<String, String> params4 = new HashMap<String, String>();
		params4.put(null,"随便乱输入的内容haha");		
		String msg4 = UserCenter.updateUserMultiProperties(userId,params4);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
	
	}
	
	@Test
	/* 根据租户ID，查询关联企业ID以及之间的绑定状态(批量)
	 * 常情况的测试
	 * * state状态对应关系
	 * 0   ：  绑定审核中
	 * 1   ：  已绑定
	 * 2   ：  未绑定
	 */
	public void  getStateListByTenantIdListExceptionTest(){	
		
		//参数值不是实际存在的云数据中心id
		List<String> tenantIdList =new ArrayList<String>();
		tenantIdList.add("随便乱输入的内容haha");
		String msg = EnterpriseCenter.getStateListByTenantIdList(tenantIdList);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("states").get(0).get("state").asInt()==2);

		//参数为空
		List<String> tenantIdList1 =new ArrayList<String>();
		tenantIdList1.add("");
		String msg1 = EnterpriseCenter.getStateListByTenantIdList(tenantIdList1);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("states").get(0).get("state").asInt()==2);
		
		//参数为null
		List<String> tenantIdList2 =new ArrayList<String>();
		tenantIdList2.add(null);
		String msg2 = EnterpriseCenter.getStateListByTenantIdList(tenantIdList2);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
	//	Assert.assertTrue(node2.get("states").get(0).get("state").asInt()==2);
		}
	
	
	
	@Test
	/* 
	 * 给已有用户发送手机或邮箱验证码
	 * 发短信
	 * 异常情况的测试
	*/			
		public void sendPhoneMessageExceptionTest() throws JsonProcessingException, IOException {
			
		String msg = UserCenter.sendPhoneMessage("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);		
		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));	
		
		String msg1 = UserCenter.sendPhoneMessage("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
//		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));	
		
		String msg2 = UserCenter.sendPhoneMessage(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
//		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));	
	}
	
	
	
	@Test
	/* 
	 * 给已有用户发送手机或邮箱验证码
	 * 发邮件
	 * 异常情况的测试
	*/			
		public void sendEmailMessageExceptionTest() throws JsonProcessingException, IOException {
		
		String msg = UserCenter.sendEmailMessage("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);		
		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));	
		
		String msg1 = UserCenter.sendEmailMessage("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
//		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));	
		
		String msg2 = UserCenter.sendEmailMessage(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
//		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));	
	}
	
	
	
	
	@Test
	/* 
	 * 给已有用户发送手机或邮箱验证码
	 * 两个参数的发短信和发邮件
	 * 异常情况的测试
	*/			
		public void sendValidateCodeExceptionTest() throws JsonProcessingException, IOException, InterruptedException {

		String msg = UserCenter.sendValidateCode("随便乱输入的内容haha","随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("Type只能是phone或者email"));

		String msg1 = UserCenter.sendValidateCode("","email");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));
		
		String msg2 = UserCenter.sendValidateCode(null,"email");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
		
		
		String msg3 = UserCenter.sendValidateCode("随便乱输入的内容haha","email");
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("用户不存在"));
		
	}
	
	
	@Test
	/* 
	 * 验证手机验证码
	 * 异常情况的测试
	*/			
		public void validateCodeExceptionTest() throws JsonProcessingException, IOException, InterruptedException {		
		String mobile = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(mobile);	
		
		//验证码错误，是随便输入的值
		String msg = UserCenter.validateCode("email",userId,"随便乱输入的内容haha",mobile);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("验证码错误或超时"));
		
		//除了type是正确的，是随便输入的值（因为验证码没办法获取正确的，导致只有验证码和手机号或邮箱错误时，提示的都是验证码那个提示）
		String msg1 = UserCenter.validateCode("email","随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		
		//type是随便输入的值
		String msg2 = UserCenter.validateCode("随便乱输入的内容haha",userId,"随便乱输入的内容haha",mobile);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("Type只能是phone或者email"));
	
		//type是空
		String msg3 = UserCenter.validateCode("",userId,"随便乱输入的内容haha",mobile);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("Type只能是phone或者email"));
		
		//type是null
		String msg4 = UserCenter.validateCode(null,userId,"随便乱输入的内容haha",mobile);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals("Type只能是phone或者email"));

		//用户ID为空
		String msg5 = UserCenter.validateCode("email","","随便乱输入的内容haha",mobile);
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
//		Assert.assertTrue(node5.get("msg").asText().equals("用户ID不能为空"));
		
		//用户ID为null
		String msg6 = UserCenter.validateCode("email",null,"随便乱输入的内容haha","随便乱输入的内容haha");
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
//		Assert.assertTrue(node6.get("msg").asText().equals("用户ID不能为空"));
		
	}

	
	@Test
	/* 
	 * 修改密码，重置密码
	 * sid：验证码校验接口返回的sid
	 * 异常情况的测试
	*/			
		public void modifypasswordExceptionTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);	
		String oldPassword="yonyou11";
		String newPassword="yonyou22";
		String shaOldPassword=SDKUtils.encodeUsingSHA(oldPassword);
		String md5OldPassword=SDKUtils.encodeUsingMD5(oldPassword); 
		String shaNewPassword=SDKUtils.encodeUsingSHA(newPassword);
		String md5NewPassword=SDKUtils.encodeUsingMD5(newPassword);

		//sid是随便输入的值
		String msg = UserCenter.modifyPassword(userId,shaOldPassword, md5OldPassword,shaNewPassword, md5NewPassword, "随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("sid错误"));
		
		
		//sid是随便输入的值
		String msg1 = UserCenter.modifyPassword("随便乱输入的内容haha",shaOldPassword, md5OldPassword,shaNewPassword, md5NewPassword, "随便乱输入的内容haha");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));

		
		//除了userId，其他参数都是随便输入的值（sid怎么都是错的，这个需要界面，和密码都错的时候们还是提示sid错误）
		String msg2 = UserCenter.modifyPassword(userId,"随便乱输入的内容haha", "随便乱输入的内容haha","随便乱输入的内容haha", "随便乱输入的内容haha", "随便乱输入的内容haha");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("sid错误"));
		
		
		//userId为空
		String msg3 = UserCenter.modifyPassword("",shaOldPassword, md5OldPassword,shaNewPassword, md5NewPassword, "随便乱输入的内容haha");
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("用户ID不能为空"));
		
		
		//除了userId，其他参数都是空
		String msg4 = UserCenter.modifyPassword(userId,"", "","", "", "");
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
//		Assert.assertTrue(node4.get("msg").asText().equals("sid不能为空"));
		
		
		//userId为null
		String msg5 = UserCenter.modifyPassword(null,shaOldPassword, md5OldPassword,shaNewPassword, md5NewPassword, "随便乱输入的内容haha");
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("用户ID不能为空"));
		
		
		//除了userId，其他参数都是空
		String msg6 = UserCenter.modifyPassword(userId,null, null,null, null, null);
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
//		Assert.assertTrue(node6.get("msg").asText().equals("sid不能为空"));
		
	}
	
	
	
	@Test
	/* 修改手机号或邮箱
	 * 异常情况的测试
	 * contact：手机号或邮箱
	 */
	public void modifyContactExceptionTest() throws JsonProcessingException, IOException{
				
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);	
		
		//contact和sid错误时，提示sid错误
		String msg =UserCenter.modifyContact(userId,"随便乱输入的内容haha","随便乱输入的内容haha");
		JsonNode node=mapper.readTree(msg);
		System.out.print(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("sid错误"));
		
		//userId随便输入
		String msg1 =UserCenter.modifyContact("随便乱输入的内容haha","18611286701","随便乱输入的内容haha");
		JsonNode node1=mapper.readTree(msg1);
		System.out.print(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		
		//contact和sid为空
		String msg2 =UserCenter.modifyContact(userId,"","");
		JsonNode node2=mapper.readTree(msg2);
		System.out.print(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
//		Assert.assertTrue(node2.get("msg").asText().equals("sid不能为空"));
		
		//userId为空
		String msg3 =UserCenter.modifyContact("","","");
		JsonNode node3=mapper.readTree(msg3);
		System.out.print(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("用户ID不能为空"));
		
		//contact和sid为null
		String msg4 =UserCenter.modifyContact(userId,null,null);
		JsonNode node4=mapper.readTree(msg4);
		System.out.print(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
//		Assert.assertTrue(node4.get("msg").asText().equals("sid不能为空"));
		
		//userId为null
		String msg5 =UserCenter.modifyContact(null,null,null);
		JsonNode node5=mapper.readTree(msg5);
		System.out.print(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("用户ID不能为空"));
		
	}
	
	
	
	
	@Test
	/* 
	 * 单点登出
	 * 异常情况的测试
	*/			
		public void logoutWithTidExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		String msg = UserCenter.logoutWithTid("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);			
		
		String msg1 = UserCenter.logoutWithTid("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		String msg2 = UserCenter.logoutWithTid(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);		

	}
	
	
	@Test
	/* 
	 * 验证accesstoken
	 * 异常情况的测试
	*/			
		public void checkOauthTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		

		String msg = UserCenter.checkOauthToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);			
		
		String msg1 = UserCenter.checkOauthToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		String msg2 = UserCenter.checkOauthToken(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);		
	}
	
	
	@Test
	/* 
	 * 根据accesstoken获取用户信息
	 * 异常情况的测试
	*/			
		public void getUserByTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.getUserByToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.getUserByToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.getUserByToken(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node1.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 销毁accesstoken
	 * 异常情况的测试
	*/			
		public void destroyAccessTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值,这个不好判断accesstoken是否正确，是否超时等，所以目前就算参数错误也提示销毁成功了
		String msg = UserCenter.destroyAccessToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==1);	
		
		//参数为空
		String msg1 = UserCenter.destroyAccessToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.destroyAccessToken(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 刷新accesstoken
	 * 异常情况的测试
	*/			
		public void refreshAccessTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值,这个不好判断accesstoken是否正确，是否超时等，所以目前就算参数错误也提示销毁成功了
		String msg = UserCenter.refreshAccessToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.refreshAccessToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.refreshAccessToken(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 根据租户ID，用户名，密码获取accesstoken
	 * 异常情况的测试
	*/			
		public void createAccessTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.createAccessToken("随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha");
		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
//		String msg1 = UserCenter.createAccessToken("","","","");
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
//		String msg2 = UserCenter.createAccessToken(null,null,null,null);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	
	
	@Test
	/* 
	 * 根据密码获取accesstoken
	 * 异常情况的测试
	*/			
		public void createAccessTokenExceptionTest1() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.createAccessToken("随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha",false);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.createAccessToken("","","","");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.createAccessToken(null,null,null,null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 根据用户名密码创建ssotoken(uclient)
	 * 异常情况的测试
	*/			
		public void createSSOTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		String Password="yonyou11";
		String shaPassword=SDKUtils.encodeUsingSHA(Password);
		String md5Password=SDKUtils.encodeUsingMD5(Password); 

		//没有密码
		Map<String, String> params=new  HashMap<String, String>();
		params.put("username","18810039018");
		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		Assert.assertTrue(node.get("msg").asText().equals("用户名或密码错误"));
		
		//没有用户名
		Map<String, String> params1=new  HashMap<String, String>();
		params1.put("shaPassword",shaPassword);
		params1.put("md5Password",md5Password);
		String msg1 = UserCenter.createSSOToken(params1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		Assert.assertTrue(node1.get("msg").asText().equals("用户名或密码错误"));
		
		//用户名密码是空
		Map<String, String> params2=new  HashMap<String, String>();
		String msg2 = UserCenter.createSSOToken(params2);
		params2.put("username","");
		params2.put("shaPassword","");
		params2.put("md5Password","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);		
		Assert.assertTrue(node2.get("msg").asText().equals("用户名或密码错误"));
				
		
		//用户名密码是null
		Map<String, String> params3=new  HashMap<String, String>();
		String msg3 = UserCenter.createSSOToken(params3);
		params3.put("username",null);
		params3.put("shaPassword",null);
		params3.put("md5Password",null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);		
		Assert.assertTrue(node3.get("msg").asText().equals("用户名或密码错误"));
	}
	
	
	
	
	@Test
	/* 
	 * 根据ssotoken获取accessToken(uclient)
	 * 异常情况的测试
	*/			
		public void genAccessTokenBySSOTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.genAccessTokenBySSOToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	

		//参数为空
		String msg1 = UserCenter.genAccessTokenBySSOToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.genAccessTokenBySSOToken(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node1.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 根据accessToken获取ouathToken(uclient)
	 * 异常情况的测试
	*/			
		public void genOuathTokenByAccessTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.genOuathTokenByAccessToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.genOuathTokenByAccessToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.genOuathTokenByAccessToken(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node1.get("status").asInt()==0);	
	}
	
	
	
	@Test
	/* 与UClient集成实现U8C用户活跃度记录接口
	 * 异常情况的测试
	 */
	public void testU8cLoginLog() throws JsonProcessingException, IOException{
		
		//手机号格式不正确
		String msg = UserCenter.recordU8cLoginLog("随便乱输入的内容haha","172.20.44.29",""+System.currentTimeMillis());
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//手机号为空
		String msg1 = UserCenter.recordU8cLoginLog("","172.20.44.29",""+System.currentTimeMillis());
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);	
		
		//时间为空
		String msg2 = UserCenter.recordU8cLoginLog("18810039018","172.20.44.29","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt()==0);	
		
		
		//手机号为null
		String msg3 = UserCenter.recordU8cLoginLog(null,"172.20.44.29",""+System.currentTimeMillis());
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);	
		
		//时间为null
		String msg4 = UserCenter.recordU8cLoginLog("18810039018","172.20.44.29",null);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt()==0);	
	}
	
//	@Test
//	/* 
//	 * 快捷发送短信验证码
//	 * 异常情况的测试
//	 * 友文化特有，在接口文档里不显示
//	 * sdk.properties里改成client.credential.path=uculture.properties
//	 * 原值是client.credential.path=authfile_yht.txt
//	 * 同时把本地对应的authfile_yht.txt文件备份到其他地方，把uculture.properties放入即可
//	*/	
//	public void simpleSendCodeExceptionTest() throws JsonProcessingException, IOException{
//
//		//手机号格式不正确
//		String jsonStr ="{\"mobile\":\"随便乱输入的内容haha\", \"sysid\":\"uculture\"}";
//		String msg = UserCenter.simpleSendCode(jsonStr);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);	
//		Assert.assertTrue(node.get("msg").asText().equals("手机号格式不正确"));
//		
//		//sysid随便输入,目前这个参数随便输入，或者不输入都没有影响
//		String jsonStr1 ="{\"mobile\":\"18810039018\", \"sysid\":\"随便乱输入的内容haha\"}";
//		String msg1 = UserCenter.simpleSendCode(jsonStr1);
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt()==1);	
//		
//		//手机号格式为空
//		String jsonStr2 ="{\"mobile\":\"\", \"sysid\":\"uculture\"}";
//		String msg2 = UserCenter.simpleSendCode(jsonStr2);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt()==0);	
//		Assert.assertTrue(node2.get("msg").asText().equals("手机号为空"));
//		
//		//手机号格式为null
//		String jsonStr3 ="{\"mobile\":null, \"sysid\":\"uculture\"}";
//		String msg3 = UserCenter.simpleSendCode(jsonStr3);
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt()==0);	
//		Assert.assertTrue(node3.get("msg").asText().equals("手机号为空"));
//				
//	}
	
	
	@Test
	/* 
	 * 绑定手机时发短信不用图形校验码
	 * 异常情况的测试
	*/	
	public void simpleSendCodeExceptionTest() throws JsonProcessingException, IOException{
		
		//手机号格式不正确
		String jsonStr ="{\"mobile\":\"随便乱输入的内容haha\", \"sysid\":\"market\"}";
		String msg = UserCenter.simpleSendCode(jsonStr);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		
		//参数为空
		String jsonStr1 ="{\"mobile\":\"\", \"sysid\":\"\"}";
		String msg1 = UserCenter.simpleSendCode(jsonStr1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);	
		
		//参数为null
		String jsonStr2 ="{\"mobile\":null, \"sysid\":null}";
		String msg2 = UserCenter.simpleSendCode(jsonStr2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		
		
	}
	
	
	
	@Test
	/* 
	 * 绑定微信账户
	 * 异常情况的测试
	 * 18810039018的ID是868d2718-4723-4504-aa03-a773918c2fdb
	*/	
	public void bindUserExceptionTest() throws JsonProcessingException, IOException{
		
		//参数都为空
		String jsonStr ="{\"type\":\"\", \"userid\":\"\",\"openid\":\"\", \"unionid\":\"\"}";
		String msg = UserCenter.bindUser(jsonStr);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空"));
		
		//type随便输入值
		String jsonStr1 ="{\"type\":\"随便乱输入的内容haha\", \"userid\":\"868d2718-4723-4504-aa03-a773918c2fdb\",\"openid\":\"aa\", \"unionid\":\"aa\"}";
		String msg1 = UserCenter.bindUser(jsonStr1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);	
		Assert.assertTrue(node1.get("msg").asText().equals("参数错误"));
		
		//参数随便输入值
		String jsonStr2 ="{\"type\":null, \"userid\":null,\"openid\":null, \"unionid\":null}";
		String msg2 = UserCenter.bindUser(jsonStr2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("参数不能为空"));
		
		//userid随便输入值
		String jsonStr3 ="{\"type\":\"qq\", \"userid\":\"随便乱输入的内容haha\",\"openid\":\"aa\", \"unionid\":\"aa\"}";
		String msg3 = UserCenter.bindUser(jsonStr3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);	
		Assert.assertTrue(node3.get("msg").asText().equals("用户不存在"));
		

		
	}
	
 }
	
	
