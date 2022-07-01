package com.hh.improve.service;

import com.hh.improve.entity.vo.ReportVoucherVo;

import java.util.List;

public interface IReportVoucherService extends IBaseService<ReportVoucherVo>{

	/**
	 * 获取所有的客户ID
	 * @return
	 */
	List<String> getAllCustomer();

	/**
	 * 获取所有月份
	 * @return
	 */
	List<String> getAllMonths();

	/**
	 * 得到数据库按月分组后的基础数据
	 * @return
	 */
	List<List<String>>  getReportVoucherVo();
}
