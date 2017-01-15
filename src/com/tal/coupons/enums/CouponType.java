package com.tal.coupons.enums;

/**
 * This enum provides the possible coupon types by category.
 * @author Tal Livny
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

}
