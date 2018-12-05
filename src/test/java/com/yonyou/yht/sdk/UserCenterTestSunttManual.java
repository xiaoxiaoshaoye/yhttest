package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.yht.sdkutils.PropertyUtil;

// caojj1@yonyou.com b5298ade-5c7e-4880-8521-f2542c2a6dab
// daixd@yonyou.com 906c8e75-2da4-473f-b14d-4ed8acaaab4f

public class UserCenterTestSunttManual {
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
	 * SDK只读方法增加超时重试机制 需要在sdk.properties里设置 yht.connect.timeout=5
	 * yht.api.get.retry=true
	 * yht.user.base.url=https://www.dsafsadfsdafsdafsadfsdaf.com
	 * yht.user.base.url = http://172.20.14.199:8086/rest 分别用户域名和不存在的IP
	 * 这个url是随便写的，就是为了找不到 这样执行下面方法的时候，控制台会报错，并且会5秒执行一次 20170920
	 * 
	 */
	public void retryTest() {
		String msg = UserCenter.getUserAvatar("8562c249-0b63-449c-a687-7cfa2ddcab7f");
		System.out.println(msg);

	}

	@Test
	/*
	 * 非用户发送手机或邮箱验证码 正常情况的测试
	 * 在注册页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里 ts的值就是key
	 * 图像验证码的值就是code 这个用例每次只能执行一次，再次执行，只能重新获取
	 */
	public void sendcodeTest() throws JsonProcessingException, IOException {

		String contact = "18611286701";
		String type = "mobile";
		String key = "1527745192000610612";
		String code = "ERC8";

		String msg = UserCenter.sendcode(contact, type, key, code);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 验证手机验证码 正常情况的测试 一次性用例，例如通过忘记密码，给手机号发一个短信，把这个短信值给code.
	 * 
	 */
	public void validateCodeTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String code = "204098";
		String contact = userName;

		// 等待一分钟，因为上两个方法执行完，就执行了发短信、发邮件，一分钟内只能发一次
		// Thread.sleep(60000);

		// 发送手机验证码
		// String msg1 =
		// UserCenter.validateCode("mobile",userId,code,contact,"86");
		String msg1 = UserCenter.validateCode("mobile", userId, code, contact);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		Assert.assertTrue(node1.get("msg").asText().equals("短信发送成功"));

	}

	@Test
	/*
	 * 验证手机验证码(携带Ts校验) 正常情况的测试
	 * 一次性用例，code需要每次收到验证码，ts与给已有用户发送手机验证码(携带Ts校验)里传的一样就行
	 */
	public void validateCodeWithTsTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		String code = "139981";

