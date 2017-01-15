package com.tal.coupons.dao.tests;

import com.tal.coupons.enums.CouponType;

import java.util.*;
import com.tal.coupons.beans.Coupon;
import com.tal.coupons.dao.CouponDao;
import com.tal.coupons.exceptions.ApplicationException;

public class CouponsDaoTester {
	//private static Company company = new Company(0, "Clinton Foundation", "666", "Clinton@moloch.com");
	
	private static CouponType couponType = CouponType.ENTERTAINMENT;
	private static Coupon coupon = new Coupon("TEST4", 0, 0, 100, couponType.getcouponType(), "Testing", 10, "C:\\test.jpg",11);
	private static CouponDao cdao = new CouponDao();
	private static long customerId = 1;

	public static void createCouponTest() {
		try {
			cdao.createCoupon(coupon);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeCouponTest(long id) {
		try {
			cdao.removeCoupon(id);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void updateCouponTest() throws ApplicationException {
		CouponType changetype = couponType.ELECTRICITY;
		Coupon cpon = new Coupon(1, "TESTCHANGED", 0, 0, 200, changetype.getcouponType(), "Changed", 20, "C:\\changed.jpg",2);
		try {
			cdao.updateCoupon(cpon);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void getCouponTest() throws ApplicationException {
		try {
			//System.out.println(coupon.toString());
			Coupon ret = cdao.getCouponById(1);
			System.out.println(ret.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getCouponsByTypeTest() throws ApplicationException {
		ArrayList<Coupon> cList = (ArrayList<Coupon>) cdao.getCouponsByType(CouponType.ELECTRICITY);
		for(Coupon c : cList) {
			System.out.println(c.toString());
		}
	}
	
	public static void getCouponsByCustomerIdTest() throws ApplicationException {
		ArrayList<Coupon> cList = (ArrayList<Coupon>) cdao.getCouponsByCustomerId(customerId);
		for(Coupon c : cList) {
			System.out.println(c.toString());
		}
	}
	
	public static void getAllCouponsTest() throws ApplicationException {
		ArrayList<Coupon> cList = (ArrayList<Coupon>) cdao.getAllCoupons();
		for(Coupon c : cList) {
			System.out.println(c.toString());
		}
	}

}
