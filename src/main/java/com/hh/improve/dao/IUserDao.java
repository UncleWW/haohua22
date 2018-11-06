package com.hh.improve.dao;


import com.hh.improve.entity.User;

import java.util.List;

public interface IUserDao extends IBaseDao<User> {

	List<User> getUserByPhone(User user);

}
