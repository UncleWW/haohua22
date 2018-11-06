/*
 * 文 件 名 : TableEditResult.java
 * 版    权 : CZYSOFT TECHNOLOGY CO.,LTD.Copyright 2017-2030.All rights reserved
 * 描    述 : <描述>
 * 修 改 人 : <011421>shiwei@inner.czy.com
 * 修改时间 : 2017年9月15日 下午3:27:21
 * 需求单号 : <需求Redmine单号>
 * 变更单号 : <变更Redmine单号>
 * 修改内容 : <修改内容>
 * Version : V2.0
 */
package com.hh.improve.common.pojo;

/**
 * <一句话功能简介>bootstrap table edit 返回值封装<br>
 * @Description:TODO <功能详细描述>
 * @ClassName:TableEditResult
 * @author  [011421]shiwei@inner.czy.com
 * @version [版本号,2017年9月15日]
 * @see     [相关类/方法]
 * @since   [产品/模块]
 */
public class TableEditResult {

	private String value;
	private String text;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
