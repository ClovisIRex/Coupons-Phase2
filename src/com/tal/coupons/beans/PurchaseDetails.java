package com.tal.coupons.beans;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class provides the basic bean structure of a 'PurchaseDetails' object,
 * which will be used in api layer.
 * @author Tal Livny
 */

@XmlRootElement
public class PurchaseDetails {
	private long customerId;
	private long couponId;
	
	public PurchaseDetails() {
	}
	
	public PurchaseDetails(long custId,long coupId) {
		this.setCustomerId(custId);
		this.setCouponId(coupId);
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	
	

}
