package com.hh.improve.service.impl;


import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.RoleResourceDao;
import com.hh.improve.entity.RoleResource;
import com.hh.improve.service.IRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResource> implements IRoleResourceService {
	@Autowired
	private RoleResourceDao roleResourceDao;
	
	@Override
	protected IBaseDao<RoleResource> getBaseDao() {
		return roleResourceDao;
	}
	@Override
	public List<RoleResource> queryParentRoleResByRoleCode(String roleCode){
		return roleResourceDao.queryParentRoleResByRoleCode(roleCode);
	}
	@Override
	public List<RoleResource> queryChildrenRoleResByRoleCode(String roleCode){
		return roleResourceDao.queryChildrenRoleResByRoleCode(roleCode);
	}
}
