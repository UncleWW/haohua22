package com.hh.improve.entity.vo;

import com.hh.improve.entity.BaseEntity;

public class VoucherSearch extends BaseEntity {
	private String voucherId;//主键——凭证编号八位顺序字符串[00000000]
	private String customerId;//客户唯一主键
	private String customerName;//客户名
	private String voucherDate_begin;//发票日期YYYY-MM-DD
	private String voucherDate_end;//发票日期YYYY-MM-DD
	private String voucherMaker;//制单人
	private String companyAccount;//公司账套

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getVoucherDate_begin() {
		return voucherDate_begin;
	}

	public void setVoucherDate_begin(String voucherDate_begin) {
		this.voucherDate_begin = voucherDate_begin;
	}

	public String getVoucherDate_end() {
		return voucherDate_end;
	}

	public void setVoucherDate_end(String voucherDate_end) {
		this.voucherDate_end = voucherDate_end;
	}

	public String getVoucherMaker() {
		return voucherMaker;
	}

	public void setVoucherMaker(String voucherMaker) {
		this.voucherMaker = voucherMaker;
	}
}
