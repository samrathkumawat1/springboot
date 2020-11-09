package com.luv2code.springboot.cruddemo.entity;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_details")
public class EmployeeDetails {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="emp_details_id")
	private int id;
	
	@Column(name="address")
	private String address;
	
	@Column(name="fb")
	private String fb;
	
//	@OneToOne(mappedBy="employee",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//	private Employee employee;
//
//	
//	public Employee getEmployee() {
//		return employee;
//	}
//
//
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}


	public EmployeeDetails() {
		
	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getFb() {
		return fb;
	}


	public void setFb(String fb) {
		this.fb = fb;
	}


		
}











