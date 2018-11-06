package com.hh.improve.service;

import com.hh.improve.entity.User;

import java.util.List;

public interface IUserService extends IBaseService<User>{

	/**
	 *登录验证
	 * @param name
	 * @return
	 */
	public User getUserByPhone(String name);

	/**
	 * 分页展示用户列表
	 * @param user
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<User> getUserPageList(User user, int offset, int limit);

	/**
	 * 新增用户信息
	 * @param user
	 * @return
	 */
	int userSave(User user);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	int userUpdate(User user);
}
