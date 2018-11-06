/**
 * 
 */
package com.hh.improve.entity;

/**
 * @author 011589
 *
 */
public class SuperAdmin extends BaseEntity {

	private String userPhone;
	
	private String userName;

	private String password;
	
	private String encryptPassword;
	
	private String remark;

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptPassword() {
		return encryptPassword;
	}

	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = encryptPassword;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SuperAdmin [userPhone = ")
		  .append(userPhone)
		  .append(", userName = ")
		  .append(userName)
		  .append(", status = ")
		  .append(super.getStatus().toString())
		  .append(", remark = ")
		  .append(remark);
		return sb.toString();
	}
}
