package com.yonyou.yzt.sdk;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64Encoder;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.yzt.domain.Contract;

import net.sf.json.JSONObject;

public class SignApiTest {

	// private final String apikey =
	// "TiQyUBtHE_E2V99lGOZFZflbpkSL2zUBQ3DmGsfZtklQKMbYBfzc2blugdh5fsXtx9CWyLVGVj8XfDBIafxQgQ";//
	// Api
	// private final String securityKey =
	// "BH-mgjRl8pg8gjjXgPNwYmmTWlmkzPn5kCe0jWNrrXjkpn2X6ToFZijqo0zjQbeZK2TPSN8nllRV1Ck3Ntt4Bw";//
	// Security

	private String apikey = "66iMgqZnZ8PkqsJ3tl8ja1Kr4f4gBS68qnT6Iv3DCw9dyTAHtxvxJ65RI-V3AggAX4laZL0mhyOcRfEobjXvkw";
	private String securityKey = "H2eAq_Bx4VlppnMG63IVZsiMvPB2Fjkoa_H9xvHWzDPJxDa9H2HNGHOj2GsRjauYsfW_k3TYtqgPkiSO8jJX4Q";

	private final String host = "https://yzt.yonyoucloud.com";
	// private final String host = "http://localhost:8080";

	private String signSecrt(String securityKey, String reqString)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA1");
		final SecretKeySpec keySpec = new SecretKeySpec(securityKey.getBytes(), "HmacSHA1");
		mac.init(keySpec);
		mac.update(reqString.toLowerCase().getBytes());

