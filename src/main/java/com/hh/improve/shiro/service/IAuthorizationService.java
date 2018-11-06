package com.hh.improve.shiro.service;

import java.util.Set;

public interface IAuthorizationService {

	/**
	 * 超级管理员角色
	 */
	public final static String SUPER_ADMIN_ROLE = "SUPER_ADMIN_ROLE";

	/**
	 * 无效的用户信息
	 */
	public final static String USER_INVALID_MESSAGE = "USER_INVALID_MESSAGE";

	/**
	 * 判断用户账号是否为超级管理员
	 *
	 * @param userAccount
	 * @return
	 */
	public boolean isSuperAdmin(String userAccount);

	/**
	 * 判断用户账号是否为有效用户
	 *
	 * @param userAccount
	 * @return
	 */
	public boolean isValidUser(String userAccount);

	/**
	 * 通过用户账号获得用户角色信息
	 *
	 * @param userAccount
	 * @return
	 */
	public Set<String> getUserRoleCodeSet(String userAccount);


	/**
	 * 通过用户账号获得用户资源权限信息
	 *
	 * @param userAccount
	 * @return
	 */
	public Set<String> getUserResCodeSet(String userAccount);

	/**
	 * 通过用户账号获得用户所拥有的数据权限信息
	 *
	 * @param userAccount
	 * @return
	 */
	public Set<String> getUserDataSet(String userAccount);

	public String getResCodeByUri(String uri);

	public String getRootResCode();


	/**
	 * 目前只记录登录成功的账号信息
	 *
	 * @param userAccount
	 */
	public void logLogin(String userAccount); // TODO 记录登录信息-该方法后续需要认真想一下具体需要记录的信息和实现方法

}
