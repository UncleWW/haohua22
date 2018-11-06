package com.hh.improve.dao;

import com.hh.improve.entity.RoleUser;
import com.hh.improve.entity.User;

import java.util.List;
import java.util.Map;

public interface RoleUserDao extends IBaseDao<RoleUser>{
	List<User> selectUserRole(Map<String, Object> condition);
	List<User> selectUserNoRole(Map<String, Object> condition);
	/**
	 * @根据参数  修改用户角色信息
	 * 
	 * @author 011336wangwei3
	 * @param Map<String, Object>
	 * @return 
	 * @date 2017/09/06
	 */
	void updateUserRole(Map<String, Object> condition);
}
