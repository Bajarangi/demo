package com.EASiCabs.DataObjects;

public class ResponseDataObject {
	private int status;
	private String message;
	private String description;
	private UserDataObject userDataObject;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserDataObject getUserDataObject() {
		return userDataObject;
	}
	public void setUserDataObject(UserDataObject userDataObject) {
		this.userDataObject = userDataObject;
	}
	
}
