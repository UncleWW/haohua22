/**
 * 
 */
package com.hh.improve.controller;

import com.hh.improve.common.constants.ErrorConstants;
import com.hh.improve.common.util.Md5Utils;
import com.hh.improve.service.ISuperAdminService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 011589
 *
 */
@Controller
@RequestMapping("/super")
public class SuperAdminController extends BaseController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SuperAdminController.class);
	
	@Autowired
	private ISuperAdminService superAdminService;
	
	@Value("${server.servlet.context-path}")
	private String appPath;
	
	@RequestMapping
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			try {
				response.sendRedirect(appPath);
				return null;
			} catch (IOException e) {
				LOGGER.error("SuperAdminController index response.sendRedirect Exception", e);
				e.printStackTrace();
			}
		}
		model.addAttribute("uri", "/super");
		return getPageUri();
	}
	
	protected String getPageUri() {
		return "views/superLoginPage";
		
	}
	
	@RequestMapping("/login")
	public String login(ModelMap modal, String superAdminUserName, String superAdminPassword,
                         HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(superAdminUserName) && StringUtils.isNotBlank(superAdminPassword)) {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(superAdminUserName.trim(), Md5Utils.hash(superAdminPassword.trim()));
			try {
				subject.login(usernamePasswordToken);
				response.sendRedirect("/");
				return null;
			}catch (UnknownAccountException e) {
				return "views/superLoginPage";
			}catch (IncorrectCredentialsException e) {
				//return "账号或密码不正确";
				return "views/superLoginPage";
			}catch (LockedAccountException e) {
				//return "账号已被锁定,请联系管理员";
				return "views/superLoginPage";
			}catch (AuthenticationException e) {
				//return "账户验证失败";
				usernamePasswordToken.clear();
				return "views/superLoginPage";
			} catch (IOException e) {
				e.printStackTrace();
				return "views/superLoginPage";
			}

		} else {
			modal.put(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SUPERADMIN_001);
		}
		return "views/superLoginPage";
	}
}
