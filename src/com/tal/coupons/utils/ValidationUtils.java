package com.tal.coupons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that contains static method of checking user's input etc.
 * @author Tal Livny
 *
 */
public class ValidationUtils {
	
	
	/**
	 * This function checks if an email string is valid according to a regex
	 * @author Tal Livny
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
	 * @author Tal Livny
	 */
	public static boolean isPasswordValid(String password) {
		String PASS_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,}";
		Pattern pattern = Pattern.compile(PASS_PATTERN);
		Matcher matcher = pattern.matcher(password);
		
		return matcher.matches();
	}

}
