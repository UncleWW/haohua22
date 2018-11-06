package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.PayRoll;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.vo.PayRollVo;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.IPayRollService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/payRoll")
public class PayRollController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(PayRollController.class);
	@Autowired
	private IPayRollService payRollService;


	@RequestMapping("")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("parentUri", "/salaryManager");
		model.addAttribute("uri", "/payRoll");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/payRollManager";
	}

	@ResponseBody
	@RequestMapping("/queryPayRollVoPage")
	public BaseDTO queryPayRollPage(PayRollVo payRollVo, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
							  @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<PayRollVo> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<PayRollVo> payRollVoList = payRollService.getPayRollVoPageList(payRollVo, offset, limit);
				if (CollectionUtils.isEmpty(payRollVoList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						payRollVoList = payRollService.getPayRollVoPageList(payRollVo, defaultOffset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<PayRollVo>(payRollVoList));
			}
		} catch (Exception e) {
			LOGGER.error("PayRollVoController.queryPayRollPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<PayRollVo> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}

	@ResponseBody
	@RequestMapping("/queryHistoryPayRollVoPage")
	public BaseDTO queryHistoryPayRollVoPage(PayRollVo payRollVo, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
							  @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<PayRollVo> pageResult = null;
		try {
			if (CollectionUtils.isEmpty(errors)) {
				List<PayRollVo> payRollVoList = payRollService.queryHistoryPayRollVoPage(payRollVo, offset, limit);
				if (CollectionUtils.isEmpty(payRollVoList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						payRollVoList = payRollService.queryHistoryPayRollVoPage(payRollVo, defaultOffset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<PayRollVo>(payRollVoList));
			}
		} catch (Exception e) {
			LOGGER.error("PayRollVoController.queryPayRollPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<PayRollVo> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}

	@ResponseBody
	@RequestMapping("/createNewMonthBill")
	public BaseDTO createNewMonthBill() {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("salaryDate",payRollService.dealEndDate());
			List<PayRoll> payRolls =  payRollService.getList(condition);
			if(payRolls!=null && payRolls.size()>0){
				ErrorInfo errorInfo = new ErrorInfo("本月账单已经创建过了！");
				errors = Arrays.asList(errorInfo);
			}else{
				payRollService.createNewMonthBill(userPhone);
			}
		} catch (Exception e) {
			LOGGER.error("PayRollVoController.queryPayRollPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}

	@ResponseBody
	@RequestMapping("/savePayRoll")
	@NoRepeatRequest(2000)
	public BaseDTO savePayRoll(@RequestBody PayRoll payRoll) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<ErrorInfo> errors = ckechPayRoll(payRoll);
		int result = 0;
		try {
			if (CollectionUtils.isEmpty(errors)){
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("payrollId",payRoll.getPayrollId());
				List<PayRoll> payRolls =  payRollService.getList(condition);
				if(payRolls!=null && payRolls.size()>0){
					PayRoll resultPay =payRolls.get(0);
					resultPay.setSalaryPaid(payRoll.getSalaryPaid());
					resultPay.setRemark(payRoll.getRemark());
					resultPay.setModifier(userPhone);
					payRollService.update(resultPay);
				}
			}
		} catch (BusinessException e) {
			LOGGER.error("SalaryController.salarySave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("SalaryController.salarySave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

	List<ErrorInfo> ckechPayRoll(PayRoll payRoll){
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		if( payRoll.getPayrollId()==null || payRoll.getPayrollId().equals("") ){
			ErrorInfo errorInfo =  new ErrorInfo("Code","主键丢失");
			errors.add(errorInfo);
		}
		return errors;
	}
	public static void main(String args[]){
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());
		rightNow.add(Calendar.MONTH,0);
		Date dt1=rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
	}
}
