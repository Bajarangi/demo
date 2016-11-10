package com.EASiCabs.DataObjects;

public class UserDataObject {
	
	private String userId;
	private String userPassword;
	private String userType;
	private EmployeeDataObject employee;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public EmployeeDataObject getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDataObject employee) {
		this.employee = employee;
	}

}
