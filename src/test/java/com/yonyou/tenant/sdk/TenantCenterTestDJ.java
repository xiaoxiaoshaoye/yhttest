package com.yonyou.tenant.sdk;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.tenant.entity.TenantInfo;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
import com.yonyou.iuap.tenant.sdk.TenantGroupCenter;
import com.yonyou.yht.sdk.CustomerCenter;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdk.Utils;
import com.yonyou.yht.sdkutils.sign.SignUtils;

public class TenantCenterTestDJ {

	ObjectMapper mapper = new ObjectMapper();
	String tenantId = "gkm4pkg2";//灰度环境 ，17901888888 yonyou@1988
	String userId = "d9fed71b-38ec-41ba-af73-4a614d908a6b";//17901888888
	@Test
	/*
	 * 新增客户--通过word“11-11上线相关接口测试”接口文档编写的 正常情况的测试
	 * 执行接口，改一下sysCustomerId的值，就会在yht-manager/客户中台里低辨识度审核里有一个待审批的数据
	 * 如果sysCustomerId是已存在的数据，就会把已存在的客户带出来。yht-manager/客户中台里低辨识度审核里没有待审数据
	 * 新增后，可以根据name到库里查询下，select *from pub_yht_enterprise where name =
	 * 'dd科技有限公司';
	 */
	public void addCustomerTest() {

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		String sysCustomerId = t;
		String name = "测试01dd科技有限公司" + t;

		System.out.println("***********************" + t + "***********************");

		// 如果sysCustomerId的值是新的，客户不存在。
		String baseInfo = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"" + sysCustomerId + "\",\"name\":\"" + name
				+ "\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"dd南宁市青秀区金湖路61号佳得鑫水晶城D座1703号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String extInfo = "{\"createTime\":\"2018-11-02\",\"MDM_CODE\":\"\",\"shortName\":\"dd哈哈\",\"totalEmployee\":\"0601\"}";
		String msg = CustomerCenter.addCustomer(baseInfo, extInfo);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("未找到客户工商信息，已放至审核队列"));

