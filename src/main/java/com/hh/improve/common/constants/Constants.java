/**
 * 
 */
package com.hh.improve.common.constants;

/**
 * @author 011589
 *
 */
public interface Constants {
	public static class  error_Code{
		public final static String ER_NAME = "erName"; // 用户名不存在
		public final static String ER_PASSWORD = "erPassword"; // 密码错误
	}
	public static class  error_Message{
		public final static String ER_NAME = "用户名不存在"; // 用户名不存在
		public final static String ER_PASSWORD = "密码错误"; // 密码错误
	}
	/* error code and error msg info - start */
	public final static String ERROR_CODE = "errorCode"; // 系统异常
	public final static String ERROR_MESSAGE = "errorMsg"; // 系统异常
	
	public final static String ERROR_SYSTEM_001 = "系统异常！"; // 系统异常
	public final static String ERROR_SYSTEM_002 = "您没有权限访问该页面，请咨询系统管理员！"; // 用户名密码错误
	public final static String ERROR_SYSTEM_401 = "401 - Unauthorized！"; // 用户名密码错误
	public final static String ERROR_SYSTEM_404 = "404 - Page Not Found！"; // 用户名密码错误
	public final static String ERROR_SYSTEM_500 = "500 - System Exception！"; // 用户名密码错误
	
	public final static String ERROR_SUPERADMIN_001 = "用户名或密码为空！"; // 用户名密码为空
	public final static String ERROR_SUPERADMIN_002 = "用户名或密码错误！"; // 用户名密码错误
	
	public final static String ERROR_USER_001 = "无效的用户！"; // 用户名密码为空
	public final static String ERROR_USER_002 = "用户登录失败！"; // 用户名密码错误
	public final static String ERROR_USER_003 = "用户未分配权限，请咨询系统管理员！"; // 用户名密码错误
	/* error code and error msg info - end */
	
	/* error info for request data - start */
	public final static String ERROR_FIELD_NAME_SYSTEM = "SYSTEM";
	public final static String ERROR_FIELD_EMPTY = "字段为空";
	public final static String ERROR_FIELD_INVALID = "字段无效";
	public final static String ERROR_FIELD_MINLEN = "字段低于最小长度";
	public final static String ERROR_FIELD_MAXLEN = "字段超过最大长度";
	
	
	public final static String ERROR_FIELD_ONLINE_INTERFACE_STATUS = "线上接口状态";
	public final static String ERROR_FIELD_WHITE_LIST_STATUS = "白名单状态";
	
	public final static String ERROR_FIELD_PROJECT_CODE = "projectCode";
	public final static String ERROR_FIELD_NAME_PROJECT_CODE = "项目代码";
	
	public final static String ERROR_SYSTEM = "系统异常！";
	
	public final static String ERROR_FIELD_MONITOR_START_TIME = "开始时间";
	public final static String ERROR_FIELD_MONITOR_END_TIME = "结束时间";
	
	/* error info for request data - end */
}
