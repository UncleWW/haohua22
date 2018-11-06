package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.common.util.UUIDUtils;
import com.hh.improve.entity.Customer;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.ICustomerService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping("")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("parentUri", "/baseInformation");
		model.addAttribute("uri", "/customer");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/customerManager";
	}

	@ResponseBody
	@RequestMapping("/queryCustomerPage")
	public BaseDTO queryCustomerPage(Customer customer, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
						    @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<Customer> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<Customer> customerList = customerService.getCustomerPageList(customer, offset, limit);
				if (CollectionUtils.isEmpty(customerList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						customerList = customerService.getCustomerPageList(customer, offset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<Customer>(customerList));
			}
		} catch (Exception e) {
			LOGGER.error("UserController.queryUserPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<Customer> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}


	@ResponseBody
	@RequestMapping("/customerSave")
	@NoRepeatRequest(2000)
	public BaseDTO customerSave(@RequestBody Customer customer) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();;
		int result = 0;
		try {
			if(customer.getCustomerName() ==null || customer.getCustomerName().equals("")){
				ErrorInfo errorInfo = new ErrorInfo("用户名不能为空！");
				errors = Arrays.asList(errorInfo);
			}
			if (CollectionUtils.isEmpty(errors)) {
				customer.setCustomerId(UUIDUtils.getUUID32());
				customerService.create(customer);
			}
		} catch (BusinessException e) {
			LOGGER.error("CustomerController.customerSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("CustomerController.customerSave Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

	@ResponseBody
	@RequestMapping("/customerUpdate")
	@NoRepeatRequest(2000)
	public BaseDTO customerUpdate(@RequestBody Customer customer) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();;
		int result = 0;
		try {
			if(customer.getCustomerName() ==null || customer.getCustomerName().equals("")){
				ErrorInfo errorInfo = new ErrorInfo("用户名不能为空！");
				errors = Arrays.asList(errorInfo);
			}
			if (CollectionUtils.isEmpty(errors)) {
				customerService.update(customer);
			}
		} catch (BusinessException e) {
			LOGGER.error("CustomerController.customerSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("CustomerController.customerSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}



}
