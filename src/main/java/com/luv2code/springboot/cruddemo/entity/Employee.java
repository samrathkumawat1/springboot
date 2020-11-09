package com.luv2code.springboot.cruddemo.entity;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Employee.Login", query = "SELECT p FROM Employee p WHERE p.email = ?1 AND p.password=?2")
@Table(name="employee")
public class Employee {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
//	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
//	@JoinColumn(name="employee_details")
//	private EmployeeDetails employeeDetails;
		

	// define constructors
	
	public Employee() {
		
	}

	public Employee(String firstName, String lastName, String email,String passWord) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = passWord;
	}

	// define getter/setter
	
	public int getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// define tostring

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + "]";
	}
		
}











