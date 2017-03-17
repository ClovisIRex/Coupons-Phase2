package com.tal.coupons.threads;


import com.tal.coupons.dao.CouponDao;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * This is the "janitor" thread which is meant to remove old coupons from the db.
 * @author Sol Invictus
 *
 */
public class WorkThread implements Runnable {

	public void run() {

		try {
			CouponDao couponDao = new CouponDao();
			couponDao.removeOldCoupons();
		
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
}
