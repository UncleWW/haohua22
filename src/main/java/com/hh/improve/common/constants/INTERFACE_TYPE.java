/**
 * 
 */
package com.hh.improve.common.constants;

/**
 * @author 011589
 *
 */
public enum INTERFACE_TYPE {

	ADD_INTERFACE("增量接口", "0"), QUERY_INTERFACE("查询接口", "1"), ALL_INTERFACE("全量接口", "2");

	private String display;
	
	private String value;
	
	private INTERFACE_TYPE(String display, String value) {
		this.display = display;
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}
	
	public static String getDisplay(String value) {
		for (INTERFACE_TYPE interfaceType: INTERFACE_TYPE.values()) {
			if (interfaceType.getValue().equals(value)) {
				return interfaceType.display;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
	public static String getValue(String display) {
		for (INTERFACE_TYPE interfaceType: INTERFACE_TYPE.values()) {
			if (interfaceType.getDisplay().equals(display)) {
				return interfaceType.value;
			}
		}
		return null;
	}
	
	public static INTERFACE_TYPE displayOf(String display) {
		if (display == null) {
			throw new NullPointerException("display is null");
		}
		for (INTERFACE_TYPE interfaceType: INTERFACE_TYPE.values()) {
			if (interfaceType.getDisplay().equals(display)) {
				return interfaceType;
			}
		}
		throw new IllegalArgumentException("No enum display " + display);
	}
	
	public static INTERFACE_TYPE newValueOf(String value) {
		if (value == null) {
			throw new NullPointerException("value is null");
		}
		for (INTERFACE_TYPE interfaceType: INTERFACE_TYPE.values()) {
			if (interfaceType.getValue().equals(value)) {
				return interfaceType;
			}
		}
		throw new IllegalArgumentException("No enum new value " + value);
	}
}
