package com.tal.coupons.logic.interfaces;

import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * /**
 * This interface is to be used by the LoginLogic.
 * @author Sol Invictus
 */
public interface ILoginLogic {
	
	public long login(String username, String password,UserProfile clientType) throws ApplicationException;

}
