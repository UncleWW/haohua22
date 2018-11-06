package com.hh.improve.controller;


import com.github.pagehelper.PageInfo;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.pojo.RoleTree;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.Role;
import com.hh.improve.entity.User;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.entity.vo.UserRoleVO;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.IRoleService;
import com.hh.improve.service.IRoleUserService;
import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	private static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private IRoleService iroleService;
	@Autowired
	private IRoleUserService iroleUserService;
	
	@RequestMapping
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/rulemanager");
		model.addAttribute("uri", "/role");
		model.addAttribute("role", "admin");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/rolePage";
	}
	
	@ResponseBody
	@RequestMapping("/queryRoleTree")
	public BaseDTO queryRoleTree(@Value("${zTree.iconOpen}")String iconOpen , @Value("${zTree.iconClose}")String iconClose) throws IllegalAccessException, InvocationTargetException {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<Role> roleList = new ArrayList<Role>();
		List<RoleTree> treeList = new ArrayList<RoleTree>();
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			if (subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
				Map<String, Object> condition = new HashMap<String, Object>();
				roleList = iroleService.getList(condition);
			}else{		
				//非超级管理员  根据登录用户查询其拥有的角色
				roleList = iroleService.getRoleSetByUser(userPhone);
			}
			for(int i= 0;i<roleList.size();i++){
				RoleTree tree = new RoleTree();
				BeanUtils.copyProperties(tree, roleList.get(i));
				if(roleList.get(i).getRoleCode().equals(IRoleService.ROOT_ROLE)){//展开二级菜单			
					tree.setOpen(true);
					tree.setIconOpen(iconOpen);
					tree.setIconClose(iconClose);
				}
				tree.setpId(roleList.get(i).getParentRoleCode());
				tree.setName(roleList.get(i).getRoleName());
				tree.setT(roleList.get(i).getRoleName());
				tree.setId(roleList.get(i).getRoleCode());
				tree.setParent(true);
				treeList.add(tree);
			}
		} catch (Exception e) {
			LOGGER.error("RoleController.queryRoleTree Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, treeList);
	}
	/**
	 * 新增 资源信息
	 * @author 011336wangwei3
	 * @param role
	 * @return 
	 * @DATE 2017/08/30
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	@NoRepeatRequest(2000)
	public BaseDTO saveRole(Role role) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		role.setCreater(userPhone);
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			if(iroleService.isAlreadyExist(role)){
				ErrorInfo errorInfo =  new ErrorInfo("code","角色编码已存在");
				errors.add(errorInfo);
			}else{
				iroleService.create(role);	
			}
		} catch (Exception e) {
			LOGGER.error("RoleController.saveRole Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
	/**
	 *  修改   资源信息
	 * @author 011336wangwei3
	 * @param role
	 * @return 
	 * @DATE 2017/08/29
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public BaseDTO updateRole(Role role) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		role.setModifier(userPhone);
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			if(CommonUtils.isEmpty(role.getRoleCode())){
				ErrorInfo errorInfo =  new ErrorInfo("角色编码不能为空");
				errors.add(errorInfo);
			}else{
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("roleCode", role.getRoleCode());
				if(CollectionUtils.isEmpty(errors)){
					iroleService.update(role);
					if(role.getStatus().equals("0")){
						iroleService.deleteChildrens(role);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("RoleController.updateRole Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
	/**
	 *  修改   资源信息
	 * @author 011336wangwei3
	 * @param role
	 * @return 
	 * @DATE 2017/08/29
	 */
	@RequestMapping("/delRole")
	@ResponseBody
	public BaseDTO delRole(Role role) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		role.setModifier(userPhone);
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("roleCode", role.getRoleCode());
			if(CollectionUtils.isEmpty(errors)){
				iroleService.deleteChildrens(role);
			}
		} catch (Exception e) {
			LOGGER.error("RoleController.delRole Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
	/**
	 *  加载   角色用户
	 * @author 011336wangwei3
	 * @param userRoleVO psnCode userName deptName  start limit
	 * @return 
	 * @DATE 2017/08/29
	 */
	
	
	@ResponseBody
	@RequestMapping("/queryUserRole")
	public BaseDTO queryUserRole(UserRoleVO userRoleVO, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
						    @RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<User> pageResult = null;
		try {
			List<User> userList = iroleUserService.selectUserRole(userRoleVO, offset, limit);
			if (CollectionUtils.isEmpty(userList)) {
				int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
				if (offset > defaultOffset) {
					userList = iroleUserService.selectUserRole(userRoleVO, defaultOffset, limit);
				}
			}
			// get page info
			pageResult = PageUtils.tranferPageInfo(new PageInfo<User>(userList));
			}
		 catch (Exception e) {
			LOGGER.error("RoleController.queryUserRole Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, pageResult);
	}
	/**
	 *  加载   未分配角色用户
	 * @author 011336wangwei3
	 * @param userRoleVO psnCode userName deptName  start limit
	 * @return 
	 * @DATE 2017/08/29
	 */
	
	@ResponseBody
	@RequestMapping("/queryUserNoRole")
	public BaseDTO queryUserNoRole(UserRoleVO userRoleVO, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset,
			@RequestParam(defaultValue = "10") int limit) {
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		PageResult<User> pageResult = null;
		try {
			List<User> userList = iroleUserService.selectUserNoRole(userRoleVO, offset, limit);
			if (CollectionUtils.isEmpty(userList)) {
				int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
				if (offset > defaultOffset) {
					userList = iroleUserService.selectUserNoRole(userRoleVO, defaultOffset, limit);
				}
			}
			// get page info
			pageResult = PageUtils.tranferPageInfo(new PageInfo<User>(userList));
			}
		 catch (Exception e) {
			LOGGER.error("RoleController.queryUserNoRole Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, pageResult);
	}
	/**
	 *  修改  用户角色
	 * @author 011336wangwei3
	 * @param roleCode psnCode userName deptName  start limit
	 * @return 
	 * @throws Exception 
	 * @DATE 2017/09/06
	 */
	@RequestMapping("/changeUserRole")
	@ResponseBody
	public BaseDTO changUserRole(String[] userIds, String roleCode, String status) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		// 不能修改自身角色的用户信息
		try {
			if (null != userIds) {
				if (!subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
					for (int i = 0; i < userIds.length; i++) {
						if(userPhone.equals(userIds[i])){
							ErrorInfo errorInfo =  new ErrorInfo("不能修改自身的角色信息");
							errors.add(errorInfo);
							return tranferBaseDTO(errors, null);
						}
					}
				}
				Map<String, Object> cond = new HashMap<String, Object>();
				for (int i = 0; i < userIds.length; i++) {
					cond.put("userPhone", userIds[i]);
					cond.put("roleCode", roleCode);
					cond.put("status", status);
					cond.put("creater", userPhone);
					cond.put("modifier", userPhone);
					iroleUserService.updateUserRole(cond);
				}
			}
		} catch (Exception e) {
			LOGGER.error("RoleController.changUserRole Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
}
