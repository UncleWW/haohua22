package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.common.util.DefaultValueUtil;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.IPaymentHistoryDao;
import com.hh.improve.entity.Customer;
import com.hh.improve.entity.PaymentHistory;
import com.hh.improve.entity.dto.PaymentHistoryDTO;
import com.hh.improve.entity.vo.PaymentHistoryVo;
import com.hh.improve.service.IPaymentHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentHistoryServiceImpl extends BaseServiceImpl<PaymentHistory> implements IPaymentHistoryService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PaymentHistoryServiceImpl.class);
	
	@Autowired
	private IPaymentHistoryDao paymentHistoryDao;

	@Override
	protected IBaseDao<PaymentHistory> getBaseDao() {
		return paymentHistoryDao;
	}

	@Override
	public List<PaymentHistoryVo> countAllCustomerSaleMoneyPage(PaymentHistoryDTO paymentHistoryDTO , int offset, int limit){
		PageHelper.offsetPage(offset, limit);
		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("customerName", paymentHistoryDTO.getCustomerName());
		condition.put("payDateBegin", paymentHistoryDTO.getPayDateBegin());
		condition.put("payDateEnd", paymentHistoryDTO.getPayDateEnd());
		//到凭证表按客户id分组统计  销售金额
		List<PaymentHistoryVo> saleMoneys = paymentHistoryDao.countAllCustomerSaleMoneyPage(condition);
		//到收款记录表按客户id分组统计  收款金额
		List<PaymentHistoryVo> payMoneys = paymentHistoryDao.countAllCustomerPayMoneyPage(condition);
		if(saleMoneys==null){
			return null;
		}
		//默认 销售比收款多   所以这里吧收款与销售合到一起，以销售list为主
		List<PaymentHistoryVo> result = new ArrayList<>();
		for(int i=0;i<saleMoneys.size();i++){
			PaymentHistoryVo sale = saleMoneys.get(i);
			sale.setTotaldebtAmount(DefaultValueUtil.bigDecimal(sale.getTotalAmount()));
			for (int j=0;j<payMoneys.size();j++){
				PaymentHistoryVo pay = payMoneys.get(j);
				if(sale.getCustomerId().equals(pay.getCustomerId())){
					sale.setTotalPayAmount(DefaultValueUtil.bigDecimal(pay.getTotalPayAmount()));
					BigDecimal totaldebtAmount = DefaultValueUtil.bigDecimal(sale.getTotalAmount()).subtract(DefaultValueUtil.bigDecimal(pay.getTotalPayAmount()));
					sale.setTotaldebtAmount(totaldebtAmount);
					break;
				}
			}
			result.add(sale);
		}
		return result;
	}

	@Override
	public List<PaymentHistory> queryPayHistoryByCustomerIdPage(PaymentHistoryDTO paymentHistoryDTO , int offset, int limit){
		PageHelper.offsetPage(offset, limit);
		// 根据条件查询主表数据
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("customerId", paymentHistoryDTO.getCustomerId());
		condition.put("payDateBegin", paymentHistoryDTO.getPayDateBegin());
		condition.put("payDateEnd", paymentHistoryDTO.getPayDateEnd());
		//根据客户id 和时间查询收款记录
		return paymentHistoryDao.queryPayHistoryByCustomerIdPage(condition);
	}

	@Override
	public List<Customer> getAllCustomer(){
		return paymentHistoryDao.getAllCustomer();
	}
}