		String msg1 = UserCenter.validateCodeWithTs(userId, code, "a");
		System.out.println(msg1);
		// JsonNode node1 = mapper.readTree(msg1);
		// Assert.assertTrue(node1.get("status").asInt()==1);

	}

	@Test
	/*
	 * 修改密码，重置密码 正常情况的测试 因为需要发送验证码，次用例每次执行都要重新获ts
	 * 在注册页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里 ts的值就是key
	 * 图像验证码的值就是code 这个用例每次只能执行一次，再次执行，只能重新获取
	 */
	public void modifypasswordTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "stt2018030101@test1988.com";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		// 旧密码
		String Password = "yonyou11";
		String shaPassword = SDKUtils.encodeUsingSHA(Password);
		String md5Password = SDKUtils.encodeUsingMD5(Password);

		// 新密码
		String Passwordnew = "yonyou11";
		String shaNewPassword = SDKUtils.encodeUsingSHA(Passwordnew);
		String md5NewPassword = SDKUtils.encodeUsingMD5(Passwordnew);

		String sysId = "market";
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyMMdd");
		String verification = matter1.format(dt);
		System.out.println(verification);

		// 先使用正确密码登录一次，让错误密码从1开始计算。
		String msg00 = UserCenter.verifyUser(userName, Password, sysId);
		System.out.println(msg00);
		JsonNode node00 = mapper.readTree(msg00);
		Assert.assertTrue(node00.get("status").asInt() == 1);

		// 估计把密码输入错误，让用户锁定
		for (int i = 0; i < 6; i++) {
			String msg0 = UserCenter.verifyUser(userName, "00", sysId);
			System.out.println(msg0);
			JsonNode node0 = mapper.readTree(msg0);
			Assert.assertTrue(node0.get("status").asInt() == 0);
			// Assert.assertTrue(node0.get("msg").asText().equals("用户名或密码错误"));
		}

		// 锁定用户登录，给友好提示
		String msg = UserCenter.verifyUser(userName, Password, sysId);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 0);
		Assert.assertTrue(node.get("msg").asText().equals("用户被锁定"));

		// 发送验证码
		String contact = "stt2018030101@test1988.com";
		String type = "email";
		String key = "153181738000081469";
		String code = "8KTL";

		String msg11 = UserCenter.sendcode(contact, type, key, code);
		System.out.println(msg11);
		JsonNode node11 = mapper.readTree(msg11);
		Assert.assertTrue(node11.get("status").asInt() == 1);

		// 获取sid
		String msg1 = UserCenter.validateCode("email", userId, verification, "");
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String sid = node1.get("sid").asText();
		System.out.println(sid);

		// 修改密码
		String msg2 = UserCenter.modifyPassword(userId, shaPassword, md5Password, shaNewPassword, md5NewPassword, sid);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);

		// 登录
		String msg3 = UserCenter.verifyUser(userName, Passwordnew, sysId);
		System.out.println(msg3);
		JsonNode node3 = mapper.readTree(msg3);
		Assert.assertTrue(node3.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 单点登出 正常情况的测试
	 * tip的值yht-manager_是固定的，后门的ST-36-9qIaHTUkU4tg7kEgK5fU-cas01.example.
	 * org是用F12获取的 开发在应用配置里系统名称“yht-manager”,其他信息是selfendpoint=true
	 * 使用idtest的后台管理的地址http://172.20.14.138:8880/yht-manager/ 登录
	 * 用F12，在network里，queryRole.do --> XHR --> Cookies-->
	 * Name是yht_username对应的Value的值，复制出来，只要前面.org部分
	 */
	public void logoutWithTidTest() throws JsonProcessingException, IOException, InterruptedException {

		String tid = "yht-manager_ST-36-9qIaHTUkU4tg7kEgK5fU-cas01.example.org";
		String msg1 = UserCenter.logoutWithTid(tid);
		System.out.println(msg1);
		JsonNode node1 = mapper.readTree(msg1);
		// Assert.assertTrue(node1.get("status").asInt()==1);
	}

	// @Test
	// /*
	// * 快捷发送短信验证码
	// * 正常情况的测试
	// * 友文化特有，在接口文档里不显示
	// * sdk.properties里改成client.credential.path=uculture.properties
	// * 原值是client.credential.path=authfile_yht.txt
	// * 同时把本地对应的authfile_yht.txt文件备份到其他地方，把uculture.properties放入即可
	// */
	// public void simpleSendCodeTest1() throws JsonProcessingException,
	// IOException{
	//
	// String jsonStr ="{\"mobile\":\"18810039018\", \"sysid\":\"uculture\"}";
	// String msg = UserCenter.simpleSendCode(jsonStr);
	// System.out.println(msg);
	// JsonNode node = mapper.readTree(msg);
	// Assert.assertTrue(node.get("status").asInt()==1);
	// }

	@Test
	/*
	 * 绑定手机时发短信不用图形校验码 正常情况的测试 友文化特有，在接口文档里不显示
	 * sdk.properties里改成client.credential.path=uculture.properties
	 * 原值是client.credential.path=authfile_yht.txt
	 * 同时把本地对应的authfile_yht.txt文件备份到其他地方，把uculture.properties放入即可
	 */
	public void simpleSendCodeTest() throws JsonProcessingException, IOException {
		String jsonStr = "{\"mobile\":\"18810039018\", \"sysid\":\"uculture\"}";
		String msg = UserCenter.simpleSendCode(jsonStr);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
	}

	@Test
	/*
	 * 使用ukey签名token登录 正常情况的测试 data
	 * 是getUkeyRandomOrAccessToken执行结果status为2时的值，即ukey用户才是2 sign
	 * 是前台接口返回的结果，下面是接口测试代码的代码。 var sign =
	 * window.YHTTool.sign("7437431d-2412-4b0f-b26d-0607beb3a948"); alert(sign);
	 * window.console && console.log(sign);
	 */
	public void uKeyLoginTest() throws JsonProcessingException, IOException {

		String msg = UserCenter.uKeyLogin("251af43c-d42f-4d0f-a509-3eb7a965518b",
				"Lliav+IaVlY5CzgO125jfZ+P17rLWC766cZBFkol7z+Q5BHpBhgUeB5kB8J529jWL6OOjYWT+WUmkBlkjtXshmXHhCrcdqOOIMLHPYiYAjPkY1wTryhTbBAL0/GZckrHc3+zbCrUzS3HtxKNs6U3FxBS1i8VAA74rpLe8+Cufz/g4dgJxWXZs36DQFxzrcdNoY3MbZ4U+PxeGsTo12QBW+y9LEJb/Q3n17j71oRwdrUIt8u+9reRid9zLZduCEBrb8HvRTmUji9d3QepvI9rsXALTZ+qgD3tDINiOM9ZcRXw6U/IoKfiies2gzZXUBM6q7hPYjhiv483mzwf6FDdAw==",
				true);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);

	}

	@Test
	/*
	 * 解密Ukey加密的数据 正常情况的测试 encryptedStr
	 * 这个是前台加密方法：window.YHTTool.encrypt(data)，返回密文 用hfs.exe工具
	 * ，加入yhtsec_test-suntt.html，在浏览器输入yhtsec_test-suntt.html，用户F12看 这个html里 var
	 * s = window.YHTTool.encrypt("123"); alert(s); window.console &&
	 * console.log(s);
	 */
	public void decryptTest() throws JsonProcessingException, IOException {
		String encryptedStr = "24da29af05c912748ef9a74558046c50P0R/8XI7FQyPSzWo0Bm39VdEq+m3ot7vrl62/c1ND7EJN7KmwBHtgJgLRGCR6gVweimzyY3Jzy6PlnOZKjCFbvEHhh0NvhCovsQpawQ2EjeIHbdVAHmpDCxVd4thKtACE5oghAEzO59IlLBVFHFDZepGLEqNRGNz1QP0D64EYq8=";
		String msg = UserCenter.decrypt(encryptedStr);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		Assert.assertTrue(node.get("data").asText().equals("123"));
	}

	@Test
	/*
	 * 根据后端临时token获取后端登录Token 正常情况的测试 btmpToken 让开发帮生成,这个值只能用一次 此用例为一次性用例
	 */
	public void getBackSsoTokenByBTmpTokenTest() throws JsonProcessingException, IOException {
		String btmpToken = "22cf91af-e1b7-46f1-a761-ee6e4294ea34";
		String msg = UserCenter.getBackSsoTokenByBTmpToken(btmpToken);
		System.out.println(msg);
		JsonNode node = mapper.readTree(msg);
		Assert.assertTrue(node.get("status").asInt() == 1);
		String accessToken = node.get("data").get("access_token").asText();
		String msg1 = UserCenter.getUserByToken("a33ec817-cc52-4a37-9a29-be604af51b36");
		System.out.println(msg1);

	}

	@Test
	/*
	 * 检测AccessToken是否过期 正常情况的测试
	 */
	public void isAccessTokenValidTest() throws JsonProcessingException, IOException, InterruptedException {

		// 获取access_token、refresh_token
		String msg = UserCenter.generateAccessToken("stt2017101701@chacuo.net", "yonyou11", false);
		System.out.println("获取access_token、refresh_token:" + msg);
		JsonNode node1 = mapper.readTree(msg);
		Assert.assertTrue(node1.get("status").asInt() == 1);
		String accessToken = node1.get("data").get("access_token").asText();

		boolean msg1 = UserCenter.isAccessTokenValid(accessToken);
		System.out.println(msg1);
		Assert.assertTrue(msg1);

		// 销毁accessToken
		String msg2 = UserCenter.destroyAccessToken(accessToken);
		System.out.println(msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);

		// 等1分钟，此时access_token失效
		Thread.sleep(60000);
		String a = UserCenter.getUserByToken("7c07e50c-eb91-4c0a-8be3-274bdfad88da");
		System.out.println(a);
		boolean msg3 = UserCenter.isAccessTokenValid(accessToken);
		System.out.println(msg3);
		Assert.assertFalse(msg3);

	}

	@Test
	public void isAccessTokenValidTest2() throws JsonProcessingException, IOException, InterruptedException {

		String a = UserCenter.getUserByToken("7c07e50c-eb91-4c0a-8be3-274bdfad88da");
		System.out.println(a);
	}

	@Test
	/*
	 * 快捷注册登录接口 正常情况的测试 在注册页面，输入邮箱，F12，点击【图像验证码】，此时Network，左侧Name下文件，右侧Headers里
	 * ts的值就是key 图像验证码的值就是code 这个用例每次只能执行一次，再次执行，只能重新获取
	 */
	public void loginOrRegisterTest() {

		String contact = "stt2018053105@test1988.com";
		int type = 1;

		// 使用测试账号，所以可以用当前日期是收到的短信验证码
		SimpleDateFormat date = new SimpleDateFormat("yyMMdd");
		String validateCode = date.format(new Date());

		// 获取验证码
		String t = "email";
		String key = "1527772110000983059";
		String code = "ap66";
		String msg = UserCenter.sendcode(contact, t, key, code);

		String msg1 = UserCenter.loginOrRegister(contact, validateCode, type);
		System.out.println(msg1);

	}

}
