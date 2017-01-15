package com.tal.coupons.logic.interfaces;

import java.util.Collection;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Coupon;
import com.tal.coupons.exceptions.ApplicationException;


/**
 * /**
 * This interface is to be used by the CompanyLogic class, contains method related to companies.
 * @author Tal Livny
 */
public interface ICompanyLogic {
	
	public void createCompany(Company company) throws ApplicationException;
	public void removeCompany(long companyID) throws ApplicationException;
	public void updateCompany(long companyID,String password, String email) throws ApplicationException;
	
	
	public Company getCompanyById(long companyID) throws ApplicationException;
	public Collection<Company> getAllCompanies() throws ApplicationException;
	
	
}
