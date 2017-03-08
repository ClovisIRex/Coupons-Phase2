/*
package com.tal.coupons.rest.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/rest/api/*")
public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest	httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse	httpServletResponse = (HttpServletResponse) response;
		
		// Check if http request came  with session, if not we get null;
		HttpSession	httpSession = httpServletRequest.getSession(false);
		
		if ( httpSession == null )
		{ // user not logged in
			System.out.println("filter failed");
			httpServletResponse.setStatus(401);
		}
		else
		{	// user logged in , continue to next filter.
			System.out.println("filter succeded");
			chain.doFilter(request, response);			
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
*/
