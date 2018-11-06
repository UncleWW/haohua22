package com.hh.improve.common.util;

import java.io.File;
import java.io.IOException;

/**
 * 获取项目根路径
 * 
 * @author wangwei
 * @since 2015-06-27
 *
 */
public class PathUtil {
	public static String getCanonicalPath(){
//		File directory = new File("");
//    	String path="D/:";//默认为D盘
//		try {
//			path = directory.getCanonicalPath();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	return "";
	}
	public static void main(String args[]){
		File directory = new File("");
    	String path="";
		try {
			path = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(path);
	}
	
}
