package com.yonyou.tenant.sdk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.tenant.entity.TenantInfo;
import com.yonyou.iuap.tenant.entity.UserSource;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
import com.yonyou.iuap.tenant.sdk.UserCenter;
//import com.yonyou.utils.UserCenterUtil;
import com.yonyou.yht.sdk.SDKUtils;
import com.yonyou.yht.sdkutils.sign.SignUtils;

public class TenantCenterTestAll {
	ObjectMapper mapper;

	// idtest************************************
	// 用户 18611286701 密码 yonyou11
	// 企业帐号"接口测试使用" 的tenantId = "u5tuj80u"
	// 企业帐号"接口测试使用1"的tenantId = "n45h6puh"
	// 企业帐号"接口测试使用3"的tenantId = "yvqsuxwt"
	// 企业帐号"接口测试使用4"的tenantId = "qdkh7g2j"
	
	// 用户 18810039018 密码 yonyou11
	// 企业帐号u8c私有云的tenantId = "p59szm28"
	// 企业帐号NCC私有云的tenantId = "clex04x4"
	// 有绑定NCC集团的tenantId_bdjt = "th9f5w3u"
	// 用户 stt2018092701@test1988.com 密码 yonyou11
	// 企业帐号aa的tenantId ="gqgk70jg"
	// 企业帐号接口测试U8C3.0的tenantId ="mhp32f13"
	//企业帐号接口测试U8C3.0new1的tenantId ="m4w1lju3"
	//path 是3.29  通过企业密钥查询租户下用户明文信息需要的企业秘钥下载后文件存放路径 是18611286701   里接口测试使用3的
	
	String tenantId = "u5tuj80u";
	String tenantId1 = "n45h6puh";
	String tenantId2 = "fizhxizr";
	String tenantId3 = "yvqsuxwt";
	String tenantId4 = "qdkh7g2j";
	String tenantId_u8c = "p59szm28";
	String tenantId_aa = "gqgk70jg";
	String tenantId_stt08 = "vik2b18p";
	String tenantId_u8c_new = "mhp32f13";
	String tenantId_u8c_new1 = "m4w1lju3";
	
	String tenantId_NCC="clex04x4";
	String tenantId_bdjt="th9f5w3u";
	String url = "https://uastest.yyuap.com/apptenant/file/fdfsimg/down?id=group1/M00/00/9E/rBQSEFtX3ASAWB3KAALv9miQkCw4354192";
	String url1 = "http://uastest.yyuap.com/apptenant/rest/tenant/page?searchParam=stt&ps=10&pn=1";
	String path="C:\\macloud-idtest.properties";
//	 euc*****************************************
//	 用户 18810039018 密码 yonyou11
//	 企业帐号"接口测试使用" 的tenantId = "wlaml5ue"
//	 企业帐号"接口测试使用1"的tenantId = "lmlt2260"
//	企业帐号"接口测试使用2"的tenantId = "fizhxizr"
//   企业帐号"接口测试使用3"的tenantId = "yxv6cpsa"
//   企业帐号"接口测试使用4"的tenantId = "ntqk5cu4"
//	 企业帐号u8c私有云的tenantId = "mz1anu3t"
//	 用户 stt2018092701@test1988.com 密码 yonyou11
//	 企业帐号aa的tenantId ="l5v7r7ts"
//   企业帐号NCC私有云的tenantId = "r29p5oeg"
//   有绑定NCC集团的tenantId_bdjt = "v5jb3kcp"
	//path 是3.29  通过企业密钥查询租户下用户明文信息需要的企业秘钥下载后文件存放路径 是18611286701   里接口测试使用3的
//	 String tenantId = "wlaml5ue";
//	 String tenantId1 = "lmlt2260";
//	 String tenantId2 = "t2ctb6r9";
//	 String tenantId3 = "yxv6cpsa";
//	 String tenantId4 = "ntqk5cu4";
//	 String tenantId_u8c = "mz1anu3t";
//	 String tenantId_aa = "h9cpaznu";
//	 String tenantId_stt08="dzlg25tq";
//	 String tenantId_NCC="r29p5oeg";
//	 String tenantId_bdjt="v5jb3kcp";
//	 String url="https://apcenter.yonyoucloud.com/apptenant/file/fdfsimg/down?id=g1/M00/02/A9/CgMHjFvkTouAd_sgAAEIDMXwN1Y2227209";
//	 String url1 = "http://apcenter.yonyoucloud.com/apptenant/rest/tenant/page?searchParam=stt&ps=10&pn=1";
//	String path="C:\\macloud-euc.properties";

	@Before
	public void init() {
		mapper = new ObjectMapper();

	}

