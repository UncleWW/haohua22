package com.hh.improve.common.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 011589
 * @date 2017-08-26
 */
public class PageResult<T> {

	private long total;
	
	private long pageNumber;
	
	private long pageSize;
	
	private List<T> rows;
	
	public PageResult () {
		this.rows = new ArrayList<T>();
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRows() {
		return rows;
	}
	
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
