package com.hh.improve.entity.vo;

import com.hh.improve.entity.BaseEntity;

import java.math.BigDecimal;

public class SalaryVo extends BaseEntity{
	private String employeePhone;//手机号主键
	private String employeeName;//(employee)雇员姓名
	private BigDecimal salary;//个人工资水平
	private String startDate;//有效期开始时间
	private String endDate;//有效期结束时间
	private String version; //版本号1:当前版本，0历史版本
	private String status; //(employee)状态（1有效，0无效）
	private String companyAccount;//公司账套

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}
}
