package com.hh.improve.entity;

import javax.validation.constraints.Size;

public class Resource extends BaseEntity  {
	@Size(min=1, max=50)
	private	String	resCode	;	//	资源编码
	private	String	parentResCode	;	//	父资源编码
	@Size(min=1, max=60)
	private	String	resName	;	//	资源名称
	private	String	resUri	;	//	路径
	private	String	resType	;	//	资源类型1菜单2按钮
	private	String	nodeType	;	//	节点类型 1叶子节点 0非叶子';
	private	Integer	resOrder	;	//	资源排序
	private	String	resIcon;	//	资源图标
	private	String	remark	;	//	备注
	

	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getParentResCode() {
		return parentResCode;
	}
	public void setParentResCode(String parentResCode) {
		this.parentResCode = parentResCode;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResUri() {
		return resUri;
	}
	public void setResUri(String resUri) {
		this.resUri = resUri;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public Integer getResOrder() {
		return resOrder;
	}
	public void setResOrder(Integer resOrder) {
		this.resOrder = resOrder;
	}
	public String getResIcon() {
		return resIcon;
	}
	public void setResIcon(String resIcon) {
		this.resIcon = resIcon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
