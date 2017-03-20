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
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.UsersLogic;
import com.tal.coupons.rest.beans.LoginDetails;
import com.tal.coupons.utils.CookieUtil;

/**
* This class provides a RESTful API for UsersLogic.
* @author Sol Invictus
*
*/
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
public class UsersApi {

	@POST
	public Response login(@Context HttpServletRequest request,LoginDetails details) throws ApplicationException {
		
		// first we get all params from the http request
		String username = details.getUsername();
		String password = details.getPassword();
		int userTypeID = details.getClientType();
		
		UserProfile userType = UserProfile.getProfilebyValue(userTypeID);
		
		// then we test login to see if the user has send valid params
		UsersLogic usersLogic = new UsersLogic();
		long logonID = usersLogic.login(username,password, userType);

		// if all is right we create a session, attach it with a new cookie and give back to the client
		if(logonID != -1) {
			HttpSession session = request.getSession();
			NewCookie sessionCookie = CookieUtil.createSessionCookie(logonID, userTypeID);
			return Response.status(Response.Status.OK).cookie(sessionCookie).build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
