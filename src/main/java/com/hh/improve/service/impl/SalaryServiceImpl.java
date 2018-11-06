package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.SalaryDao;
import com.hh.improve.entity.Salary;
import com.hh.improve.entity.vo.SalaryVo;
import com.hh.improve.service.ISalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SalaryServiceImpl extends BaseServiceImpl<Salary> implements ISalaryService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SalaryServiceImpl.class);
	
	@Autowired
	private SalaryDao salaryDao;
	
	@Override
	protected IBaseDao<Salary> getBaseDao() {
		return salaryDao;
	}

	@Override
	public List<SalaryVo> getSalaryPageList(SalaryVo salaryVo, int offset, int limit){
		PageHelper.offsetPage(offset, limit);

		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("employeePhone", salaryVo.getEmployeePhone());
		condition.put("employeeName",salaryVo.getEmployeeName());
		condition.put("status", salaryVo.getStatus());
		condition.put("version", salaryVo.getVersion());//只有当值等于0时，才会作为判断条件
		condition.put("companyAccount", salaryVo.getCompanyAccount());
		return salaryDao.getSalaryPageList(condition);
	}

	@Override
	public List<SalaryVo> getSalaryHistoryPageList(SalaryVo salaryVo, int offset, int limit){
		PageHelper.offsetPage(offset, limit);

		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("employeePhone", salaryVo.getEmployeePhone());
		condition.put("employeeName",salaryVo.getEmployeeName());
		condition.put("status", salaryVo.getStatus());
		condition.put("version", salaryVo.getVersion());//只有当值等于0时，才会作为判断条件
		condition.put("companyAccount", salaryVo.getCompanyAccount());
		return salaryDao.getSalaryHistoryPageList(condition);
	}

	@Override
	public String dealStartDate(String status){
		if(status == null || status.equals("")){
			status = "0";//本月生效
		}
		int effectMonth = Integer.parseInt(status);
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());
		if(effectMonth==1){//次月生效
			rightNow.add(Calendar.MONTH,1);//日期加1个月
		}
		Date dt1=rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		return reStr;
	}

	@Override
	public String dealEndDate(String status){
		if(status == null || status.equals("")){
			status = "0";//本月生效
		}
		int effectMonth = Integer.parseInt(status);
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());
		if(effectMonth==0){//次月生效
			rightNow.add(Calendar.MONTH,-1);//日期加1个月
		}
		Date dt1=rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		return reStr;
	}

	@Override
	public int delete(Salary salary){
		return salaryDao.delete(salary);
	}
}
