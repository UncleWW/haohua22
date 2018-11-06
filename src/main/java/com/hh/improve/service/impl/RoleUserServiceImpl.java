package com.hh.improve.service.impl;


import com.github.pagehelper.PageHelper;
import com.hh.improve.common.constants.STATUS;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.RoleUserDao;
import com.hh.improve.entity.RoleUser;
import com.hh.improve.entity.User;
import com.hh.improve.entity.vo.UserRoleVO;
import com.hh.improve.service.IRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleUserServiceImpl extends BaseServiceImpl<RoleUser> implements IRoleUserService {
	@Autowired
	private RoleUserDao roleUserDao;
	
	@Override
	protected IBaseDao<RoleUser> getBaseDao() {
		return roleUserDao;
	}
	@Override
	public List<User> selectUserRole(UserRoleVO userRoleVO, int offset, int limit) {
		PageHelper.offsetPage(offset, limit);
		
		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("roleCode", userRoleVO.getRoleCode());
		condition.put("userPhone", userRoleVO.getUserPhone());
		condition.put("userName", userRoleVO.getUserName());
		//condition.put("deptname", userRoleVO.getDeptname());
		condition.put("status", "1");
		return roleUserDao.selectUserRole(condition);
	}
	@Override
	public List<User> selectUserNoRole(UserRoleVO userRoleVO, int offset, int limit) {
		PageHelper.offsetPage(offset, limit);
		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("roleCode", userRoleVO.getRoleCode());
		condition.put("userPhone", userRoleVO.getUserPhone());
		condition.put("userName", userRoleVO.getUserName());
		//condition.put("deptname", userRoleVO.getDeptname());
		condition.put("status", "1");
		
		return roleUserDao.selectUserNoRole(condition);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserRole(Map<String, Object> condition){
		 roleUserDao.updateUserRole(condition);
	}
}
