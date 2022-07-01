package com.hh.improve.service;

import com.hh.improve.entity.Voucher;
import com.hh.improve.entity.vo.VoucherSearch;
import com.hh.improve.entity.vo.VoucherVo;

import java.math.BigDecimal;
import java.util.List;

public interface IVoucherService extends IBaseService<Voucher>{
	/**
	 * 获取 需要新插入的凭证ID
	 * @return
	 */
	String getNewMaxVoucherId();

	/**
	 * 新增或修改凭证
	 * @param voucherVo
	 */
	void voucherSaveOrUpdate(VoucherVo voucherVo);

	/**
	 * 查询最后一次插入的凭证信息
	 * @return
	 */
	VoucherVo queryLastVoucher();

	/**
	 *根据voucherId查询凭证
	 * @param voucherId
	 * @return
	 */
	VoucherVo queryVoucherById(String voucherId);
	/**
	 *根据voucherId查询其前面一个后面一个凭证
	 * 0向前  1向后
	 * @param voucherId
	 * @param type
	 * @return
	 */
	VoucherVo queryAroundByVoucherId(String voucherId,int type);

	/**
	 * 分页查询  凭证列表  根据客户分组
	 * @param voucherSearch
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<VoucherVo> queryVoucherListPage(VoucherSearch voucherSearch, int offset, int limit);

	/**
	 * 查询具体一个客户的   凭证
	 * @param voucherSearch
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<VoucherVo> queryVoucherSonListPage(VoucherSearch voucherSearch, int offset, int limit);

	/**
	 * 根据客户 customerId 查询该客户的累计欠款
	 * @param customerId
	 * @return
	 */
	BigDecimal queryAccumulateDebtById(String customerId);

}
