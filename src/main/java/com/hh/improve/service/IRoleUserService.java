package com.hh.improve.service;


import com.hh.improve.entity.RoleUser;
import com.hh.improve.entity.User;
import com.hh.improve.entity.vo.UserRoleVO;

import java.util.List;
import java.util.Map;

public interface IRoleUserService extends IBaseService<RoleUser>{
	/**
	 * @根据条件查询列表
	 * 
	 * @author 011336wangwei3
	 * @param userRoleVO
	 * @param offset
	 * @param limit
	 * @return List<T>
	 */
	List<User> selectUserRole(UserRoleVO userRoleVO, int offset, int limit);
	/**
	 * @根据条件查询列表
	 * 
	 * @author 011336wangwei3
	 * @param userRoleVO
	 * @param offset
	 * @param limit
	 * @return List<T>
	 */
	List<User> selectUserNoRole(UserRoleVO userRoleVO, int offset, int limit);
	/**
	 * @根据参数  修改用户角色信息
	 * 
	 * @author 011336wangwei3
	 * @param condition
	 * @return 
	 * @date 2017/09/06
	 */
	void updateUserRole(Map<String, Object> condition);
}
