package com.hh.improve.service.impl;

import com.hh.improve.common.util.DefaultValueUtil;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.IReportVoucherDao;
import com.hh.improve.entity.vo.ReportVoucherVo;
import com.hh.improve.service.IReportVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportVoucherServiceImpl extends BaseServiceImpl<ReportVoucherVo> implements IReportVoucherService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ReportVoucherServiceImpl.class);
	
	@Autowired
	private IReportVoucherDao reportDao;

	@Override
	protected IBaseDao<ReportVoucherVo> getBaseDao() {
		return reportDao;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public List<List<String>> getReportVoucherVo(){

		//按客户和月份汇总后的数据
		List<ReportVoucherVo> monthCustomerList= reportDao.getReportVoucherVo();

		//按月份统计每月的总销售额
		List<ReportVoucherVo> monthList= reportDao.getReportSaleGroupByMonth();

		//按月统计每个客户的销售额
		List<List<String>> data = new ArrayList<>();
		List<String> amount = new ArrayList<>();
		int monthNum = getMonthsNum();
		for(int i = 0 ; i< monthCustomerList.size(); i++){
			BigDecimal amountMoney  = DefaultValueUtil.bigDecimal(monthCustomerList.get(i).getAmount());
			amount.add(amountMoney.toString());
			//如果当前客户统计完成，统计下一个
			if((i+1)!= 1 && (i+1) % monthNum == 0){
				data.add(amount);
				amount = new ArrayList<>();
			}
		}
		//每个月的销售额汇总
		List<String> totalAmount = new ArrayList<>();
		for(int i = 0 ; i< monthList.size(); i++){
			BigDecimal amountMoney  = DefaultValueUtil.bigDecimal(monthList.get(i).getAmount());
			totalAmount.add(amountMoney.toString());
		}
		data.add(totalAmount);
		return data;
	}

	@Override
	public List<String> getAllCustomer(){
		return reportDao.getAllCustomer();
	}

	@Override
	public List<String> getAllMonths(){
		return reportDao.getAllMonths();
	}

	/**
	 * 获取共有几个月
	 * @return
	 */
	private int getMonthsNum(){
		List<String> months =  reportDao.getAllMonths();
		if(months != null){
			return months.size();
		}else{
			return 0;
		}
	}
}
