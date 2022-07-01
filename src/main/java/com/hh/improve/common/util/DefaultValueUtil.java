package com.hh.improve.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DefaultValueUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(DefaultValueUtil.class);

	/**
	 * 当置为空时处理成0
	 * @param obj
	 * @return
	 */
	public static BigDecimal bigDecimal(BigDecimal obj) {
		if(obj == null){
			return new BigDecimal(0);
		}
		return obj;
	}
}
