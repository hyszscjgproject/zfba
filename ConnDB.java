package com.hyjx.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnDB {

	private static BasicDataSource dataSource = null;

	public static Connection getConnection() {
		Connection connection = null;
		Properties rm = getProp();
		try {
			Class.forName((String) rm
					.getProperty("wxdatasource.DRIVERCLASSNAME"));
			String url = (String) rm.getProperty("wxdatasource.URL");
			String user = (String) rm.getProperty("wxdatasource.USER");
			String pwd = (String) rm.getProperty("wxdatasource.PASSWORD");
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static Properties getProp() {
		InputStream in = ConnDB.class.getResourceAsStream("/db.properties");
		Properties prop = null;
		try {
			if (null == prop) {
				prop = new Properties();
			}
			prop.load(in);
		} catch (IOException e) {
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return prop;

	}

}