package com.hh.improve.service.impl;


import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.TestDao;
import com.hh.improve.entity.TestBean;
import com.hh.improve.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends BaseServiceImpl<TestBean> implements TestService {
	
	@Autowired
	private TestDao testDao;


	@Override
	protected IBaseDao<TestBean> getBaseDao() {
		return testDao;
	}
}
