package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.IEmployeeDao;
import com.hh.improve.entity.Employee;
import com.hh.improve.entity.User;
import com.hh.improve.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private IEmployeeDao employeeDao;

	@Override
	protected IBaseDao<Employee> getBaseDao() {
		return employeeDao;
	}

    @Override
    public List<Employee> getEmployeePageList(Employee employee, int offset, int limit) {
        PageHelper.offsetPage(offset, limit);
        return employeeDao.query(employee);
    }

	@Override
	public int employeeSave(Employee employee){
		return employeeDao.insert(employee);
	}

	@Override
	public int employeeUpdate(Employee employee){
		return employeeDao.update(employee);
	}

	@Override
	public Employee getEmployeeByPhone(String phone) {
		// 验证
		Employee employee = new Employee();
		employee.setEmployeePhone(phone);
		Employee e = getEmployeeByPhone(employee);
		return e;
	}

	public Employee getEmployeeByPhone(Employee employee){
		List<Employee> employees =  employeeDao.getEmployeeByPhone(employee);
		if(employees!=null && employees.size()>0){
			return employees.get(0);
		}
		return null;
	}
}
