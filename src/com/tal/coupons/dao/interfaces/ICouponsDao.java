package com.tal.coupons.dao.interfaces;
import java.util.Collection;
import com.tal.coupons.beans.Coupon;
import com.tal.coupons.enums.CouponType;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * This interface provides methods describing coupon-related actions performed on the DB via SQL(dao level).
 * @author Tal Livny
 */

public interface ICouponsDao {
	public void createCoupon(Coupon coupon) throws ApplicationException;
	public void removeCoupon(long couponID) throws ApplicationException;
	public void updateCoupon(Coupon coupon) throws ApplicationException;
	public void purchaseCoupon(long customerID, long couponID) throws ApplicationException;
	
	// Methods of retrieving coupons from the DB according to various parameters(by ID, by Type, All...)
	
	public Coupon getCouponById(long couponId) throws ApplicationException;
	
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException;
	public Collection<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException;
	public Collection<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException;
	public Collection<Coupon> getAllCoupons() throws ApplicationException;
	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException;
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws ApplicationException;
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double couponPrice) throws ApplicationException;
}
