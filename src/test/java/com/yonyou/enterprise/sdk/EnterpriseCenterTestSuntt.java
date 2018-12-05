package com.yonyou.enterprise.sdk;

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


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.entity.UserInfo;
import com.yonyou.yht.sdk.CustomerCenter;
import com.yonyou.yht.sdk.EnterpriseCenter;
import com.yonyou.yht.sdk.SDKUtils;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdk.Utils;
import com.yonyou.yht.sdkutils.PropertyUtil;

import net.sf.json.JSONObject;

public class EnterpriseCenterTestSuntt {

	ObjectMapper mapper= new ObjectMapper();
	

	// @Before
		public void  beforeEuc(){
			System.setProperty("yht.load.order","2");
			String path="eucsdk.properties";
//			String authfile="euc.properties";
			String authfile="marketeuc.properties";
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
			String authfile="market.properties";
			//String authfile="uculture.properties";
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
	/*添加企业
	 * 正常情况的测试
	 * 用户stt2017101801@chacuo.net的ID是55806668-426e-4ba8-aa2b-f3333cd2bc43
	*/
	public void addEnterTest(){
		
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t =date.format(new Date());
		
//		//只填必输值
//		Map<String ,String> params=new HashMap<String,String>(); 
//		params.put("name", "测试01stt"+t);
//		params.put("contactName", "stt-name");
//		params.put("contactMobile", "18800001010");
//		String msg = EnterpriseCenter.addEnter(params);
//		System.out.println(msg);	
//		JsonNode  node=Utils.getJson(mapper, msg);
//		Assert.assertTrue(node.get("status").asInt()==1);
//		
//		//必输值加创建人，方便在页面上查看
//		Map<String ,String> params1=new HashMap<String,String>(); 		
//		params1.put("name", "测试01stt"+t+"1");
//		params1.put("contactName", "stt-name");
//		params1.put("contactMobile", "18800001010");
//		params1.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
//		String msg1 = EnterpriseCenter.addEnter(params1);
//		System.out.println(msg1);
//		JsonNode  node1=Utils.getJson(mapper, msg1);
//		Assert.assertTrue(node1.get("status").asInt()==1);
//		
//
//
//		
//		
//		//接口文档里有的字段，能填的字段都输入值
//		Map<String ,String> params2=new HashMap<String,String>(); 
//		params2.put("name", "测试01stt"+t+"2");
//		params2.put("contactName", "stt-name");
//		params2.put("contactMobile", "18800001010");
//		params2.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
//		params2.put("type", "IndividualPerson");
//		params2.put("address", "1-11-8-北清路68号aaaaa");
//		params2.put("trades", "E");
//		params2.put("scale", "Thousand");
//		params2.put("invoiceType", "integrationCode");
//		params2.put("businessTax", "111111111111110");
//		params2.put("integrationCode", "111111111111110000");
//		params2.put("legalPerson", "aaa");
//		params2.put("yonyouMember", "true");
//		String msg2 = EnterpriseCenter.addEnter(params2);
//		System.out.println(msg2);
//		JsonNode  node2=Utils.getJson(mapper, msg2);
//		Assert.assertTrue(node2.get("status").asInt()==1);
//		
//
//		//特殊字符，不包括"\ 因为有这两个代码就不通过
//		Map<String ,String> params3=new HashMap<String,String>(); 
//		params3.put("name", "测试01stt"+t+"~!@#$%^&*()_+|{}:<>?/.,';][=-`");
//		params3.put("contactName", "stt-name");
//		params3.put("contactMobile", "18800001010");
//		params3.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
//		String msg3 = EnterpriseCenter.addEnter(params3);
//		System.out.println(msg3);
//		JsonNode  node3=Utils.getJson(mapper, msg3);
//		Assert.assertTrue(node3.get("status").asInt()==1);
//		
//		//只能执行一次，增加实际的公司，能把部分信息带出来
//		Map<String ,String> params4=new HashMap<String,String>(); 
//		params4.put("name", "广州统一企业有限公司");
//		params4.put("contactName", "stt-name");
//		params4.put("contactMobile", "18800001010");
//		params4.put("creater", "55806668-426e-4ba8-aa2b-f3333cd2bc43");
//		String msg4 = EnterpriseCenter.addEnter(params4);
//		System.out.println(msg4);
//		JsonNode  node4=Utils.getJson(mapper, msg4);
//		Assert.assertTrue(node4.get("status").asInt()==1);
		
		//必输值加创建人，方便在页面上查看	
		
		Map<String ,String> params5=new HashMap<String,String>(); 	
		for(int i=0;i<1;i++){			
		params5.put("name", "测试01s"+t+i);
		params5.put("contactName", "stt-name");
		params5.put("contactMobile", "18800001010");
		params5.put("creater", "1ad6048c-7729-478e-9b24-d1663c7da4b3");
//		params5.put("creater", "555efe32-1447-4f86-96cc-a972d440ea0b");
		params5.put("contactEmail", "suntt@yonyou.com");		
		String msg5 = EnterpriseCenter.addEnter(params5);
		System.out.println(msg5);
		JsonNode  node5=Utils.getJson(mapper, msg5);
		Assert.assertTrue(node5.get("status").asInt()==1);
		}
		
		
//		//循环多建几个企业
//		for (int j=0;j<50;j++){
//		Map<String ,String> params5=new HashMap<String,String>(); 	
//		for(int i=0;i<1;i++){			
//		params5.put("name", "测试01s"+t+i+j);
//		params5.put("contactName", "stt-name");
//		params5.put("contactMobile", "18800001010");
//		params5.put("creater", "6cb090a7-83aa-493e-a493-fcfc7448a80e");
//		params5.put("contactEmail", "suntt@yonyou.com");		
//		String msg5 = EnterpriseCenter.addEnter(params5);
//		System.out.println(msg5);
//		JsonNode  node5=Utils.getJson(mapper, msg5);
//		Assert.assertTrue(node5.get("status").asInt()==1);
//		}
//		}
		
	}
	
	
	
	
	
	@Test
	/* 根据企业ID获取企业信息
	 * 正常情况的测试
	*/
	public void  getEnInfoTest(){
		//用户stt2017092801@chacuo.net创建的a111111这个企业的id是f33c078b-7c17-45dd-949e-3c7f734e1618
		String enterpriseId="f33c078b-7c17-45dd-949e-3c7f734e1618";
		//String enterpriseId="868d2718-4723-4504-aa03-a773918c2fdb";	
		String msg = EnterpriseCenter.getEnInfo(enterpriseId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("eninfo").get("scale").asText().equals("100-500人"));
		Assert.assertTrue(node.get("eninfo").get("yonyouMember").asBoolean());
		Assert.assertTrue(node.get("eninfo").get("businessTax").asText().equals("000000000000000"));
		Assert.assertTrue(node.get("eninfo").get("type").asText().equals("个人企业"));
		Assert.assertTrue(node.get("eninfo").get("integrationCode").asText().equals("111111111111111111"));
		Assert.assertTrue(node.get("eninfo").get("legalPerson").asText().equals("aa"));
		Assert.assertTrue(node.get("eninfo").get("invoiceType").asText().equals("businessTax"));
		Assert.assertTrue(node.get("eninfo").get("contactMobile").asText().equals("18800001111"));
		Assert.assertTrue(node.get("eninfo").get("address").asText().equals("中国-河北省-石家庄市-bb"));
		Assert.assertTrue(node.get("eninfo").get("responsePersonName").asText().equals("aa"));
		Assert.assertTrue(node.get("eninfo").get("contactName").asText().equals("dd"));
		Assert.assertTrue(node.get("eninfo").get("sourceSystemId").asText().equals("yht"));
		Assert.assertTrue(node.get("eninfo").get("trades").asText().equals("采矿业"));
		Assert.assertTrue(node.get("eninfo").get("responsePersonIDCode").asText().equals("aa"));
		Assert.assertTrue(node.get("eninfo").get("creater").asText().equals("b1820c08-e50f-4f6d-b074-c5187ab9ab51"));
		Assert.assertTrue(node.get("eninfo").get("superiorCorpId").asText().equals("cc"));
	}
	
	
	@Test
	/*根据企业名称查找企业
	 * 正常情况的测试
	*/
	public void  searchEnterByNameTest(){

		String enterpriseName="接口测试数据-查询";
		String msg = EnterpriseCenter.searchEnterByName(enterpriseName);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("获取企业信息成功"));
		Assert.assertTrue(node.get("enterprises").get(0).get("name").asText().equals("接口测试数据-查询-认证不通过"));
		Assert.assertTrue(node.get("enterprises").get(1).get("name").asText().equals("接口测试数据-查询-申请中"));
		Assert.assertTrue(node.get("enterprises").get(2).get("name").asText().equals("接口测试数据-查询-已认证"));
		Assert.assertTrue(node.get("enterprises").get(3).get("name").asText().equals("接口测试数据-查询-未认证"));


	}
	
	
	@Test
	/* 根据用户ID获取该用户所管理的企业信息
	 * 正常情况的测试
	*/
	public void  getMemberEnListByUidTest(){
		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = EnterpriseCenter.getMemberEnListByUid(userId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);
	}

	
	@Test
	/* 云数据中心绑定企业(根据企业ID和云数据中心ID（租户ID）)
	 * 正常情况的测试
	*/
	public void  bindEnterTest(){
		//用户18810039018的已认证企业“绑定解绑--正常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
		//用户18810039018的云数据中心stt01,未帮带企业。
		//此测试方法，先绑定，再解绑。
		String enterpriseId = "f15738fe-771b-494c-ad77-91523c10514d";
		String tenantId="huv804a2";
		
		//绑定的代码
		String msg = EnterpriseCenter.bindEnter(enterpriseId,tenantId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("绑定企业成功"));
		//解绑的代码
		String msg1 = EnterpriseCenter.unBind(enterpriseId,tenantId);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);
		Assert.assertTrue(node1.get("msg").asText().equals("解除绑定成功"));
		
	}

	
	@Test
	/* 云数据中心绑定企业(根据企业ID、云数据中心ID（租户ID）、用户ID（主要用于判断该租户是否需要企业管理员审核）)
	 * 正常情况的测试
	*/
	public void  bindEnter1Test(){
		//用户18810039018的已认证企业“绑定解绑--正常测试使用”的id是f15738fe-771b-494c-ad77-91523c10514d
		//用户18810039018的云数据中心stt05,tenantId=bdv029uh,未帮带企业。
		//用户18810039018的云数据中心stt06,tenantId=t67iench,未帮带企业。
		//用户18611286701的云数据中心18611286701stt01,tenantId=swc3evbp,未帮带企业。
		//此测试方法，先绑定，再解绑。
		

		String data [][]={
				{"f15738fe-771b-494c-ad77-91523c10514d","bdv029uh","18810039018","1","企业ID和租户ID都是18810039018的"},
				{"f15738fe-771b-494c-ad77-91523c10514d","t67iench","18611286701","0","企业ID和租户ID都不是18611286701的"},
				{"f15738fe-771b-494c-ad77-91523c10514d","swc3evbp","18611286701","0","企业ID是18810039018，租户ID是18611286701的"}
				};

		for(int i=0;i<data.length;i++){
		
		String userName = data[i][2];
		String userId = UserCenterUtil.getUserIdByLoginName(userName);	
			
		//绑定的代码
		String msg = EnterpriseCenter.bindEnter(data[i][0],data[i][1],userId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		System.out.println("-----------------------"+i+"--------------------");
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("绑定企业成功"));
		Assert.assertTrue(node.get("code").asInt()==Integer.valueOf(data[i][3]));
		//解绑的代码
		String msg1 = EnterpriseCenter.unBind(data[i][0],data[i][1]);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);
		Assert.assertTrue(node1.get("msg").asText().equals("解除绑定成功"));
		
		}
	}
	
	
	@Test
	/* 根据云数据中心id(tenantId)查询已经绑定的企业信息
	 * 正常情况的测试
	 * 用户18810039018的云数据中心stt03，id是vcs3t77l,已申请绑定企业，已审核。
	*/
	public void  searchEnterByTenantIdTest(){		
		String tenantId="vcs3t77l";
		String msg = EnterpriseCenter.searchEnterByTenantId(tenantId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("获取企业信息成功"));
		Assert.assertTrue(node.get("enterpriseToWebs").get(0).get("id").asText().equals("d0a36d86-acac-4115-b729-291223eaea81"));
		Assert.assertTrue(node.get("enterpriseToWebs").get(0).get("name").asText().equals("绑定解绑--异常测试使用"));

	}
	
	@Test
	/* 云数据中心解绑企业(根据企业ID和云数据中心ID（租户ID）)
	 * 正常情况的测试
	*/
	public void  unBindTest(){

		//正常测试的代码，再绑定企业中已测试，为了多次执行自动化考虑，需要两个一起测试。
		
	}
	
	
	
	@Test
	/* 根据企业ID查询企业绑定的云数据中心个数
	 * 正常情况的测试
	 * 用户18810039018的企业“stt”的ID是f2b4f8b3-a27d-4010-91cb-4d7552ef5abb
	 * 有两个已绑定的企业，1个待审核的企业
	*/
	public void  countBindNumTest(){		
		String enterpriseId="f2b4f8b3-a27d-4010-91cb-4d7552ef5abb";
		String msg = EnterpriseCenter.countBindNum(enterpriseId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("bindNum").asInt()==2);
		Assert.assertTrue(node.get("status").asInt()==1);
		Assert.assertTrue(node.get("msg").asText().equals("查询绑定信息成功"));

	}
	
	
	
	@Test
	/* 根据租户ID，查询关联企业ID以及之间的绑定状态(批量)
	 * 正常情况的测试
	 * 用户18810039018的云数据中心stt01, id是"huv804a2"未帮带企业。
	 * 用户18810039018的云数据中心stt02，id是jht4bwzs,已申请绑定企业，未审核。
	 * 用户18810039018的云数据中心stt03，id是vcs3t77l,已申请绑定企业，已审核。
	 * * state状态对应关系
	 * 0   ：  绑定审核中
	 * 1   ：  已绑定
	 * 2   ：  未绑定
	 * 用户18810039018的已认证企业“绑定解绑--异常测试使用”的id是d0a36d86-acac-4115-b729-291223eaea81
	 */
	public void  getStateListByTenantIdListTest(){	
		List<String> tenantIdList =new ArrayList<String>();
		tenantIdList.add("huv804a2");
		tenantIdList.add("jht4bwzs");
		tenantIdList.add("vcs3t77l");
		String msg = EnterpriseCenter.getStateListByTenantIdList(tenantIdList);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("states").get(0).get("state").asInt()==2);
		Assert.assertTrue(node.get("states").get(1).get("state").asInt()==0);
		Assert.assertTrue(node.get("states").get(1).get("enterId").asText().equals("d0a36d86-acac-4115-b729-291223eaea81"));
		Assert.assertTrue(node.get("states").get(2).get("state").asInt()==1);
		Assert.assertTrue(node.get("states").get(2).get("enterId").asText().equals("d0a36d86-acac-4115-b729-291223eaea81"));
	}
	
	
	
	@Test
	/* 搜索企业内用户（关键字范围:用户名、账号、手机号、邮箱）
	 * 正常情况的测试
	 * 用户18810039018的企业“stt”的ID是f2b4f8b3-a27d-4010-91cb-4d7552ef5abb
	 */
	public void  searchEnterUserTest(){	
		
		String enterId="f2b4f8b3-a27d-4010-91cb-4d7552ef5abb";
		String keyWord="stt";
		int pageNum=1;
		int pageSize=30;
		String sortProperty="user_name ";
		String sortDirection="ASC";
		String msg = EnterpriseCenter.searchEnterUser(enterId,keyWord,pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("totalNumber").asInt()==11);
		Assert.assertTrue(node.get("status").asInt()==1);
		
		//管理员包含18810039018，但是这个接口是不包含管理员的
		String keyWord1="18810039018";
		String msg1 = EnterpriseCenter.searchEnterUser(enterId,keyWord1,pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("totalNumber").asInt()==0);
		
	}
	
	
	@Test
	/* 搜索企业内所有用户（包括管理员）（关键字范围:用户名、账号、手机号、邮箱）
	 * 正常情况的测试
	 * 用户18810039018的企业“stt”的ID是f2b4f8b3-a27d-4010-91cb-4d7552ef5abb
	 */
	public void  searchAllEnterUserTest(){	
		
		String enterId="f2b4f8b3-a27d-4010-91cb-4d7552ef5abb";
		String keyWord="stt";
		int pageNum=2;
		int pageSize=6;
		String sortProperty="user_name ";
		String sortDirection="ASC";
		String msg = EnterpriseCenter.searchAllEnterUser(enterId,keyWord,pageNum,pageSize,sortProperty,sortDirection);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("totalNumber").asInt()==11);
		Assert.assertTrue(node.get("status").asInt()==1);
		
		//管理员包含18810039018，这个接口是包含管理员的
		String keyWord1="18810039018";
		int pageNum1=1;
		String msg1 = EnterpriseCenter.searchAllEnterUser(enterId,keyWord1,pageNum1,pageSize,sortProperty,sortDirection);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("totalNumber").asInt()==1);
		
	}
	
	@Test
	/* 云市场企业认证失败回调
	 * 正常情况的测试
	 */
	public void  marketAuthenticateFailedTest(){	
		
//		String enterId="cb6e7100-6537-4977-b076-ed71f7fdaf7e";
		String enterId="63c70829-4a49-474e-92f4-fc3729439bb4";
	
		String userName = "18810039018"; 
//		String auditorId = UserCenterUtil.getUserIdByLoginName(userName);
//		String auditorId = "488e6137-e684-4d40-86ea-a619d43c5a50";
		
		//euc的数据
		String auditorId = "e06b733d-8b4b-4fce-856d-d085284dbdd8";
		
		
		String message="测试云市场企业认证失败的情况";
		String msg = EnterpriseCenter.marketAuthenticateFailed(enterId,auditorId,message);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);		
	}
	
	@Test
	/* 云市场企业认证成功回调
	 * 正常情况的测试
	 */
	public void  marketAuthenticateSuccessTest(){	
		

		String enterId="558aff6f-9749-4b77-92d4-7d8fff2fa090";
//		String userName = "18810039018"; 
//		String auditorId = UserCenterUtil.getUserIdByLoginName(userName);
		
		//idtest的数据
		String auditorId = "488e6137-e684-4d40-86ea-a619d43c5a50";
		
		//euc的数据
//		String auditorId = "e06b733d-8b4b-4fce-856d-d085284dbdd8";
		
		String msg = EnterpriseCenter.marketAuthenticateSuccess(enterId,auditorId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);		
	}
	
	@Test
	/* 根据UserID搜索ISV企业
	 * 正常情况的测试
	 */
	public void  searchMarketISVenterByUserIdTest(){
		String userName = "stt2018020601@test1988.com"; 
		String auditorId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = EnterpriseCenter.searchMarketISVenterByUserId(auditorId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);		
	}
	
	@Test
	/* 根据企业ID获取云市场企业信息
	 * 正常情况的测试
	 */
	public void  getMarketEnterInfoTest(){
		String enterId="f2b4f8b3-a27d-4010-91cb-4d7552ef5abb";
		String msg = EnterpriseCenter.getMarketEnterInfo(enterId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);		
	}
	
	@Test
	/* 根据企业ID获取云市场企业信息
	 * 正常情况的测试
	 */
	public void  getMarketEnterInfoTest1(){
		String enterId="a89ed630-80cd-432d-bad0-2f0602015d83";
		String msg = EnterpriseCenter.getMarketEnterInfo(enterId,"02fdb2dd-c3a8-479a-b3fc-c5d8a33c0eac");
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);		
	}
	
	
	@Test
	/* 企业认证信息回写接口
	 * 正常情况的测试
	 * 企业未认证，则直接认证。如果企业不存在，则创建企业，再认证。
	 * 创建完，可以通过userId这个用户登录企业中心，看这个企业是否认证成功。
	 */
	public void  saveEnterpriseAuthInfoTest(){
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("enterpriseName", "广州还有音乐传播有限公司");
		json.put("licenseRegisterCode", "111111111111122222");
		json.put("userId", "e6b50050-c00d-458b-9f95-8af04d1b1ff1");
		json.put("enterpriseType", "LegalPerson");
		json.put("agentName", "经办人小哈");
		json.put("agentMobile", "18800000001");
		json.put("agentEmail", "jingbanren01@test1988.com");
		json.put("agentCertNo", "110111111111111101");
		json.put("contactName", "企业联系人小乖");
		json.put("contactMobile", "18800000002");
		json.put("contactEmail", "qiyelianxiren01@test1988.com");
		json.put("address", "1-11-8-北清路68号");
		json.put("registerTime", "20180315000521");
		json.put("legalPersoName", "法人小屁");
		json.put("legalPersoCertNo", "110111111111111102");
		json.put("bankAcctBankNo", "111222333");
		json.put("bankAcctCnapsCode", "444555666");
		json.put("bankAcctAcctNo", "777888999");
		json.put("bankAcctAcctName", "银行对公账户名小闪");
		json.put("treasurerName", "财务小嘚瑟");
		json.put("treasurerCertNo", "110111111111111103");
		json.put("licenseImage", "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");
		json.put("legalPersonIdCardBack", "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");
		json.put("legalPersonIdCardFront", "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");

		String msg = EnterpriseCenter.saveEnterpriseAuthInfo(SDKUtils.gson.toJson(json));
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);		
	}
	
	@Test
	/* 天威企业基本信息认证
	 * 正常情况的测试
	 */
	public void  enterpriseITrustTest(){
		//stt2018030101@test1988.com  的企业   
		//通过企业对公打款认证页面http://idtest.yyuap.com/enterprise/remitauthoo?sysid=ipu&tenantid=gg4jrldf
		String enterId="42d205bc-8cdf-4542-8944-4517677506d1b";
		String msg = EnterpriseCenter.enterpriseITrust(enterId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);	
		
		//stt2018030101@test1988.com  的未认证的企业中心建的企业2018030101-a
		String enterId1="c73885eb-889b-4cd4-b47d-cadc909d9836";
		String msg1 = EnterpriseCenter.enterpriseITrust(enterId1);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		
	}
	
	
	@Test
	/* 获取天威认证地址
	 * 正常情况的测试
	 */
	public void  enterItrust(){
		
		String tenantId="zog4csgc";
		String msg1 = EnterpriseCenter.enterpriseITrust(tenantId);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		
	}
	
	@Test
	/* 授权应用标记企业用户
	 * 正常情况的测试
	 * enterId的值是用户18810039018里201471220-a-188haha这个企业的id
	 * 这个用例执行，需要在manager里企业应用授权里添加一个数据。
	 * 企业名是201471220-a-188haha，应用是uculture。
	 * 这个是根据String authfile="uculture.properties"这个里面的username的值
	 * 并且权限是读写
	 */
	public void  markEnterUserTest(){
			
		String enterId="f83a9964-5c8e-4b8e-b2cc-0a174cb952cf";
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg1 = EnterpriseCenter.markEnterUser(enterId,userId);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		
	}
	
	@Test
	/* 授权应用移除标记企业用户
	 * 正常情况的测试
	 * enterId的值是用户18810039018里201471220-a-188haha这个企业的id
	 * 并且权限是读写
	 */
	public void  unMarkEnterUserTest(){
			
		String enterId="f83a9964-5c8e-4b8e-b2cc-0a174cb952cf";
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg1 = EnterpriseCenter.unMarkEnterUser(enterId,userId);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		
	}
	
	
	@Test
	/* 根据时间获取最新的企业信息
	 * 正常情况的测试
	 */
	public void  listNewEnter2MDTest(){
			
		String time="2018-11-03 14:50:31";
		String dateFormat = "yyyy-MM-dd HH:mm:ss"; 
		String msg1 = EnterpriseCenter.listNewEnter2MD(time,dateFormat);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
	}
	
	@Test
	/* 根据企业ID获取企业对公打款认证信息（云合同e签宝）
	 * 正常情况的测试
	 * enterId的值是用户18810039018里"荣盛建设工程有限公司"这个企业的id
	 */
	public void  getTsignRemitInfoTest(){
		   
		String enterId="4219e8dd-d3f9-46d4-a030-41aa1909d2ef";
		String msg1 = EnterpriseCenter.getTsignRemitInfo(enterId);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		
	}
	
	
	@Test
	/* 新增客户--通过word“11-11上线相关接口测试”接口文档编写的
	 * 正常情况的测试
	 * 执行接口，改一下sysCustomerId的值，就会在yht-manager/客户中台里低辨识度审核里有一个待审批的数据
	 * 如果sysCustomerId是已存在的数据，就会把已存在的客户带出来。yht-manager/客户中台里低辨识度审核里没有待审数据
	 * 新增后，可以根据name到库里查询下，select *from pub_yht_enterprise where name = 'stt科技有限公司';
	 */
	public void  addCustomerTest(){
		
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t =date.format(new Date());
		String sysCustomerId=t;
		String name="测试01stt科技有限公司"+t;
		
		System.out.println("***********************"+t+"***********************");
		
		//如果sysCustomerId的值是新的，客户不存在。
		String baseInfo = "{\"masterDataCode\":\"\",\"sysCustomerId\":\""+sysCustomerId+"\",\"name\":\""+name+"\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"stt南宁市青秀区金湖路61号佳得鑫水晶城D座1703号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String extInfo = "{\"createTime\":\"2018-11-02\",\"MDM_CODE\":\"\",\"shortName\":\"stt哈哈\",\"totalEmployee\":\"0601\"}";
		String msg = CustomerCenter.addCustomer(baseInfo, extInfo);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==0);	
		Assert.assertTrue(node.get("msg").asText().equals("未找到客户工商信息，已放至审核队列"));	
		
		
		//如果sysCustomerId的值是已存在的，客户已存在。
