/**
 * 
 */
package com.hh.improve.service;


import com.hh.improve.entity.Resource;

import java.util.List;
import java.util.Map;

/**
 * @author 011589
 *
 */
public interface IResourceService extends IBaseService<Resource>{
	
	public static final String ROOT_RESOURCE = "ROOT_RESOURCE";
	public static final String ROOT_RESOURCE_PARENT = "ROOT";
	
	/**
	 * 获取所有资源列表
	 * 
	 * @param userAccount
	 * @return
	 */
	List<Resource> getUserResList(String userAccount);
	/**
	 * 当删除一个节点时  其所有后代节点全部置为无效
	 * 
	 * @param userAccount
	 * @return
	 */
	void deleteChildrens(Resource resource);
	/**
	 * @根据当前登录用户查询资源列表
	 * 
	 * @author 011336wangwei3
	 * @param userAccount
	 * @return List<T>
	 */
	List<Resource> getResSetByUser(String userAccount);
	
	List<Resource> getUserRes(Map<String, Object> condition);
	
	/**
	 * @//判断编码是否已经存在
	 * 
	 * @author 011336wangwei3
	 * @param Resource
	 * @return boolean
	 */
	boolean isAlreadyExist(Resource res);
	/**
	 * @//判断uri是否已经存在
	 * 
	 * @author 011336wangwei3
	 * @param Resource
	 * @return boolean
	 */
	boolean isAlreadyExistUri(Resource res);
}
