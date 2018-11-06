/**
 * 
 */
package com.hh.improve.service.impl;

import com.hh.improve.common.util.Md5Utils;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.ISuperAdminDao;
import com.hh.improve.entity.SuperAdmin;
import com.hh.improve.service.ISuperAdminService;
import com.hh.improve.shiro.SuperAdminTicketUtils;
import com.hh.improve.shiro.entity.AuthorizationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;



/**
 * @author 011589
 *
 */
@Service
public class SuperAdminServiceImpl extends BaseServiceImpl<SuperAdmin> implements ISuperAdminService {

	private static Logger LOGGER = LoggerFactory.getLogger(SuperAdminServiceImpl.class);
	
	@Autowired
	private ISuperAdminDao superAdminDao;
	
	@Override
	protected IBaseDao<SuperAdmin> getBaseDao() {
		return superAdminDao;
	}
	
	@Override
	public String login(SuperAdmin superAdmin) {
		SuperAdmin result = super.get(superAdmin);
		if (null == result) {
			LOGGER.info("SuperAdmin account is not exist:" + superAdmin.toString());
			return null;
		}
		String encryptPassword = Md5Utils.hash(superAdmin.getPassword());
		if (!result.getEncryptPassword().equals(encryptPassword)) {
			LOGGER.info("Password Error When SuperAdmin Login");
			return null;
		}
		AuthorizationUser user = new AuthorizationUser();
		user.setUserId(result.getUserPhone());
		user.setUserAccount(result.getUserPhone());
		user.setUserName(result.getUserName());
		//user.setPassword(result.getEncryptPassword());
		user.setTime(Long.valueOf(new Date().getTime()));
		String ticket = SuperAdminTicketUtils.putTicket(user);
		return ticket;
	}


}
