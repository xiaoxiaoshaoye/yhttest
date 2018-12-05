package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import yhttest.UserCenterUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.entity.UserInfo;
import com.yonyou.yht.sdkutils.PropertyUtil;


public class UserCenterExceptionTestSuntt {

ObjectMapper mapper= new ObjectMapper();
	

// @Before
	public void  beforeEuc(){
		System.setProperty("yht.load.order","2");
		String path="eucsdk.properties";
		String authfile="euc.properties";
		String oauthfile="oauth2_eucCloud.properties";
		Properties p = PropertyUtil.loadFile(path);
		Properties p2 = PropertyUtil.loadFile(authfile);
		Properties p3 = PropertyUtil.loadFile(oauthfile);
		setEnv(p);
		setEnv(p2);
		setEnv(p3);
		System.out.println("#############finished before");
	}
	
  @Before
	public void  beforeIdtest(){
	  	System.setProperty("yht.load.order","2");
		String path="idtestsdk.properties";
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
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		
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
	public void getUserByPksExceptionTest() throws JsonProcessingException, IOException  {
		//参数为空
		String [] pks=new String [] {"",""};
		String  msg = UserCenter.getUserByPks(pks);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("Userids不能为空"));
		
		//参数值随便输入的内容
		String [] pks1=new String [] {"随便乱输入的内容哈哈","随便乱输入的内容haha"};		
		String  msg1 = UserCenter.getUserByPks(pks1);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("未找到用户"));
		
