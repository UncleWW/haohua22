package com.hh.improve.dao;


import com.hh.improve.entity.Customer;
import com.hh.improve.entity.PaymentHistory;
import com.hh.improve.entity.vo.PaymentHistoryVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IPaymentHistoryDao extends IBaseDao<PaymentHistory> {
    /**
     * 根据查询条件到 凭证表按客户id分组统计  销售金额
     * @param condition
     * @return
     */
    List<PaymentHistoryVo> countAllCustomerSaleMoneyPage(Map<String, Object> condition);

    /**
     * 根据查询条件到  到收款记录表按客户id分组统计  收款金额
     * @param condition
     * @return
     */
    List<PaymentHistoryVo> countAllCustomerPayMoneyPage(Map<String, Object> condition);

    /**
     * 根据查询条件 查询收款记录
     * @param condition
     * @return
     */
    List<PaymentHistory> queryPayHistoryByCustomerIdPage(Map<String, Object> condition);

    /**
     * 获取客户下拉列表
     * @return
     */
    List<Customer> getAllCustomer();

    /**
     * 根据 客户Id 统计收款总额
     * @param customerId
     * @return
     */
    BigDecimal countPayHistoryByCustomerId(String customerId);
}
