package com.fesco.propertyMaster.bo;

import com.fesco.propertyMaster.model.BaseImdConfig;
import com.fesco.propertyMaster.model.Environment;
import com.fesco.propertyMaster.model.PropertyUnit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @date 9:30 2017/6/15
 * @author 王新宇
 */
public interface IPropertyMasterBO {
	@Transactional
	void insertNewConfig(List<BaseImdConfig> baseImdConfigs);
	@Transactional
	void updateConfig(List<BaseImdConfig> baseImdConfigs);
	@Transactional
	void deleteConfig(List<BaseImdConfig> baseImdConfigs);
	List<BaseImdConfig> getInsertConfigs(PropertyUnit propertyUnit);
	List<BaseImdConfig> getUpdateConfigs(PropertyUnit propertyUnit);
	List<BaseImdConfig> getDeleteConfigs(PropertyUnit propertyUnit);



	@Transactional
	void executePropertyFile(Map<Environment,PropertyUnit> propertyUnitMap);
}
