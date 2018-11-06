package com.hh.improve.entity;

import java.math.BigDecimal;

public class PayRoll extends BaseEntity{
	private String payrollId;//UUID主键
	private String employeePhone;//手机号主键
	private BigDecimal salary;//应发工资
	private BigDecimal salaryPaid;//实际发放工资
	private String salaryDate;//当前月份YYYY-MM
	private String remark;//备注信息
	private String companyAccount;//公司账套

	public String getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
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

	public BigDecimal getSalaryPaid() {
		return salaryPaid;
	}

	public void setSalaryPaid(BigDecimal salaryPaid) {
		this.salaryPaid = salaryPaid;
	}

	public String getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(String salaryDate) {
		this.salaryDate = salaryDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
}
