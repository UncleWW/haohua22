package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.util.UUIDUtils;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.PayRollDao;
import com.hh.improve.dao.SalaryDao;
import com.hh.improve.entity.PayRoll;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.vo.PayRollVo;
import com.hh.improve.entity.vo.SalaryVo;
import com.hh.improve.service.IPayRollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PayRollServiceImpl extends BaseServiceImpl<PayRoll> implements IPayRollService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PayRollServiceImpl.class);
	
	@Autowired
	private PayRollDao payRollDao;

	@Autowired
	private SalaryDao salaryDao;
	
	@Override
	protected IBaseDao<PayRoll> getBaseDao() {
		return payRollDao;
	}

	@Override
	public List<PayRollVo> getPayRollVoPageList(PayRollVo payRollVo, int offset, int limit){
		PageHelper.offsetPage(offset, limit);

		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("employeeName",payRollVo.getEmployeeName());
		condition.put("salaryDate", payRollVo.getSalaryDate());
		condition.put("companyAccount", payRollVo.getCompanyAccount());
		return payRollDao.getPayRollVoPageList(condition);
	}

	@Override
	public List<PayRollVo> queryHistoryPayRollVoPage(PayRollVo payRollVo, int offset, int limit){
		PageHelper.offsetPage(offset, limit);

		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("employeePhone", payRollVo.getEmployeePhone());
		condition.put("companyAccount", payRollVo.getCompanyAccount());
		return payRollDao.queryHistoryPayRollVoPage(condition);
	}

	@Override
	public void createNewMonthBill(String userPhone){
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("status","1");
		condition.put("version", "1");
		List<SalaryVo> salaryVos = salaryDao.getSalaryPageList(condition);
		for (SalaryVo emp : salaryVos) {
			PayRoll payRoll = new PayRoll();
			payRoll.setPayrollId(UUIDUtils.getUUID32());
			payRoll.setEmployeePhone(emp.getEmployeePhone());
			payRoll.setSalary(emp.getSalary()==null?new BigDecimal(0):emp.getSalary());
			payRoll.setSalaryPaid(emp.getSalary()==null?new BigDecimal(0):emp.getSalary());
			payRoll.setSalaryDate(dealEndDate());
			payRoll.setCreater(userPhone);
			payRollDao.insert(payRoll);
		}
	}

	@Override
	public String dealEndDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());
		rightNow.add(Calendar.MONTH,0);
		Date dt1=rightNow.getTime();
		String reStr = sdf.format(dt1);
		//System.out.println(reStr);
		return reStr;
	}
}
