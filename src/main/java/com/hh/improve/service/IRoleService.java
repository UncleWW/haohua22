package com.hh.improve.service;


import com.hh.improve.entity.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService extends IBaseService<Role> {
	
	public static final String ROOT_ROLE = "ROOT_ROLE";
	
	public static final String ROOT_ROLE_PARENT = "ROOT";
	
	List<Role> queryRoleByParentCode(String parentRoleCode);
	/**
	 * 当删除一个节点时  其所有后代节点全部置为无效
	 * 
	 * @param role
	 * @return
	 */
	void deleteChildrens(Role role);
	/**
	 * @根据当前登录用户查询角色列表
	 * 
	 * @author 011336wangwei3
	 * @param userAccount
	 * @return List<T>
	 */
	List<Role> getRoleSetByUser(String userAccount);
	
	List<Role> getUserRole(Map<String, Object> condition);
	/**
	 * @//判断编码是否已经存在
	 * 
	 * @author 011336wangwei3
	 * @param role
	 * @return boolean
	 */
	boolean isAlreadyExist(Role role);
	/**
	 * @根据当前角色编码查询其所有的父节点
	 * 
	 * @author 011336wangwei3
	 * @param roleCode
	 * @return List<Role>
	 */
	List<Role> getRoleSetByRoleCode(String roleCode);
}
