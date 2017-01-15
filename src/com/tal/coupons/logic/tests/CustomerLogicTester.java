package com.tal.coupons.logic.tests;

import java.util.ArrayList;

import com.tal.coupons.beans.Customer;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CustomerLogic;

public class CustomerLogicTester {

private static CustomerLogic cLogic = new CustomerLogic();
	
	
	public static void createCustomerTest(Customer Customer) {
		try {
			cLogic.createCustomer(Customer);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateCustomerTest(long id,String password) {
		try {
			cLogic.updateCustomer(id, password);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void getallCustomersTest() {
		try {
			ArrayList<Customer> customers = (ArrayList<Customer>) cLogic.getAllCustomers();
			
			for(Customer Customer : customers) {
				System.out.println(Customer.toString());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void getCustomerByIdTest(long id) {
		try {
			
			Customer Customer = cLogic.getCustomerById(id);
			System.out.println(Customer.toString());
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeCustomerTest(long id) {
		try {
			cLogic.removeCustomer(id);
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
}
