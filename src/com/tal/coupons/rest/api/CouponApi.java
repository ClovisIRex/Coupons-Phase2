package com.tal.coupons.rest.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import com.tal.coupons.beans.Company;
import com.tal.coupons.beans.Coupon;
import com.tal.coupons.beans.Customer;
import com.tal.coupons.beans.PurchaseDetails;
import com.tal.coupons.enums.CouponType;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;
import com.tal.coupons.logic.CouponLogic;
import com.tal.coupons.logic.CustomerLogic;
import com.tal.coupons.utils.CookieUtil;

/**
 * This class provides a RESTful API for the 'coupon' resource.
 * @author Sol Invictus
 *
 */
@Path("/api/coupon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CouponApi {
	
	@POST
	public void createCoupon(@CookieParam("couponSession") Cookie cookie,Coupon coupon) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.verifySessionCookie(cookie);
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.createCoupon(coupon);
	}
	
	@DELETE
	@Path("/{id}/")
	public void removeCoupon(@PathParam("id") long couponID) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.removeCoupon(couponID);
	}
	
	@PUT
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.updateCoupon(coupon.getCouponId(),coupon.getCouponPrice(),
								 coupon.getCouponEndDate());
	}
	
	
	@POST
	@Path("/purchase/")
	public void purchaseCoupon(PurchaseDetails details) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.purchaseCoupon(details.getCustomerId(), details.getCouponId());
	}
	
	@GET
	@Path("/{type}/")
	public Collection<Coupon> getCouponsByType(@PathParam("type") String couponType) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getCouponsByType(CouponType.valueOf(couponType.toUpperCase()));
		
		return coupons;
	}
	
	
	@GET
	@Path("/CompanyId/{id}/")
	public Collection<Coupon> getCouponsByCompanyId(@PathParam("id") long companyId) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getCouponsByCompanyId(companyId);
		
		return coupons;
	}
	
	@GET
	@Path("/CustomerId/{id}/")
	public Collection<Coupon> getCouponsByCustomerId(@PathParam("id") long customerId) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getCouponsByCustomerId(customerId);
		
		return coupons;
	}
	
	@GET
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getAllCoupons();
		
		return coupons;
	}
	
	@GET
	@Path("/purchase/type/{type}/")
	public Collection<Coupon> getAllPurchasedCouponsbyType(
			@PathParam("type")
			String couponType) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getAllPurchasedCouponsByType(CouponType.valueOf(couponType.toUpperCase()));
		
		return coupons;
	}
	
	@GET
	@Path("/purchase/price/{price}/")
	public Collection<Coupon> getAllPurchasedCouponsbyPrice(
			@PathParam("price")
			double couponPrice) throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getAllPurchasedCouponsByPrice(couponPrice);
		
		return coupons;
	}
	
	@GET
	@Path("/purchase/")
	public Collection<Coupon> getAllPurchasedCoupons() throws ApplicationException {
		
		CouponLogic couponLogic = new CouponLogic();
		ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
				couponLogic.getAllPurchasedCoupons();
		
		return coupons;
	}
}
