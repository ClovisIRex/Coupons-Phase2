package com.tal.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class provides the basic bean structure of a 'Customer' object,
 * which will be used in higher layers of this program.
 * @author Tal Livny
 */

@XmlRootElement
public class Customer {
	private long id;
	private String custName;
	private String password;
	
	
	public Customer() {
		
	}
	
	public Customer(String custName, String password) {
		this.setCustName(custName);
		this.setPassword(password);	
	}
	
	public Customer(long id, String custName, String password) {
		this.setId(id);
		this.setCustName(custName);
		this.setPassword(password);	
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}
	
	

	

}
