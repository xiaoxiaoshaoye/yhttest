package com.yonyou.tenant.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
import com.yonyou.iuap.tenant.sdk.UserCenter;



public class TenantCenterTestSuntt {
	ObjectMapper mapper;
	
	//idtest************************************  
	//用户 18611286701  密码 yonyou11 
	//企业帐号"接口测试使用" 的tenantId  = "u5tuj80u"
	//企业帐号"接口测试使用1"的tenantId  = "n45h6puh"
	
	//用户 18810039018  密码 yonyou11 
	//企业帐号u8c私有云的tenantId  = "p59szm28"
	
	//用户 stt2018092701@test1988.com 密码 yonyou11
	//企业帐号aa的tenantId  ="gqgk70jg"
	
	String tenantId  = "u5tuj80u";
	String tenantId1  = "n45h6puh";
	String tenantId_u8c  = "p59szm28";
	String tenantId_aa  = "gqgk70jg";
	String tenantId_stt08="vik2b18p";
	String url="https://uastest.yyuap.com/apptenant/file/fdfsimg/down?id=group1/M00/00/9E/rBQSEFtX3ASAWB3KAALv9miQkCw4354192";
	
	
	//euc*****************************************  
	//用户 18810039018  密码 yonyou11 
	//企业帐号"接口测试使用"	的tenantId  = "wlaml5ue"
	//企业帐号"接口测试使用1"的tenantId  = "lmlt2260"
	//企业帐号u8c私有云的tenantId  = "mz1anu3t"
	//用户 stt2018092701@test1988.com 密码 yonyou11
	//企业帐号aa的tenantId  ="l5v7r7ts"
//	String tenantId  = "wlaml5ue";
//	String tenantId1  = "lmlt2260";
//	String tenantId_u8c = "mz1anu3t";
//	String tenantId_aa  = "h9cpaznu";
//	String tenantId_stt08="dzlg25tq";
//	String url="https://apcenter.yonyoucloud.com/apptenant/file/fdfsimg/down?id=g1/M00/02/A9/CgMHjFvkTouAd_sgAAEIDMXwN1Y2227209";
//	
	
	//daily
	
	
	
