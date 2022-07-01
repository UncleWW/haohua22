package com.hh.improve.dao;


import com.hh.improve.entity.Voucher;
import com.hh.improve.entity.vo.VoucherVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IVoucherDao extends IBaseDao<Voucher> {
    /**
     * 获取 需要新插入的凭证ID
     * @return
     */
    String getMaxVoucherId();

    /**
     * 分页查询  凭证列表  根据客户分组
     * @param condition
     * @return
     */
    List<VoucherVo> queryVoucherListPage(Map<String, Object> condition);

    /**
     * 查询具体一个客户的   凭证
     * @param condition
     * @return
     */
    List<VoucherVo> queryVoucherSonListPage(Map<String, Object> condition);

    /**
     * 根据 客户Id 统计销售总金额
     * @param customerId
     * @return
     */
    BigDecimal countAmountByCustomerId(String customerId);

}
