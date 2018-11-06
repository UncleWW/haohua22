package com.hh.improve.common.analyzeexcel.moudles;

import java.util.List;

public class ErrorExcel {

    private int totalInterface;//全部需要导入的接口
    
	private int totalInterfaceError;//失败数量
	
	private int totalParam;//全部需要导入的参数量
	
	private int totalParamError;//参数导入失败数量
	
	private int totalParamSuccess;//参数导入失败数量
	
	private List<ErrorExcelStatistics> errorList; //错误信息
	
	private String result; // 成功-'0' 未知错误-'1' 没有数据-'2'
	
	public int getTotalParamSuccess() {
		return totalParamSuccess;
	}

	public void setTotalParamSuccess(int totalParamSuccess) {
		this.totalParamSuccess = totalParamSuccess;
	}


	public int getTotalInterface() {
		return totalInterface;
	}

	public void setTotalInterface(int totalInterface) {
		this.totalInterface = totalInterface;
	}

	public int getTotalInterfaceError() {
		return totalInterfaceError;
	}

	public void setTotalInterfaceError(int totalInterfaceError) {
		this.totalInterfaceError = totalInterfaceError;
	}

	public int getTotalParam() {
		return totalParam;
	}

	public void setTotalParam(int totalParam) {
		this.totalParam = totalParam;
	}

	public int getTotalParamError() {
		return totalParamError;
	}

	public void setTotalParamError(int totalParamError) {
		this.totalParamError = totalParamError;
	}

	public List<ErrorExcelStatistics> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorExcelStatistics> errorList) {
		this.errorList = errorList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
