package com.hh.improve.controller;


import com.hh.improve.common.constants.ErrorConstants;
import com.hh.improve.common.constants.WebConstants;
import com.hh.improve.common.pojo.ErrorInfo;
import com.hh.improve.entity.Resource;
import com.hh.improve.entity.SuperAdmin;
import com.hh.improve.entity.User;
import com.hh.improve.entity.dto.BaseDTO;
import com.hh.improve.service.IResourceService;
import com.hh.improve.service.ISuperAdminService;
import com.hh.improve.service.IUserService;
import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 011589
 *
 */
public abstract class BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ISuperAdminService superAdminService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IResourceService resourceService;

    protected String commonModel(Model model, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        if (null == subject || subject.getPrincipals()==null || subject.getPrincipals().getPrimaryPrincipal()==null) {
            return "views/userLogin";
        }

        User user = (User)subject.getPrincipals().getPrimaryPrincipal();
        String userPhone = user.getUserPhone();
        try {
            String userName = StringUtils.EMPTY;
            String sex = StringUtils.EMPTY;
            List<Resource> menuResList = null;
            if (subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
                SuperAdmin superAdmin = new SuperAdmin();
                superAdmin.setUserPhone(userPhone);
                SuperAdmin resultSperAdmin = superAdminService.get(superAdmin);
                userName = resultSperAdmin.getUserName();
                sex = "1";

                menuResList = resourceService.getList(new HashMap<String, Object>());
            } else {
                // 页面头用户信息
                //User resultUser = userService.get(user);
                userName = user.getUserName();
                sex = user.getSex();

                // 用户菜单按钮信息
                Map<String, Object> condition = new HashMap<String, Object>();
                condition.put("userPhone", userPhone);
                menuResList = resourceService.getUserRes(condition);
            }
            model.addAttribute("userPhone", userPhone);
            model.addAttribute("userName", userName);
            model.addAttribute("sex", sex);

            Map<String, Object> menuMap = new HashMap<String, Object>();
            Resource rootMenu = null;
            List<Resource> list = null;
            if (CollectionUtils.isNotEmpty(menuResList)) {
                for (Resource res: menuResList) {
                    if (IResourceService.ROOT_RESOURCE.equalsIgnoreCase(res.getResCode())) {
                        rootMenu = res;
                    }
                    list = new ArrayList<Resource>();
                    for (Resource r: menuResList) {
                        if (res.getResCode().equals(r.getParentResCode())) {
                            list.add(r);
                        }
                    }
                    menuMap.put(res.getResCode(), list);
                }
                model.addAttribute("rootMenu", rootMenu);
                model.addAttribute("menuMap", menuMap);
            } else {
                model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_USER_003);
                return "views/errorPage";
            }
        } catch (Exception e) {
            LOGGER.error("common page error", e);
            model.addAttribute(ErrorConstants.ERROR_MESSAGE, ErrorConstants.ERROR_SYSTEM_001);
            return "views/errorPage";
        }
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addDateHeader("Expires", -1);
        return getPageUri();
    }

    protected abstract String getPageUri();

    protected String getUserPhone() {
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        if (null != subject) {
            PrincipalCollection coll = subject.getPrincipals();
            if(coll!=null) {
                user = (User) coll.getPrimaryPrincipal();
            }
        }
        if(user!=null){
            return user.getUserPhone();
        }else{
            return null;
        }
    }
    protected BaseDTO tranferBaseDTO (List<ErrorInfo> errors, Object result) {
        BaseDTO baseDTO = new BaseDTO();
        if (CollectionUtils.isNotEmpty(errors)) {
            baseDTO.setResultFlag(WebConstants.RESULT_FLAG_FAIL);
        } else {
            baseDTO.setResultFlag(WebConstants.RESULT_FLAG_SUCCESS);
        }
        baseDTO.setErrors(errors);
        baseDTO.setResult(result);
        return baseDTO;
    }
}
