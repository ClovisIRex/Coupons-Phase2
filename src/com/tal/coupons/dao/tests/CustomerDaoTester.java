package com.tal.coupons.dao.tests;

import java.util.ArrayList;
import java.util.Arrays;

import com.tal.coupons.beans.Customer;
import com.tal.coupons.dao.CustomerDao;
import com.tal.coupons.exceptions.ApplicationException;

public class CustomerDaoTester {
	
private static Customer customer = new Customer(0, "John", "1234");
	
	private static CustomerDao cdao = new CustomerDao();
	
	public static void createCustomerTest() {
		try {
			cdao.createCustomer(customer);
			System.out.println(customer.toString());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public static void removeCustomerTest(long id) {
		try {
			cdao.removeCustomer(id);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void getCustomerTest() throws ApplicationException {
		try {
			Customer ret = cdao.getCustomerById(customer.getId());
			System.out.println(ret.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllCustomersTest() throws ApplicationException {
		try {
			ArrayList<Customer> allCustomers = (ArrayList<Customer>) cdao.getAllCustomers();
			System.out.println(Arrays.toString(allCustomers.toArray()));
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}	
	}
	
	public static void updateCustomerTest() throws ApplicationException {
		Customer uComp = new Customer(1, "George Soros Fund", "Illuminati1234");
		try {
			cdao.updateCustomer(uComp);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void getCustomerByIDTest() throws ApplicationException {
		try {
			Customer compbyid = cdao.getCustomerById(10);
			
			System.out.println(compbyid.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void loginCustomerTest() throws ApplicationException {
		try {
			boolean flag = cdao.login("George Soros Fund", "Illuminati1234");
			if(flag) {
				System.out.println("user and password match");
			}
			else {
				System.out.println("invalid user or password");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}


}
