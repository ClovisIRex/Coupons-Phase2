package com.tal.coupons.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.LoginLogic;
import com.tal.coupons.rest.beans.LoginDetails;

/**
* This class provides a RESTful API for UsersLogic.
* @author Sol Invictus
*
*/
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginApi {

	@POST
	public boolean login(LoginDetails details) throws ApplicationException {

		LoginLogic loginLogic = new LoginLogic();
		boolean isLoginSuccessfull = loginLogic.login(details.getUsername(),
				details.getPassword(), details.getClientType());

		return isLoginSuccessfull;

	}

}
