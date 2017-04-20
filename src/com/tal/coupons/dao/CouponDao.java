package com.tal.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mysql.jdbc.ResultSetMetaData;
import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Coupon;
import com.tal.coupons.dao.interfaces.ICouponsDao;
import com.tal.coupons.enums.CouponType;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.utils.JdbcUtils;


/**
 * This Dao object is responsible with altering the DB in Coupon-related columns and tables.
 * @author Sol Invictus
 *
 */
public class CouponDao implements ICouponsDao {
	
	public CouponDao() {
	}
	
	/**
	 * This function creastes a new coupon entry in the db.
	 * @author Sol Invictus
	 */
	@Override
	public void createCoupon(Coupon coupon) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys	= null;

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "INSERT INTO coupons (COUPON_TITLE,START_DATE,END_DATE,"
						+ "AMOUNT,COUPON_TYPE,COUPON_MESSAGE,"
						+ "COUPON_PRICE,COUPON_IMAGE_PATH,COMPANY_ID) VALUES (?,?,?,?,?,?,?,?,?)";
			

			// Creating a statement object which holds the SQL we're about to execute  + the option to return generate keys for the id
			
	
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			
			// Replacing question marks with their correct values
			
			preparedStatement.setString(1,coupon.getCouponTitle());
			preparedStatement.setLong(2, coupon.getCouponStartDate());
			preparedStatement.setLong(3, coupon.getCouponEndDate());
			preparedStatement.setInt(4, coupon.getCouponsInStock());
			preparedStatement.setString(5, coupon.getCouponType());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setDouble(7, coupon.getCouponPrice());
			preparedStatement.setString(8, coupon.getCouponImage());
			preparedStatement.setLong(9, coupon.getCompanyID());
			
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			
			preparedStatement.executeUpdate();		
			