		//参数为null
		String [] pks2=new String [] {null,null};
		String  msg2 = UserCenter.getUserByPks(pks2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("Userids不能为空"));
	}
	
	@Test
	/* 
	 * 根据用户Code数组获取用户信息
	 * 异常情况的测试
	 * 用户Code数组输入空或者不存在的值
	*/
	public void getUserByCodesExceptionTest() throws JsonProcessingException, IOException {

		//参数为空,因为真的有code是空的，所以能查出来
		String [] pks=new String [] {"",""};
		String  msg = UserCenter.getUserByCodes(pks);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数值随便输入的内容
		String [] pks1=new String [] {"随便乱输入的内容哈哈","随便乱输入的内容haha"};		
		String  msg1 = UserCenter.getUserByCodes(pks1);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("未找到用户"));
		
		//参数为null
		String [] pks2=new String [] {null,null};
		String  msg2 = UserCenter.getUserByCodes(pks2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("Userids不能为空"));
	}
	
	@Test
	/* 
	 * 根据登录名获取用户信息
	 * 异常情况的测试
	 * 登录名输入空、不存在的值、null
	*/

	public void getUserByLoginNameExceptionTest() {
		String [] LoginName={"","随便乱输入的内容haha",null};
		String [] m={"用户名称不能为空","用户不存在'随便乱输入的内容haha'","用户名称不能为空"};
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
	public void getUserByLoginNameSidExceptionTest() throws JsonProcessingException, IOException {
		//参数为空
		String msg = UserCenter.getUserByLoginName("", "", "");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("用户名称不能为空"));
		
		//参数为null
		String msg1 = UserCenter.getUserByLoginName(null, null,null);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户名称不能为空"));
		
		//参数为随便输入的内容
		String msg2 = UserCenter.getUserByLoginName("随便乱输入的内容haha", "随便乱输入的内容haha", "随便乱输入的内容haha");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("msg").asText().equals("未找到用户随便乱输入的内容haha"));
	}

	@Test
	/* 
	 * 判断手机号格式是否正确
	 * 正常情况的测试
	 * 手机号输入正确的值
	*/
	public void isMobileExceptionTest() throws JsonProcessingException, IOException {
		
		//手机号是空
		String Mobile="";
		String msg = UserCenter.isMobile(Mobile);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空"));
		
		
		//手机号是null
		String Mobile1=null;
		String msg1 = UserCenter.isMobile(Mobile1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("参数不能为空"));
		
		//手机号是位数不够
		String Mobile2="188";
		String msg2 = UserCenter.isMobile(Mobile2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("flag").asInt() == 0);
		
		//手机号是位数正确，但是包含字母
		String Mobile3="1880000000a";
		String msg3 = UserCenter.isMobile(Mobile3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("flag").asInt() == 0);
		
		//手机号是位数正确，也是数字，但不是手机号
		String Mobile4="10000000000";
		String msg4 = UserCenter.isMobile(Mobile4);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("flag").asInt() == 0);

		
	}
	
	@Test
	/* 
	 * 判断用户编码是否已经存在
	 * 异常情况的测试
	*/
	public void isUserCodeExistExceptionTest() throws JsonProcessingException, IOException {
		
		//参数是随便输入的值
		String userCode="随便乱输入的内容haha111";	
		String msg = UserCenter.isUserCodeExist(userCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("flag").asInt() == 0);
		
		//参数为空
		String userCode1="";	
		String msg1 = UserCenter.isUserCodeExist(userCode1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("usercode不能为空"));
		
		//参数为null
		String userCode2=null;	
		String msg2 = UserCenter.isUserCodeExist(userCode2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("usercode不能为空"));
		
	}
	
	
	@Test
	/* 
	 * 根据登录名判断用户是否存在
	 * 异常情况的测试
	 * 登录名不输入值
	*/
	public void isUserExistExceptionTest() throws JsonProcessingException, IOException {


		//参数是随便输入的值
		String userCode="随便乱输入的内容haha111";	
		String msg = UserCenter.isUserExist(userCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));
		
		//参数为空(系统里真的有code为空的)
		String userCode1="";	
		String msg1 = UserCenter.isUserExist(userCode1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("msg").asText().equals("参数不能为空"));
		
		//参数为null
		String userCode2=null;	
		String msg2 = UserCenter.isUserExist(userCode2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("msg").asText().equals("参数不能为空"));
		
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
		
		
//		//一个是随便输入的不存在的值，这样查询出来的元素总个数是0
//		String []pks={"随便乱输入的内容haha"};
//		String  userName="s";
//		String msg = UserCenter.searchUser(pks,userName);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 0);
		
		//用户ID数组里是3个正确的值，用户名没有输入值，应该给友好提示，用户名不能为空
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
		Assert.assertTrue(node1.get("msg").asText().equals("用户名不能为空"));
		
		//用户id数组为空
		String []pks2={""};
		String  userName2="stt";
		String msg2 = UserCenter.searchUser(pks2,userName2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("msg").asText().equals("Userids不能为空"));
	
		//用户id数组为null，给友好提示
		String msg3 = UserCenter.searchUser(null,null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("msg").asText().equals("pks不能为空"));
		
		
		//用户id数组为null，给友好提示
		String msg31 = UserCenter.searchUser(pks1,null);
		System.out.println(msg31);
		JsonNode node31 = mapper.readTree(msg31);
		Assert.assertTrue(node31.get("msg").asText().equals("用户名不能为空"));
		
		//搜索"1"
		String msg4 = UserCenter.searchUser(pks1,"1");
		System.out.println(msg4);
		JsonNode  node4=Utils.getJson(mapper, msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals("关键字不能用于搜索"));
	
		//搜索@
		String msg5 = UserCenter.searchUser(pks1,"@");
		System.out.println(msg5);
		JsonNode  node5=Utils.getJson(mapper, msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("关键字不能用于搜索"));
		
		
		//搜索13
		String msg6 = UserCenter.searchUser(pks1,"13");
		System.out.println(msg6);
		JsonNode  node6=Utils.getJson(mapper, msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("关键字不能用于搜索"));
		
		
		//搜索15
		String msg7 = UserCenter.searchUser(pks1,"15");
		System.out.println(msg7);
		JsonNode  node7=Utils.getJson(mapper, msg7);
		Assert.assertTrue(node7.get("status").asInt()==0);
		Assert.assertTrue(node7.get("msg").asText().equals("关键字不能用于搜索"));
		
		//搜索18
		String msg8 = UserCenter.searchUser(pks1,"18");
		System.out.println(msg8);
		JsonNode  node8=Utils.getJson(mapper, msg8);
		Assert.assertTrue(node8.get("status").asInt()==0);
		Assert.assertTrue(node8.get("msg").asText().equals("关键字不能用于搜索"));
		
		//搜索*
		String msg9 = UserCenter.searchUser(pks1,"*");
		System.out.println(msg9);
		JsonNode  node9=Utils.getJson(mapper, msg9);
		Assert.assertTrue(node9.get("status").asInt()==0);
		Assert.assertTrue(node9.get("msg").asText().equals("关键字不能用于搜索"));
	
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
				{"手机号输入不存在的值","18855559876","mobile","1","未找到该用户"},
				{"邮箱输入不存在的值","abcdteg1abcdteg2abcdteg3@yonyou.com","email","1","未找到该用户"},
				{"参数都是空","","","0","参数不能为空"},
				{"参数都是null",null,null,"0","参数不能为空"},
				{"手机号格式不正确","随便乱输入的内容haha","mobile","0","手机号格式不正确"},
				{"邮箱格式不正确","随便乱输入的内容haha","email","0","邮箱格式不正确"},
				{"参数都是随便输入的值","随便乱输入的内容haha","随便乱输入的内容haha","0","手机号格式不正确"}
				};
		for(int j=0;j<users.length;j++){
		String msg = UserCenter.getUserByContact(users[j][1],users[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(users[j][3]));		
		Assert.assertTrue(node.get("msg").asText().equals(users[j][4]));
	
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
		JsonNode node = mapper.readTree(msg);
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
		params8.put("userCode", userCode8+"00000000001111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
		params8.put("userName", userCode8+"name");
		params8.put("userEmail", userCode8+"@chacuo.net");		
		String msg8 = UserCenter.addUser(params8);
		System.out.println(msg8);
		JsonNode node8 = mapper.readTree(msg8);
//		Assert.assertTrue(node8.get("status").asInt()==0);
//		Assert.assertTrue(node8.get("msg").asText().equals("帐号格式不正确，帐号目前支持两位以上的中文名称或3-36位的数字和字符组合"));

		
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
		Assert.assertTrue(node9.get("msg").asText().equals("用户名不可超过64个字符"));

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

		//userCode的值是手机号格式,因为不支持手机号格式，所以变成自动生成的值
		Map<String, String> params14 = new HashMap<String, String>();
		SimpleDateFormat date14 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode14 =date.format(new Date());
		params14.put("userCode", "18810039018");
		params14.put("userName", userCode+"name");
		params14.put("userEmail", userCode+"@chacuo.net");		
		String msg14 = UserCenter.addUser(params14);
		System.out.println(msg14);
		JsonNode node14 = mapper.readTree(msg14);
		Assert.assertTrue(node14.get("status").asInt()==1);
		
		//userCode的值是邮箱格式
		Map<String, String> params15 = new HashMap<String, String>();
		SimpleDateFormat date15 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode15 =date.format(new Date());
		params15.put("userCode", "aaaa@cccccc.ccccccc");
		params15.put("userName", userCode+"name");
		params15.put("userEmail", userCode+"@chacuo.net");		
		String msg15 = UserCenter.addUser(params15);
		System.out.println(msg15);
		JsonNode node15 = mapper.readTree(msg14);
		Assert.assertTrue(node15.get("status").asInt()==1);
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
//		
//	    //用户信息格式不正确，是随便输入的内容
//	    String msg6 = UserCenter.addUsers(systemCode, "随便乱输入的内容haha");
//	    System.out.println(msg6);
//	    JsonNode node6 = mapper.readTree(msg6);
//	    Assert.assertTrue(node6.get("status").asInt()==0);
//	    Assert.assertTrue(node6.get("msg").asText().equals("保存失败"));
//
//	    
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
	
	   //userCode是手机号和邮件格式，因为不支持这个格式，所以userCode变成自动生成的值
		SimpleDateFormat date10 =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode10 =date.format(new Date());
		
		String systemCode10 = "yht";
		int i=1;
		Map<String, String> user10 = new HashMap<String, String>();
		user10.put("userCode", "18810039018");
		user10.put("userName", userCode+"name");
		user10.put("userEmail", userCode+"@chacuo.net");	
		
		Map<String, String> user101 = new HashMap<String, String>();
		user101.put("userCode", "suntt111111111@yonyou.com");
		user101.put("userName", userCode+i+"name");
		user101.put("userEmail", userCode+i+"@chacuo.net");	
		
		Map<String, Object> params10 = new HashMap<String, Object>();
		List<Object> users10 = new ArrayList<Object>();
		users10.add(user10);
		users10.add(user101);
		params10.put("users", users10);
		String jsonStr10 = Utils.getJsonStr(mapper, params10);
		System.out.println(jsonStr10);
		String msg10 = UserCenter.addUsers(systemCode, jsonStr10);
		System.out.println(msg10);
		JsonNode node10 = mapper.readTree(msg10);
		Assert.assertTrue(node10.get("status").asInt()==1);
	
	
	
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
		Assert.assertTrue(node2.get("msg").asText().equals("手机号或邮箱不能为空"));

		
		//参数都是null
		String msg3 = UserCenter.activeUserByCode(null,null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("手机号或邮箱不能为空"));

		
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
		String key="随便乱输入的内容haha";
		String value="随便乱输入的内容haha";			
		String msg = UserCenter.updateUserProperties(userId,key,value);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("修改用户属性失败,属性不正确"));
		
		
		//修改性别，性别随便输入不正确的值
		String key1="sex";
		String value1="随便乱输入的内容haha";		
		String msg1 = UserCenter.updateUserProperties(userId,key1,value1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("性别格式错误"));

		//修改出生日期,日期随便输入不正确的值
		String key2="birthday";
		String value2="随便乱输入的内容haha";		
		String msg2 = UserCenter.updateUserProperties(userId,key2,value2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("生日格式错误"));
		
		//修改地址，地址随便输入不正确的值
		String key3="address";
		String value3="随便乱输入的内容haha";		
		String msg3 = UserCenter.updateUserProperties(userId,key3,value3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("地址格式错误"));

		//属性和属性值为""
		String msg4 = UserCenter.updateUserProperties(userId,"","");
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals("key不能为空"));
		
		//属性和属性值为null
		String msg5 = UserCenter.updateUserProperties(userId,null,null);
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("key不能为空"));
		
		//用户ID为空	
		String msg6 = UserCenter.updateUserProperties("","birthday","2017年8月8日");
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("用户ID不能为空"));
		
		//用户ID为null
		String msg7 = UserCenter.updateUserProperties(null,"birthday","2017年8月8日");
		System.out.println(msg7);
		JsonNode node7 = mapper.readTree(msg7);
		Assert.assertTrue(node7.get("status").asInt()==0);
		Assert.assertTrue(node7.get("msg").asText().equals("用户ID不能为空"));
		
		//修改userCode，修改成手机号格式
		String key8="userCode";
		String value8="18810039018";			
		String msg8 = UserCenter.updateUserProperties(userId,key8,value8);
		System.out.println(msg8);
		JsonNode node8 = mapper.readTree(msg8);
		Assert.assertTrue(node8.get("status").asInt()==0);
		Assert.assertTrue(node8.get("msg").asText().equals("帐号不能为手机号或邮箱"));
		
		//修改userCode，修改成邮箱格式
		String key9="userCode";
		String value9="18810039018";			
		String msg9 = UserCenter.updateUserProperties(userId,key9,value9);
		System.out.println(msg9);
		JsonNode node9 = mapper.readTree(msg9);
		Assert.assertTrue(node9.get("status").asInt()==0);
		Assert.assertTrue(node9.get("msg").asText().equals("帐号不能为手机号或邮箱"));
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
		Assert.assertTrue(node.get("status").asInt()==1);		
		
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
		Assert.assertTrue(node1.get("status").asInt()==1);		
		
		//属性值都是null
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
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("","随便乱输入的内容haha");		
		String msg3 = UserCenter.updateUserMultiProperties(userId,params3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
			
		//属性是null
		Map<String, String> params4 = new HashMap<String, String>();
		params4.put(null,"随便乱输入的内容haha");		
		String msg4 = UserCenter.updateUserMultiProperties(userId,params4);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
	

		 //修改userCode，userCode的值为手机号   
		Map<String, String> params5 = new HashMap<String, String>();

		params5.put("userName", d+"name");
		params5.put("sex", "0");
		params5.put("address", "1-11-2-1");
		params5.put("birthday", "2017年08月08日");
		params5.put("userCode", "18810039018");
		
		String msg5 = UserCenter.updateUserMultiProperties(userId,params5);
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==1);
		Assert.assertTrue(node5.get("errmsg").get(0).asText().equals("\'userCode\'属性修改失败,帐号不能为手机号或邮箱"));
		
		
		 //修改userCode，userCode的值为手机号   
		Map<String, String> params6 = new HashMap<String, String>();

		params6.put("userName", d+"name");
		params6.put("sex", "0");
		params6.put("address", "1-11-2-1");
		params6.put("birthday", "2017年08月08日");
		params6.put("userCode", "suntt11111111111111111@uu.cccccccccccccc");
		
		String msg6 = UserCenter.updateUserMultiProperties(userId,params6);
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==1);
		Assert.assertTrue(node6.get("errmsg").get(0).asText().equals("\'userCode\'属性修改失败,帐号不能为手机号或邮箱"));
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
		tenantIdList.add("hahahahahahahahhahahahahahaha");
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
		Assert.assertTrue(node1.get("states").get(0).get("msg").asText().equals("云数据中心ID为空"));
		
		//参数为null
		List<String> tenantIdList2 =new ArrayList<String>();
		tenantIdList2.add(null);
		String msg2 = EnterpriseCenter.getStateListByTenantIdList(tenantIdList2);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("states").get(0).get("msg").asText().equals("云数据中心ID为空"));
		}
	
	
	@Test
	/* 
	 * 非用户发送手机或邮箱验证码
	 * 发短信
	 * 异常情况的测试
	*/			
		public void sendcodeExceptionTest() throws JsonProcessingException, IOException {
			
		String contact="stt851026@163.com";
		String type="email";
		String key="1527557621000558310";
		String code="5ZTG";
		
		//key、code错误
		String msg = UserCenter.sendcode(contact,type,key,code);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);		
		Assert.assertTrue(node.get("msg").asText().equals("codeerror"));	
		
		//Type不正确
		String msg1 = UserCenter.sendcode(contact,"aa",key,code);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		Assert.assertTrue(node1.get("msg").asText().equals("Type只能是phone或者email"));	
		
		//key、code为空
		String msg2 = UserCenter.sendcode(contact,type,"","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("imgcodeerror"));	
		
		//key、code为null
		String msg3 = UserCenter.sendcode(contact,type,null,null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);	
		Assert.assertTrue(node3.get("msg").asText().equals("imgcodeerror"));	
		
		//Type为空
		String msg4 = UserCenter.sendcode(contact,"",key,code);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);		
		Assert.assertTrue(node4.get("msg").asText().equals("Type只能是phone或者email"));	
	
		//Type为null
		String msg5 = UserCenter.sendcode(contact,null,key,code);
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);		
		Assert.assertTrue(node5.get("msg").asText().equals("Type只能是phone或者email"));	
	
	
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
		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));	
		
		String msg2 = UserCenter.sendPhoneMessage(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));	
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
		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));	
		
		String msg2 = UserCenter.sendEmailMessage(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));	
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
	 * 给已有用户发送手机验证码(携带Ts校验)
	 * 异常情况的测试
	*/			
		public void sendValidateCodeWithTsExceptionTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);	
		
		//用户id是随便输入的不存在的值
		String msg = UserCenter.sendValidateCodeWithTs("随便乱输入的内容haha","a");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));

		//用户id为空
		String msg1 = UserCenter.sendValidateCodeWithTs("","a");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));
		
		//用户id为null
		String msg2 = UserCenter.sendValidateCodeWithTs(null,"a");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
		
		//ts为空
		String msg3 = UserCenter.sendValidateCodeWithTs("userId","");
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals(""));
		
		//ts为null
		String msg4 = UserCenter.sendValidateCodeWithTs("userId",null);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals(""));
		
	}
	
	@Test
	/* 
	 * 验证手机验证码(携带Ts校验)
	 * 异常情况的测试
	*/			
		public void validateCodeWithTsExceptionTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);	
		
		//用户id是随便输入的不存在的值
		String msg = UserCenter.validateCodeWithTs("随便乱输入的内容haha","a","a");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));

		//用户id是空
		String msg1 = UserCenter.validateCodeWithTs("","a","a");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
