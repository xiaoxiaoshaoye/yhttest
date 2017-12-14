package yhttest;

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
import com.yonyou.yht.sdk.EnterpriseCenter;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdk.Utils;

public class EnterpriseCenterTestSuntt {


	ObjectMapper mapper;
	
	@Before
	public void init() {
		mapper = new ObjectMapper();
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
//		params.put("name", "stt"+t);
//		params.put("contactName", "stt-name");
//		params.put("contactMobile", "18800001010");
//		String msg = EnterpriseCenter.addEnter(params);
//		System.out.println(msg);	
//		JsonNode  node=Utils.getJson(mapper, msg);
//		Assert.assertTrue(node.get("status").asInt()==1);
//		
//		//必输值加创建人，方便在页面上查看
//		Map<String ,String> params1=new HashMap<String,String>(); 		
//		params1.put("name", "stt"+t+"1");
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
//		params2.put("name", "stt"+t+"2");
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
//		params3.put("name", "stt"+t+"~!@#$%^&*()_+|{}:<>?/.,';][=-`");
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
//		
		//必输值加创建人，方便在页面上查看
		Map<String ,String> params5=new HashMap<String,String>(); 	
		for(int i=0;i<2;i++){			
		params5.put("name", "stt"+t+i);
		params5.put("contactName", "stt-name");
		params5.put("contactMobile", "18800001010");
		params5.put("creater", "1de8af53-89b7-4c74-8758-79b30b103427");
//		params5.put("creater", "4bf3e04d-a4bb-4bdf-a9df-68005ea5ea2b");
		String msg5 = EnterpriseCenter.addEnter(params5);
		System.out.println(msg5);
		JsonNode  node5=Utils.getJson(mapper, msg5);
		Assert.assertTrue(node5.get("status").asInt()==1);
		}
	}
	
	
	
	
	
	@Test
	/* 根据企业ID获取企业信息
	 * 正常情况的测试
	*/
	public void  getEnInfoTest(){
		//用户stt2017092801@chacuo.net创建的a111111这个企业的id是f33c078b-7c17-45dd-949e-3c7f734e1618
		String enterpriseId="f33c078b-7c17-45dd-949e-3c7f734e1618";
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
		Assert.assertTrue(node.get("eninfo").get("address").asText().equals("中国-河北-石家庄-bb"));
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
		Assert.assertTrue(node.get("msg").asText().equals("获取企业绑定信息成功"));

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
		Assert.assertTrue(node.get("totalNumber").asInt()==20);
		Assert.assertTrue(node.get("status").asInt()==1);
	}
}
