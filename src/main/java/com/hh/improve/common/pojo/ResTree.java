package com.hh.improve.common.pojo;


public class ResTree {

	private String id;

	private String pId;

	private String name;
	
	private String iconOpen; //树的图标
	
	private String iconClose;//树的图标
	
	private String t;// title

	private boolean open; // 是否展开子节点

	private boolean isParent; // 是否是父节点

	private String resCode; // 资源编码
	private String parentResCode; // 父资源编码
	private String resName; // 资源名称
	private String resUri; // 路径
	private String resType; // 资源类型1菜单2按钮
	private String nodeType; // 节点类型 1叶子节点 0非叶子';
	private Integer resOrder; // 资源排序
	private String resIcon; // 资源图标
	private String remark; // 备注
	private String modifier; // 修改人
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
