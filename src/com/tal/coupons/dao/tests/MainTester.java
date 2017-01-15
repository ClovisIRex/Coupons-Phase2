package com.tal.coupons.dao.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.w3c.dom.CDATASection;
import com.tal.coupons.*;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.utils.*;

public class MainTester {

	public static void main(String[] args) {
		
		//Testing for a basic sql connection to see if it can connect to DB
		//ConnectionTester.testConnection();
			
		 try {
			 
	//COMPANYDAO TESTS		 
		//Creating a company in the DB
		//	 System.out.println("test");
		//	CompanyDaoTester.createCompanyTest();
		//Getting and printing company object
			//CompanyDaoTester.getCompanyTest();
		//removing 
		//	 CompanyDaoTester.removeCompanyTest(11);
			 
		//GETTING ALL
			 //CompanyDaoTester.getAllCompaniesTest();
			 
		//updating existing company
			// CompanyDaoTester.updateCompanyTest();
			 
		//get by id
			 //CompanyDaoTester.getCompanyByIDTest();
			 
		// test dao login
			//CompanyDaoTester.loginCompanyTest();
		
			
			 
	//COUPONSDAO TESTS
			//Creating a coupon in the DB
		//		CouponsDaoTester.createCouponTest();
			//Getting and printing coupon object
			//	CouponsDaoTester.getCouponTest();
			//removing 
			//	CouponsDaoTester.removeCouponTest(2);
			
				//getting all coupons by type
				//CouponsDaoTester.getCouponsByTypeTest();
			//GETTING ALL
				// CouponsDaoTester.getAllCouponsTest();
				 
			//updating existing coupon
			//	CouponsDaoTester.updateCouponTest();
				 
			
	//CUSTOMERSDAO TESTS
			 
		//	 CustomerDaoTester.createCustomerTest();
			 
			 
				 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		
		

	
		}
	}
