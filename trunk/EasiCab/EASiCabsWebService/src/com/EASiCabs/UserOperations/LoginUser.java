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
import com.EASiCabs.DataObjects.ResponseDataObject;
import com.EASiCabs.DataObjects.UserDataObject;

@Path ("/user")
public class LoginUser {
	
	@POST
	@Path ("/login")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	
	public Response loginUser(InputStream inputStream) throws Exception {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		responseDataObject.setStatus(200);
		responseDataObject.setMessage("success");
		responseDataObject.setDescription("valid user");
		
		String responseEntity = null;
		
		try {
			String inputString = Constants.getHELPER().readInputStream(inputStream);
			if (inputString == null || inputString.length() == 0) {
				responseDataObject.setStatus(400);
				responseDataObject.setMessage("Bad request Body");
				responseDataObject.setDescription("The request data is empty");
				
				System.out.println("Recieved null or empty user login data");
			} else {
				JSONObject userLoginJsonObject = new JSONObject(inputString);
				UserDataObject userDataObject = employeeObjecFromJSON(userLoginJsonObject);
				if (userDataObject == null) {
					responseDataObject.setStatus(400);
					responseDataObject.setMessage("Bad request Body");
					responseDataObject.setDescription("One or more required parameter is missing");
					
					JSONObject responseJSON = Constants.getHELPER().jsonFromResponseDataObject(responseDataObject);
					responseEntity = "@Produces(\"application/json\") Output:" + responseJSON;
					
					return Response.status(responseDataObject.getStatus()).entity(responseEntity).build();
				}
				ResponseDataObject validationResult = validateUserLoginData(userDataObject);
				responseDataObject = validationResult;
				
				if (validationResult.getStatus() == 200) {
					responseDataObject = Constants.getDATABASE_MANAGER().loginUserWithDetails(userDataObject);
				}
			}
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

	private UserDataObject employeeObjecFromJSON(JSONObject jsonObject) throws JSONException {
		UserDataObject userDataObject = new UserDataObject();
		
		try {
			userDataObject.setUserId(jsonObject.getString(Constants.UserFields.USER_ID_KEY.toString()));
			userDataObject.setUserPassword(jsonObject.getString(Constants.UserFields.USER_PASSWORD_KEY.toString()));
		}catch (JSONException e) {
			e.printStackTrace();
			userDataObject = null;
		}
		
		return userDataObject;
	}
	
	private ResponseDataObject validateUserLoginData(UserDataObject userDataObject) {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		responseDataObject.setStatus(200);
		responseDataObject.setMessage("success");
		responseDataObject.setDescription("Data validated successfully");
		
		boolean isValidated = true;
		String errorDescription = null;
		
		if (userDataObject.getUserId() == null || userDataObject.getUserId().length() == 0) {
			isValidated = false;
			errorDescription = "Bad user id";
		} else if (userDataObject.getUserPassword() == null || userDataObject.getUserPassword().length() == 0) {
			isValidated = false;
			errorDescription = "Bad user password";
		}
		
		if (!isValidated) {
			responseDataObject.setStatus(400);
			responseDataObject.setMessage("failure");
			responseDataObject.setDescription(errorDescription);
		}
		
		return responseDataObject;
	}
}
