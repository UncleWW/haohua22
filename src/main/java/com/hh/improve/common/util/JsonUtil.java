package com.hh.improve.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class JsonUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	
	private static ObjectMapper om = new ObjectMapper();

	private JsonUtil() {
	}

	public ObjectMapper getInstance() {
		return om;
	}

	/**
	 * 对象转json
	 * 
	 * @param obj
	 * @return json
	 */
	public static String objectToJson(Object obj) {
		Assert.notNull(obj, "Param is not null");
		try {
			return om.writeValueAsString(obj);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * json 转javaBean
	 * 
	 * @param json
	 * @param bean
	 * @return
	 */
	public static <T> T toJavaBean(String json, Class<T> bean) {
		Assert.notNull(json, "Param is not null");
		try {
			return om.readValue(json, bean);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * json数组转List
	 * 
	 * @param json
	 * @param typeReference
	 * @return
	 */
	public static <T> List<T> toJavaBeanList(String json, TypeReference<List<T>> typeReference) {
		Assert.notNull(json, "Param is not null");
		try {
			return om.readValue(json, typeReference);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * json字符串转Map
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<Object, T> jsonToMap(String json) {

		Assert.notNull(json, "Param is not null");
		try {
			return om.readValue(json, Map.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Map转json字符串
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<Object, Object> map) {

		Assert.notNull(map, "Param is not null");
		try {
			return om.writeValueAsString(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public static String parameterMapToJson(Map<String, String[]> map) {

		Assert.notNull(map, "Param is not null");
		try {
			return om.writeValueAsString(map);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
