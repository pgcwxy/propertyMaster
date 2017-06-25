package com.fesco.propertyMaster.dao;

import com.fesco.propertyMaster.model.BaseImdConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
public interface IPropertyMasterDAO {
	public List<BaseImdConfig> getValidProperties();
	void insertNewConfig(List<BaseImdConfig> baseImdConfigs);
	void updateConfig(List<BaseImdConfig> baseImdConfigs);
	void deleteConfig(List<BaseImdConfig> baseImdConfigs);
}

