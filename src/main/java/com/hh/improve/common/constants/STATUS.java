/**
 * 
 */
package com.hh.improve.common.constants;

/**
 * 状态枚举(Common枚举，请勿修改。)
 * 
 * @author 011589
 *
 */
public enum STATUS {

	VALID("有效", "1"), INVALID("无效", "0");

	private String display;
	
	private String value;
	
	private STATUS(String display, String value) {
		this.display = display;
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}
	
	public static String getDisplay(String value) {
		for (STATUS status: STATUS.values()) {
			if (status.getValue().equals(value)) {
				return status.display;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
	public static String getValue(String display) {
		for (STATUS status: STATUS.values()) {
			if (status.getDisplay().equals(display)) {
				return status.value;
			}
		}
		return null;
	}
	
	public static STATUS displayOf(String display) {
		if (display == null) {
			throw new NullPointerException("display is null");
		}
		for (STATUS status: STATUS.values()) {
			if (status.getDisplay().equals(display)) {
				return status;
			}
		}
		throw new IllegalArgumentException("No enum display " + display);
	}
	
	public static STATUS newValueOf(String value) {
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		for (STATUS status: STATUS.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("No enum new value " + value);
	}
}