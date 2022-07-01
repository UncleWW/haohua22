package com.hh.improve.entity.vo;

import com.hh.improve.entity.BaseEntity;

import java.math.BigDecimal;

public class PaymentHistoryVo extends BaseEntity {
	private String customerId;
	private String customerName;
	private BigDecimal totalAmount;
	private BigDecimal totalPayAmount;
	private BigDecimal totaldebtAmount;

	public BigDecimal getTotaldebtAmount() {
		return totaldebtAmount;
	}

	public void setTotaldebtAmount(BigDecimal totaldebtAmount) {
		this.totaldebtAmount = totaldebtAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public BigDecimal getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(BigDecimal totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}
}