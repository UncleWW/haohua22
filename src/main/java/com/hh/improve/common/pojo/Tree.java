package com.hh.improve.common.pojo;

public class Tree {

	private String id;
	
	private String pId;
	
	private String name;
	
	private String t;//title
	
	private	boolean	open;	//是否展开子节点
	
	private	boolean	isParent;	//是否是父节点
	
	
	
	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getpId() {
		return pId;
	}
	
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
