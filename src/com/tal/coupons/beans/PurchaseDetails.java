package com.tal.coupons.beans;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class provides the basic bean structure of a 'PurchaseDetails' object,
 * which will be used in api layer.
 * @author Sol Invictus
 */

@XmlRootElement
public class PurchaseDetails {
	private String customerName;
	private long couponId;
	
	public PurchaseDetails() {
	}
	
	public PurchaseDetails(String customerName,long couponId) {
		this.setCustomerName(customerName);
		this.setCouponId(couponId);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
}
