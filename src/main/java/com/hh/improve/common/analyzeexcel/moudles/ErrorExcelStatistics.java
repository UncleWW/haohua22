package com.hh.improve.common.analyzeexcel.moudles;

public class ErrorExcelStatistics {

    private String rowNumber;//行号
    
	private String columnNumber;//列号
	
	private String titleName;//单元格  名字
	
	private String cellValue;//单元格内容
	
	private String errorMgs;//错误信息
	
	private String statisticsType;//统计的类型   是接口  还是入参   出参

	private String excelName;//excel的名字
	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getCellValue() {
		return cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

	public String getErrorMgs() {
		return errorMgs;
	}

	public void setErrorMgs(String errorMgs) {
		this.errorMgs = errorMgs;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	
}
