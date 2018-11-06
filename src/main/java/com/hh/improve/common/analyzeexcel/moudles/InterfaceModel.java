package com.hh.improve.common.analyzeexcel.moudles;


import com.hh.improve.common.analyzeexcel.annotation.ExcelField;

import java.util.List;

public class InterfaceModel {

	@ExcelField(title = "编号", order = 1)
    private String id;
	@ExcelField(title = "接口类别", order = 2)
	private String category;
	@ExcelField(title = "接口名称", order = 3)
	private String name;
	@ExcelField(title = "版本号", order = 4)
	private String version;
	@ExcelField(title = "接口类型", order = 5)
	private String type;
	@ExcelField(title = "数据更新频率", order = 6)
	private String updateRate;
	@ExcelField(title = "接口地址", order = 7)
	private String address; 
	@ExcelField(title = "接口状态", order = 8)
	private String status; 
	@ExcelField(title = "上线时间", order = 9)
	private String onDate;
	@ExcelField(title = "停止维护时间", order = 10)
	private String shutDate;
	@ExcelField(title = "产品经理", order = 11)
	private String productManager;
	@ExcelField(title = "项目经理", order = 12)
	private String projectManager;
	@ExcelField(title = "是否包含敏感信息", order = 13)
	private String isSensitive;
	@ExcelField(title = "是否导入", order = 14)
	private String isExport;
	@ExcelField(title = "备注", order = 15)
	private String remarks;
	
	private String purpose; //接口用途
	private String schema;  //视图Schema名
	private String viewName;//数据视图
	private String paraRemarks;//参数说明
	private List<InterfaceParameters> iParasList; //入参
	private List<ParasmeterDetail> paraDList;	  //详细信息
	List<ErrorExcelStatistics> errorES ; //错误信息

	public List<ErrorExcelStatistics> getErrorES() {
		return errorES;
	}
	public void setErrorES(List<ErrorExcelStatistics> errorES) {
		this.errorES = errorES;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getParaRemarks() {
		return paraRemarks;
	}
	public void setParaRemarks(String paraRemarks) {
		this.paraRemarks = paraRemarks;
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
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Override
    public String toString() {
        return "InterfaceModel{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", name=" + name +
                ", type=" + type +
                ", isSensitive='" + isSensitive + '\'' +
                ", version='" + version + '\'' +
                ", updateRate='" + updateRate + '\'' +
                ", productManager='" + productManager + '\'' +
                ", isExport='" + isExport + '\'' +
                ", projectManager='" + projectManager + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