//		String baseInfo1 = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"1756888\",\"name\":\"stt科技有限公司\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"stt南宁市青秀区金湖路61号佳得鑫水晶城D座1703号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String baseInfo1 = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"1756888\",\"name\":\"测试01stt科技有限公司888\",\"industryEb\":\"0021\",\"regionEb\":\"021111\",\"integrationCode\":\"91450100MA5KD9Raaa\",\"website\":\"\",\"address\":\"stt用友产业园888号11111\",\"legalperson\":\"叶宏林11\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
//		String baseInfo1 = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"778899\",\"name\":\"测试01stt科技有限公司8888\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"stt用友产业园888号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String extInfo1 = "{\"createTime\":\"2018-11-06\",\"MDM_CODE\":\"\",\"shortName\":\"stt哈哈\",\"totalEmployee\":\"0601\"}";
		String msg1 = CustomerCenter.addCustomer(baseInfo1, extInfo1);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		Assert.assertTrue(node1.get("msg").asText().equals("成功"));	
		
		//只填必输字段
		String sysCustomerId2="02"+t;
		String name2="测试01stt科技有限公司02"+t;
		String baseInfo2 = "{\"sysCustomerId\":\""+sysCustomerId2+"\",\"name\":\""+name2+"\"}";
		String extInfo2 = "";
		String msg2 = CustomerCenter.addCustomer(baseInfo2, extInfo2);
		System.out.println(msg2);
		JsonNode  node2=Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt()==0);	
		Assert.assertTrue(node2.get("msg").asText().equals("未找到客户工商信息，已放至审核队列"));	


		
		
	}
	
	@Test
	/* 根据主数据编码或各应用中客户ID查询客户信息--通过word“11-11上线相关接口测试”接口文档编写的
	 * 正常情况的测试
	 * addCustomer这个接口新增的，还没有审核的客户，在getByCode接口里使用，是查不到的。
	 * 这个里面得是审核通过的
	 */
	public void  getByCodeTest(){
		String masterDataCode = "";
		String sysCustomerId = "778899";
		String msg = CustomerCenter.getByCode(masterDataCode, sysCustomerId);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);	
		Assert.assertTrue(node.get("msg").asText().equals("成功"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("name").asText().equals("测试01stt科技有限公司8888"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("industryEb").asText().equals("0001"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("integrationCode").asText().equals("91450100MA5KD9R87W"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("address").asText().equals("stt用友产业园888号"));	
	
		
	}
	
	
	@Test
	/* 根据名称精确查询客户信息--通过word“11-11上线相关接口测试”接口文档编写的
	 * 正常情况的测试
	 * addCustomer这个接口新增的，还没有审核的客户，在getByCode接口里使用，是查不到的。
	 * 这个里面得是审核通过的
	 */
	public void  getByNameTest(){
		String name = "测试01stt科技有限公司8888";
		String msg = CustomerCenter.getByName(name);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);	
		Assert.assertTrue(node.get("msg").asText().equals("成功"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("industryEb").asText().equals("0001"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("integrationCode").asText().equals("91450100MA5KD9R87W"));	
		Assert.assertTrue(node.get("data").get("baseInfo").get("address").asText().equals("stt用友产业园888号"));	
	}
	
	@Test
	/* 客户名称变更--通过word“11-11上线相关接口测试”接口文档编写的
	 * 正常情况的测试
	 * 客户得是审核通过的。
	 */
	public void  updateCustomerNameTest(){
		String masterDataCode = "";
		String sysCustomerId = "778899";
		String name = "测试01stt科技有限公司8888999";
		String msg = CustomerCenter.updateCustomerName(masterDataCode, sysCustomerId,name);
		System.out.println(msg);
		JsonNode  node=Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt()==1);	
		Assert.assertTrue(node.get("msg").asText().equals("成功"));	
//		Assert.assertTrue(node.get("data").get("masterDataCode").asText().equals("KH02000000000088"));	
		

		
		String name1 = "测试01stt科技有限公司8888";
		String msg1 = CustomerCenter.updateCustomerName(masterDataCode, sysCustomerId,name1);
		System.out.println(msg1);
		JsonNode  node1=Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);	
		Assert.assertTrue(node1.get("msg").asText().equals("成功"));	
//		Assert.assertTrue(node1.get("data").get("masterDataCode").asText().equals("KH02000000000088"));	


	}
	
	
	

}
