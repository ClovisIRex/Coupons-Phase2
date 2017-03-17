package com.tal.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import com.tal.coupons.utils.JdbcUtils;
import com.tal.coupons.beans.Company;
import com.tal.coupons.dao.interfaces.ICompanyDao;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.exceptions.ApplicationException;


/**
 * This Dao object is responsible with altering the DB in Company-related columns and tables.
 * @author Sol Invictus
 *
 */
public class CompanyDao implements ICompanyDao {
	
	public CompanyDao() {
	}

	/**
	 * This function creates a new company entry in the db
	 * @author Sol Invictus
	 */
	@Override
	public void createCompany(Company company) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys	= null;

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			
			connection = JdbcUtils.getConnection();
			
			
			//creating the SQL query
			
			String sql = "INSERT INTO companies (COMPANY_NAME,PASSWORD,EMAIL) VALUES (?,?,?)";
			

			// Creating a statement object which holds the SQL we're about to execute  + the option to return generate keys for the id
			
	
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			
			// Replacing question marks with their correct values
			
			preparedStatement.setString(1,company.getCompName());
			preparedStatement.setString(2,company.getPassword() );
			preparedStatement.setString(3, company.getEmail());
			
			// executeUpdate is a method used in order to : insert, delete, update (not get)
			
			preparedStatement.executeUpdate();		
			
			// Handling the generated Keys
			
			generatedKeys = preparedStatement.getGeneratedKeys();
			
			
			if( generatedKeys.next()) {
				company.setId(generatedKeys.getLong(1));
			}
			else {
				throw new SQLException("Failed to create company user, no ID obtained.");
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
	 * This function deletes a company entry and all its stock coupons in the db.
	 * @author Sol Invictus
	 */
	@Override
	public void removeCompany(long companyID) throws ApplicationException {
		
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();

			// Creating a statement object which holds the SQL we're about to execute
			//Deleting from the coupons table all the coupons of the deleted company as well as deleting the company
			
			String sql = "DELETE s, r FROM companies s LEFT JOIN coupons r ON s.COMPANY_ID = r.COMPANY_ID WHERE s.COMPANY_ID = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			// Replacing question mark with the company id
			
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
	 * This function alters details of a company entry in the db
	 * @author Sol Invictus
	 */
	@Override
	public void updateCompany(Company company) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			String sql = "UPDATE companies SET COMPANY_NAME = ?,PASSWORD = ?,EMAIL = ?  WHERE COMPANY_ID = ?";
			
			// Creating a statement object which holds the SQL we're about to execute
			
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question marks with their correct values
			
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setLong(4, company.getId());
			
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
	 * This function gets a company id, and returns a company object via searching the db for the 
	 * company row which correspondes with that id.
	 * 
	 * @author Sol Invictus
	 */
	@Override
	public Company getCompanyById(long companyId) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company companyReturned = new Company();
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM companies WHERE COMPANY_ID = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setLong(1, companyId);
			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			//If no results were found in the db, our function won't return anything
			if(!resultSet.next()) {
				return null;
			}
			
			//extracting data from the resultSet in order to build the company object to be returned
			
			companyReturned.setId(resultSet.getLong("COMPANY_ID"));
		    companyReturned.setCompName(resultSet.getString("COMPANY_NAME"));
			companyReturned.setEmail(resultSet.getString("EMAIL"));
			companyReturned.setPassword(resultSet.getString("PASSWORD"));
		} 
		
		catch (SQLException e) 
		{
			throw new ApplicationException(ErrorType.DAO_GET_ERROR, e, "Failed to get due to :" + e.getMessage());
		} 
		finally 
		{
			JdbcUtils.closeResources(connection, preparedStatement,resultSet);
		}
		
		return companyReturned;
	}
	
	
	/**
	 * This function returns a collection of all company objects in the db
	 * by retrieving all the values and assembling them as Company objects
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Company> getAllCompanies() throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;
		Collection<Company> allCompanies = new ArrayList<>(); 
		
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			String sql = "SELECT * FROM companies";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			
			// executing query, putting result returned by the function in resultSet
			resultSet = preparedStatement.executeQuery(); 
			
			/*iterating on all the results and assembling the company objects, then putting each to
			 *the collection to be returned
			*/
			while(resultSet.next()) {
				company = new Company();
				
				company.setId(resultSet.getLong("COMPANY_ID"));
				company.setCompName(resultSet.getString("COMPANY_NAME"));
				company.setEmail(resultSet.getString("EMAIL"));
				company.setPassword(resultSet.getString("PASSWORD"));
				
				allCompanies.add(company);	
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
		
		return allCompanies;		
	}
	
	/**
	 * This function checks whether a company name and password match those in the db. returns true or false based on result.
	 * @author Sol Invictus
	 */
	@Override
	public boolean login(String companyName, String password) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT COMPANY_NAME FROM companies WHERE COMPANY_NAME = ? AND PASSWORD = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their companyID
			preparedStatement.setString(1, companyName);
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
	 * This function checks if a company exists by fetching db details according to it's name
	 * @author Sol Invictus
	 */
	public boolean isCompanyExistByName(String companyName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			// Getting a connection from the connections manager (getConnection is a static method)
			connection = JdbcUtils.getConnection();
			
			//creating the SQL query
			
			String sql = "SELECT * FROM companies WHERE COMPANY_NAME = ?";

			// Creating a statement object which holds the SQL we're about to execute
			preparedStatement = connection.prepareStatement(sql);

			// Replacing question mark with their couponID
			preparedStatement.setString(1, companyName);
			
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

}
