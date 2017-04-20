package com.tal.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.dao.CustomerDao;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.interfaces.ICustomerLogic;
import com.tal.coupons.logic.interfaces.IUsersLogic;
import com.tal.coupons.utils.ValidationUtils;


/**
 * This Logic class is responsible with Customer-related actions.
 * @author Sol Invictus
 *
 */
public class CustomerLogic implements ICustomerLogic {
	
	private CustomerDao customerDao;
	
	public CustomerLogic() {
		this.customerDao = new CustomerDao();
	}

	
	/**
	 * This function checks for conditions while creating a customer.
	 * @author Sol Invictus
	 * 
	 */
	@Override
	public void createCustomer(Customer customer) throws ApplicationException {
		
		// before meddling down with the db, we must check if the user has entered valid inputs first to avoid waste of time accessing the db and such

		if(!ValidationUtils.isPasswordValid(customer.getPassword())) {
			throw new ApplicationException(ErrorType.INVALID_CREDENTIALS, null, "One or more fields are in an invalid format, try again");		
		}


		if(customerDao.isCustomerExistByName(customer.getCustName())) {
			throw new ApplicationException(ErrorType.CUSTOMER_NAME_ALREADY_EXISTS,null,"Cannot create customer, customer name already exists."
					+ " Try a different name.");
		}

		//If we got this point it means all is fine and it's ok to update
		customerDao.createCustomer(customer);
		System.out.println("Customer: " + customer.toString() + " Has been created!");
		
	}

	/**
	 * This function checks for conditions while deleting a customer and all its purchased coupons.
	 * @author Sol Invictus
	 */
	@Override
	public void removeCustomer(long customerID) throws ApplicationException {
		if(customerDao.getCustomerById(customerID) == null) {
			throw new ApplicationException(ErrorType.CUSTOMER_DOESNT_EXIST,null, "Cannot delete this customer, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
	
		customerDao.removeCustomer(customerID);;
		System.out.println("Customer with the ID: " + customerID + " Has been deleted with all its purchased coupons!");
		
	}

	/**
	 * This function checks for conditions while updating a customer.
	 * @author Sol Invictus
	 * @throws ApplicationException
	 */
	@Override
	public void updateCustomer(long customerID, String password) throws ApplicationException {
		// before meddling down with the db, we must check if the user has entered valid inputs first to avoid waste of time accessing the db and such

		if(!ValidationUtils.isPasswordValid(password)) {

			throw new ApplicationException(ErrorType.INVALID_CREDENTIALS, null, "One or more fields are in an invalid format, try again");		
		}

		//Now we need to get the company from dao, check if it exists, and after set only email and and password

		Customer updatedCustomer = customerDao.getCustomerById(customerID);

		if(updatedCustomer == null){
			throw new ApplicationException(ErrorType.CUSTOMER_DOESNT_EXIST,null, "Cannot update this customer, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}

		//if we got to this spot it means all is good, let's update

		updatedCustomer.setPassword(password);
		customerDao.updateCustomer(updatedCustomer);

		System.out.println("Company with the ID: " + customerID + " has been updated with the new details: "
				+ updatedCustomer.toString());
		
	}

	
	/**
	 * This function checks for conditions while getting a customer by ID.
	 * @author Sol Invictus
	 */
	@Override
	public Customer getCustomerById(long customerId) throws ApplicationException {
		Customer customer = customerDao.getCustomerById(customerId);

		if(customer == null) {
			throw new ApplicationException(ErrorType.CUSTOMER_DOESNT_EXIST,null, "Cannot get this customer, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
		return customer;
	}
	

	public Customer getCustomerByName(String customerName) throws ApplicationException {
		Customer customer = customerDao.getCustomerByName(customerName);

		if(customer == null) {
			throw new ApplicationException(ErrorType.CUSTOMER_DOESNT_EXIST,null, "Cannot get this customer, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
		return customer;
	}

	
	/**
	 * This function gets all customers from the db.
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws ApplicationException {
		ArrayList<Customer> customers = (ArrayList<Customer>) customerDao.getAllCustomers();

		return customers;
	}

}
