package com.EASiCabs.UserOperations;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.EASiCabs.Constants.Constants;
import com.EASiCabs.DataObjects.EmployeeDataObject;
import com.EASiCabs.DataObjects.ResponseDataObject;
import com.EASiCabs.DataObjects.UserDataObject;

@Path ("/user")
public class RegisterUser {
	
	@POST
	@Path ("/register")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response registerUser(InputStream inputStream) throws JSONException {
		
		ResponseDataObject responseDataObject = new ResponseDataObject();
		String inputString = null;
		String responseEntity = null;
		
		try {
			inputString = Constants.getHELPER().readInputStream(inputStream);
			
			if (inputString == null || inputString.length() == 0) {
				responseDataObject.setStatus(400);
				responseDataObject.setMessage("Bad request Body");
				responseDataObject.setDescription("The request data is empty");
				
				System.out.println("Recieved null or empty user registration data");
			} else {
				JSONObject userRegistrationJsonObject = new JSONObject(inputString);
				UserDataObject userDataObject = userObjectFromJSON(userRegistrationJsonObject);
				if (userDataObject == null) {
					responseDataObject.setStatus(400);
					responseDataObject.setMessage("Bad request Body");
					responseDataObject.setDescription("One or more required parameter is missing");
					
					JSONObject responseJSON = Constants.getHELPER().jsonFromResponseDataObject(responseDataObject);
					responseEntity = "@Produces(\"application/json\") Output:" + responseJSON;
					
					return Response.status(responseDataObject.getStatus()).entity(responseEntity).build();
				}
				ResponseDataObject validationResult = validateUserRegistrationData(userDataObject);
				responseDataObject = validationResult;
				
				if (validationResult.getStatus() == 200) {
					responseDataObject = Constants.getDATABASE_MANAGER().registerUserWithDetails(userDataObject);
				}
			}
		} catch (JSONException e) {
			responseDataObject.setStatus(400);
			responseDataObject.setMessage(e.getMessage());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			e.printStackTrace();
		} catch (Exception e) {
			responseDataObject.setStatus(400);
			responseDataObject.setMessage(e.getMessage());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			e.printStackTrace();
		}
				
		JSONObject responseJSON = Constants.getHELPER().jsonFromResponseDataObject(responseDataObject);
		responseEntity = "@Produces(\"application/json\") Output:" + responseJSON;
		
		return Response.status(responseDataObject.getStatus()).entity(responseEntity).build();
	}
	
	private UserDataObject userObjectFromJSON(JSONObject userJsonObject) throws JSONException {
		UserDataObject userDataObject = new UserDataObject();
		EmployeeDataObject employeeDataObject = new EmployeeDataObject();

		try {
			userDataObject.setUserId(userJsonObject.getString(Constants.UserFields.USER_ID_KEY.toString()));
			userDataObject.setUserPassword(userJsonObject.getString(Constants.UserFields.USER_PASSWORD_KEY.toString()));
			userDataObject.setUserType(userJsonObject.getString(Constants.UserFields.USER_TYPE.toString()));
			
			employeeDataObject.setEmployeeName(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_NAME_KEY.toString()));
			employeeDataObject.setEmployeeId(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_ID_KEY.toString()));
			employeeDataObject.setEmployeeEmail(userJsonObject.getString(Constants.EmployeeFields.EMPLOOYEE_EMAIL_KEY.toString()));
			employeeDataObject.setEmployeeContact(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_CONTACT_KEY.toString()));
			employeeDataObject.setEmployeeEmergencyContact(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_EMERGENCY_CONTACT_KEY.toString()));
			employeeDataObject.setEmployeeShift(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_SHIFT_KEY.toString()));
			employeeDataObject.setEmployeeAddress(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_ADDRESS_KEY.toString()));
			employeeDataObject.setEmployeeOfficeLocation(userJsonObject.getString(Constants.EmployeeFields.EMPLOYEE_OFFICE_LOCATION_KEY.toString()));
			
			userDataObject.setEmployee(employeeDataObject);
		} catch (JSONException e) {
			e.printStackTrace();
			userDataObject = null;
		}
		
		return userDataObject;
	}
	
	private ResponseDataObject validateUserRegistrationData(UserDataObject userDataObject) throws JSONException {		
		ResponseDataObject validationResult = new ResponseDataObject();
		
		boolean isValidated = true;
		
		int statusCode = 200;
		String message = "success";
		String errorDescription = null;
		
		EmployeeDataObject employeeDataObject = userDataObject.getEmployee();
		
		if (employeeDataObject.getEmployeeName() == null || employeeDataObject.getEmployeeName().length() == 0) {
			isValidated = false;
			errorDescription = "Bad employee name";
		} else if (employeeDataObject.getEmployeeId() == null || employeeDataObject.getEmployeeId().length() == 0) {
			isValidated = false;
			errorDescription = "Bad employee id";
		} else if (employeeDataObject.getEmployeeEmail() == null || employeeDataObject.getEmployeeEmail().length() == 0 || !Constants.getHELPER().isEmailValid(employeeDataObject.getEmployeeEmail())) {
			isValidated = false;
			errorDescription = "Bad employee email";
		} else if (employeeDataObject.getEmployeeContact() == null || employeeDataObject.getEmployeeContact().length() == 0 || employeeDataObject.getEmployeeContact().length() != 10) {
			isValidated = false;
			errorDescription = "Bad employee contact";
		} else if (employeeDataObject.getEmployeeEmergencyContact() == null || employeeDataObject.getEmployeeEmergencyContact().length() == 0 || employeeDataObject.getEmployeeEmergencyContact().length() != 10) {
			isValidated = false;
			errorDescription = "Bad employee emergency contact";
		} else if (employeeDataObject.getEmployeeShift() == null || employeeDataObject.getEmployeeShift().length() == 0) {
			isValidated = false;
			errorDescription = "Bad employee shift type";
		} else if (employeeDataObject.getEmployeeAddress() == null || employeeDataObject.getEmployeeAddress().length() == 0) {
			isValidated = false;
			errorDescription = "Bad employee address";
		} else if (employeeDataObject.getEmployeeOfficeLocation() == null || employeeDataObject.getEmployeeOfficeLocation().length() == 0) {
			isValidated = false;
			errorDescription = "Bad employee office location";
		} else if (userDataObject.getUserId() == null || userDataObject.getUserId().length() == 0) {
			isValidated = false;
			errorDescription = "Bad user id";
		} else if (userDataObject.getUserPassword() == null || userDataObject.getUserPassword().length() == 0) {
			isValidated = false;
			errorDescription = "Bad user password";
		} else if (userDataObject.getUserType() == null || userDataObject.getUserType().length() == 0) {
			isValidated = false;
			errorDescription = "Bad user type";
		}
		
		if (!isValidated) {
			statusCode = 400;
			message = "failure";
		}
		
		validationResult.setStatus(statusCode);
		validationResult.setMessage(message);
		validationResult.setDescription(errorDescription);
		
		return validationResult;
	}
}
