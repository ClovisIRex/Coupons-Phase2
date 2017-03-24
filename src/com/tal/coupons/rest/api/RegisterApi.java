package com.tal.coupons.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;
import com.tal.coupons.logic.CustomerLogic;

import sun.font.CreatedFontTracker;

/**
 * This class provides a RESTful API for registering users.
 * @author Sol Invictus
 *
 */

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterApi {


	@POST
	@Path("/customer")
	public void registerCustomer(Customer customer) throws ApplicationException {
		CustomerLogic custLogic = new CustomerLogic();
		custLogic.createCustomer(customer);
	}

	@POST
	@Path("/company")
	public void registerCompany(Company company) throws ApplicationException {
		CompanyLogic compLogic = new CompanyLogic();
		compLogic.createCompany(company);
	}
}





