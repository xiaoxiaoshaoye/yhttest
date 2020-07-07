package com.yonyou.tenant.sdk;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
import com.yonyou.iuap.tenant.sdk.TenantGroupCenter;
import com.yonyou.yht.sdk.CustomerCenter;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdk.Utils;

public class TenantCenterTestDjLine {

	ObjectMapper mapper = new ObjectMapper();
	/**
	 根据用户id和系统code获取可以登录租户(用户身份版)
    sdk 方法：
        public static String getCanLoginTenantsByUserIdentity(String userId, String systemCode)
     两个参数都不能为空
     userType:1 普通员工
	 * 
	 * */
	@Test
	public void getCanLoginTenantsByUserIdentityTest()throws JsonProcessingException,IOException{

		String userId = "fc8a5064-8338-4f9c-83a0-2f0fd5588224";//  15210142172  
		//String userId = "";
		String systemCode = "diwork";
		//String systemCode = "intelliv";
		//String msg = com.yonyou.iuap.tenant.sdk.UserCenter.searchUserList(tenantId, ps, pn, null, sortType);
		String msg = TenantCenter.getCanLoginTenantsByUserIdentity(userId, systemCode);
		
		//String result = TenantCenter.getCanLoginTenantsByUserIdentity(userId, systemCode, productLineCode);
		JsonNode node = mapper.readTree(msg);
		System.out.println(node);

	}
	@Test
	/*
	 * 开通应用 正常流程测试 stt08:vik2b18p
	 * "resCode\":\"ipu\"，需要sdk.properties中client.credential.path=ipu.properties
	 * ipu.properties是通过yht-manager里系统--》应用配置--》搜索ipu--》进入详情--》下载密钥
	 * 可以把里面的内容粘到uculture.properties里，因为现在sdk.properties中client.credential.path=
	 * uculture.properties uculture.properties里的username=的值是什么，就能开通什么应用。
	 * username=app-manager时，能开通所有应用
	 * 开始时间可以为空
	 * 
	 *  只有手机号、只有邮箱、有手机号和邮箱
	 */
	public void StringBathOpenAppTest() throws JsonProcessingException, IOException {
		//o2hgbgdt  apitest0403   "resCode" : "diwork", "uspace"
		//要开通NCC移动应用的话，先要开通NCC  NCC移动应用：NCCloud_Mobile NCC：NCC
		//service_code = '0561000296',service_pass='758867'  tenantID:doudioay 无ncc的租户
		// NCC私有0924 kj5ewbc9  有ncc的租户只要是ncc就行不分公有和私有
		String str = "[{\"beginDate\":\"2019-06-27 11:40:32\",\"endDate\":" +
			"\"2019-09-28 00:00:00\",\"tenantId\":\"doudioay\",\"resCode\":\"NCCloud_Mobile\"}] ";
//		String str = "[{\"beginDate\":\"\",\"endDate\":" +
//				"\"\",\"tenantId\":\"doudioay\",\"resCode\":\"uspace\"}] ";
		String result = TenantCenter.StringBathOpenApp(str);

		System.out.println(result);


	}

	@Test
	/**同步NCC用户并添加身份
	 * userJson {"users":[{"userEmail":"","userMobile":"","userName":"","vendorId":"供应商id","customId":"联系人id"}]}
	 * @param tenantId
	 * @param resCode
	 * @param userType 固定值为7
	 * @param userJson
	 * @re
	 * */
	public void addUsersAndIdentityTest()throws JsonProcessingException,IOException{

		String tenantId = "doudioay";//  apitest0403租户   测试是使用线上的租户
		String resCode = "NCC";
		Integer userType = 7;//供应商默认7
		//Integer userType = null ;
		String userJson = "{\"users\":[{\"userEmail\":\"\",\"userMobile\":\"17988888888\",\"userName\":\"\",\"vendorId\":\"22\",\"customId\":\"33\"}]}";
		//String userJson = null;
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.addUsersAndIdentity(tenantId, resCode, userType, userJson);
		
		JsonNode node = mapper.readTree(msg);
		System.out.println(node);


	}

