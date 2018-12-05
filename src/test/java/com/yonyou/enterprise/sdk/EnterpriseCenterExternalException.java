package com.yonyou.enterprise.sdk;

import java.util.Enumeration;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.ex.sdk.EnterpriseCenter;
import com.yonyou.yht.sdk.Utils;
import com.yonyou.yht.sdkutils.PropertyUtil;

public class EnterpriseCenterExternalException {
	
	ObjectMapper mapper= new ObjectMapper();
	

	  @Before
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
		
	//  @Before
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
	/* 根据企业名获取企业基本信息
	 * 异常情况的测试
	*/
	public void  getEnterpriseByNameExceptionTest(){
		
		//参数是随便输入的值
		String msg = EnterpriseCenter.getEnterpriseByName("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("企业不存在"));
		
		//参数为空
		String msg1 = EnterpriseCenter.getEnterpriseByName("");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("企业名称不能为空"));
		
		
		//参数是null
		String msg2 = EnterpriseCenter.getEnterpriseByName(null);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("企业名称不能为空"));
		
 	}

	
	@Test
	/* 根据企业ID获取企业信息
	 * 异常情况的测试
	*/
	public void  getEnterpriseByIdExceptionTest(){
		
		//参数是随便输入的值
		String msg = EnterpriseCenter.getEnterpriseById("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("企业不存在"));
		
		//参数为空
		String msg1 = EnterpriseCenter.getEnterpriseById("");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("企业id不能为空"));
		
		
		//参数是null
		String msg2 = EnterpriseCenter.getEnterpriseById(null);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("企业id不能为空"));
		
 	}
	
	
	@Test
	/* 根据企业帐号ID获取企业信息
	 * 异常情况的测试
	*/
	public void  getEnterpriseByEnterAccountIdExceptionTest(){
		
		//参数是随便输入的值
		String msg = EnterpriseCenter.getEnterpriseByEnterAccountId("随便乱输入的内容haha哈哈哈哈aaaa");
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("该企业帐号不存在"));
		
		//参数为空
		String msg1 = EnterpriseCenter.getEnterpriseByEnterAccountId("");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("企业帐号id不能为空"));
		
		
		//参数是null
		String msg2 = EnterpriseCenter.getEnterpriseByEnterAccountId(null);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("企业帐号id不能为空"));
		
 	}
	
}
