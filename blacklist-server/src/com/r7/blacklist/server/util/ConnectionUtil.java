package com.r7.blacklist.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {

	public static synchronized Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/r7", "r7", "rrrrrrr");
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void commit(boolean boo) {
		try {
			if (boo) {
				ConnectionUtil.getConnection().commit();
			} else {
				ConnectionUtil.getConnection().rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void release(PreparedStatement ps) {
		release(ps);
	}

	public static void release(PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null) {
				Connection connection = ps.getConnection();

				if (connection != null) {
					connection.close();
				}

				ps.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
