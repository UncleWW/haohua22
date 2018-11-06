package com.hh.improve.shiro.filter;

import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ExtendAuthorizationFilter extends AuthorizationFilter {

	private IAuthorizationService authorizationService;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		if (null == subject || !subject.isAuthenticated()) {
			return false;
		}
		if (subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
			return true;
		}
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getServletPath();
		String resCode = authorizationService.getResCodeByUri(path);
		if (StringUtils.hasText(resCode)) {
			/*if (ExtendSecurityUtils.getUserAuthTimeStamp() < ExtendSecurityUtils.authTimeStamp) {
				// 重新加载用户资源权限,同步修改后的资源编码
				ExtendSecurityUtils.syncAuthorizationCache();
			}*/
			if (subject.isPermitted(resCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        //saveRequest(request);
        redirectToLogin(request, response);
    }
    
	public IAuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(IAuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
}
