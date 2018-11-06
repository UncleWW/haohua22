package com.hh.improve.interceptor;


import com.hh.improve.common.util.JsonUtil;
import com.hh.improve.interceptor.annotation.NoRepeatRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个用户 相同url 同时提交 相同数据 验证 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单
 * 
 * @author Administrator
 *
 */
@Component
public class NoRepeatRequestInterceptor extends HandlerInterceptorAdapter {

	private final static String REPEAT_DATA = "REPEATE_DATA";
	private final static String REPEAT_DATA_SLIPT_CHAR = "|";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			NoRepeatRequest annotation = method.getAnnotation(NoRepeatRequest.class);
			if (annotation != null) {
				return !repeatRequestValidator(request, annotation);
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	/**
	 * 验证同一个url数据是否相同提交 ,相同返回true
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public boolean repeatRequestValidator(HttpServletRequest httpServletRequest, NoRepeatRequest annotation) {
		boolean repeatFlag = false;
		String params = JsonUtil.parameterMapToJson(httpServletRequest.getParameterMap());
		String url = httpServletRequest.getRequestURI();
		Map<String, String> requestDataMap = new HashMap<String, String>();
		requestDataMap.put(url, params);
		String nowRequestData = requestDataMap.toString();
		long nowTimestamp = Calendar.getInstance().getTimeInMillis();

		Object sessionData = httpServletRequest.getSession().getAttribute(REPEAT_DATA);
		if (sessionData != null) {
			String sessionDataStr = (String)sessionData;
			String sessionRequestData = sessionDataStr.substring(0, sessionDataStr.lastIndexOf(REPEAT_DATA_SLIPT_CHAR));
			String sessionTimestampStr = sessionDataStr.substring(sessionDataStr.lastIndexOf(REPEAT_DATA_SLIPT_CHAR) + 1, sessionDataStr.length());
			long sessionTimestamp = Long.valueOf(sessionTimestampStr).longValue();
			if (nowRequestData.equals(sessionRequestData) && (nowTimestamp - sessionTimestamp) < annotation.value()) {
				repeatFlag = true;
			}
		}
		if (!repeatFlag) {
			httpServletRequest.getSession().setAttribute(REPEAT_DATA, nowRequestData + REPEAT_DATA_SLIPT_CHAR + nowTimestamp);
		}
		return repeatFlag;
	}

}
