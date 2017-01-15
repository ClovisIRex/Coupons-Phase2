package com.tal.coupons.enums;

/**
 * This enum provides 3 levels of the system's user DB permissions to be used later on, identified by different id's:
 * 
 * 	ADMINISTARTOR(ID 1) - can modify the lists of companies and customers
 * 	COMPANY(ID 2) - Can modify lists of coupons of his own company
 * 	CUSTOMER(ID 3) - Can only purchase coupons
 *  @author Tal Livny
 */
public enum UserProfile {
	ADMINISTRATOR(1),
	COMPANY(2),
	CUSTOMER(3);
	
	private int userType;
	
	UserProfile(int userType){
		this.userType = userType;
	}

	public int getUserType() {
		return userType;
	}

	
}
