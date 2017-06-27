package com.yonyou.yht.sdk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static Random random = new Random();
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"YYYY-MM-dd_HH:mm:ss");

	public static String suffixTimeAndRandom(String s) {
		return s + "_" + sdf.format(new Date()) + "_" + random.nextInt();
	}

	public static void main(String[] args) {
		System.out.println(suffixTimeAndRandom("abc"));
	}

	public static JsonNode getJson(ObjectMapper mapper, String msg) {
		JsonNode node = null;
		try {
			node = mapper.readTree(msg);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return node;
	}

	public static <T> T getObject(ObjectMapper mapper, String msg,
			Class<T> clazz) {
		T obj = null;
		try {
			obj = mapper.readValue(msg, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return obj;
	}

	public static <T> T getObject(ObjectMapper mapper, String msg,
			TypeReference<T> ref) {
		T obj = null;
		try {
			obj = mapper.readValue(msg, ref);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return obj;
	}

	public static Map<String, Object> getMap(ObjectMapper mapper, String msg) {
		TypeReference<Map<String, Object>> ref = new TypeReference<Map<String, Object>>() {
		};
		return getObject(mapper, msg, ref);
	}

	public static List<?> getList(ObjectMapper mapper, String msg) {
		TypeReference<List<?>> ref = new TypeReference<List<?>>() {
		};
		return getObject(mapper, msg, ref);
	}

	public static <T> List<T> getList(ObjectMapper mapper, String msg,
			Class<T> clazz) {
		TypeReference<List<T>> ref = new TypeReference<List<T>>() {
		};
		return getObject(mapper, msg, ref);
	}

	public static <T> List<T> getList(ObjectMapper mapper, String msg,
			TypeReference<T> clazz) {
		TypeReference<List<T>> ref = new TypeReference<List<T>>() {
		};
		return getObject(mapper, msg, ref);
	}
	
	public static String getJsonStr(ObjectMapper mapper, Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
