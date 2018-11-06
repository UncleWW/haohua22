/**
 * 
 */
package com.hh.improve.common.exception;

/**
 * @author 011589
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -5943950933485420591L;

	private String errorCode;
	
	private String errorMsg;

	public BusinessException() {}
	
	public BusinessException(String errorCode) {
		this.errorCode = errorCode;
	}
	public BusinessException(String errorCode, String errorMsg) {
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
