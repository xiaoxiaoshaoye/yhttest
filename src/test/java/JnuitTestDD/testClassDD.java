package JnuitTestDD;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.tenant.sdk.UserCenter;

import net.sf.json.JSONObject;

public class testClassDD {
	@BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
    }

    // Run once, e.g close connection, cleanup
    @AfterClass
    public static void runOnceAfterClass() {
        System.out.println("@AfterClass - runOnceAfterClass");
    }

    // Should rename to @BeforeTestMethod
    // e.g. Creating an similar object and share for all @Test
    @Before
    public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod");
    }

    // Should rename to @AfterTestMethod
    @After
    public void runAfterTestMethod() {
        System.out.println("@After - runAfterTestMethod");
    }

    @Test
    public void test_method_1() {
        System.out.println("@Test - test_method_1");
    }

    @Test
    public void test_method_2() {
        System.out.println("@Test - test_method_2");
    }
    @Test
    public void getUserByIDTest()  {
    	
    	/*
    	 * 1、场景测试-获取的数据是否正确，边界值校验,将参数的值填为开发语言中的关键字 
    	 * 2、兼容性测试-接口做了调整，前端界面没有变化，新的接口是否支持旧的调用方式
    	 * 3、异常测试-环境异常、网络异常
    	 * 4、性能测试-响应时间，并发数，服务器的cpu、内存、IO、Network
    	 * 5、安全测试-敏感信息是否加密
    	 * 
    	 * */
    	String userId = "722b78ba-53fc-4200-bfdf-682f97c31e3a"; 
    	String msgString = UserCenter.getUserById(userId);
    	System.out.println(msgString);
    	JSONObject jsonObject = JSONObject.fromObject(msgString);
    	
    	System.out.println(jsonObject.getString("user"));
//    	Assert.assertTrue((jsonObject.getString("user")).getString("userCode"));
    	String mobileString = jsonObject.getJSONObject("user").getString("userMobile");
    	String userString = "{\"name\":\"shily\",\"sex\":\"女\",\"age\":\"23\"}";
    		
    	JSONObject actual = JSONObject.fromObject(userString);
//		Assert.assertEquals(jsonObject.getString("user"), actual);
//    	Assert.assertTrue(mobileString == "15210142172");
    	
        
    	
    }
    public void isUserExistTest(Map<?, ?> param) throws JsonProcessingException, IOException {

        
 		//		String userCode = "dd";
 		//		String msg = UserCenter.isUserCodeExist(userCode);
 		//		System.out.println(msg);
 		//		JsonNode node = mapper.readTree(msg);
 		//		Assert.assertTrue(node.get("status").asInt()==1);
 		//		Assert.assertTrue(node.get("flag").asInt()==1);

 		//		ObjectMapper mapper= new ObjectMapper();
 		//		String str1 = UserCenter.isUserExist("15210142172", "15210142172",null);
 		//		System.out.println("usercode与手机号一样且匹配：1");
 		//		System.out.println(str1);
 		//		JsonNode node1 = mapper.readTree(str1);
 		//		Assert.assertTrue(node1.get("status").asInt()==1);
 		//status:0
	  
// 				        String str1= UserCenter.isUserExist("",null,null);
	        String str1= UserCenter.isUserExist(param.get("usercode").toString(),param.get("usermobile").toString(),param.get("useremail").toString());
 						System.out.println("手机号和邮箱不能同时为空:0");
 						System.out.println(str1);
// 						JsonNode node1 = mapper.readTree(str1);
// 						Assert.assertTrue(node1.get("status").asInt()==0);
 		//status:1
// 				        String str1= UserCenter.isUserExist("YHT-226-5601582092339312","15210142172" ,null);
// 						System.out.println("usercode与手机号一样且匹配:2");
// 						System.out.println(str1);
// 					    JsonNode node1 = mapper.readTree(str1);
// 						Assert.assertTrue(node1.get("status").asInt()==1);

 		//status:2
// 				  String str1= UserCenter.isUserExist("asd02",null ,"dujuanh@yonyou.com");
// 				  System.out.println("usercode与邮箱一样且匹配:2");
// 				  System.out.println(str1);
// 			     JsonNode node1 = mapper.readTree(str1);
// 				 Assert.assertTrue(node1.get("status").asInt()==2);
 		//status:3
// 				  String str1= UserCenter.isUserExist("asd02","17801888888" ,"dujuanh@yonyou.com");
// 				System.out.println("usercode、手机、邮箱完全且匹配:3");
// 				System.out.println(str1);
// 			     JsonNode node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==3);
 		//status:4  -用户账号已经存在
// 				String  str1 = UserCenter.isUserExist("YHT-473-3831552274659609",null,"dujuanh123@yonyou.com");
// 				System.out.println("usercode匹配，手机、邮箱都不匹配:4");
// 				System.out.println(str1);
// 			    JsonNode node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==4);

 		//status:5
// 				String str1 =  UserCenter.isUserExist( null,"15210142172","dddddd@e.com");
// 				System.out.println("只有手机号匹配:5");
// 				System.out.println(str1);
// 			    JsonNode  node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==5);
 		//status:6
// 				String str1 = UserCenter.isUserExist( null,"13681550002","dujuanh@yonyou.com");
// 				System.out.println("只有邮箱匹配:6");
// 				System.out.println(str1);
// 			    JsonNode  node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==6);
 		//status:7
// 				String str1 = UserCenter.isUserExist( "ewe","13581550002","dddddd@e.com");
// 				System.out.println("都不匹配，不存在存在这样的用户：7");
// 				System.out.println(str1);
// 				JsonNode node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==7);
 		//status:8
// 				String str1 =UserCenter.isUserExist("15210142172","15210142172","dddddd@e.com");
// 				System.out.println("该手机号作为另一个用户的usercode已经存在:8");
// 				System.out.println(str1);
// 				JsonNode node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==8);
 		//status:9
// 	  
// 				String str1  = UserCenter.isUserExist( null,"13581550002","dujuanh@yonyou.com");
// 				System.out.println("该邮箱作为另一个用户的usercode已经存在:9");
// 				System.out.println(str1);
// 			   
// 				JsonNode node1 = mapper.readTree(str1);
// 				Assert.assertTrue(node1.get("status").asInt()==9);
 		//status:10
// 		String str1  = UserCenter.isUserExist( null,"15210142172","dujuanh@yonyou.com");
// 		System.out.println("手机，邮箱匹配:10");
// 		System.out.println(str1);
// 		JsonNode node1 = mapper.readTree(str1);
// 		Assert.assertTrue(node1.get("status").asInt()==10);


 	}
    

}
