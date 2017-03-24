package com.tal.coupons.enums;

import com.tal.coupons.exceptions.ApplicationException;

/**
 * This enum provides the possible coupon types by category.
 * @author Sol Invictus
 */

public enum CouponType {
	HOLIDAY("Holiday"),
	RESTAURANT("Restaurant"), 
	ENTERTAINMENT("Entertainment"),
	TRAVELLING("Travelling"),
	ELECTRICITY("Electricity"),
	FOOD("Food"),
	HEALTH("Health"),
	SPORTS("Sports"),
	CAMPING("Camping");
	
	private String couponType;
		
	CouponType(String couponType){
		this.couponType = couponType;
	}

	public String getcouponType() {
		return this.couponType;
	}
	
	public static boolean isCouponTypeExist(String couponType) throws ApplicationException {
		for(CouponType type : CouponType.values()) {
			if(type.getcouponType().toUpperCase().equals(couponType.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

}
