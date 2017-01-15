package com.tal.coupons.logic.interfaces;

import java.util.Collection;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.exceptions.ApplicationException;

public interface ICustomerLogic {
	
	public void createCustomer(Customer customer) throws ApplicationException;
	public void removeCustomer(long customerID) throws ApplicationException;
	public void updateCustomer(long customerID, String password) throws ApplicationException;
	
	public Customer getCustomerById(long customerId) throws ApplicationException;
	public Collection<Customer> getAllCustomers() throws ApplicationException;
	

}
