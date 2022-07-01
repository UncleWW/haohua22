package com.hh.improve.entity.vo;

import java.math.BigDecimal;

public class ReportVoucherVo {
	private String customerId;//客户唯一主键
	private String customerName;//客户名字
	private BigDecimal amount;//此凭证全部商品总价
	private BigDecimal payAmount;//付款金额
	private BigDecimal debtAmount;//欠款金额
	private String voucherDate;//发票日期YYYY-MM

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getDebtAmount() {
		return debtAmount;
	}

	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
}
