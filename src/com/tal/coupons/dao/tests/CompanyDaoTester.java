package com.tal.coupons.dao.tests;

import java.util.ArrayList;
import java.util.Arrays;

import com.tal.coupons.beans.Company;
import com.tal.coupons.dao.CompanyDao;
import com.tal.coupons.exceptions.ApplicationException;

/**
 * Tester class for all methods of ComapniesDao
 * @author Tal
 *
 */
public class CompanyDaoTester {
	private static Company company = new Company("Clinton Foundation", "666", "Clinton@moloch.com");
	
	private static CompanyDao cdao = new CompanyDao();
	
	public static void createCompanyTest() {
		try {
			cdao.createCompany(company);
			System.out.println(company.toString());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public static void removeCompanyTest(long id) {
		try {
			cdao.removeCompany(id);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void getCompanyTest() throws ApplicationException {
		try {
			System.out.println(company.toString());
			//Company ret = cdao.getCompanyById(company.getId());
			//System.out.println(ret.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllCompaniesTest() throws ApplicationException {
		try {
			ArrayList<Company> allCompanies = (ArrayList<Company>) cdao.getAllCompanies();
			System.out.println(Arrays.toString(allCompanies.toArray()));
			
		} catch(ApplicationException e) {
			e.printStackTrace();
		}	
	}
	
	public static void updateCompanyTest() throws ApplicationException {
		Company uComp = new Company(10, "George Soros Fund", "Illuminati1234", "Clinton@moloch.com");
		try {
			cdao.updateCompany(uComp);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void getCompanyByIDTest() throws ApplicationException {
		try {
			Company compbyid = cdao.getCompanyById(10);
			
			System.out.println(compbyid.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void loginCompanyTest() throws ApplicationException {
		try {
			boolean flag = cdao.login("George Soros Fund", "Illuminati1234");
			if(flag) {
				System.out.println("user and password match");
			}
			else {
				System.out.println("invalid user or password");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
