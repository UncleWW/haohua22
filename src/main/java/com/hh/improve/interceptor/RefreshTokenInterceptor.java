package com.hh.improve.interceptor;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Prevent refresh interceptor
 * 
 * @author Summer
 *
 */
public class RefreshTokenInterceptor extends HandlerInterceptorAdapter {

	public final static String REFRESH_TOKEN_VALUE_NAME = "_token_";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		RefreshToken annotation = method.getAnnotation(RefreshToken.class);
		if (annotation != null) {
			if (annotation.generate()) {
				request.getSession(false).setAttribute(REFRESH_TOKEN_VALUE_NAME, UUID.randomUUID().toString());
			}
			if (annotation.remove()) {
				if (isRepeatSubmit(request)) {
					return false;
				}
				request.getSession(false).removeAttribute(REFRESH_TOKEN_VALUE_NAME);
			}
		}
		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(REFRESH_TOKEN_VALUE_NAME);
		if (StringUtils.isEmpty(serverToken)) {
			return false;
		}

		String token = request.getParameter("token");
		if (StringUtils.equals(serverToken, token)) {
			return true;
		}

		return false;
	}

}