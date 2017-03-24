package com.tal.coupons.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

import com.tal.coupons.dao.CompanyDao;
import com.tal.coupons.dao.CustomerDao;
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
	 * gets users params, hashes and encodes them and returns the cookie with the in format;
	 * Name:"couponSession" value(token):hashed_id-hashed_typeid
	 * @param userType
	 * @param userID
	 * @return NewCookie
	 * @throws ApplicationException
	 */
	public static NewCookie createSessionCookie(long userID, int userTypeId) throws ApplicationException {

		String idCookieValue = String.valueOf(userID);
		String typeCookieValue = String.valueOf(userTypeId);
		NewCookie sessionCookie = null;
		
		
		String hashedCookieUserType = ValidationUtils.sha256(typeCookieValue);
		
		String cookieValue = idCookieValue + "-" + hashedCookieUserType;
		String base64CookieValue = new String(Base64.getEncoder().encode(cookieValue.getBytes()));
		
		sessionCookie = new NewCookie("couponSession",base64CookieValue);
		System.out.println("cookie: " + sessionCookie.toString());
		System.out.println("name: " + sessionCookie.getName());
		System.out.println("value: " + sessionCookie.getValue());
		return sessionCookie;
	}
	
	/**
	 * get the sessionCookie of the coupons system from a array of cookies.
	 * @param cookies
	 * @return sessionCookie
	 */
	public static javax.servlet.http.Cookie getSessionCookie(javax.servlet.http.Cookie[] cookies) {
		Map<String, javax.servlet.http.Cookie> cookieMap = new HashMap<>();
		for (javax.servlet.http.Cookie cookie : cookies) {
		    cookieMap.put(cookie.getName(), cookie);
		}
		javax.servlet.http.Cookie sessionCookie = cookieMap.get("couponSession");
		
		return sessionCookie;		
	}
	/**
	 * removes the sessionCookie of the coupons system from a array of cookies.
	 * @param cookies
	 */
	public static void removeSessionCookie(Cookie[] cookies) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		for (Cookie cookie : cookies) {
		    cookieMap.put(cookie.getName(), cookie);
		}
		Cookie sessionCookie = cookieMap.get("couponsSessionCookie");
			
	}
	
	/**
	 * gets a session cookie's text, parses it, queries the DB and returns token containing the
	 *  user's id and profile type if the user exists. 
	 * @param sessionCookie
	 * @return
	 * @throws ApplicationException
	 */
	public static Map<String,UserProfile> createSessionToken(Cookie sessionCookie) throws ApplicationException {
		
		// cookie parsing by "-" delimiter
		String codedCookieValue = sessionCookie.getValue();
		String decodedCookieValue = new String(Base64.getDecoder().decode(codedCookieValue.getBytes()));
		
		String[] cookieSplitArray = decodedCookieValue.split("-");
		long userID = Long.parseLong(cookieSplitArray[0]);
		String hashedUserTypeIDfromCookie = cookieSplitArray[1];
		
		/*
		 *  hashing all usertype id's and preparing data structs to hold them and
		 *  to map them to their hashed values
		 */
		UserProfile [] userProfiles = UserProfile.values();
		Map<String, UserProfile> profileHashMap = new HashMap<String, UserProfile>();
		
		/*
		 * we iterate through all the profiles, hash heir values and map them
		 */
		for(int i=0; i <= userProfiles.length; i++) {
			String profileID = String.valueOf(userProfiles[i].getUserType());
			String hashedProfileID = ValidationUtils.sha256(profileID);
			
			profileHashMap.put(hashedProfileID,userProfiles[i]);

		/*
		 * now we proceed to query the db for the validity of the user's id ONLY if the cookie seems
		 *  valid-profile wise in order to save redundant db
		 * queries which are precious in resources and memory.
		   the puropse is that we don't want a company/customer to be able to modify or access
		   a different company/customer db data when using Cookieutil's service in other api classes.
		 */
			if(hashedProfileID.equals(hashedUserTypeIDfromCookie)) {
				
				/*
				 * we get the current profile from the map we had prepared earlier and prepare a token to be
				 *  returned to the client only if he exists in the db.
				 */
				UserProfile currentProfile = profileHashMap.get(hashedProfileID);
				Map<String,UserProfile> token = new HashMap<String,UserProfile>();
				token.put(String.valueOf(userID),UserProfile.getProfilebyValue(i+1));

				
				// if it's admin we don't need to query the db because he's not in the tables(BAD DB DESIGN)
				switch(currentProfile) {
					case ADMINISTRATOR: {
						return token;
				}
					case COMPANY: {
						CompanyDao companyDao = new CompanyDao();
						if(companyDao.isCompanyExistByID(userID)) {
							return token;
						};
					}
					
					case CUSTOMER: {
						CustomerDao customerDao = new CustomerDao();
						if(customerDao.isCustomerExistByID(userID)) {
							return token;
						};
					}			
				}
			}
		}
		
		/*
		 * if anything fails along the way, we will not give a
		 *  token back to the client so he will be forced to login legally w/o tricks.
		 */
		return null;
	}
}