	@Test
	/**同步NCC用户并添加身份
	 * userJson {"users":[{"userEmail":"","userMobile":"","userName":"","vendorId":"供应商id","customId":"联系人id"}]}
	 * @param tenantId
	 * @param resCode
	 * @param userType 固定值为7
	 * @param userJson
	 * @re
	 * */
	public void addUserIdentityToTenantTest()throws JsonProcessingException,IOException{

		String tenantId = "qyic8c7o"; //qyic8c7o  用友集团
		String resCode = "diwork";
		Integer userType = 1;//供应商默认7  普通用户 3 管理员 1
		//Integer userType = null ;
		String userJson = "{\"users\":[{\"userEmail\":\"\",\"userMobile\":\"17988888888\",\"userName\":\"\",\"vendorId\":\"22\",\"customId\":\"33\"}]}";
		//String userJson = null;
		String [] pks = {"fc8a5064-8338-4f9c-83a0-2f0fd5588224"};
		String token = "";
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.addUserIdentityToTenant(tenantId, resCode, userType, pks, null, null);
		
		
		JsonNode node = mapper.readTree(msg);
		System.out.println(node);


	}
	
	@Test
	/*
	 * 根据TenantId批量获取用户id 正常流程测试 
	 * 测试环境：租户id

	 */
	public void getUserIdsByTenantIdTest() throws JsonProcessingException, IOException {

		String tenantId = "doudioay";
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
	 * 根据租户code获取租户信息 正常流程测试 idtest:用户 16801888888/yonyou@1988
	 * 测试环境："tenantCode" : "allirqvftgwjpkyq",    "tenantName" : "test0919",

	 */
	public void getTenantByTenantCodeTest() throws JsonProcessingException, IOException {

		String msg = TenantCenter.getTenantByTenantCode("allirqvftgwjpkyq");
		//		String msg = TenantCenter.getTenantByTenantCode("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
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
	public void getYxyUserIdsTest() throws JsonProcessingException, IOException {

		String tenantId = "qyic8c7o"; //qyic8c7o  用友集团
		//String tenantId = "";
		String userType = "1";//1、 普通员工
		List<String> userIds = new ArrayList<>();
		userIds.add("fc8a5064-8338-4f9c-83a0-2f0fd5588224");//15210142172
		userIds.add("105f871e-635a-432c-a4b4-6850bbc19c18");//13488759070
		//		userIds.add("6e1d9cc0-e944-4268-834a-d556dd838151");//15210142172
		//		userIds.add("429c47cd-fb10-4069-aedb-e4a51a873fd8");
		String msg = com.yonyou.iuap.tenant.sdk.UserCenter.getYxyUserIds(tenantId, userType, userIds);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	@Test
	/*
	 * 根据userId，resCode批量获取租户已经激活的应用 正常流程测试 
	 * 测试环境：租户id

	 */
	public void getCanLoginTenantsTest() throws JsonProcessingException, IOException {
     // "resCode" : "u8", "NCC", “uspace ”-友空间，“diwork” ，“u8c”  aed0008c-7965-4500-9b4e-9b89ba709645 16701888888
		String resCode = "uspace";
//		String resCode = null;
		String userId = "aed0008c-7965-4500-9b4e-9b89ba709645";
		//String userId = "";
		String msg = TenantCenter.getCanLoginTenants(userId, resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		//		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("test0919"));
		//		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("dujuanh@yonyou.com"));
	}
//	
//	@Test
//	/*
//	 * 查询启用了diwork并激活了NCC应用的租户列表 正常流程测 ps 页面大小 pn 页号 sortType 排序类型（默认不填）升序排列
//	 */
//	public void searchActiveNccTenantOnDiworkTest() throws JsonProcessingException, IOException {
//		String ps = "2";
//		String pn = "1";
//		String sortType = "";
//		String msg = TenantCenter.searchActiveNccTenantOnDiwork(ps, pn, sortType);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//	}

}