	@Before
	public void init() {
		mapper = new ObjectMapper();

	}


	
	@Test
	/* 根据租户id获取租户信息
	 * 正常流程测试
	 * 用户 18611286701  密码 yonyou11 企业帐号"接口测试使用"的tenantId  = "u5tuj80u"
	*/
	public void getTenantByIdTest() throws JsonProcessingException, IOException{
		
		String msg=TenantCenter.getTenantById(tenantId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("接口测试使用"));
		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("suntt@yonyou.com"));
	}
	
	
	@Test
	/* 根据租户code获取租户信息
	 * 正常流程测试
	 * idtest:用户 18611286701  密码 yonyou11 企业帐号"接口测试使用1"的code  = "stt123456"
	 * euc:用户 18810039018  密码 yonyou11 企业帐号"接口测试使用1"的code  = "stt123456"
	*/
	public void getTenantByTenantCodeTest() throws JsonProcessingException, IOException{
		
		String msg=TenantCenter.getTenantByTenantCode("stt123456");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenant").get("tenantName").asText().equals("接口测试使用1"));
		Assert.assertTrue(node.get("tenant").get("tenantEmail").asText().equals("suntt@yonyou.com"));
	}
	
	
	
	@Test
	/* 根据租户code获取租户状态
	 * 正常流程测试
	 * idtest:用户 18611286701  密码 yonyou11 
	 * euc:用户 18611286701  密码 yonyou11 
	 * 企业帐号"接口测试使用1"的code  = "stt123456"
	 * 企业帐号"接口测试使用2"的code  = "stt000000"
	*/
	public void getTenantStatusTest() throws JsonProcessingException, IOException{
		String tenantCodes []={"stt123456","stt000000"};
		String msg=TenantCenter.getTenantStatus(tenantCodes);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("statusMap").get("stt123456").asInt()==0);
		Assert.assertTrue(node.get("statusMap").get("stt000000").asInt()==0);

		
	}
	
	@Test
	/* 根据租户id获取企业LOGO
	 * 正常流程测试
	 * 用户 18611286701  密码 yonyou11 企业帐号"接口测试使用"的tenantId  = "u5tuj80u"
	 * 用户 18611286701  密码 yonyou11 企业帐号"接口测试使用1"的tenantId  = "n45h6puh"
	*/
	public void getLogoByTenantIdTest() throws JsonProcessingException, IOException{
		
		//没有头像的企业帐号，获取的默认头像
		String msg=TenantCenter.getLogoByTenantId(tenantId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("url").asText().equals("http://cdn.yonyoucloud.com/dev/apcenter/img/logo/LOGO.png"));
	
		//用户上传了头像
		String msg1=TenantCenter.getLogoByTenantId(tenantId1);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("url").asText().equals(url));
	
	}
	
	@Test
	/* 根据用户id和系统code获取租户
	 * 正常流程测试
	 * 例如：新建了个用户，然后新建了两个租户1，租户2，租户1下开通了友空间uspace，租户2没有开通，
	 * 调用这个接口getCanUseTenants(userId,"uspace");，会返回租户1
	*/
	public void getCanUseTenantsTest() throws JsonProcessingException, IOException{
		String userName = "jlccstt@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=TenantCenter.getCanUseTenants(userId,"u8");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenants").get(0).get("tenantName").asText().equals("有U8"));
		Assert.assertTrue(node.get("tenants").get(1).get("tenantName").asText().equals("有U8-1"));
	}
	
	
	@Test
	/* 根据用户id和系统code获取可以登录租户
	 * 正常流程测试
	 * 例如：jlccstt@126.com，新建了两个租户1，租户2
	 * 两个租户都有u8，两个租户都添加用户 17779888888，这个手机号对应的邮箱是jlccstt@126.com
	 * 但租户1激活时，给jlccstt@126.com分配了许可。租户2没有激活。
	 * 这样用户id使用jlccstt@126.com，就能查出来租户1.
	 * 这个
	*/
	public void getCanLoginTenantsTest() throws JsonProcessingException, IOException{
//		String userName = "18811346659"; 
		String userName = "jlccstt@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=TenantCenter.getCanLoginTenants(userId,"u8");	
//		String msg=TenantCenter.getCanLoginTenants(userId,"ublinker_gateway");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据用户id查询所有的租户
	 * 正常流程测试
	*/
	public void findTenantsByUserIdTest() throws JsonProcessingException, IOException{
		String userName = "jlccstt@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=TenantCenter.findTenantsByUserId(userId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据用户id查询所管理的租户
	 * 正常流程测试
	*/
	public void getUserManageTenantsTest() throws JsonProcessingException, IOException{
		String userName = "jlccstt@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=TenantCenter.getUserManageTenants(userId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	
	@Test
	/* 根据resCode查询所有的租户
	 * 正常流程测试
	*/
	public void getTenantIdsByResCodeTest() throws JsonProcessingException, IOException{
		String resCode = "u8"; 
		String msg=TenantCenter.getTenantIdsByResCode(resCode);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据tenantId查询所有的应用的code
	 * 正常流程测试
	*/
	public void getResCodesByTenantIdTest() throws JsonProcessingException, IOException{

		String msg=TenantCenter.getResCodesByTenantId(tenantId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("resCodes").get(0).asText().equals("ssc_performance"));
		Assert.assertTrue(node.get("resCodes").get(1).asText().equals("NCC"));
	}
	
	@Test
	/* 根据userId获取该用户的应用列表
	 * 正常流程测试
	*/
	public void getAppsByUserIdTest() throws JsonProcessingException, IOException{

		String userName = "stt851026@126.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String msg=TenantCenter.getAppsByUserId(userId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("state").asInt() == 1);
//		Assert.assertTrue(node.get("data").get(0).get("resName").get(0).asText().equals("友空间"));
//		Assert.assertTrue(node.get("data").get(1).get("resName").get(1).asText().equals("U8"));
	}
	
	
	
	@Test
	/* 根据租户id和应用code查询该租户的应用状态
	 * 正常流程测试
	*/
	public void queryResStateTest() throws JsonProcessingException, IOException{

		String resCode = "NCC"; 
		String msg=TenantCenter.queryResState(tenantId,resCode);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
}
	
	
	
	@Test
	/* 查询启用了diwork并激活了NCC应用的租户列表
	 * 正常流程测
	 * ps 页面大小 
	 * pn 页号
	 * sortType 排序类型（默认不填）升序排列
	 */
	public void searchActiveNccTenantOnDiworkTest() throws JsonProcessingException, IOException{
		String ps = "2"; 
		String pn = "1";
		String sortType = "";
		String msg=TenantCenter.searchActiveNccTenantOnDiwork(ps,pn,sortType);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}


	
	@Test
	/* 根据租户ID查询U8c激活地址和变更剩余次数信息
	 * 正常流程测试
	 * 用户 18810039018  密码 yonyou11 企业帐号u8c私有云的tenantId  = "p59szm28"
	*/
	public void getU8cActiveUrlTest() throws JsonProcessingException, IOException{

		String msg=TenantCenter.getU8cActiveUrl(tenantId_u8c);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}


	
	@Test
	/* 判断某租户是否购买了NCC并已激活成功 
	 * 正常流程测试
	 * 用户 18810039018  密码 yonyou11 企业帐号 NCC 的tenantId  = "j4iy9opc"
	*/
	public void checkNCCActiveStatusTest() throws JsonProcessingException, IOException{
		String tenantId  = "j4iy9opc";
		String msg=TenantCenter.checkNCCActiveStatus(tenantId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据租户ID查询NCC集团主键 
	 * 正常流程测试
	 * 用户 18810039018  密码 yonyou11 企业帐号 NCC 的tenantId  = "j4iy9opc"
	*/
	public void getGroupPkByEnterAccIdTest() throws JsonProcessingException, IOException{
		
		//主企业账号未绑定集团
		String tenantId  = "j4iy9opc";
		String msg=TenantCenter.getGroupPkByEnterAccId(tenantId);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("主企业账号未绑定集团"));

		//绑定集团，胜利给的测试数据
		String tenantId1  = "th9f5w3u";
		String msg1=TenantCenter.getGroupPkByEnterAccId(tenantId1);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("groupPk").asText().equals("0001N91000000000013R"));


	}
	
	@Test
	/* 根据企业代码更新租户信息
	 * 正常流程测试
	*/
	public void updateTenantByCodeTest() throws JsonProcessingException, IOException{
		
		//用户 stt2018092701@test1988.com 密码 yonyou11
		//企业帐号bb
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
		String msg=TenantCenter.updateTenantByCode(params);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("保存成功"));
		
		//还原数据
		params.put("tenantName", "bb");
		params.put("tenantCode", "stt2018092701bb");
		params.put("tenantAddress", "什么地址丫丫");
		params.put("tenantTel", "18811112222");
		params.put("tenantEmail", "suntt@yonyou.com");
		params.put("tenantFullname", "幸福大家庭公司");
		params.put("tenantArea", "北京市/市辖区/昌平区");
		params.put("tenantOfficalWeb", "https://www.jd.com");
		params.put("tenantIndustry", "采矿业");
		String msg1=TenantCenter.updateTenantByCode(params);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("保存成功"));
	}

	
	
	
	@Test
	/* 根据租户主键查询当前租户下所有的用户的主键  
	 * 正常流程测试
	 * 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号cc的编码是stt2018092701cc
	 * 下面有两个用户 stt2018092701@test1988.com  stt2018092702@test1988.com
	*/
	public void getUserIdsByTenantIdTest() throws JsonProcessingException, IOException{
		
		//根据编码获取租户id
		String msg=TenantCenter.getTenantByTenantCode("stt2018092701cc");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String tenantId=node.get("tenant").get("tenantId").asText();
		
		//本测试方法
		String msg1=UserCenter.getUserIdsByTenantId(tenantId);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//获取用户id，用户校验本测试方法的结果
		String userName = "stt2018092701@test1988.com"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String userName1 = "stt2018092702@test1988.com"; 
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		Assert.assertTrue(node1.get("userIds").get(0).asText().equals(userId));
		Assert.assertTrue(node1.get("userIds").get(1).asText().equals(userId1));
			
	}
	
	
	
	
	@Test
	/* 根据类型查询应用
	 * 正常流程测试
	 * 001 云应用  008 nc、ncc、u8c
	*/
	public void queryAppsTest() throws JsonProcessingException, IOException{
		String msg=TenantCenter.queryApps("008");	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 根据应用编码及租户关键字搜索租户
	 * 正常流程测试
	 * @param resCode 应用的编码
	 * @param ps      一页的条数
	 * @param pn   页码
	 * @param searchcode 搜索的关键字
	 * @param sortType  排序类型 auto    name
	 * 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号stt520aa、stt520bb有u8，stt520cc没有u8
	*/
	public void PageTenantsTest() throws JsonProcessingException, IOException{
		//一页显示6个数据，第一页。所有两个数据都能查出来
		String resCode="u8";
		String ps="6";
		String pn="1";
		String searchcode="stt520";
		String sortType="";
		String msg=TenantCenter.PageTenants(resCode,ps,pn,searchcode,sortType);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("tenants").get("content").get(0).get("tenantName").asText().equals("stt520bb"));
		Assert.assertTrue(node.get("tenants").get("content").get(1).get("tenantName").asText().equals("stt520aa"));

		//一页显示1条数据，显示第二页
		String resCode1="u8";
		String ps1="1";
		String pn1="2";
		String searchcode1="stt520";
		String sortType1="";
		String msg1=TenantCenter.PageTenants(resCode1,ps1,pn1,searchcode1,sortType1);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("tenants").get("content").get(0).get("tenantName").asText().equals("stt520aa"));

	}
	
	@Test
	/*
	 * 新增管理员信息
	 * 正常测试
	 * 用户 stt2018092701@test1988.com 密码 yonyou11
	 * 企业帐号dd的tenantCode是stt2018092701dd
	 */
	public void addAdminTest() throws JsonProcessingException, IOException{
		
		//根据编码获取租户id
		String msg=TenantCenter.getTenantByTenantCode("stt2018092701dd");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		String tenantId=node.get("tenant").get("tenantId").asText();
		//为了用户不存在，使用当前日期时间
		SimpleDateFormat date =new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String t =date.format(new Date());
		//本接口测试
		Map<String, String> params = new HashMap<String, String>();
		params.put("userCode", t+"sttAdmin001");
		params.put("userName", t+"stt管理员001");
		params.put("userEmail", t+"@test1988.com");
		params.put("tenantId", tenantId);		
		String msg1=TenantCenter.addAdmin(params);
		System.out.println(msg1);
		JsonNode node1=mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt()==1);
		Assert.assertTrue(node1.get("msg").asText().equals("注册成功"));
	}
	
	
	
	
	@Test
		public void addAndActiveTest() {
//			String addAndActive = TenantCenter.addAndActive("kely8ohi", "pm_cloud", "ontrail", "2018-09-05 20:58:42");
			String addAndActive = TenantCenter.addAndActive("l5v7r7ts", "pm_cloud", "ontrail", "2018-09-05 20:58:42");

			System.out.println(addAndActive);
		}
	

    @Test
	/*
     * 根据账号判断是否是管理员
     * 正常流程测试
     */
   	public void isAdminNewTest() throws JsonProcessingException, IOException{
    	

//    	//不是管理员
		String userName = "18611286701"; 
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
       	String msg  = UserCenter.isAdminNew(tenantId_aa,userId);
       	System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("flag").asInt() == 0);
		
		//添加用户，设置成管理员
		String userName1 = "18810039018"; 
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
       	String msg1  = UserCenter.isAdminNew(tenantId_aa,userId1);
       	System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("flag").asInt() == 1);
		
		//企业帐号的创建者，自动就是管理员
		String userName2 = "stt2018092701@test1988.com"; 
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
		String msg2  = UserCenter.isAdminNew(tenantId_aa,userId2);
       	System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
		Assert.assertTrue(node2.get("flag").asInt() == 1);
   	}
	
    
	@Test
	/* 将用户加入指定租户 
	 * 正常流程测试
	 *  userType(1为租户管理员，3为普通用户)
	*/
	public void addToTenantTest() throws JsonProcessingException, IOException{

		//添加管理员
		int  userType  = 1;
		String userName1 = "suntt1026@126.com"; 
		String userId1 = UserCenterUtil.getUserIdByLoginName(userName1);
		String userName2 = "18611286701"; 
		String userId2 = UserCenterUtil.getUserIdByLoginName(userName2);
		String [] pks  = {userId1,userId2};
		String msg=UserCenter.addToTenant(tenantId,userType,pks);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
		//添加普通用户
		int  userType1  = 3;
		String userName11 = "stt2018112801@test1988.com"; 
		String userId11 = UserCenterUtil.getUserIdByLoginName(userName11);
		String [] pks1  = {userId11};
		String msg1=UserCenter.addToTenant(tenantId,userType1,pks1);	
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		
		//添加100个用户
		int  userType2  = 3;
		String userName21;
		String userId21;
		String [] pks2  = new String [100];
		for(int i=0;i<10;i++){
			userName21="1860000000"+i;
			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
			pks2[i]  = userId21;
		}
		for(int i=10;i<100;i++){
			userName21="186000000"+i;
			userId21 = UserCenterUtil.getUserIdByLoginName(userName21);
			pks2[i]  = userId21;
		}
		String msg2=UserCenter.addToTenant(tenantId,userType2,pks2);	
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}

	
	@Test
	/* 云应用停用接口
	 * 正常流程测试
	 * 用户 18810039018  密码 yonyou11 企业帐号 stt05 的tenantId  = "bdv029uh"
	 * 友零售 的应用编码是retail
	 * diwork 的应用编码是diwork
	 * 执行这个接口，StringBathOpenApp，让应用是开通的,然后执行云应用停用接口，能看到应用状态是停用。
	 * 再执行云应用启用接口，应用状态是试用中
	*/
	public void disableAppTest() throws JsonProcessingException, IOException{
	
		//执行本测试用例前，需要先执行这个接口。让应用时开通的，使用中的。
		String resCode = "ssc_performance"; 
		String resluts = TenantCenter.StringBathOpenApp(
				"[{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ssc_performance\",\"tenantId\":\""+tenantId_aa+"\",\"endDate\":\"2020-11-01 01:10:00\"},{\"beginDate\":\"2016-05-01 12:00:00\",\"resCode\":\"uorder\",\"tenantId\":\"v80a9ocy\",\"endDate\":\"2018-01-01 01:10:00\"}]"
				);
		//停用接口
		String msg=TenantCenter.disableApp(tenantId_aa, resCode);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		
	}
	
	@Test
	/* 云应用启用接口
	 * 正常流程测试
	 * 共享服务绩效看板  的应用编码是ssc_performance
	 * 云应用停用接口
	 * 再执行云应用启用接口，应用状态是试用中
	*/
	public void enableAppTest() throws JsonProcessingException, IOException{

		String resCode = "ssc_performance"; 
		String msg=TenantCenter.enableApp(tenantId_aa,resCode);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}
	
	
	@Test
	/* 应用下移除用户
	 * 正常流程测试
	 * 用户 18810039018  密码 yonyou11 企业帐号 stt05 的tenantId  = "bdv029uh"
	 * 友零售 的应用编码是retail
	 * 用户 18810039018  密码 yonyou11 企业帐号 717线上空间 的tenantId  = "o32y4cs3"
	 * 云表单 的应用编码是iform
	*/
	public void removeUsersFromAppsTest() throws JsonProcessingException, IOException{
		//idtest环境
//		String tenantId  = "bdv029uh";
//		String resCode = "diwork"; 
		
//		//daily环境
//		String tenantId  = "u2okte6b";
//		String resCode = "NCC"; 
		
		//euc环境
//		String tenantId  = "w6ehuyxs";
//		String resCode = "pm_cloud"; 
//		String tenantId  = "o32y4cs3";
		String resCode = "u8"; 
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
		
		//应用下移除用户
		String msg=UserCenter.removeUsersFromApps(tenantId_u8c,resCode,pks2);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
//		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("message").asText().equals("删除成功\r\n"));
		
		//还原数据，把这10个用户添加上，以便下次执行应用下移除用户
		String msg2=UserCenter.addToTenant(tenantId,userType,pks2);	
		System.out.println(msg2);

		
	}
	
	@Test
	/* 解除租户和用户之间的关联
	 * 正常流程测试
	*/
	public void removeFromTenantTest() throws JsonProcessingException, IOException{


		int  userType  = 3;		
		//100个用户的ID
		String userName;
		String userId;
		String [] pks2  = new String [100];
		for(int i=0;i<10;i++){
			userName="1860000000"+i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i]  = userId;
		}
		for(int i=10;i<100;i++){
			userName="186000000"+i;
			userId = UserCenterUtil.getUserIdByLoginName(userName);
			pks2[i]  = userId;
		}
		//解除租户和用户之间的关联
		String msg=UserCenter.removeFromTenant(tenantId_u8c,pks2);	
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("msg").asText().equals("解除关联成功"));
		
		//还原数据，把这100个用户添加上，以便下次执行应用下移除用户
		String msg2=UserCenter.addToTenant(tenantId,userType,pks2);	
		System.out.println(msg2);
		
	}

	
	
	@Test
	/* 开通应用
	 * 正常流程测试
	 * stt08:vik2b18p
	 * "resCode\":\"ipu\"，需要sdk.properties中client.credential.path=ipu.properties 
	 * ipu.properties是通过yht-manager里系统--》应用配置--》搜索ipu--》进入详情--》下载密钥
	 * 可以把里面的内容粘到uculture.properties里，因为现在sdk.properties中client.credential.path=uculture.properties
	 * uculture.properties里的username=的值是什么，就能开通什么应用。
	 * username=app-manager时，能开通所有应用
	 */
	public void StringBathOpenAppTest() throws JsonProcessingException, IOException{

		String openStr = "[{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"retail\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2020-1-2 01:10:00\"},"   
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ssc_performance\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2023-6-12 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"pm_cloud\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-16 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"xfs\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-16 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu_datang\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-9 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-8 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"ipu_aoyang\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-6 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"uspace\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-5 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"iform\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-3 01:10:00\"},"
   + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"yonyoueinvoice\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2024-8-1 01:10:00\"},"
    + "{\"beginDate\":\"2015-12-10 12:00:00\",\"resCode\":\"diwork\",\"tenantId\":\""+tenantId_stt08+"\",\"endDate\":\"2025-9-18 01:10:00\"}]"; 
		
		String msg=TenantCenter.StringBathOpenApp(openStr);
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
		Assert.assertTrue(node.get("success").get(9).get("resCode").asText().equals("yonyoueinvoice"));	
		Assert.assertTrue(node.get("success").get(10).get("resCode").asText().equals("diwork"));	
	
	}
		
}