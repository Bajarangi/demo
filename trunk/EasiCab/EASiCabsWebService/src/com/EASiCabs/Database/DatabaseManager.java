package com.EASiCabs.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.EASiCabs.DataObjects.EmployeeDataObject;
import com.EASiCabs.DataObjects.ResponseDataObject;
import com.EASiCabs.DataObjects.UserDataObject;

public class DatabaseManager {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/EASiCabs_test";
	
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "loveer";
	
	private Connection connection = null;
	//private Statement statement = null;
	//private PreparedStatement preparedStatement = null;
	//private ResultSet resultSet = null;
	
	static private DatabaseManager sharedInstance = null; 
	
	public static DatabaseManager getSharedInstance() {
		if(sharedInstance == null) {
			sharedInstance = new DatabaseManager();
		}
		return sharedInstance;
	}
	
	private ResponseDataObject createConnection() {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		responseDataObject.setStatus(200);
		responseDataObject.setMessage("success");
		responseDataObject.setDescription(null);
		
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		} catch (Exception e) {
			responseDataObject.setStatus(500);
			responseDataObject.setMessage("Error Code : " + e.getMessage());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			System.out.println("Exception : "+e.getMessage());
		}
		
		return responseDataObject;
	}
	
	public ResponseDataObject registerUserWithDetails(UserDataObject userDataObject) throws SQLException {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		
		try {
			if (connection == null) {
				responseDataObject = createConnection();
			}
			
			if (connection == null) {
				return responseDataObject;
			}
	
			EmployeeDataObject employeeDataObject = userDataObject.getEmployee();
			String fetchQuery = "SELECT * FROM User WHERE user_id = " + "'" + userDataObject.getUserId() + "'";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (resultSet.next()) {
				responseDataObject.setStatus(400);
				responseDataObject.setMessage("User already exist");
				responseDataObject.setDescription("User already registered");
			} else {
				//Insert the details into the Employee table
				String insertQuery = String.format("INSERT INTO Employee(employee_id, employee_name, employee_email, employee_address, employee_officeLocation, employee_contact, employee_emergencyContact, employee_shift) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", employeeDataObject.getEmployeeId(), employeeDataObject.getEmployeeName(), employeeDataObject.getEmployeeEmail(), employeeDataObject.getEmployeeAddress(), employeeDataObject.getEmployeeOfficeLocation(), employeeDataObject.getEmployeeContact(), employeeDataObject.getEmployeeEmergencyContact(), employeeDataObject.getEmployeeShift());
				statement.executeUpdate(insertQuery);
				
				//Insert the details into the user table and map it to the respective employee details
				insertQuery = String.format("INSERT INTO User(user_id, user_password, user_type, employee_id) VALUES('%s', '%s', '%s', '%s')", userDataObject.getUserId(), userDataObject.getUserPassword(), userDataObject.getUserType(), employeeDataObject.getEmployeeId());
				statement.executeUpdate(insertQuery);
				
				responseDataObject.setStatus(200);
				responseDataObject.setMessage("success");
				responseDataObject.setDescription(null);
			}
		} catch (SQLException e) {
			responseDataObject.setStatus(500);
			responseDataObject.setMessage("Error Code : " + e.getErrorCode());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			e.printStackTrace();
		}
		return responseDataObject;
	}
	
	public ResponseDataObject loginUserWithDetails(UserDataObject userDataObject) throws SQLException {
		
		ResponseDataObject responseDataObject = new ResponseDataObject();
		
		try {
			if (connection == null) {
				responseDataObject = createConnection();
				if (connection == null) {
					return responseDataObject;
				}
			}
			
			String fetchQuery = "SELECT * FROM User WHERE user_id = " + "'" + userDataObject.getUserId() + "'" + " AND " + "user_password = " + "'" + userDataObject.getUserPassword() + "'";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (resultSet.next()) {
				responseDataObject.setStatus(200);
				responseDataObject.setMessage("Valid user");
				responseDataObject.setDescription("Registered user with the matching email id and the password found");
			} else {
				responseDataObject.setStatus(400);
				responseDataObject.setMessage("Failure");
				responseDataObject.setDescription("Invalid user name or password");
			}
		} catch (SQLException e) {
			responseDataObject.setStatus(500);
			responseDataObject.setMessage("Error Code : " + e.getErrorCode());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			e.printStackTrace();
		}
		return responseDataObject;
	}
	
	public ResponseDataObject validateUserEmailForPasswordReset(UserDataObject userDataObject) throws SQLException {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		
		try {
			if (connection == null) {
				responseDataObject = createConnection();
				if (connection == null) {
					return responseDataObject;
				}
			}
			
			String fetchQuery = "SELECT * FROM Employee WHERE employee_email = " + "'" + userDataObject.getEmployee().getEmployeeEmail() + "'";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (!resultSet.next()) {
				responseDataObject.setStatus(400);
				responseDataObject.setMessage("Failure");
				responseDataObject.setDescription("Email id not registered");
			} else {
				userDataObject.getEmployee().setEmployeeName(resultSet.getString("employee_name"));
				fetchQuery = "SELECT * FROM User WHERE employee_id = " + "'" + resultSet.getString("employee_id") + "'";
				statement = connection.createStatement(); 
				resultSet = statement.executeQuery(fetchQuery);
				if (!resultSet.next()) {
					responseDataObject.setStatus(400);
					responseDataObject.setMessage("Failure");
					responseDataObject.setDescription("The requested email does not have a login");
				} else {
					userDataObject.setUserPassword(resultSet.getString("user_password"));
					
					responseDataObject.setStatus(200);
					responseDataObject.setMessage("success");
					responseDataObject.setDescription("Valid email id");
					responseDataObject.setUserDataObject(userDataObject);
				}
			}
		} catch (SQLException e) {
			responseDataObject.setStatus(500);
			responseDataObject.setMessage("Error Code : " + e.getErrorCode());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			e.printStackTrace();
		}
		
		return responseDataObject;
	}
}