	@Test
	/*
	 * 根据租户id获取租户信息 正常流程测试 用户 18611286701 密码 yonyou11 企业帐号"接口测试使用"的tenantId =
	 * "u5tuj80u"
	 */
	public void getTenantByIdTest() throws JsonProcessingException, IOException {

		String msg = TenantCenter.getTenantById(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("接口测试使用"));
		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("suntt@yonyou.com"));
	}

	@Test
	/*
	 * 根据租户code获取租户信息 正常流程测试 idtest:用户 18611286701 密码 yonyou11
	 * 企业帐号"接口测试使用1"的code = "stt123456" euc:用户 18810039018 密码 yonyou11
	 * 企业帐号"接口测试使用1"的code = "stt123456"
	 */
	public void getTenantByTenantCodeTest() throws JsonProcessingException, IOException {

		String msg = TenantCenter.getTenantByTenantCode("stt123456");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("接口测试使用1"));
		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("suntt@yonyou.com"));
	}

	@Test
	/*
	 * 根据租户code获取租户状态 正常流程测试 idtest:用户 18611286701 密码 yonyou11 euc:用户
	 * 18611286701 密码 yonyou11 企业帐号"接口测试使用1"的code = "stt123456"
	 * 企业帐号"接口测试使用2"的code = "stt000000"
	 */
	public void getTenantStatusTest() throws JsonProcessingException, IOException {
		String tenantCodes[] = { "stt123456", "stt000000" };
		String msg = TenantCenter.getTenantStatus(tenantCodes);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("statusMap").get("stt123456").asInt() == 0);
		Assert.assertTrue(node.get("statusMap").get("stt000000").asInt() == 0);

	}

	@Test
	/*
	 * 根据租户id获取企业LOGO 正常流程测试 用户 18611286701 密码 yonyou11 企业帐号"接口测试使用"的tenantId =
	 * "u5tuj80u" 用户 18611286701 密码 yonyou11 企业帐号"接口测试使用1"的tenantId = "n45h6puh"
	 */
	public void getLogoByTenantIdTest() throws JsonProcessingException, IOException {

		// 没有头像的企业帐号，获取的默认头像
		String msg = TenantCenter.getLogoByTenantId(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("url").asText().equals("http://cdn.yonyoucloud.com/dev/apcenter/img/logo/LOGO.png"));

		// 用户上传了头像
		String msg1 = TenantCenter.getLogoByTenantId(tenantId1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("url").asText().equals(url));

	}

	@Test
	/*
	 * 根据用户id和系统code获取租户 正常流程测试
	 * 例如：新建了个用户，然后新建了两个租户1，租户2，租户1下开通了友空间uspace，租户2没有开通，
	 * 调用这个接口getCanUseTenants(userId,"uspace");，会返回租户1
	 */
	public void getCanUseTenantsTest() throws JsonProcessingException, IOException {
		String userName = "jlccstt@126.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getCanUseTenants(userId, "u8");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenants").get(0).get("tenantName").asText().equals("有U8"));
		Assert.assertTrue(node.get("tenants").get(1).get("tenantName").asText().equals("有U8-1"));
	}

	@Test
	/*
	 * 根据用户id和系统code获取可以登录租户 正常流程测试 例如：jlccstt@126.com，新建了两个租户1，租户2
	 * 两个租户都有u8，两个租户都添加用户 17779888888，这个手机号对应的邮箱是jlccstt@126.com
	 * 但租户1激活时，给jlccstt@126.com分配了许可。租户2没有激活。 这样用户id使用jlccstt@126.com，就能查出来租户1.
	 * 这个
	 */
	public void getCanLoginTenantsTest() throws JsonProcessingException, IOException {
		// String userName = "18811346659";
		String userName = "jlccstt@126.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getCanLoginTenants(userId, "u8");
		// String
		// msg=TenantCenter.getCanLoginTenants(userId,"ublinker_gateway");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据用户id查询所有的租户 正常流程测试
	 */
	public void findTenantsByUserIdTest() throws JsonProcessingException, IOException {
		String userName = "jlccstt@126.com";
		
//		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userId = "6fbe1125-2c7d-4808-bc41-a3575ad4f727";
		String msg = TenantCenter.findTenantsByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据用户id查询所管理的租户 正常流程测试
	 */
	public void getUserManageTenantsTest() throws JsonProcessingException, IOException {
		String userName = "jlccstt@126.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getUserManageTenants(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据resCode查询所有的租户 正常流程测试
	 */
	public void getTenantIdsByResCodeTest() throws JsonProcessingException, IOException {
		String resCode = "u8";
		String msg = TenantCenter.getTenantIdsByResCode(resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据tenantId查询所有的应用的code 正常流程测试
	 */
	public void getResCodesByTenantIdTest() throws JsonProcessingException, IOException {

		String msg = TenantCenter.getResCodesByTenantId(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("resCodes").get(0).asText().equals("ssc_performance"));
		Assert.assertTrue(node.get("resCodes").get(1).asText().equals("NCC"));
	}

	@Test
	/*
	 * 根据userId获取该用户的应用列表 正常流程测试
	 */
	public void getAppsByUserIdTest() throws JsonProcessingException, IOException {

		String userName = "stt851026@126.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getAppsByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
		// Assert.assertTrue(node.get("data").get(0).get("resName").get(0).asText().equals("友空间"));
		// Assert.assertTrue(node.get("data").get(1).get("resName").get(1).asText().equals("U8"));
	}

//	@Test
//	/*
//	 * 根据租户id和应用code查询该租户的应用状态 
//	 * 正常流程测试
//	 * 这个接口已经没有人用了，现在返回的值不正确。不该。以后接口不用测了
//	 */
//	public void queryResStateTest() throws JsonProcessingException, IOException {
//
//		String resCode = "NCC";
//		String msg = TenantCenter.queryResState(tenantId_NCC, resCode);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//
//	}

	@Test
	/*
	 * 查询启用了diwork并激活了NCC应用的租户列表 正常流程测 ps 页面大小 pn 页号 sortType 排序类型（默认不填）升序排列
	 */
	public void searchActiveNccTenantOnDiworkTest() throws JsonProcessingException, IOException {
		String ps = "2";
		String pn = "1";
		String sortType = null;
		String msg = TenantCenter.searchActiveNccTenantOnDiwork(ps, pn, sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据租户ID查询U8c激活地址和变更剩余次数信息 正常流程测试 用户 18810039018 密码 yonyou11
	 * 企业帐号u8c私有云的tenantId = "p59szm28"
	 */
	public void getU8cActiveUrlTest() throws JsonProcessingException, IOException {

		String msg = TenantCenter.getU8cActiveUrl(tenantId_u8c);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 判断某租户是否购买了NCC并已激活成功 正常流程测试 用户 18810039018 密码 yonyou11 企业帐号 NCC 的tenantId
	 * = "j4iy9opc"
	 */
	public void checkNCCActiveStatusTest() throws JsonProcessingException, IOException {
		String tenantId = "j4iy9opc";
		String msg = TenantCenter.checkNCCActiveStatus(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 根据租户ID查询NCC集团主键 正常流程测试 用户 18810039018 密码 yonyou11 企业帐号 NCC 的tenantId =
	 * "j4iy9opc"
	 */
	public void getGroupPkByEnterAccIdTest() throws JsonProcessingException, IOException {

		// 主企业账号未绑定集团
		String msg = TenantCenter.getGroupPkByEnterAccId(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("主企业账号未绑定集团"));

		// 绑定集团
		String msg1 = TenantCenter.getGroupPkByEnterAccId(tenantId_bdjt);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
//		Assert.assertTrue(node1.get("groupPk").asText().equals("0001N91000000000013R"));

	}

	@Test
	/*
	 * 根据企业代码更新租户信息 正常流程测试
	 */
	public void updateTenantByCodeTest() throws JsonProcessingException, IOException {

		// 用户 stt2018092701@test1988.com 密码 yonyou11
		// 企业帐号bb
		Map<String, String> params = new HashMap<String, String>();
		params.put("tenantName", "bbNew");
		params.put("tenantCode", "stt2018092701bb");
		params.put("tenantAddress", "tenantAddress哈哈");
		params.put("tenantTel", "18800000000");
		params.put("tenantEmail", "suntt@yonyou.com");
		params.put("tenantFullname", "一家人公司");
		params.put("tenantArea", "北京市/市辖区/海淀区");
		params.put("tenantOfficalWeb", "https://www.baidu.com");
		params.put("tenantIndustry", "科技");
		String msg = TenantCenter.updateTenantByCode(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("保存成功"));

		// 还原数据
		params.put("tenantName", "bb");
		params.put("tenantCode", "stt2018092701bb");
		params.put("tenantAddress", "什么地址丫丫");
		params.put("tenantTel", "18811112222");
		params.put("tenantEmail", "suntt@yonyou.com");
		params.put("tenantFullname", "幸福大家庭公司");
		params.put("tenantArea", "北京市/市辖区/昌平区");
		params.put("tenantOfficalWeb", "https://www.jd.com");
		params.put("tenantIndustry", "采矿业");
		String msg1 = TenantCenter.updateTenantByCode(params);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("保存成功"));
	}

	@Test
	/*
	 * 根据租户主键查询当前租户下所有的用户的主键 正常流程测试 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号cc的编码是stt2018092701cc 下面有两个用户 stt2018092701@test1988.com
	 * stt2018092702@test1988.com
	 */
	public void getUserIdsByTenantIdTest() throws JsonProcessingException, IOException {

		// 根据编码获取租户id
		String msg = TenantCenter.getTenantByTenantCode("stt2018092701cc");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String tenantId = node.get("tenant").get("tenantId").asText();

		// 本测试方法
		String msg1 = UserCenter.getUserIdsByTenantId(tenantId);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 获取用户id，用户校验本测试方法的结果
		String userName = "stt2018092701@test1988.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userName1 = "stt2018092702@test1988.com";
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		Assert.assertTrue(node1.get("userIds").get(0).asText().equals(userId));
		Assert.assertTrue(node1.get("userIds").get(1).asText().equals(userId1));

	}

	@Test
	/*
	 * 2.23 根据类型查询应用 正常流程测试 001 云应用 008 nc、ncc、u8c
	 */
	public void queryAppsTest() throws JsonProcessingException, IOException {
		String msg = TenantCenter.queryApps("008");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
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
	 * @param sortType 排序类型 auto name 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号stt520aa、stt520bb有u8，stt520cc没有u8
	 */
	public void PageTenantsTest() throws JsonProcessingException, IOException {
		// 一页显示6个数据，第一页。所有两个数据都能查出来
		String resCode = "u8";
		String ps = "6";
		String pn = "1";
		String searchcode = "stt520";
		String sortType = "";
		String msg = TenantCenter.PageTenants(resCode, ps, pn, searchcode, sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenants").get("content").get(0).get("tenantName").asText().equals("stt520bb"));
		Assert.assertTrue(node.get("tenants").get("content").get(1).get("tenantName").asText().equals("stt520aa"));

		// 一页显示1条数据，显示第二页
		String resCode1 = "u8";
		String ps1 = "1";
		String pn1 = "2";
		String searchcode1 = "stt520";
		String sortType1 = "";
		String msg1 = TenantCenter.PageTenants(resCode1, ps1, pn1, searchcode1, sortType1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("tenants").get("content").get(0).get("tenantName").asText().equals("stt520aa"));

	}

	@Test
	/*
	 * 2.25 新增管理员信息 正常测试 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号dd的tenantCode是stt2018092701dd
	 */
	public void addAdminTest() throws JsonProcessingException, IOException {

		// 根据编码获取租户id
		String msg = TenantCenter.getTenantByTenantCode("stt2018092701dd");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String tenantId = node.get("tenant").get("tenantId").asText();
		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		// 本接口测试
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", t + "sttAdmin001");
		params.put("userName", t + "stt管理员001");
		params.put("userEmail", t + "@test1988.com");
		params.put("tenantId", tenantId);
		String msg1 = TenantCenter.addAdmin(params);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("注册成功"));
	}

	
	@Test
	/*
	 * 2.26 新增管理员信息、如果已经存在那么直接关联
	 * 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号dd的tenantCode是stt2018092701dd
	 */
	public void addAdminAndRelationTest() throws JsonProcessingException, IOException {

		// 根据编码获取租户id
		String msg = TenantCenter.getTenantByTenantCode("stt2018092701dd");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String tenantId = node.get("tenant").get("tenantId").asText();
		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		// 本接口测试
		//全新用户
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", t + "sttAdmin001");
		params.put("userName", t + "stt管理员001");
		params.put("userEmail", t + "@test1988.com");
		params.put("tenantId", tenantId);
		String msg1 = TenantCenter.addAdminAndRelation(params);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("注册成功"));
		
		
		//已存在用户
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("userCode", t + "sttAdmin001");
		params2.put("userName", t + "stt管理员001");
		params2.put("userEmail", t + "@test1988.com");
		params2.put("tenantId", tenantId_u8c);
		String msg2 = TenantCenter.addAdminAndRelation(params2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("msg").asText().equals("注册成功"));
	}
	


	
//	@Test
//	/*
//	 * 2.27 批量新增租户及管理员信息
//	 */
//	public void batchSaveTenantInfo1Test() throws JsonProcessingException, IOException {
//		// 为了租户不存在，使用当前日期时间
//		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String t = date.format(new Date());
//		List<TenantInfo> tenantInfoList=new ArrayList<TenantInfo>();
//		//14417888888下创建租户	
//		for(int i=0;i<2;i++) {
//			TenantInfo tenantInfo=new TenantInfo();
//			tenantInfo.setUserMobile("14417888888");
//			tenantInfo.setTenantCode("sttCode"+i+t);
//			tenantInfo.setTenantName("sttName"+i+t);
//			tenantInfo.setTenantFullName("sttFullName"+i+t);
//			tenantInfoList.add(tenantInfo);
//		}
//		String msg =  TenantCenter.batchSaveTenantInfo(tenantInfoList);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//	}
//	
	
	@Test
	/*
	 * 2.28 创建租户及管理员并发送开通事件到应用
	 */
	public void batchSaveTenantInfoTest() throws JsonProcessingException, IOException {
		// 为了租户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		
		//14417888888下创建租户
		TenantInfo tenantInfo=new TenantInfo();
		tenantInfo.setUserMobile("14417888888");
		tenantInfo.setTenantCode("sttCode"+t);
		tenantInfo.setTenantName("sttName"+t);
		tenantInfo.setTenantFullName("sttFullName"+t);
		
		String msg =  TenantCenter.batchSaveTenantInfo(tenantInfo);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	
	}
	
	
	@Test
	/*
	 * 2.29 试用开通应用
	 * @param tenantId   企业账号id
	 * @param resCode   应用的编码
	 * @param status    "ontrail"试用
	 * @param endDate   格式:yyyy-MM-dd HH:mm:ss   
	 */
	public void addAndActiveTest() {
		// "pm_cloud", 友工程
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
		String t = date.format(new Date());
//		String addAndActive = TenantCenter.addAndActive(tenantId4, "pm_cloud", "ontrail", "2028-09-05 20:58:42");
		String addAndActive = TenantCenter.addAndActive(tenantId4, "pm_cloud", "ontrail", t);

		System.out.println(addAndActive);
	}
	





	@Test
	/*
	 * 2.30 云应用启用接口 正常流程测试 共享服务绩效看板 的应用编码是ssc_performance 云应用停用接口 再执行云应用启用接口，应用状态是试用中
	 */
	public void enableAppTest() throws JsonProcessingException, IOException {

		String resCode = "ssc_performance";
		String msg = TenantCenter.enableApp(tenantId_aa, resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	
	@Test
	/*
	 * 2.31 云应用停用接口 正常流程测试 用户 18810039018 密码 yonyou11 企业帐号 stt05 的tenantId =
	 * "bdv029uh" 友零售 的应用编码是retail diwork 的应用编码是diwork
	 * 执行这个接口，StringBathOpenApp，让应用是开通的,然后执行云应用停用接口，能看到应用状态是停用。
	 * 再执行云应用启用接口，应用状态是试用中
	 */
	public void disableAppTest() throws JsonProcessingException, IOException {

		// 执行本测试用例前，需要先执行这个接口。让应用时开通的，使用中的。
		String resCode = "ssc_performance";
		String resluts = TenantCenter.StringBathOpenApp(
				"[{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ssc_performance\",\"tenantId\":\"" + tenantId_aa
						+ "\",\"endDate\":\"2020-11-01 01:10:00\"},{\"beginDate\":\"2016-05-01 12:00:00\",\"resCode\":\"uorder\",\"tenantId\":\"v80a9ocy\",\"endDate\":\"2018-01-01 01:10:00\"}]");
		// 停用接口
		String msg = TenantCenter.disableApp(tenantId_aa, resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	
	
	
	@Test
	/*
	 * 2.32 根据用户id获取应用
	 */
	public void getYhtAppsByUserIdTest() throws JsonProcessingException, IOException {

		
		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getYhtAppsByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
	}
	
	@Test
	/*
	 * 2.33 友空间开通更新服务
	 *   @param  comId
	 *	 @param  state
	 */
	public void tenantstateTest() throws JsonProcessingException, IOException {

		
		String comId = "";
		String state = "";
		String msg = TenantCenter.tenantstate(comId,state);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
	}
	
	
	@Test
	/*
	 *2.34 获取应用过期时间
	 */
	public void getRemainTimeTest() throws JsonProcessingException, IOException {

		// 执行本测试用例前，需要先执行这个接口。让应用时开通的，使用中的。
		String resCode = "ssc_performance";
		String resluts = TenantCenter.StringBathOpenApp(
				"[{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ssc_performance\",\"tenantId\":\"" + tenantId4
						+ "\",\"endDate\":\"2020-11-01 01:10:00\"}]");

		String msg = TenantCenter.getRemainTime(resCode,tenantId4);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	


	@Test
	/*
	 * 开通应用 正常流程测试 stt08:vik2b18p
	 * "resCode\":\"ipu\"，需要sdk.properties中client.credential.path=ipu.properties
	 * ipu.properties是通过yht-manager里系统--》应用配置--》搜索ipu--》进入详情--》下载密钥
	 * 可以把里面的内容粘到uculture.properties里，因为现在sdk.properties中client.credential.path=
	 * uculture.properties uculture.properties里的username=的值是什么，就能开通什么应用。
	 * username=app-manager时，能开通所有应用
	 */
	public void StringBathOpenAppTest() throws JsonProcessingException, IOException, InterruptedException {

//		String tenantId_stt08 ="auh5suoc";
//		String userName = "stt2019082901@test1988.com";
		
		String tenantId_stt08 ="vo0kk2ah";
		String userName = "18611286701";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String openStr = "[{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"retail\",\"tenantId\":\""
				+ tenantId_stt08 + "\",\"endDate\":\"2020-1-2 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ssc_performance\",\"tenantId\":\""
				+ tenantId_stt08 + "\",\"endDate\":\"2023-6-12 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"pm_cloud\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-16 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"xfs\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-16 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu_datang\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-9 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-8 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu_aoyang\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-6 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"uspace\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-5 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"iform\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2024-8-3 01:10:00\"},"				
				+ "{\"beginDate\":\"2013-1-11 12:50:00\",\"resCode\":\"diwork\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2030-1-30 10:25:54\",\"subApp\":\"diwork_u8c\",\"productLineCode\":\"diwork\",\"userId\":\"" + userId+"\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"yonyoueinvoice\",\"tenantId\":\""
				+ tenantId_stt08 + "\",\"endDate\":\"2024-8-1 01:10:00\"},"
				+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"diwork\",\"tenantId\":\"" + tenantId_stt08
				+ "\",\"endDate\":\"2025-9-18 01:10:00\"}]";

		String msg = TenantCenter.StringBathOpenApp(openStr);
		System.out.println(msg);

		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("success").get(0).get("resCode").asText().equals("retail"));
		Assert.assertTrue(node.get("success").get(1).get("resCode").asText().equals("ssc_performance"));
		Assert.assertTrue(node.get("success").get(2).get("resCode").asText().equals("pm_cloud"));
		Assert.assertTrue(node.get("success").get(3).get("resCode").asText().equals("xfs"));
		Assert.assertTrue(node.get("success").get(4).get("resCode").asText().equals("ipu_datang"));
		Assert.assertTrue(node.get("success").get(5).get("resCode").asText().equals("ipu"));
		Assert.assertTrue(node.get("success").get(6).get("resCode").asText().equals("ipu_aoyang"));
		Assert.assertTrue(node.get("success").get(7).get("resCode").asText().equals("uspace"));
		Assert.assertTrue(node.get("success").get(8).get("resCode").asText().equals("iform"));
		Assert.assertTrue(node.get("success").get(9).get("resCode").asText().equals("diwork"));
		Assert.assertTrue(node.get("success").get(10).get("resCode").asText().equals("yonyoueinvoice"));
		Assert.assertTrue(node.get("success").get(11).get("resCode").asText().equals("diwork"));

		
//		//给压测做数据，新增租户，开通应用	
//
//		
//		for(int i=100;i<300;i++) {
//			//14417888888下创建租户
//			TenantInfo tenantInfo=new TenantInfo();
//			tenantInfo.setUserMobile("14418888888");
//			tenantInfo.setTenantCode("stt"+i);
//			tenantInfo.setTenantName("stt"+i);
//			tenantInfo.setTenantFullName("stt"+i);
//			
//			String msg =  TenantCenter.batchSaveTenantInfo(tenantInfo);
//			System.out.println(msg);
//			JsonNode node = mapper.readTree(msg);
//			Assert.assertTrue(node.get("status").asInt() == 1);
//			// 等待两秒钟
//			Thread.sleep(12000);
//			
//			
//			String tenantId_stress=node.get("tenant").get("tenantId").asText();	
//			String openStr = "[{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"retail\",\"tenantId\":\""
//			+ tenantId_stress + "\",\"endDate\":\"2020-1-2 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ssc_performance\",\"tenantId\":\""
//			+ tenantId_stress + "\",\"endDate\":\"2023-6-12 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"pm_cloud\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-16 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"xfs\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-16 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu_datang\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-9 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-8 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu_aoyang\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-6 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"uspace\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-5 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"iform\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2024-8-3 01:10:00\"},"				
//			+ "{\"beginDate\":\"2013-1-11 12:50:00\",\"resCode\":\"diwork\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2030-1-30 10:25:54\",\"subApp\":\"diwork_u8c\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"yonyoueinvoice\",\"tenantId\":\""
//			+ tenantId_stress + "\",\"endDate\":\"2024-8-1 01:10:00\"},"
//			+ "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"diwork\",\"tenantId\":\"" + tenantId_stress
//			+ "\",\"endDate\":\"2025-9-18 01:10:00\"}]";
//
//			String msg1 = TenantCenter.StringBathOpenApp(openStr);
//		}

		
		
	}

	

	
	@Test
	/*
	 * 新增租户信息 
	 */
	public void addTenantTest() throws JsonProcessingException, IOException {
		
		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("tenantCode", "stt"+t);
		map.put("tenantId", "stt"+t);
		map.put("tenantName", "stt"+t);
		String msg = TenantCenter.addTenant(map);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
}
	
	
	@Test
	/*
	 * 分页查询租户接口 
	 */
	public void signAndGetTest() throws JsonProcessingException, IOException{
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t1 = date.format(new Date());
		long tt1 = Long.parseLong(t1);
		String result = SignUtils.signAndGet(url1);
		SimpleDateFormat date1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t2 = date.format(new Date());
		long tt2 = Long.parseLong(t2);
		long ttt=tt2-tt1;
		System.out.println("------------------------"+ttt+"------------------------");
		System.out.println(result);
//		System.err.println(result);
	}
	
	
	@Test
	public void test1113(){
//		String url = "http://if5oa2bf.c87e2267-1001-4c70-bb2a-ab41f3b81aa3.app.yyuap.com/apptenant/rest/tenant/page?searchParam=NC633简版&ps=20&pn=1"
		String url = "http://if5oa2bf.c87e2267-1001-4c70-bb2a-ab41f3b81aa3.app.yyuap.com/apptenant/rest/tenant/page?searchParam=NCC&ps=20&pn=1";
		String result = SignUtils.signAndGet(url);
		System.err.println(result);
	}

	
	@Test
	/*
	 * 根据租户id获取绑定该租户的ncc集团信息 
	 */
	public void getNCCBindInfoTest() throws JsonProcessingException, IOException {
		
		// 主企业账号未绑定集团
		String msg = TenantCenter.getNCCBindInfo(tenantId);		
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		// 绑定集团
		String msg1 = TenantCenter.getNCCBindInfo(tenantId_bdjt);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
}
	
	

	
	

	




	@Test
	/*
	 * 获取所有的应用的接口
	 */
	public void getAppsTest() throws JsonProcessingException, IOException {
	
		String msg = TenantCenter.getApps(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	
	@Test
	/*
	 * 通过营销云租户id查询租户信息
	 * 测试环境，王伟给的营销云租户ID 1182622131278080、1181151576543488、1201636592685312
	 */
	public void getTenantByMcIdTest() throws JsonProcessingException, IOException {
	
		String msg = TenantCenter.getTenantByMcId("1181151576543488");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}
	

	
	



	@Test
	/*
	 *  2.35 用友户通用户ID获取身份ID
	 * @param yhtTenantId 租户ID
	 * @param resCode 应用编码
	 * @param yhtUserIds 用户ID
	 */
	public void getYxyIdsByYhtIdsTest() throws JsonProcessingException, IOException {
		String resCode="diwork_u8c";	
		String authorizerId=UserCenterUtil.getUserIdByLoginName("18810039018");
		List<String> userIds = new ArrayList<String>();
		String[] value = { "suntt1026@126.com", "18611286701" ,"stt2018112801@test1988.com"};
		for (int j = 0; j < value.length; j++) {
			String userName = value[j];
			String yhtUserId = UserCenterUtil.getUserIdByLoginName(userName);
			userIds.add(yhtUserId);
		}
		String msg = TenantCenter.getYxyIdsByYhtIds(tenantId_u8c_new,resCode,userIds);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
	}

	
	@Test
	/*
	 *  2.36 根据用户id查询服务信息
	 */
	public void getCloudPortalAppsByUserIdTest() throws JsonProcessingException, IOException {
		String userId=UserCenterUtil.getUserIdByLoginName("jlccstt@126.com");
		String msg = TenantCenter.getCloudPortalAppsByUserId(userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);	
	}


	@Test
	/*
	 *  2.37 创建租户及管理员
	 * @param tenantName 租户名称
	 * @param mobile 手机号
	 * @param countryCode 手机号国家编码， 默认86
	 * @param userName 用户名
	 * @param terminal 终端标识 T+ 这个取值是畅捷通创建的租户，友空间和diwork会根据此标识开通相应的规格
	 * 
	 */
	public void createTenantAndOpenUspaceTest() throws JsonProcessingException, IOException {
		// 为了用户不存在，使用当前日期时间
//		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String t = date.format(new Date());
		
		String tenantName="stt";
		String mobile="14415888888";
		String countryCode="";
		String userName="suntt";
		String termina="T+";
		String msg = TenantCenter.createTenantAndOpenUspace(tenantName,mobile,countryCode,userName,termina);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);		
	}


	@Test
	/*
	 * 2.38 根据用户id和系统code获取可以登录租户(用户身份版)   
	 * 目前先测diwork  20190829
	 *  
	 */
	public void getCanLoginTenantsByUserIdentityTest() throws JsonProcessingException, IOException {

		String userName = "18810039018";
		String UserId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getCanLoginTenantsByUserIdentity(UserId,"diwork");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 *  2.39 更新产品初始化状态值
	 *  有产品初始化按钮的企业帐号，执行接口后产品初始化按钮没了。
	 */
	public void updateProductInitStatusToSuccessTest() throws JsonProcessingException, IOException {
		String msg = TenantCenter.updateProductInitStatusToSuccess("lamru63t","diwork","u8c3.0");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}



	@Test
	/*
	 *  2.40 批量获取产品初始化状态
	 *  -1   	应用未开通，未初始化
		0    	新购，未初始化
		1		新购，初始化中
		2		新购，初始化完成
		3		续费，未初始化
		4		续费，初始化中
		5		续费，初始化完成
	 */
	public void getProductInitStatusTest(){
		List<String> tenantIds = new ArrayList<String>();
		tenantIds.add("kn45w9pu");
		tenantIds.add("n30uawdr");
//		tenantIds.add("nyo8gxgd");
		String str = TenantCenter.getProductInitStatus(tenantIds,"diwork","u8c3.0");
		System.out.println(str);
	}
	
	
	@Test
	/*
	 *  2.41  U8C3.0根据用户id获取有权限登录的租户
	 * U8C3.0根据用户id获取有权限登录的租户，类似canLoginTenants
	 * @param userId
	 * @param systemCode
	 * @param productLineCode 产品线code  就是diwork分   原来的diwork 和U8C3.0（值为 diwork和u8c3.0）
	 * @return
	 */
	public void getCanLoginTenantsByUserIdentity1Test() throws JsonProcessingException, IOException{
		String userName = "18810039018";
		String UserId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getCanLoginTenantsByUserIdentity(UserId,"diwork","u8c3.0");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		String msg1 = TenantCenter.getCanLoginTenantsByUserIdentity(UserId,"diwork","diwork");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
	}
	
	@Test
	/*
	 *  2.42  获取应用下可登录租户列表
	 * 	获取应用下可登录租户
	 * @param userId
	 * @param resCode
	 * @param productLineCode
	 * @return
	 * @return
	 */
	public void getCanLoginTenants1Test() throws JsonProcessingException, IOException{
		String userName = "18810039018";
		String UserId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = TenantCenter.getCanLoginTenants(UserId,"diwork","u8c3.0");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		String msg1 = TenantCenter.getCanLoginTenants(UserId,"diwork","diwork");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);		
	}
	
	
	@Test
	/*
	 * 3.1 分页获取用户列表 
	 * @param tenantId 租户id
	 * @param resCode 系统code
	 * @param ps 一页显示多少条
	 * @param pn   第几页，默认为1
	 * @param searchcode  关键字，支持（用户名称、手机号、邮箱）
	 * @param sortType 排序，默认为null
	 */
	public void searchTenantUserListTest() throws JsonProcessingException, IOException {

		String resCode = "ncc";
		String ps = "3";
		String pn = "4";
		String searchcode = "st";		
		String sortType = "";
		String msg = UserCenter.searchTenantUserList(tenantId, resCode,ps,pn,searchcode,sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	
	@Test
	/*
	 * 3.2 分页获取用户列表
	 * @param tenantId  租户ID
     * @param ps  一页显示多少数据，默认为20         
     * @param pn  第几页，默认为1          
     * @param sortType 排序方式，默认按账号升序排列，如果传userName,则按用户名升序

	 */
	public void getUserListTest() throws JsonProcessingException, IOException {

		//默认每页条数，默认第一页，默认排序
		String msg = UserCenter.getUserList(tenantId1, null, null,"userName");;
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("content").get(0).get("userEmail").asText().equals("stt2019032901@test1988.com"));
		Assert.assertTrue(node.get("users").get("content").get(1).get("userMobile").asText().equals("18611286701"));
		
		//每页1条，显示第2页，按userName排序
		String msg1 = UserCenter.getUserList(tenantId1, "1", "2","userName");;
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("users").get("content").get(0).get("userMobile").asText().equals("18611286701"));
	}
	
	
	@Test
	/*
	 * 3.3 搜索该租户下的用户
	   *    分页获取用户列表
     * @param tenantId 租户ID
     * @param ps 一页显示多少数据，默认为20
     * @param pn  第几页，默认为1 
     * @param searchcode 按照用户名、邮箱、手机号搜索该租户下的用户
     * @param sortType 排序方式，默认按账号升序排列，如果传userName,则按用户名升序
     * @return json
	 */
	public void searchUserListTest() throws JsonProcessingException, IOException {
		
		String ps="";
		String pn="";
		String searchcode="stt";
		String sortType="";		
		String msg = UserCenter.searchUserList(tenantId3,ps, pn,searchcode,sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	/*
	 *  3.3.1搜索该租户下的用户排除用户的集合
	 *  @param tenantId 租户ID
     *  @param ps 一页显示多少数据，默认为20
     *  @param pn 第几页，默认为1 
     *  @param searchcode 按照用户名、邮箱、手机号搜索该租户下的用户
     *  @param sortType 排序方式，默认按账号升序排列，如果传userName,则按用户名升序
     *  @param userIds 该租户下的不在该集合的用户id
     *  @return json
	 */
	public void searchUserListNotInUserIdsTest() throws JsonProcessingException, IOException {
		
		//除了查询条件都使用默认值，一共应该查出22条数据，查询邮箱
		String ps="";
		String pn="";
		String searchcode="stt";
		String sortType="";
		List<String> userIds = new ArrayList<String>();
		String[] value = { "stt2019031723@test1988.com", "18611286701" };
		for (int j = 0; j < value.length; j++) {
			String userName = value[j];
			String yhtUserId = UserCenterUtil.getUserIdByLoginName(userName);
			userIds.add(yhtUserId);
		}	
//		String msg = UserCenter.searchUserListNotInUserIds(tenantId3, ps, pn, searchcode, sortType, userIds);		
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		Assert.assertTrue(node.get("users").get("totalPages").asInt() == 2);	
//		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 22);	
//		Assert.assertTrue(node.get("users").get("size").asInt() == 20);	
				
		//一页5条数据，显示第二页
		String ps1="5";
		String pn1="2";
		String sortType1="userName";
		String msg1 = UserCenter.searchUserListNotInUserIds(tenantId3, ps1, pn1, searchcode, sortType1, userIds);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("users").get("totalPages").asInt() == 5);	
		Assert.assertTrue(node1.get("users").get("totalElements").asInt() == 22);	
		Assert.assertTrue(node1.get("users").get("size").asInt() == 5);	
		
//		//汉字,用户名
//		String searchcode3="用户";
//		String msg3 = UserCenter.searchUserListNotInUserIds(tenantId3, ps, pn, searchcode3, sortType, userIds);		
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 1);
//		Assert.assertTrue(node3.get("users").get("totalElements").asInt() == 22);
//		
//		//特殊字符
//		String searchcode4="*";
//		String msg4 = UserCenter.searchUserListNotInUserIds(tenantId3, ps, pn, searchcode4, sortType, userIds);		
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 1);
//		Assert.assertTrue(node4.get("users").get("totalElements").asInt() == 1);
//		
//		//手机号
//		String searchcode5="6701";
//		List<String> userIds5 = new ArrayList<String>();
//		String[] value5 = { "stt2019031723@test1988.com"};
//		for (int j5 = 0; j5 < value5.length; j5++) {
//			String userName5 = value[j5];
//			String yhtUserId5 = UserCenterUtil.getUserIdByLoginName(userName5);
//			userIds5.add(yhtUserId5);
//		}	
//		String msg5 = UserCenter.searchUserListNotInUserIds(tenantId3, ps, pn, searchcode5, sortType, userIds5);		
//		System.out.println(msg5);
//		JsonNode node5 = mapper.readTree(msg5);
//		Assert.assertTrue(node5.get("status").asInt() == 1);
//		Assert.assertTrue(node5.get("users").get("totalElements").asInt() == 1);
		}
	
	@Test
	/*
	 * 3.3.2搜索该租(应用)下的用户
	   *   分页获取用户列表    
     * @param tenantId租户ID
     * @param resCode
     * @param ps  一页显示多少数据，默认为20
     * @param pn 第几页，默认为1 
     * @param searchcode 按照用户名、邮箱、手机号搜索该租户下的用户
     * @param sortType排序方式，默认按账号升序排列，如果传userName,则按用户名升序
     * @return json
	 */
	public void searchAppUserListTest() throws JsonProcessingException, IOException {
		
		//除了查询条件都使用默认值，一共应该查出22条数据，查询邮箱
		String ps="";
		String pn="";
		String resCode="NCC";
		String searchcode="stt";
		String sortType="";
		
		String msg = UserCenter.searchAppUserList(tenantId, resCode,ps, pn, searchcode, sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("users").get("totalPages").asInt() == 2);	
		Assert.assertTrue(node.get("users").get("totalElements").asInt() == 22);	
		Assert.assertTrue(node.get("users").get("size").asInt() == 20);	
				
//		//一页5条数据，显示第二页
//		String ps1="5";
//		String pn1="2";
//		String sortType1="userName";
//		String msg1 = UserCenter.searchAppUserList(tenantId3, ps1, pn1, searchcode, sortType1, userIds);		
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//		Assert.assertTrue(node1.get("users").get("totalPages").asInt() == 5);	
//		Assert.assertTrue(node1.get("users").get("totalElements").asInt() == 22);	
//		Assert.assertTrue(node1.get("users").get("size").asInt() == 5);	
//		
//		//汉字,用户名
//		String searchcode3="用户";
//		String msg3 = UserCenter.searchAppUserList(tenantId3, ps, pn, searchcode3, sortType, userIds);		
//		System.out.println(msg3);
//		JsonNode node3 = mapper.readTree(msg3);
//		Assert.assertTrue(node3.get("status").asInt() == 1);
//		Assert.assertTrue(node3.get("users").get("totalElements").asInt() == 22);
//		
//		//特殊字符
//		String searchcode4="*";
//		String msg4 = UserCenter.searchAppUserList(tenantId3, ps, pn, searchcode4, sortType, userIds);		
//		System.out.println(msg4);
//		JsonNode node4 = mapper.readTree(msg4);
//		Assert.assertTrue(node4.get("status").asInt() == 1);
//		Assert.assertTrue(node4.get("users").get("totalElements").asInt() == 1);
//		
//		//手机号
//		String searchcode5="6701";
//		List<String> userIds5 = new ArrayList<String>();
//		String[] value5 = { "stt2019031723@test1988.com"};
//		for (int j5 = 0; j5 < value5.length; j5++) {
//			String userName5 = value[j5];
//			String yhtUserId5 = UserCenterUtil.getUserIdByLoginName(userName5);
//			userIds5.add(yhtUserId5);
//		}	
//		String msg5 = UserCenter.searchAppUserList(tenantId3, ps, pn, searchcode5, sortType, userIds5);		
//		System.out.println(msg5);
//		JsonNode node5 = mapper.readTree(msg5);
//		Assert.assertTrue(node5.get("status").asInt() == 1);
//		Assert.assertTrue(node5.get("users").get("totalElements").asInt() == 1);
		}
	
	@Test
	/*
	 * 3.4 根据账号判断是否是管理员 正常流程测试
	 */
	public void isAdminNewTest() throws JsonProcessingException, IOException {

		// //不是管理员
		String userName = "18611286701";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg = UserCenter.isAdminNew(tenantId_aa, userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("flag").asInt() == 0);

		// 添加用户，设置成管理员
		String userName1 = "18810039018";
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		String msg1 = UserCenter.isAdminNew(tenantId_aa, userId1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("flag").asInt() == 1);

		// 企业帐号的创建者，自动就是管理员
		String userName2 = "stt2018092701@test1988.com";
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
		String msg2 = UserCenter.isAdminNew(tenantId_aa, userId2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("flag").asInt() == 1);
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
		params.put("userCode", userCode + "code");
		params.put("userName", userCode + "name");
		params.put("userEmail", userCode + "@test1988.com");
		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//用户新增后就是激活的
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("tenantId", tenantId2);
		params1.put("userCode", userCode + "code1");
		params1.put("userName", userCode + "name1");
		params1.put("userEmail", userCode + "1@test1988.com");
		params1.put("userActivate", "true");
		String msg1 = UserCenter.addUser(params1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
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
		params.put("userCode", t + "User002");
		params.put("userName", t + "用户002");
		params.put("userEmail", t + "@test1988.com");
		params.put("tenantId", "bi9o4bsk");//
		String msg1 = UserCenter.addUserAndRelation(params);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("保存成功"));
	
	}
	
	
	@Test
	/*
	 * 3.7 批量添加用户（最多50个）
	 * @param tenantId 租户ID
     * @param systemCode 系统编码（例如：ipu、hr_cloud）
     * @param source 来源
     * @param cuser 操作用户的ID
     * @param jsonStr 批量添加的json串
	 */
	public void addUsersTest() throws JsonProcessingException, IOException {

		String  systemCode="ipu";
		UserSource  source = null;
		String cuser = UserCenterUtil.getUserIdByLoginName("18810039018"); 
		String  jsonStr="";
		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());

		String msg1 = UserCenter.addUsers(tenantId2,systemCode,source,cuser,jsonStr);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("保存成功"));
	
	}
	
	
	@Test
	/*
	 * 3.8 批量添加用户，如果存在的用户直接添加关联关系（最多50个）
	 * @param tenantId 租户ID
     * @param systemCode 系统编码（例如：ipu、hr_cloud）
     * @param source 来源
     * @param cuser 操作用户的ID
     * @param jsonStr 批量添加的json串
	 */
	public void addUsersAndRelationsTest() throws JsonProcessingException, IOException {

		String  systemCode="ipu";
		UserSource  source = null;
		String cuser = UserCenterUtil.getUserIdByLoginName("18810039018"); 
		String  jsonStr="";
		// 为了用户不存在，使用当前日期时间
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t = date.format(new Date());
		String msg1 = UserCenter.addUsersAndRelations(tenantId2,systemCode,source,cuser,jsonStr);		
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("保存成功"));
	
	}
	
	
//	@Test
//	/*
//	 * 3.9 根据用户名搜索用户，和getUserList方法基本一致，只是多了一个用户名的条件，相当于数据库中的
//     * @param userCode 账号
//     * @param tenantId 租户ID
//     * @param ps 页面大小
//     * @param pn 当前页数
//     * @param sortType 排序方式，默认按账号升序排列，如果传userName,则按用户名升序
//	 */
//	public void searchUserByUserCodeTest() throws JsonProcessingException, IOException {
//		String  userCode="stt";
//		//默认每页条数，默认第一页，默认排序
//		String msg = UserCenter.searchUserByUserCode(userCode,tenantId1, null, null,"userName");
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//		Assert.assertTrue(node.get("users").get("content").get(0).get("userEmail").asText().equals("stt2019032901@test1988.com"));
//		Assert.assertTrue(node.get("users").get("content").get(1).get("userMobile").asText().equals("18611286701"));
//		
//		//每页1条，显示第2页，按userName排序
//		String msg1 = com.yonyou.iuap.tenant.sdk.UserCenter.searchUserByUserCode(tenantId1, "1", "2","userName");;
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//		Assert.assertTrue(node1.get("users").get("content").get(0).get("userMobile").asText().equals("18611286701"));
//	}
	
	@Test
	/*
	 * 3.10 将用户加入指定租户  正常流程测试 userType(1为租户管理员，3为普通用户)
	 */
	public void addToTenantTest() throws JsonProcessingException, IOException {

//		// 添加管理员
//		int userType = 1;
//		String userName1 = "suntt1026@126.com";
//		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
//		String userName2 = "18611286701";
//		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
//		String[] pks = { userId1, userId2 };
//		String msg = UserCenter.addToTenant(tenantId, userType, pks);
//		System.out.println(msg);
//		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
//
//		// 添加普通用户
//		int userType1 = 3;
//		String userName11 = "stt2018112801@test1988.com";
//		String userId11 = UserCenterUtil.getUserIdByLoginName(userName11);
//		String[] pks1 = { userId11 };
//		String msg1 = UserCenter.addToTenant(tenantId, userType1, pks1);
//		System.out.println(msg1);
//		JsonNode node1 = mapper.readTree(msg1);
//		Assert.assertTrue(node1.get("status").asInt() == 1);
//
//		// 添加100个用户
//		int userType2 = 3;
//		String userName21;
//		String userId21;
//		String[] pks2 = new String[100];
//		for (int i = 0; i < 10; i++) {
//			userName21 = "1860000000" + i;
//			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
//			pks2[i] = userId21;
//		}
//		for (int i = 10; i < 100; i++) {
//			userName21 = "186000000" + i;
////			System.out.println(i+"-------------------------");
//			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
//			pks2[i] = userId21;
//		}
//		String msg2 = UserCenter.addToTenant(tenantId, userType2, pks2);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		// 添加压测用户
		int userType2 = 3;
		String userName21;
		String userId21;
		String[] pks2 = new String[200];
		for (int i = 100; i < 200; i++) {
			userName21 ="stt20190815s"+i + "@test1988.com";
			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
			pks2[i] = userId21;
		}
		String msg2 = UserCenter.addToTenant("aeoo2t4l", userType2, pks2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);	
	}

	
	@Test
	/*
	 * 3.11 根据租户id获取所有的管理员
	 */
	public void getTenantAdminByTenantIdTest() throws JsonProcessingException, IOException {

		String msg = UserCenter.getTenantAdminByTenantId(tenantId1);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenantUsers").get(0).get("userEmail").asText().equals("yixixinzi@126.com"));
	}
	
	@Test
	/*
	 * 3.12 将普通用户设置成为管理员
	 */
	public void signUserToAdminTest() throws JsonProcessingException, IOException {
		
		//用户不存在
		String user = "201909051019590911@test1988.com";
		String userId = UserCenterUtil.getUserIdByLoginName(user);
		String msg = UserCenter.signUserToAdmin(userId,tenantId2);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("该用户不属于该租户请先将该用户添加到该租户下"));

		String user1 = "20190905095916072@test1988.com";
		String userId1 = UserCenterUtil.getUserIdByLoginName(user1);
		String msg1 = UserCenter.signUserToAdmin(userId1,tenantId2);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("更改用户的权限成功"));
	}
	
	
	@Test
	/*
	 * 3.13 将管理员设置成为普通用户
	 */
	public void changeAdminToUserTest() throws JsonProcessingException, IOException {
		
		//用户不存在
		String user = "201909051019590911@test1988.com";
		String userId = UserCenterUtil.getUserIdByLoginName(user);
		String msg = UserCenter.changeAdminToUser(userId,tenantId2);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("该用户不属于该租户请先将该用户添加到该租户下"));
		
		//管理员设置成普通用户
		String user1 = "20190905095916072@test1988.com";
		String userId1 = UserCenterUtil.getUserIdByLoginName(user1);
		String msg1 = UserCenter.changeAdminToUser(userId1,tenantId2);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("更改用户的权限成功"));
		
		//普通用户设置成普通用户（上一步已经设置成普通用户了）
		String msg2 = UserCenter.changeAdminToUser(userId1,tenantId2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
//		Assert.assertTrue(node2.get("msg").asText().equals(""));
	}
	
	@Test
	/*
	 * 3.14  合并用户
	 * @param srcUserId 需要合并的userid
     * @param destUserId 合并后的userid
     * @return
	 */
	public void mergerUserTest() throws JsonProcessingException, IOException, InterruptedException {
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userCode = date.format(new Date());
		//新增第一个用户
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", userCode + "code");
		params.put("userName", userCode + "name");
		params.put("userEmail", userCode + "@test1988.com");
		String msg = UserCenter.addUser(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		String userId=node.get("user").get("userId").asText();
		
		//新增第二个用户
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("userCode", userCode + "code1");
		params1.put("userName", userCode + "name1");
		params1.put("userEmail", userCode + "1@test1988.com");
		String msg1 = UserCenter.addUser(params1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String userId1=node.get("user").get("userId").asText();
		
		// 等待2秒
		Thread.sleep(12000);
				
		//用户合并
		String msg2 = UserCenter.mergerUser(userId,userId1);
//		String msg2 = UserCenter.mergerUser("03168adc-3ece-4825-b62f-fa17e1658df3","6ca668ee-8426-462f-8e02-67ad5226fbd1");
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
	}
	
	
	@Test
	/*
	 * 3.15 判断用户是否存在租户下
	 * { "status":1, "code":"1000", } 
	 * 1000:用户不存在 1001:用户存在，但不存在租户下 1002:用户存在，是该租户的普通用户 1003:用户存在，是该租户的管理员
	 */
	public void userExistInTenantTest() throws JsonProcessingException, IOException {
		//1003:用户存在，是该租户的管理员
		String userMobile="18611286701";
		String userEmail="";
		String resCode="NCC";
		String msg=UserCenter.userExistInTenant(tenantId,userMobile,userEmail,resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("code").asInt() == 1003);
		
		//1002:用户存在，是该租户的普通用户
		String userMobile1="18600000002";
		String msg1=UserCenter.userExistInTenant(tenantId,userMobile1,userEmail,resCode);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("code").asInt() == 1002);
		
		//1001:用户存在，但不存在租户下
		String userMobile2="18800000000";
		String msg2=UserCenter.userExistInTenant(tenantId,userMobile2,userEmail,resCode);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("code").asInt() == 1001);
		
		//1000:用户不存在
		String userEmail3="stt2019090701111111@test1988.com";
		String msg3=UserCenter.userExistInTenant(tenantId,userMobile1,userEmail3,resCode);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		Assert.assertTrue(node3.get("code").asInt() == 1000);
	}
	
	
	@Test
	/*
	 *   3.15.1 判断用户是否存在租户下
	 *{ "status":1, "code":"1000", } 
	 *1000:用户不存在 1001:用户存在，但不存在租户下 1002:用户存在，是该租户的普通用户 1003:用户存在，是该租户的管理员
	 */
	public void userExistInTenantByIdTest() throws JsonProcessingException, IOException {
		//1003:用户存在，是该租户的管理员
		String userName = "18611286701";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=UserCenter.userExistInTenantById(tenantId,userId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("code").asInt() == 1003);
		
		//1002:用户存在，是该租户的普通用户
		String userName1 = "18600000002";
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		String msg1=UserCenter.userExistInTenantById(tenantId,userId1);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("code").asInt() == 1002);
		
		//1001:用户存在，但不存在租户下
		String userName2 = "18800000000";
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
		String msg2=UserCenter.userExistInTenantById(tenantId,userId2);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("code").asInt() == 1001);
		
		//1000:用户不存在
		String userId3 = "111111111111111111111111111111111111";
		String msg3=UserCenter.userExistInTenantById(tenantId,userId3);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		Assert.assertTrue(node3.get("code").asInt() == 1000);
	}
	
	
	
	@Test
	/*
	 * 3.16 搜索租户下的用户
	 * diwork 根据添加的顺序时间排序倒排
     * @param tenantId 租户ID
     * @param ps 页面大小
     * @param pn 当前页数
     * @param searchcode  过滤条件，支持用户名，手机号，邮箱
     * @param sortType 排序方式，默认按账号升序排列，如果传userName,则按用户名升序
	 */
	public void pageUsersTest() throws JsonProcessingException, IOException {
		String ps="3";
		String pn="2";
		String searchcode="stt";
		String sortType="";
		String msg=UserCenter.pageUsers(tenantId3,ps, pn,searchcode,sortType);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
	}
	
	@Test
	/*
	 * 3.17 搜索该应用下的排除userIds
     * @param tenantId 租户ID
     * @resCode 应用编码
     * @param ps 页面大小
     * @param pn 当前页数
     * @param searchcode   按照用户名、邮箱、手机号搜索该租户下的用户
     * @param sortType 排序方式，默认按账号升序排列，如果传userName,则按用户名升序
     * @param userIds 该租户下的不在该集合的用户id
	 */
	public void searchAppUserListNotInUserIdsTest() throws JsonProcessingException, IOException {
		String resCode="NCC";
		String ps="";
		String pn="";
		String searchcode="stt";
		String sortType="";
		List<String> userIds=new ArrayList<String>() ;
		String[] value = { "18810039018", "18611286701" };
		for (int j = 0; j < value.length; j++) {
			String userName = value[j];
			String yhtUserId = UserCenterUtil.getUserIdByLoginName(userName);
			userIds.add(yhtUserId);
		}
		String msg=UserCenter.searchAppUserListNotInUserIds(tenantId3,resCode,ps, pn,searchcode, sortType,userIds);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
	}
	
	
	@Test
	/*
	 * 3.18 根据多用户id查询多用户
	 */
	public void getUsersByUserIdsTest() throws JsonProcessingException, IOException {
	List<String> userIds=new ArrayList<String>() ;
	String[] value = { "18810039018", "18611286701" };
	for (int j = 0; j < value.length; j++) {
		String userName = value[j];
		String yhtUserId = UserCenterUtil.getUserIdByLoginName(userName);
		userIds.add(yhtUserId);
	}
	String msg=	UserCenter.getUsersByUserIds( userIds);
	System.out.println(msg);
	JsonNode node = mapper.readTree(msg);
	Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/*
	 * 3.19 解除租户和用户之间的关联 正常流程测试
	 */
	public void removeFromTenantTest() throws JsonProcessingException, IOException {

		int userType = 3;
		// 100个用户的ID
		String userName;
		String userId;
		String[] pks2 = new String[100];
		for (int i = 0; i < 10; i++) {
			userName = "1860000000" + i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i] = userId;
		}
		for (int i = 10; i < 100; i++) {
			userName = "186000000" + i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i] = userId;
		}
		// 解除租户和用户之间的关联
		String msg = UserCenter.removeFromTenant(tenantId_u8c, pks2);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("解除关联成功"));

		// 还原数据，把这100个用户添加上，以便下次执行应用下移除用户
		String msg2 = UserCenter.addToTenant(tenantId, userType, pks2);
		System.out.println(msg2);

	}

	
	@Test
	/*
	 * 3.20 将用户加入指定租户（有授权者authorizerId）
	 * @param tenantId
     * @param userType(1为租户管理员，3为普通用户) 
     * @param cUserId 操作用户ID
     * @param pks（用户的userID集合，最多100）
     * @param authorizerId（授权者id）
     * @return
	 */
	public void addToTenantTest1() throws JsonProcessingException, IOException {
		String userName = "18810039018";
		String authorizerId=UserCenterUtil.getUserIdByLoginName(userName);
			
		// 添加管理员
		int userType = 1;
		String userName1 = "suntt1026@126.com";
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		String userName2 = "18611286701";
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
		String[] pks = { userId1, userId2 };		
		String msg = UserCenter.addToTenant(tenantId, userType, pks,authorizerId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 添加普通用户
		int userType1 = 3;
		String userName11 = "stt2018112801@test1988.com";
		String userId11 = UserCenterUtil.getUserIdByLoginName(userName11);
		String[] pks1 = { userId11 };
		String msg1 = UserCenter.addToTenant(tenantId, userType1, pks1,authorizerId);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);

//		// 添加100个用户
//		int userType2 = 3;
//		String userName21;
//		String userId21;
//		String[] pks2 = new String[100];
//		for (int i = 0; i < 10; i++) {
//			userName21 = "1860000000" + i;
//			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
//			pks2[i] = userId21;
//		}
//		for (int i = 10; i < 100; i++) {
//			userName21 = "186000000" + i;
////			System.out.println(i+"-------------------------");
//			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
//			pks2[i] = userId21;
//		}
//		String msg2 = UserCenter.addToTenant(tenantId, userType2, pks2,authorizerId);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);
		
//		// 添加压测用户
//		int userType2 = 3;
//		String userName21;
//		String userId21;
//		String[] pks2 = new String[200];
//		for (int i = 100; i < 200; i++) {
//			userName21 ="stt20190815s"+i + "@test1988.com";
//			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
//			pks2[i] = userId21;
//		}
//		String msg2 = UserCenter.addToTenant("aeoo2t4l", userType2, pks2);
//		System.out.println(msg2);
//		JsonNode node2 = mapper.readTree(msg2);
//		Assert.assertTrue(node2.get("status").asInt() == 1);	
	}
	
	
	@Test
	/*
	 * 3.21 应用下移除用户 
	 *  正常流程测试 用户 18810039018 密码 yonyou11 企业帐号 stt05 的tenantId =
	 * "bdv029uh" 友零售 的应用编码是retail 用户 18810039018 密码 yonyou11 企业帐号 717线上空间
	 * 的tenantId = "o32y4cs3" 云表单 的应用编码是iform
	 */
	public void removeUsersFromAppsTest() throws JsonProcessingException, IOException {
		// idtest环境
		// String tenantId = "bdv029uh";
		// String resCode = "diwork";

		// //daily环境
		// String tenantId = "u2okte6b";
		// String resCode = "NCC";

		// euc环境
		// String tenantId = "w6ehuyxs";
		// String resCode = "pm_cloud";
		// String tenantId = "o32y4cs3";
		String resCode = "u8";
		int userType = 3;

		// 10个用户的ID
		String userName;
		String userId;
		String[] pks2 = new String[10];
		for (int i = 0; i < 10; i++) {
			userName = "1860000000" + i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i] = userId;
		}

		// 应用下移除用户
		String msg = UserCenter.removeUsersFromApps(tenantId_u8c, resCode, pks2);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		// Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("message").asText().equals("删除成功\r\n"));

		// 还原数据，把这10个用户添加上，以便下次执行应用下移除用户
		String msg2 = UserCenter.addToTenant(tenantId, userType, pks2);
		System.out.println(msg2);

	}
	
	
	@Test
	/*
	 * 3.22 批量启用用户身份
	   *   正常流程测试
	 */
	public void batchEnableUserIdentityTest() throws JsonProcessingException, IOException {
	
		// 用户的ID
		String userName;
		String userId;
		String[] pks2 = new String[100];
		for (int i = 0; i < 2; i++) {
			userName = "1860000000" + i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i] = userId;
		}		
		String resCode="diwork_u8c";		
		String msg = UserCenter.batchEnableUserIdentity(tenantId_u8c_new, resCode,pks2);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("success").asText().equals("更新成功"));
	}
	
	
	



	@Test
	/*
	 * 3.23 批量停用用户身份
	   *  正常流程测试
	 */
	public void batchStopUserIdentityTest() throws JsonProcessingException, IOException {
	
		// 用户的ID
		String userName;
		String userId;
		String[] pks2 = new String[100];
		for (int i = 0; i < 2; i++) {
			userName = "1860000000" + i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i] = userId;
		}		
		String resCode="diwork_u8c";		
		String msg = UserCenter.batchStopUserIdentity(tenantId_u8c_new, resCode,pks2);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("更新成功"));
	}
	
	
	@Test
	/*
	 * 3.24 关键字搜索用户身份 正常流程测试
	 * @param tenantId 租户ID
	 * @param resCode 应用编码
	 * @param keyword 匹配用户名、手机号、邮箱，编码，可空
	 * @param extFilters 预留过滤字段
	 * @param userTypes 可空
	 * @param pn 页码
	 * @param ps 页大小
	 * @return
	 */
	public void searchUserIdentityTest() throws JsonProcessingException, IOException {
	
		
		String keyword="YHT-18810039018";
	//	String keyword="18810039018";
		int pn=1;
		int ps=10;
		Map<String, Object> extFilters=new HashMap<String, Object>();
		// 用户的ID
		String userName;
		List<Integer> userTypes=new ArrayList<Integer>();
		String resCode="diwork_u8c";		
		String msg = UserCenter.searchUserIdentity(tenantId_u8c_new1, resCode,keyword,extFilters,userTypes,pn,ps);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	
	@Test
	/*
	 	   * 3.25  批量添加用户身份（只支持员工）
	 	   * @param tenantId 租户ID
	 	   * @param resCode 应用编码（diwork_u8c）
	 	   * @param userType(3为普通用户； ) 
	 	   * @param pks（用户的userID集合，最多100）
	 	   * @param authorizerId（授权者id）
	 	   * @param identityType(1 普通员工) 
	 	   * @param accessToken
	 */
	public void addUserIdentityToTenantTest() throws JsonProcessingException, IOException {
		//String resCode="diwork_u8c";	
		String resCode="diwork";
		// 只能添加普通用户
		int userType = 3;
		String authorizerId=UserCenterUtil.getUserIdByLoginName("18810039018");
		String userName1 = "suntt1026@126.com";
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		String userName2 = "18611286701";
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
		String[] pks = { userId1, userId2 };
		// 获取ssotoken--本测试方法需要
		String Password = "yonyou11";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "18810039018");
		params.put("shaPassword", shaPassword);
		params.put("md5Password", md5Password);

		String msg = UserCenter.createSSOToken(params);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String ssotoken = node.get("data").asText();

		// 本获取accesstoken--本测试方法需要这个参数
		String msg1 = UserCenter.genAccessTokenBySSOToken(ssotoken);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		String accesstoken = node1.get("accesstoken").asText();
		
		
		String msg2 = UserCenter.addUserIdentityToTenant(tenantId_u8c_new1,resCode, userType, pks,authorizerId,accesstoken);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		
		//测试自定义添加的用户身份类型的用户
		String msg3 = UserCenter.addUserIdentityToTenant("hpwkkhz9",resCode, userType, pks,authorizerId,"17");
		System.out.println(msg3);
		

	}
	
	
	@Test
	/*
	 * 3.26  获取一个用户的所有身份类型
	 */
	
	public void getAllIdentityByUserIdTest() throws JsonProcessingException, IOException {
		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String sysCode="diwork";	
		String msg= UserCenter.getAllIdentityByUserId(userId,sysCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
	}
	
	
	@Test
	/*
	 * 3.27  获取某租户下所有身份类型列表
	 */
	
	public void getIdentityTypeByTenantIdTest() throws JsonProcessingException, IOException {
		String pn = "1";
		String ps = "10";	
		String msg= UserCenter.getIdentityTypeByTenantId(tenantId,pn,ps);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
	}
	
	
	@Test
	/*
	 * 3.28  同步NCC用户并添加身份
	 *  
	 */
	public void addUsersAndIdentityTest() throws JsonProcessingException, IOException {
		String resCode = "cloudportal";
		int userType = 7;
		String userJson = "{\"users\":[{\"userEmail\":\"\",\"userMobile\":\"15910464742\",\"userName\":\"cl\",\"vendorId\":\"ceshiid\",\"customId\":\"haishics\"}," +
		                "{\"userEmail\":\"\",\"userMobile\":\"1348875901\",\"userName\":\"c3\",\"vendorId\":\"ceshiid\",\"customId\":\"haishics\"}]}";
		String msg = UserCenter.addUsersAndIdentity(tenantId,resCode,userType,userJson);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/*
	 * 3.29  通过企业密钥查询租户下用户明文信息
	 */
	public void getUsersInfoTest() throws JsonProcessingException, IOException {
		String resCode="";
		InputStream stream=new FileInputStream(path);
		String msg=TenantCenter.getUsersInfo(resCode , stream);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	
	
	@Test
	/*
	 * 3.30 批量根据userId和UserType，查询身份信息，主要需要yxyUserId
	 */
	public void getYxyUserIdsTest() throws JsonProcessingException, IOException {
		String userType="1";
		List<String> userIds = new ArrayList<String>();
		String[] value = { "suntt1026@126.com", "18611286701" ,"stt2018112801@test1988.com"};
		for (int j = 0; j < value.length; j++) {
			String userName = value[j];
			String yhtUserId = UserCenterUtil.getUserIdByLoginName(userName);
			userIds.add(yhtUserId);
		}
		String msg=UserCenter.getYxyUserIds("sumwffgs",userType,userIds);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	
	@Test
	/*
	 *  根据U8C租户id、单组织id获取预制（默认）账号获取token接口
	 *  orgId测试时随便输入
	 */
	public void getU8cSystemAccessTokenTest() throws JsonProcessingException, IOException {
		String orgId="aa";
		String msg = UserCenter.getU8cSystemAccessToken(tenantId_u8c_new1,orgId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
	}



	

	

	
	@Test
	/*
	 * GetAuthFileBySysId（只允许授权应用接入）
	 *  
	 */
	public void getAuthFileBySysIdTest() throws JsonProcessingException, IOException {
		String sysId = "yht";
		String msg = UserCenter.getAuthFileBySysId(sysId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	@Test
	public void testBatch1111(){
//		String userName = "stt2019083001@test1988.com";
//		String userName = "14419888888";
		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String resluts = TenantCenter.StringBathOpenApp(""
				+ "[{\"beginDate\":\"2019-08-29 17:00:00\",\"resCode\":\"diwork\","
				+ "\"tenantId\":\"j51aphpt\",\"endDate\":\"2020-08-29 17:00:00\","
				+ "\"productLineCode\":\"diwork\","
				+ "\"userId\":\"" + userId+"\"}]");
		System.out.println(userId);
		System.err.println(resluts);
	}
	
	@Test
	public void testBatch2222(){
		String resluts = TenantCenter.StringBathOpenApp(""
				+ "[{\"beginDate\":\"2019-08-29 17:00:00\",\"resCode\":\"diwork\","
				+ "\"tenantId\":\"llcd8atu\",\"endDate\":\"2020-08-29 17:00:00\","
				+ "\"productLineCode\":\"diwork\","
				+ "\"userId\":\"2db5b88d-4ef0-41b5-9df9-d177f138c918\"}]");
		System.err.println(resluts);
	}
	
	
	
	@Test
	/*产品线开通测试用例*/
		public void testBatch(){
			String resluts = TenantCenter.StringBathOpenApp("[{\"beginDate\":\"2019-02-30 10:25:54\",\"resCode\":\"diwork\",\"tenantId\":\"ja97kdpd\",\"endDate\":\"2020-02-30 10:25:54\",\"subApp\":\"diwork_u8c\"}]");
			System.err.println(resluts);
		}
}