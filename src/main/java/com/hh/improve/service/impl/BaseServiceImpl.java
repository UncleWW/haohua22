package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	
	protected abstract IBaseDao<T> getBaseDao();
	
	@Override
	public List<T> getList(Map<String, Object> condition) {
		return getBaseDao().queryByCondition(condition);
	}
	
	@Override
	public List<T> getPageList(T entity, int offset, int limit) {
		PageHelper.offsetPage(offset, limit);
		return getBaseDao().query(entity);
	}
	
	@Override
	public T get(T entity) {
		List<T> entitys = getBaseDao().query(entity);
		if (!CollectionUtils.isEmpty(entitys)) {
			return entitys.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(T entity) {
		getBaseDao().insert(entity);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(T entity) {
		getBaseDao().update(entity);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(List<T> entitys) {
		if(CollectionUtils.isEmpty(entitys)) {
			return;
		}
		for (T entity: entitys) {
			getBaseDao().update(entity);
		}
	}
}
