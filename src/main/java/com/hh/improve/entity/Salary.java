package com.hh.improve.entity;

import java.math.BigDecimal;

public class Salary extends BaseEntity{
	private String employeePhone;//手机号主键
	private BigDecimal salary;//个人工资水平
	private String startDate;//有效期开始时间
	private String endDate;//有效期结束时间
	private String version; //版本号1:当前版本，0历史版本
	private String companyAccount;//公司账套

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
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

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
}
