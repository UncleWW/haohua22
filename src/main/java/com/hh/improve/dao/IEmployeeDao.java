package com.hh.improve.dao;


import com.hh.improve.entity.Employee;

import java.util.List;

public interface IEmployeeDao extends IBaseDao<Employee> {
    List<Employee> getEmployeeByPhone(Employee employee);
}