//		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));
		
		//用户id为null
		String msg2 = UserCenter.validateCodeWithTs(null,"a","a");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
//		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
		
		//验证码为空
		String msg3 = UserCenter.validateCodeWithTs("userId","","a");
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("验证码不能为空"));
		
		//验证码为null
		String msg4 = UserCenter.validateCodeWithTs("userId",null,"a");
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals("验证码不能为空"));
		
		//ts为空
		String msg5 = UserCenter.validateCodeWithTs("userId","1111","");
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
//		Assert.assertTrue(node5.get("msg").asText().equals(""));
		
		//ts为null
		String msg6 = UserCenter.validateCodeWithTs("userId","1111",null);
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
//		Assert.assertTrue(node6.get("msg").asText().equals(""));
		
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
		Assert.assertTrue(node4.get("msg").asText().equals("sid不能为空"));
		
		
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
		Assert.assertTrue(node6.get("msg").asText().equals("sid不能为空"));
		
	}
	
	
	
	@Test
	/* 修改手机号或邮箱
	 * 异常情况的测试
	 * contact：手机号或邮箱
	 */
	public void modifyContactExceptionTest() throws JsonProcessingException, IOException{
				
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);	
		
	    Date dt=new Date();
		SimpleDateFormat matter1=new SimpleDateFormat("yyMMdd");
		String verification=matter1.format(dt);
		
		//contact和sid错误
		String msg =UserCenter.modifyContact(userId,"随便乱输入的内容haha","随便乱输入的内容haha");
		JsonNode node=mapper.readTree(msg);
		System.out.print(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("update contact failed, contact is not email or mobile"));
		
		//userId随便输入
		String msg1 =UserCenter.modifyContact("随便乱输入的内容hahaaaaa","18810039018","随便乱输入的内容hahaaaaa");
		JsonNode node1=mapper.readTree(msg1);
		System.out.print(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		
		//contact和sid为空
		String msg2 =UserCenter.modifyContact(userId,"15210040725","");
		JsonNode node2=mapper.readTree(msg2);
		System.out.print(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("sid不能为空"));
		
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
		Assert.assertTrue(node4.get("msg").asText().equals("sid不能为空"));
		
		//userId为null
		String msg5 =UserCenter.modifyContact(null,null,null);
		JsonNode node5=mapper.readTree(msg5);
		System.out.print(msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("用户ID不能为空"));
		
		//"stt2018011801@test1988.com"是企业应用授权用户，只有读写功能，不能修改手机号邮箱。ID是"183c1252-b602-43b1-87f2-16b24b4d4a9a"
 		String userId1 = "183c1252-b602-43b1-87f2-16b24b4d4a9a"; 
		String msg6 =UserCenter.modifyContact(userId1,"18822888888",verification);
		JsonNode node6=mapper.readTree(msg6);
		System.out.print(msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("当前用户为企业用户，不支持在友户通编辑手机号和邮箱"));
		
		//" stt2018050701@test1988.com"当前用户为NC用户，不支持在友户通编辑用户手机号，请登录NC系统进行修改。ID是"604ceaf6-7a61-4cde-82f9-20155b8c9589"
 		String userId2 = "604ceaf6-7a61-4cde-82f9-20155b8c9589"; 
		String msg7 =UserCenter.modifyContact(userId2,"18822888888",verification);
		JsonNode node7=mapper.readTree(msg7);
		System.out.print(msg7);
		Assert.assertTrue(node7.get("status").asInt()==0);
		Assert.assertTrue(node7.get("msg").asText().equals("当前用户为NC用户，不支持在友户通编辑手机号，请登录NC系统修改"));
		
		

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
	 * 根据密码获取accesstoken
	 * 异常情况的测试
	 * 无链接的
	*/			
		public void createAccessTokenExceptionTest1() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.createAccessToken("随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha",false);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.createAccessToken("","","","","",false);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.createAccessToken(null,null,null,null,null,false);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	@Test
	/* 
	 * 根据密码获取accesstoken
	 * 异常情况的测试
	 * 有链接的
	*/			
		public void generateAccessTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.generateAccessToken("随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha",false);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.generateAccessToken("","","",false);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.generateAccessToken(null,null,null,false);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 根据验证码获取accesstoken
	 * 异常情况的测试
	*/			
		public void generateAccessTokenByValidateCodeExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.generateAccessTokenByValidateCode("随便乱输入的内容haha","随便乱输入的内容haha",false);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.generateAccessTokenByValidateCode("","",false);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.generateAccessTokenByValidateCode(null,null,false);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
	}
	
	
	@Test
	/* 
	 * 根据accesstoken获取临时token
	 * 异常情况的测试
	*/			
		public void genTokenByAccessTokenExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值
		String msg = UserCenter.genTokenByAccessToken("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		
		//参数为空
		String msg1 = UserCenter.genTokenByAccessToken("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		
		//参数是null
		String msg2 = UserCenter.genTokenByAccessToken(null);
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
		Assert.assertTrue(node.get("code").asText().equals("400"));
		
		//没有用户名
		Map<String, String> params1=new  HashMap<String, String>();
		params1.put("shaPassword",shaPassword);
		params1.put("md5Password",md5Password);
		String msg1 = UserCenter.createSSOToken(params1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);		
		Assert.assertTrue(node.get("code").asText().equals("400"));
		
		//用户名密码是空
		Map<String, String> params2=new  HashMap<String, String>();
		String msg2 = UserCenter.createSSOToken(params2);
		params2.put("username","");
		params2.put("shaPassword","");
		params2.put("md5Password","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);		
		Assert.assertTrue(node.get("code").asText().equals("400"));
				
		
		//用户名密码是null
		Map<String, String> params3=new  HashMap<String, String>();
		String msg3 = UserCenter.createSSOToken(params3);
		params3.put("username",null);
		params3.put("shaPassword",null);
		params3.put("md5Password",null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);		
		Assert.assertTrue(node.get("code").asText().equals("400"));
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
	/* 
	 * 绑定邮箱
	 * 异常情况的测试
	*/			
		public void bindMailExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三轮参数邮箱
		//第四列返回值status
		//第五列返回值msg
		String [][] value={				
				{"邮箱为空",userId,"","0","邮箱已存在"},
				{"用户ID为空","","yixixinzi@126.com","0","用户ID不能为空"},
				{"用户ID为null",null,"yixixinzi@126.com","0","用户ID不能为空"},
				{"邮箱格式不正确",userId,"随便乱输入的内容haha","0","邮箱格式不正确"},
				{"用户ID不正确","随便乱输入的内容haha","yixixinzi@126.com","0","用户不存在"},
				{"邮箱被别人已使用",userId,"suntt@yonyou.com","0","邮箱已存在"},
				{"邮箱为null",userId,null,"1",""},
				{"还原邮箱",userId,"yixixinzi@126.com","1",""},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<6;j++){
		String msg = UserCenter.bindEmail(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		String msg1=value[j][4];
		Assert.assertTrue(node.get("msg").asText().equals(msg1));

		}
				
		//返回值没有有msg的情况
		for(int j=6;j<8;j++){
		String msg = UserCenter.bindEmail(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		}

	}
	
	
	
	@Test
	/* 
	 * 绑定手机
	 * 异常情况的测试
	*/			
		public void bindMobileExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三轮参数邮箱
		//第四列返回值status
		//第五列返回值msg
		String [][] value={				
				{"手机号为空",userId,"","0","手机号已存在"},
				{"用户ID为空","","18801282841","0","用户ID不能为空"},
				{"用户ID为null",null,"18801282841","0","用户ID不能为空"},
				{"手机号格式不正确",userId,"随便乱输入的内容haha","0","手机号格式不正确"},
				{"用户ID不正确","随便乱输入的内容haha","18801272841","0","用户不存在"},
				{"手机号被别人已使用",userId,"18611286701","0","手机号已存在"},
				{"手机号为null",userId,null,"1",""},
				{"还原手机号",userId,"18801282841","1",""},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<6;j++){
		String msg = UserCenter.bindMobile(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		String msg1=value[j][4];
		Assert.assertTrue(node.get("msg").asText().equals(msg1));

		}
				
		//返回值没有有msg的情况
		for(int j=6;j<8;j++){
		String msg = UserCenter.bindMobile(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		}

	}
	
	
	@Test
	/* 
	 * 上传用户头像
	 * 异常情况的测试
	*/			
		public void uploadUserAvatorExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三轮参数上传用户头像路径
		//第四列返回值status
		//第五列返回值msg
		String [][] value={				
				{"头像文件路径为空",userId,"","0","文件不存在"},
				{"用户ID为空","","F:\\头像.png","0","用户ID不能为空"},
				{"用户ID为null",null,"F:\\头像.png","0","用户ID不能为空"},
				{"头像格式不正确",userId,"F:\\校验导入数据.xls","0","非图片格式"},
				{"用户ID不正确","随便乱输入的内容haha","F:\\头像.png","0","用户不存在"},
				{"头像文件路径为null",userId,null,"0","文件不存在"},
				{"头像文件路径不存在",userId,"随便乱输入的内容haha","0","文件不存在"},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<value.length;j++){

		String msg = UserCenter.uploadUserAvator(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		String msg1=value[j][4];
		Assert.assertTrue(node.get("msg").asText().equals(msg1));

		}

	}
	
	
	
	@Test
	/* 
	 * 根据用户ID向该用户手机发送消息
	 * 异常情况的测试
	*/			
		public void sendMsgByMobileExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
//		原来是两个参数的，后面把两个参数的屏蔽了，需要用三个参数的		
//		//第一列是异常描述
//		//第二列参数userID
//		//第三列消息内容
//		//第四列返回值status
//		//第五列返回值msg
//		String [][] value={				
//				{"消息为空",userId,"","0","通过手机号发送消息失败"},
//				{"用户ID为空","","发送测试消息","0","用户ID不能为空"},
//				{"用户ID为null",null,"发送测试消息","0","用户ID不能为空"},
//				{"用户ID不正确","随便乱输入的内容haha","发送测试消息","0","用户不存在"},
//				{"消息为null",userId,null,"0","通过手机号发送消息失败"},
//				};	
//		
//		//返回值有msg的异常情况
//		for(int j=0;j<5;j++){
//		String msg = UserCenter.sendMsgByMobile(value[j][1],value[j][2]);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		String status=value[j][3];
//		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
//		String msg1=value[j][4];
//		Assert.assertTrue(node.get("msg").asText().equals(msg1));
//
		
		//第一列是异常描述
		//第二列参数userID
		//第三列模板参数
		//第四列模板号
		//第五列返回值status
		//第六列返回值msg
		String [][] value={				
				{"模板参数为空",userId,"","rvxuv2Ge","1"},
				{"模板号为空",userId,"123","","1",""},
				{"用户ID为空","","123456","rvxuv2Ge","0","用户ID不能为空"},
				{"用户ID为null",null,"123456","rvxuv2Ge","0","用户ID不能为空"},
				{"用户ID不正确","随便乱输入的内容haha","123456","rvxuv2Ge","0","用户不存在"},
				{"模板号为null",userId,"123",null,"1"},
				{"模板参数为null",userId,null,"rvxuv2Ge","0","通过手机号发送消息失败"},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<value.length;j++){		
		String msg = UserCenter.sendMsgByMobile(value[j][1],value[j][2],value[j][3]);
		System.out.println("-------------------------------"+j+"--------------------------");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		int status=Integer.valueOf(value[j][4]);
		Assert.assertTrue(node.get("status").asInt() == status);
		if(status==0){
			String msg1=value[j][5];
			Assert.assertTrue(node.get("msg").asText().equals(msg1));
		}

		}
		
	}
	
	@Test
	/* 
	 * 根据用户ID向该用户邮箱发送消息
	 * 异常情况的测试
	*/			
		public void sendMsgByEmailExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三列消息内容
		//第四列返回值status
		//第五列返回值msg
		String [][] value={				
				{"消息为空",userId,"","0","邮件内容不能为空"},
				{"用户ID为空","","发送测试消息","0","用户ID不能为空"},
				{"用户ID为null",null,"发送测试消息","0","用户ID不能为空"},
				{"用户ID不正确","随便乱输入的内容haha","发送测试消息","0","用户不存在"},
				{"消息为null",userId,null,"0","邮件内容不能为空"},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<value.length;j++){
		System.out.println("-------------------"+j+"--------------------------");	
		String msg = UserCenter.sendMsgByEmail(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		String msg1=value[j][4];
		Assert.assertTrue(node.get("msg").asText().equals(msg1));
		}
		
	}
	
	
	@Test
	/* 
	 * 根据用户ID获取用户标签
	 * 异常情况的测试
	*/			
		public void getTagsExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值,不存在这个用户
		String msg = UserCenter.getTags("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));	
		
		//参数为空
		String msg1 = UserCenter.getTags("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);	
		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));	
		
		//参数是null
		String msg2 = UserCenter.getTags(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));	
		
		//用户没有标签
		String msg3 = UserCenter.getTags("18611286701");
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);	
		Assert.assertTrue(node3.get("msg").asText().equals("该用户无标签"));
	}
	
	
	@Test
	/* 
	 * 根据标签获取用户ID
	 * 异常情况的测试
	*/			
		public void getUserIdsByTagExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//没有用户有这个标签
		String msg = UserCenter.getUserIdsByTag("随便乱输入的内容haha");
		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);	
