package com.hh.improve.entity.vo;

import com.hh.improve.entity.BaseEntity;
import com.hh.improve.entity.VoucherSon;

import java.math.BigDecimal;
import java.util.List;

public class VoucherVo extends BaseEntity {
	private String voucherId;//主键——凭证编号八位顺序字符串[00000000]
	private String customerId;//客户唯一主键
	private String customerName;//客户名字
	private BigDecimal amount;//此凭证全部商品总价
	private BigDecimal payAmount;//付款金额
	private BigDecimal debtAmount;//欠款金额
	private String voucherDate;//发票日期YYYY-MM-DD
	private String voucherMaker;//制单人
	private String remark;//备注信息
	private String voucherNnm;//凭证数量（一段时间内开了多少个凭证单据）

	List<VoucherSon> sonList;
	private String companyAccount;//公司账套

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	public String getVoucherNnm() {
		return voucherNnm;
	}

	public void setVoucherNnm(String voucherNnm) {
		this.voucherNnm = voucherNnm;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public List<VoucherSon> getSonList() {
		return sonList;
	}

	public void setSonList(List<VoucherSon> sonList) {
		this.sonList = sonList;
	}
}
