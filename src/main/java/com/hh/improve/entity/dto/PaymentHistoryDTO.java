package com.hh.improve.entity.dto;

import com.hh.improve.entity.BaseEntity;

public class PaymentHistoryDTO extends BaseEntity{
	private String phId;
	private String customerId;
	private String customerName;
	private String payee;
	private String payDateBegin;
	private String payDateEnd;

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayDateBegin() {
		return payDateBegin;
	}

	public void setPayDateBegin(String payDateBegin) {
		this.payDateBegin = payDateBegin;
	}

	public String getPayDateEnd() {
		return payDateEnd;
	}

	public void setPayDateEnd(String payDateEnd) {
		this.payDateEnd = payDateEnd;
	}
}
