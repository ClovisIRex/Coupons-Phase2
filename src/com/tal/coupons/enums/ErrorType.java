package com.tal.coupons.enums;
/**
 * This enum provides various values for different Errors that can occur throughout the program.
 * @author Sol Invictus
 */

public enum ErrorType {
	
	DAO_CREATE_ERROR(1),
	DAO_GET_ERROR(2),
	DAO_UPDATE_ERROR(3),
	DAO_DELETE_ERROR(4),
	
	CUSTOMER_NAME_ALREADY_EXISTS(5),
	COUPON_NAME_ALREADY_EXISTS(6),
	COMPANY_NAME_ALREADY_EXISTS(7),
	INVALID_CREDENTIALS(8),
	
	COUPON_DOESNT_EXIST(9),
	COUPON_OUT_OF_STOCK(10),
	COUPON_EXPIRED(11),
	COUPON_ALREADY_PURCHASED(12),
	
	COMPANY_DOESNT_EXIST(13),
	CUSTOMER_DOESNT_EXIST(14),
	
	LOGIN_PROFILE_INVALID(15),
	
	LOGIN_SECURITY_FAILURE(16),
	
	GENERAL_ERROR(17);
	

	
	private int internalErrorCode;
	
	ErrorType(int internalErrorCode){
		this.internalErrorCode = internalErrorCode;
	}
	
	public int getInternalErrorCode() {
		return internalErrorCode;
	}

}
