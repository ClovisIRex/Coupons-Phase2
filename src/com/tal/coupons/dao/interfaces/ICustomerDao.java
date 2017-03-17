package com.tal.coupons.dao.interfaces;

import java.util.Collection;

import com.tal.coupons.beans.Customer;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * This interface provides methods describing customer-related actions performed on the DB via SQL(dao level).
 * @author Sol Invictus
 */

public interface ICustomerDao {
	public void createCustomer(Customer customer) throws ApplicationException;
	public void removeCustomer(long customerID) throws ApplicationException;
	public void updateCustomer(Customer customer) throws ApplicationException;
	
	// Methods of retrieving Customers from the DB according to various parameters
	
	public Customer getCustomerById(long customerId) throws ApplicationException;
	public Collection<Customer> getAllCustomers() throws ApplicationException;
	
	// login with username who has CUSTOMER permissions
	
	public boolean login(String customerName, String password) throws ApplicationException;
}
