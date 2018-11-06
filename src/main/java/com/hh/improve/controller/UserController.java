package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.exception.BusinessException;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.User;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.IUserService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("")
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/rulemanager");
		model.addAttribute("uri", "/user");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/userManager";
	}

	@ResponseBody
	@RequestMapping("/queryUserPage")
	public BaseDTO queryUserPage(User user, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
						    @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<User> pageResult = null;
		try {
			// valid request data
			//errors = ParamUtils.validUserStatus(userVO.getStatus());
			if (CollectionUtils.isEmpty(errors)) {
				// invoke service to get user page data
				List<User> userList = userService.getUserPageList(user, offset, limit);
				if (CollectionUtils.isEmpty(userList)) {
					int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
					if (offset > defaultOffset) {
						userList = userService.getUserPageList(user, defaultOffset, limit);
					}
				}
				// get page info
				pageResult = PageUtils.tranferPageInfo(new PageInfo<User>(userList));
			}
		} catch (Exception e) {
			LOGGER.error("UserController.queryUserPage Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == pageResult) {
			pageResult = new PageResult<User> ();
		}
		return tranferBaseDTO(errors, pageResult);
	}

	@ResponseBody
	@RequestMapping("/userSave")
	@NoRepeatRequest(2000)
	public BaseDTO userSave(@RequestBody User user) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();;
		int result = 0;
		try {
			User existUser = userService.getUserByPhone(user.getUserPhone());
			if(existUser!=null){
				ErrorInfo errorInfo = new ErrorInfo("userExist", "用户已存在");
				errors = Arrays.asList(errorInfo);
			}
			if (CollectionUtils.isEmpty(errors)) {
				userService.userSave(user);
			}
		} catch (BusinessException e) {
			LOGGER.error("UserController.userDetailSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("UserController.userDetailSave Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

	@ResponseBody
	@RequestMapping("/userUpdate")
	@NoRepeatRequest(2000)
	public BaseDTO userUpdate(@RequestBody User user) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();;
		int result = 0;
		try {
			User existUser = userService.getUserByPhone(user.getUserPhone());
			if(existUser==null ){
				ErrorInfo errorInfo = new ErrorInfo("userIsNull", "用户不存在");
				errors = Arrays.asList(errorInfo);
			}
			if (CollectionUtils.isEmpty(errors)) {
				userService.userUpdate(user);
			}
		} catch (BusinessException e) {
			LOGGER.error("UserController.userDetailSave BusinessException: ", e);
			ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getErrorMsg());
			errors = Arrays.asList(errorInfo);
		} catch (Exception e) {
			LOGGER.error("UserController.userDetailSave Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, result);
	}

}
