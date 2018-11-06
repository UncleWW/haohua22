package com.hh.improve.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	
	public static String DEFAULT_DATE = "yyyy-MM-dd";
	public static String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATETIME);
	}
	
	public static String formatDate(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			LOGGER.error("DateUtils.formatDate Error with " + date + " and " + format, e);
		}
		return null;
	}
	
	
	public static Date parseDoubleToDate(Double doubleTime) {
		if (null == doubleTime) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(doubleTime.longValue());
		return cal.getTime();
	}
	
	public static Date parseDate(String date) {
		return parseDate(date, DEFAULT_DATETIME);
	}
	
	public static Date parseDate(String date, String format) {
		if (date==null || date.equals("")) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(date.trim());
		} catch (ParseException e) {
			LOGGER.error("DateUtils.parseDate Error with " + date + " and " + format, e);
			e.printStackTrace();
		}
		return null;
	}
}
