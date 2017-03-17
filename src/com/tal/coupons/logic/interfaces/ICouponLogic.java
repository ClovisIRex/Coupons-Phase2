package com.tal.coupons.logic.interfaces;

import java.util.Collection;

import com.tal.coupons.beans.Coupon;
import com.tal.coupons.enums.CouponType;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * /**
 * This interface is to be used by the CompanyLogic class, contains method related to coupons.
 * @author Sol Invictus
 */
public interface ICouponLogic {
	
	public void createCoupon(Coupon coupon) throws ApplicationException;
	public void removeCoupon(long couponID) throws ApplicationException;
	public void updateCoupon(long couponID, double couponPrice, long couponEndDate) throws ApplicationException;
	public void purchaseCoupon(long customerID, long couponID) throws ApplicationException;
	
	
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException;
	public Collection<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException;
	public Collection<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException;
	public Collection<Coupon> getAllCoupons() throws ApplicationException;
	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException;
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws ApplicationException;
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double couponPrice) throws ApplicationException;
	

}
