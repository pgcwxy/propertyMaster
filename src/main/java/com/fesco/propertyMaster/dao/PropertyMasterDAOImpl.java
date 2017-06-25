package com.fesco.propertyMaster.dao;

import com.fesco.propertyMaster.model.BaseImdConfig;
import com.fesco.propertyMaster.utils.ConsoleUtils;
import com.fesco.propertyMaster.utils.ResultMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @date 11:43 2017/6/15
 * @author 王新宇
 */
public class PropertyMasterDAOImpl extends JdbcDaoSupport implements IPropertyMasterDAO {
	Logger logger = Logger.getLogger(PropertyMasterDAOImpl.class);
	protected class BaseImdConfigRowMapper extends ResultMapper<BaseImdConfig> {
		@Override
		public BaseImdConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseImdConfig baseImdConfig = new BaseImdConfig();
			if (rs.getString("BASE_IMD_CONFIG_ID")!=null) {
				baseImdConfig.setBaseImdConfigId(rs.getLong("BASE_IMD_CONFIG_ID"));
			}
			if (rs.getString("PROJECT")!=null) {
				baseImdConfig.setProject(rs.getInt("PROJECT"));
			}
			if (rs.getString("KEY_")!=null) {
				baseImdConfig.setKey(rs.getString("KEY_").trim());
			}
			if (rs.getString("VALUE_")!=null) {
				baseImdConfig.setValue(rs.getString("VALUE_").trim());
			}
			if (rs.getString("IS_EFFECT")!=null) {
				baseImdConfig.setIsEffect(rs.getShort("IS_EFFECT"));
			}
			if (rs.getString("VERSION_")!=null) {
				baseImdConfig.setVersion(rs.getLong("VERSION_"));
			}
			if (rs.getString("OPR_TYPE")!=null) {
				baseImdConfig.setOprType(rs.getInt("OPR_TYPE"));
			}
			if (rs.getString("CRE_TIME")!=null) {
				baseImdConfig.setCreTime(rs.getDate("CRE_TIME"));
			}
			if (rs.getString("CRE_NAME")!=null) {
				baseImdConfig.setCreName(rs.getString("CRE_NAME"));
			}
			if (rs.getString("LMDF_TIME")!=null) {
				baseImdConfig.setLmdfTime(rs.getDate("LMDF_TIME"));
			}
			if (rs.getString("LMDF_NAME")!=null) {
				baseImdConfig.setLmdfName(rs.getString("LMDF_NAME"));
			}
			if (rs.getString("REMARK")!=null) {
				baseImdConfig.setRemark(rs.getString("REMARK"));
			}
			return baseImdConfig;
		}
	}
	@Override
	public List<BaseImdConfig> getValidProperties() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT *                     ");
		sql.append("  FROM base_imd_config t     ");
		sql.append(" where t.is_effect = 1       ");
		sql.append("   and t.project = 1         ");
		sql.append(" order by t.version_         ");
		return this.getJdbcTemplate().query(sql.toString(),new BaseImdConfigRowMapper());
	}

	@Override
	public void insertNewConfig(List<BaseImdConfig> baseImdConfigs) {
		if(baseImdConfigs==null||baseImdConfigs.size()==0){
			logger.error("参数异常");
			throw new RuntimeException("参数异常");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("{call PKG_BASE_IMD_CONFIG.insert_new_config(?,?,?,?)}");
		Connection connection = this.getConnection();
		for(BaseImdConfig baseImdConfig:baseImdConfigs){
			CallableStatement callableStatement = null;
		try {
			ConsoleUtils.println("新增的数据是："+baseImdConfig.getKey()+"="+baseImdConfig.getValue());
			callableStatement = connection.prepareCall(sql.toString());
			callableStatement.setString(1,baseImdConfig.getKey());
			callableStatement.setString(2,baseImdConfig.getValue());
			callableStatement.setString(3,baseImdConfig.getRemark());
			callableStatement.setString(4,baseImdConfig.getCreName());
			callableStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException("新增配置存储报错",e);
		}finally {
			try {
				callableStatement.close();
			} catch (SQLException e) {
				throw new RuntimeException("新增配置存储报错",e);
			}
		}

		}

	}

	@Override
	public void updateConfig(List<BaseImdConfig> baseImdConfigs) {
		if(baseImdConfigs==null||baseImdConfigs.size()==0){
			logger.error("参数异常");
			throw new RuntimeException("参数异常");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("{call PKG_BASE_IMD_CONFIG.update_config(?,?,?,?)}");
		Connection connection = this.getConnection();
		for(BaseImdConfig baseImdConfig:baseImdConfigs){
			CallableStatement callableStatement = null;
			try {
				ConsoleUtils.println("更新的数据是："+baseImdConfig.getKey()+"="+baseImdConfig.getValue());
				callableStatement = connection.prepareCall(sql.toString());
				callableStatement.setString(1,baseImdConfig.getKey());
				callableStatement.setString(2,baseImdConfig.getValue());
				callableStatement.setString(3,baseImdConfig.getRemark());
				callableStatement.setString(4,baseImdConfig.getLmdfName());
				callableStatement.execute();
			} catch (SQLException e) {
				throw new RuntimeException("新增配置存储报错");
			}finally {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException("新增配置存储报错");
				}
			}

		}
	}

	@Override
	public void deleteConfig(List<BaseImdConfig> baseImdConfigs) {
		if(baseImdConfigs==null||baseImdConfigs.size()==0){
			logger.error("参数异常");
			throw new RuntimeException("参数异常");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("{call PKG_BASE_IMD_CONFIG.delete_config(?,?,?)}");
		Connection connection = this.getConnection();
		for(BaseImdConfig baseImdConfig:baseImdConfigs){
			CallableStatement callableStatement = null;
			try {
				ConsoleUtils.println("删除的键是："+baseImdConfig.getKey());
				callableStatement = connection.prepareCall(sql.toString());
				callableStatement.setString(1,baseImdConfig.getKey());
				callableStatement.setString(2,baseImdConfig.getRemark());
				callableStatement.setString(3,baseImdConfig.getLmdfName());
				callableStatement.execute();
			} catch (SQLException e) {
				throw new RuntimeException("新增配置存储报错");
			}finally {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException("新增配置存储报错");
				}
			}

		}
	}
}
