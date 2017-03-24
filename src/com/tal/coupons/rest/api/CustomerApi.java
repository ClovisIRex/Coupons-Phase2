package com.tal.coupons.rest.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;
import com.tal.coupons.logic.CustomerLogic;
import com.tal.coupons.utils.CookieUtil;

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
	public void createCustomer(@CookieParam("couponSession") Cookie cookie,Customer customer) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token.containsValue(UserProfile.ADMINISTRATOR)) {
			CustomerLogic custLogic = new CustomerLogic();
			custLogic.createCustomer(customer);
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@DELETE
	@Path("/{id}/")
	public void removeCustomer(@CookieParam("couponSession") Cookie cookie, @PathParam("id") long customerID) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);


		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			CustomerLogic custLogic = new CustomerLogic();
			custLogic.removeCustomer(customerID);
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@PUT
	public void updateCustomer(@CookieParam("couponSession") Cookie cookie, Customer customer) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);


		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.CUSTOMER) 
						&& token.containsKey(String.valueOf((customer.getId()))))) {
			CustomerLogic custLogic = new CustomerLogic();
			custLogic.updateCustomer(customer.getId(),customer.getPassword());
		} 

		else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@GET
	@Path("/{id}/")
	public Customer getCustomer(@CookieParam("couponSession") Cookie cookie, @PathParam("id") long customerID) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		CustomerLogic custLogic = new CustomerLogic();

		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.COMPANY) &&
						token.containsKey(String.valueOf((customerID))))) { 
			Customer customer = custLogic.getCustomerById(customerID);
			return customer;	
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}


	}

	@GET
	public Collection<Customer> getAllCustomers(@CookieParam("couponSession") Cookie cookie) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		CustomerLogic custLogic = new CustomerLogic();

		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			ArrayList<Customer> customers = (ArrayList<Customer>) custLogic.getAllCustomers();
			return customers;
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}

	}	

}
