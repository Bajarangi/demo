package com.EASiCabs.Constants;

import com.EASiCabs.Database.DatabaseManager;
import com.EASiCabs.Helper.Helper;
import com.EASiCabs.MessageOperations.EmailOperation;

public class Constants {
	
	static private Helper HELPER = null;
	static private DatabaseManager DATABASE_MANAGER = null;
	static private EmailOperation EMAIL_MANAGER = null;
	
	public static Helper getHELPER() {
		if (HELPER == null) {
			HELPER = Helper.getSharedInstance();
		}
		return HELPER;
	}
	
	public static DatabaseManager getDATABASE_MANAGER() {
		if (DATABASE_MANAGER == null) {
			DATABASE_MANAGER = DatabaseManager.getSharedInstance();
		}
		return DATABASE_MANAGER;
	}
	
	public static EmailOperation getEMAIL_MANAGER() {
		if (EMAIL_MANAGER == null) {
			EMAIL_MANAGER = EmailOperation.getSharedInstance();
		}
		return EMAIL_MANAGER;
	}
	
	public enum EmployeeFields {
		EMPLOYEE_NAME_KEY("employee_name"),
		EMPLOYEE_ID_KEY("employee_id"),
		EMPLOYEE_PASSWORD_KEY("employee_password"),
		EMPLOOYEE_EMAIL_KEY("employee_email"),
		EMPLOYEE_ADDRESS_KEY("employee_address"),
		EMPLOYEE_OFFICE_LOCATION_KEY("employee_officeLocation"),
		EMPLOYEE_CONTACT_KEY("employee_contact"),
		EMPLOYEE_EMERGENCY_CONTACT_KEY("employee_emergencyContact"),
		EMPLOYEE_SHIFT_KEY("employee_shift"),
		USER_TYPE("user_type");
		
		private final String text;
		
		private EmployeeFields(final String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
	
	public enum UserFields {
		USER_ID_KEY("user_id"),
		USER_PASSWORD_KEY("user_password"),
		USER_TYPE("user_type");
		
		private final String text;
		
		private UserFields(final String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
	
}
