package com.EASiCabs.UserOperations;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.EASiCabs.Constants.Constants;
import com.EASiCabs.DataObjects.EmployeeDataObject;
import com.EASiCabs.DataObjects.ResponseDataObject;
import com.EASiCabs.DataObjects.UserDataObject;

@Path ("/user")
public class ResetPassword {
	
	@POST
	@Path ("/password")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public Response resetPassword(@QueryParam("reset") boolean reset, InputStream inputStream) throws JSONException {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		String responseEntity = null;
		String inputString = null;
		JSONObject responseJSON = null;
		
		if (reset == false) {
			responseDataObject.setStatus(400);
			responseDataObject.setMessage("Bad url");
			responseDataObject.setDescription("Bad reset value");
			
			responseJSON = Constants.getHELPER().jsonFromResponseDataObject(responseDataObject);
			responseEntity = "@Produces(\"application/json\") Output:" + responseJSON;
			
			return Response.status(responseDataObject.getStatus()).entity(responseEntity).build();
		}
		
		try {
			inputString = Constants.getHELPER().readInputStream(inputStream);
			if (inputString == null || inputString.length() == 0) {
				responseDataObject.setStatus(400);
				responseDataObject.setMessage("Bad request Body");
				responseDataObject.setDescription("The request data is empty");
				
				System.out.println("Recieved null or empty data");
			} else {
				JSONObject jsonObject = new JSONObject(inputString);
				UserDataObject userDataObject = userDataObjectFromJSON(jsonObject);
				if (userDataObject == null) {
					responseDataObject.setStatus(400);
					responseDataObject.setMessage("Bad request Body");
					responseDataObject.setDescription("One or more required parameter is missing");
					
					responseJSON = Constants.getHELPER().jsonFromResponseDataObject(responseDataObject);
					responseEntity = "@Produces(\"application/json\") Output:" + responseJSON;
					
					return Response.status(responseDataObject.getStatus()).entity(responseEntity).build();
				}
				ResponseDataObject validationResult = validateUserData(userDataObject);
				responseDataObject = validationResult;
				
				if (validationResult.getStatus() == 200) {
					responseDataObject = Constants.getDATABASE_MANAGER().validateUserEmailForPasswordReset(userDataObject);
					if (responseDataObject.getStatus() == 200) {
						responseDataObject = Constants.getEMAIL_MANAGER().sendUserPassword(responseDataObject.getUserDataObject());
					}
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
		
		responseJSON = Constants.getHELPER().jsonFromResponseDataObject(responseDataObject);
		responseEntity = "@Produces(\"application/json\") Output:" + responseJSON;
		
		return Response.status(responseDataObject.getStatus()).entity(responseEntity).build();
	}
	
	private UserDataObject userDataObjectFromJSON(JSONObject jsonObject) throws JSONException {
		UserDataObject userDataObject = new UserDataObject();
		EmployeeDataObject employeeDataObject = new EmployeeDataObject();
		
		try {
			employeeDataObject.setEmployeeEmail(jsonObject.getString(Constants.EmployeeFields.EMPLOOYEE_EMAIL_KEY.toString()));
			
			userDataObject.setEmployee(employeeDataObject);
		} catch (JSONException e) {
			e.printStackTrace();
			userDataObject = null;
		}
		return userDataObject;
	}
	
	private ResponseDataObject validateUserData(UserDataObject userDataObject) {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		responseDataObject.setStatus(200);
		responseDataObject.setMessage("success");
		responseDataObject.setDescription("Data validated successfully");
		
		boolean isValidated = true;
		String errorDescription = null;
		
		if (userDataObject.getEmployee().getEmployeeEmail() == null || userDataObject.getEmployee().getEmployeeEmail().length() == 0) {
			isValidated = false;
			errorDescription = "Bad email id";
		}
		
		if (!isValidated) {
			responseDataObject.setStatus(400);
			responseDataObject.setMessage("failure");
			responseDataObject.setDescription(errorDescription);
		}
		return responseDataObject;
	}

}
