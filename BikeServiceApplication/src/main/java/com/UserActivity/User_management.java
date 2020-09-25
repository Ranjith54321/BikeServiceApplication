package com.UserActivity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Service.ServiceDetails;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
public class User_management {
	@Value("${database.driver}")
	private String driver; 
	@Value("${database.url}")
	private String url;
	@Value("${database.user_name}")
	private String user_name;
	@Value("${database.pass}")
	private String pass; 
	static Connection con;

	@PostConstruct // it is called by the spring when you create your obj in the bean class first step after creating the obj
	public void init() throws ClassNotFoundException, SQLException { // this declaration for good coding style then only other developer can under stand
		startconnection();
	}
	public void startconnection() throws ClassNotFoundException, SQLException {
		//System.out.println("connected to DB successfully");
			//1..) load the driver
				Class.forName(driver);
				
				//2.) get a connection
				con = (Connection) DriverManager.getConnection(url,user_name,pass);
	}
	
	@RequestMapping("/adduser")
	public  String adduser(HttpServletRequest request) throws SQLException{
		int status=0;
		//System.out.println("im coming here");
			PreparedStatement st=(PreparedStatement) con.prepareStatement("insert into user values (?,?,?)");
			st.setString(1,request.getParameter("mail"));
			st.setString(2,request.getParameter("number"));
			st.setString(3,request.getParameter("user_pass"));

			status=st.executeUpdate();
			if(status>0)
				return "index";
			else
				return "fail";
	}
	
	@RequestMapping("/book_service")
	public String Makebooking(HttpServletRequest request,Model model) {
		//System.out.println("im coming here" + request.getParameter("email"));
		model.addAttribute("email",request.getParameter("email"));
		return "newbooking";
	}
	
	@RequestMapping("/send_book")
	public String send_to_book(HttpServletRequest request,Model model) throws SQLException {
		model.addAttribute("email",request.getParameter("email"));
		//model.addAttribute("id",request.getParameter("id"));
		ServiceDetails u = getRecordById(request.getParameter("id"));
		model.addAttribute("serviceDetail_obj",u);
		return "edit_to_book";
	}
	
	public static ServiceDetails getRecordById(String id) throws SQLException{
		ServiceDetails u=null;
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from service where id="+id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				u=new ServiceDetails();
				u.setService_id(rs.getInt(1));
				if("0".equals(rs.getString(2)))
					u.setGeneral_check("NO");
				else
					u.setGeneral_check(rs.getString(2));
				if("0".equals(rs.getString(3)))
					u.setOil_service("NO");
				else
					u.setOil_service(rs.getString(3));
				if("0".equals(rs.getString(4)))
					u.setWater_wash("NO");
				else
					u.setWater_wash(rs.getString(4));
				u.setTotal(rs.getString(5));
				u.setUser_count(rs.getInt(6));
				//u.setUser_name(rs.getString(7)); // visibility
				u.setAilability(rs.getString(8));
			}
		return u;
	}
	
	public static List<ServiceDetails> getAllRecords() throws SQLException{
		List<ServiceDetails> list=new ArrayList<ServiceDetails>();
		
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from service where visibility = 'yes'and availability = 'yes' order by id");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ServiceDetails s=new ServiceDetails();
				s.setService_id(rs.getInt(1));
				if("0".equals(rs.getString(2)))
					s.setGeneral_check("NO");
				else
					s.setGeneral_check(rs.getString(2));
				if("0".equals(rs.getString(3)))
					s.setOil_service("NO");
				else
					s.setOil_service(rs.getString(3));
				if("0".equals(rs.getString(4)))
					s.setWater_wash("NO");
				else
					s.setWater_wash(rs.getString(4));
				s.setTotal(rs.getString(5));
				s.setUser_count(rs.getInt(6));
				s.setAilability(rs.getString(8));
				list.add(s);
			}
		return list;
	}
	
	@RequestMapping("/service_status")
	public String service_details(HttpServletRequest request,Model model) {
		model.addAttribute("email",request.getParameter("email"));
		return "service_status_page";
	}
	
	@RequestMapping("/previous_bookings")
	public String previous_bookings(HttpServletRequest request,Model model) {
		model.addAttribute("email",request.getParameter("email"));
		return "previous_bookings_page";
	}
	public static List<BookDetail> getAllRecords(String email,String pattern) throws SQLException{
		List<BookDetail> list=new ArrayList<BookDetail>();
		//System.out.println("from get all arg"+email+" and "+pattern);
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from booking where state "+pattern+" and email='"+email+"' order by booking_date");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				BookDetail s=new BookDetail();
				s.setVehicle_no(rs.getString(2));
				s.setBook_date(rs.getString(4));
				s.setDelivery_date(rs.getString(5));
				PreparedStatement ps2=(PreparedStatement) con.prepareStatement("select * from service where id="+rs.getInt(3));
				ResultSet rs2=ps2.executeQuery();
				rs2.next();
				s.setTotal(rs2.getString(5));
				s.setCurr_state(rs.getString(6));
				list.add(s);
			}
		return list;
	}
	
	@PreDestroy
	public void destroy() throws SQLException { // it automatically closed at the end of this file
		//System.out.println("connection successfully closed");
		closeconnection();
	}
	public void closeconnection() throws SQLException {
		//System.out.println("connection successfully closed");
		con.close();
	}
}