package com.fesco.propertyMaster.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Administrator on 2017/6/13.
 */
public class DynamicDateSource extends AbstractRoutingDataSource {

	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getDateSourceType();
	}
}
