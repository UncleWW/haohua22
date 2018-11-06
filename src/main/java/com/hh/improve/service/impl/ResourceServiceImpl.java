package com.hh.improve.service.impl;


import com.hh.improve.common.constants.STATUS;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.ResourceDao;
import com.hh.improve.entity.Resource;
import com.hh.improve.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements IResourceService {
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	protected IBaseDao<Resource> getBaseDao() {
		return resourceDao;
	}

	@Override
	public List<Resource> getUserResList(String userAccount) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userAccount", userAccount);
		return super.getList(condition);
	}
	@Override
	public void  deleteChildrens(Resource resource){
		resourceDao.deleteChildrens(resource);
	}
	@Override
	public List<Resource> getResSetByUser(String userPhone){
		return resourceDao.getResSetByUser(userPhone);
	}
	
	@Override
	public List<Resource> getUserRes(Map<String, Object> condition) {
		if (null == condition.get("resStatus")) {
			condition.put("resStatus", "1");
		}
		if (null == condition.get("roleResStatus")) {
			condition.put("roleResStatus", "1");
		}
		if (null == condition.get("roleStatus")) {
			condition.put("roleStatus", "1");
		}
		if (null == condition.get("roleUserStatus")) {
			condition.put("roleUserStatus", "1");
		}
		if (null == condition.get("userStatus")) {
			condition.put("userStatus", "1");
		}
		return resourceDao.queryUserRes(condition);
	}
	//判断编码是否已经存在
	public boolean isAlreadyExist(Resource res){
		Resource r = new Resource();
		r.setResCode(res.getResCode());
		List<Resource> queryRes = resourceDao.query(r);
		if(queryRes.size()>0){
			return true;//存在
		}else{
			return false;//不存在
		}
	}
	//判断编码是否已经存在
	public boolean isAlreadyExistUri(Resource res){
		Resource r = new Resource();
		r.setResUri(res.getResUri());
		List<Resource> queryRes = resourceDao.query(r);
		if(queryRes.size()>0){
			return true;//存在
		}else{
			return false;//不存在
		}
	}
}
