package com.tal.coupons.rest.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.tal.coupons.utils.CookieUtil;


@WebFilter("/rest/api/*")
public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletResponse	httpServletResponse = (HttpServletResponse) response;
		
		// Check if http request came  with cookie, if not we get null;
		Cookie sessionCookie = CookieUtil.getSessionCookie(((HttpServletRequest) request).getCookies());
		
		if (sessionCookie == null) {
			// user not logged in
			httpServletResponse.setStatus(401);
		} else {	
			// user logged in , continue to next filter.
			chain.doFilter(request, response);			
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

