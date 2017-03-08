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

import org.eclipse.jdt.internal.compiler.apt.dispatch.AptProblem;

import com.tal.coupons.beans.Company;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;

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
	public void createCompany(Company company) throws ApplicationException {
		
		CompanyLogic compLogic = new CompanyLogic();
		compLogic.createCompany(company);
	}
	
	
	
	@DELETE
	@Path("/{id}/")
	public void removeCompany(@PathParam("id") long companyID) throws ApplicationException {
		
		CompanyLogic compLogic = new CompanyLogic();
		compLogic.removeCompany(companyID);
	}
	
	
	@PUT
	public void updateCompany(Company company) throws ApplicationException {
		CompanyLogic compLogic = new CompanyLogic();
		compLogic.updateCompany(company.getId(),company.getPassword(),company.getEmail());
	}
	
	
	
	@GET
	@Path("/{id}/")
	public Company getCompany(@PathParam("id") long companyID) throws ApplicationException {
		
		CompanyLogic compLogic = new CompanyLogic();
		Company company = compLogic.getCompanyById(companyID);
		
		return company;
	}
	
	@GET
	public Collection<Company> getAllCompanies() throws ApplicationException {
		
		CompanyLogic compLogic = new CompanyLogic();
		ArrayList<Company> companies = (ArrayList<Company>) compLogic.getAllCompanies();
		
		return companies;
		
	}	
}