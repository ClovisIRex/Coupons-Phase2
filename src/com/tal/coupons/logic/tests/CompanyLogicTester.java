package com.tal.coupons.logic.tests;

import java.util.ArrayList;

import com.tal.coupons.beans.Company;
import com.tal.coupons.enums.UserProfile;
import com.tal.coupons.exceptions.ApplicationException;
import com.tal.coupons.logic.CompanyLogic;

public class CompanyLogicTester {
	
	private static CompanyLogic cLogic = new CompanyLogic();
	
	public static void createAdminTest() {
		try {
			cLogic.createAdmin();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void createCompanyTest(Company company) {
		try {
			cLogic.createCompany(company);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateCompanyTest(long id,String email, String password) {
		try {
			cLogic.updateCompany(id, password, email);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void getallCompaniesTest() {
		try {
			ArrayList<Company> companies = (ArrayList<Company>) cLogic.getAllCompanies();
			
			for(Company company : companies) {
				System.out.println(company.toString());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void getCompanyByIdTest(long id) {
		try {
			
			Company company = cLogic.getCompanyById(id);
			System.out.println(company.toString());
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeCompanyTest(long id) {
		try {
			cLogic.removeCompany(id);
		} catch(ApplicationException e) {
			e.printStackTrace();
		}
	}

}