		// 如果sysCustomerId的值是已存在的，客户已存在。
		// String baseInfo1 =
		// "{\"masterDataCode\":\"\",\"sysCustomerId\":\"1756888\",\"name\":\"dd科技有限公司\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"dd南宁市青秀区金湖路61号佳得鑫水晶城D座1703号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String baseInfo1 = "{\"masterDataCode\":\"\",\"sysCustomerId\":\"1756888\",\"name\":\"测试01dd科技有限公司888\",\"industryEb\":\"0021\",\"regionEb\":\"021111\",\"integrationCode\":\"91450100MA5KD9Raaa\",\"website\":\"\",\"address\":\"dd用友产业园888号11111\",\"legalperson\":\"叶宏林11\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		// String baseInfo1 =
		// "{\"masterDataCode\":\"\",\"sysCustomerId\":\"778899\",\"name\":\"测试01dd科技有限公司8888\",\"industryEb\":\"0001\",\"regionEb\":\"021101\",\"integrationCode\":\"91450100MA5KD9R87W\",\"website\":\"\",\"address\":\"dd用友产业园888号\",\"legalperson\":\"叶宏林\",\"mainBusiness\":\"\",\"registeredCapital\":\"\"}";
		String extInfo1 = "{\"createTime\":\"2018-11-06\",\"MDM_CODE\":\"\",\"shortName\":\"dd哈哈\",\"totalEmployee\":\"0601\"}";
		String msg1 = CustomerCenter.addCustomer(baseInfo1, extInfo1);
		System.out.println(msg1);
		JsonNode node1 = Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("成功"));

		// 只填必输字段
		String sysCustomerId2 = "02" + t;
		String name2 = "测试01dd科技有限公司02" + t;
		String baseInfo2 = "{\"sysCustomerId\":\"" + sysCustomerId2 + "\",\"name\":\"" + name2 + "\"}";
		String extInfo2 = "";
		String msg2 = CustomerCenter.addCustomer(baseInfo2, extInfo2);
		System.out.println(msg2);
		JsonNode node2 = Utils.getJson(mapper, msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("未找到客户工商信息，已放至审核队列"));

	}

	@Test
	/*
	 * 根据userId获取该用户的应用列表 正常流程测试
	 */
	public void getAppsByUserIdTest() throws JsonProcessingException, IOException {

		//String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";//15210142172
		//String userId = "17bd202b-ae14-49a7-bc63-62b7ee7df978";//17601888888
		//String userId = "21d89360-8a98-4eea-8952-7d9300311274";
		String msg = TenantCenter.getAppsByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
		// Assert.assertTrue(node.get("data").get(0).get("resName").get(0).asText().equals("友空间"));
		// Assert.assertTrue(node.get("data").get(1).get("resName").get(1).asText().equals("U8"));
	}

	@Test
	/*
	 * 根据供应商ID获得对应租户 正常流程测试
	 * supplierId--供应商ID
	 * masterTenantId-租户ID
	 * groupTypeId-群类型：现在只有2：产业链型-资产服务
	 * 返回租户信息
	 */
	public void TenantGroupCenterTest() throws JsonProcessingException, IOException {

		String supplierId = "222222";
		String masterTenantId = "kgqbpz9o";
		Integer groupTypeId = 2;
		String msg = TenantGroupCenter.getTenantBySupplierId(supplierId, masterTenantId, groupTypeId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//		Assert.assertTrue(node.get("state").asInt() == 1);

	}


	@Test
	/*
	 * 参数：userId，sysCode。
     请求方法：GET
      SDK方法：
     public static String getCanLoginTenants(String userId, String systemCode)
    返回类型：JSON，该用户在该应用可登录的租户
      F12 -getAppsByTenant接口   "resCode" : "intelliv",--systemCode 智象
       "resCode" : "diwork", "resCode" : "uspace"友空间,
	 */
	public void getCanLoginTenantsTest() throws JsonProcessingException, IOException {
		//		String userName = "15210142172";
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		//String userId =  "6e1d9cc0-e944-4268-834a-d556dd838151";//15210142172
		//String userId = "8721fa1e-40e4-46d4-9c3c-169e42b1c226";//18803888888
		//String msg = TenantCenter.getCanLoginTenants(userId, "intelliv");
		String msg = TenantCenter.getCanLoginTenants(userId, "uspace");
		//String msg = TenantCenter.getCanLoginTenants(null, "U8");
		//String msg = TenantCenter.getCanLoginTenants(null, null);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 完成了支持云采可查询相应租户下的明文用户信息
	 * 企业帐号-下载 macloud.properties 作为参数传入
	 */
	public void getUsersInfoTest() throws JsonProcessingException, IOException {
		//		
		//		String userId = UserCenterUtil.getUserIdByLoginName(userName);

		String fileName="/Users/juandu/Downloads/macloud.properties";
		InputStream fileIn = new BufferedInputStream(new FileInputStream(fileName));
		String msg = TenantCenter.getUsersInfo(null, fileIn);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 开通应用 正常流程测试 dd08:vik2b18p
	 * "resCode\":\"ipu\"，需要sdk.properties中client.credential.path=ipu.properties
	 * ipu.properties是通过yht-manager里系统--》应用配置--》搜索ipu--》进入详情--》下载密钥
	 * 可以把里面的内容粘到uculture.properties里，因为现在sdk.properties中client.credential.path=
	 * uculture.properties uculture.properties里的username=的值是什么，就能开通什么应用。
	 * username=app-manager时，能开通所有应用
	 */
	public void StringBathOpenAppTest() throws JsonProcessingException, IOException {

		//o2hgbgdt  无ncc租户
		//qjil7uhb   15210142172  NCC移动应用
		//预发环境  - odsmuz2z  YS预发0920a 
		//灰度环境 gkm4pkg2 
		//要开通NCC移动应用的话，先要开通NCC  NCC移动应用：NCCloud_Mobile NCC：NCC
		//service_code = '0561000296',service_pass='758867'  tenantID:fkwr6dlp
		//				String str = "[{\"beginDate\":\"2019-06-27 11:40:32\",\"endDate\":" +
		//						"\"2019-06-28 00:00:00\",\"tenantId\":\"pz868s5s\",\"resCode\":\"NCC\"}]";
		//YS\diwork productLineCode 必填 
		//其他产品不必填
		String str = "[{\"beginDate\":\"2019-11-27 11:40:32\",\"endDate\":" +
				"\"2019-12-20 00:00:00\",\"tenantId\":\"iqsvqxp1\",\"resCode\":\"diwork\",\"productLineCode\":\"diwork\"}]";
		//		String str = "[{\"beginDate\":\"2019-06-27 11:40:32\",\"endDate\":" +
		//				"\"2019-06-28 00:00:00\",\"tenantId\":\"hseo41pi\",\"resCode\":\"NCC\"},"+ 
		//				"{\"beginDate\":\"2019-06-27 11:40:32\",\"endDate\":" + 
		//				"\"2019-06-28 00:00:00\",\"tenantId\":\"hseo41pi\",\"resCode\":\"NCCloud_Mobile\"}] ";
		String result = TenantCenter.StringBathOpenApp(str);
		System.out.println(result);


	}
	@Test
	/**
	 * 分页获取用户列表
	 * @param tenantId
	 *            租户ID
	 * @param ps
	 *            一页显示多少数据，默认为20
	 * @param pn
	 *            第几页，默认为1 
	 *  @param searchcode
	 *            按照用户名、邮箱、手机号搜索该租户下的用户 随便穿
	 * @param sortType
	 *            排序方式，默认按账号升序排列，如果传userName,则按用户名升序
	 * 
	 * */
	public void searchUserLisTest()throws JsonProcessingException,IOException{

		//String tenantId = "o2hgbgdt";//  apitest0403租户   测试是使用线上的租户
		String ps = "1";
		String pn = "2";
		String searchcode = "11";
		String sortType = "userName";

		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.searchUserList(tenantId, ps, pn, null, sortType);
		//String msg = com.yonyou.iuap.tenant.sdk.UserCenter.addUsersAndIdentity(tenantId, resCode, userType, userJson);

		JsonNode node = mapper.readTree(msg);
		System.out.println(node);


	}
	@Test
	/*
	 * 2.24 根据应用编码及租户关键字搜索租户 正常流程测试
	 * 
	 * @param resCode 应用的编码
	 * 
	 * @param ps 一页的条数
	 * 
	 * @param pn 页码
	 * 
	 * @param searchcode 搜索的关键字
	 * 
	 * @param sortType 排序类型 auto name 用户 dd2018092701@test1988.com 密码 yonyou11
	 * 企业帐号dd520aa、dd520bb有u8，dd520cc没有u8
	 */
	public void PageTenantsTest() throws JsonProcessingException, IOException {
		// 一页显示6个数据，第一页。所有两个数据都能查出来
		long start = System.currentTimeMillis();
		System.out.println(start);
		/*运行的程序主体*/

		String resCode = "iuapbp";// "resCode" : "iuapbp",  u8
		String ps = "2";
		String pn = "1";
		String searchcode = "iuap";
		String sortType = "auto";
		String msg = TenantCenter.PageTenants(resCode, ps, pn, searchcode, sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("tenants").get("content").get(0).get("tenantName").asText().equals("dd520bb"));
		Assert.assertTrue(node.get("tenants").get("content").get(1).get("tenantName").asText().equals("dd520aa"));
		long end = System.currentTimeMillis();
		System.out.println("程序运行时间："+(end-start)+"ms");
		// 一页显示1条数据，显示第二页
		//		String resCode1 = "u8";
		//		String ps1 = "1";
		//		String pn1 = "2";
		//		String searchcode1 = "dd520";
		//		String sortType1 = "";
		//		String msg1 = TenantCenter.PageTenants(resCode1, ps1, pn1, searchcode1, sortType1);
		//		System.out.println(msg1);
		//		JsonNode node1 = mapper.readTree(msg1);
		//		Assert.assertTrue(node1.get("status").asInt() == 1);
		//		Assert.assertTrue(node1.get("tenants").get("content").get(0).get("tenantName").asText().equals("dd520aa"));

	}


	@Test
	/**同步NCC用户并添加身份
	 * userJson {"users":[{"userEmail":"","userMobile":"","userName":"","vendorId":"供应商id","customId":"联系人id"}]}
	 * username不能为空
	 * @param tenantId
	 * @param resCode
	 * @param userType 固定值为7
	 * @param userJson  vendorId--供应商id customId -接口传参数，供应商那边对应的联系人id
	 * @re
	 * */
	public void addUsersAndIdentityTest()throws JsonProcessingException,IOException{
		//存在的用户-手机号、邮箱
		//不存在的用户 -手机号、邮箱   19910888888 
		//不存在的用户会自动创建用户，相当于新增用户
		//String tenantId = "esv7yyvi";//长林提供  apitest0403租户   测试是使用线上的租户 hseo41pi 
		//esv7yyvi
		String resCode = "NCC";
		Integer userType = 7;//供应商默认7
		//		Integer userType = null ;
		//		String userJson = "{" + 
		//				"	\"users\": [{" + 
		//				"		\"userEmail\": \"\"," + 
		//				"		\"userMobile\": \"19910888888\"," + 
		//				"		\"userName\": \"\"," + 
		//				"		\"vendorId\": \"33\"," + 
		//				"		\"customId\": \"33\"" + 
		//				"	},{" + 
		//				"	  \"userEmail\": \"\"," + 
		//				"		\"userMobile\": \"19910888888\"," + 
		//				"		\"userName\": \"\"," + 
		//				"		\"vendorId\": \"22\"," + 
		//				"		\"customId\": \"22\"" + 
		//				"	}" + 
		//				"	 ]" + 
		//				"}";
		String userJson = "{" + 
				"	\"users\": [{" + 
				"		\"userEmail\": \"123@yonyou.com\"," + 
				"		\"userMobile\": \"\"," + 
				"		\"userName\": \"\"," + 
				"		\"vendorId\": \"44\"," + 
				"		\"customId\": \"44\"" + 
				"	},{" + 
				"	  \"userEmail\": \"123@yonyou.com\"," + 
				"		\"userMobile\": \"\"," + 
				"		\"userName\": \"\"," + 
				"		\"vendorId\": \"55\"," + 
				"		\"customId\": \"55\"" + 
				"	}" + 
				"	 ]" + 
				"}";
		//		String userJson = null;
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.addUsersAndIdentity(tenantId, resCode, userType, userJson);

		JsonNode node = mapper.readTree(msg);
		System.out.println(node);


	}
	@Test
	/*
	 * 根据租户code获取租户信息 正常流程测试 idtest:用户 16801888888/yonyou@1988
	 * 测试环境："tenantCode" : "allirqvftgwjpkyq",    "tenantName" : "test0919",

	 */
	public void getTenantByTenantCodeTest() throws JsonProcessingException, IOException {
		// "tenantCode" : "venbvqfq77zeqngy",
		String msg = TenantCenter.getTenantByTenantCode("venbvqfq77zeqngy");
		//		String msg = TenantCenter.getTenantByTenantCode("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("test0919"));
		//		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("dujuanh@yonyou.com"));
	}
	//

	@Test
	/*
	 * 根据TenantId批量获取用户id 正常流程测试 
	 * 测试环境：租户id

	 */
	public void getUserIdsByTenantIdTest() throws JsonProcessingException, IOException {

		//String tenantId = "x45i3vx0";
		//String tenantId = null;
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.getUserIdsByTenantId(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("test0919"));
		//		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("dujuanh@yonyou.com"));
	}

	@Test
	/*
	 * 批量根据userId和UserType，查询身份信息，主要需要yxyUserId
	 * @param tenantId 租户ID
	 * @param userType 身份类型
	 * @param userIds 用户id列表
    根据useid和身份类型返回对应的营销云用户id，一个友户用userid对应一个营销云用户id
    例如返回结果
    {
"status" : 1,
"data" : [ {
"yxyUserId" : "1330256914583808",
"yhtUserId" : "0e4a1fc8-2e58-4a08-8432-b8821bd52b0d",
"yhtTenantId" : "w71jqo2a",
"userType" : "1",
"yxyTenantId" : "0"
} ]
}

	 */
	public void getYxyUserIdsTest() throws JsonProcessingException, IOException {//作废

		//String tenantId = "ztbyb5t2";
		//String tenantId = "";
		String userType = "1";//1、 普通员工
		List<String> userIds = new ArrayList<>();
		userIds.add("fc8a5064-8338-4f9c-83a0-2f0fd5588224");//15210142172  线上fc8a5064-8338-4f9c-83a0-2f0fd5588224
		//灰度 0b1f39c8-1f69-47a0-9d62-27ed825093d5
		userIds.add("090e4c9e-f571-4cf2-9adb-fd18f07eb5dc");
		//		userIds.add("6e1d9cc0-e944-4268-834a-d556dd838151");//15210142172
		//		userIds.add("429c47cd-fb10-4069-aedb-e4a51a873fd8");
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.getYxyUserIds(tenantId, userType, userIds);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	//	@Test
	//	/*
	//	 * 查询启用了diwork并激活了NCC应用的租户列表 正常流程测 ps 页面大小 pn 页号 sortType 排序类型（默认不填）升序排列
	//	 */
	//	public void searchActiveNccTenantOnDiworkTest() throws JsonProcessingException, IOException {
	//		String ps = "2";
	//		String pn = "1";
	//		String sortType = null;
	//		String msg = TenantCenter.searchActiveNccTenantOnDiwork(ps, pn, sortType);
	//		System.out.println(msg);
	//		JsonNode node = mapper.readTree(msg);
	//		Assert.assertTrue(node.get("status").asInt() == 1);
	//	}

	@Test
	//注册营销云租户
	//根据友户通租户Id 获得MCId
	/*
	 * userId-给当前用户添加用户身份
	 * 一个租户对应一个MCId,是唯一的
	 * tenantId, userId, resCode -用户没有身份的时候
	 * 一个用户 在不同的租户下 显示不同的身份
	 * 
	 * **/
	public void registerYxyTenantTest() throws JsonProcessingException, IOException {
		String tenantId = "l4b5myru";//租户名称：dd1021a  "MCId" : 1453208137486592 
		//String userId = "1233";//15210142172 测试环境
		//String userId = "";
		//String resCode = "diwork";
		String resCode = "diwork";
		String msg = TenantCenter.registerYxyTenant(tenantId, userId, resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	@Test

	//通过租户id返回 MCID
	public void getUpcTenantByIdTest() throws JsonProcessingException, IOException {
		//String tenantId = "esv7yyvi"; //"tenantId" : 1453208137486592,
		String msg = TenantCenter.getUpcTenantById(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/**
	 * 通过租户id为所有管理员添加身份,身份都是员工
	 * {
  "status" : 1,
  "msg" : "添加关联成功"
}
	 * */
	public void addTenantMgrIdentityTest() throws JsonProcessingException, IOException {
		String tenantId = "vcv0jjbj";
		String resCode = "diwork";
		String msgString = com.yonyou.iuap.tenant.sdk.UserCenter.addTenantMgrIdentity(tenantId, resCode);
		System.out.println(msgString);


	}

	@Test
	/**
	 * 通过租户id为所有管理员添加身份,身份都是员工
	 * {
  "status" : 1,
  "msg" : "添加关联成功"
}
	 * */
	public void assistopenTest() throws JsonProcessingException, IOException {
		//	   String tenantId = "vcv0jjbj";
		//	   String resCode = "diwork";
		//	   String msgString = com.yonyou.iuap.tenant.sdk.UserCenter.
		//	   System.out.println(msgString);


	}


	@Test
	/*
	 * 新增租户信息 
	 */
	public void addTenantTest() throws JsonProcessingException, IOException {

		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());

//		for (int i = 0; i < 30; i++) {
			Map<String,String> map=new HashMap<String,String>();
//			map.put("tenantCode", "qiukb1uzp8ovzbpe061999"+t);
			map.put("tenantId", "test0619"+t);//自动生成
			map.put("tenantName", "@#￥￥&**（（！！！@#￥%……&*（）");//新增租户支持特殊字符：[#%/]
			String msg = TenantCenter.addTenant(map);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
//		}

	}
	@Test
	//	/*
	//	 * 2.27 批量新增租户及管理员信息
	//	 */
	public void batchSaveTenantInfo1Test() throws JsonProcessingException, IOException {
		// 为了租户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		ArrayList<TenantInfo> tenantInfoList=new ArrayList<TenantInfo>();
		//17901888888用户下创建租户	
		for(int i=0;i<2;i++) {
			TenantInfo tenantInfo=new TenantInfo();
			tenantInfo.setUserMobile("17901888888");
			tenantInfo.setTenantCode("ddCode"+i+t);
			tenantInfo.setTenantName("ddName"+i+t);
			tenantInfo.setTenantFullName("ddFullName"+i+t);
			tenantInfoList.add(tenantInfo);
			String msg =  TenantCenter.batchSaveTenantInfo(tenantInfoList.get(i));
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
		}


	}
	@Test
	/*
	 * 2.28 创建租户及管理员并发送开通事件到应用
	 */
	public void batchSaveTenantInfoTest() throws JsonProcessingException, IOException {

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());

		for (int i = 0; i < 1; i++) {
			TenantInfo tenantInfo=new TenantInfo();
			tenantInfo.setUserMobile("17801888888");
			tenantInfo.setTenantCode("testCodeff1127"+i);
			tenantInfo.setTenantName("testNameff1127"+i);
			tenantInfo.setTenantFullName("testFullName1126"+i);

			String msg =  TenantCenter.batchSaveTenantInfo(tenantInfo);
			System.out.println(msg);
			JsonNode node = mapper.readTree(msg);
			Assert.assertTrue(node.get("status").asInt() == 1);
		}
		//15210142172下创建租户


	}
	@Test
	/*
	 * 2.29 -YouSuite产品试用
	 */
	public void openOntrailTest() throws JsonProcessingException, IOException {
		// 为了租户不存在，使用当前日期时间

		String tenandid = "vlo2ixmb";//租户id 试用开通-vlo2ixmb
		String resCode = "diwork";
		String productLineCode = "u8c3.0";//对应 ys   diwork下有两个 ，一个是u8c3.0，一个是diwork
		String endDate = "2020-10-20";
		String managerId = "3882104d-169e-4837-a372-10c39fb54196";
		String operatorId = "3882104d-169e-4837-a372-10c39fb54196"; //用户id
		String msg =  com.yonyou.iuap.tenant.sdk.TenantCenter.openOntrail(tenandid, resCode, productLineCode, endDate, managerId, operatorId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 沙箱应用开通
	 */
	public void openSandboxTest() throws JsonProcessingException, IOException {
  
		String tenantId = "ue1mdjpq";//xrm3dc84  p54qfanq  u8cdaily环境：ue1mdjpq -沙箱应用
		String resCode = "diwork";
		String productLineCode = "u8c3.0";//对应 ys   diwork下有两个 ，一个是u8c3.0，一个是diwork
		String endDate = "2020-12-20";
		String managerId = "3882104d-169e-4837-a372-10c39fb54196";
		String operatorId = "3882104d-169e-4837-a372-10c39fb54196"; 
		String msg = TenantCenter.openSandbox(tenantId, resCode, productLineCode, endDate, managerId, operatorId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	

	@Test
	/*
	 * 2.30 -租户下所有身份类型列表
	 * tenantId  -不能为空
	 * identityProp  身份属性，0:内部身份、1:外部身份、不传所有
	 */
	public void getIdentityListTest() throws JsonProcessingException, IOException {
		// 为了租户不存在，使用当前日期时间


		String tenantId = "bi9o4bsk";//预发环境：wzk0claz  测试环境：pvg7bbra  u8cdaily环境：bi9o4bsk-简版员工1025--测试1
		Integer identityProp = 0;

		String msg =  com.yonyou.iuap.tenant.sdk.UserCenter.getIdentityList(tenantId, identityProp);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//			Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 2.31 -获取租户下用户身份列表
	 * identityProp 身份属性，0:内部身份、1:外部身份、不传所有 非必填
	 * "userType": 1, //身份类型 普通员工
	 * ps 分页参数，每页大小，默认20 非必填
	 * pn 分页参数，页码数，默认1 非必填
	 * 
	 */
	public void getUserIdentityListTest() throws JsonProcessingException, IOException {

		String tenantId = "nvzlcglj";////预发环境：wzk0claz  测试环境：pvg7bbra u8cdaily环境 er51jfmi 线上环境：nvzlcglj
		//u8cdaily环境：bi9o4bsk-简版员工1025--测试1
		Integer identityProp = 0;
		Integer ps = 100;
		Integer pn = 1;
		Integer userType = 1;

		String msg =  com.yonyou.iuap.tenant.sdk.UserCenter.getUserIdentityList(tenantId, ps, pn, identityProp, userType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		
		//			Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 2.32 -搜索租户下用户身份列表
	 * identityProp 身份属性，0:内部身份、1:外部身份、不传所有 非必填
	 * searchCode 模糊搜索字段。用户名、手机号、邮箱 非必填
	 * "userType": 1, //身份类型 普通员工
	 * ps 分页参数，每页大小，默认20 非必填
	 * pn 分页参数，页码数，默认1 非必填
	 * 
	 */
	public void searchUserIdentityListTest() throws JsonProcessingException, IOException {
		

		String tenantId = "orq5s8og";//预发环境：wzk0claz  测试环境：pvg7bbra  u8cdaily环境 er51jfmi
		//orq5s8og u8cdaily防火租户
		Integer identityProp = 0;
		Integer ps = 10;
		Integer pn = 1;
		String searchCode = "";
		Integer userType = 1;

		String msg =  com.yonyou.iuap.tenant.sdk.UserCenter.searchUserIdentityList(tenantId, ps, pn, searchCode, identityProp, userType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//			Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 2.33 -更新产品初始化状态值（初始化中）
	 * String tenantId,String resCode, String productLineCode

	 * 
	 */
	public void updateProductInitStatusToInitializingTest() throws JsonProcessingException, IOException {
		// 为了租户不存在，使用当前日期时间
		//"resCode" : "intelliv",--systemCode 智象
		//"resCode" : "diwork", "resCode" : "uspace"友空间,
		String tenantId = "pickz69v";//预发环境：wzk0claz  测试环境：pvg7bbra
		 // u8cdaily环境  沙箱应用-ue1mdjpq  试用开通-vlo2ixmb
		//u8cdaily环境 -YSDaily激活状态修改aa-pickz69v
		String resCode = "diwork"; 
		String productLineCode = "u8c3.0";// YS:"productLineCode" : "u8c3.0", "resCode" : "diwork",
		String msg =  TenantCenter.updateProductInitStatusToInitializing(tenantId, resCode, productLineCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		//			Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 2.34 -修改产品到期时间
	 * 租户管理员能收到邮件

	 * 
	 */
	public void modifyAppExpiredTimegTest() throws JsonProcessingException, IOException {
     //线上环境NC产品服务  "resCode" : "cloudportal",  "tenantId" : "yxhu6hib",
		String tenantId="yxhu6hib";    //  u8cdaily环境 ：p54qfanq diwork0408    YSDaily1224001-rz64ymnt  
		String resCode ="cloudportal"; // "resCode" : "diwork"   u8cdaily环境 er51jfmi   
		String productLineCode =null;  
		String endDate="2020-11-27 11:22:33"; //时间改大
//		String endDate="2019-10-27 11:22:33"; //时间改小
		String msg2 = TenantCenter.modifyAppExpiredTime(endDate,tenantId,resCode,productLineCode); 
		System.out.println(msg2); JsonNode node2 = mapper.readTree(msg2); Assert.assertTrue(node2.get("status").asInt() == 1); 
		Assert.assertTrue(node2.get("msg").asText().equals("success"));

	}

	//租户和用户首选项设置

	@Test
	/*
	 * 2.35 -获取上次首选项设置

	 * 
	 */
	public void getInitializeConfigTest() throws JsonProcessingException, IOException {

		String tenantId="bi9o4bsk";    
		String userId ="722b78ba-53fc-4200-bfdf-682f97c31e3a"; //15210142172
		String msg =  TenantCenter.getGlobalizationConfig(tenantId, userId);
		System.out.println(msg); 
		JsonNode node2 = mapper.readTree(msg);
		Assert.assertTrue(node2.get("status").asInt() == 1); 
		Assert.assertTrue(node2.get("msg").asText().equals("success"));

	}
	@Test
	/**
	 * 
	 * @param tenantId 租户id
	 * @param userIds 用户id列表
	 * @return
	 * userCenterNotExitUser 标识用户不存在 
     notExitRelation  标识不在租户下的用户
     exitRelation   标识在租户下的用户
	 */
	public void batchUserExistInTenantByIdsTest() throws JsonProcessingException, IOException {

//		String tenantId = "";    
		String tenantId="bi9o4bsk";  //bi9o4bsk   wzk0claz
		List<String> userIds = new ArrayList<>();
		userIds.add("722b78ba-53fc-4200-bfdf-682f97c31e3a");//15210142172 在租户下的用户	
		userIds.add("7c31e3a");//不存在的用户
		userIds.add("ede2d0b1-0488-465b-958b-e19d20529d7e");//不在租户下的用户
		String msg =  com.yonyou.iuap.tenant.sdk.UserCenter.batchUserExistInTenantByIds(tenantId, userIds);
		System.out.println(msg); 
		JsonNode node2 = mapper.readTree(msg);
		Assert.assertTrue(node2.get("status").asInt() == 1); 
		Assert.assertTrue(node2.get("msg").asText().equals("success"));
	}
	@Test
	/*
	 * 2.31 云应用停用接口 正常流程测试 用户 18810039018 密码 yonyou11 企业帐号 stt05 的tenantId =
	 * "bdv029uh" 友零售 的应用编码是retail diwork 的应用编码是diwork
	 * 执行这个接口，StringBathOpenApp，让应用是开通的,然后执行云应用停用接口，能看到应用状态是停用。
	 * 再执行云应用启用接口，应用状态是试用中
	 */
	public void disableAppTest() throws JsonProcessingException, IOException {

		// 停用接口
		String tenantId = "p54qfanq";
		String resCode = "diwork";
		String msg = TenantCenter.disableApp(tenantId, resCode);// "resCode" : "diwork",  友空间： "resCode" : "uspace",
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 2.30 云应用启用接口 正常流程测试 共享服务绩效看板 的应用编码是ssc_performance 云应用停用接口 再执行云应用启用接口，应用状态是试用中
	 */
	public void enableAppTest() throws JsonProcessingException, IOException {
  
		String tenantId = "xrm3dc84";//xrm3dc84  p54qfanq
		String resCode = "diwork";
		String msg = TenantCenter.enableApp(tenantId, resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	@Test
	/*
	 * 3.6 添加用户,类似于管理员添加用户,如果有的用户存在的话不返回错误，直接添加关联关系
	 */
	public void addUserAndRelationTest() throws JsonProcessingException, IOException {


		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());

		// 本接口测试
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", "User003");
		params.put("userName", "用户003");
		params.put("userEmail",  "test0408ee@test1988.com");
		params.put("tenantId", "bi9o4bsk");//
		String msg1 =  com.yonyou.iuap.tenant.sdk.UserCenter.addUserAndRelation(params);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("保存成功"));
	
	}
	@Test
	/*
	 * 3.5 添加用户,类似于管理员添加用户(所有添加用户密码参照1.1.5的说明)
	 * 	 正常流程测试
	 */
	public void addUserTest() throws JsonProcessingException, IOException {
		
		//生成默认密码
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode = date.format(new Date());
		params.put("tenantId", "bi9o4bsk");
		params.put("userCode", "test0409bb@163.com");
		params.put("userName",  "test0409bb@163.com");
		params.put("userEmail",  "test0409bb@163.com");
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		
	}
	@Test
	  public void addUserTest1(){
			//生成默认密码
			Map<String, String> params = new HashMap<String, String>();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String userCode = date.format(new Date());
			params.put("tenantId", "bi9o4bsk");
			params.put("userCode", "byscode11");
			params.put("userName",  "bystest02");
			params.put("userEmail",  "bystest02@163.com");
			String msg = UserCenter.addUser(params);
			System.out.println(msg);	
		}
	//u8,u8c，ncc重发许可
		@Test
		public void ResetCodeTest() throws JsonProcessingException,IOException{
			//https://uastest.yyuap.com/apptenant/rest/u8c/timesReset?key=s0268b6d481ced448c78956bd53ffadb619&tenantId=null
			//u8c  私有云：s0268b6d481ced448c78956bd53ffadb619
			//ncc 私有云：s1008e24dba8cf24fc8bc0bdff7d6c9a03a
			//tenantId 可以为空，非必填，不校验
			//u8c和ncc的重发许可
//	      System.out.println(SignUtils.signAndGet("http://yht-apptenant-gray.stage.app.yyuap.com/apptenant/rest/u8c/timesReset?key=s1008e24dba8cf24fc8bc0bdff7d6c9a03a&tenantId=null"));
			//https://uastest.yyuap.com/apptenant/rest/renewLicense
//			//U8重发许可
			Map<String, String> map = new HashMap<>();
			  map.put("shapeCode", "PN000001");
			  //active_code = 's014fa1a961f40d485fb8367b84162afd62' ; U8： 在线买断
			  //#PN000001 
			  
			  
			  //active_code = 's161d1e00272bf84323b560a7f54b73b890';U8+V16总并发(在线借用)
			  //#PM000001
			  //active_code = 's014fa1a961f40d485fb8367b84162afd62'; U8+V16领域并发(离线买断)
			  //#PA000021
			  
			  System.out.println(SignUtils.signAndPost("http://yht-apptenant-gray.stage.app.yyuap.com/apptenant/rest/renewLicense",map));
		}
		
	
}
