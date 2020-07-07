package com.yonyou.tenant.sdk;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yonyou.enterprise.sdk.UserCenterUtil;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
import com.yonyou.iuap.tenant.sdk.UserCenter;

public class StressTenant {

	/*
	 * 根据账号判断是否是管理员 18810039018下企业帐号stt06 tenantId = "xrnoih4a" 18810039018是管理员
	 * 18611286701不是管理员
	 */
	public static String isAdminNewTest() throws JsonProcessingException, IOException, InterruptedException {

		String tenantId = "xrnoih4a";
		// String userName = "18810039018";
		String userName = "14500000001";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		return UserCenter.isAdminNew(tenantId, userId);
	}

	public static void main(String[] args) {
		try {
			String str = StressTenant.isAdminNewTest();
			System.out.println(str);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 根据用户id和系统code获取可以登录租户 例如：jlccstt@126.com，新建了两个租户1，租户2 两个租户都有u8，两个租户都添加用户
	 * 17779888888，这个手机号对应的邮箱是jlccstt@126.com
	 * 但租户1激活时，给jlccstt@126.com分配了许可。租户2没有激活。 这样用户id使用jlccstt@126.com，就能查出来租户1.
	 * jlccstt@126.com userId = "47624900baea"
	 */
	public static String getCanLoginTenantsTest() throws JsonProcessingException, IOException, InterruptedException {

		String userName = "18810039018";
		String userId = UserCenterUtil.getUserIdByLoginName(userName);
		return TenantCenter.getCanLoginTenants(userId, "u8");
	}

	/*
	 * 根据租户id获取租户管理员 18810039018下企业帐号stt06 tenantId = "xrnoih4a"
	 */
	public static String getTenantAdminByTenantIdTest()
			throws JsonProcessingException, IOException, InterruptedException {

		String tenantId = "xrnoih4a";
		return UserCenter.getTenantAdminByTenantId(tenantId);
	}

	/*
	 * 根据租户id获取租户信息 18810039018下企业帐号stt06 tenantId = "xrnoih4a"
	 */
	public static String getTenantByIdTest() throws JsonProcessingException, IOException, InterruptedException {

		String tenantId = "xrnoih4a";
		return TenantCenter.getTenantById(tenantId);
	}

}
