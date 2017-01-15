package com.tal.coupons.beans;
/**
 * This class provides the basic bean structure of a 'Company' object,
 * which will be used in higher layers of this program.
 * @author Tal Livny
 */
public class Company {
	
	private long id;
	private String compName;
	private String password;
	private String email;
	
	//Empty default constructor,  in order to reserve place for future constructors with diff. parameters, in accordance to requests
	public Company() {	
	}
	
	
	//ctor w/o id, in case we don't need it(id is auot incremented by the db)
	public Company(String compName, String password, String email) {
		this.setCompName(compName);
		this.setPassword(password);
		this.setEmail(email);
	}
	
	//ctor w/id, in cases which we need to search a company by its id
	public Company(long id, String compName, String password, String email) {
		this.setId(id);
		this.setCompName(compName);
		this.setPassword(password);
		this.setEmail(email);
	}
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	
	
}
