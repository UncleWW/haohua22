package com.hh.improve.controller;

import com.github.pagehelper.StringUtil;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.common.pojo.ResTree;
import com.hh.improve.common.pojo.RoleTree;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("/rule")
public class RuleController extends BaseController {
	private static Logger LOGGER = LoggerFactory.getLogger(RuleController.class);
	@Autowired
	private IRoleService iroleService;
	
	@Autowired
	private IResourceService iResourceService;
	
	@Autowired
	private IRoleResourceService iRoleResourceService;
	
	@RequestMapping
	public String index(Model model, HttpServletResponse response) {
		model.addAttribute("parentUri", "/rulemanager");
		model.addAttribute("uri", "/rule");
		model.addAttribute("rule", "admin");
		return super.commonModel(model, response);
	}
	
	protected String getPageUri() {
		return "views/rulePage";
	}
	/**
	 * 查询   资源树     根据角色 查询   节点    封装成相应的结构
	 * @author 011336wangwei3
	 * @return 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @DATE 2017/08/31
	 */
	@ResponseBody
	@RequestMapping("/queryResTree")
	public BaseDTO queryResTree(@Value("${zTree.iconOpen}")String iconOpen , @Value("${zTree.iconClose}")String iconClose) throws IllegalAccessException, InvocationTargetException {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<ErrorInfo> errors =new ArrayList<ErrorInfo>();
		List<ResTree> treeList = null;
		try {
			List<Resource> resList = new ArrayList<Resource>();
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
		} catch (Exception e) {
			LOGGER.error("RuleController.queryResTree Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		if (null == treeList) {
			treeList = new ArrayList<ResTree>();
		}
		return tranferBaseDTO(errors, treeList);
    }
	/**
	 *  查询   角色树    （根据登录角色） 所有节点    封装成相应的结构
	 * @author 011336wangwei3
	 * @return 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @DATE 2017/09/1
	 */
	@ResponseBody
	@RequestMapping("/queryRoleTree")
	public BaseDTO  queryRoleTree(@Value("${zTree.iconOpen}")String iconOpen , @Value("${zTree.iconClose}")String iconClose) throws IllegalAccessException, InvocationTargetException {
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<Role> roleList = new ArrayList<Role>();
		List<ErrorInfo> errors =new ArrayList<ErrorInfo>();
		List<RoleTree> treeList = null;
		try {
			if (subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
				Map<String, Object> condition = new HashMap<String, Object>();
				roleList = iroleService.getList(condition);
			}else{		
				//非超级管理员  根据登录用户查询其拥有的角色  包括其所有的后代角色
				roleList = iroleService.getRoleSetByUser(userPhone);
			}
			treeList = new ArrayList<RoleTree>();
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
			LOGGER.error("ResourceController.queryRoleTree Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}
		return tranferBaseDTO(errors, treeList);
    }
	/**
	 * 根据RoleCode 查询角色资源信息
	 * @author 011336wangwei3
	 * @return 
	 * @DATE 2017/08/31
	 */
	@ResponseBody
	@RequestMapping("/queryRuleByRoleCode")
	public BaseDTO  queryRuleByRoleCode(String roleCode) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("roleCode", roleCode);
		condition.put("status", "1");
		List<String> resCodeList = null;
		List<ErrorInfo> errors =new ArrayList<ErrorInfo>();
		try {
			if(StringUtil.isEmpty(roleCode)){
				ErrorInfo errorInfo =  new ErrorInfo("roleCode","往后台传值时角色编码roleCode为空");
				errors.add(errorInfo);
			}else{				
				List<RoleResource> ruleList = iRoleResourceService.getList(condition);
				resCodeList = new ArrayList<String>();
				for (RoleResource rule : ruleList) {
					resCodeList.add(rule.getResCode());
				}
			}
		} catch (Exception e) {
			LOGGER.error("RuleController.queryRuleByRoleCode Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}	
		return tranferBaseDTO(errors, resCodeList);
    }
	/**
	 *  构建   角色资源关联信息       修改和新增
	 * @author 011336wangwei3
	 * @return 
	 * @throws Exception 
	 * @DATE 2017/09/1
	 */
	@ResponseBody
	@RequestMapping("/saveRule")
	@NoRepeatRequest(2000)
	public  BaseDTO saveRule(String roleCode,String parentRoleCode, String... resCodes) throws Exception {  
		Subject subject = SecurityUtils.getSubject();
		String userPhone = getUserPhone();
		List<ErrorInfo> errors =new ArrayList<ErrorInfo>();
		try {
			//当前角色所有后代角色所拥有的资源   不包括其本身
			List<RoleResource> rrlist2 = iRoleResourceService.queryChildrenRoleResByRoleCode(roleCode);
			//前台传来的将要分配的当前角色的资源
			Set<String> rrlist1Set = getResCodeSetFromString(resCodes);
			Set<String> rrlist2Set = getResCodeSetFromRoleRes(rrlist2);
			//避免删除角色一些资源时   其后代还拥有这些资源
			for (String resCode : rrlist2Set) {
				if (!rrlist1Set.contains(resCode)) {
					ErrorInfo errorInfo =  new ErrorInfo("Resource","分配失败！因为分配后子角色的资源比当前资源更多");
					errors.add(errorInfo);
					return tranferBaseDTO(errors, null);
				}
			}
			if (!subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {//非超级管理员
				// 角色参数验证: 不能修改自身角色
				if (subject.hasRole(roleCode)) {
					ErrorInfo errorInfo =  new ErrorInfo("role","不能修改自身的角色");
					errors.add(errorInfo);
					return tranferBaseDTO(errors, null);
				}
				// 资源参数验证: 是否全部为自身所拥有的资源
				List<Resource> list = iResourceService.getResSetByUser(userPhone);
				Set<String> resCodeSet = getResCodeSet(list);
				if (CollectionUtils.isNotEmpty(resCodeSet) &&  resCodes != null) {
					for (int i=0;i< resCodes.length;i++) {
						String resCode = resCodes[i];
						if (!resCodeSet.contains(resCode)) {
							ErrorInfo errorInfo =  new ErrorInfo("Resource","不能分配自身没有的资源");
							errors.add(errorInfo);
							return tranferBaseDTO(errors, null);
						}
					}
				}else if(resCodes!=null && resCodes.length>0){//自身资源为空  但前台分配资源 不为空
					ErrorInfo errorInfo =  new ErrorInfo("Resource","不能分配自身没有的资源");
					errors.add(errorInfo);
					return tranferBaseDTO(errors, null);
				}
			}else if(!roleCode.endsWith(IRoleService.ROOT_ROLE)){//是超级管理员  但是当前操作的  不能是根角色
				//查新其父角色拥有的资源
				List<RoleResource> ResList = iRoleResourceService.queryParentRoleResByRoleCode(roleCode);
				Set<String> resCodeSet = getResCodeSetFromRoleRes(ResList);
				if (CollectionUtils.isNotEmpty(resCodeSet) &&  resCodes != null) {
					for (int i=0;i< resCodes.length;i++) {
						String resCode = resCodes[i];
						if (!resCodeSet.contains(resCode)) {
							ErrorInfo errorInfo =  new ErrorInfo("Resource","不能为当前角色分配其父角色没有的资源");
							errors.add(errorInfo);
							return tranferBaseDTO(errors, null);
						}
					}
				}else if(resCodes!=null && resCodes.length>0){//父角色资源为空  但前台分配资源 不为空
					ErrorInfo errorInfo =  new ErrorInfo("Resource","不能为当前角色分配其父角色没有的资源");
					errors.add(errorInfo);
					return tranferBaseDTO(errors, null);
				}
				
			}

			// 修改角色资源关系表
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("roleCode", roleCode);
			List<RoleResource> roleResList = iRoleResourceService.getList(cond);
			Map<String, Object> resCodeMap = new HashMap<String, Object>();
			if (null != resCodes) {
				for (int i = 0, len = resCodes.length; i < len; i++) {
					resCodeMap.put(resCodes[i], StringUtils.EMPTY);
				}
			}
			String resCode = StringUtils.EMPTY;
			RoleResource roleRes = null;
			if (CollectionUtils.isNotEmpty(roleResList)) {
				Object o = null;
				for (RoleResource rr: roleResList) {
					resCode = rr.getResCode();
					o = resCodeMap.get(resCode);
					if (null == o) {
						if (rr.getStatus().equals("1")) {
							rr.setStatus("0");
							rr.setModifier(userPhone);
						} else {
							continue;
						}
					} else {
						resCodeMap.remove(resCode);
						if (rr.getStatus().equals("0")) {
							rr.setStatus("1");
							rr.setModifier(userPhone);
						} else {
							continue;
						}
					}
					iRoleResourceService.update(rr);
				}
			}
			for (Map.Entry<String, Object> enrty: resCodeMap.entrySet()) {
				resCode = enrty.getKey();
				if(StringUtils.isNotEmpty(resCode)) {
					roleRes = new RoleResource();
					roleRes.setRoleCode(roleCode);
					roleRes.setResCode(resCode);
					roleRes.setStatus("1");
					roleRes.setCreater(userPhone);
					iRoleResourceService.create(roleRes);
				}
			}
		} catch (Exception e) {
			LOGGER.error("RuleController.saveRule Exception: ", e);
			ErrorInfo errorInfo = new ErrorInfo("system.error", "系统异常！");
			errors = Arrays.asList(errorInfo);
		}	
		return tranferBaseDTO(errors, null);
    }
	private Set<String> getResCodeSetFromRoleRes(List<RoleResource> list) {
		Set<String> resCodeSet = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (RoleResource res: list) {
				resCodeSet.add(res.getResCode());
			}
		}
		return resCodeSet;
	}
	private Set<String> getResCodeSetFromString(String[] resCodes) {
		Set<String> resCodeSet = new HashSet<String>();
		if (resCodes!=null && resCodes.length>0) {
			for (String resCode: resCodes) {
				resCodeSet.add(resCode);
			}
		}
		return resCodeSet;
	}
	private Set<String> getResCodeSet(List<Resource> list) {
		Set<String> resCodeSet = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Resource res: list) {
				resCodeSet.add(res.getResCode());
			}
		}
		return resCodeSet;
	}
	
}
