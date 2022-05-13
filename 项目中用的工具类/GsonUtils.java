package com.yhl.ros.common.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @ClassName: GsonUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:18
 */
public class GsonUtils<T> {

	private static Gson gson = null;
	static {
		GsonBuilder builder = new GsonBuilder();
		gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public static String toJson(Object objec) {
		return gson.toJson(objec);
	}

	public T fromJson(String json, Type type) {
		return gson.fromJson(json, type);
	}

	public static JsonObject getJsonObject(String json) {
		return new JsonParser().parse(json).getAsJsonObject();
	}

}
