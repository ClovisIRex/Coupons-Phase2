package com.tal.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.dao.interfaces.ICustomerDao;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.utils.JdbcUtils;


/**
 * This Dao object is responsible with altering the DB in Customer-related columns and tables.
 * @author Sol Invictus
 *
 */
public class CustomerDao implements ICustomerDao {
	
	public CustomerDao() {
	}

	/**
	 * This function creates a new customer entry in the db
	 * @author Sol Invictus
	 */
	@Override
	public void createCustomer(Customer customer) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys	= null;

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "INSERT INTO customers (CUSTOMER_NAME,PASSWORD) VALUES (?,?)";
			

			// Creating a statement object which holds the SQL we're about to execute  + the option to return generate keys for the id
			
	
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			
			// Replacing question marks with their correct values
			
			preparedStatement.setString(1,customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			
			
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			
			preparedStatement.executeUpdate();		
			
			// Handling the generated Keys
			
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			
			if(generatedKeys.next()) {
				customer.setId(generatedKeys.getLong(1));
			}
			else {
				throw new SQLException("Failed to create user, no ID obtained.");
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
	 * This function deletes a customer entry in the db and all its purchased coupons
	 * @author Sol Invictus
	 */
	@Override
	public void removeCustomer(long customerID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement("DELETE s, r FROM customers s"
															+ " LEFT JOIN join_customer_coupon r "
															+ "ON s.CUSTOMER_ID = r.CUSTOMER_ID WHERE s.CUSTOMER_ID = ?");

			
			// Replacing question mark with the customer id
			
			preparedStatement.setLong(1, customerID);
			
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
	 * This function alters details of a customer entry in the db
	 * @author Sol Invictus
	 */
	@Override
	public void updateCustomer(Customer customer) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			String sql = "UPDATE customers SET CUSTOMER_NAME = ?,PASSWORD = ? WHERE CUSTOMER_ID = ?";
			
			// Creating a statement object which holds the SQL we're about to execute
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with their correct values
			
			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setLong(3, customer.getId());
			
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
	 * This function gets a customer's id, and returns a customer object via searching the db for the 
	 * customer row which correspondes with that id.
	 * @author Sol Invictus
	 */
	@Override
	public Customer getCustomerById(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customerReturned = new Customer();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM customers WHERE CUSTOMER_ID = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their customerID
			preparedStatement.setLong(1, customerId);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			//If no results were found in the db, our function won't return anything
			if(!resultSet.next()) {
				return null;
			}
			
			//extracting data from the resultSet in order to build the customer object to be returned
			
			customerReturned.setId(resultSet.getLong("CUSTOMER_ID"));
			customerReturned.setCustName(resultSet.getString("CUSTOMER_NAME"));
			customerReturned.setPassword(resultSet.getString("PASSWORD"));
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return customerReturned;
	}

	/**
	 * This function returns a collection of all customer objects in the db
	 * by retrieving all the values and assembling them as Customer objects
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;
		Collection<Customer> allCustomers = new ArrayList<>(); 
		
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			String sql = "SELECT * FROM customers";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			/*iterating on all the results and assembling the company objects, then putting each to
			 *the collection to be returned
			*/
			while(resultSet.next()) {
				customer = new Customer();
				
				customer.setId(resultSet.getLong("CUSTOMER_ID"));
				customer.setCustName(resultSet.getString("CUSTOMER_NAME"));
				customer.setPassword(resultSet.getString("PASSWORD"));
				
				allCustomers.add(customer);	
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
		
		return allCustomers;		
	}
	
	/**
	 * This function checks whether a customer's name and password match those in the db. returns true or false based on result.
	 * @author Sol Invictus
	 */
	@Override
	public boolean login(String customerName, String password) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT CUSTOMER_NAME FROM customers WHERE CUSTOMER_NAME = ? AND PASSWORD = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setString(1, customerName);
			preparedStatement.setString(2, password);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			//If no results were found in the db, our function will return false
			if(!resultSet.next()) {
				return false;
			}
			
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.GENERAL_ERROR, e, "Failed due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return true;
	}

	
	/**
	 * This function checks if a customer exists by fetching db details according to it's name
	 * @author Sol Invictus
	 */
	public boolean isCustomerExistByName(String customerName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM customers WHERE CUSTOMER_NAME = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their couponID
			preparedStatement.setString(1, customerName);
			
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
	 * This function gets a customer name, and returns a customer object id searching the db for the 
	 * id which corresponds with that name.
	 * 
	 * @author Sol Invictus
	 */
	
	public long getIdByCustomerName(String customerName) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		long customerId = -1;
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT CUSTOMER_ID FROM customers WHERE CUSTOMER_NAME = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setLong(1, customerId);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			// extracting data
			customerId = resultSet.getLong("CUSTOMER_ID");
		    
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return customerId;
	}

}
