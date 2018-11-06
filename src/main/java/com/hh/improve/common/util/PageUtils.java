/**
 * 
 */
package com.hh.improve.common.util;

import com.github.pagehelper.PageInfo;
import com.hh.improve.common.pojo.PageResult;

/**
 * @author 011589
 *
 */
public class PageUtils {

	public static <T> PageResult<T> tranferPageInfo(PageInfo<T> pageInfo) {
		PageResult<T> pageResult = new PageResult<T>();
		pageResult.setRows(pageInfo.getList());
		pageResult.setTotal(pageInfo.getTotal());
		pageResult.setPageNumber(pageInfo.getPageNum());
		pageResult.setPageSize(pageInfo.getPageSize());
		return pageResult;
	}
}
