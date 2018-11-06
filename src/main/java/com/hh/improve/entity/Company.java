package com.hh.improve.entity;

public class Company extends BaseEntity{
	private String companyAccount;//公司账套
	private String companyName;//公司名称
	private String corporate;//公司法人
	private String corporatePhone;//公司法人联系电话

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getCorporatePhone() {
		return corporatePhone;
	}

	public void setCorporatePhone(String corporatePhone) {
		this.corporatePhone = corporatePhone;
	}
}
