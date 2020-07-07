package com.yonyou.yht.sdk;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.sdk.NCUserCenter;

public class NCUserCenterTest {
	// ncc绑定的接口，根据返回的refreshToken 调用NCUserCenter.genLoginToken
	//根据refreshToken生成友户通登录token
	//生成的token，界面上调用
	static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 根据refreshToken生成友户通登录token
	 * 生成的token，界面上调用 
	 * https://euc.yonyoucloud.com/cas/login?token=ca6b8f88c7a6dd922433ef6987cb94e6a90ac97bb26e71bfd236c6ec502451e5&service=http://www.baidu.com
	 *  http://idtest.yyuap.com/cas/login?token=ca6b8f88c7a6dd922433ef6987cb94e6a90ac97bb26e71bfd236c6ec502451e5&service=http://www.baidu.com
	 */
	//2ae6a60d17680da248e0b76cccb4bf0afadb81c5ab9fcab887104dd161906d39
	//https://u8c-user-daily.yyuap.com/cas/login?token=2ae6a60d17680da248e0b76cccb4bf0afadb81c5ab9fcab887104dd161906d39&service=http://www.baidu.com
    @Test
	public void testGenLogin(){
		String str = NCUserCenter.genLoginToken("eeb6ba02f45d83fe8fed19d1aeb416a779162834");
		System.out.println(str);
	}

	@Test
	/*
	 * 根据accesstoken获取用户信息 正常情况的测试
	 */
	public void getUserByTokenTest() throws JsonProcessingException, IOException, InterruptedException {

		
		// 本测试方法的代码
		String accessToken = "7902289678da24fdb6e53ee7addc05395c5ba1b7f2bf4ca682ce5e7a79a305fe";
		String msg2 = UserCenter.getUserByToken(accessToken);
		System.out.println(" 根据accesstoken获取用户信息 "+msg2);
		JsonNode node2 = mapper.readTree(msg2);
		Assert.assertTrue(node2.get("status").asInt() == 1);
	}
    /**
     * ncc绑定的接口，根据返回的refreshToken 调用NCUserCenter.genLoginToken
     * tenantId\ncUserId\refreshToken
     * 一个ncc用户可以绑定多个友户通用户
     * 一个
     */
    @Test
    public void testGenerRefreshToken(){
    	String contact ="test1105a@test1988.com";//test1105@test1988.com、test1105a@test1988.com
//    	String contact ="test042701@163.com";  //线上用户 test1105aa@test1988.com、test1105b@test1988.com 测试环境：test1108aa@test1988.com
    	//
    	String type="email";//
    	String code="200617";
    	String tenantId="test2003011aaTenant";//
    	String ncUserId="test202003111111";// test201911081111  test201911052222 、test201911051111
    	String nccrefreshToken="";//refreshToken2019110555
    	String countryCode = null;
    	String msg = NCUserCenter.genRefreshToken(contact, type, code, tenantId, ncUserId, nccrefreshToken, countryCode);
       System.out.println("genRefreshToken:"+msg);
    }
}
