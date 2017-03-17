package com.tal.coupons.rest.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.servlet.http.Cookie;
import javax.ws.rs.core.MediaType;

import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.LoginLogic;
import com.tal.coupons.rest.beans.LoginDetails;

/**
* This class provides a RESTful API for UsersLogic.
* @author Sol Invictus
*
*/
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
public class LoginApi {

	@POST
	public boolean login(@Context HttpServletRequest request, @Context HttpServletResponse response,LoginDetails details) throws ApplicationException {
		
		String username = details.getUsername();
		String password = details.getPassword();
		UserProfile userType = UserProfile.getProfilebyValue(details.getClientType());
		
		LoginLogic loginLogic = new LoginLogic();
		boolean isLoginSuccessfull = loginLogic.login(username,password, userType);

		if(isLoginSuccessfull) {
			HttpSession session = request.getSession();
			
		}
		return isLoginSuccessfull;

	}

}