//		Assert.assertTrue(node.get("msg").asText().equals(""));	
		
		//参数为空
		String msg1 = UserCenter.getUserIdsByTag("");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);	
		Assert.assertTrue(node1.get("msg").asText().equals("标签不能为空"));	
		
		//参数是null
		String msg2 = UserCenter.getUserIdsByTag(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("标签不能为空"));	

	}

	@Test
	/* 
	 * 为用户产生自动登录Token
	 * 异常情况的测试
	 * 废弃接口，除了云审批用外，没人用，这个接口不安全。云审批后期也会换
	*/			
		public void genTokenByUserIdExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//参数是随便输入的不正确的值,不存在这个用户
		String msg = UserCenter.genTokenByUserId("随便乱输入的内容haha");
		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt()==0);	
//		Assert.assertTrue(node.get("msg").asText().equals("用户不存在"));	
		
		//参数为空
		String msg1 = UserCenter.genTokenByUserId("");
		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt()==0);	
//		Assert.assertTrue(node1.get("msg").asText().equals("用户ID不能为空"));	
		
		//参数是null
		String msg2 = UserCenter.genTokenByUserId(null);
		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt()==0);	
//		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));	
		
	}
	
	
	@Test
	/* 
	 * 为用户设置标签
	 * 异常情况的测试
	*/			
		public void setTagExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三列消息内容
		//第四列返回值status
		//第五列返回值msg
		String [][] value={				
				{"标签为空",userId,"","0","标签不能为空"},
				{"用户ID为空","","发送测试消息","0","用户ID不能为空"},
				{"用户ID为null",null,"发送测试消息","0","用户ID不能为空"},
				{"用户ID不正确","随便乱输入的内容haha","发送测试消息","0","用户不存在"},
				{"标签为null",userId,null,"0","标签不能为空"},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<5;j++){
		String msg = UserCenter.setTag(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		String msg1=value[j][4];
		Assert.assertTrue(node.get("msg").asText().equals(msg1));

		}
	
	}
	
	
	@Test
	/* 
	 * 移除用户标签
	 * 异常情况的测试
	*/			
		public void removeTagExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三列消息内容
		//第四列返回值status
		//第五列返回值msg
		String [][] value={				
				{"标签为空",userId,"","0","标签不能为空"},
				{"用户ID为空","","恬恬O(∩_∩)O哈哈~","0","用户ID不能为空"},
				{"用户ID为null",null,"恬恬O(∩_∩)O哈哈~","0","用户ID不能为空"},
				{"用户ID不正确","随便乱输入的内容haha","恬恬O(∩_∩)O哈哈~","0","用户不存在"},
				{"标签为null",userId,null,"0","标签不能为空"},
				{"标签为不存在的随便输入的内容",userId,"随便乱输入的内容haha","0","标签不存在，无法删除"},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<6;j++){
		System.out.println("------------------------------"+j+"-----------------------");	
		String msg = UserCenter.removeTag(value[j][1],value[j][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String status=value[j][3];
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
		String msg1=value[j][4];
		Assert.assertTrue(node.get("msg").asText().equals(msg1));
		}
	
	}
	
	
	@Test
	/* 
	 * 修改用户标签
	 * 异常情况的测试
	*/			
		public void modifyTagExceptionTest() throws JsonProcessingException, IOException, InterruptedException {
		
		//获取用户ID		
		String userName = "18810039018"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是异常描述
		//第二列参数userID
		//第三列老标签
		//第四列新标签
		//第五列返回值status
		//第六列返回值msg
		String [][] value={				
				{"老标签为空",userId,"","阳光","0","老标签不能为空"},
				{"新标签为空",userId,"健康","","0","新标签不能为空"},
				{"用户ID为空","","阳光","可爱","0","用户ID不能为空"},
				{"用户ID为null",null,"阳光","可爱","0","用户ID不能为空"},
				{"老标签为null",userId,null,"可爱","0","老标签不能为空"},
				{"新标签为null",userId,"阳光",null,"0","新标签不能为空"},
				{"用户ID不正确","随便乱输入的内容haha","阳光","可爱","0","用户不存在"},
				{"老标签为不存在的随便输入的内容",userId,"随便乱输入的内容haha","可爱","1","标签不存在，无法修改"},
				};	
		
		//返回值有msg的异常情况
		for(int j=0;j<value.length;j++){
		System.out.println("-----------------------"+j+"--------------------------------");

			String msg = UserCenter.modifyTag(value[j][1],value[j][2],value[j][3]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			String status=value[j][4];
			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(status));
			String msg1=value[j][5];
			Assert.assertTrue(node.get("msg").asText().equals(msg1));

		}
	}
	
	
	
	@Test  
	/* 查询用户是否待激活
	 * 异常情况的测试
	 * msg 失败信息“empty”（参数为空）“active”（用户已经是激活状态）“notexits”（用户不存在）
	*/
	
	public void isUserActivateExceptionTest() throws JsonProcessingException, IOException  {
		
		//参数为空
		String msg = UserCenter.isUserActivate("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("empty"));
		
		//参数是随便输入的值
		String msg1 = UserCenter.isUserActivate("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("notexits"));
		
		//参数是null
		String msg2 = UserCenter.isUserActivate(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("empty"));
	}
	
	
	
	@Test  
	/* 查询用户冲突用户
	 * 异常情况的测试 
	*/
	
	public void getConflictUserByIdExceptionTest() throws JsonProcessingException, IOException  {
		
		//参数为空
		String msg = UserCenter.getConflictUserById("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));
		
		//参数是随便输入的值
		String msg1 = UserCenter.getConflictUserById("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("未找到用户随便乱输入的内容哈哈哈哈哈"));
		
		//参数是null
		String msg2 = UserCenter.getConflictUserById(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
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
	 * 根据第三方账号创建用户
	 * 正常情况的测试
	 * 第三方账号信息，必有值：若type=qq，则openid值必须有，若type=wechat，则unionid值必须有；可选值：headimgurl、nickname
	*/
	public void createUserByThirdExceptionTest() throws JsonProcessingException, IOException {

		//type=qq openid应该是必输  现在这个没有字段没有值
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String d =date.format(new Date());
		params.put("nickname", d);
		params.put("unionid", d);
		String msg = UserCenter.createUserByThird(params,"qq");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空"));
		
		//type=wechat unionid应该是必输  现在这个没有字段没有值
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("nickname", d);
		params1.put("openid", d);
		String msg1 = UserCenter.createUserByThird(params1,"wechat");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("参数不能为空"));
		
		//参数都为空
		Map<String, String> params2 = new HashMap<String, String>();
		String msg2 = UserCenter.createUserByThird(params2,"");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		
		
		//参数都为null
		String msg3 = UserCenter.createUserByThird(null,null);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		
		
	}
	
	
	@Test  
	/* 获取用户实名认证状态
	 * 异常情况的测试 
	*/
	
	public void getPersonVerifyStatusExceptionTest() throws JsonProcessingException, IOException  {
		
		//参数为空
		String msg = UserCenter.getPersonVerifyStatus("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));
		
		//参数是随便输入的值
		String msg1 = UserCenter.getPersonVerifyStatus("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		
		//参数是null
		String msg2 = UserCenter.getPersonVerifyStatus(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
	}
	
	
	@Test  
	/* 用户ID获取用户头像URL地址
	 * 异常情况的测试
	*/
	
	public void getUserAvatarExceptionTest() throws JsonProcessingException, IOException  {
		
		//参数为空
		String msg = UserCenter.getUserAvatar("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("用户ID不能为空"));
		
		//参数是随便输入的值
		String msg1 = UserCenter.getUserAvatar("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		
		//参数是null
		String msg2 = UserCenter.getUserAvatar(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
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
		Assert.assertTrue(node1.get("msg").asText().equals("手机号为空"));	
		
		
		//时间为空，时间不是必须参数，可以自动生成
		String msg2 = UserCenter.recordU8cLoginLog("18810039018","172.20.44.29","");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt()==1);	
		
		
		//手机号为null
		String msg3 = UserCenter.recordU8cLoginLog(null,"172.20.44.29",""+System.currentTimeMillis());
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);	
		Assert.assertTrue(node1.get("msg").asText().equals("手机号为空"));	
		
		//时间为null，时间不是必须参数，可以自动生成
		String msg4 = UserCenter.recordU8cLoginLog("18810039018","172.20.44.29",null);
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt()==1);	
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
	
	
	@Test
	/* 
	 * 登录校验
	 * 异常情况的测试
	*/	
	public void verifyUserExceptionTest() throws JsonProcessingException, IOException, InterruptedException{

		
		//第一列是异常描述
		//第二列参数nc项目Id
		//第三列返回值第几页
		//第四列返回值每页大小
		//第五列返回值status
		//第六列返回值msg
		String [][] value={				
				{"用户名是随便输入的值","随便乱输入的内容haha","yonyou11","market","0","用户名或密码错误"},
				{"密码是随便输入的值","jlccstt@163.com","00000000","market","0","用户名或密码错误"},
				{"用户名为空","","yonyou11","market","0","用户名称不能为空"},
				{"密码为空","jlccstt@163.com","","market","0","用户名或密码错误"},
				{"用户名为null",null,"yonyou11","market","0","用户名称不能为空"},
				{"密码为null","jlccstt@163.com",null,"market","0","用户名或密码错误"},
				{"sysId是随便输入的值","jlccstt@163.com","yonyou11","随便乱输入的内容haha","1",""},
				{"sysId为空","jlccstt@163.com","yonyou11","","1",""},
		};	
		
		//返回值有msg的异常情况
		for(int j=0;j<6;j++){
		String msg = UserCenter.verifyUser(value[j][1],value[j][2],value[j][3]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][4]));
	//	Assert.assertTrue(node.get("msg").asText().equals(value[j][5]));
		}
		
		
		//返回值有msg的异常情况
		for(int j=7;j<8;j++){
		String msg = UserCenter.verifyUser(value[j][1],value[j][2],value[j][3]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][4]));
		}
		
		
//		String msg0 = UserCenter.verifyUser("jlccstt@163.com",null,"market");
//		System.out.println(msg0);
	
	}
	
	
	@Test
	/* 
	 * 获取nc项目的nc用户
	 * 异常情况的测试
	 * 7是NC的代表，L代表long 
	 * 设置yht.user.base.url =http://172.20.4.206:8080/rest   能查出一个数据   
	*/	
	public void listUserInNcProjectTest() throws JsonProcessingException, IOException{

		//第一列是异常描述
		//第二列参数nc项目Id
		//第三列返回值第几页
		//第四列返回值每页大小
		//第五列返回值status
		//第六列返回值msg
		String [][] value={				
				{"nc项目Id是随便输入的值","8","0","10","1","success"},
				{"第几页为负数","7","-1","10","0","unprocess"},
				{"每页大小为负数","7","0","-10","0","unprocess"}
		};	
		
		//返回值有msg的异常情况
		for(int j=0;j<3;j++){
		String msg = UserCenter.listUserInNcProject(Long.parseLong(value[j][1]),Integer.valueOf(value[j][2]),Integer.valueOf(value[j][3]));
		System.out.println(msg);
		//JsonNode node = mapper.readTree(msg);
		//Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][4]));
		}		
		
	}
	
	
	@Test
	/* 
	 * 根据旧用户ID批量查询新用户信息
	 * 异常情况的测试
	 * 用户ID数组为空或错误时应该不能查出数据
	*/
	public void getUsersByOldIdsExceptionTest() throws JsonProcessingException, IOException  {
		//参数为空
		String [] pks=new String [] {""};
		String  msg = UserCenter.getUsersByOldIds(pks);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("").get("status").asInt() == 0);
		Assert.assertTrue(node.get("").get("msg").asText().equals("没有找到用户 "));
		
		//参数值随便输入的内容
		String [] pks1=new String [] {"随便乱输入的内容哈哈"};		
		String  msg1 = UserCenter.getUsersByOldIds(pks1);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("随便乱输入的内容哈哈").get("status").asInt() == 0);
		Assert.assertTrue(node1.get("随便乱输入的内容哈哈").get("msg").asText().equals("没有找到用户 随便乱输入的内容哈哈"));
		
		//参数为null
		String [] pks2=new String [] {null};
		String  msg2 = UserCenter.getUsersByOldIds(pks2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("不能传递null值userid"));
	}
	
	@Test  
	/* 更新用户Ca配置
	 * 异常情况的测试
	*/
	
	public void updateCaConfExceptionTest() throws JsonProcessingException, IOException {
		String userName = "stt2017120601@chacuo.net"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//第一列是测试点
		//第二列参数userID
		//第三列返回值使用uKey
		//第四列返回值免密码登录(目前是预留字段，没作用)
		//第五列返回值status
		//第六列返回值msg
		//第七列返回值useUkey
		//第八列返回值noPassword
		String [][] value={				
				{"使用uKey和免密码登录的值都是null",userId,null,null,"1","","false","false"},
				{"userId是随便输入不存在的值","随便乱输入的内容哈哈","true","true","0","Can not find user by userId 随便乱输入的内容哈哈"},
				{"userId为空","","true","false","0","UserId can not be empty "},
				{"userId为null",null,"true","true","0","UserId can not be empty "},
		};	
		
		//返回值有msg的异常情况
		for(int j=0;j<4;j++){
		String msg = UserCenter.updateCaConf(value[j][1],Boolean.parseBoolean(value[j][2]),Boolean.parseBoolean(value[j][3]));
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		
		int num=Integer.valueOf(value[j][4]);
		
		if(num==1){
			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][4]));
			Assert.assertTrue(node.get("ca").get("useUkey").asText().equals(value[j][6]));
			Assert.assertTrue(node.get("ca").get("noPassword").asText().equals(value[j][7]));
		}else{
			Assert.assertTrue(node.get("status").asInt() == Integer.valueOf(value[j][4]));
			Assert.assertTrue(node.get("msg").asText().equals(value[j][5]));

		}

		}
			
	}	
		
		
		
		
		@Test
		/* 
		 * 根据用户名密码获取uKey登录token
		 * 异常情况的测试
		*/	
		public void getUkeyRandomOrAccessTokenExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数userName
			//第三列参数md5password
			//第四列参数shaPassword
			//第五列参数手机验证码
			//第六列参数是否支持多端登录
			//第七列返回值：status
			
			String Password="yonyou11";
			String md5Password=SDKUtils.encodeUsingMD5(Password); 
			String shaPassword=SDKUtils.encodeUsingSHA(Password);
			
			String [][] value1={				
					{"userName错误","随便乱输入的内容哈哈",md5Password,shaPassword,"","0"},
					{"密码错误","18611286701","随便乱输入的内容哈哈","随便乱输入的内容哈哈","","0"},
					{"userName为空","",md5Password,shaPassword,"","0"},
					{"密码为空","18611286701","","","","0"},
					{"userName为null",null,md5Password,shaPassword,"","0"},
					{"密码为null","18611286701",null,null,null,"0"},
			};	
			
			for(int i=0;i<6;i++){
			String msg = UserCenter.getUkeyRandomOrAccessToken(value1[i][1],value1[i][2],value1[i][3],value1[i][4],true);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value1[i][5]));		
			
			}
		}
		
		

		@Test
		/* 
		 * 使用ukey签名token登录
		 * 异常情况的测试
		*/	
		public void uKeyLoginExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数token
			//第三列参数ukey 对token的签名值
			//第四列返回值：status
			
			String [][] value1={				
					{"参数错误","随便乱输入的内容哈哈","随便乱输入的内容哈哈","0"},
					{"参数为空","","","0"},
					{"参数为null",null,null,"0"},
			};	
			
			for(int i=0;i<3;i++){
			String msg = UserCenter.uKeyLogin(value1[i][1],value1[i][2],true);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value1[i][3]));		
			
			}
		}
	
	
	

		@Test
		/* 
		 * 解密Ukey加密的数据
		 * 异常情况的测试
		*/	
		public void decryptExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数btmpToken
			//第三列返回值：status
			
			String encryptedStr="test29af05c912748ef9a74558046c50P0R/8XI7FQyPSzWo0Bm39VdEq+m3ot7vrl62/c1ND7EJN7KmwBHtgJgLRGCR6gVweimzyY3Jzy6PlnOZKjCFbvEHhh0NvhCovsQpawQ2EjeIHbdVAHmpDCxVd4thKtACE5oghAEzO59IlLBVFHFDZepGLEqNRGNz1QP0D64EYq8=";		
			String [][] value1={
					{"参数错误，把前四位改成了test",encryptedStr,"1"},
					{"参数值位数不正确","随便乱输入的内容哈哈","0"},
					{"参数为空","","0"},
					{"参数为null",null,"0"},
			};	
			
			for(int i=0;i<4;i++){
			String msg = UserCenter.decrypt(value1[i][1]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value1[i][2]));		
			
			}
		}
	
		
	
		@Test
		/* 
		 * 根据后端临时token获取后端登录Token
		 * 异常情况的测试
		*/	
		public void getBackSsoTokenByBTmpTokenExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数临时token
			//第三列返回值：status
			
			String [][] value1={
					{"参数不正确","随便乱输入的内容哈哈","1"},
					{"参数为空","","0"},
					{"参数为null",null,"0"},
			};	
			
			for(int i=0;i<3;i++){
			String msg = UserCenter.getBackSsoTokenByBTmpToken(value1[i][1]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value1[i][2]));		
			
			}
		}
	
	
		
		@Test
		/* 
		 * 检测AccessToken是否过期
		 * 异常情况的测试
		*/	
		public void isAccessTokenValidExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数临时AccessToken
			
			String [][] value1={
					{"参数不正确","随便乱输入的内容哈哈"},
					{"参数为空",""},
					{"参数为null",null},
			};	
			
			for(int i=0;i<3;i++){
			boolean msg = UserCenter.isAccessTokenValid(value1[i][1]);
			System.out.println(msg);
			Assert.assertFalse(msg);
			}
		}
		
		
		@Test
		/* 
		 * 快捷注册登录接口
		 * 异常情况的测试
		*/	
		public void loginOrRegisterExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数token
			//第三列参数ukey 对token的签名值
			//第四列返回值：status
			
			String [][] value1={				
					{"参数错误","随便乱输入的内容哈哈","随便乱输入的内容哈哈","0"},
					{"参数为空","","","0"},
					{"参数为null",null,null,"0"},
			};	
			
			for(int i=0;i<3;i++){
//			String msg = UserCenter.loginOrRegister(value1[i][1],value1[i][2],value1[i][3],value1[i][4]);
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value1[i][3]));		
//			
			}
		}
		
		@Test
		/* 
		 * yht用户id和nc用户Id相互转换(根据ncuserid和租户code获取yht user id)
		 * 异常情况的测试
		 * 参数为空和null的时候，执行结果返回null统一不做处理
		*/	
		public void getYhtUserIdExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数ncId
			//第三列参数tenantCode
			//第四列返回值：status
			
			String [][] value={				
					{"参数错误","随便乱输入的内容哈哈","随便乱输入的内容哈哈","0"},
					{"参数为空","","","0"},
					{"参数为null",null,null,"0"},
			};	
			
			for(int i=0;i<value.length;i++){
			String msg = UserCenter.getYhtUserId(value[i][1],value[i][2]);
			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value[i][3]));		
			
			}
		
		}
		
		@Test
		/* 
		 * yht用户id和nc用户Id相互转换(根据yht user id和租户code获取nc user id)
		 * 异常情况的测试
		*/	
		public void getNcUserIdExceptionTest() throws JsonProcessingException, IOException {
			
			//第一列是测试点
			//第二列参数ncId
			//第三列参数tenantCode
			//第四列返回值：status
			
			String [][] value={				
					{"参数错误","随便乱输入的内容哈哈","随便乱输入的内容哈哈","0"},
					{"参数为空","","","0"},
					{"参数为null",null,null,"0"},
			};	
			
			for(int i=0;i<value.length;i++){
			String msg = UserCenter.getNcUserId(value[i][1],value[i][2]);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value[i][3]));		
			
			}
		
		}
		
		
		@Test
		/* 根据用户ID列表批量获取用户标签
		 * 异常情况的测试
		*/
		public void listUserTagsExceptionTest() throws JsonProcessingException, IOException{
			
		
			//参数为null				
			String msg=UserCenter.listUserTags(null);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 0);
