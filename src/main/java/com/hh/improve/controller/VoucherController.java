package com.hh.improve.controller;

import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.entity.Customer;
import com.hh.improve.entity.VoucherSon;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.vo.VoucherVo;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.ICustomerService;
import com.hh.improve.service.IVoucherService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/voucher")
public class VoucherController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(VoucherController.class);
	@Autowired
	private IVoucherService voucherService;

	@Autowired
	private ICustomerService customerService;

	@RequestMapping("")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("parentUri", "/invoiceVoucher");
		model.addAttribute("uri", "/voucher");
		String voucherId = request.getParameter("voucherId");
		model.addAttribute("voucherId", voucherId);
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/voucherManager";
	}

	@ResponseBody
	@RequestMapping("/getNewMaxVoucherId")
	public BaseDTO getNewMaxVoucherId() {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		String voucherId = "";
		try {
			voucherId = voucherService.getNewMaxVoucherId();
		} catch (Exception e) {
			LOGGER.error("VoucherController.getNewMaxVoucherId Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, voucherId);
	}

	@ResponseBody
	@RequestMapping("/queryLastVoucher")
	public BaseDTO queryLastVoucher() {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		VoucherVo voucherVo = new VoucherVo();
		try {
			voucherVo = voucherService.queryLastVoucher();
		} catch (Exception e) {
			LOGGER.error("VoucherController.queryLastVoucher Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, voucherVo);
	}

    @ResponseBody
    @RequestMapping("/queryVoucherById")
    public BaseDTO queryVoucherById(String voucherId) {
        List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
        VoucherVo voucherVo = new VoucherVo();
        try {
            voucherVo = voucherService.queryVoucherById(voucherId);
        } catch (Exception e) {
            LOGGER.error("VoucherController.queryVoucherById Exception: ", e);
            ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
            errors = Arrays.asList(errorInfo);
        }
        return tranferBaseDTO(errors, voucherVo);
    }

	@ResponseBody
	@RequestMapping("/queryAroundByVoucherId")
	public BaseDTO queryAroundByVoucherId(String voucherId,int type) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		VoucherVo voucherVo = new VoucherVo();
		try {
			if(StringUtils.isEmpty(voucherId)){
				ErrorInfo errorInfo = new ErrorInfo("无法查询凭证，当前凭证号为空");
				errors = Arrays.asList(errorInfo);
			}
			if(errors.size()<1) {
				voucherVo = voucherService.queryAroundByVoucherId(voucherId, type);
			}
		} catch (Exception e) {
			LOGGER.error("VoucherController.queryAroundByVoucherId Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, voucherVo);
	}

	@ResponseBody
	@RequestMapping("/voucherSaveOrUpdate")
	@NoRepeatRequest(2000)
	public BaseDTO voucherSaveOrUpdate(@RequestBody VoucherVo voucherVo) {
         Subject subject = SecurityUtils.getSubject();
         String userPhone = getUserPhone();
		List<ErrorInfo> errors = checkErrors(voucherVo);
		try {
			if(errors.size()<1){
                    voucherVo.setCreater(userPhone);
                    voucherVo.setModifier(userPhone);
				voucherService.voucherSaveOrUpdate(voucherVo);
			}
		} catch (BusinessException e) {
			LOGGER.error("VoucherController.voucherSaveOrUpdate BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("VoucherController.voucherSaveOrUpdate Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}

	List<ErrorInfo>  checkErrors(VoucherVo voucherVo){
		List<VoucherSon> sonList = voucherVo.getSonList();
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		if(StringUtils.isEmpty(voucherVo.getCustomerId())){
			ErrorInfo errorInfo = new ErrorInfo("没有选择客户");
			errors = Arrays.asList(errorInfo);
		}
		if(StringUtils.isEmpty(voucherVo.getVoucherDate())){
			ErrorInfo errorInfo = new ErrorInfo("凭证日期不能为空");
			errors = Arrays.asList(errorInfo);
		}
		if(sonList==null || sonList.size()<1){
			ErrorInfo errorInfo = new ErrorInfo("list为空");
			errors = Arrays.asList(errorInfo);
		}
		for (VoucherSon voucherSon:sonList) {
			if(StringUtils.isEmpty(voucherSon.getGoodsName())){
				ErrorInfo errorInfo = new ErrorInfo("第"+voucherSon.getGoodsId()+"列，商品名称为空");
				errors = Arrays.asList(errorInfo);
			}
		}
		return errors;
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
			Map<String, Object> condition = new HashMap<String, Object>();
			customerList = customerService.getList(condition);
		} catch (Exception e) {
			LOGGER.error("VoucherController.queryUserPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, customerList);
	}

	@ResponseBody
	@RequestMapping("/queryAccumulateDebtById")
	public BaseDTO queryAccumulateDebtById(String customerId) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		BigDecimal AccumulateDebt = new BigDecimal(0);
		try {
			if(StringUtils.isEmpty(customerId)){
				ErrorInfo errorInfo = new ErrorInfo("客户id为空，无法查询累计欠款！");
				errors = Arrays.asList(errorInfo);
			}
			if(errors.size()> 0) {
				return tranferBaseDTO(errors, AccumulateDebt);
			}
			AccumulateDebt = voucherService.queryAccumulateDebtById(customerId);
		} catch (Exception e) {
			LOGGER.error("VoucherController.queryAccumulateDebtById Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, AccumulateDebt);
	}
}
