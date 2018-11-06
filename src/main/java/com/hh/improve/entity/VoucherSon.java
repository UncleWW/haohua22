package com.hh.improve.entity;

import java.math.BigDecimal;

public class VoucherSon extends BaseEntity{
	private String voucherId;//主键——凭证编号八位顺序字符串[00000000]
	private int goodsId;//序号
	private String goodsName;//商品名称
	private String goodsFormat;//商品规格
	private String goodsUnit;//计量单位
	private int goodsNumber;//数量
	private BigDecimal unitPrice;//单价
	private BigDecimal totalPrice;//总价
	private String remark;//备注信息

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsFormat() {
		return goodsFormat;
	}

	public void setGoodsFormat(String goodsFormat) {
		this.goodsFormat = goodsFormat;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public int getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
