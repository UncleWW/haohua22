package com.hh.improve.service;


import com.hh.improve.entity.CompanyUser;

public interface ICompanyUserService extends IBaseService<CompanyUser> {
	/**
	 * 判断该用户是否有登录权限
	 *
	 * @param phone
	 * @return
	 */
	boolean checkUserLoginAuthority(String phone);

	/**
	 * 根据用户电话获取公司信息
	 *
	 * @param userPhone
	 * @return
	 */
	CompanyUser getUserLoginAuthority(String userPhone);
}