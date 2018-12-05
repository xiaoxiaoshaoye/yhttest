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

public class EnterpriseCenterExternalSuntt {

	ObjectMapper mapper = new ObjectMapper();

	// @Before
	public void beforeEuc() {
		System.setProperty("yht.load.order", "2");
		String path = "eucsdk.properties";
		String authfile = "euc.properties";
		String oauthfile = "oauth2_eucCloud.properties";
		Properties p = PropertyUtil.loadFile(path);
		Properties p2 = PropertyUtil.loadFile(authfile);
		Properties p3 = PropertyUtil.loadFile(oauthfile);
		setEnv(p);
		setEnv(p2);
		setEnv(p3);
		System.out.println("#############finished before");
	}

	@Before
	public void beforeIdtest() {
		System.setProperty("yht.load.order", "2");
		String path = "idtestsdk.properties";
		// String authfile="market.properties";
		String authfile = "uculture.properties";
		String oauthfile = "oauth2_dd.properties";
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
		String name = null;
		while (e.hasMoreElements()) {
			name = (String) e.nextElement();
			System.setProperty(name, p.getProperty(name));
		}
	}

	@Test
	/*
	 * 根据企业名获取企业基本信息 正常情况的测试
	 */
	public void getEnterpriseByNameTest() {
		// 用户stt2017092801@chacuo.net创建的a111111这个企业的id是f33c078b-7c17-45dd-949e-3c7f734e1618
		String enterpriseName = "a111111";
		String msg = EnterpriseCenter.getEnterpriseByName(enterpriseName);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("enterprise").get("id").asText().equals("f33c078b-7c17-45dd-949e-3c7f734e1618"));
		Assert.assertTrue(node.get("enterprise").get("name").asText().equals("a111111"));
		Assert.assertTrue(node.get("enterprise").get("type").asText().equals("个人企业"));
		Assert.assertTrue(node.get("enterprise").get("address").asText().equals("中国-河北-石家庄-bb"));
		Assert.assertTrue(node.get("enterprise").get("trades").asText().equals("采矿业"));
	}

	@Test
	/*
	 * 根据企业ID获取企业信息 正常情况的测试
	 */
	public void getEnterpriseByIdTest() {
		// 用户stt2017092801@chacuo.net创建的a111111这个企业的id是f33c078b-7c17-45dd-949e-3c7f734e1618
		String enterpriseId = "f33c078b-7c17-45dd-949e-3c7f734e1618";
		String msg = EnterpriseCenter.getEnterpriseById(enterpriseId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("enterprise").get("id").asText().equals("f33c078b-7c17-45dd-949e-3c7f734e1618"));
		Assert.assertTrue(node.get("enterprise").get("name").asText().equals("a111111"));
		Assert.assertTrue(node.get("enterprise").get("type").asText().equals("个人企业"));
		Assert.assertTrue(node.get("enterprise").get("address").asText().equals("中国-河北-石家庄-bb"));
		Assert.assertTrue(node.get("enterprise").get("trades").asText().equals("采矿业"));

	}

	@Test
	/*
	 * 根据企业帐号ID获取企业信息 正常情况的测试
	 */
	public void getEnterpriseByEnterAccountIdTest() {
		// 用户18611286701创建的"已绑定"这个企业帐号的id是koe0t8ks
		String tenantId = "koe0t8ks";
		String msg = EnterpriseCenter.getEnterpriseByEnterAccountId(tenantId);
		System.out.println(msg);
		JsonNode node = Utils.getJson(mapper, msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

		// 用户18611286701创建的"未绑定"这个企业帐号的id是exbf58f7
		String tenantId1 = "exbf58f7";
		String msg1 = EnterpriseCenter.getEnterpriseByEnterAccountId(tenantId1);
		System.out.println(msg1);
		JsonNode node1 = Utils.getJson(mapper, msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("该企业帐号尚未绑定企业"));

	}

}
