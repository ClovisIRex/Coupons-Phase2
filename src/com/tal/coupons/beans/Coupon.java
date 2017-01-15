package com.tal.coupons.beans;

import com.tal.coupons.enums.CouponType;
import java.util.Calendar;

/**
 * This class provides the basic bean structure of a 'Coupon' object,
 * which will be used in higher layers of this program.
 * @author Tal Livny
 */
public class Coupon {
	private long couponId;
	private String couponTitle;
	private	long   couponStartDate;
	private	long   couponEndDate;
	private	int    couponsInStock;
	private	String   couponType;
	private	String couponMessage;
	private	double couponPrice;
	private	String couponImage;
	private long companyID;
	
	


	// We use 'long' format for timestamps instead of using the 'Timestamp' class to prevent future problems
	private long couponStartDateTimeStamp;
	private long couponEndDateTimeStamp;
	
	
	//Empty default constructor,  in order to reserve place for future constructors with diff. parameters, in accordance to requests
	public Coupon() {
	}
	
	//ctor w/o id, in case we don't need it(id is auto incremented by the db)
		public Coupon(String couponTitle, long couponStartDate,
					  long couponEndDate, int couponsInStock,
					  String couponType, String couponMessage,
					  double couponPrice, String couponImage, long companyID) {
			
			this.setCouponTitle(couponTitle);
			this.setCouponStartDate(couponStartDate);
			this.setCouponEndDate(couponEndDate);
			this.setCouponsInStock(couponsInStock);
			this.setCouponType(couponType);
			this.setCouponMessage(couponMessage);
			this.setCouponPrice(couponPrice);
			this.setCouponImage(couponImage);
			this.setCompanyID(companyID);
		}
		
	//ctor w/id, in case we need it
		public Coupon(long couponId,
					  String couponTitle, long couponStartDate,
					  long couponEndDate, int couponsInStock,
					  String couponType, String couponMessage,
					  double couponPrice, String couponImage, long companyID) {
			
			this.setCouponId(couponId);
			this.setCouponTitle(couponTitle);
			this.setCouponStartDate(couponStartDate);
			this.setCouponEndDate(couponEndDate);
			this.setCouponsInStock(couponsInStock);
			this.setCouponType(couponType);
			this.setCouponMessage(couponMessage);
			this.setCouponPrice(couponPrice);
			this.setCouponImage(couponImage);
			this.setCompanyID(companyID);
		}
		
		
	
	public long getCouponId() {
		return couponId;
	}



	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}



	public String getCouponTitle() {
		return couponTitle;
	}



	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}



	public long getCouponStartDate() {
		return couponStartDate;
	}



	public void setCouponStartDate(long couponStartDate) {
		this.couponStartDate = couponStartDate;
		Calendar calendar = Calendar.getInstance();
		this.setCouponStartDateTimeStamp(calendar.getTimeInMillis());
		
	}



	public long getCouponEndDate() {
		return couponEndDate;
	}



	public void setCouponEndDate(long couponEndDate) {
		this.couponEndDate = couponEndDate;
		Calendar calendar = Calendar.getInstance();
		this.setCouponEndDateTimeStamp(calendar.getTimeInMillis());
	}



	public int getCouponsInStock() {
		return couponsInStock;
	}



	public void setCouponsInStock(int couponsInStock) {
		this.couponsInStock = couponsInStock;
	}



	public String getCouponType() {
		return couponType;
	}



	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}



	public String getCouponMessage() {
		return couponMessage;
	}



	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}



	public double getCouponPrice() {
		return couponPrice;
	}


	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}


	public String getCouponImage() {
		return couponImage;
	}
	

	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}
	

	public long getCouponStartDateTimeStamp() {
		return couponStartDateTimeStamp;
	}
	

	public void setCouponStartDateTimeStamp(long couponStartDateTimeStamp) {
		this.couponStartDateTimeStamp = couponStartDateTimeStamp;
	}
	

	public long getCouponEndDateTimeStamp() {
		return couponEndDateTimeStamp;
	}
	

	public void setCouponEndDateTimeStamp(long couponEndDateTimeStamp) {
		this.couponEndDateTimeStamp = couponEndDateTimeStamp;
	}
	
	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}
	
	
	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", couponTitle=" + couponTitle + ", couponStartDate=" + couponStartDate
				+ ", couponEndDate=" + couponEndDate + ", couponsInStock=" + couponsInStock + ", couponType="
				+ couponType + ", couponMessage=" + couponMessage + ", couponPrice=" + couponPrice + ", couponImage="
				+ couponImage + ",companyID=" + companyID + "]";
	}

}
