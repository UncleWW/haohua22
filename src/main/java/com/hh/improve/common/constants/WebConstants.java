package com.hh.improve.common.constants;

public interface WebConstants {
	
	public static final String CAS_FILTER_URI = "/shiro-cas";
	
	public static final String REDIS_SLIPT_CHAR = ":";
	public static final String IP_SLIPT_CHAR = ",";
	public static final String SYSTEM_OPERATOR = "SYSTEM";
	
	public final static String AUTH_ROOT_RESOURCE_CODE = "QXGL";
	public final static String UNAUTH_BUTTON_CODE = "UNAUTH_BUTTON";
	
	public final static String USER_STATUS_VALID = "1";
	public final static String USER_STATUS_INVALID = "2";
	
	public final static String WHITELIST_TEMP_PASSWORD = "******";
	
	public final static String INTERFACE_LIST_EXCEL_FILE_NAME = "interfacelist-excel";
	
	public final static String RESULT_FLAG_SUCCESS = "success";
	public final static String RESULT_FLAG_FAIL = "fail";
	
	public final static String EXCEL_IS_SENSITIVE_TRUE = "是";
	public final static String EXCEL_IS_SENSITIVE_FALSE = "否";
	
	public final static String EXCEL_IS_NULL_TRUE = "Y";
	public final static String EXCEL_IS_NULL_FALSE = "N";
	
	public final static String EXCEL_EXPORT_PREFIX = "接口清单";
	public final static String EXCEL_EXPORT_SUFFIX = ".xlsx";
	public final static String EXCEL_EXPORT_MONITOR_PREFIX = "监控信息";
	public final static String EXCEL_EXPORT_MONITORDETAIL_PREFIX = "监控明细信息";
	
	public final static String DEFAULT_PAGE_OFFSET = "0";
	
	public final static String EXCEL_DATE_FORMAT = "yyyy/MM/dd";
	
	public interface BtnCode {
		// 资源管理界面
		public static final String res_add_btn="BTN04010001";
		public static final String res_edit_btn="BTN04010002";
		public static final String res_del_btn="BTN04010003";
		public static final String res_save_btn="BTN04010004";
		// 用户管理界面
		public static final String user_search_btn = "BTN04020001";
		// 角色管理界面
		public static final String role_add_btn="BTN04030001";
		public static final String role_edit_btn="BTN04030002";
		public static final String role_del_btn="BTN04030003";
		public static final String role_search_btn="BTN04030004";
		public static final String role_userrolesearch_btn="BTN04030005";
		public static final String role_usernorolesearch_btn="BTN04030006";
		public static final String role_torole_btn="BTN04030007";
		public static final String role_tonorole_btn="BTN04030008";
		// 权限分配界面
		public static final String rule_rolesearch_btn = "BTN04040001";
		public static final String rule_ressearch_btn = "BTN04040002";
		public static final String rule_ressave_btn = "BTN04040003";
		//数据地图界面
		public static final String data_map_location = "BTN07010001";
		public static final String data_map_edit = "BTN07010002";
		public static final String data_map_save = "BTN07010003";
		//报表详情界面
		public static final String report_detail_query = "BTN07020001";
		public static final String report_detail_add_edit = "BTN07020002";
		public static final String report_detail_delete = "BTN07020003";
		//指标管理界面
		public static final String target_query = "BTN07030001";
		public static final String target_add = "BTN07030002";
		public static final String target_download = "BTN07030003";
		public static final String target_import = "BTN07030004";
		public static final String target_delete = "BTN07030005";
		public static final String target_edit = "BTN07030006";
		//维度管理界面
		public static final String dimension_query = "BTN07040001";
		public static final String dimension_add_edit = "BTN07040002";
		public static final String dimension_download = "BTN07040003";
		public static final String dimension_import = "BTN07040004";
		public static final String dimension_delete = "BTN07040005";
		//分析信息管理界面
		public static final String analyze_query = "BTN07050001";
		public static final String analyze_change_status = "BTN07050002";
		//信息系统管理界面
		public static final String system_query = "BTN08010001";
		public static final String system_add = "BTN08010002";
		public static final String system_edit = "BTN08010003";
		public static final String system_delete = "BTN08010004";
		//数据分类界面
		public static final String classify_query = "BTN08020001";
		public static final String classify_add_edit = "BTN08020002";
		public static final String classify_delete = "BTN08020003";
		//数据地图部门维护
		public static final String datamap_dept_query = "BTN08030001";
		public static final String datamap_dept_edit = "BTN08030002";
		//数据实体项维护
		public static final String entity_query = "BTN08040001";
		public static final String entity_add_edit = "BTN08040002";
		public static final String entity_download = "BTN08040003";
		public static final String entity_import = "BTN08040004";
		public static final String entity_achieve = "BTN08040005";
		public static final String entity_delete = "BTN08040006";
		//报表访问记录
		public static final String tableauUserRecord_query = "BTN04050001";
		
	}
}
