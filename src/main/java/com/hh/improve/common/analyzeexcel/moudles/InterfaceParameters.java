/**
 * 
 */
package com.hh.improve.common.analyzeexcel.moudles;

/**
 * @author Administrator
 *
 */
public class InterfaceParameters {

	private String condition; //接口输入条件
	private String demoData;  //样例数据
	private String parasName; //参数名
	private String type;      //参数类型
	private String dataFormat;//数据格式 
	private String isNull;    //是否可为空
	private String remarks;   //参数说明
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getDemoData() {
		return demoData;
	}
	public void setDemoData(String demoData) {
		this.demoData = demoData;
	}
	public String getParasName() {
		return parasName;
	}
	public void setParasName(String parasName) {
		this.parasName = parasName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
