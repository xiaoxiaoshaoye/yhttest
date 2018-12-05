package com.yonyou.yht.sdk;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.yht.ex.sdk.NCUserCenter;
import com.yonyou.yht.ex.sdk.UserCenter;
import com.yonyou.yht.sdkutils.PropertyUtil;

import yhttest.UserCenterUtil;

public class YhtSdkExternalSuntt {
	
ObjectMapper mapper= new ObjectMapper();
	

//  @Before
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
	/* 
	 * 对外融合SDK提供基于手机号查询用户功能
	 * 正常情况的测试
	 * 外部接口是getUserByMobile，内部接口是getUserByCode
	 * 执行这个用例，需要把pom.xml里修改下
	 * <dependency>
     *<groupId>com.yonyou.yht</groupId>
     *<artifactId>yht-sdk-external</artifactId>   
     *<version>1.0-SNAPSHOT</version>
		</dependency>
	 */
	public void getUserByMobileTest(){				
		String mobile="18810039018";
		String msg = UserCenter.getUserByMobile(mobile);
		System.out.println(msg);
	}

	@Test
	/* 根据手机号或邮箱及验证码绑定NC用户并生成RefreshToken
	 * 正常流程测试
	 * sendValidateCodeTest()先获取验证码，然后再执行这个测试用例
	*/
	public void genRefreshTokenTest() throws JsonProcessingException, IOException{
		
		String str = NCUserCenter.genRefreshToken("yixixinzi@126.com",
    			"email",//email or mobile
    			"577519",//验证码；
    			"er323",//租户id
    			"test10001A210MU",//ncc_USERID
    			"34gwbffddd0003df5c");

        System.out.println(str);	
		JsonNode node = mapper.readTree(str);
		Assert.assertTrue(node.get("status").asInt() == 1);

		
	}
	
	@Test
	/*
	 * 测试多语
	 * 正常情况的测试
	 */
	public void testNccMultiLanguage(){

	   InvocationInfoProxy.setLocale("zh_TW");
	   String str= NCUserCenter.genLoginToken("ddewsfs");
	   System.out.println(str);
	   InvocationInfoProxy.setLocale("zh_TW");
	   String str2= NCUserCenter.genRefreshToken("zhangtl@sdfs", "email", "2131", "sdfsdf", "dds", "dsdf");
	   System.out.println(str2);
	}

}
