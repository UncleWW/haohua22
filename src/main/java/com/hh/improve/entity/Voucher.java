package com.hh.improve.entity;

import java.math.BigDecimal;

public class Voucher extends BaseEntity{
	private String voucherId;//主键——凭证编号八位顺序字符串[00000000]
	private String customerId;//客户唯一主键
	private BigDecimal amount;//此凭证全部商品总价
	private String voucherDate;//发票日期YYYY-MM-DD
	private String voucherMaker;//制单人
	private String remark;//备注信息
	private String companyAccount;//公司账套

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getVoucherMaker() {
		return voucherMaker;
	}

	public void setVoucherMaker(String voucherMaker) {
		this.voucherMaker = voucherMaker;
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
