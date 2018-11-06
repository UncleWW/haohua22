package com.hh.improve.common.analyzeexcel.moudles;

public class ParasmeterDetail {

	private String id;			//id
	private String returnName;	//接口返回字段名称
	private String DemoData;	//样例数据
	private String parasName;	//参数名
	private String parasType;	//参数类型
	private String isNull;		//是否可为空
	private String restrain;	//约束
	private String member;		//字段成员
	private String definition;	//字段定义
	private String links;		//字段系统关系
	private String managerDept;	//管理责任部门
	private String status;		//字段状态
	private String startDate;	//生效时间
	private String endDate;		//失效时间
	private String version;		//版本号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReturnName() {
		return returnName;
	}
	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	public String getDemoData() {
		return DemoData;
	}
	public void setDemoData(String demoData) {
		DemoData = demoData;
	}
	public String getParasName() {
		return parasName;
	}
	public void setParasName(String parasName) {
		this.parasName = parasName;
	}
	public String getParasType() {
		return parasType;
	}
	public void setParasType(String parasType) {
		this.parasType = parasType;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getRestrain() {
		return restrain;
	}
	public void setRestrain(String restrain) {
		this.restrain = restrain;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public String getManagerDept() {
		return managerDept;
	}
	public void setManagerDept(String managerDept) {
		this.managerDept = managerDept;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
