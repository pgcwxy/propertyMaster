package com.fesco.propertyMaster.bo;

import com.fesco.propertyMaster.dao.IPropertyMasterDAO;
import com.fesco.propertyMaster.model.BaseImdConfig;
import com.fesco.propertyMaster.model.Environment;
import com.fesco.propertyMaster.model.PropertyUnit;
import com.fesco.propertyMaster.spring.CustomerContextHolder;
import com.fesco.propertyMaster.utils.ConsoleUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @date 10:04 2017/6/15
 * @author 王新宇
 *
 */
public class PropertyMasterBOImpl implements IPropertyMasterBO {
	Logger logger = Logger.getLogger(PropertyMasterBOImpl.class);
	private IPropertyMasterDAO propertyMasterDAO;

	public void insertNewConfig(List<BaseImdConfig> baseImdConfigs) {
		if (baseImdConfigs!=null&&baseImdConfigs.size()>0) {
			ConsoleUtils.println("开始向数据库中新增配置，共有"+baseImdConfigs.size()+"条数据需要新增");
			propertyMasterDAO.insertNewConfig(baseImdConfigs);
			ConsoleUtils.println("新增完毕");
		}else {
			return;
		}

	}

	public void updateConfig(List<BaseImdConfig> baseImdConfigs) {
		if (baseImdConfigs!=null&&baseImdConfigs.size()>0) {
			ConsoleUtils.println("开始向数据库中更新配置，共有"+baseImdConfigs.size()+"条数据需要更新");
			propertyMasterDAO.updateConfig(baseImdConfigs);
			ConsoleUtils.println("更新完毕");
		}else {
			return;
		}
	}

	public void deleteConfig(List<BaseImdConfig> baseImdConfigs) {
		if (baseImdConfigs!=null&&baseImdConfigs.size()>0) {
			ConsoleUtils.println("开始删除数据库中的配置，共有"+baseImdConfigs.size()+"条数据需要删除");
			propertyMasterDAO.deleteConfig(baseImdConfigs);
			ConsoleUtils.println("删除完毕");
		}else {
			return;
		}
	}

