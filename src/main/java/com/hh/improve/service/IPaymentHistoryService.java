package com.hh.improve.service;

import com.hh.improve.entity.Customer;
import com.hh.improve.entity.PaymentHistory;
import com.hh.improve.entity.dto.PaymentHistoryDTO;
import com.hh.improve.entity.vo.PaymentHistoryVo;

import java.util.List;

public interface IPaymentHistoryService extends IBaseService<PaymentHistory>{

    /**
     * 分页查询所有客户的 累计销售金额，累计收款，累计欠款
     * @param paymentHistoryDTO
     * @param offset
     * @param limit
     * @return
     */
    List<PaymentHistoryVo> countAllCustomerSaleMoneyPage(PaymentHistoryDTO paymentHistoryDTO , int offset, int limit);

    /**
     * 根据客户id和时间 查询收款记录
     * @param paymentHistoryDTO
     * @param offset
     * @param limit
     * @return
     */
    List<PaymentHistory> queryPayHistoryByCustomerIdPage(PaymentHistoryDTO paymentHistoryDTO , int offset, int limit);

    /**
     * 获取客户下拉列表
     * @return
     */
    List<Customer> getAllCustomer();
}
