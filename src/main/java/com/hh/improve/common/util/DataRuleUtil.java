package com.hh.improve.common.util;

public class DataRuleUtil {
	/**
	 * 最大输入多少字符
	 */
	private static final  String dataRule_MaxLength="^.{0,%s}$";
	/**
	 * 只能是英文字母或数字
	 */
	private static final  String dataRule_letterOrNumber="^[A-Za-z0-9]{0,%s}$";
	/**
	 * 只能是数字
	 */
	private static final  String dataRule_number="^[0-9]{0,%s}$";
	/**
	 * 只能是英文字母
	 */
	private static final  String dataRule_letter="^[A-Za-z]{0,%s}$";
	/**
	 * 邮箱验证
	 */
	private static final String dataRule_email = "^[\\w\\+\\-]+(\\.[\\w\\+\\-]+)*@[a-z\\d\\-]+(\\.[a-z\\d\\-]+)*\\.([a-z]{2,4})$";
	/**
	 * 整数或者小数 小数点前2-3位,小数点后0-2位
	 */
	private static final String dataRule_number2 = "^[0-9]{2,3}+\\.{0,1}[0-9]{0,2}$";
	/**
	 * 手机号验证
	 */
	private static final String data_mobile = "^1[3-9]\\d{9}$";
	
	public static String DataRule_Maxlength(int length) {
		return String.format(dataRule_MaxLength, length);
	}
	public static String DataRule_LetterOrNumber(int length) {
		return String.format(dataRule_letterOrNumber, length);
	}

	public static String DataRule_Number(int length) {
		return String.format(dataRule_number, length);
	}

	public static String DataRule_Letter(int length) {
		return String.format(dataRule_letter, length);
	}
	public static String DataRule_Email() {
		return dataRule_email;
	}
	public static String DataRule_Number2() {
		return dataRule_number2;
	}
	public static String DataRule_Mobile() {
		return String.format(data_mobile);
	}
}
