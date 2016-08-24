package com.EASiCabs.Helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.EASiCabs.DataObjects.ResponseDataObject;

public class Helper {
	static private Helper sharedInstance = null;
	
	private Helper() {		
	}
	
	// The shared
	public static Helper getSharedInstance() {
		if(sharedInstance == null) {
			sharedInstance = new Helper();
		}
		return sharedInstance;
	}
	
	public String readInputStream(InputStream inputStream) {
		StringBuilder responseString = new StringBuilder();
		try {
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(inputStream));
			String readerString = null;
			while ((readerString = bfReader.readLine()) != null) {
				responseString.append(readerString).append("\n");
			}
			bfReader.close();
		} catch (Exception e) {
			System.out.println("Error Parsing : " + e.getMessage());
		}
		return responseString.toString();
	}
	
	public boolean isEmailValid(String emailString) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean isValidEmail = emailString.matches(EMAIL_REGEX);

		return isValidEmail;
	}
	
	public JSONObject jsonFromResponseDataObject(ResponseDataObject responseDataObject) throws JSONException {
		JSONObject responseJSON = new JSONObject();
		try {
			responseJSON.put("status", responseDataObject.getStatus());
			responseJSON.put("message", responseDataObject.getMessage());
			responseJSON.put("description", responseDataObject.getDescription());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJSON;
	}
}
