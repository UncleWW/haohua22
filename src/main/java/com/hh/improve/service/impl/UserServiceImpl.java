package com.hh.improve.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.improve.common.util.Md5Utils;
import com.hh.improve.dao.IBaseDao;
import com.hh.improve.dao.IUserDao;
import com.hh.improve.entity.User;
import com.hh.improve.service.IUserService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserDao userDao;
	
	@Override
	protected IBaseDao<User> getBaseDao() {
		return userDao;
	}

	@Override
	public User getUserByPhone(String phone) {
		// 验证
		User user = new User();
		user.setUserPhone(phone);
		User u = getUserByPhone(user);
		return u;
	}

	public User getUserByPhone(User user){
		List<User> users =  userDao.getUserByPhone(user);
		if(users!=null && users.size()>0){
		    return users.get(0);
          }
          return null;
	}

    @Override
    public List<User> getUserPageList(User user, int offset, int limit) {
        PageHelper.offsetPage(offset, limit);

        // 根据条件查询主表数据
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userName", user.getUserName());
        condition.put("userPhone",user.getUserPhone());
        condition.put("status", user.getStatus());
        condition.put("sex", user.getSex());
        return userDao.queryByCondition(condition);
    }
	@Override
	public int userSave(User user){
		if(CommonUtils.isNotEmpty( user.getPassword() )){
			user.setPassword( Md5Utils.hash( user.getPassword() ) );
		}
		return userDao.insert(user);
	}

	@Override
	public int userUpdate(User user){
		User resultUser = getUserByPhone(user.getUserPhone());
		//如果数据库密码与前台密码相同则没有修改密码，那么就不修改
		if( CommonUtils.isNotEmpty(resultUser.getPassword()) && resultUser.getPassword().equals( user.getPassword() ) ){
			user.setPassword(null);//password为null时，SQL语句不修改密码
		}else if(CommonUtils.isEmpty( user.getPassword() )){
			//如果前台密码为空，则也视为不修改
			user.setPassword(null);
		}else{//修改了密码  则加密后  存储
			user.setPassword( Md5Utils.hash( user.getPassword() ) );
		}
		return userDao.update(user);
	}
}
