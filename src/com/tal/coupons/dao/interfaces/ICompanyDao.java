package com.tal.coupons.dao.interfaces;

import java.util.Collection;

import com.tal.coupons.beans.Company;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * This interface provides methods describing company-related actions performed on the DB via SQL(dao level).
 * @author Tal Livny
 */
public interface ICompanyDao {
	public void createCompany(Company company) throws ApplicationException;
	public void removeCompany(long companyID) throws ApplicationException;
	public void updateCompany(Company company) throws ApplicationException;
	
// Methods of retrieving comapnies from the DB according to various parameters
	
	public Company getCompanyById(long companyId) throws ApplicationException;
	public Collection<Company> getAllCompanies() throws ApplicationException;
	
	// SQL login with username who has COMPANY permissions
	public boolean login(String companyName, String password) throws ApplicationException;
	

}
