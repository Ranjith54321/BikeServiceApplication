package com.OwnerActivity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AutoMail.MailOperation;
import com.Service.ServiceDetails;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
public class Ownerprocess {
	@Value("${database.driver}")
	private String driver; 
	@Value("${database.url}")
	private String url;
	@Value("${database.user_name}")
	private String user_name;
	@Value("${database.pass}")
	private String pass; 
	static Connection con;
	
	static String vehicle_no;
	static String oil_serv;
	static String wat_wash;
	static String gen_check;
	static String total;
	static String email;
	static String phone;
	static String state;
	static String book_date;
	
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
	// ------------------------below business logics ----------------
	@RequestMapping("/Service")
	public String servicepage(HttpServletRequest request) {
		return "service_info_page";
	}
	
	@RequestMapping("/booking_details")
	public String booking_details(HttpServletRequest request) {
		return"booking_details_page";
	}
	public static List<SimpleDetails> getSimplerecords() throws SQLException{
		List<SimpleDetails> list=new ArrayList<SimpleDetails>();
		
		PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from booking order by state");
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			SimpleDetails s=new SimpleDetails();
			s.setVehicle_no(rs.getString(2));
			s.setBooking_date(rs.getString(4));
			s.setState(rs.getString(6));
			s.setService_id(rs.getInt(3));
			list.add(s);
		}
	return list;
	}
	
	@RequestMapping("/update_state")
	public @ResponseBody String update_state(HttpServletRequest request) throws SQLException, ClassNotFoundException {
		String state = request.getParameter("state");
		String vehicle_no = request.getParameter("vehicle_no");
		int status;
		if(state.equals("ready")) {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("update booking set state = 'ready'  where vehicle_no='"+vehicle_no+"'");
			status = ps.executeUpdate();
			
			PreparedStatement ps2 = (PreparedStatement) con.prepareStatement("select * from booking where vehicle_no='"+vehicle_no+"'");
			ResultSet rs2 = ps2.executeQuery();
			rs2.next();
			MailOperation.sendmail(rs2.getString(1), "ready");
		}
		else {
			long millis=System.currentTimeMillis();  
		    java.sql.Date date=new java.sql.Date(millis);
		    DateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
		    String curr_date = df.format(date);
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("update booking set state = 'completed', delivery_date = '"+curr_date+"' where vehicle_no='"+vehicle_no+"'");
			status = ps.executeUpdate();
		}
		if(status>0)
			return "success";
		else
			return "fail";
	}
	@RequestMapping("/more_details")
	public String moreDetails(HttpServletRequest request,Model model) throws SQLException {
		String vehicle_no = request.getParameter("vehicle_no");
		int service_id = Integer.parseInt(request.getParameter("id"));
		FullDetails u = getfulldetails(vehicle_no,service_id);
		//model.addAttribute("id",request.getParameter("id"));
		model.addAttribute("fullDetail_list",u);
		return "more_user_detail_page";
	}
	
	public static FullDetails getfulldetails(String vehicle_no,int id) throws SQLException{
		FullDetails u=null;
			// to get email and bookingDate 
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from booking where vehicle_no='"+vehicle_no+"'");
			ResultSet rs=ps.executeQuery();
			rs.next();
			String email = rs.getString(1);
			String book_date = rs.getString(4);
			String state = rs.getString(6);

			// to get mobile_no
			PreparedStatement ps2=(PreparedStatement) con.prepareStatement("select * from user where email='"+email+"'");
			ResultSet rs2=ps2.executeQuery();
			rs2.next();
			String phone = rs2.getString(2);

			// to get gen_chk, oil_serv, wat_wash, tot
			
			PreparedStatement ps3=(PreparedStatement) con.prepareStatement("select * from service where id="+id);
			ResultSet rs3=ps3.executeQuery();
			rs3.next();
			String gen_check = rs3.getString(2);
			String oil_serv = rs3.getString(3);
			String wat_wash = rs3.getString(4);
			String total = rs3.getString(5);
			

			
			u = new FullDetails();
			u.setVehicle_no(vehicle_no);
			u.setOil_serv(oil_serv);
			u.setWat_wash(wat_wash);
			u.setGen_check(gen_check);
			u.setTotal(total);
			u.setEmail(email);
			u.setPhone(phone);
			u.setState(state);
			u.setBook_date(book_date);
			
			
		return u;
	}
	//...........................
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
