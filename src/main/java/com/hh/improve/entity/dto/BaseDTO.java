/**
 * 
 */
package com.hh.improve.entity.dto;

import com.hh.improve.common.pojo.ErrorInfo;

import java.util.List;


/**
 * @author 011589
 *
 */
public class BaseDTO {
	
	private String resultFlag;

	private List<ErrorInfo> errors;

	private Object result;
	
	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public List<ErrorInfo> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorInfo> errors) {
		this.errors = errors;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
