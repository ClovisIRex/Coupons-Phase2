package com.tal.coupons.logic;

import com.tal.coupons.dao.CompanyDao;
import com.tal.coupons.dao.CustomerDao;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.interfaces.IUsersLogic;

public class UsersLogic implements IUsersLogic {


	/**
	 * This function performs a login to to system by verifying the user's credentials according to its profile
	 * @author Tal Livny
	 * @throws ApplicationException 
	 */
	@Override
	public boolean login(String username, String password, UserProfile clientType) throws ApplicationException {
		
		boolean isloginSuccessful = false;
		
		if (clientType == UserProfile.COMPANY) {
			
			CompanyDao companyDao = new CompanyDao();
			isloginSuccessful = companyDao.login(username, password);
			
			if(!isloginSuccessful) {
				throw new ApplicationException(ErrorType.LOGIN_SECURITY_FAILURE, null, "Failed to login. One or more fields do not match,"
						+ " or the company does not exist");
			}
			
			System.out.println("Company user: " + username + " has logged in!");
			return isloginSuccessful;
		} 
		
		else if (clientType == UserProfile.CUSTOMER) {
			
			CustomerDao customerDao = new CustomerDao();
			isloginSuccessful = customerDao.login(username, password);
			
			if(!isloginSuccessful) {
				throw new ApplicationException(ErrorType.LOGIN_SECURITY_FAILURE, null, "Failed to login. One or more fields do not match,"
						+ " or the customer does not exist");
			}
			
			System.out.println("Customer user: " + username + " has logged in!");
			return isloginSuccessful;
		}
		
		else { 
			throw new ApplicationException(ErrorType.LOGIN_PROFILE_INVALID, null, "Invalid user profile- only customer or company allowed.");
		}
		
	}

}
