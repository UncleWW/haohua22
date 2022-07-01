package com.hh.improve.entity;

import java.math.BigDecimal;

public class PaymentHistory extends BaseEntity{
	private String phId;
	private String customerId;
	private BigDecimal payAmount;
	private String payee;
	private String payDate;
	private String remark;

	public String getPhId() {
		return phId;
	}

	public void setPhId(String phId) {
		this.phId = phId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
