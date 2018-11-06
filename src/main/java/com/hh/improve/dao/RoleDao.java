package com.hh.improve.dao;


import com.hh.improve.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleDao extends IBaseDao<Role>{
	
	List<Role> queryRoleByParentCode(@Param("parentRoleCode") String parentRoleCode);
	/**
	 * 当删除一个节点时  其所有后代节点全部置为无效
	 * 
	 * @param userAccount
	 * @return
	 */
	void deleteChildrens(Role role);

	/**
	 * @根据当前登录用户查询角色列表
	 * 
	 * @author 011336wangwei3
	 * @param userPhone
	 * @return List<T>
	 */
	List<String> getRoleCodeByUser(String userPhone);

	/**
	 * @根据角色编码得到所有的后代
	 *
	 * @author 011336wangwei3
	 * @param RoleCode
	 * @return List<T>
	 */
	List<Role> getChildrensRoleByRoleCode(String RoleCode);


	List<Role> queryUserRole(Map<String, Object> condition);
	/**
	 * @根据当前角色编码查询其所有的父节点
	 * 
	 * @author 011336wangwei3
	 * @param roleCode
	 * @return List<Role>
	 */
	List<Role> getRoleSetByRoleCode(String roleCode);
}
