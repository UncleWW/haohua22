package com.hh.improve.controller;

import com.hh.improve.common.util.Md5Utils;
import com.hh.improve.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/signIn")
public class LoginController {

	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserService userService;

	@RequestMapping
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/rulemanager");
		model.addAttribute("uri", "/user");
		return getPageUri();
	}
	
	protected String getPageUri() {
		return "views/userLogin";
	}

	@RequestMapping("/login")
	public String queryUserPage(ModelMap modal, String username, String password,
						   HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, Md5Utils.hash(password));
			try {
				subject.login(usernamePasswordToken);
				response.sendRedirect("/");
				return null;
			}catch (UnknownAccountException e) {
				return e.getMessage();
			}catch (IncorrectCredentialsException e) {
				return "账号或密码不正确";
			}catch (LockedAccountException e) {
				return "账号已被锁定,请联系管理员";
			} catch (IOException e) {
				e.printStackTrace();
				return "views/superLoginPage";
			}catch (AuthenticationException e) {
				//return "账户验证失败";
				usernamePasswordToken.clear();
				return "views/userLogin";
			}
		}
		return "views/userLogin";
	}
}
