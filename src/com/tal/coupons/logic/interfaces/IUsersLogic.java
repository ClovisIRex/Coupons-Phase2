package com.tal.coupons.logic.interfaces;

import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * /**
 * This interface is to be used by the userLogic.
 * @author Tal Livny
 */
public interface IUsersLogic {
	
	public boolean login(String username, String password,UserProfile clientType) throws ApplicationException;

}
