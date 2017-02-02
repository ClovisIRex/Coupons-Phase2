package com.tal.coupons.rest.api;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;
import com.tal.coupons.logic.CustomerLogic;

/**
* This class provides a RESTful API for the 'customer' resource.
* @author Sol Invictus
*
*/
@Path("/api/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerApi {
	
	@POST
	public void createCustomer(Customer customer) throws ApplicationException {
		
		CustomerLogic custLogic = new CustomerLogic();
		custLogic.createCustomer(customer);
	}
	
	@DELETE
	@Path("/{id}/")
	public void removeCustomer(@PathParam("id") long customerID) throws ApplicationException {
		
		CustomerLogic custLogic = new CustomerLogic();
		custLogic.removeCustomer(customerID);
	}
	
	@PUT
	public void updateCustomer(Customer customer) throws ApplicationException {
		CustomerLogic custLogic = new CustomerLogic();
		custLogic.updateCustomer(customer.getId(),customer.getPassword());
	}
	
	@GET
	@Path("/{id}/")
	public Customer getCustomer(@PathParam("id") long customerID) throws ApplicationException {
		
		CustomerLogic custLogic = new CustomerLogic();
		Customer customer = custLogic.getCustomerById(customerID);
		
		return customer;
	}
	
	@GET
	public Collection<Customer> getAllCustomers() throws ApplicationException {
		
		CustomerLogic custLogic = new CustomerLogic();
		ArrayList<Customer> customers = (ArrayList<Customer>) custLogic.getAllCustomers();
		
		return customers;
		
	}	

}
