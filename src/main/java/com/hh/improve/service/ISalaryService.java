package com.hh.improve.service;

import com.hh.improve.entity.Salary;
import com.hh.improve.entity.vo.SalaryVo;

import java.util.List;

public interface ISalaryService extends IBaseService<Salary>{
	/**
	 * 分页展示用户列表
	 * @param salaryVo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<SalaryVo> getSalaryPageList(SalaryVo salaryVo, int offset, int limit);

	/**
	 * 分页展示用户详细历史数据
	 * @param salaryVo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<SalaryVo> getSalaryHistoryPageList(SalaryVo salaryVo, int offset, int limit);
	/**
	 * 处理得到新的工资开始生效月份
	 * @param status
	 * @return
	 */
	String dealStartDate(String status);

	/**
	 * 处理得到旧的工资生效结束月份
	 * @param status
	 * @return
	 */
	String dealEndDate(String status);

	/**
	 * 物理删除
	 * @param salary
	 * @return
	 */
	 int delete(Salary salary);
}