		final byte[] encryptedBytes = mac.doFinal();
		final String computedSignature = org.apache.commons.codec.binary.Base64.encodeBase64String(encryptedBytes);
		System.out.println("dd:" + computedSignature);
		return computedSignature;
	}

	@Test
	public void testCreateSeal() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		String host = "http://localhost:8080";
		String uri = "/api/sign/seals";

		StringBuffer unsignedRequestSB = new StringBuffer(uri + "?");
		TreeMap<String, String> requestMap = new TreeMap<String, String>();
		requestMap.put("apikey", apikey);
		for (Entry<String, String> entry : requestMap.entrySet()) {
			unsignedRequestSB.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		String unsignedRequest = unsignedRequestSB.substring(0, unsignedRequestSB.length() - 1);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("binaryData", new String(new Base64Encoder().encode(getFile("D:/ssl/test.png"))));
		dataMap.put("binaryDataContentType", "image/png");
		dataMap.put("companyId", "2e456ea6-b8dc-4333-91bd-e3957acb0209");
		dataMap.put("tanentId", "2e456ea6-b8dc-4333-91bd-e3957acb0209");
		dataMap.put("userId", "e35ac485-5661-4d0e-b259-10a6fad6046a");
		dataMap.put("orgCode", "91110114697689054Y");
		dataMap.put("sealDesc", "xxxxDesc");
		dataMap.put("sealName", "testName");
		// ObjectMapper om = new ObjectMapper();
		String json = new JSONObject(dataMap).toString();
		String computedSignature = signSecrt(securityKey, unsignedRequest + json);
		System.out.println("sign：" + computedSignature);
		HttpHeaders headers = new HttpHeaders();
		headers.add("signature", computedSignature);// 放入签名
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity httpEntity = new HttpEntity(json, headers);
		ResponseEntity<String> resp = restTemplate.postForEntity(host + unsignedRequest, httpEntity, String.class);
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getBody());
	}

	@Test
	public void testListSeal() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		// String host = "http://localhost:8080";
		String uri = "/api/sign/seals";
		StringBuffer unsignedRequestSB = new StringBuffer(uri + "?");

		TreeMap<String, String> requestMap = new TreeMap<String, String>();
		LinkedMultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		requestMap.put("apikey", apikey);
		requestMap.put("userId", "44bc320a-4269-41cf-bc3c-c6c3208e7153");
		for (Entry<String, String> entry : requestMap.entrySet()) {
			unsignedRequestSB.append(entry.getKey() + "=" + entry.getValue() + "&");
			postData.add(entry.getKey(), entry.getValue());
		}
		String unsignedRequest = unsignedRequestSB.substring(0, unsignedRequestSB.length() - 1);

		String computedSignature = signSecrt(securityKey, unsignedRequest);
		System.out.println("sign：" + computedSignature);
		HttpHeaders headers = new HttpHeaders();
		headers.add("signature", computedSignature);// 放入签名
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity httpEntity = new HttpEntity(null, headers);
		ResponseEntity<String> resp = restTemplate.exchange(host + unsignedRequest, HttpMethod.GET, httpEntity,
				String.class);
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getBody());

	}

	@Test
	public void testGrantListSeal() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		// String host = "http://localhost:8080";
		String uri = "/api/sign/grant/seals";
		StringBuffer unsignedRequestSB = new StringBuffer(uri + "?");

		TreeMap<String, String> requestMap = new TreeMap<String, String>();
		LinkedMultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		requestMap.put("apikey", apikey);
		requestMap.put("tanentId", "2e456ea6-b8dc-4333-91bd-e3957acb0209");
		// requestMap.put("userId", "a44bc320a-4269-41cf-bc3c-c6c3208e7153");
		for (Entry<String, String> entry : requestMap.entrySet()) {
			unsignedRequestSB.append(entry.getKey() + "=" + entry.getValue() + "&");
			postData.add(entry.getKey(), entry.getValue());
		}
		String unsignedRequest = unsignedRequestSB.substring(0, unsignedRequestSB.length() - 1);

		String computedSignature = signSecrt(securityKey, unsignedRequest);
		System.out.println("sign：" + computedSignature);
		HttpHeaders headers = new HttpHeaders();
		headers.add("signature", computedSignature);// 放入签名
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity httpEntity = new HttpEntity(null, headers);
		ResponseEntity<String> resp = restTemplate.exchange(host + unsignedRequest, HttpMethod.GET, httpEntity,
				String.class);
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getBody());

	}

	@Test
	public void testSignContract() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		String host = "http://localhost:8080";
		String uri = "/api/sign/contracts/sign/new";

		StringBuffer unsignedRequestSB = new StringBuffer(uri + "?");
		TreeMap<String, String> requestMap = new TreeMap<String, String>();
		requestMap.put("apikey", apikey);
		for (Entry<String, String> entry : requestMap.entrySet()) {
			unsignedRequestSB.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		String unsignedRequest = unsignedRequestSB.substring(0, unsignedRequestSB.length() - 1);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// mapContract.put("doc", new String(new
		// Base64Encoder().encode(bytesPdf)));
		dataMap.put("title", "合同123456");
		dataMap.put("binaryData", new String(new Base64Encoder().encode(getFile("D:/test.pdf"))));
		dataMap.put("binaryDataContentType", "application/pdf");
		// dataMap.put("companyId", "2eac8175-3c1e-4083-982c-bd9226a116c6");
		dataMap.put("userId", "e35ac485-5661-4d0e-b259-10a6fad6046a");
		dataMap.put("docNum", " 1528774832000193376");
		dataMap.put("docType", "pdf");
		dataMap.put("fileName", "合同123456");
		dataMap.put("validateCode", "799224");
		List<Map<String, Object>> listSignatures = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapSignature1 = new HashMap<String, Object>();

		mapSignature1.put("sealId", "daa9f21e-5590-41ee-bd6c-0dc2551fc7d8");
		// mapSignature1.put("sealName", "哈哈哈哈哈！");
		mapSignature1.put("page", "1");
		mapSignature1.put("positionx", "80");
		mapSignature1.put("positiony", "650");
		listSignatures.add(mapSignature1);
		dataMap.put("signatures", listSignatures);
		// dataMap.put("signatures1", mapSignature1);

		String json = new JSONObject(dataMap).toString();
		// System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Contract contract = null;
		try {
			contract = objectMapper.readValue(json, Contract.class);
			// json = new JSONObject(contract).toString();
			System.out.println(contract);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		String computedSignature = signSecrt(securityKey, unsignedRequest + json);
		System.out.println("sign：" + computedSignature);
		HttpHeaders headers = new HttpHeaders();
		headers.add("signature", computedSignature);// 放入签名
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity httpEntity = new HttpEntity(json, headers);
		ResponseEntity<String> resp = restTemplate.postForEntity(host + unsignedRequest, httpEntity, String.class);
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getBody());

	}

	private static Map<String, Integer> ENTYPE_NAME = new HashMap<String, Integer>();
	static {
		ENTYPE_NAME.put("法人企业", 1);
		ENTYPE_NAME.put("个体工商户", 2);
		ENTYPE_NAME.put("个人企业", 3);
		ENTYPE_NAME.put("其他", 4);
	}
	// @Test
	// public void getentInfo(){
	// String en =
	// EnterpriseCenter.getEnInfo("ec92b05c-2557-4b9a-ac3f-ab6b356ba3ae");
	// Enterprise result = new Enterprise();
	// try {
	// JSONObject jsonResp = new JSONObject(en);
	// int status = jsonResp.getInt("status");
	// if(status == 1){
	// JSONObject eninfo = jsonResp.getJSONObject("eninfo");
	// result.setId(eninfo.getString("id"));
	// result.setName(eninfo.getString("name"));
	// result.setType(eninfo.getString("type"));
	// result.setAgentName(eninfo.getString("legalPerson"));
	// result.setAgentPhone(eninfo.getString("contactMobile"));
	// String creditCode = eninfo.getString("creditCode");
	// String legalLicense = eninfo.getString("legalLicense");
	// if(("active").equals(eninfo.getString("state"))){
	// result.setItVaild(true);
	// }else{
	// result.setItVaild(false);
	// }
	// int type = ENTYPE_NAME.get(eninfo.getString("type") + "");//企业类型
	// switch (type) {
	// case 1:// 法人企业
	// if (StringUtils.isEmpty(creditCode)) {// 统一信用码为空则填充营业执照号
	// creditCode = eninfo.getString("legalLicenseRegisterCode");
	// }
	// legalLicense = eninfo.getString("legalLicense");
	// break;
	// case 2:// 个体工商户
	// if (StringUtils.isEmpty(creditCode)) {// 统一信用码为空则填充营业执照号
	// creditCode = eninfo.getString("personLicenseRegisterCode") + "";
	// }
	// legalLicense = eninfo.getString("personLicense") + "";
	// break;
	// case 3:// 个人企业
	// if (StringUtils.isEmpty(creditCode)) {// 统一信用码为空则填充责任人码
	// creditCode = eninfo.getString("responsePersonIDCode") + "";
	// }
	// legalLicense = eninfo.getString("responsePersonID") + "";
	// break;
	// case 4:// 其时
	// if (StringUtils.isEmpty(creditCode)) {// 统一信用码为空则填充为组织机构码
	// creditCode = eninfo.getString("organizationCertificateCode") + "";
	// }
	// legalLicense = eninfo.getString("organizationCertificate") + "";
	// break;
	// }
	// result.setType(String.valueOf(type));
	// result.setLegalLicense(legalLicense);
	// result.setCreditCode(creditCode);
	// System.out.println(result.toString());
	// }
	// } catch (JSONException e) {
	// }
	// }
	// @Test
	// public void testSign1Contract() throws InvalidKeyException,
	// NoSuchAlgorithmException, UnsupportedEncodingException {
	// RestTemplate restTemplate = new RestTemplate();
	//// String host = "http://localhost:8080";
	// String uri =
	// "/api/sign/contracts/sign/2f1dc9b7-b8ef-4c49-8600-8583e7c35e91";
	// StringBuffer unsignedRequestSB = new StringBuffer(uri + "?");
	//
	// TreeMap<String, String> requestMap = new TreeMap<String, String>();
	// LinkedMultiValueMap<String, String> postData = new
	// LinkedMultiValueMap<String, String>();
	// requestMap.put("apikey", apikey);
	// requestMap.put("userId", "a8b67db4-0cae-4f16-a3be-320b33801500");
	// requestMap.put("sealId", "a8b67db4-0cae-4f16-a3be-320b33801500");
	// for (Entry<String, String> entry : requestMap.entrySet()) {
	// unsignedRequestSB.append(entry.getKey() + "=" + entry.getValue() + "&");
	// postData.add(entry.getKey(), entry.getValue());
	// }
	// String unsignedRequest = unsignedRequestSB.substring(0,
	// unsignedRequestSB.length() - 1);
	//
	// String computedSignature = signSecrt(securityKey, unsignedRequest);
	// System.out.println("sign：" + computedSignature);
	// HttpHeaders headers = new HttpHeaders();
	// headers.add("signature", computedSignature);// 放入签名
	// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	//
	// HttpEntity httpEntity = new HttpEntity(null, headers);
	// ResponseEntity<String> resp = restTemplate.exchange(host +
	// unsignedRequest, HttpMethod.GET, httpEntity,
	// String.class);
	// System.out.println(resp.getStatusCode());
	// System.out.println(resp.getBody());
	//
	// }

	public byte[] getFile(String file) {
		InputStream is = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			is = new FileInputStream(file);
			byte[] b = new byte[1024];
			int n;
			while ((n = is.read(b)) != -1) {
				out.write(b, 0, n);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ignore) {
				}
			}
		}
		byte[] bytes = out.toByteArray();
		return bytes;
	}

}
