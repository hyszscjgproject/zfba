package com.hyjx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hyjx.wx.TokenDTO;

/**
 * 数据库操作
 * 
 * @author hyjxzmq
 * 
 */
public class DbDAO {

	/**
	 * 查询token
	 * 
	 * @return
	 */
	public List<TokenDTO> queryTokeBaseInfo() {
		String sql = "select appid,secret, workstarttime,workendtime,token from wxtoken where status = '1' and now() between workstarttime and workendtime";
		Connection connection = ConnDB.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TokenDTO> dtos = new ArrayList<TokenDTO>();
		TokenDTO dto = null;

		try {
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				dto = new TokenDTO();
				dto.setAppid(rs.getString("appid"));
				dto.setSecret(rs.getString("secret"));
				dto.setWorkEndTime(rs.getString("workEndTime"));
				dto.setWorkStartTime(rs.getString("workStartTime"));
				dto.setToken(rs.getString("token"));
				dtos.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose(connection, pst, null, rs);
		}
		return dtos;

	}

	public void addTokenInfo(TokenDTO dto) {
		String sql = "insert into wxtoken values( replace(uuid(),'-','' ),?,?,?,?,?,'1')";
		Connection connection = ConnDB.getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, dto.getAppid());
			pst.setString(2, dto.getSecret());
			pst.setString(3, dto.getWorkStartTime());
			pst.setString(4, dto.getWorkEndTime());
			pst.setString(5, dto.getToken());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose(connection, pst, null, null);
		}
	}

	/**
	 * 置为无效
	 * 
	 * @param dto
	 */
	public void updateTokenStatus(TokenDTO dto) {
		String sql = "update wxtoken set status = '2' where appid = ?";
		Connection connection = ConnDB.getConnection();
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, dto.getAppid());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose(connection, pst, null, null);
		}
	}

	private void dbClose(Connection conn, PreparedStatement pst,
			Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private DbDAO() {

	}

	public static DbDAO getInstance() {
		return Sigle.instance;
	}

	private static class Sigle {
		private static DbDAO instance = new DbDAO();
	}

}
