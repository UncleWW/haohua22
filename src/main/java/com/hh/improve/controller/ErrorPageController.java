/**
 * 
 */
package com.hh.improve.controller;


import com.hh.improve.common.constants.ErrorConstants;
import com.hh.improve.shiro.service.IAuthorizationService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 011589
 *
 */
@Controller
@RequestMapping("/error")
public class ErrorPageController implements ErrorController {

	@RequestMapping
	public String error(HttpServletRequest request, HttpServletResponse response, Model model) {
		int responseStatus = response.getStatus();
		if (500 == responseStatus) {
			model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SYSTEM_500);
		} else if (401 == responseStatus) {
			model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SYSTEM_401);
		} else if (404 == responseStatus) {
			model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SYSTEM_404);
		} else {
			model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SYSTEM_001);
		}
		return "views/errorPage";
	}
	
	@RequestMapping("/casfailure")
	public String casfailure(HttpServletRequest request, HttpServletResponse response, Model model) {
		if (IAuthorizationService.USER_INVALID_MESSAGE.equalsIgnoreCase((String) request.getParameter("errorCode"))) {
			model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_USER_001);
			return "views/errorPage";
		}
		model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_USER_002);
		return "views/errorPage";
	}
	
	@RequestMapping("/unauthorized")
	public String unauthorized(Model model) {
		model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SYSTEM_002);
		return "views/errorPage";
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.web.servlet.error.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {
		return null;//"views/errorPage";
	}
	
}
