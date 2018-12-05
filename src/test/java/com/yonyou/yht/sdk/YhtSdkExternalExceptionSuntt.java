package com.yonyou.yht.sdk;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.ex.sdk.NCUserCenter;
import com.yonyou.yht.ex.sdk.UserCenter;
import com.yonyou.yht.sdkutils.PropertyUtil;

public class YhtSdkExternalExceptionSuntt {

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
	 * 对外融合SDK提供基于手机号查询用户功能 异常情况的测试 外部接口是getUserByMobile，内部接口是getUserByCode
	 * 执行这个用例，需要把pom.xml里修改下 <dependency> <groupId>com.yonyou.yht</groupId>
	 * <artifactId>yht-sdk-external</artifactId> <version>1.0-SNAPSHOT</version>
	 * </dependency>
	 */

	public void getUserByMobileExceptionTest() throws JsonProcessingException, IOException {

		// 参数为空
		String msg = UserCenter.getUserByMobile("");
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("mobile 不能为空"));

		// 参数是随便输入的值
		String msg1 = UserCenter.getUserByMobile("随便乱输入的内容哈哈哈哈哈");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("未找到该用户"));

		// 参数是null
		String msg2 = UserCenter.getUserByMobile(null);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 0);
		Assert.assertTrue(node2.get("msg").asText().equals("mobile 不能为空"));

		// 参数是随便输入的值
		String msg3 = UserCenter.getUserByMobile("15210040725");
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);
		Assert.assertTrue(node3.get("msg").asText().equals("未找到该用户"));
	}

	@Test
	/*
	 * 根据手机号或邮箱及验证码绑定NC用户并生成RefreshToken 异常情况的测试
	 *
	 */

	public void genRefreshTokenExceptionTest() throws JsonProcessingException, IOException {

		// 参数为null
		String msg = NCUserCenter.genRefreshToken(null, null, // email or mobile
				null, // 验证码；
				null, // 租户id
				null, // ncc_USERID
				null);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("参数不能为空"));

		// 参数为空
		String msg1 = NCUserCenter.genRefreshToken("", "", // email or mobile
				"", // 验证码；
				"", // 租户id
				"", // ncc_USERID
				"");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 0);
		Assert.assertTrue(node1.get("msg").asText().equals("参数不能为空"));
		// //参数是随便输入的值
		// String msg1 = NCUserCenter.genRefreshToken("随便乱输入的内容哈哈哈哈哈");
		// System.out.println(msg1);
		// JsonNode node1 = mapper.readTree(msg1);
		// Assert.assertTrue(node1.get("status").asInt() == 1);
		// Assert.assertTrue(node1.get("msg").asText().equals("用户不存在"));
		//
		// //参数是null
		// String msg2 = NCUserCenter.genRefreshToken(null);
		// System.out.println(msg2);
		// JsonNode node2 = mapper.readTree(msg2);
		// Assert.assertTrue(node2.get("status").asInt() == 0);
		// Assert.assertTrue(node2.get("msg").asText().equals("mobile 不能为空"));
		//
		// //参数是随便输入的值
		// String msg3 = NCUserCenter.genRefreshToken("15210040725");
		// System.out.println(msg3);
		// JsonNode node3 = mapper.readTree(msg3);
		// Assert.assertTrue(node3.get("status").asInt() == 1);
		// Assert.assertTrue(node3.get("msg").asText().equals("用户不存在"));
	}

}
