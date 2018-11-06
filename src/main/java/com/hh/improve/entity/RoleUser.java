package com.hh.improve.entity;

public class RoleUser extends BaseEntity {
    
	private	String	userAccount;	//	用户主键
	private	String	roleCode;	//	角色编码

	

	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
