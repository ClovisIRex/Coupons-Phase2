package com.tal.coupons.logic;

import java.util.ArrayList;
import java.util.Collection;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Coupon;
import com.tal.coupons.dao.CompanyDao;
import com.tal.coupons.dao.CouponDao;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.interfaces.ICompanyLogic;
import com.tal.coupons.logic.interfaces.ILoginLogic;
import com.tal.coupons.utils.ValidationUtils;




/**
 * This Logic class is responsible with Company-related actions.
 * @author Sol Invictus
 *
 */
public class CompanyLogic implements ICompanyLogic {
	
	private CompanyDao companyDao;
	private CouponDao couponDao;

	public CompanyLogic() {
		this.companyDao = new CompanyDao();	
		this.couponDao = new CouponDao();
	}
	
	
	/**
	 * Creates built-in Admin user
	 * @author Sol Invictus
	 */
	public void createAdmin() throws ApplicationException {
		
		Company adminCompany = new Company("Admin", "1234", null);
		if(companyDao.isCompanyExistByName("Admin")) {
			throw new ApplicationException(ErrorType.COMPANY_NAME_ALREADY_EXISTS,null,"Cannot create admin, admin already exists.");
		}

		
		companyDao.createCompany(adminCompany);
		System.out.println("Company: " + adminCompany.toString() + " Has been created!");
	}
	
	/**
	 * This function checks for conditions while creating a company.
	 * @author Sol Invictus
	 * 
	 */
	@Override
	public void createCompany(Company company) throws ApplicationException {
		
		// before meddling down with the db, we must check if the user has entered valid inputs first to avoid waste of time accessing the db and such
		
		if(!ValidationUtils.isEmailValid(company.getEmail()) || !ValidationUtils.isPasswordValid(company.getPassword())) {
			throw new ApplicationException(ErrorType.INVALID_CREDENTIALS, null, "One or more fields are in an invalid format, try again");		
		}
		
		
		if(companyDao.isCompanyExistByName(company.getCompName())) {
			throw new ApplicationException(ErrorType.COMPANY_NAME_ALREADY_EXISTS,null,"Cannot create company, company name already exists."
					+ " Try a different name.");
		}
		
		//If we got this point it means all is fine and it's ok to update
		companyDao.createCompany(company);
		System.out.println("Company: " + company.toString() + " Has been created!");
		
		
	}
	
	
	
	
	

	/**
	 * This function checks for conditions while deleting a company and all its related stock and purchaed coupons.
	 * @author Sol Invictus
	 * @throws ApplicationException
	 */
	@Override
	public void removeCompany(long companyID) throws ApplicationException {
		
		if(companyDao.getCompanyById(companyID) == null) {
			throw new ApplicationException(ErrorType.COMPANY_DOESNT_EXIST,null, "Cannot delete this company, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
	
		//we first remove purchased coupons, then we remove company and stock coupons. 
		//Why? because in order to remove the purchased coupons we need the company and coupons to exist first.
		
		couponDao.removePurchasedCouponsByCompanyID(companyID);
		companyDao.removeCompany(companyID);
		System.out.println("Company with the ID: " + companyID + " Has been deleted with all its stock and purchased coupons!");
	
		
	}
	
	
	/**
	 * This function checks for conditions while updating a company.
	 * @author Sol Invictus
	 * @throws ApplicationException
	 */
	@Override
	public void updateCompany(long companyID, String password, String email) throws ApplicationException {

		// before meddling down with the db, we must check if the user has entered valid inputs first to avoid waste of time accessing the db and such
		
		if(!ValidationUtils.isPasswordValid(password) || !ValidationUtils.isEmailValid(email)) {
			
			throw new ApplicationException(ErrorType.INVALID_CREDENTIALS, null, "One or more fields are in an invalid format, try again");		
		}
		
		//Now we need to get the company from dao, check if it exists, and after set only email and and password
		
		Company updatedCompany = companyDao.getCompanyById(companyID);

		if(updatedCompany == null){
			throw new ApplicationException(ErrorType.COMPANY_DOESNT_EXIST,null, "Cannot update this company, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
	
		//if we got to this spot it means all is good, let's update
		
		updatedCompany.setPassword(password);
		updatedCompany.setEmail(email);
		companyDao.updateCompany(updatedCompany);
		
		System.out.println("Company with the ID: " + companyID + " has been updated with the new details: "
				+ updatedCompany.toString());
	}

	/**
	 * This function checks for conditions while getting a company by ID.
	 * @author Sol Invictus
	 */
	@Override
	public Company getCompanyById(long companyID) throws ApplicationException {
		
		Company company = companyDao.getCompanyById(companyID);
		
		if(company == null) {
			throw new ApplicationException(ErrorType.COMPANY_DOESNT_EXIST,null, "Cannot get this company, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
		return company;
	}

	/**
	 * This function gets all companies from the db.
	 * @author Sol Invictus
	 */
	@Override
	public Collection<Company> getAllCompanies() throws ApplicationException {
		
		ArrayList<Company> companies = (ArrayList<Company>) companyDao.getAllCompanies();
		
		return companies;
	}

}
