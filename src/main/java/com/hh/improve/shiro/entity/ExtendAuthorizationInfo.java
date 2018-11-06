package com.hh.improve.shiro.entity;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.Set;

/**
 * 扩展权限信息类加入数据权限设置
 * 
 * @author 011589
 *
 */
public class ExtendAuthorizationInfo extends SimpleAuthorizationInfo {

	private static final long serialVersionUID = 2101829201747706309L;

	private Set<String> dataPermissions;
	
	private long userAuthTimeStamp;

	public Set<String> getDataPermissions() {
		return dataPermissions;
	}

	public void setDataPermissions(Set<String> dataPermissions) {
		this.dataPermissions = dataPermissions;
	}

	public long getUserAuthTimeStamp() {
		return userAuthTimeStamp;
	}

	public void setUserAuthTimeStamp(long userAuthTimeStamp) {
		this.userAuthTimeStamp = userAuthTimeStamp;
	}

}
