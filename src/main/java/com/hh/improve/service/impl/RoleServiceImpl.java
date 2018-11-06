package com.hh.improve.service.impl;


import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.RoleDao;
import com.hh.improve.entity.Role;
import com.hh.improve.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Override
	protected IBaseDao<Role> getBaseDao() {
		return roleDao;
	}
	@Override
	public List<Role> queryRoleByParentCode(String parentRoleCode){
		return roleDao.queryRoleByParentCode(parentRoleCode);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void  deleteChildrens(Role role){
		roleDao.deleteChildrens(role);
	}
	
	@Override
	public List<Role> getRoleSetByUser(String userPhone){
		List<Role> resultRoles = new ArrayList<Role>();
		List<String> roles = roleDao.getRoleCodeByUser(userPhone);
		for (String roleCode:roles){
			List<Role> rs = roleDao.getChildrensRoleByRoleCode(roleCode);
			resultRoles.addAll(rs);
		}
		Set<Role> set = new HashSet<Role>(resultRoles);
		resultRoles =  new ArrayList<Role>(set);
		return resultRoles;
	}
	
	@Override
	public List<Role> getUserRole(Map<String, Object> condition) {
		if (null == condition.get("roleStatus")) {
			condition.put("roleStatus", "1");
		}
		if (null == condition.get("roleUserStatus")) {
			condition.put("roleUserStatus", "1");
		}
		if (null == condition.get("userStatus")) {
			condition.put("userStatus", WebConstants.USER_STATUS_VALID);
		}
		return roleDao.queryUserRole(condition);
	}
	//判断编码是否已经存在
		public boolean isAlreadyExist(Role role){
			Role r =  new Role();
			r.setRoleCode(role.getRoleCode());
			List<Role> queryRole = roleDao.query(r);
			if(queryRole.size()>0){
				return true;//存在
			}else{
				return false;//不存在
			}
		}
		@Override
		public List<Role> getRoleSetByRoleCode(String roleCode){
			return roleDao.getRoleSetByRoleCode(roleCode);
		}
}
