package com.hh.improve.dao;

import com.hh.improve.entity.TestBean;

import java.util.List;

public interface TestDao extends IBaseDao<TestBean>{
    /**
     * 新增用户
     * @param user
     */
    void createUser(TestBean user);
    /**
     * 查询用户列表
     * @return
     */
    List<TestBean> findAllUser();
}
