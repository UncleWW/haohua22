package com.hh.improve.service;

import com.hh.improve.entity.Customer;

import java.util.List;

public interface ICustomerService extends IBaseService<Customer>{
	/**
	 * 分页客户
	 * @param cstomer
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Customer> getCustomerPageList(Customer cstomer, int offset, int limit);

}
