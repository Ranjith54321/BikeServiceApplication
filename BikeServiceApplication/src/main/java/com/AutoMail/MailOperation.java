package com.AutoMail;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
public class MailOperation {
	// database variable's
	/*
		@Value("${database.driver}")
		private static String driver; 
		@Value("${database.url}")
		private static String url;
		@Value("${database.user_name}")
		private static String user_name;
		@Value("${database.pass}")
		private static String pass; 
		static Connection con;\
		*/
	
	private static String driver = "com.mysql.jdbc.Driver"; 
	private static String url = "jdbc:mysql://localhost:3306/workshop";
	private static String user_name = "root";
	private static String pass = "toor"; 
	static Connection con;
	
	/*
	 	@Value("${owner.mail}")
		private static String owneremail;
	 	@Value("${owner.mail_pass}")
		private static String ownerpass;
	 	@Value("${database.mobile}")
		private static String owner_phone_number;
	 	
	 	*/
	private static String owneremail = "xyz@gmail.com";
	private static String ownerpass = "****";
	private static String owner_phone_number = "9876543210";
	 	public static void startconnection() throws ClassNotFoundException, SQLException {
			//System.out.println("connected to DB successfully");
					Class.forName(driver);
					con = (Connection) DriverManager.getConnection(url,user_name,pass);
		}
	public static void sendmail(String to,String type) throws SQLException, ClassNotFoundException { 
		   
		  Properties prop = new Properties();
		  prop.put("mail.smtp.host", "smtp.gmail.com");
		  prop.put("mail.smtp.port", "465");
		  prop.put("mail.smtp.auth", "true");
		  prop.put("mail.smtp.socketFactory.port", "465");
		  prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		  Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(owneremail, ownerpass);
		   }
		  });

		  try {

			   if(type.equals("booking")) {
				   //System.out.println("im coming hre");
				   Message message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(owneremail)); // from mail
				   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(owneremail));// to owner mail to notify the owner
				   message.setSubject("New Booking From "+to);
				   message.setText("Mail From Java Code Using Gmail...");
				   
				   startconnection();
				   
				   PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from booking where email='"+to+"'");
				   ResultSet rs = ps.executeQuery();
				   rs.next();
				   
				   String vehicle_no = rs.getString(2);
				   int service_id = rs.getInt(3);
				   
				   PreparedStatement ps2 = (PreparedStatement) con.prepareStatement("select * from service where id="+service_id);
				   ResultSet rs2 = ps2.executeQuery();
				   rs2.next();
				   
				   PreparedStatement ps3 = (PreparedStatement) con.prepareStatement("select * from user where email='"+to+"'");
				   ResultSet rs3 = ps3.executeQuery();
				   rs3.next();
				   
				   String User_phone_number = rs3.getString(2);
				   
				   String gen_check;
				   String oil_change;
				   String wat_wash;
				   String total = rs2.getString(5);
				   
				    if("0".equals(rs2.getString(2)))
					   gen_check = "NO";
					else
						gen_check = rs2.getString(2);
					if("0".equals(rs2.getString(3)))
						oil_change = "NO";
					else
						oil_change = rs2.getString(3);
					if("0".equals(rs2.getString(4)))
						wat_wash = "NO";
					else
						wat_wash = rs2.getString(4);

				   String htmlcode = "<h1> New Bike Booking Detail's</h1><table>\r\n" + 
					   		"	<tr><td>Vehicle Number : </td>\r\n" + 
					   		"	<td>"+ vehicle_no +"</td></tr>\r\n" + 
					   		"	<tr><td>User Mail-ID : </td>\r\n" + 
					   		"	<td>"+ to +"</td></tr>\r\n" +
					   		"	<tr><td>User Phone Number : </td>\r\n" + 
					   		"	<td>"+ User_phone_number +"</td></tr>\r\n" +
					   		"	<tr><td>General Checkup Charge : </td>\r\n" + 
					   		"	<td>"+ gen_check +"</td></tr>\r\n" +  
					   		"	<tr><td>Oil Change Charge : </td>\r\n" + 
					   		"	<td>"+ oil_change +"</td></tr>\r\n" +
					   		"	<tr><td>Water Wash Charge : </td>\r\n" + 
					   		"	<td>"+ wat_wash +"</td></tr>\r\n" +
					   		"	<tr><td>Total Charge : </td>\r\n" + 
					   		"	<td>"+ total +"</td></tr>\r\n" +
					   		"	</table>";
					   message.setContent(htmlcode,"text/html");

					   Transport.send(message);
					   
			   System.out.println("Mail Sent to owner and informed...");
		   }
			   else {
				   System.out.println("sender mail"+owneremail+" driver"+driver);
				   Message message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(owneremail)); // from mail
				   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));// to customer mail
				   message.setSubject("Bike Service Center");
				   
				   startconnection();
				   
				   PreparedStatement ps = (PreparedStatement) con.prepareStatement("select * from booking where email='"+to+"'");
				   ResultSet rs = ps.executeQuery();
				   rs.next();
				   
				   String vehicle_no = rs.getString(2);
				   int service_id = rs.getInt(3);
				   
				   PreparedStatement ps2 = (PreparedStatement) con.prepareStatement("select * from service where id="+service_id);
				   ResultSet rs2 = ps2.executeQuery();
				   rs2.next();
				   
				   String total = rs2.getString(5);
				   
				   String htmlcode = "<h1> You'r Bike is ready for Delivery</h1><table>\r\n" + 
					   		"	<tr><td>Vehicle Number : </td>\r\n" + 
					   		"	<td>"+ vehicle_no +"</td></tr>\r\n" + 
					   		"	<tr><td>Total Amount : </td>\r\n" + 
					   		"	<td>"+ total +"</td></tr>\r\n" +
					   		"	<tr><td>For More Details call : </td>\r\n" + 
					   		"	<td>"+ owner_phone_number +"</td></tr>\r\n" +
					   		"	</table>";
					   message.setContent(htmlcode,"text/html");
					   
				   
				   //message.setText("You'r Bike is ready for Delivery \n for more Details call : "+owner_phone_number);
				   Transport.send(message);
				   System.out.println("Mail Sent to user and informed...");
			   }
		  } catch (MessagingException e) {
		   e.printStackTrace();
		  }
	}
	/*
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		MailOperation.sendmail("54321@gmail.com", "ready");
	}*/
}
