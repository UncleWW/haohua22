package com.hh.improve.shiro.cas;


import com.hh.improve.entity.SuperAdmin;
import com.hh.improve.entity.User;
import com.hh.improve.service.ISuperAdminService;
import com.hh.improve.service.IUserService;
import com.hh.improve.shiro.service.IAuthorizationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ExtendCasRealm extends AuthorizingRealm {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ExtendCasRealm.class);

	@Autowired
	IAuthorizationService authorizationService;

	@Autowired
	private IUserService userService;

	@Autowired
	ISuperAdminService superAdminService;

	// 权限管理
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SecurityUtils.getSubject().getSession().setTimeout(-1000L);
		//获取用户的信息
		User user = (User)principals.getPrimaryPrincipal();
		String userPhone = user.getUserPhone();
		//返回的对象
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 设置用户权限信息,包括角色信息,资源权限信息,数据权限
		if (authorizationService.isSuperAdmin(userPhone)) {
			authorizationInfo.addRole(IAuthorizationService.SUPER_ADMIN_ROLE);
			//authorizationInfo.setStringPermissions(authorizationService.getSuperAdminResCodeSet());
			//authorizationInfo.setDataPermissions(authorizationService.getSuperAdminDataSet());
		} else {
			Set<String> roleCodes = authorizationService.getUserRoleCodeSet(userPhone);
			authorizationInfo.addRoles(roleCodes);
			Set<String> resCodes = authorizationService.getUserResCodeSet(userPhone);
			// TODO 这里需要修改，用户在分配资源菜单的时候，默认分配根资源权限
			resCodes.add(authorizationService.getRootResCode());
			authorizationInfo.setStringPermissions(resCodes);
			//authorizationInfo.setDataPermissions(authorizationService.getUserDataSet(userPhone));
			//authorizationInfo.setUserAuthTimeStamp(ExtendSecurityUtils.authTimeStamp);
		}
		return authorizationInfo;
	}

	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		//获取用户的输入的账号.
		//String username = (String)token.getPrincipal();
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String userPhone=token.getUsername();
		//userPhone = userPhone.substring(0,userPhone.length()-1);
		//根据用户名去数据库查询是否有该用户
		User user = new User();
		if(userPhone.contains("super-")){
			SuperAdmin superAdmin = new SuperAdmin();
			superAdmin.setUserPhone(userPhone);
			SuperAdmin admin = superAdminService.get(superAdmin);
			if(admin==null)return null;
			user.setUserPhone(admin.getUserPhone());
			user.setPassword(admin.getEncryptPassword());
		}else{
			user = userService.getUserByPhone(userPhone);
		}

		if(user == null){
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user, //用户名
				user.getPassword(), //密码
				getName()  //realm name
		);
		return authenticationInfo;
	}








	public IAuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(IAuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
}
