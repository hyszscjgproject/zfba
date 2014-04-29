package com.hyjx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.hyjx.DTO.BLOGDTO;
import com.hyjx.util.PropertiesUnit;

/**
 * 添加数据库1111111111111111111111dfgasdfasdf
 * 
 * @author hyjxzmq
 * 
 */
public class InsertDao {
	public void insertBatch(List<BLOGDTO> list) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = PropertiesUnit.getProperties("insertsql");
		connection = ConnDB.getConnection();
		try {
			if (list != null && list.size() > 0) {
				preparedStatement = connection.prepareStatement(sql);
				for (BLOGDTO dto : list) {
					preparedStatement.setString(1, dto.getSend_date());
					preparedStatement.setString(2, dto.getUser_type());
					preparedStatement.setString(3, dto.getContent());
					preparedStatement.setString(4, dto.getKeyword());
					preparedStatement.setString(5, dto.getRece_date());
					preparedStatement.setString(6, dto.getStatus());
					preparedStatement.setString(7, dto.getTouser());
					preparedStatement.setString(8, dto.getBlog_user());
					preparedStatement.setString(9, dto.getId());
					preparedStatement.setString(10, dto.getBlog_id());
					preparedStatement.setString(11, dto.getImageurl());
					preparedStatement.setString(12, dto.getUseravatar());
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert(BLOGDTO dto) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = PropertiesUnit.getProperties("insertsql");
		connection = ConnDB.getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dto.getSend_date());
			preparedStatement.setString(2, dto.getUser_type());
			preparedStatement.setString(3, dto.getContent());
			preparedStatement.setString(4, dto.getKeyword());
			preparedStatement.setString(5, dto.getRece_date());
			preparedStatement.setString(6, dto.getStatus());
			preparedStatement.setString(7, dto.getTouser());
			preparedStatement.setString(8, dto.getBlog_user());
			preparedStatement.setString(9, dto.getId());
			preparedStatement.setString(10, dto.getBlog_id());
			preparedStatement.setString(11, dto.getImageurl());
			preparedStatement.setString(12, dto.getUseravatar());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
