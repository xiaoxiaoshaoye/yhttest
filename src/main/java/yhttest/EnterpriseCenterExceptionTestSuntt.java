package yhttest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.yonyou.yht.sdk.EnterpriseCenter;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdk.Utils;
import com.yonyou.yht.sdkutils.PropertyUtil;

public class EnterpriseCenterExceptionTestSuntt {


	ObjectMapper mapper= new ObjectMapper();
	

//	  @Before
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
	/* 添加企业
	 * 异常情况的测试
	 * 用户stt2017101801@chacuo.net的ID是55806668-426e-4ba8-aa2b-f3333cd2bc43
	*/
	public void addEnterExceptionTest(){
		
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t =date.format(new Date());
		
		//name企业名称,必输字段的值是空
		Map<String ,String> params=new HashMap<String,String>(); 
		params.put("name", "");
		params.put("contactName", "stt-name");
		params.put("contactMobile", "18800001010");
		String msg = EnterpriseCenter.addEnter(params);
		System.out.println(msg);	
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("企业名称不能为空"));
				
		//name企业名称,必输字段的值是null
		Map<String ,String> params1=new HashMap<String,String>(); 
		params1.put("name",null);
		params1.put("contactName","stt-name");
		params1.put("contactMobile","18800001010");
		String msg1 = EnterpriseCenter.addEnter(params1);
		System.out.println(msg1);	
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("企业名称不能为空"));

		
		//contactName联系人姓名,必输字段的值是空
	    Map<String ,String> params2=new HashMap<String,String>(); 
	    params2.put("name","stt"+t);
	    params2.put("contactName","");
	    params2.put("contactMobile","18800002020");
	    String msg2 = EnterpriseCenter.addEnter(params2);
	    System.out.println(msg2); 
	    JsonNode  node2=Utils.getJson(mapper, msg2);
	    Assert.assertTrue(node2.get("status").asInt()==0);
	    Assert.assertTrue(node2.get("msg").asText().equals("企业联系人姓名不能为空")); 
		
		
	    //contactName联系人姓名,必输字段的值是null
	     Map<String ,String> params3=new HashMap<String,String>(); 
	     params3.put("name","stt"+t);
	     params3.put("contactName",null);
	     params3.put("contactMobile","18800003030");
	     String msg3 = EnterpriseCenter.addEnter(params3);
	     System.out.println(msg3); 
	     JsonNode  node3=Utils.getJson(mapper, msg3);
	     Assert.assertTrue(node3.get("status").asInt()==0);
	     Assert.assertTrue(node3.get("msg").asText().equals("企业联系人姓名不能为空")); 
		
		
       //contactMobile联系电话,必输字段的值是空
        Map<String ,String> params4=new HashMap<String,String>(); 
        params4.put("name","stt"+t);
        params4.put("contactName","stt-name");
        params4.put("contactMobile","");
        String msg4 = EnterpriseCenter.addEnter(params4);
        System.out.println(msg4); 
        JsonNode  node4=Utils.getJson(mapper, msg4);
        Assert.assertTrue(node4.get("status").asInt()==0);
        Assert.assertTrue(node4.get("msg").asText().equals("企业联系人手机号不能为空"));
	   
	   
       //contactMobile联系电话,必输字段的值是null
        Map<String ,String> params5=new HashMap<String,String>(); 
        params5.put("name","stt"+t);
        params5.put("contactName","stt-name");
        params5.put("contactMobile",null);
        String msg5 = EnterpriseCenter.addEnter(params5);
        System.out.println(msg5); 
        JsonNode  node5=Utils.getJson(mapper, msg5);
        Assert.assertTrue(node5.get("status").asInt()==0);
        Assert.assertTrue(node5.get("msg").asText().equals("企业联系人手机号不能为空"));
		
		
	    //contactMobile（联系电话）格式不正确
	    Map<String ,String> params6=new HashMap<String,String>(); 
	    params6.put("name","stt"+t);
	    params6.put("contactName","stt-name");
	    params6.put("contactMobile","随便乱输入的内容haha");
	    String msg6 = EnterpriseCenter.addEnter(params6);
	    System.out.println(msg6); 
	    JsonNode  node6=Utils.getJson(mapper, msg6);
	    Assert.assertTrue(node6.get("status").asInt()==0);
	    Assert.assertTrue(node6.get("msg").asText().equals("企业联系人手机号格式不正确"));
		
		
	    //type企业类型,格式不正确
	    Map<String ,String> params7=new HashMap<String,String>(); 
	    params7.put("name", "stt"+t);
	    params7.put("contactName", "stt-name");
	    params7.put("contactMobile", "18800007070");
	    params7.put("type", "随便乱输入的内容haha");
	    String msg7 = EnterpriseCenter.addEnter(params7);
	    System.out.println(msg7);
	    JsonNode  node7=Utils.getJson(mapper, msg7);
	    Assert.assertTrue(node7.get("status").asInt()==0);
	    Assert.assertTrue(node7.get("msg").asText().equals("企业类型格式不正确"));
		
	     //address企业地址,格式不正确
	     Map<String ,String> params8=new HashMap<String,String>(); 
	     params8.put("name", "stt"+t);
	     params8.put("contactName", "stt-name");
	     params8.put("contactMobile", "18800008080");
	     params8.put("address", "随便乱输入的内容haha");
	     String msg8 = EnterpriseCenter.addEnter(params8);
	     System.out.println(msg8);
	     JsonNode  node8=Utils.getJson(mapper, msg8);
	     Assert.assertTrue(node8.get("status").asInt()==0);
	     Assert.assertTrue(node8.get("msg").asText().equals("企业地址格式不正确"));
		

	      //trades所属行业,格式不正确
	      Map<String ,String> params9=new HashMap<String,String>(); 
	      params9.put("name", "stt"+t);
	      params9.put("contactName", "stt-name");
	      params9.put("contactMobile", "18800009090");
	      params9.put("trades", "随便乱输入的内容haha");
	      String msg9 = EnterpriseCenter.addEnter(params9);
	      System.out.println(msg9);
	      JsonNode  node9=Utils.getJson(mapper, msg9);
	      Assert.assertTrue(node9.get("status").asInt()==0);
	      Assert.assertTrue(node9.get("msg").asText().equals("企业所属行业格式不正确"));
	     
	      //scale企业规模,格式不正确
	      Map<String ,String> params10=new HashMap<String,String>(); 
	      params10.put("name", "stt"+t);
	      params10.put("contactName", "stt-name");
	      params10.put("contactMobile", "18800009090");
	      params10.put("scale", "随便乱输入的内容haha");
	      String msg10 = EnterpriseCenter.addEnter(params10);
	      System.out.println(msg10);
	      JsonNode  node10=Utils.getJson(mapper, msg10);
	      Assert.assertTrue(node10.get("status").asInt()==0);
	      Assert.assertTrue(node10.get("msg").asText().equals("企业规模格式不正确"));
	      
	      //invoiceType开票类型,格式不正确
	      Map<String ,String> params11=new HashMap<String,String>(); 
	      params11.put("name", "stt"+t);
	      params11.put("contactName", "stt-name");
	      params11.put("contactMobile", "18800009090");
	      params11.put("invoiceType", "随便乱输入的内容haha");
	      String msg11 = EnterpriseCenter.addEnter(params11);
	      System.out.println(msg11);
	      JsonNode  node11=Utils.getJson(mapper, msg11);
	      Assert.assertTrue(node11.get("status").asInt()==0);
	      Assert.assertTrue(node11.get("msg").asText().equals("企业开票类型格式不正确"));

	      //businessTax税号,格式不正确
	      Map<String ,String> params12=new HashMap<String,String>(); 
	      params12.put("name", "stt"+t);
	      params12.put("contactName", "stt-name");
	      params12.put("contactMobile", "18800009090");
	      params12.put("businessTax", "随便乱输入的内容haha");
	      String msg12 = EnterpriseCenter.addEnter(params12);
	      System.out.println(msg12);
	      JsonNode  node12=Utils.getJson(mapper, msg12);
	      Assert.assertTrue(node12.get("status").asInt()==0);
	      Assert.assertTrue(node12.get("msg").asText().equals("企业税号格式不正确"));

	      
	      //integrationCode统一社会信用代码,格式不正确
	      Map<String ,String> params13=new HashMap<String,String>(); 
	      params13.put("name", "stt"+t);
	      params13.put("contactName", "stt-name");
	      params13.put("contactMobile", "18800009090");
	      params13.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
	      params13.put("integrationCode", "随便乱输入的内容haha");
	      String msg13 = EnterpriseCenter.addEnter(params13);
	      System.out.println(msg13);
	      JsonNode  node13=Utils.getJson(mapper, msg13);
	      Assert.assertTrue(node13.get("status").asInt()==0);
	      Assert.assertTrue(node13.get("msg").asText().equals("统一社会信用代码格式不正确"));

	      
	      //yonyouMember是否为用友合作伙伴,格式不正确
	      Map<String ,String> params14=new HashMap<String,String>(); 
	      params14.put("name", "stt"+t);
	      params14.put("contactName", "stt-name");
	      params14.put("contactMobile", "18800009090");
	      params14.put("yonyouMember", "随便乱输入的内容haha");
	      String msg14 = EnterpriseCenter.addEnter(params14);
	      System.out.println(msg14);
	      JsonNode  node14=Utils.getJson(mapper, msg14);
	      Assert.assertTrue(node14.get("status").asInt()==0);
	      Assert.assertTrue(node14.get("msg").asText().equals("是否为用友合作伙伴格式不正确"));

	      //唯一性,当前用户下有111111的企业
	      Map<String ,String> params15=new HashMap<String,String>(); 
	      params15.put("name", "111111");
	      params15.put("contactName", "stt-name");
	      params15.put("contactMobile", "18800009090");
	      params15.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
	      String msg15 = EnterpriseCenter.addEnter(params15);
	      System.out.println(msg15);
	      JsonNode  node15=Utils.getJson(mapper, msg15);
	      Assert.assertTrue(node15.get("status").asInt()==0);
	      Assert.assertTrue(node15.get("msg").asText().equals("该企业已存在"));

	      
	      //唯一性，其他用户有已认证的stt企业
	      Map<String ,String> params16=new HashMap<String,String>(); 
	      params16.put("name", "stt");
	      params16.put("contactName", "stt-name");
	      params16.put("contactMobile", "18800009090");
	      params16.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
	      String msg16 = EnterpriseCenter.addEnter(params16);
	      System.out.println(msg16);
	      JsonNode  node16=Utils.getJson(mapper, msg16);
	      Assert.assertTrue(node16.get("status").asInt()==0);
	      Assert.assertTrue(node16.get("msg").asText().equals("该企业已存在"));


	      //当开票类型是统一社会信用代码时，但“统一社会信用代码”字段没输入值
	      Map<String ,String> params17=new HashMap<String,String>(); 
	      params17.put("name", "stt"+t);
	      params17.put("contactName", "stt-name");
	      params17.put("contactMobile", "18800009090");
	      params17.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
		  params17.put("invoiceType", "integrationCode");
	      String msg17 = EnterpriseCenter.addEnter(params17);
	      System.out.println(msg17);
	      JsonNode  node17=Utils.getJson(mapper, msg17);
	      Assert.assertTrue(node17.get("status").asInt()==0);
	      Assert.assertTrue(node17.get("msg").asText().equals("开票类型为企业统一社会信用代码，但统一社会信用代码为空"));


			//字段值超长（企业名称、联系人姓名、地址、法人、上级企业）
			Map<String ,String> params18=new HashMap<String,String>(); 
			params18.put("name", "00000000001111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
			params18.put("contactName", "00000000001111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
			params18.put("contactMobile", "18800001010");
			params18.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
			params18.put("address", "1-11-8-北清路68号00000000001111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
			params18.put("legalPerson", "00000000001111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");
			params18.put("superiorCorpId", "00000000001111111111222222222233333333334444444444555555555566666666667777777777888888888899999999990000000000111111111122222222223333333333444444444455555555556666666666777777777788888888889999999999");		
			String msg18 = EnterpriseCenter.addEnter(params18);
			System.out.println(msg18);
			JsonNode  node18=Utils.getJson(mapper, msg18);
			Assert.assertTrue(node18.get("status").asInt()==0);

	}
	
	
	
	@Test
	/* 根据企业ID获取企业信息
	 * 异常情况的测试
	*/
	public void  getEnInfoExceptionTest(){
		
		//参数是随便输入的值
		String msg = EnterpriseCenter.getEnInfo("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		
		//参数为空
		String msg1 = EnterpriseCenter.getEnInfo("");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		
		
		//参数是null
		String msg2 = EnterpriseCenter.getEnInfo(null);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
 	}
	
	
	@Test
	/* 根据企业名称查找企业
	 * 异常情况的测试
	*/
	public void  searchEnterByNameExceptionTest(){
		String enterpriseName="a11";
		String msg = EnterpriseCenter.searchEnterByName(enterpriseName);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("获取企业信息成功"));
	}
	
	@Test
	/* 根据用户ID获取该用户所管理的企业信息
	 * 正常情况的测试
	*/
	public void  getMemberEnListByUidExceptionTest(){
		//没有所管理的企业
		String userName = "stt2017092901@chacuo.net";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = EnterpriseCenter.getMemberEnListByUid(userId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		Assert.assertTrue(node.get("msg").asText().equals("未找到该用户管理的企业"));
		
		//用户id是随便输入的内容
		//执行应该给友好提示，现在报错
		String msg1 = EnterpriseCenter.getMemberEnListByUid("随便乱输入的内容haha");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("用户ID格式不正确"));
		
		//用户id为空
		String msg2 = EnterpriseCenter.getMemberEnListByUid("");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("用户ID不能为空"));
		
		//用户id为null
		String msg3 = EnterpriseCenter.getMemberEnListByUid(null);
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("用户ID不能为空"));
				
	}
	
	@Test
	/* 云数据中心绑定企业(根据企业ID和云数据中心ID（租户ID）)
	 * 异常情况的测试
	*/
	public void  bindEnterExceptionTest(){
		//用户18810039018的已认证企业“绑定解绑--异常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
		//用户18810039018的云数据中心stt02，id是jht4bwzs,已申请绑定企业，但未审核。
		String enterpriseId = "d0a36d86-acac-4115-b729-291223eaea81";
		String tenantId="jht4bwzs";
		String msg = EnterpriseCenter.bindEnter(enterpriseId,tenantId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("绑定审核中，不能重复绑定"));

		//用户18810039018的云数据中心stt03，id是vcs3t77l,已申请绑定企业，并且审核通过。
		String enterpriseId1 = "d0a36d86-acac-4115-b729-291223eaea81";
		String tenantId1="vcs3t77l";
		String msg1 = EnterpriseCenter.bindEnter(enterpriseId,tenantId1);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("该云数据中心已绑定企业，不能重复绑定 "));

		//企业和云数据中心都是随便输入的值
		String msg2 = EnterpriseCenter.bindEnter("随便乱输入的内容haha","随便乱输入的内容haha");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);

		//云数据中心是空,企业值正确
		//用户18810039018的已认证企业“绑定解绑--异常测试使用”的id是d0a36d86-acac-4115-b729-291223eaea81
		String enterpriseId3 = "d0a36d86-acac-4115-b729-291223eaea81";
		String msg3 = EnterpriseCenter.bindEnter(enterpriseId3,"");
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
//		Assert.assertTrue(node3.get("msg").asText().equals("云数据中心ID不能为空"));
		
		//云数据中心是null
		//用户18810039018的已认证企业“绑定解绑--异常测试使用”的id是d0a36d86-acac-4115-b729-291223eaea81
		String enterpriseId4 = "d0a36d86-acac-4115-b729-291223eaea81";
		String msg4 = EnterpriseCenter.bindEnter(enterpriseId4,null);
		System.out.println(msg4);
		JsonNode  node4=Utils.getJson(mapper, msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
//		Assert.assertTrue(node4.get("msg").asText().equals("云数据中心ID不能为空"));

		//企业值是空,云数据中心值正确
		//用户18810039018的云数据中心stt04，id是jht4bwzs,
		String tenantId5 = "jht4bwzs";
		String msg5 = EnterpriseCenter.bindEnter("",tenantId5);
		System.out.println(msg5);
		JsonNode  node5=Utils.getJson(mapper, msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("企业ID不能为空"));

		//企业值是null,云数据中心值正确
		//用户18810039018的云数据中心stt04，id是jht4bwzs,
		String tenantId6 = "jht4bwzs";
		String msg6 = EnterpriseCenter.bindEnter(null,tenantId6);
		System.out.println(msg6);
		JsonNode  node6=Utils.getJson(mapper, msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("企业ID不能为空"));
			
	}

	@Test
	/* 根据云数据中心id(tenantId)查询已经绑定的企业信息
	 * 异常情况的测试
	*/
	public void  searchEnterByTenantIdExceptionTest(){	
		
		//参数随便输入不正确的值
		String msg = EnterpriseCenter.searchEnterByTenantId("随便乱输入的内容haha");
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("该云数据中心未绑定企业"));
		
		//参数是空
		String msg1 = EnterpriseCenter.searchEnterByTenantId("");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
	//	Assert.assertTrue(node1.get("msg1").asText().equals("云数据中心ID不能为空"));
		
		//参数是空
		String msg2 = EnterpriseCenter.searchEnterByTenantId(null);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
	//	Assert.assertTrue(node2.get("msg2").asText().equals("云数据中心ID不能为空"));
		
	}
	
	@Test
	/* 云数据中心解绑企业(根据企业ID和云数据中心ID（租户ID）)
	 * 异常情况的测试
	*/
	public void  unBindExceptionTest(){
		//用户18810039018的已认证企业“绑定解绑--正常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
		//用户18810039018的云数据中心stt01,未帮带企业。
		String enterpriseId = "f15738fe-771b-494c-ad77-91523c10514d";
		String tenantId="huv804a2";
		String msg = EnterpriseCenter.unBind(enterpriseId,tenantId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("不存在该绑定关系"));

		//企业和云数据中心都是随便输入的值
		String msg2 = EnterpriseCenter.unBind("随便乱输入的内容haha","随便乱输入的内容haha");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);

		//云数据中心是空,企业值正确
		//用户18810039018的已认证企业“绑定解绑--异常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
		String enterpriseId3 = "d0a36d86-acac-4115-b729-291223eaea81";
		String msg3 = EnterpriseCenter.unBind(enterpriseId3,"");
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
//		Assert.assertTrue(node3.get("msg").asText().equals("云数据中心ID不能为空"));
		
		//云数据中心是null
		//用户18810039018的已认证企业“绑定解绑--异常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
		String enterpriseId4 = "d0a36d86-acac-4115-b729-291223eaea81";
		String msg4 = EnterpriseCenter.unBind(enterpriseId4,null);
		System.out.println(msg4);
		JsonNode  node4=Utils.getJson(mapper, msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
//		Assert.assertTrue(node4.get("msg").asText().equals("云数据中心ID不能为空"));

		//企业值是空,云数据中心值正确
		//用户18810039018的云数据中心stt04，id是jht4bwzs,
		String tenantId5 = "jht4bwzs";
		String msg5 = EnterpriseCenter.unBind("",tenantId5);
		System.out.println(msg5);
		JsonNode  node5=Utils.getJson(mapper, msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("企业ID不能为空"));

		//企业值是null,云数据中心值正确
		//用户18810039018的云数据中心stt04，id是jht4bwzs,
		String tenantId6 = "jht4bwzs";
		String msg6 = EnterpriseCenter.unBind(null,tenantId6);
		System.out.println(msg6);
		JsonNode  node6=Utils.getJson(mapper, msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("企业ID不能为空"));
			
	}

	@Test
	/* 根据企业ID查询企业绑定的云数据中心个数
	 * 正常情况的测试
	 *用户18810039018的已认证企业“绑定解绑--正常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
	 * 有0个已审核企业
	*/
	public void  countBindNumExceptionTest(){
		//有0个已审核企业
		String enterpriseId = "f15738fe-771b-494c-ad77-91523c10514d";
		String msg = EnterpriseCenter.countBindNum(enterpriseId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("bindNum").asInt()==0);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("获取企业绑定信息成功"));

		//参数时随便输入的值
		String msg1 = EnterpriseCenter.countBindNum("随便乱输入的内容haha");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("企业ID格式不正确"));

		//参数为空
		String msg2 = EnterpriseCenter.countBindNum("");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("企业ID不能为空"));
		
		//参数为null
		String msg3 = EnterpriseCenter.countBindNum(null);
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("企业ID不能为空"));

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
	 
	//参数值包括中文
	List<String> tenantIdList =new ArrayList<String>();
	tenantIdList.add("随便乱输入的内容haha");
	String msg = EnterpriseCenter.getStateListByTenantIdList(tenantIdList);
	System.out.println(msg);
	JsonNode  node=Utils.getJson(mapper, msg);
	Assert.assertTrue(node.get("states").get(0).get("msg").asText().equals("云数据中心ID中含有中文字符"));
	 
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
	Assert.assertTrue(node1.get("states").get(0).get("msg").asText().equals("云数据中心ID为空"));
	
	
	//参数值不是实际存在的云数据中心id
	List<String> tenantIdList3 =new ArrayList<String>();
	tenantIdList3.add("suibianshurudeneironghaha");
	String msg3 = EnterpriseCenter.getStateListByTenantIdList(tenantIdList3);
	System.out.println(msg3);
	JsonNode  node3=Utils.getJson(mapper, msg3);
	Assert.assertTrue(node3.get("states").get(0).get("state").asInt()==2);
	
	
	}
	 
	
	
	
	@Test
	/* 搜索企业内用户（关键字范围:用户名、账号、手机号、邮箱）
	 * 异常情况的测试
	 * 用户18810039018的企业“st”的ID是f2b4f8b3-a27d-4010-91cb-4d7552ef5abb
	 */
	public void  searchEnterUserExceptionTest(){	
		
		String enterId="a2962ba0-dcea-4757-859e-fb702119f52a";
		String keyWord="@";
		int pageNum=1;
		int pageSize=3;
		String sortProperty="user_name ";
		String sortDirection="ASC";
		//搜索"1"
		String msg = EnterpriseCenter.searchEnterUser(enterId,"1",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);
		Assert.assertTrue(node.get("msg").asText().equals("关键字不能用于搜索"));
	
		//搜索@
		String msg1 = EnterpriseCenter.searchEnterUser(enterId,"@",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("msg").asText().equals("关键字不能用于搜索"));
		
		//搜索条件为空，现在把全部数据查出来，然后按当前页数、条数等其他数据显示数据
		String msg2 = EnterpriseCenter.searchEnterUser(enterId,"",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==1);
		
		//搜索条件为null，现在把全部数据查出来，然后按当前页数、条数等其他数据显示数据
		String msg3 = EnterpriseCenter.searchEnterUser(enterId,null,pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==1);
		
		
		//页数为负数，现在没有给友好提示
		String msg4 = EnterpriseCenter.searchEnterUser(enterId,"stt",-3,pageSize,sortProperty,sortDirection);
		System.out.println(msg4);
		JsonNode  node4=Utils.getJson(mapper, msg4);
		Assert.assertTrue(node4.get("status").asInt()==0);
		Assert.assertTrue(node4.get("msg").asText().equals("页码不能小于1"));

		
		//页面大小为负数，现在没有给友好提示
		String msg5 = EnterpriseCenter.searchEnterUser(enterId,"stt",pageNum,-3,sortProperty,sortDirection);
		System.out.println(msg5);
		JsonNode  node5=Utils.getJson(mapper, msg5);
		Assert.assertTrue(node5.get("status").asInt()==0);
		Assert.assertTrue(node5.get("msg").asText().equals("页面大小不能小于1"));

		
		//企业ID为空
		String msg6 = EnterpriseCenter.searchEnterUser("","stt",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg6);
		JsonNode  node6=Utils.getJson(mapper, msg6);
		Assert.assertTrue(node6.get("status").asInt()==0);
		Assert.assertTrue(node6.get("msg").asText().equals("企业ID不能为空"));
		
		
		//企业ID为null
		String msg7 = EnterpriseCenter.searchEnterUser(null,"stt",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg7);
		JsonNode  node7=Utils.getJson(mapper, msg7);
		Assert.assertTrue(node7.get("status").asInt()==0);
		Assert.assertTrue(node7.get("msg").asText().equals("企业ID不能为空"));
		
		//排序依据、排序方向（升序、降序）值为空
		String msg8 = EnterpriseCenter.searchEnterUser(enterId,"stt",pageNum,pageSize,"","");
		System.out.println(msg8);
		JsonNode  node8=Utils.getJson(mapper, msg8);
		Assert.assertTrue(node8.get("status").asInt()==1);
		
		//排序依据、排序方向（升序、降序）值为null
		String msg9 = EnterpriseCenter.searchEnterUser(enterId,"stt",pageNum,pageSize,null,null);
		System.out.println(msg9);
		JsonNode  node9=Utils.getJson(mapper, msg9);
		Assert.assertTrue(node9.get("status").asInt()==1);
		
		//搜索13
		String msg10 = EnterpriseCenter.searchEnterUser(enterId,"13",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg10);
		JsonNode  node10=Utils.getJson(mapper, msg10);
		Assert.assertTrue(node10.get("status").asInt()==0);
		Assert.assertTrue(node10.get("msg").asText().equals("关键字不能用于搜索"));
		
		
		//搜索15
		String msg11 = EnterpriseCenter.searchEnterUser(enterId,"15",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg11);
		JsonNode  node11=Utils.getJson(mapper, msg11);
		Assert.assertTrue(node10.get("status").asInt()==0);
		Assert.assertTrue(node10.get("msg").asText().equals("关键字不能用于搜索"));
		
		//搜索18
		String msg12 = EnterpriseCenter.searchEnterUser(enterId,"18",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg12);
		JsonNode  node12=Utils.getJson(mapper, msg12);
		Assert.assertTrue(node12.get("status").asInt()==0);
		Assert.assertTrue(node12.get("msg").asText().equals("关键字不能用于搜索"));
		
		//搜索*
		String msg13 = EnterpriseCenter.searchEnterUser(enterId,"*",pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg13);
		JsonNode  node13=Utils.getJson(mapper, msg13);
		Assert.assertTrue(node13.get("status").asInt()==0);
		Assert.assertTrue(node13.get("msg").asText().equals("关键字不能用于搜索"));
	}
	
	@Test
	/* 
	 * 云市场企业认证失败回调
	 * 异常情况的测试
	*/	
	public void isAccessTokenValidExceptionTest() throws JsonProcessingException, IOException {

//		String enterId="681e162c-049b-4c47-b157-432ee2795da6";
		String enterId="6704db23-b51d-414a-b7af-aa99461e496a";
		String userName = "18810039018"; 
		String auditorId = UserCenterUtil.getUserIdByLoginName(userName);
		String message="测试云市场企业认证失败的情况";
		
		String [][] value1={
				{"enterId随便输入的值","随便乱输入的内容哈哈",auditorId,message},
				{"enterId为空","",auditorId,message},
				{"enterId为null",null,auditorId,message},
				{"auditorId随便输入的值",enterId,"随便乱输入的内容哈哈",message},
				{"auditorId为空",enterId,"",message},
				{"auditorId为null",enterId,null,message},
				{"失败原因为空",enterId,auditorId,""},
				{"失败原因为null",enterId,auditorId,null},
				{"企业本身就是认证失败的",enterId,auditorId,"重复验证失败"},
		};	
		
		for(int i=0;i<value1.length;i++){
		String msg = EnterpriseCenter.marketAuthenticateFailed(value1[i][1],value1[i][2],value1[i][3]);
		System.out.println(msg);
		}
	}
	
	
	@Test
	/* 
	 * 云市场企业认证成功回调
	 * 异常情况的测试
	*/	
	public void marketAuthenticateSuccessExceptionTest() throws JsonProcessingException, IOException {

		String enterId="6704db23-b51d-414a-b7af-aa99461e496a";
		String userName = "18810039018"; 
		String auditorId = UserCenterUtil.getUserIdByLoginName(userName);
		String message="测试云市场企业认证失败的情况";
		
		String [][] value1={
				{"enterId随便输入的值","随便乱输入的内容哈哈",auditorId},
				{"enterId为空","",auditorId},
				{"enterId为null",null,auditorId},
				{"auditorId随便输入的值",enterId,"随便乱输入的内容哈哈"},
				{"auditorId为空",enterId,""},
				{"auditorId为null",enterId,null},
				{"企业本身就是已认证的",enterId,auditorId},
		};	
		
		for(int i=0;i<value1.length;i++){
		String msg = EnterpriseCenter.marketAuthenticateSuccess(value1[i][1],value1[i][2]);
		System.out.println(msg);
		}
	}
	
	
	@Test
	/* 根据UserID搜索ISV企业
	 * 正常情况的测试
	*/
	public void   searchMarketISVenterByUserIdTest(){
		
		//参数时随便输入的值
		String msg1 = EnterpriseCenter.searchMarketISVenterByUserId("随便乱输入的内容haha");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);
		Assert.assertTrue(node1.get("data").asText().equals(""));

		//参数为空
		String msg2 = EnterpriseCenter.searchMarketISVenterByUserId("");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("userId不能为空"));
		
		//参数为null
		String msg3 = EnterpriseCenter.searchMarketISVenterByUserId(null);
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("userId不能为空"));

	}
	
	
	@Test
	/* 根据企业ID获取云市场企业信息
	 * 正常情况的测试
	*/
	public void   getMarketEnterInfoTest(){
		
		//参数时随便输入的值
		String msg1 = EnterpriseCenter.getMarketEnterInfo("随便乱输入的内容haha");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);
		Assert.assertTrue(node1.get("data").asText().equals(""));

		//参数为空
		String msg2 = EnterpriseCenter.getMarketEnterInfo("");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("enterId不能为空"));
		
		//参数为null
		String msg3 = EnterpriseCenter.getMarketEnterInfo(null);
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("enterId不能为空"));

	}
	
	@Test
	/* 天威企业基本信息认证
	 * 异常情况的测试
	 */
	public void  enterpriseITrustExceptionTest(){

		//参数时随便输入的值
		String msg1 = EnterpriseCenter.enterpriseITrust("随便乱输入的内容hahaaaaaa");
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==0);
		Assert.assertTrue(node1.get("data").asText().equals("该企业帐号不存在"));

		//参数为空
		String msg2 = EnterpriseCenter.enterpriseITrust("");
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);
		Assert.assertTrue(node2.get("msg").asText().equals("tenantId不能为空"));
		
		//参数为null
		String msg3 = EnterpriseCenter.enterpriseITrust(null);
		System.out.println(msg3);
		JsonNode  node3=Utils.getJson(mapper, msg3);
		Assert.assertTrue(node3.get("status").asInt()==0);
		Assert.assertTrue(node3.get("msg").asText().equals("tenantId不能为空"));
		
	}
	
	@Test
	/* 授权应用标记企业用户
	 * 异常情况的测试
	 * enterId的值是用户18810039018里201471220-a-188haha这个企业的id
	 * 这个用例执行，需要在manager里企业应用授权里添加一个数据。
	 * 企业名是201471220-a-188haha，应用是uculture。
	 * 这个是根据String authfile="uculture.properties"这个里面的username的值
	 * 并且权限是读写
	 */
	public void  markEnterUserExceptionTest() throws JsonProcessingException, IOException{

		String enterId="f83a9964-5c8e-4b8e-b2cc-0a174cb952cf";
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是测试点
		//第二列参数enterId
		//第三列参数userId 
		//第四列返回值：status
		
		String [][] value={		
				
				{"enterId错误","随便乱输入的内容哈哈",userId,"0","企业不存在"},
				{"enterId为空","",userId,"0","enterId不能为空"},
				{"enterId为null",null,userId,"0","enterId不能为空"},
				{"userId错误",enterId,"随便乱输入的内容哈哈","0","未找到用户"},
				{"userId为空",enterId,"","0","userId不能为空"},
				{"userId为null",enterId,null,"0","userId不能为空"},
		};	
		
		for(int i=0;i<value.length;i++){		
		String msg = EnterpriseCenter.markEnterUser(value[i][1],value[i][2]);
		System.out.println(msg);
		System.out.println("------------------------------"+i+"-------------------------");	
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value[i][3]));		
		Assert.assertTrue(node.get("msg").asText().equals(value[i][4]));
		}
		
	}
	
	
	@Test
	/* 授权应用移除标记企业用户
	 * 异常情况的测试
	 * enterId的值是用户18810039018里201471220-a-188haha这个企业的id
	 * 这个用例执行，需要在manager里企业应用授权里添加一个数据。
	 * 企业名是201471220-a-188haha，应用是uculture。
	 * 这个是根据String authfile="uculture.properties"这个里面的username的值
	 * 并且权限是读写
	 */
	public void  unMarkEnterUserExceptionTest() throws JsonProcessingException, IOException{

		String enterId="f83a9964-5c8e-4b8e-b2cc-0a174cb952cf";
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//第一列是测试点
		//第二列参数enterId
		//第三列参数userId 
		//第四列返回值：status
		
		String [][] value={				
				{"enterId错误","随便乱输入的内容哈哈",userId,"0","企业不存在"},
				{"enterId为空","",userId,"0","enterId不能为空"},
				{"enterId为null",null,userId,"0","enterId不能为空"},
				{"userId错误",enterId,"随便乱输入的内容哈哈","0","未找到用户"},
				{"userId为空",enterId,"","0","userId不能为空"},
				{"userId为null",enterId,null,"0","userId不能为空"},
		};	
		
		for(int i=0;i<value.length;i++){
		String msg = EnterpriseCenter.unMarkEnterUser(value[i][1],value[i][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value[i][3]));		
		Assert.assertTrue(node.get("msg").asText().equals(value[i][4]));
		}
		}
	
	
	
	
	@Test
	/* 根据时间获取最新的企业信息
	 * 异常情况的测试
	 */
	public void  listNewEnter2MDExceptionTest() throws JsonProcessingException, IOException{

		String time="2018-07-03 14:50:31";
		String dateFormat = "yyyy-MM-dd HH:mm:ss"; 
		
		//第一列是测试点
		//第二列参数enterId
		//第三列参数userId 
		//第四列返回值：status
		
		String [][] value={				
				{"time格式错误","随便乱输入的内容哈哈",dateFormat,"0","时间格式转换异常"},
				{"time为空","",dateFormat,"0","时间不能为空"},
				{"time为null",null,dateFormat,"0","时间不能为空"},
				{"dateFormat格式错误",time,"随便乱输入的内容哈哈","0","时间格式转换异常"},
				{"dateFormat为空",time,"","0","时间格式不能为空"},
				{"dateFormat为null",time,null,"0","时间格式不能为空"},
		};	
		
		for(int i=0;i<value.length;i++){
		String msg = EnterpriseCenter.listNewEnter2MD(value[i][1],value[i][2]);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt()==Integer.valueOf(value[i][3]));	
		Assert.assertTrue(node.get("msg").asText().equals(value[i][4]));
		}
		
	}
}
