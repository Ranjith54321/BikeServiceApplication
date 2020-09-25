package com.Controller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@Controller
public class Userlogin {
	// database connection variables
	@Value("${database.driver}")
	private String driver; 
	@Value("${database.url}")
	private String url;
	@Value("${database.user_name}")
	private String user_name;
	@Value("${database.pass}")
	private String pass; 
	
	// Owner details from property file
	@Value("${owner.mail}")
	private String wmail;
	@Value("${owner.pass}")
	private String wpass;
	
	@RequestMapping("/checkuser")
	public String checkuser(HttpServletRequest request,Model model) throws ClassNotFoundException, SQLException {
		if(request.getParameter("mail").equals(wmail) && request.getParameter("password").equals(wpass)) {
			return "owner_page";
		}else {
			String mail_get = request.getParameter("mail");
			String pass_get = request.getParameter("password");
			
			// load the driver
			Class.forName(driver);
			
			//2.) get a connection
			Connection con = (Connection) DriverManager.getConnection(url,user_name,pass);
			Statement st = (Statement) con.createStatement();
			
			ResultSet rs = st.executeQuery("select * from user");
			
			while(rs.next()) {
				String mail = rs.getString(1);
				String pass = rs.getString(3);
				if(mail.equals(mail_get) && pass.equals(pass_get)) {
					model.addAttribute("email",mail);
					//model.addAttribute("Dno",rs.getString(1));
					HttpSession session = request.getSession();
					session.setAttribute("username", mail);
					
					return "user_page";
				}

			}
		}
		return "wrong";
	}
	
	
	// for new user registration
	@RequestMapping("/newuser")
	public String newuser(HttpServletRequest rqeRequest) {
		return "sign_up";
	}
}
