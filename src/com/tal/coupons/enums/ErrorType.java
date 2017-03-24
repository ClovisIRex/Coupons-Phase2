package com.tal.coupons.enums;
/**
 * This enum provides various values for different Errors that can occur throughout the program.
 * @author Sol Invictus
 */

public enum ErrorType {
	
	DAO_CREATE_ERROR(10),
	DAO_GET_ERROR(20),
	DAO_UPDATE_ERROR(30),
	DAO_DELETE_ERROR(40),
	
	CUSTOMER_NAME_ALREADY_EXISTS(50),
	COUPON_NAME_ALREADY_EXISTS(60),
	COMPANY_NAME_ALREADY_EXISTS(70),
	INVALID_CREDENTIALS(80),
	
	COUPON_DOESNT_EXIST(90),
	COUPON_OUT_OF_STOCK(100),
	COUPON_EXPIRED(110),
	COUPON_ALREADY_PURCHASED(120),
	COUPON_TYPE_INVALID(121),
	
	COMPANY_DOESNT_EXIST(130),
	CUSTOMER_DOESNT_EXIST(140),
	
	LOGIN_PROFILE_INVALID(150),
	
	LOGIN_SECURITY_FAILURE(160),
	
	INVALID_COOKIE(170),
	
	GENERAL_ERROR(999);
	

	
	private int internalErrorCode;
	
	ErrorType(int internalErrorCode){
		this.internalErrorCode = internalErrorCode;
	}
	
	public int getInternalErrorCode() {
		return internalErrorCode;
	}

}
