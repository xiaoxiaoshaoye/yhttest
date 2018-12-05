package com.yonyou.assist.sdk;

import org.junit.Test;

import com.yonyou.app.sdk.AppCenter;

public class AssistTest {

	@Test
	public void testOpen() {
		String activeCode = "ysc753a2cd2d57c435f8ea29dbb689cf33c";
		String tenantId = "ytnnrmqn";
		String resCode = "workbench";
		String startDate = "2016-01-11 15:48:43";
		String endDate = "2019-01-11 15:48:43";
		String result = AppCenter.openResCallBack(activeCode, tenantId, resCode);
		System.out.println(result);
	}

}
