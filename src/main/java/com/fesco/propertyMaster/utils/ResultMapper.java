/** 
 * Name: net.uni.ap.jdbc.ResultMapper.java
 * Version: 1.0
 * Date: 2012-2-16
 * Author: 孙伟
 */
package com.fesco.propertyMaster.utils;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class ResultMapper<T> implements RowMapper<T> {
	
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getString(ResultSet rs, String column){
		try{
			String value=rs.getString(column);
			return (value==null||"".equals(value))?null:value;
		}catch(SQLException e){
			return null;
		}
	}
	
	public Short getShort(ResultSet rs, String column){
		String value=getString(rs,column);
		return value==null?null: Short.valueOf(value);
	}
	
	public Long getLong(ResultSet rs, String column){
		String value=getString(rs,column);
		return value==null?null: Long.valueOf(value);
	}
	
	public Integer getInt(ResultSet rs, String column){
		String value=getString(rs,column);
		return value==null?null: Integer.valueOf(value);
	}
	
	public Date getDate(ResultSet rs, String column){
		try{
			return simpleDateFormat.parse(getString(rs, column));
		}catch(Exception e){
			return null;
		}
	}
	
	public Timestamp getTimestamp(ResultSet rs, String column){
		try{
			return rs.getTimestamp(column);
		}catch(SQLException e){
			return null;
		}
	}
	
	public Double getDouble(ResultSet rs, String column){
		String value=getString(rs,column);
		return value==null?null: Double.valueOf(value);
	}
	
	public BigDecimal getBigDecimal(ResultSet rs, String column){
		try{
			return rs.getBigDecimal(column);
		}catch(SQLException e){
			return null;
		}
	}
	
	public Float getFloat(ResultSet rs, String column){
		String value=getString(rs,column);
		return value==null?null: Float.valueOf(value);
	}
	
	public Boolean getBoolean(ResultSet rs, String column){
		try{
			return rs.getBoolean(column);
		}catch(SQLException e){
			return false;
		}
		//String value=getString(rs,column);
		//return value==null?null:Boolean.valueOf(value);
	}
	
	public Blob getBlob(ResultSet rs, String column){
		try{
			return rs.getBlob(column);
		}catch(SQLException e){
			return null;
		}
	}
	
	public Clob getClob(ResultSet rs, String column){
		try{
			return rs.getClob(column);
		}catch(SQLException e){
			return null;
		}
	}
	public abstract T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
