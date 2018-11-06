package com.hh.improve.handler;


import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.entity.Resource;
import com.hh.improve.entity.Role;
import com.hh.improve.entity.SuperAdmin;
import com.hh.improve.entity.User;
import com.hh.improve.service.IResourceService;
import com.hh.improve.service.IRoleService;
import com.hh.improve.service.ISuperAdminService;
import com.hh.improve.service.IUserService;
import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorizationServiceHandler implements IAuthorizationService {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceHandler.class);
	
	@Autowired
	private ISuperAdminService superAdminService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IResourceService resService;
	
	@Autowired
	private IRoleService roleService;
	
	@Override
	public boolean isSuperAdmin(String userPhone) {
		if (StringUtils.isNotBlank(userPhone)) {
			SuperAdmin superAdmin = new SuperAdmin();
			superAdmin.setUserPhone(userPhone);
			SuperAdmin resultSuperAdmin = superAdminService.get(superAdmin);
			if(null != resultSuperAdmin) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isValidUser(String userPhone) {
		if (StringUtils.isNotBlank(userPhone)) {
			User user = new User();
			user.setUserPhone(userPhone);
			user.setStatus(WebConstants.USER_STATUS_VALID);
			User resultUser = userService.get(user);
			if (null != resultUser) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Set<String> getUserRoleCodeSet(String userPhone) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userPhone", userPhone);
		condition.put("status", "1");
		List<Role> results = roleService.getUserRole(condition);
		Set<String> set = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(results)) {
			for (Role role: results) {
				set.add(role.getRoleCode());
			}
		}
		return set;
	}

	@Override
	public Set<String> getUserResCodeSet(String userPhone) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userPhone", userPhone);
		List<Resource> results = resService.getUserRes(condition);
		Set<String> set = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(results)) {
			for (Resource res: results) {
				set.add(res.getResCode());
			}
		}
		return set;
	}
	
	@Override
	public Set<String> getUserDataSet(String userAccount) {
		Set<String> set = new HashSet<String>();
		// TODO 数据权限,暂时不做
		return set;
	}

	@Override
	public String getResCodeByUri(String uri) {
		Resource res = new Resource();
		res.setResUri(uri);
		Resource result = resService.get(res);
		if (null != result) {
			return result.getResCode();
		}
		return null;
	}

	@Override
	public String getRootResCode() {
		return IResourceService.ROOT_RESOURCE;
	}

	@Override
	public void logLogin(String userAccount) {
		// TOOD 记录登录log
		LOGGER.info("LOGIN SUCCESS! USER ACCOUNT IS " + userAccount + " AND TIME IS" + new Date());
	}
}
