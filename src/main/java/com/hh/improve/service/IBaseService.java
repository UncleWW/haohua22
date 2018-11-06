package com.hh.improve.service;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {
	
	/**
	 * @根据条件查询列表,针对联表查询
	 * 
	 * @author 011336wangwei3
	 * @param condition
	 * @param condition
	 * @return List<T>
	 */
	List<T> getList(Map<String, Object> condition);

	/**
	 * @根据条件查询列表
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @param offset
	 * @param limit
	 * @return List<T>
	 */
	List<T> getPageList(T entity, int offset, int limit);
	
	/**
	 * @根据条件查询单条数据
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @return T
	 */
	T get(T entity);
	
	/**
	 * @创建单条数据
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @return 
	 */
	void create(T entity);
	
	/**
	 * @更新单条数据
	 * 
	 * @author 011336wangwei3
	 * @param entity
	 * @return 
	 */
	void update(T entity);
	
	/**
	 * @批量更新数据
	 * 
	 * @author 011336wangwei3
	 * @param entitys
	 * @return 
	 */
	void update(List<T> entitys);
}
