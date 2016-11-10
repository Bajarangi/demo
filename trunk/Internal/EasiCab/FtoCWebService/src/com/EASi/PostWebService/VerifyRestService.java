package com.EASi.PostWebService;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.EASi.Helper.Helper;
import com.EASi.PostWebService.RequestBody.RegisterBody;

@Path ("/verify")
public class VerifyRestService {
		
	@POST
	@Path ("/rest")	
	public Response samplePost(InputStream incomingData) {
		String inputString = null;
		try {
			inputString = Helper.getSharedInstance().readInputStream(incomingData);
			JSONObject jsonObject = new JSONObject(inputString);
			
			String employeeName = jsonObject.getString("employeeName");
			String employeeID = jsonObject.getString("employeeID");
			Long employeeContactNumber = jsonObject.getLong("contactNumber");
			System.out.println("Recieved Data : " + employeeName + "\n" + employeeID + "\n" + employeeContactNumber + "\n");
		} catch (Exception e) {
			System.out.println("Error Parsing : " + e.getMessage());
		}
		
		return Response.status(200).entity(inputString.toString()).build();
	}
	
	@POST
	@Path ("/restJson")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response samplePostJson(RegisterBody incomingRegisterJSONData) {
		System.out.println("Error Parsing : " + incomingRegisterJSONData.getEmployeeName());
		System.out.println("Error Parsing : " + incomingRegisterJSONData.getEmployeeId());
		System.out.println("Error Parsing : " + incomingRegisterJSONData.getContactNumber());
				
		return Response.status(200).entity(incomingRegisterJSONData.toString()).build();
	}

}
