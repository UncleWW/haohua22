package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.vo.VoucherSearch;
import com.hh.improve.entity.vo.VoucherVo;
import com.hh.improve.service.IVoucherService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/voucherList")
public class VoucherListController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(VoucherListController.class);
	
	@Autowired
	private IVoucherService voucherService;
	
	@RequestMapping("")
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/invoiceVoucher");
		model.addAttribute("uri", "/voucherList");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/voucherListManager";
	}



    @ResponseBody
    @RequestMapping("/queryVoucherListPage")
    public BaseDTO queryVoucherListPage(VoucherSearch voucherSearch , @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
                                        @RequestParam(defaultValue = "10") int limit) {
        List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
        PageResult<VoucherVo> pageResult = null;
        try {
            if (CollectionUtils.isEmpty(errors)) {
                List<VoucherVo> voucherVoList = voucherService.queryVoucherListPage(voucherSearch, offset, limit);
                if (CollectionUtils.isEmpty(voucherVoList)) {
                    int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
                    if (offset > defaultOffset) {
                        voucherVoList = voucherService.queryVoucherListPage(voucherSearch, defaultOffset, limit);
                    }
                }
                // get page info
                pageResult = PageUtils.tranferPageInfo(new PageInfo<VoucherVo>(voucherVoList));
            }
        } catch (Exception e) {
            LOGGER.error("VoucherListController.queryVoucherListPage Exception: ", e);
            ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
            errors = Arrays.asList(errorInfo);
        }
        if (null == pageResult) {
            pageResult = new PageResult<VoucherVo>();
        }
        return tranferBaseDTO(errors, pageResult);
    }

    @ResponseBody
    @RequestMapping("/queryVoucherSonListPage")
    public BaseDTO queryVoucherSonListPage(VoucherSearch voucherSearch , @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
                                        @RequestParam(defaultValue = "10") int limit) {
        List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
        PageResult<VoucherVo> pageResult = null;
        try {
            if (CollectionUtils.isEmpty(errors)) {
                List<VoucherVo> voucherVoList = voucherService.queryVoucherSonListPage(voucherSearch, offset, limit);
                if (CollectionUtils.isEmpty(voucherVoList)) {
                    int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
                    if (offset > defaultOffset) {
                        voucherVoList = voucherService.queryVoucherSonListPage(voucherSearch, defaultOffset, limit);
                    }
                }
                // get page info
                pageResult = PageUtils.tranferPageInfo(new PageInfo<VoucherVo>(voucherVoList));
            }
        } catch (Exception e) {
            LOGGER.error("VoucherListController.queryVoucherListPage Exception: ", e);
            ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
            errors = Arrays.asList(errorInfo);
        }
        if (null == pageResult) {
            pageResult = new PageResult<VoucherVo>();
        }
        return tranferBaseDTO(errors, pageResult);
    }
}
