package com.tal.coupons.rest.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import com.tal.coupons.beans.Company;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;
import com.tal.coupons.utils.CookieUtil;

/**
 * This class provides a RESTful API for the 'company' resource.
 * @author Sol Invictus
 *
 */
@Path("/api/company")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyApi {

	@POST
	public void createCompany(@CookieParam("couponSession") Cookie cookie,Company company) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			CompanyLogic compLogic = new CompanyLogic();
			compLogic.createCompany(company);
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}

	}



	@DELETE
	@Path("/{id}/")
	public void removeCompany(@CookieParam("couponSession") Cookie cookie, @PathParam("id") long companyID) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		
		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			CompanyLogic compLogic = new CompanyLogic();
			compLogic.removeCompany(companyID);
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}


	}


	@PUT
	public void updateCompany(@CookieParam("couponSession") Cookie cookie, Company company) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		
		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.COMPANY) &&
						token.containsKey(String.valueOf((company.getId()))))) {
			CompanyLogic compLogic = new CompanyLogic();
			compLogic.updateCompany(company.getId(),company.getPassword(),company.getEmail());

		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}

	}



	@GET
	@Path("/{id}/")
	public Company getCompany(@CookieParam("couponSession") Cookie cookie, @PathParam("id") long companyID) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		
		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.COMPANY) &&
						token.containsKey(String.valueOf((companyID))))) {	
			CompanyLogic compLogic = new CompanyLogic();
			Company company = compLogic.getCompanyById(companyID);
			
			return company;

		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}


	@GET
	public Collection<Company> getAllCompanies(@CookieParam("couponSession") Cookie cookie) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token.containsValue(UserProfile.ADMINISTRATOR)) {

			CompanyLogic compLogic = new CompanyLogic();
			ArrayList<Company> companies = (ArrayList<Company>) compLogic.getAllCompanies();

			return companies;

		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie");
		}
	}	
}