			// Handling the generated Keys
			
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			
			if( generatedKeys.next()) {
				coupon.setCouponId(generatedKeys.getLong(1));
			}
			else {
				throw new SQLException("Failed to create coupon, no ID obtained.");
			}					
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_CREATE_ERROR, e, "Failed to create due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement, generatedKeys);
		}
		
	}
	
	
	/**
	 * This function removes a coupon from the db according to its ID.
	 * @author Sol Invictus
	 */
	@Override
	public void removeCoupon(long couponID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			// Deleting the coupon from the join_customer_coupon table as well
			
			String sql = "DELETE s, r FROM coupons s LEFT JOIN join_customer_coupon r ON s.COUPON_ID = r.COUPON_ID WHERE s.COUPON_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			
			// Replacing question mark with the coupon id
			
			preparedStatement.setLong(1, couponID);
			
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

		} 
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR,e, "Failed to remove due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	/**
	 * This function updates a coupon's details in the db.
	 * @author Sol Invictus
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			String sql = "UPDATE coupons "
					+ "SET COUPON_TITLE = ?,START_DATE = ?,END_DATE = ?,AMOUNT = ?"
					+ ",COUPON_TYPE = ?,COUPON_MESSAGE = ?,COUPON_PRICE = ?,COUPON_IMAGE_PATH = ?,COMPANY_ID = ?  WHERE COUPON_ID = ?";
			
			// Creating a statement object which holds the SQL we're about to execute
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with their correct values
			
			preparedStatement.setString(1, coupon.getCouponTitle());
			preparedStatement.setLong(2, coupon.getCouponStartDate());
			preparedStatement.setLong(3, coupon.getCouponEndDate());
			preparedStatement.setInt(4, coupon.getCouponsInStock());
			preparedStatement.setString(5, coupon.getCouponType());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setDouble(7, coupon.getCouponPrice());
			preparedStatement.setString(8, coupon.getCouponImage());
			preparedStatement.setLong(9, coupon.getCompanyID());
			preparedStatement.setLong(10, coupon.getCouponId());
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

		} 
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_UPDATE_ERROR, e, "Failed to update due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	/**
	 * This function performes a coupon purchase- inserts values into join_customer_coupon table.
	 * @author Sol Invictus
	 */
	@Override
	public void purchaseCoupon(long customerID, long couponID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "INSERT INTO join_customer_coupon (CUSTOMER_ID,COUPON_ID) VALUES (?,?)";
			
			// Creating a statement object which holds the SQL we're about to execute  + the option to return generate keys for the id
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with their correct values
			
			preparedStatement.setLong(1,customerID);
			preparedStatement.setLong(2,couponID);
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			
			preparedStatement.executeUpdate();		
			
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_CREATE_ERROR, e, "Failed to create customer coupon due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	/**
	 * This function creates a coupon bean object from fetching db details according to it's ID
	 * @author Sol Invictus
	 */
	@Override
	public Coupon getCouponById(long couponId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon couponReturned = new Coupon();
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM coupons WHERE COUPON_ID = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their couponID
			preparedStatement.setLong(1, couponId);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			//If no results were found in the db, our function won't return anything
			if(!resultSet.next()) {
				return null;
			}
			
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
			
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return couponReturned;
	}
	
	
	

	/**
	 * This function creates a coupon bean list from fetching db details according to it's type
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM coupons WHERE COUPON_TYPE = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setString(1, couponType.getcouponType());
			
			// executing query, putting result returned by the function in resultSet, storing column count for iteration
			resultSet = preparedStatement.executeQuery(); 
			
			//extracting data from the resultSet in order to build the company object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}
	

	/**
	 * This function creates a coupon bean list from fetching db details according to the creator's company id 
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM coupons WHERE COMPANY_ID = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setLong(1, companyId);
			
			
			resultSet = preparedStatement.executeQuery(); 
			
			
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}
	
	

	/**
	 * This function creates a coupon bean list from fetching db details according to the buyer customer id crossed by 'join' query
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM"
					+ " (SELECT coupons.* FROM coupons "
					+ "INNER JOIN join_customer_coupon "
					+ "ON coupons.COUPON_ID=join_customer_coupon.COUPON_ID "
					+ "WHERE join_customer_coupon.CUSTOMER_ID = ?) AS innerSelect;";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setLong(1, customerId);
			
			
			resultSet = preparedStatement.executeQuery(); 
			
			
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}

	/**
	 * This function creates a coupon bean list of all coupons existing on the db.
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM coupons";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery(); 
				
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}
	
	/**
	 * This function creates a coupon bean list containing only all the coupons that were purchased using a JOIN filter.
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM"
					+ " (SELECT coupons.* FROM coupons "
					+ "INNER JOIN join_customer_coupon "
					+ "ON coupons.COUPON_ID=join_customer_coupon.COUPON_ID "
					+ ") AS innerSelect;";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery(); 
				
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}

	/**
	 * This function creates a coupon bean list containing only all the coupons that were purchased by using a
	 *  JOIN filter according to their type.
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM"
					+ " (SELECT coupons.* FROM coupons "
					+ "INNER JOIN join_customer_coupon "
					+ "ON coupons.COUPON_ID=join_customer_coupon.COUPON_ID "
					+ "WHERE coupons.COUPON_TYPE = ?) AS innerSelect;";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);
			
			// Replacing question mark with the type
			preparedStatement.setString(1, couponType.toString());
						

			resultSet = preparedStatement.executeQuery(); 
				
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}

	
	/**
	 * This function creates a coupon bean list containing only all the coupons that were purchased by using a
	 *  JOIN filter according to their price.
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double couponPrice) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM"
					+ " (SELECT coupons.* FROM coupons "
					+ "INNER JOIN join_customer_coupon "
					+ "ON coupons.COUPON_ID=join_customer_coupon.COUPON_ID "
					+ "WHERE coupons.COUPON_PRICE = ?) AS innerSelect;";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);
			
			// Replacing question mark with the price
			preparedStatement.setString(1, String.valueOf(couponPrice));
						

			resultSet = preparedStatement.executeQuery(); 
				
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}
	
	

	public Collection<Coupon> getAllPurchasedCouponsByCustomerId(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> allCouponsReturned = new ArrayList<Coupon>();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM"
					+ " (SELECT coupons.* FROM coupons "
					+ "INNER JOIN join_customer_coupon "
					+ "ON coupons.COUPON_ID=join_customer_coupon.COUPON_ID "
					+ "WHERE join_customer_coupon.CUSTOMER_ID = ?) AS innerSelect;";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);
			
			// Replacing question mark with the price
			preparedStatement.setLong(1, customerId);
						

			resultSet = preparedStatement.executeQuery(); 
				
			//extracting data from the resultSet in order to build the coupon object to be returned
			
			while(resultSet.next()) {
				Coupon couponReturned = new Coupon();
				this.extractDataFromResultSetToCouponBean(couponReturned,resultSet);
				allCouponsReturned.add(couponReturned);				
			}	
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return allCouponsReturned;
	}
	
	
	
	/**
	 * This function removes all the purchased coupons from the db according to their company ID.
	 * @author Sol Invictus
	 */
	public void removePurchasedCouponsByCompanyID(long companyID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			// Deleting the coupon from the join_customer_coupon table as well
			
			String sql = "DELETE FROM join_customer_coupon WHERE COUPON_ID IN"
					+ " (SELECT COUPON_ID FROM COUPONS WHERE COMPANY_ID = ?);";
			
			preparedStatement = connection.prepareStatement(sql);
			
			// Replacing question mark with the coupon id
			
			preparedStatement.setLong(1, companyID);
			
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			preparedStatement.executeUpdate();

		} 
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR,e, "Failed to remove due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	/**
	 * This function deletes coupons whose end date has expired from both the coupons and join tables.
	 *  for use with the janitor cleanup thread.
	 * @author Sol Invictus
	 */
	public void removeOldCoupons() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			preparedStatement = connection.prepareStatement("DELETE s, r FROM coupons s"
					+ " LEFT JOIN join_customer_coupon r "
					+ "ON s.COUPON_ID = r.COUPON_ID WHERE s.END_DATE <= NOW()");


			preparedStatement.executeUpdate();
					
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_DELETE_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
	}
	
	
	/**
	 * This function checks if a coupon exists by fetching db details according to it's Title
	 * @author Sol Invictus
	 */
	public boolean isCouponExistByTitle(String couponTitle) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM coupons WHERE COUPON_TITLE = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their couponID
			preparedStatement.setString(1, couponTitle);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			//If no results were found in the db, our function will return false
			if(!resultSet.next()) {
				return false;
			}
			
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return true;
	}
	
	/**
	 * This function checks if a the customer has already purchased the coupon
	 * @author Sol Invictus
	 */
	public boolean isCouponAlreadyPurchased(long customerID, long couponID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM join_customer_coupon WHERE CUSTOMER_ID = ? AND COUPON_ID = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with the id's
			preparedStatement.setLong(1, customerID);
			preparedStatement.setLong(2, couponID);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			//If no results were found in the db, our function will return false
			if(!resultSet.next()) {
				return false;
			}
			
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return true;
	}
	
	/**
	 * This is a local function designed to shorten repeated code segments throught this class.
	 * @param coupon
	 * @param resultSet
	 * @throws SQLException
	 * @author Sol Invictus
	 */
	private void extractDataFromResultSetToCouponBean (Coupon coupon, ResultSet resultSet) throws SQLException
	{
		coupon.setCouponId(resultSet.getLong("COUPON_ID"));
		coupon.setCouponTitle( resultSet.getString("COUPON_TITLE"));
		coupon.setCouponStartDate(resultSet.getLong("START_DATE"));
		coupon.setCouponEndDate(resultSet.getLong("END_DATE"));
		coupon.setCouponsInStock(resultSet.getInt("AMOUNT"));
		coupon.setCouponType(resultSet.getString("COUPON_TYPE"));
		coupon.setCouponMessage(resultSet.getString("COUPON_MESSAGE"));
		coupon.setCouponPrice(resultSet.getDouble("COUPON_PRICE"));
		coupon.setCouponImage(resultSet.getString("COUPON_IMAGE_PATH"));
		coupon.setCompanyID(resultSet.getLong("COMPANY_ID"));
	}
}
