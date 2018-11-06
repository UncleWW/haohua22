/**
 * 
 */
package com.hh.improve.common.mybatis;

import com.hh.improve.common.constants.STATUS;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 011589
 *
 */
public class MyBatisEnumHandlerSTATUS implements TypeHandler<STATUS> {

	@Override
	public void setParameter(PreparedStatement ps, int i, STATUS status, JdbcType jdbcType) throws SQLException {
		ps.setString(i, status.getValue());
	}

	@Override
	public STATUS getResult(ResultSet rs, String columnName) throws SQLException {
		String status = rs.getString(columnName);
		return STATUS.newValueOf(status);
	}

	@Override
	public STATUS getResult(ResultSet rs, int columnIndex) throws SQLException {
		String status = rs.getString(columnIndex);
		return STATUS.newValueOf(status);
	}

	@Override
	public STATUS getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String status = cs.getString(columnIndex);
		return STATUS.newValueOf(status);
	}

}
