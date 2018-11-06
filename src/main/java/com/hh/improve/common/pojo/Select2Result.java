/*
 * 文 件 名 : Select2Result.java
 * 版    权 : CZYSOFT TECHNOLOGY CO.,LTD.Copyright 2017-2030.All rights reserved
 * 描    述 : <描述>
 * 修 改 人 : <011421>shiwei@inner.czy.com
 * 修改时间 : 2017年9月13日 上午10:58:51
 * 需求单号 : <需求Redmine单号>
 * 变更单号 : <变更Redmine单号>
 * 修改内容 : <修改内容>
 * Version : V2.0
 */
package com.hh.improve.common.pojo;

/**
 * <一句话功能简介>用于封装select2结果集<br>
 * @Description:TODO <功能详细描述>
 * @ClassName:Select2Result
 * @author  [011421]shiwei@inner.czy.com
 * @version [版本号,2017年9月13日]
 * @see     [相关类/方法]
 * @since   [产品/模块]
 */
public class Select2Result {

	private String id;
	
	private String text;
	
	public Select2Result(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public Select2Result() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
