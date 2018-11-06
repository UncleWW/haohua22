package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.ICustomerDao;
import com.hh.improve.entity.Customer;
import com.hh.improve.entity.Employee;
import com.hh.improve.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements ICustomerService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private ICustomerDao customerDao;
	
	@Override
	protected IBaseDao<Customer> getBaseDao() {
		return customerDao;
	}

    @Override
    public List<Customer> getCustomerPageList(Customer customer, int offset, int limit) {
        PageHelper.offsetPage(offset, limit);
        return customerDao.query(customer);
    }
}
