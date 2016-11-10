package com.EASi.Database;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.ws.RequestWrapper;
import javax.ws.rs.Path;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ws.rs.GET;

@WebService
@Path ("/user")
public class UserRegistration {
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DATABASE_URL = "jdbc:mysql://localhost/Sample";
	
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "loveer";
	
	private Connection connection = null;
	private Statement statement = null;
	//private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	@WebMethod
	@GET
	public void connectDatabase() throws Exception{
		try {
			if (connection == null) {
				createConnection();
			}
			
			statement = connection.createStatement();
			//resultSet = statement.executeQuery("Select * from customer");
			
			System.out.println("Statement "+statement);
			System.out.println("Result Set "+resultSet);
			
			String sampleInsert = "INSERT INTO customer " + "VALUES (400)";
			statement.executeUpdate(sampleInsert);
			
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
			throw e;
		} finally {
			connection.close();
		}
	}
	
	private void createConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
		}
	}
	
	@RequestWrapper
	private void setUpDataBase() {
		try {
			connectDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
