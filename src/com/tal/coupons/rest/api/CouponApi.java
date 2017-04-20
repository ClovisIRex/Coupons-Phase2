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
import com.tal.coupons.enums.ErrorType;
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
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		CompanyLogic companyLogic = new CompanyLogic();
		long companyId = companyLogic.getIdByCompanyName(coupon.getCompanyName());
		
		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.COMPANY) &&
						token.containsKey(String.valueOf((companyId))))) { 
			CouponLogic couponLogic = new CouponLogic();
			coupon.setCompanyID(companyId);
			couponLogic.createCoupon(coupon);		
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@DELETE
	@Path("/{name}/{id}/")
	public void removeCoupon(@CookieParam("couponSession") Cookie cookie,@PathParam("name") String compName ,@PathParam("id") long couponID) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		
		long companyId = -1;
		
		if(!compName.equals("admin")) { 
			CompanyLogic companyLogic = new CompanyLogic();
			companyId = companyLogic.getIdByCompanyName(compName);
		}
		
		

		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.COMPANY) &&
						token.containsKey(String.valueOf((companyId))))) {
			CouponLogic couponLogic = new CouponLogic();
			couponLogic.removeCoupon(couponID);
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@PUT
	public void updateCoupon(@CookieParam("couponSession") Cookie cookie,Coupon coupon) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		long companyId = -1;
		String companyName = coupon.getCompanyName();
		
		if(!companyName.equals("admin")) { 
			CompanyLogic companyLogic = new CompanyLogic();
			companyId = companyLogic.getIdByCompanyName(companyName);
		}
		
		if(token.containsValue(UserProfile.ADMINISTRATOR) ||
				(token.containsValue(UserProfile.COMPANY) &&
						token.containsKey(String.valueOf((companyId))))) {
			CouponLogic couponLogic = new CouponLogic();
			couponLogic.updateCoupon(coupon.getCouponId(),coupon.getCouponPrice(),
					coupon.getCouponEndDate());
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}


	@POST
	@Path("/purchase/")
	public void purchaseCoupon(@CookieParam("couponSession") Cookie cookie,PurchaseDetails details) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token != null) {
			for(UserProfile profile : UserProfile.values() ) {
				if(token.containsValue(profile)) {
					CouponLogic couponLogic = new CouponLogic();
					CustomerLogic customerLogic = new CustomerLogic();
					Customer customer = customerLogic.getCustomerByName(details.getCustomerName());
					couponLogic.purchaseCoupon(customer.getId(), details.getCouponId());
				}
			}
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}


	}

	@GET
	@Path("/{type}/")
	public Collection<Coupon> getCouponsByType(@CookieParam("couponSession") Cookie cookie, @PathParam("type") String couponType) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token != null) { 
			for(UserProfile profile : UserProfile.values() ) {
				if(token.containsValue(profile)) {
					CouponLogic couponLogic = new CouponLogic();
					ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
							couponLogic.getCouponsByType(CouponType.valueOf(couponType.toUpperCase())); 
					return coupons;
				}
			}

		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
		return null;

	}


	@GET
	@Path("/CompanyId/{id}/")
	public Collection<Coupon> getCouponsByCompanyId(@CookieParam("couponSession") Cookie cookie, @PathParam("id") long companyId) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token != null) { 
			for(UserProfile profile : UserProfile.values() ) { 
				if(token.containsValue(profile)) { 
					CouponLogic couponLogic = new CouponLogic();
					ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
							couponLogic.getCouponsByCompanyId(companyId);

					return coupons;
				}
			}
		}  else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
		return null;		
	}
	
	@GET
	@Path("/CompanyName/{name}/")
	public Collection<Coupon> getCouponsByCompanyName(@CookieParam("couponSession") Cookie cookie, @PathParam("name") String name) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);

		if(token != null) { 
			for(UserProfile profile : UserProfile.values() ) { 
				if(token.containsValue(profile)) { 
					CouponLogic couponLogic = new CouponLogic();
					CompanyLogic companyLogic = new CompanyLogic();
					long companyId = companyLogic.getIdByCompanyName(name);
					
					ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
							couponLogic.getCouponsByCompanyId(companyId);

					return coupons;
				}
			}
		}  else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
		return null;		
	}

	@GET
	@Path("/CustomerId/{id}/")
	public Collection<Coupon> getCouponsByCustomerId(@CookieParam("couponSession") Cookie cookie, @PathParam("id") long customerId) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		
		if(token != null) { 
			for(UserProfile profile : UserProfile.values() ) { 
				if(token.containsValue(profile)) { 
					CouponLogic couponLogic = new CouponLogic();
					ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
							couponLogic.getCouponsByCustomerId(customerId);
					return coupons;
				}
			}
		}  else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
		return null;
	}

	@GET
	public Collection<Coupon> getAllCoupons(@CookieParam("couponSession") Cookie cookie) throws ApplicationException {
		try{
			CouponLogic couponLogic = new CouponLogic();
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
					couponLogic.getAllCoupons();
			return coupons;
		} catch(Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	
	}

	@GET
	@Path("/purchase/type/{type}/")
	public Collection<Coupon> getAllPurchasedCouponsbyType(@CookieParam("couponSession") Cookie cookie,
			@PathParam("type")
			String couponType) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		
		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			CouponLogic couponLogic = new CouponLogic();
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
					couponLogic.getAllPurchasedCouponsByType(CouponType.valueOf(couponType.toUpperCase()));
			return coupons;

		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@GET
	@Path("/purchase/price/{price}/")
	public Collection<Coupon> getAllPurchasedCouponsbyPrice(
			@CookieParam("couponSession") Cookie cookie, @PathParam("price") 
			double couponPrice) throws ApplicationException {

		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			CouponLogic couponLogic = new CouponLogic();
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
					couponLogic.getAllPurchasedCouponsByPrice(couponPrice);

			return coupons;
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}
	
	@GET
	@Path("/purchase/customername/{customername}/")
	public Collection<Coupon> getAllPurchasedCouponsbyCustomerName(
			@CookieParam("couponSession") Cookie cookie, @PathParam("customername") 
			String customerName) throws ApplicationException {

		try {
			CustomerLogic customerLogic = new CustomerLogic();
			CouponLogic couponLogic = new CouponLogic();
			Customer customer = customerLogic.getCustomerByName(customerName);
			
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
			couponLogic.getAllPurchasedCouponsByCustomerId(customer.getId());
			return coupons;
			
			
		} catch(Exception e) {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}

	@GET
	@Path("/purchase/")
	public Collection<Coupon> getAllPurchasedCoupons(@CookieParam("couponSession") Cookie cookie) throws ApplicationException {
		Map<String,UserProfile> token = CookieUtil.createSessionToken(cookie);
		if(token.containsValue(UserProfile.ADMINISTRATOR)) { 
			CouponLogic couponLogic = new CouponLogic();
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) 
					couponLogic.getAllPurchasedCoupons();

			return coupons;
		} else {
			throw new ApplicationException(ErrorType.INVALID_COOKIE, null, "invalid cookie or unauthorized use with cookie");
		}
	}
}
