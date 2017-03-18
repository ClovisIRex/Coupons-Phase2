package com.tal.coupons.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.ws.rs.core.NewCookie;

import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;
import com.tal.coupons.logic.CustomerLogic;
import com.tal.coupons.rest.beans.LoginDetails;

/**
 * This static util class provides various functions responsible for cookie management
 * @author Sol Invictus
 *
 */
public class CookieUtil {
	
	/**
	 * gets users params, hashes them and returns the cookie in format hashed_id:hashed_typeid
	 * @param userType
	 * @param userID
	 * @return NewCookie
	 * @throws ApplicationException
	 */
	public static NewCookie createSessionCookie(long userID, int userTypeId) throws ApplicationException {

		String idCookieValue = String.valueOf(userID);
		String typeCookieValue = String.valueOf(userTypeId);
		NewCookie sessionCookie = null;
		
		String encodedUserId = Base64.getEncoder().encode(idCookieValue.getBytes()).toString();

		sessionCookie = new NewCookie(encodedUserId,
				ValidationUtils.sha256(typeCookieValue));
		
		return sessionCookie;
	}
	
	/**
	 * gets a session cookie, parses it, queries the DB and returns if the user exists. 
	 * @param sessioncookie
	 * @return
	 * @throws ApplicationException
	 */
	public static boolean verifySessionCookie(NewCookie sessioncookie) throws ApplicationException { 
		
		long userID = Long.parseLong(Base64.getDecoder().decode(sessioncookie.getName().getBytes()).toString());
		String hashedUserTypeIDfromCookie = sessioncookie.getValue();
		
		
		
		UserProfile [] userProfiles = UserProfile.values();
		ArrayList<String> hashedUserProfileIds = new ArrayList<String>();
		
		for(UserProfile profile : userProfiles) {
			String hashedProfileID = ValidationUtils.sha256(String.valueOf(profile.getUserType()));
			hashedUserProfileIds.add(hashedProfileID);
		}
		
		//means the user has a company UsertypeID
		if(hashedUserTypeIDfromCookie == hashedUserProfileIds.get(1)) {
			
			CompanyLogic companyLogic = new CompanyLogic();
			if(companyLogic.getCompanyById(userID) != null){
				return true;
			};
		}
		
		//means the user has a customer UsertypeID
		if(hashedUserTypeIDfromCookie == hashedUserProfileIds.get(2)) {

			CustomerLogic customerLogic = new CustomerLogic();
			if(customerLogic.getCustomerById(userID) != null){
				return true;
			};
		}
		
		return false;
	}
}
