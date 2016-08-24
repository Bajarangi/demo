package com.EASiCabs.MessageOperations;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.EASiCabs.DataObjects.ResponseDataObject;
import com.EASiCabs.DataObjects.UserDataObject;

public class EmailOperation {
	private static final String emailUserName = "rsaraf@easi.com";
	private static final String emailUserPassword = "123QWEasdZXC!";

	private Properties properties = null;

	static private EmailOperation sharedInstance = null;

	private EmailOperation() {		
	}

	// The shared
	public static EmailOperation getSharedInstance() {
		if(sharedInstance == null) {
			sharedInstance = new EmailOperation();
		}
		return sharedInstance;
	}
	
	private Properties getProperties() {
		if (properties == null) {
			properties = System.getProperties();
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.host", "smtp.outlook.office365.com");
			properties.setProperty("mail.smtp.port", "25");
		}
		return properties;
	}

	public ResponseDataObject sendUserPassword(UserDataObject userDataObject) throws MessagingException {
		ResponseDataObject responseDataObject = new ResponseDataObject();
		responseDataObject.setStatus(200);
		responseDataObject.setMessage("success");
		responseDataObject.setDescription("Password sent successfully to user email id");
		
		PasswordAuthentication passwordAuthentication = new PasswordAuthentication(emailUserName, emailUserPassword);
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return passwordAuthentication;
			}
		};
		Session session = Session.getDefaultInstance(getProperties(), authenticator);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailUserName));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userDataObject.getEmployee().getEmployeeEmail()));
			message.setSubject("EASi Cabs Password");
			message.setText("Hi "+ userDataObject.getEmployee().getEmployeeName() + "," + "\n\n" + "Please use the below password for login : \n" + userDataObject.getUserPassword() + "\n \n" + "Thank You,\n" + "EASi Cabs Team!");
			
			Transport.send(message);
			
		} catch (Exception e) {
			responseDataObject.setStatus(500);
			responseDataObject.setMessage(e.getMessage());
			responseDataObject.setDescription(e.getLocalizedMessage());
			
			e.printStackTrace();
		}
		
		return responseDataObject;
	}

}
