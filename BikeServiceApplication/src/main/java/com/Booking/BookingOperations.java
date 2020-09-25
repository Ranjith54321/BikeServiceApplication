package com.Booking;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AutoMail.MailOperation;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
public class BookingOperations {
	@Value("${database.driver}")
	private String driver; 
	@Value("${database.url}")
	private String url;
	@Value("${database.user_name}")
	private String user_name;
	@Value("${database.pass}")
	private String pass; 
	static Connection con;

	@PostConstruct 
	public void init() throws ClassNotFoundException, SQLException { 
		startconnection();
	}
	public void startconnection() throws ClassNotFoundException, SQLException {
		//System.out.println("connected to DB successfully");
			//1..) load the driver
				Class.forName(driver);
				
				//2.) get a connection
				con = (Connection) DriverManager.getConnection(url,user_name,pass);
	}
	
	@RequestMapping("/addBookTable")
	public @ResponseBody String addBookTable(HttpServletRequest request) throws SQLException, ClassNotFoundException {
		int status=0;
		
			PreparedStatement st=(PreparedStatement) con.prepareStatement("insert into booking values(?,?,?,?,?,?);");
			st.setString(1,request.getParameter("email"));
			st.setString(2,request.getParameter("no"));
			st.setInt(3,Integer.parseInt(request.getParameter("id")));
			
			long millis=System.currentTimeMillis();  
		    java.sql.Date date=new java.sql.Date(millis);
		    DateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
		    String curr_date = df.format(date); 
			st.setString(4,curr_date);// date
			st.setString(5,"-");
			st.setString(6,"pending");
			status=st.executeUpdate();
			
			
			int id = Integer.parseInt(request.getParameter("id"));
			PreparedStatement st2 = (PreparedStatement) con.prepareStatement("select * from service where id = "+id);
			ResultSet rs2 = st2.executeQuery();
			rs2.next();
			int cnt = rs2.getInt(6);
			cnt++;
			
			PreparedStatement st3 = (PreparedStatement) con.prepareStatement("update service set user_count = "+cnt+" where id = "+id);
			status=st3.executeUpdate();
			
			// send mail to notify the owner 
			MailOperation.sendmail(request.getParameter("email"),"booking"); // we send use email 
						
			if(status>0)
				return "success";
			else
				return "fail";
	}

	
	@PreDestroy
	public void destroy() throws SQLException { 
		//System.out.println("connection successfully closed");
		closeconnection();
	}
	public void closeconnection() throws SQLException {
		//System.out.println("connection successfully closed");
		con.close();
	}
}
