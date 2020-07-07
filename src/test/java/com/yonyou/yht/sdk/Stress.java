package com.yonyou.yht.sdk;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yonyou.enterprise.sdk.UserCenterUtil;

public class Stress {

	/*
	 * 根据密码获取accesstoken 正常情况的测试 文档里有链接的
	 */
	public static String generateAccessTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		// System.out.println(Thread.currentThread().getContextClassLoader().getResource("/"));

		String username = "18810039018";
		String Password = "yonyou11";
		String md5Password = SDKUtils.encodeUsingMD5(Password);
		String shaPassword = SDKUtils.encodeUsingSHA(Password);

		return UserCenter.generateAccessToken(username, md5Password, shaPassword, false);

	}

	/*
	 * 根据用户ID获取用户信息 正常流程测试
	 */
	public static String getUserByIdTest(String userName) throws JsonProcessingException, IOException {

		return UserCenter.getUserById(UserCenterUtil.getUserIdByLoginName(userName));

	}

	/*
	 * 根据登录名获取用户信息 正常情况的测试 登录名输入正确的值，分别测试code、邮箱、手机号三种情况
	 */
	public static String getUserByLoginNameTest(String loginName) {

		return UserCenter.getUserByLoginName(loginName);

	}

	public static void main(String[] args) throws JsonProcessingException, IOException, InterruptedException {

		System.out.println(getUserByIdTest("14500000001"));
	}
}
