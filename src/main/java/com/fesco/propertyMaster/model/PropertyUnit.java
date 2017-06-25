package com.fesco.propertyMaster.model;

import com.fesco.propertyMaster.utils.StringUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/6/13.
 */
public class PropertyUnit {
	private String dbUrl;
	private String dbUserName;
	private String dbPassword;
	private String svnUrl;
	private String svnUserName;
	private String svnPassword;
	private File propertyFile;


	public boolean isIntegrated(){
		if (!StringUtils.nullOrEmptyString(dbUrl)&&
				!StringUtils.nullOrEmptyString(dbUserName)&&
				!StringUtils.nullOrEmptyString(dbPassword)&&
				!StringUtils.nullOrEmptyString(svnUrl)&&
				!StringUtils.nullOrEmptyString(svnUserName)&&
				!StringUtils.nullOrEmptyString(svnPassword)) {
			return true;
		}
		return false;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public File getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(File propertyFile) {
		this.propertyFile = propertyFile;
	}

	public String getSvnUserName() {
		return svnUserName;
	}

	public void setSvnUserName(String svnUserName) {
		this.svnUserName = svnUserName;
	}

	public String getSvnPassword() {
		return svnPassword;
	}

	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}
}
