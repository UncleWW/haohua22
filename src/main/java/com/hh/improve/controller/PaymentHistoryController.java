package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.DateUtils;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.common.util.UUIDUtils;
import com.hh.improve.entity.Customer;
import com.hh.improve.entity.PaymentHistory;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.dto.PaymentHistoryDTO;
import com.hh.improve.entity.vo.PaymentHistoryVo;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.IPaymentHistoryService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/paymentHistory")
public class PaymentHistoryController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(PaymentHistoryController.class);

	@Autowired
	private IPaymentHistoryService paymentHistoryService;

	@RequestMapping("")
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/invoiceVoucher");
		model.addAttribute("uri", "/paymentHistory");
		return super.commonModel(model, response);
	}

	protected String getPageUri() {
		return "views/paymentHistoryManager";
	}



	@ResponseBody
	@RequestMapping("/queryAllCustomerPaymentHistoryPage")
	public BaseDTO queryAllCustomerPaymentHistoryPage(PaymentHistoryDTO paymentHistoryDTO , @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
								 @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<PaymentHistoryVo> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<PaymentHistoryVo> paymentHistoryVos = paymentHistoryService.countAllCustomerSaleMoneyPage(paymentHistoryDTO,offset, limit);
				pageResult = PageUtils.tranferPageInfo(new PageInfo<PaymentHistoryVo>(paymentHistoryVos));
			}
		} catch (Exception e) {
			LOGGER.error("PaymentHistoryController.queryAllCustomerPaymentHistoryPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<PaymentHistoryVo>();
		}
		return tranferBaseDTO(errors, pageResult);
	}

	@ResponseBody
	@RequestMapping("/queryPayHistoryByCustomerIdPage")
	public BaseDTO queryPayHistoryByCustomerIdPage(PaymentHistoryDTO paymentHistoryDTO , @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
											@RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<PaymentHistory> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<PaymentHistory> paymentHistoryVos = paymentHistoryService.queryPayHistoryByCustomerIdPage(paymentHistoryDTO,offset, limit);
				pageResult = PageUtils.tranferPageInfo(new PageInfo<PaymentHistory>(paymentHistoryVos));
			}
		} catch (Exception e) {
			LOGGER.error("PaymentHistoryController.queryPayHistoryByCustomerIdPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<PaymentHistory>();
		}
		return tranferBaseDTO(errors, pageResult);
	}

	/**
	 * 下拉查询客户列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryAllCustomer")
	public BaseDTO queryAllCustomer() {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = paymentHistoryService.getAllCustomer();
		} catch (Exception e) {
			LOGGER.error("PaymentHistoryController.queryUserPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, customerList);
	}

	@ResponseBody
	@RequestMapping("/addPayment")
	@NoRepeatRequest(2000)
	public BaseDTO addPayment(@RequestBody PaymentHistory pay) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		int result = 0;
		try {
			if(pay.getCustomerId() ==null || pay.getCustomerId().equals("")){
				ErrorInfo errorInfo = new ErrorInfo("用户名不能为空！");
				errors = Arrays.asList(errorInfo);
			}
			if(pay.getPayDate() ==null || pay.getPayDate().equals("")){
				pay.setPayDate(DateUtils.formatDate(new Date(),DateUtils.DEFAULT_DATE));
			}
			if (CollectionUtils.isEmpty(errors)) {
				pay.setPhId(UUIDUtils.getUUID32());
				pay.setCreater(getUserPhone());
				paymentHistoryService.create(pay);
			}
		} catch (BusinessException e) {
			LOGGER.error("PaymentHistoryController.customerSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("PaymentHistoryController.customerSave Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

	@ResponseBody
	@RequestMapping("/updatePayment")
	@NoRepeatRequest(2000)
	public BaseDTO updatePayment(@RequestBody PaymentHistory pay) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		int result = 0;
		try {
			if(pay.getPhId() == null || pay.getPhId().equals("")){
				ErrorInfo errorInfo = new ErrorInfo("主键不能为空！");
				errors = Arrays.asList(errorInfo);
			}
			if(pay.getCustomerId() ==null || pay.getCustomerId().equals("")){
				ErrorInfo errorInfo = new ErrorInfo("用户名不能为空！");
				errors = Arrays.asList(errorInfo);
			}
			if(pay.getPayDate() ==null || pay.getPayDate().equals("")){
				pay.setPayDate(DateUtils.formatDate(new Date(),DateUtils.DEFAULT_DATE));
			}
			if (CollectionUtils.isEmpty(errors)) {
				pay.setCreater(getUserPhone());
				paymentHistoryService.update(pay);
			}
		} catch (BusinessException e) {
			LOGGER.error("PaymentHistoryController.customerSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("PaymentHistoryController.customerSave Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

}
