package com.hh.improve.common.analyzeexcel.moudles;

import java.util.List;

public class InterfaceDetail {

    private String id;
	private String category; //接口类别
	private String name; //接口名字
	private String type; //接口类型
	private String isSensitive; //是否包含敏感信息
	private String version;  //版本号
	private String updateRate; //更新频率
	private String productManager;
	private String isExport; //是否导入
	private String projectManager;
	private String remarks; //参数说明
	//用途
	private String purpose; //接口用途
	private String address; //接口地址
	private String schema;  //视图Schema名
	private String viewName;//数据视图
	private String status;  //接口状态
	private String onDate;  //上线时间
	private String shutDate;//停止维护时间
	private String userName;//使用系统：需求人
	private String dateRange;//数据时间范围
	
	private List<InterfaceParameters> iParasList; //入参
	private List<ParasmeterDetail> paraDList;	  //详细信息	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsSensitive() {
		return isSensitive;
	}
	public void setIsSensitive(String isSensitive) {
		this.isSensitive = isSensitive;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUpdateRate() {
		return updateRate;
	}
	public void setUpdateRate(String updateRate) {
		this.updateRate = updateRate;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	public String getIsExport() {
		return isExport;
	}
	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOnDate() {
		return onDate;
	}
	public void setOnDate(String onDate) {
		this.onDate = onDate;
	}
	public String getShutDate() {
		return shutDate;
	}
	public void setShutDate(String shutDate) {
		this.shutDate = shutDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public List<InterfaceParameters> getiParasList() {
		return iParasList;
	}
	public void setiParasList(List<InterfaceParameters> iParasList) {
		this.iParasList = iParasList;
	}
	public List<ParasmeterDetail> getParaDList() {
		return paraDList;
	}
	public void setParaDList(List<ParasmeterDetail> paraDList) {
		this.paraDList = paraDList;
	}
}
