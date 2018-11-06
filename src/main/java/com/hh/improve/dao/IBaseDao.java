package com.hh.improve.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {
	
	/**
	 * 根据条件查询数据
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @return List<T>
	 */
	List<T> query(T entity);
	
	/**
	 * 根据条件查询数据
	 * 
	 * @author 011336wangwei3
	 * @param condition
	 * @return List<T>
	 */
	List<T> queryByCondition(Map<String, Object> condition);
	
	/**
	 * 根据条件创建数据
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @return List<T>
	 */
	int insert(T entity);
	
	/**
	 * 根据条件更新数据
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @return List<T>
	 */
	int update(T entity);
}
