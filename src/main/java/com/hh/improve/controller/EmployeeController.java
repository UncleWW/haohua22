package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.Employee;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.IEmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private IEmployeeService employeeService;

	@RequestMapping("")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("parentUri", "/baseInformation");
		model.addAttribute("uri", "/employee");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/employeeManager";
	}

	@ResponseBody
	@RequestMapping("/queryEmployeePage")
	public BaseDTO queryUserPage(Employee employee, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
						    @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<Employee> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<Employee> employeeList = employeeService.getEmployeePageList(employee, offset, limit);
				if (CollectionUtils.isEmpty(employeeList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						employeeList = employeeService.getEmployeePageList(employee, defaultOffset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<Employee>(employeeList));
			}
		} catch (Exception e) {
			LOGGER.error("UserController.queryUserPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<Employee> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}
	@ResponseBody
	@RequestMapping("/employeeSave")
	@NoRepeatRequest(2000)
	public BaseDTO employeeSave(@RequestBody Employee employee) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();;
		int result = 0;
		try {
			Employee existEmployee = employeeService.getEmployeeByPhone(employee.getEmployeePhone());
			if(existEmployee!=null){
				ErrorInfo errorInfo = new ErrorInfo("employeeExist", "手机号已被使用");
				errors.add(errorInfo);
			}
			if (CollectionUtils.isEmpty(errors)) {
				employeeService.employeeSave(employee);
			}
		} catch (BusinessException e) {
			LOGGER.error("EmployeeController.employeeSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("EmployeeController.employeeSave Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

	@ResponseBody
	@RequestMapping("/employeeUpdate")
	@NoRepeatRequest(2000)
	public BaseDTO employeeUpdate(@RequestBody Employee employee) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();;
		int result = 0;
		try {
			Employee existEmployee = employeeService.getEmployeeByPhone(employee.getEmployeePhone());
			if(existEmployee==null){
				ErrorInfo errorInfo = new ErrorInfo("userIsNull", "员工不存在");
				errors.add(errorInfo);
			}
			if (CollectionUtils.isEmpty(errors)) {
				employeeService.employeeUpdate(employee);
			}
		} catch (BusinessException e) {
				LOGGER.error("EmployeeController.employeeUpdate BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("EmployeeController.employeeUpdate Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}



}
