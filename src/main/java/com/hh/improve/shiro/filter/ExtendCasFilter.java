/**
 * 
 */
package com.hh.improve.shiro.filter;

import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author 011589
 *
 */
public class ExtendCasFilter extends CasFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(CasFilter.class);
	
	protected String extendFailureUrl;
	
	/**
	 * 重写单点登录验证失败方法，在失败页面展示错误原因
	 * 
	 */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, 
            ServletRequest request, ServletResponse response) {
    	if (null != ae && IAuthorizationService.USER_INVALID_MESSAGE.equalsIgnoreCase(ae.getMessage())) {
    		// transfer user invalid error message to error page
    		setFailureUrl(extendFailureUrl + "?errorCode=" + IAuthorizationService.USER_INVALID_MESSAGE);
    		LOGGER.debug(extendFailureUrl + "?errorCode=" + IAuthorizationService.USER_INVALID_MESSAGE);
    	} else {
    		setFailureUrl(extendFailureUrl);
    	}
        return super.onLoginFailure(token, ae, request, response);
    }
    
    public void setExtendFailureUrl(String extendFailureUrl) {
        this.extendFailureUrl = extendFailureUrl;
    }
}
