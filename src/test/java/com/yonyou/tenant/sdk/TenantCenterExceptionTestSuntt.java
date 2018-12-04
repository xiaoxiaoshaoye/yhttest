package com.yonyou.tenant.sdk;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.iuap.tenant.entity.Tenant;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
import com.yonyou.iuap.tenant.sdk.UserCenter;
import com.yonyou.yht.sdkutils.PropertyUtil;

import yhttest.UserCenterUtil;


public class TenantCenterExceptionTestSuntt {
	ObjectMapper mapper;
	
	@Before
	public void init() {
		mapper = new ObjectMapper();
	}

	@Test
	/* 根据租户id获取租户信息
	 * 异常流程测试
	*/
	public void getTenantByIdExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getTenantById("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getTenantById(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//租户id不存在
		String msg2=TenantCenter.getTenantById("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据租户code获取租户信息
	 * 异常流程测试
	*/
	public void getTenantByTenantCodeExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getTenantByTenantCode("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getTenantByTenantCode(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数值是不存在的，随便输入的值
		String msg2=TenantCenter.getTenantByTenantCode("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	@Test
	/* 根据租户code获取租户状态
	 * 异常流程测试
	*/
	public void getTenantStatusExceptionTest() throws JsonProcessingException, IOException{

		//code为空
		String tenantCodes []={"",""};
		String msg=TenantCenter.getTenantStatus(tenantCodes);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//code为null
		String tenantCodes1 []={null};
		String msg1=TenantCenter.getTenantStatus(tenantCodes1);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
			
		//参数值是不存在的，随便输入的值
		String tenantCodes2 []={"随便乱输入的内容haha"};
		String msg2=TenantCenter.getTenantStatus(tenantCodes2);	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		//参数值是null
		String msg3=TenantCenter.getTenantStatus(null);	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
	}
	
	@Test
	/* 根据租户id获取企业LOGO
	 * 异常流程测试
	*/
	public void getLogoByTenantIdExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getLogoByTenantId("");		
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getLogoByTenantId(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//租户id是中文
		String msg2=TenantCenter.getLogoByTenantId("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		//租户id是不存在的值
		String msg3=TenantCenter.getLogoByTenantId("n45h6puhn45h6puh");	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
	}
	
	

	@Test
	/* 根据用户id和系统code获取租户
	 * 异常流程测试
	*/
	public void getUserIdByLoginNameExceptionTest() throws JsonProcessingException, IOException{

		String userName = "jlccstt@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//系统code为空
		String msg=TenantCenter.getCanUseTenants(userId,"");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//系统code为null
		String msg1=TenantCenter.getCanUseTenants(userId,null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//系统code为不存在的值
		String msg2=TenantCenter.getCanUseTenants(userId,"随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		//用户id为空
		String msg3=TenantCenter.getCanUseTenants("","u8");	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		
		//用户id为null
		String msg4=TenantCenter.getCanUseTenants(null,"u8");	
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 1);
		
		//用户id为不存在的值
		String msg5=TenantCenter.getCanUseTenants("随便乱输入的内容haha","u8");	
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt() == 1);	
		
	}
	
	@Test
	/* 根据用户id和系统code获取可以登录租户
	 * 异常流程测试
	*/
	public void getCanLoginTenantsExceptionTest() throws JsonProcessingException, IOException{

		String userName = "jlccstt@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		
//		//系统code为空
//		String msg=TenantCenter.getCanLoginTenants(userId,"");	
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//系统code为null
		String msg1=TenantCenter.getCanLoginTenants(userId,null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//系统code为不存在的值
		String msg2=TenantCenter.getCanLoginTenants(userId,"随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		//用户id为空
		String msg3=TenantCenter.getCanLoginTenants("","u8");	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		
		//用户id为null
		String msg4=TenantCenter.getCanLoginTenants(null,"u8");	
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 1);
		
		//用户id为不存在的值
		String msg5=TenantCenter.getCanLoginTenants("随便乱输入的内容haha","u8");	
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
		Assert.assertTrue(node5.get("status").asInt() == 1);	
		
	}
	
	@Test
	/* 根据用户id查询所有的租户
	 * 异常流程测试
	*/
	public void findTenantsByUserIdExceptionTest() throws JsonProcessingException, IOException{

//		//参数都为空
//		String msg=TenantCenter.findTenantsByUserId("");	
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		
//		//参数都为null
//		String msg1=TenantCenter.findTenantsByUserId(null);	
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数不存在
		String msg2=TenantCenter.findTenantsByUserId("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据用户id查询所管理的租户
	 * 异常流程测试
	*/
	public void getUserManageTenantsExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getUserManageTenants("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getUserManageTenants(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数不存在
		String msg2=TenantCenter.getUserManageTenants("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据resCode查询所有的租户
	 * 异常流程测试
	*/
	public void getTenantIdsByResCodeExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getTenantIdsByResCode("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getTenantIdsByResCode(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数不存在
		String msg2=TenantCenter.getTenantIdsByResCode("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	
	
	@Test
	/* 根据tenantId查询所有的应用的code
	 * 异常流程测试
	*/
	public void getResCodesByTenantIdExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getResCodesByTenantId("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getResCodesByTenantId(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数不存在
		String msg2=TenantCenter.getResCodesByTenantId("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据userId获取该用户的应用列表
	 * 异常流程测试
	*/
	public void getAppsByUserIdExceptionTest() throws JsonProcessingException, IOException{

		//参数都为空
		String msg=TenantCenter.getAppsByUserId("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getAppsByUserId(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数不存在
		String msg2=TenantCenter.getAppsByUserId("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 查询启用了diwork并激活了NCC应用的租户列表
	 * 异常流程测
	 * ps 页面大小 
	 * pn 页号
	 * sortType 排序类型（默认不填）升序排列
	 */
	public void searchActiveNccTenantOnDiworkExceptionTest() throws JsonProcessingException, IOException{
		
		//参数都为空
		String msg=TenantCenter.searchActiveNccTenantOnDiwork("","","");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.searchActiveNccTenantOnDiwork(null,null,null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数值随便输入，不符合要求
		String msg2=TenantCenter.searchActiveNccTenantOnDiwork("随便乱输入的内容haha","随便乱输入的内容haha","随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		//第10页没有数据，查不出来数据，但不能报错
		String ps = "2"; 
		String pn = "10";
		String sortType = "";
		String msg3=TenantCenter.searchActiveNccTenantOnDiwork(ps,pn,sortType);	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		
	}


	
	@Test
	/* 根据租户ID查询U8c激活地址和变更剩余次数信息
	 * 异常流程测试
	*/
	public void getU8cActiveUrlExceptionTest() throws JsonProcessingException, IOException{

//		//参数都为空
//		String msg=TenantCenter.getU8cActiveUrl("");	
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//参数都为null
		String msg1=TenantCenter.getU8cActiveUrl(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//参数值随便输入，不符合要求
		String msg2=TenantCenter.getU8cActiveUrl("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}


	
	@Test
	/* 判断某租户是否购买了NCC并已激活成功 
	 * 异常流程测试
	*/
	public void checkNCCActiveStatusExceptionTest() throws JsonProcessingException, IOException{
		//参数都为空
		String msg=TenantCenter.checkNCCActiveStatus("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空 tenantId"));
		
		//参数都为null
		String msg1=TenantCenter.checkNCCActiveStatus(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		
		//参数值随便输入，不符合要求
		String msg2=TenantCenter.checkNCCActiveStatus("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
	}
	
	@Test
	/* 根据租户ID查询NCC集团主键 
	 * 异常流程测试
	*/
	public void getGroupPkByEnterAccIdExceptionTest() throws JsonProcessingException, IOException{
		//参数都为空
		String msg=TenantCenter.getGroupPkByEnterAccId("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
				
		//参数都为null
		String msg1=TenantCenter.getGroupPkByEnterAccId(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		
		//参数值随便输入，不符合要求
		String msg2=TenantCenter.getGroupPkByEnterAccId("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("主企业账号未绑定集团"));

	}
	
	
	@Test
	/* 根据企业代码更新租户信息
	 * 异常流程测试
	 * 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号cc
	*/
	public void updateTenantByCodeExceptionTest() throws JsonProcessingException, IOException{
	
		//只填租户编码
		Map<String, String> params = new HashMap<String, String>();
		params.put("tenantCode", "stt2018092701cc");
		String msg=TenantCenter.updateTenantByCode(params);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("保存成功"));
		
		//参数为空
		Map<String, String> params1 = new HashMap<String, String>();
		String msg1=TenantCenter.updateTenantByCode(params1);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("租户编码不能为空"));
		
		//参数为null
		String msg2=TenantCenter.updateTenantByCode(null);	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("租户编码不能为空"));

		//字段名不存在，字段值不存在
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("tenantCode", "stt2018092701cc");
		params3.put("tenantEmail", "suntt333@yonyou.com");
		params3.put("tenantIndustry", "不存在的行业");
		params3.put("aaabbbcccddd", "aaabbbcccddd");
		String msg3=TenantCenter.updateTenantByCode(params3);	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 0);

	}

	
	@Test
	/* 根据租户主键查询当前租户下所有的用户的主键  
	 * 异常流程测试
	*/
	public void getUserIdsByTenantIdExceptionTest() throws JsonProcessingException, IOException{
//		//参数都为空
//		String msg=UserCenter.getUserIdsByTenantId("");	
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 0);
//				
//		//参数都为null
//		String msg1=UserCenter.getUserIdsByTenantId(null);	
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 0);
		
		//参数值随便输入，不符合要求
		String msg2=UserCenter.getUserIdsByTenantId("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);

	}
	
	
	
	/**
     * 根据账号判断是否是管理员
     * 异常流程测试
     * stt2018092701@test1988.com这个用户的aa企业帐号l5v7r7ts
     */
    @Test
   	public void isAdminNewExceptionTest() throws JsonProcessingException, IOException{
    	
    	//tenantId是不存在的值
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
       	String msg  = UserCenter.isAdminNew("l5v7r7ts",userId);
       	System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("flag").asInt() == 0);
		
		//添加用户，设置成管理员
       	String msg1  = UserCenter.isAdminNew("l5v7r7ts","hahahahahah不存在的值");
       	System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("flag").asInt() == 0);
		
		//参数为空
       	String msg2  = UserCenter.isAdminNew("","");
       	System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("flag").asInt() == 0);
		
		//参数为null
       	String msg3  = UserCenter.isAdminNew("","");
       	System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		Assert.assertTrue(node3.get("flag").asInt() == 0);
   	}
    
    
    
	@Test
	/* 将用户加入指定租户  
	 * 异常流程测试
	*/
	public void addToTenantExceptionTest() throws JsonProcessingException, IOException{
		String tenantId  = "bdv029uh";
		int  userType  = 1;
		String userName1 = "suntt1026@126.com"; 
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		String userName2 = "18611286701"; 
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName1);
		String [] pks  = {userId1,userId2};
		String [] pks1  = {,};
		
		//tenantId   为空
		String msg=UserCenter.addToTenant("",userType,pks);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("请传入要处理的数据"));		
			
		//tenantId为null
		String msg1=UserCenter.addToTenant(null,userType,pks);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("请传入要处理的数据"));
		
		//tenantId随便输入，不符合要求
		String msg2=UserCenter.addToTenant("随便乱输入的内容haha",userType,pks);	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 0);
		
		// userType(1为租户管理员，3为普通用户),故意输入错误的值
		String msg3=UserCenter.addToTenant(tenantId,1000,pks);	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 0);		
		
		//userID为空
		String msg4=UserCenter.addToTenant(tenantId,userType,pks1);	
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 0);
		Assert.assertTrue(node4.get("msg").asText().equals("数组为空"));
		
		//userID为null
		String [] pks2  = {null,null};
		String msg5=UserCenter.addToTenant(tenantId,userType,pks2);	
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
//		Assert.assertTrue(node5.get("status").asInt() == 0);
//		Assert.assertTrue(node5.get("msg").asText().equals("数组为空"));
		
		//userID不存在
		String [] pks3  = {"随便乱输入的内容haha","随便乱输入的内容haha1"};
		String msg6=UserCenter.addToTenant(tenantId,userType,pks3);	
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
//		Assert.assertTrue(node6.get("status").asInt() == 0);
//		Assert.assertTrue(node6.get("msg").asText().equals("userID不存在"));
		
		
		//添加101个用户
		int  userType7  = 3;
		String userName7;
		String userId7;
		String [] pks7  = new String [101];
		for(int i=0;i<10;i++){
			userName7="1860000000"+i;
			userId7 = UserCenterUtil.getUserIdByLoginName(userName7);
			pks7[i]  = userId7;
		}
		for(int i=10;i<100;i++){
			userName7="186000000"+i;
			userId7 = UserCenterUtil.getUserIdByLoginName(userName7);
			pks7[i]  = userId7;
		}
		pks7[100]="18611286701";
		String msg7=UserCenter.addToTenant(tenantId,userType7,pks7);	
		System.out.println(msg7);
		JsonNode node7 = mapper.readTree(msg7);
		Assert.assertTrue(node7.get("status").asInt() == 1);
	}
    
    

    
    
	@Test
	/* 云应用启用接口 
	 * 异常流程测试
	*/
	public void enableAppExceptionTest() throws JsonProcessingException, IOException{
		String tenantId  = "bdv029uh";
		String resCode = "retail"; 
	
		//tenantId   为空
		String msg=TenantCenter.enableApp("",resCode);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空"));		
			
		//tenantId为null
		String msg1=TenantCenter.enableApp(null,resCode);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("参数不能为空"));
		
//		//tenantId随便输入，不符合要求
//		String msg2=TenantCenter.enableApp("随便乱输入的内容haha",resCode);	
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
////		Assert.assertTrue(node2.get("status").asInt() == 0);
		
		// 应用编码为空
		String msg3=TenantCenter.enableApp(tenantId,"");	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 0);	
		Assert.assertTrue(node3.get("msg").asText().equals("参数不能为空"));
		
		//应用编码为null
		String msg4=TenantCenter.enableApp(tenantId,null);	
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
		Assert.assertTrue(node4.get("status").asInt() == 0);
		Assert.assertTrue(node4.get("msg").asText().equals("参数不能为空"));
		
		//应用编码是不存在的值
		String msg5=TenantCenter.enableApp(tenantId,"随便乱输入的内容haha");	
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
//		Assert.assertTrue(node5.get("status").asInt() == 0);
//		Assert.assertTrue(node5.get("msg").asText().equals("应用编码不存在"));
		

	}
    
    
    
	@Test
	/* 应用下移除用户
	 * 异常流程测试
	*/
	public void removeUsersFromAppsTest() throws JsonProcessingException, IOException{
		String tenantId  = "bdv029uh";
		String resCode = "retail"; 
		int  userType  = 3;
		
		//10个用户的ID
		String userName;
		String userId;
		String [] pks2  = new String [10];
		for(int i=0;i<10;i++){
			userName="1860000000"+i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i]  = userId;
		}
		//tenantId   为空
		String msg=UserCenter.removeUsersFromApps("",resCode,pks2);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("message").asText().equals("租户不能为空"));		
			
		//tenantId为null
		String msg1=UserCenter.removeUsersFromApps(null,resCode,pks2);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("message").asText().equals("租户不能为空"));
		
		//tenantId随便输入，不符合要求
		String msg2=UserCenter.removeUsersFromApps("随便乱输入的内容haha",resCode,pks2);	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 0);
//		Assert.assertTrue(node2.get("message").asText().equals("租户不存在"));
		
		// 应用编码为空
		String msg3=UserCenter.removeUsersFromApps(tenantId,"",pks2);	
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 0);	
		Assert.assertTrue(node3.get("message").asText().equals("应用不能为空"));
		
		//应用编码为null
		String msg4=UserCenter.removeUsersFromApps(tenantId,null,pks2);	
		System.out.println(msg4);
		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 0);
		Assert.assertTrue(node4.get("message").asText().equals("应用不能为空"));
		
		//应用编码是不存在的值
		String msg5=UserCenter.removeUsersFromApps(tenantId,"随便乱输入的内容haha",pks2);	
		System.out.println(msg5);
		JsonNode node5 = mapper.readTree(msg5);
//		Assert.assertTrue(node5.get("status").asInt() == 0);
//		Assert.assertTrue(node5.get("message").asText().equals("应用不存在"));
		
		//用户id为空
		String [] pks6  ={};
		String msg6=UserCenter.removeUsersFromApps(tenantId,resCode,pks6);	
		System.out.println(msg6);
		JsonNode node6 = mapper.readTree(msg6);
//		Assert.assertTrue(node6.get("status").asInt() == 0);
		Assert.assertTrue(node6.get("message").asText().equals("用户不能为空"));
		
		
		//用户id为不存在
		String [] pks8  ={"随便乱输入的内容haha"};
		String msg8=UserCenter.removeUsersFromApps(tenantId,resCode,pks8);	
		System.out.println(msg8);
		JsonNode node8 = mapper.readTree(msg8);
//		Assert.assertTrue(node8.get("status").asInt() == 0);
//		Assert.assertTrue(node8.get("message").asText().equals("用户不存在"));
		
	}
    
    
    
	@Test
	/* 开通应用 
	 * 异常流程测试
	*/
	public void StringBathOpenAppExceptionTest() throws JsonProcessingException, IOException{
		//参数都为空
		String msg=TenantCenter.StringBathOpenApp("");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空"));
		
		//参数都为null
		String msg1=TenantCenter.StringBathOpenApp(null);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		
		//参数值随便输入，不符合要求
		String msg2=TenantCenter.StringBathOpenApp("随便乱输入的内容haha");	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
	}
    
    
    
    
    
    
    
    
}