//			Assert.assertTrue(node.get("msg").asText().equals("userId不能为空"));

			
			//参数为空	
			List<String> users1 = new ArrayList<String>();			
			users1.add("");			
			String msg1=UserCenter.listUserTags(users1);
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt() == 0);
//			Assert.assertTrue(node1.get("msg").asText().equals("userId不能为空"));

			
			//参数为不存在的用户	
			List<String> users2 = new ArrayList<String>();			
			users2.add("哈哈哈呀呀呀");			
			String msg2=UserCenter.listUserTags(users2);
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
//			Assert.assertTrue(node2.get("status").asInt() == 0);
//			Assert.assertTrue(node2.get("msg").asText().equals("哈哈哈呀呀呀用户不存在"));
		}
		
		
		@Test  
		/* 新增NC租户白名单接口
		 * 异常情况的测试
		*/
		
		public void addNcTenantWhiteExceptionTest() throws JsonProcessingException, IOException  {
			
			//参数为空
			String msg = NCUserCenter.addNcTenantWhite("","");
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 0);
			
			//参数是随便输入的值
			String msg1 = NCUserCenter.addNcTenantWhite("随便乱输入的内容哈哈哈哈哈","随便乱输入的内容哈哈哈哈哈");
			System.out.println(msg1);
			JsonNode node1 = mapper.readTree(msg1);
//			Assert.assertTrue(node1.get("status").asInt() == 0);
			
			//参数是null
			String msg2 = NCUserCenter.addNcTenantWhite(null,null);
			System.out.println(msg2);
			JsonNode node2 = mapper.readTree(msg2);
//			Assert.assertTrue(node2.get("status").asInt() == 0);
		}
		
		
}
	

	
	
