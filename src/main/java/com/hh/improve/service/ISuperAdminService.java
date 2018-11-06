/**
 * 
 */
package com.hh.improve.service;


import com.hh.improve.entity.SuperAdmin;

/**
 * @author sunyong-011589
 * @date 2017年8月25日
 */
public interface ISuperAdminService extends IBaseService<SuperAdmin> {

	String login(SuperAdmin superAdmin);
}
