package com.hh.improve.common.util;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author liuwh
 * @since 2015-06-27
 *
 */
public class UUIDUtils {
	/**
	 * 将带-的36位UUID转成不带-的32位UUID
	 * @param uuid36
	 * @return 
	 */
	public static String from36to32(String uuid36){
		return uuid36.replaceAll("-", "");
	}
	/**
	 * 将不带-的32位UUID转成带-的36位UUID
	 * @param uuid36
	 * @return 
	 */
	public static String from32to36(String uuid32){
		StringBuffer sb=new StringBuffer();
		sb.append(uuid32.substring(0, 8)).append("-");
		sb.append(uuid32.substring(8, 12)).append("-");
		sb.append(uuid32.substring(12, 16)).append("-");
		sb.append(uuid32.substring(16, 20)).append("-");
		sb.append(uuid32.substring(20));		
		return sb.toString();
	}
	/**
	 * 随机生成32位的UUID
	 * @return
	 */
	public static String getUUID32(){
		return from36to32(UUID.randomUUID().toString());
	}
	/**
	 * 随机生成36位的UUID
	 * @return
	 */
	public static String getUUID36(){
		return UUID.randomUUID().toString();
	}
	
	/*public static void main(String[] args){
		String uuid36=getUUID36();
		String uuid32=getUUID32();
		System.out.println(uuid36);
		System.out.println(from36to32(uuid36));
		System.out.println(from32to36(from36to32(uuid36)));
		System.out.println(uuid32);
		System.out.println(from32to36(uuid32));
		
	}*/
}
