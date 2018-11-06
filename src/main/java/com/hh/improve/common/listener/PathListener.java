package com.hh.improve.common.listener;

import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PathListener implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//this.context = arg0.getServletContext();
		
		WebUtils.removeWebAppRootSystemProperty(arg0.getServletContext());

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		WebUtils.setWebAppRootSystemProperty(arg0.getServletContext());
		
	}

}
