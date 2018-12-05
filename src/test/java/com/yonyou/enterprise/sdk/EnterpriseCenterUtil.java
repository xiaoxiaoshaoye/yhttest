package com.yonyou.enterprise.sdk;

import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yht.entity.EnterpriseInfo;

public class EnterpriseCenterUtil {

	static ObjectMapper mapper = new ObjectMapper();

	public static EnterpriseInfo getEnterpriseInfoByName(String enterpriseName) {
//		String msg = EnterpriseCenter.search(enterpriseName);
//		System.out.println(msg);
//		Map<String, Object> m = Utils.getMap(mapper, msg);
//		@SuppressWarnings("unchecked")
//		List<Map<String, Object>> enterprises = (List<Map<String, Object>>) m
//				.get("enterprises");
//		for (Map<String, Object> enterpriseMap : enterprises) {
//			if (enterpriseMap.get("enterName").equals(enterpriseName)) {
//				EnterpriseInfo enterprise = new EnterpriseInfo();
//				enterprise.setLastTime((String) enterpriseMap.get("lastTime"));
//				enterprise.setContactMobile((String) enterpriseMap
//						.get("contactMobile"));
//				enterprise.setEnterLegalrepre((String) enterpriseMap
//						.get("enterLegalrepre"));
//				enterprise.setSuperiorEnter((String) enterpriseMap
//						.get("superiorEnter"));
//				enterprise.setEnterAddress((String) enterpriseMap
//						.get("enterAddress"));
//				enterprise.setContactEmail((String) enterpriseMap
//						.get("contactEmail"));
//				enterprise.setRegisterTime((String) enterpriseMap
//						.get("registerTime"));
//				enterprise.setContactName((String) enterpriseMap
//						.get("contactName"));
//				enterprise.setLogoPath((String) enterpriseMap.get("logoPath"));
//				enterprise
//						.setEnterType((String) enterpriseMap.get("enterType"));
//				enterprise.setIndustry((String) enterpriseMap.get("industry"));
//				enterprise.setIspartner((Boolean) enterpriseMap
//						.get("ispartner"));
//				enterprise.setEnterId((String) enterpriseMap.get("enterId"));
//				enterprise.setEnterDetailaddress((String) enterpriseMap
//						.get("enterDetailaddress"));
//				enterprise.setEnterScale((String) enterpriseMap
//						.get("enterScale"));
//				enterprise.setAuditStatus((String) enterpriseMap
//						.get("auditStatus"));
//				enterprise
//						.setEnterName((String) enterpriseMap.get("enterName"));
//				return enterprise;
//			}
//		}
		return null;
	}

	public static String getEnterpriseIdByName(String enterpriseName) {
		EnterpriseInfo enterprise = getEnterpriseInfoByName(enterpriseName);
		Assert.notNull(enterpriseName);
		return enterprise.getEnterId();
	}
}
