package com.hh.improve.dao;


import com.hh.improve.entity.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceDao extends IBaseDao<Resource>{
	void deleteChildrens(Resource resource);
	/**
	 * @根据当前登录用户查询资源列表
	 * 
	 * @author 011336wangwei3
	 * @param userAccount
	 * @return List<T>
	 */
	List<Resource> getResSetByUser(String userAccount);
	
	List<Resource> queryUserRes(Map<String, Object> condition);
	
}
