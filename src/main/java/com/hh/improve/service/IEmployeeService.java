package com.hh.improve.service;

import com.hh.improve.entity.Employee;

import java.util.List;

public interface IEmployeeService extends IBaseService<Employee>{
	/**
	 *判断手机号是否已存在
	 * @param phone
	 * @return
	 */
	 Employee getEmployeeByPhone(String phone);
	/**
	 * 新增g雇员信息
	 * @param employee
	 * @return
	 */
	int employeeSave(Employee employee);

	/**
	 * 修改用户信息
	 * @param employee
	 * @return
	 */
	int employeeUpdate(Employee employee);
	/**
	 * 分页展示雇员列表
	 * @param employee
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Employee> getEmployeePageList(Employee employee, int offset, int limit);
}
