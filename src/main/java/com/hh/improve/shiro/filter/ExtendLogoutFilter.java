/**
 * 
 */
package com.hh.improve.shiro.filter;

import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author 011589
 *
 */
public class ExtendLogoutFilter extends LogoutFilter {

	final Logger log = LoggerFactory.getLogger(ExtendLogoutFilter.class);
	private IAuthorizationService authorizationService;

	@Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
		if (subject.hasRole(IAuthorizationService.SUPER_ADMIN_ROLE)) {
        	return "/super";
        } else {
        	return super.getRedirectUrl();
        }
    }

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = this.getSubject(request, response);
		String redirectUrl = this.getRedirectUrl(request, response, subject);
		DefaultWebSecurityManager defaultWebSecurityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
		//MemoryConstrainedCacheManager memoryCacheManager = new MemoryConstrainedCacheManager();
		MemoryConstrainedCacheManager memoryCacheManager = (MemoryConstrainedCacheManager)defaultWebSecurityManager.getCacheManager();
		memoryCacheManager.destroy();
		try {
			subject.logout();
		} catch (SessionException var6) {
			log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
		}

		this.issueRedirect(request, response, redirectUrl);
		return false;
	}
	public IAuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(IAuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	
}
