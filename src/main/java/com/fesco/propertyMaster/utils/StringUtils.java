package com.fesco.propertyMaster.utils;

/**
 * Created by Administrator on 2017/6/13.
 */
public class StringUtils {
	public static boolean nullOrEmptyString(String in){
		if(in==null||"".equals(in)){
			return true;
		}
		return false;
	}
	public static String getFirstWordCapital(String in){
		String temp = in.substring(0,1).toUpperCase();
		temp +=in.substring(1,in.length());
		return temp;
	}
}
