package com.hh.improve.entity;

public class CompanyUser extends BaseEntity{
	private String cuId;//公司账套
	private String userPhone;//公司名称
	private String companyAccount;//公司法人

	public String getCuId() {
		return cuId;
	}

	public void setCuId(String cuId) {
		this.cuId = cuId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
}
