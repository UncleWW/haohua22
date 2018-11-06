package com.hh.improve.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class RequestDealFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                          FilterChain filter) throws IOException, ServletException {
			HttpServletRequest httpHequest = (HttpServletRequest) request;
			RequestHandler wrapRequest = new RequestHandler(httpHequest,
					httpHequest.getParameterMap());
			filter.doFilter(wrapRequest, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
