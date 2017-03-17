package com.tal.coupons.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides static method to interact with the DB via jdbc
 * @author Sol Invictus
 *
 */
public class JdbcUtils {


	static
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static Connection getConnection() throws SQLException{
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/coupons_manager","root", 
				"toor");
		// localhost means "THIS (LOCAL) COMPUTER"
		// SCHEMA_NAME_YOU_NEED_TO_RENAME is the DB name, also known as Schema name.
		// in your DB you will need to SCHEMA_NAME_YOU_NEED_TO_RENAME 
		//into to YOUR schema name	
		return connection;
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement){
		try {
			if(connection!=null)
			{
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Write to log that we have a resource leak
		}

		try {
			if(preparedStatement!=null)
			{
				preparedStatement.close();
			}
		} catch (SQLException e) {
			// Write to log that we have a resource leak
			e.printStackTrace();
		}
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		closeResources(connection, preparedStatement);
		try {
			if(resultSet!=null)
			{
				resultSet.close();
			}
		} catch (SQLException e) {
			// Write to log that we have a resource leak
			e.printStackTrace();
		}

	}

}