	public List<BaseImdConfig> getInsertConfigs(PropertyUnit propertyUnit) {
		if(propertyUnit==null||propertyUnit.getPropertyFile()==null){
			throw new RuntimeException("入参异常，PropertyUnit对象为空或者其中的PropertyFile对象为空");
		}
		//从数据库获得的配置list
		List<BaseImdConfig> dbProperties = propertyMasterDAO.getValidProperties();
		//从svn获得的配置list
		List<BaseImdConfig> svnProperties = getSvnProperties(propertyUnit);
		//需要新增的配置list
		List<BaseImdConfig> needInsertConfigs = new ArrayList<BaseImdConfig>();
		outFor:
		for(BaseImdConfig svnBaseImdConfig :svnProperties){
			for(BaseImdConfig dbBaseImdConfig:dbProperties){
				if(dbBaseImdConfig.getKey().equals(svnBaseImdConfig.getKey())){
					continue outFor;
				}
			}
			svnBaseImdConfig.setRemark("未添加注释，可以通过修改数据库方式添加");
			svnBaseImdConfig.setCreName(propertyUnit.getSvnUserName());
			needInsertConfigs.add(svnBaseImdConfig);
		}
		return needInsertConfigs;
	}
	public List<BaseImdConfig> getSvnProperties(PropertyUnit propertyUnit){
		List<BaseImdConfig> svnProperties = new ArrayList<BaseImdConfig>();
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertyUnit.getPropertyFile()));
		} catch (IOException e) {
			logger.error("读取从svn下载下来的配置文件失败");
			throw new RuntimeException("读取从svn下载下来的配置文件失败");
		}
		Iterator<Map.Entry<Object, Object>> entryIterator = properties.entrySet().iterator();
		while (entryIterator.hasNext()){
			Map.Entry<Object, Object> next = entryIterator.next();
			BaseImdConfig baseImdConfig = new BaseImdConfig();
			baseImdConfig.setKey(next.getKey().toString().trim());
			baseImdConfig.setValue(next.getValue().toString().trim());
			svnProperties.add(baseImdConfig);
		}
		return svnProperties;
	}

	public List<BaseImdConfig> getUpdateConfigs(PropertyUnit propertyUnit) {
		if(propertyUnit==null||propertyUnit.getPropertyFile()==null){
			throw new RuntimeException("入参异常，PropertyUnit对象为空或者其中的PropertyFile对象为空");
		}
		//从数据库获得的配置list
		List<BaseImdConfig> dbProperties = propertyMasterDAO.getValidProperties();
		//从svn获得的配置list
		List<BaseImdConfig> svnProperties = getSvnProperties(propertyUnit);
		//需要新增的配置list
		List<BaseImdConfig> needUpdateConfigs = new ArrayList<BaseImdConfig>();
		outFor:
		for(BaseImdConfig dbBaseImdConfig:dbProperties){
			for(BaseImdConfig svnBaseImdConfig:svnProperties){
				if(dbBaseImdConfig.getKey().equals(svnBaseImdConfig.getKey())){
					if(!dbBaseImdConfig.getValue().equals(svnBaseImdConfig.getValue())){
						dbBaseImdConfig.setValue(svnBaseImdConfig.getValue());
						dbBaseImdConfig.setLmdfName(propertyUnit.getSvnUserName());
						svnBaseImdConfig.setRemark("未添加注释，可以通过修改数据库方式添加");
						needUpdateConfigs.add(dbBaseImdConfig);
					}
				}
			}
		}
		return needUpdateConfigs;
	}

	public List<BaseImdConfig> getDeleteConfigs(PropertyUnit propertyUnit) {
		if(propertyUnit==null||propertyUnit.getPropertyFile()==null){
			throw new RuntimeException("入参异常，PropertyUnit对象为空或者其中的PropertyFile对象为空");
		}
		//从数据库获得的配置list
		List<BaseImdConfig> dbProperties = propertyMasterDAO.getValidProperties();
		//从svn获得的配置list
		List<BaseImdConfig> svnProperties = getSvnProperties(propertyUnit);
		//需要删除的配置list
		List<BaseImdConfig> needDeleteConfigs = new ArrayList<BaseImdConfig>();
		outFor:
		for(BaseImdConfig dbBaseImdConfig:dbProperties){
			for(BaseImdConfig svnBaseImdConfig:svnProperties){
				if(dbBaseImdConfig.getKey().equals(svnBaseImdConfig.getKey())){
					continue outFor;
				}
			}
			dbBaseImdConfig.setLmdfName(propertyUnit.getSvnUserName());
			dbBaseImdConfig.setRemark("未添加注释，可以通过修改数据库方式添加");
			needDeleteConfigs.add(dbBaseImdConfig);
		}
		return needDeleteConfigs;
	}

	public void executePropertyFile(Map<Environment, PropertyUnit> propertyUnitMap) {
		Iterator<Map.Entry<Environment, PropertyUnit>> iterator = propertyUnitMap.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<Environment, PropertyUnit> entry = iterator.next();
			//切换数据源
			CustomerContextHolder.setDateSourceType(entry.getKey());
			ConsoleUtils.println("切换数据源到"+entry.getKey()+"环境");

			this.insertNewConfig(this.getInsertConfigs(entry.getValue()));
			this.deleteConfig(this.getDeleteConfigs(entry.getValue()));
			this.updateConfig(this.getUpdateConfigs(entry.getValue()));
		}

	}
//getters and setters

	public IPropertyMasterDAO getPropertyMasterDAO() {
		return propertyMasterDAO;
	}

	public void setPropertyMasterDAO(IPropertyMasterDAO propertyMasterDAO) {
		this.propertyMasterDAO = propertyMasterDAO;
	}
}
