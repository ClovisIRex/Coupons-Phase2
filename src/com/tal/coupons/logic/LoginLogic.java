package com.tal.coupons.logic;

import com.tal.coupons.dao.CompanyDao;
import com.tal.coupons.dao.CustomerDao;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.interfaces.ILoginLogic;

public class LoginLogic implements ILoginLogic {


	/**
	 * This function performs a login to to system by verifying the user's credentials according to its profile
	 * and returns it's login id
	 * @author Sol Invictus
	 * @throws ApplicationException 
	 */
	@Override
	public long login(String username, String password, UserProfile clientType) throws ApplicationException {
		
		long userID;
		boolean isloginSuccessful = false;
		
		if (clientType == UserProfile.COMPANY) {
			
			CompanyDao companyDao = new CompanyDao();
			isloginSuccessful = companyDao.login(username, password);
			
			
			if(!isloginSuccessful) {
				throw new ApplicationException(ErrorType.LOGIN_SECURITY_FAILURE, null, "Failed to login. One or more fields do not match,"
						+ " or the company does not exist");
			}
			
			userID = companyDao.getIdByCompanyName(username);
			
			System.out.println("Company user: " + username + " has logged in!");
			
			return userID;
		} 
		
		else if (clientType == UserProfile.CUSTOMER) {
			
			CustomerDao customerDao = new CustomerDao();
			isloginSuccessful = customerDao.login(username, password);
			
			if(!isloginSuccessful) {
				throw new ApplicationException(ErrorType.LOGIN_SECURITY_FAILURE, null, "Failed to login. One or more fields do not match,"
						+ " or the customer does not exist");
			}
			
			userID = customerDao.getIdByCustomerName(username);
			
			System.out.println("Customer user: " + username + " has logged in!");
			return userID;
		}
		
		else { 
			throw new ApplicationException(ErrorType.LOGIN_PROFILE_INVALID, null, "Invalid user profile- only customer or company allowed.");
		}
		
	}

}
