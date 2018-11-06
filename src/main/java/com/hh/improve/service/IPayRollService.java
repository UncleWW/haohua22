package com.hh.improve.service;

import com.hh.improve.entity.PayRoll;
import com.hh.improve.entity.vo.PayRollVo;

import java.util.List;

public interface IPayRollService extends IBaseService<PayRoll>{
	/**
	 * 分页展示工资发放记录
	 * @param payRollVo
	 * @param offset
	 * @aram limit
	 * @return
	 */
	List<PayRollVo> getPayRollVoPageList(PayRollVo payRollVo, int offset, int limit);

	/**
	 * 分页展示History工资发放记录
	 * @param payRollVo
	 * @param offset
	 * @aram limit
	 * @return
	 */
	List<PayRollVo> queryHistoryPayRollVoPage(PayRollVo payRollVo, int offset, int limit);

	/**
	 * 复制 在职员工  薪资信息 到工资发放记录表中
	 * @return
	 */
	void createNewMonthBill(String userPhone);

	public String dealEndDate();

}
