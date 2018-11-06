package com.hh.improve.controller;


import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.entity.dto.BaseDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class HomeController extends BaseController {
    @RequestMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("parentUri", "/ROOT");
        model.addAttribute("uri", "/");
        return super.commonModel(model, response);
    }

    protected String getPageUri() {
        return "views/homePage";
    }
    private static Logger LOGGER = LoggerFactory.getLogger(HomeController.class);


    @ResponseBody
    @RequestMapping("/queryHomePage")
    public BaseDTO queryHomePage() {
        List<ErrorInfo> errors = new ArrayList<ErrorInfo>();

        try {
            // valid request data
            if (CollectionUtils.isEmpty(errors)) {
                Map<String, Object> condition = null;

            }
        } catch (Exception e) {
            LOGGER.error("HomePageController.queryHomePage Exception: ", e);
            ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
            errors = Arrays.asList(errorInfo);
        }

        return tranferBaseDTO(errors, null);
    }
}

