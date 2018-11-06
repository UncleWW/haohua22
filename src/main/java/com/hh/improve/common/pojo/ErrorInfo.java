/**
 * 
 */
package com.hh.improve.common.pojo;

/**
 * @author 011589
 *
 */
public class ErrorInfo {

	private String errorCode;
	
	private String errorMsg;

	public ErrorInfo() {
	}
	
	public ErrorInfo(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ErrorInfo(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
