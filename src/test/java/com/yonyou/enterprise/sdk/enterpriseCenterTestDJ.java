package com.yonyou.enterprise.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.sdk.CustomerCenter;
import com.yonyou.yht.sdk.EnterpriseCenter;
import com.yonyou.yht.sdk.Utils;

public class enterpriseCenterTestDJ {

	ObjectMapper mapper = new ObjectMapper();
	@Test
	/*
	 * 根据企业ID获取企业信息 正常情况的测试
	 */
	public void getEnInfoTest() {
		// 企业名称cesi0717a   entid=d12d2d24-bb50-4e4b-a155-90524cd4471
		//String enterpriseId = "d12d2d24-bb50-4e4b-a155-90524cd44715";
		String enterpriseId = "";
		// String enterpriseId="868d2718-4723-4504-aa03-a773918c2fdb";
		String msg = EnterpriseCenter.getEnInfo(enterpriseId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("eninfo").get("scale").asText().equals("100-500人"));
		Assert.assertTrue(node.get("eninfo").get("yonyouMember").asBoolean());
		//Assert.assertTrue(node.get("eninfo").get("businessTax").asText().equals("000000000000000"));

	}
	@Test
	/*
	 * 新增客户--通过word“11-11上线相关接口测试”接口文档编写的 正常情况的测试
	 * 执行接口，改一下sysCustomerId的值，就会在yht-manager/客户中台里低辨识度审核里有一个待审批的数据
	 * 如果sysCustomerId是已存在的数据，就会把已存在的客户带出来。yht-manager/客户中台里低辨识度审核里没有待审数据
	 * 新增后，可以根据name到库里查询下，select *from pub_yht_enterprise where name =
	 * 'stt科技有限公司';
	 */
	public void addCustomerTest() {

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		String sysCustomerId = "啊哈哈0323aaa";
		String name = "测试dd科技有限公司0323aaa";

		// 新增一个不存在的客户。sysCustomerId的值是新的，客户不存在。
		String baseInfo = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"" + sysCustomerId + "\",\"name\":\"" + name
				+ "\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"ddd南宁市青秀区金湖路61号佳得鑫水晶城D座1703号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String extInfo = "{\"createTime\":\"2020-02-02\",\"MDM_CODE\":\"\",\"shortName\":\"dd哈哈0323\",\"totalEmployee\":\"0601\"}";
		String msg = CustomerCenter.addCustomer(baseInfo, extInfo);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("未找到客户工商信息，已放至审核队列"));

		// 如果sysCustomerId的值是已存在的，客户已存在。
		// String baseInfo1 =
		// "{\"masterDataCode\":\"\",\"sysCustomerId\":\"1756888\",\"name\":\"stt科技有限公司\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"stt南宁市青秀区金湖路61号佳得鑫水晶城D座1703号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		//		String baseInfo1 = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"1756888\",\"name\":\"测试01stt科技有限公司888\",\"industryEb\":\"0021\",\"regionEb\":\"021111\",\"integrationCode\":\"91450100MA5KD9Raaa\",\"website\":\"\",\"address\":\"stt用友产业园888号11111\",\"legalperson\":\"叶宏林11\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		//		// String baseInfo1 =
		//		// "{\"masterDataCode\":\"\",\"sysCustomerId\":\"778899\",\"name\":\"测试01stt科技有限公司8888\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"stt用友产业园888号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		//		String extInfo1 = "{\"createTime\":\"2018-11-06\",\"MDM_CODE\":\"\",\"shortName\":\"stt哈哈\",\"totalEmployee\":\"0601\"}";
		//		String msg1 = CustomerCenter.addCustomer(baseInfo1, extInfo1);
		//		System.out.println(msg1);
		//		JsonNode node1 = Utils.getJson(mapper, msg1);
		//		Assert.assertTrue(node1.get("status").asInt() == 1);
		//		Assert.assertTrue(node1.get("msg").asText().equals("成功"));

		//		// 只填必输字段
		//		String sysCustomerId2 = "02" + t;
		//		String name2 = "测试01stt科技有限公司02" + t;
		//		String baseInfo2 = "{\"sysCustomerId\":\"" + sysCustomerId2 + "\",\"name\":\"" + name2 + "\"}";
		//		String extInfo2 = "";
		//		String msg2 = CustomerCenter.addCustomer(baseInfo2, extInfo2);
		//		System.out.println(msg2);
		//		JsonNode node2 = Utils.getJson(mapper, msg2);
		//		Assert.assertTrue(node2.get("status").asInt() == 0);
		//Assert.assertTrue(node2.get("msg").asText().equals("未找到客户工商信息，已放至审核队列"));

	}

	@Test
	/*
	 * 
	 * addCustomer这个接口新增的，还没有审核的客户，在getByCode接口里使用，是查不到的。 这个里面得是审核通过的
	 */
	public void getByCodeTest() {
		String masterDataCode = "KH02000000036954";
		String sysCustomerId = "20190731102323039";
		String msg = CustomerCenter.getByCode(masterDataCode, sysCustomerId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("成功"));
		Assert.assertTrue(node.get("data").get("baseInfo").get("name").asText().equals("测试dd科技有限公司"));
		Assert.assertTrue(node.get("data").get("baseInfo").get("industryEb").asText().equals("0001"));

	}

	@Test
	/*
	 * 
	 * addCustomer这个接口新增的，还没有审核的客户，在getByCode接口里使用，是查不到的。 这个里面得是审核通过的
	 */
	public void getByNameTest() {
		String name = "测试dd科技有限公司20190731102323039";
		String msg = CustomerCenter.getByName(name);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("成功"));
		Assert.assertTrue(node.get("data").get("baseInfo").get("industryEb").asText().equals("0001"));
		Assert.assertTrue(
				node.get("data").get("baseInfo").get("integrationCode").asText().equals("91450100MA5KD9R87W"));
		Assert.assertTrue(node.get("data").get("baseInfo").get("address").asText().equals("stt用友产业园888号"));
	}
	@Test
	/*
	 * 根据企业id和绑定状态批量获取租户id列表
	 *enterId String 企业ID  不能为空
      state	 String	绑定状态   绑定中 ：0 已绑定 ：1  未绑定：2
     pageNum	int	页码（从第一页开始）--暂不需要 需要扩展
     pageSize	int	页面大小（一页元素个数）--暂不需要 需要扩展
     sortProperty	String	排序属性（排序依据）（冗余字段，暂不需要）
     sortDirection	String	排序方向（升序、降序）（ASC、DESC）（冗余字段，暂不需要）
	 */
	public void getTenantListByEnteridTest() {
		//entid=baaf3961-653b-4758-a4fd-665b5880cb6f&entname=cesi_dd0704   12个企业帐号
		//enterId = "d12d2d24-bb50-4e4b-a155-90524cd44715" entname=cesi0717a 3个企业帐号
		String  enterId = "baaf3961-653b-4758-a4fd-665b5880cb6f";//entname=cesi0717a
		Integer state = 1;
		Integer pageSize = 2;
		Integer pageNum = 3;
		String sortProperty = "";
		String sortDirection = "DESC";	
		String msg = EnterpriseCenter.getTenantListByEnterid(enterId, state, pageSize, pageNum, sortProperty, sortDirection);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		
	}
}
