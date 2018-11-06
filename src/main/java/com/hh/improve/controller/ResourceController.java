/**
 * 
 */
package com.hh.improve.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.PageResult;
import com.hh.improve.common.pojo.ResTree;
import com.hh.improve.common.util.PageUtils;
import com.hh.improve.entity.Resource;
import com.hh.improve.entity.Role;
import com.hh.improve.entity.RoleResource;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import com.hh.improve.service.IResourceService;
import com.hh.improve.service.IRoleResourceService;
import com.hh.improve.service.IRoleService;
import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

/**
 * @author sunyong-011589
 * @date 2017年8月25日
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
	private static Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);
	@Autowired
	private IResourceService iResourceService;
	@Autowired
	private IAuthorizationService authorizationService;
	@Autowired
	private IRoleResourceService iRoleResourceService;
	@Autowired
	private IRoleService iroleService;
	
	@RequestMapping
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/rulemanager");
		model.addAttribute("uri", "/resource");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/resourcePage";
	}
	

	@RequestMapping("/queryResourcePage")
	public PageResult<Resource> queryUserPage(Resource resource, @RequestParam(defaultValue = WebConstants.DEFAULT_PAGE_OFFSET) int offset, @RequestParam(defaultValue = "10") int limit) {
		List<Resource> result = iResourceService.getPageList(resource, offset, limit);
		if (CollectionUtils.isEmpty(result)) {
			int defaultOffset = Integer.valueOf(WebConstants.DEFAULT_PAGE_OFFSET).intValue();
			if (offset > defaultOffset) {
				iResourceService.getPageList(resource, defaultOffset, limit);
			}
		}
		return PageUtils.tranferPageInfo(new PageInfo<Resource>(result));
	}
	@ResponseBody
	@RequestMapping("/queryResTree")
	public BaseDTO queryResTree(@Value("${zTree.iconOpen}")String iconOpen , @Value("${zTree.iconClose}")String iconClose) throws IllegalAccessException, InvocationTargetException {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<Resource> resList = new ArrayList<Resource>();
		List<ErrorInfo> errors =new ArrayList<ErrorInfo>();
		List<ResTree> treeList = null;
		try {
			if (subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
				Map<String, Object> condition = new HashMap<String, Object>();
				resList = iResourceService.getList(condition);
			}else{
				//非超级管理员  根据登录用户查询其拥有的资源
				resList = iResourceService.getResSetByUser(userPhone);
			}
			treeList = new ArrayList<ResTree>();
			for(int i= 0;i<resList.size();i++){
				ResTree tree = new ResTree();
				BeanUtils.copyProperties(tree, resList.get(i));
				if(resList.get(i).getResCode().equals(IResourceService.ROOT_RESOURCE)){//展开二级菜单			
					tree.setOpen(true);
					tree.setIconOpen(iconOpen);
					tree.setIconClose(iconClose);
				}
				tree.setpId(resList.get(i).getParentResCode());
				tree.setName(resList.get(i).getResName());
				tree.setId(resList.get(i).getResCode());
				tree.setT(resList.get(i).getResName());
				tree.setParent(true);
				treeList.add(tree);
			}
		}catch (Exception e) {
			LOGGER.error("ResourceController.queryResTree Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == treeList) {
			treeList = new ArrayList<ResTree>();
		}
		return tranferBaseDTO(errors, treeList);
	}
	/**
	 * 新增 资源信息
	 * @author 011336wangwei3
	 * @param res
	 * @return 
	 * @DATE 2017/08/29
	 */
	@ResponseBody
	@RequestMapping("/saveRes")
	@NoRepeatRequest(2000)
	public BaseDTO saveRes(Resource res) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		res.setCreater(userPhone);
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			if(iResourceService.isAlreadyExist(res)){
				ErrorInfo errorInfo =  new ErrorInfo("resCode","资源编码已存在");
				errors.add(errorInfo);
			}else if(iResourceService.isAlreadyExistUri(res)){
				ErrorInfo errorInfo =  new ErrorInfo("uri","资源路径已存在");
				errors.add(errorInfo);
			}else{
				iResourceService.create(res);
				//如果不是超级管理员    需要将资源分配给   当前登录人   所拥有的角色
				if (!authorizationService.isSuperAdmin(userPhone)) {
					List<Role> roleList = iroleService.getRoleSetByUser(userPhone);
					if(roleList.size()==1){//如果当前用户只有一个角色，则把新增资源加到其角色下
						String roleCode = roleList.get(0).getRoleCode();
						RoleResource roleRes = new RoleResource();
						roleRes.setResCode(res.getResCode());
						roleRes.setStatus("1");
						roleRes.setCreater(userPhone);
						//查询其所有的父节点  包括其本身
						List<Role> List = iroleService.getRoleSetByRoleCode(roleCode);
						for(int i=0;i<List.size();i++){//新建资源要分配给其所有的父节点和其本身
							roleRes.setRoleCode(List.get(i).getRoleCode());
							iRoleResourceService.create(roleRes);
						}					
					}else{
						return tranferBaseDTO(errors, roleList);
					}
				}else{//如果是超级管理员要把资源分配给根角色
					RoleResource roleR = new RoleResource();
					roleR.setRoleCode(IRoleService.ROOT_ROLE);
					roleR.setResCode(res.getResCode());
					roleR.setStatus("1");
					roleR.setCreater(userPhone);
					iRoleResourceService.create(roleR);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ResourceController.saveRes Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, new ArrayList<Role>());
	}
	/**
	 * 为用户 所拥有的角色添加资源
	 * @author 011336wangwei3
	 * @param rule
	 * @return 
	 * @DATE 2017/09/26
	 */
	@ResponseBody
	@RequestMapping("/saveRule")
	public BaseDTO saveRule(RoleResource rule) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			if(StringUtil.isEmpty(rule.getResCode())){
				ErrorInfo errorInfo =  new ErrorInfo("resCode","资源编码为空,保存失败");
				errors.add(errorInfo);
			}else{
				RoleResource roleRes = new RoleResource();
				String roleCode = "";
				if(StringUtil.isEmpty(rule.getRoleCode())){//用户没有选  自动分配一个
					List<Role> roleList = iroleService.getRoleSetByUser(userPhone);
					roleCode = roleList.get(0).getRoleCode();
				}else{//用户选择了一个
					roleCode = rule.getRoleCode();
				}
				roleRes.setResCode(rule.getResCode());
				roleRes.setStatus("1");
				roleRes.setCreater(userPhone);
				//查询其所有的父节点  包括其本身
				List<Role> roleList = iroleService.getRoleSetByRoleCode(roleCode);
				for(int i=0;i<roleList.size();i++){//新建资源要分配给其所有的父节点和其本身
					roleRes.setRoleCode(roleList.get(i).getRoleCode());
					iRoleResourceService.create(roleRes);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ResourceController.saveRule Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
	/**
	 *  修改   资源信息
	 * @author 011336wangwei3
	 * @param resource
	 * @return 
	 * @DATE 2017/08/29
	 */
	@RequestMapping("/updateResource")
	@ResponseBody
	public BaseDTO updateResource(Resource resource) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		resource.setModifier(userPhone);
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			if(StringUtil.isEmpty(resource.getResCode())){
				ErrorInfo errorInfo =  new ErrorInfo("resCode","资源编码为空,修改失败");
				errors.add(errorInfo);
			}else{
				iResourceService.update(resource);
				if(resource.getStatus().equals("0")){
					iResourceService.deleteChildrens(resource);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ResourceController.updateResource Exception: "  ,  e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
	/**
	 *  修改   资源信息
	 * @author 011336wangwei3
	 * @param resource
	 * @return 
	 * @DATE 2017/08/29
	 */
	@RequestMapping("/delResource")
	@ResponseBody
	public BaseDTO delResource(Resource resource) {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		resource.setModifier(userPhone);
		List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
		try {
			iResourceService.deleteChildrens(resource);
		} catch (Exception e) {
			LOGGER.error("ResourceController.delResource Exception: " , e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, null);
	}
}
