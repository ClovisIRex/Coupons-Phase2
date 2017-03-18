package com.tal.coupons.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * Class that contains static method of checking user's input etc.
 * @author Sol Invictus
 *
 */
public class ValidationUtils {
	
	
	/**
	 * This function checks if an email string is valid according to a regex
	 * @author Sol Invictus
	 */
	public static boolean isEmailValid(String email) {
		
		String EMAIL_PATTERN =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
	
	
	/**
	 * This function checks if a password string contains upper and lower case, digits,special character, no whitespace and at least 8 chars long according to a regex
	 * @author Sol Invictus
	 */
	public static boolean isPasswordValid(String password) {
		String PASS_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,}";
		Pattern pattern = Pattern.compile(PASS_PATTERN);
		Matcher matcher = pattern.matcher(password);
		
		return matcher.matches();
	}
	
	/**
	 * hashes a string with sha256
	 * @param data
	 * @return hashedData
	 * @throws NoSuchAlgorithmException 
	 */
	public static String sha256(String data) throws ApplicationException {
		MessageDigest digest;
		byte[] hashedData = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			hashedData = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			
		} catch (NoSuchAlgorithmException e) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, null, e.getMessage());
		}
		
		
		return hashedData.toString();
	}

}
