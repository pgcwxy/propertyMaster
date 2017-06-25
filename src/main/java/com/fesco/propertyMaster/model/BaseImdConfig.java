package com.fesco.propertyMaster.model;

import java.util.Date;

/**
 * 即时生效配置文件bean
 * @author 王新宇
 * @Date 17:18 2017/5/22
 */

public class BaseImdConfig implements java.io.Serializable {

	// Fields

	private Long baseImdConfigId;
	private String key;
	private String value;
	private Integer project;
	private Short isEffect;
	private Long version;
	private Integer oprType;
	private Date creTime;
	private String creName;
	private Date lmdfTime;
	private String lmdfName;
	private String remark;

	// Constructors

	/** default constructor */
	public BaseImdConfig() {
	}



	// Property accessors

	public Long getBaseImdConfigId() {
		return this.baseImdConfigId;
	}

	public void setBaseImdConfigId(Long baseImdConfigId) {
		this.baseImdConfigId = baseImdConfigId;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Short getIsEffect() {
		return this.isEffect;
	}

	public void setIsEffect(Short isEffect) {
		this.isEffect = isEffect;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Integer getOprType() {
		return this.oprType;
	}

	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}

	public Date getCreTime() {
		return this.creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public String getCreName() {
		return this.creName;
	}

	public void setCreName(String creName) {
		this.creName = creName;
	}

	public Date getLmdfTime() {
		return this.lmdfTime;
	}

	public void setLmdfTime(Date lmdfTime) {
		this.lmdfTime = lmdfTime;
	}

	public String getLmdfName() {
		return this.lmdfName;
	}

	public void setLmdfName(String lmdfName) {
		this.lmdfName = lmdfName;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}