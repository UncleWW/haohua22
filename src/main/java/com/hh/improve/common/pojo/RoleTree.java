package com.hh.improve.common.pojo;

public class RoleTree {

	private String id;

	private String pId;

	private String name;

	private String t;// title
	
	private String iconOpen; //树的图标
	
	private String iconClose;//树的图标
	
	private boolean open; // 是否展开子节点

	private boolean isParent; // 是否是父节点

	private String roleCode; // 角色编码
	private String parentRoleCode; // 父角色编码
	private String roleName; // 角色名称
	private Integer roleOrder; // 排序
	private String roleType; // 角色类型
	private String nodeType; // 节点类型 1叶子节点 0非叶子';
	private String remark; // 备注
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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getParentRoleCode() {
		return parentRoleCode;
	}

	public void setParentRoleCode(String parentRoleCode) {
		this.parentRoleCode = parentRoleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleOrder() {
		return roleOrder;
	}

	public void setRoleOrder(Integer roleOrder) {
		this.roleOrder = roleOrder;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
