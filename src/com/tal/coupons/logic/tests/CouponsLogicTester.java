package com.tal.coupons.logic.tests;

import java.util.ArrayList;

import com.tal.coupons.beans.Coupon;
import com.tal.coupons.enums.CouponType;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CouponLogic;

public class CouponsLogicTester {
	
	private static CouponLogic cLogic = new CouponLogic();
	
	public static void createCouponTest(Coupon coupon) {
		try {
			cLogic.createCoupon(coupon);
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeCouponTest(long couponID) {
		try {
			cLogic.removeCoupon(couponID);
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCouponTest(long couponID, double couponPrice, long couponEndDate) {
		try {
			cLogic.updateCoupon(couponID, couponPrice, couponEndDate);
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void purchaseCouponTest(long customerID, long couponID) {
		try {
			cLogic.purchaseCoupon(customerID, couponID);
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void getCouponsByTypeTest(CouponType couponType) {
		try {
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) cLogic.
					getCouponsByType(couponType);
			
			for(Coupon coupon : coupons) {
				System.out.println(coupon.toString());
			}
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void getCouponsByCompanyIdTest(long companyId) {
		try {
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) cLogic.getCouponsByCompanyId(companyId);
			
			
			for(Coupon coupon : coupons) {
				System.out.println(coupon.toString());
			}
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllCouponsTest() {
		try {
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) cLogic.getAllCoupons();
			
			
			for(Coupon coupon : coupons) {
				System.out.println(coupon.toString());
			}
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void getAllPurchasedCouponsByTypeTest(CouponType couponType) {
		try {
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) cLogic.
					getAllPurchasedCouponsByType(couponType);
			
			for(Coupon coupon : coupons) {
				System.out.println(coupon.toString());
			}
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
			
	}
	
	
}
