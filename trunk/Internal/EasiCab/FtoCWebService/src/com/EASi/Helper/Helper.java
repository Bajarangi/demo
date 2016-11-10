package com.EASi.Helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Helper {
	
	static private Helper sharedInstance = null;
	
	private Helper() {
		
	}
	
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
}
