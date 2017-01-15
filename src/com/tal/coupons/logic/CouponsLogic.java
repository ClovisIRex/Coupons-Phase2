package com.tal.coupons.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.tal.coupons.beans.Coupon;
import com.tal.coupons.dao.CouponDao;
import com.tal.coupons.enums.CouponType;
import com.tal.coupons.enums.ErrorType;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.interfaces.ICouponLogic;

/**
 * This Logic class is responsible with Coupon-related actions.
 * @author Tal Livny
 *
 */
public class CouponsLogic implements ICouponLogic {
	
	
	
	private CouponDao couponDao;
	
	public CouponsLogic() {
		this.couponDao = new CouponDao();
	}
	
	/**
	 * This function checks for conditions while creating a coupon.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public void createCoupon(Coupon coupon) throws ApplicationException {
		if(couponDao.isCouponExistByTitle(coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorType.COUPON_NAME_ALREADY_EXISTS,null,"Cannot create coupon because the coupon's name already exists. Try a different name.");
		}

		couponDao.createCoupon(coupon);
		System.out.println("Coupon: " + coupon.toString() + " Has been created!");
		
		
	}
	
	/**
	 * This function checks for conditions while removing a coupon from coupons table and purchased coupons.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public void removeCoupon(long couponID) throws ApplicationException {
		
		if(couponDao.getCouponById(couponID) == null){
			throw new ApplicationException(ErrorType.COUPON_DOESNT_EXIST,null, "Cannot delete this coupon, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
	
		
		couponDao.removeCoupon(couponID);
		System.out.println("Coupon with the ID: " + couponID + " Has been deleted!");
		
		
	}

	/**
	 * This function checks for conditions while updating a coupon's details.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public void updateCoupon(long couponID, double couponPrice, long couponEndDate) throws ApplicationException {
			
		//We need to get the coupon from dao, check if it exists, and after set only its end date and price
		Coupon updatedCoupon = couponDao.getCouponById(couponID);
		
		if(updatedCoupon == null){
			throw new ApplicationException(ErrorType.COUPON_DOESNT_EXIST,null, "Cannot update this coupon, "
					+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
		}
		

		updatedCoupon.setCouponEndDate(couponEndDate);
		updatedCoupon.setCouponPrice(couponPrice);

		couponDao.updateCoupon(updatedCoupon);
		System.out.println("Coupon with the ID: " + couponID + " has been updated with the new details: "
				+ "Price: " + couponPrice + " End date: " + couponEndDate);
		
		
		
	}
	
	
	/**
	 * This function checks for conditions while a customer purchases a coupon.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public void purchaseCoupon(long customerID, long couponID) throws ApplicationException {
		
				//prepare a coupon object from the db for checks later
				Coupon coupon = couponDao.getCouponById(couponID);
				
				
				//get current datetime of purchase
				long currentTime = Calendar.getInstance().getTimeInMillis();
							
				//cannot purchase a nonexistent coupon
				if(coupon == null){
					throw new ApplicationException(ErrorType.COUPON_DOESNT_EXIST,null, "Cannot purchase this coupon, "
							+ "it appears that it was deleted by someone/something else and it doesn't exist anymore.");
				}
				
				//cannot purchase a coupon with 0 or less amount
				int couponStock = coupon.getCouponsInStock();
				
				if(couponStock <= 0) {
					throw new ApplicationException(ErrorType.COUPON_OUT_OF_STOCK,null, "Coupon is out of stock, cannot purchase.");
				}
				
				//Cannot purchase a coupon with expired date
				if(coupon.getCouponEndDate() <= currentTime) {
					throw new ApplicationException(ErrorType.COUPON_EXPIRED,null, "Coupon date has expired, cannot purchase.");
				}
				
				//Can purchase coupon only once, don't get greedy!
				if(couponDao.isCouponAlreadyPurchased(customerID,couponID)) {
					throw new ApplicationException(ErrorType.COUPON_ALREADY_PURCHASED,null, "Coupon has already been purchased by this customer,"
																							+ " cannot purchase again.");
				}
						
				//If everything is good, we buy the coupon by inserting the details into the join table and decrementing the stock in the coupons table
		
				couponDao.purchaseCoupon(customerID, couponID);
				couponStock --;
				coupon.setCouponsInStock(couponStock);
				couponDao.updateCoupon(coupon);

				System.out.println("The coupon: " + coupon.getCouponTitle() + " has been purchased successfully by the customer with the ID: " + customerID);

		
	}

	/**
	 * This function gets a list of all coupons by a type.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {
		
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getCouponsByType(couponType);
		
		return coupons;
	}
	
	
	/**
	 * This function gets a list of all coupons by a company's id.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException {

		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getCouponsByCompanyId(companyId);
		
		return coupons;
	}

	/**
	 * This function gets a list of all coupons by a customer's id.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException {

		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getCouponsByCustomerId(customerId);

		return coupons;
	}
	
	
	/**
	 * This function gets a list of all coupons.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws ApplicationException {

		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getAllCoupons();

		return coupons;
	}
	
	
	/**
	 * This function gets a list of all purchased coupons by type.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws ApplicationException {

		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getAllPurchasedCouponsByType(couponType);

		return coupons;
	}

	/**
	 * This function gets a list of all purchased coupons by price.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double couponPrice) throws ApplicationException {

		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getAllPurchasedCouponsByPrice(couponPrice);

		return coupons;
	}

	/**
	 * This function gets a list of all purchased coupons.
	 * @author Tal Livny
	 * @throws ApplicationException
	 */
	@Override
	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException {

		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponDao.getAllPurchasedCoupons();

		return coupons;
	}

}
