package com.fesco.propertyMaster.spring;

import com.fesco.propertyMaster.model.Environment;

/**
 * Created by Administrator on 2017/6/13.
 */
public class CustomerContextHolder {
	public static final ThreadLocal<Environment> THREAD_LOCAL = new ThreadLocal<Environment>();
	public static void setDateSourceType(Environment environment){
		THREAD_LOCAL.set(environment);
	}
	public static Environment getDateSourceType(){
		return THREAD_LOCAL.get();
	}
	public static void clearCustomerType() {
		THREAD_LOCAL.remove();
	}
}
