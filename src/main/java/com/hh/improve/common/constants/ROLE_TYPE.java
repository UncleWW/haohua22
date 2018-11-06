/**
 * 
 */
package com.hh.improve.common.constants;

/**
 * @author 011589
 *
 */
public enum ROLE_TYPE {

	SYSTEM_ADMIN("系统管理员", "0"), ADMIN("管理员", "1"), USER("用户", "2");
	
	private String display;
	
	private String value;
	
	private ROLE_TYPE(String display, String value) {
		this.display = display;
		this.value = value;
	}

	public String getDisplay() {
		return display;
	}
	
	public static String getDisplay(String value) {
		for (ROLE_TYPE status: ROLE_TYPE.values()) {
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
		for (ROLE_TYPE status: ROLE_TYPE.values()) {
			if (status.getDisplay().equals(display)) {
				return status.value;
			}
		}
		return null;
	}
}
