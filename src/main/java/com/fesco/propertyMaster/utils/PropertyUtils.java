package com.fesco.propertyMaster.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/13.
 */
public class PropertyUtils {
	private static Logger logger = Logger.getLogger(PropertyUtils.class);
	private static Properties properties = new Properties();
	static {

		try {
			properties.load(new FileInputStream(PathUtils.getJarPath()+System.getProperty("file.separator")+"config"+
					System.getProperty("file.separator")+"config.properties"));
		} catch (IOException e) {
			String message = "在jar包同级目录没有找到config文件夹中的config.properties文件，或者文件正在被占用不可读写";
			logger.error(message,e);
			ConsoleUtils.println(message);
		}
	}
	public static String getValue(String key){
		String value = properties.getProperty(key);
		if(value==null){
			logger.info("从配置文件中获取配置，key="+key+"，但是没有找到");
			return null;
		}
		logger.info("从配置文件中获取配置，key="+key+"，value="+value);
		return value.trim();
	}
}