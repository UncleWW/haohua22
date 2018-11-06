package com.hh.improve.common.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 011589
 * @date 2017-08-26
 */
public class PageResultWithSub<K, V> {

	private long total;
	
	private List<K> rows;
	
	private List<V> subRows;
	
	public PageResultWithSub () {
		this.rows = new ArrayList<K>();
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<K> getRows() {
		return rows;
	}
	
	public void setRows(List<K> rows) {
		this.rows = rows;
	}

	public List<V> getSubRows() {
		return subRows;
	}

	public void setSubRows(List<V> subRows) {
		this.subRows = subRows;
	}
	
}
