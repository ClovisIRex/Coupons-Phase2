package com.tal.coupons.dao.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.tal.coupons.utils.JdbcUtils;

/**
 * Tester class which contains 1 method which intitalizes a connection to the DB
 * @author Tal
 *
 */
public class ConnectionTester {
	
	public static void testConnection() { 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			connection = JdbcUtils.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (connection != null) {
				System.out.println("You made it, take control of your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

}
