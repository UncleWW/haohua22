package com.hh.improve.controller;

import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.service.IReportVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/reportVoucher")
public class ReportVoucherController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(ReportVoucherController.class);
	
	@Autowired
	private IReportVoucherService iReportVoucherService;
	
	@RequestMapping("")
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/reportEchart");
		model.addAttribute("uri", "/reportVoucher");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/reportVoucherManager";
	}



    @ResponseBody
    @RequestMapping("/getTestData")
    public Object getTestData() {
        List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
        HashMap<String, Object> result = new HashMap<>();
        //销售数值
        List<List<String>> data = new ArrayList<>();
        try {
            data = iReportVoucherService.getReportVoucherVo();
            List<String> months = iReportVoucherService.getAllMonths();
            List<String> customerNames = iReportVoucherService.getAllCustomer();
            customerNames.add("总额");
            result.put("data",data);
            result.put("months",months);
            result.put("names",customerNames);
            result.put("resultFlag","success");
        } catch (Exception e) {
            LOGGER.error("ReportVoucherController.getTestData Exception: ", e);
            ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
            errors = Arrays.asList(errorInfo);
        }
        return result;
    }


}
