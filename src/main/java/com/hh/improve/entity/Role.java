package com.hh.improve.entity;

public class Role extends BaseEntity {
	private String roleCode;	//	角色编码
	private String parentRoleCode;	//	父角色编码
	private String roleName;	//	角色名称
	private Integer roleOrder;	//	排序
	private String roleType;	//	角色类型
	private String nodeType;	//	节点类型 1叶子节点 0非叶子';
	private String remark;      //	备注
	
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
