package com.Service;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
public class ServiceOperations {
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
	// ------------------------below business logics ----------------
	@RequestMapping("/insert_service")
	public String insert_new_service(HttpServletRequest request) {
		return "insert_new_service";
	}
	
	@RequestMapping("/add_new_service")
	public @ResponseBody String add_new_service(HttpServletRequest request) throws SQLException {
		int status=0;

			PreparedStatement st=(PreparedStatement) con.prepareStatement("insert into service(general_check,oil_change,water_wash,total,user_count,visibility,availability) values(?,?,?,?,?,?,?);");
			st.setString(1,request.getParameter("gen_check"));
			st.setString(2,request.getParameter("oil_serv"));
			st.setString(3,request.getParameter("wat_wash"));
			int tot = Integer.parseInt(request.getParameter("gen_check")) + Integer.parseInt(request.getParameter("oil_serv")) + Integer.parseInt(request.getParameter("wat_wash"));
			st.setString(4,Integer.toString(tot));
			st.setInt(5,0);
			st.setString(6,"yes");
			st.setString(7,request.getParameter("avail"));
			
			status=st.executeUpdate();
			if(status>0)
				return "success";
			else
				return "fail";
	}
	public static List<ServiceDetails> getAllRecords() throws SQLException{
		List<ServiceDetails> list=new ArrayList<ServiceDetails>();
		
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from service where visibility = 'yes' order by id");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ServiceDetails s=new ServiceDetails();
				s.setService_id(rs.getInt(1));
				s.setGeneral_check(rs.getString(2));
				s.setOil_service(rs.getString(3));
				s.setWater_wash(rs.getString(4));
				s.setTotal(rs.getString(5));
				s.setUser_count(rs.getInt(6));
				s.setAilability(rs.getString(8));
				list.add(s);
			}
		return list;
	}
	public static ServiceDetails getRecordById(String id) throws SQLException{
		ServiceDetails u=null;
			PreparedStatement ps=(PreparedStatement) con.prepareStatement("select * from service where id="+id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				u=new ServiceDetails();
				u.setService_id(rs.getInt(1));
				u.setGeneral_check(rs.getString(2));
				u.setOil_service(rs.getString(3));
				u.setWater_wash(rs.getString(4));
				u.setTotal(rs.getString(5));
				u.setUser_count(rs.getInt(6));
				//u.setUser_name(rs.getString(7)); // visibility
				u.setAilability(rs.getString(8));
			}
		return u;
	}
	
	@RequestMapping("/edit_this_service")
	public String edit_this_service(HttpServletRequest request,Model model) throws SQLException {
		ServiceDetails u = getRecordById(request.getParameter("id"));
		model.addAttribute("id",request.getParameter("id"));
		model.addAttribute("serviceDetail_obj",u);
		return "edit_service_form";
	}
	
	@RequestMapping("/edit_service_data")
	public @ResponseBody String update(HttpServletRequest request) throws SQLException{
			int status=0;
			//System.out.println("from edit user controller");
			PreparedStatement st=(PreparedStatement) con.prepareStatement("update service set general_check=?,oil_change=?,water_wash=?,total=?,availability=? where id=?");
			st.setString(1,request.getParameter("gen_check"));
			st.setString(2,request.getParameter("oil_serv"));
			st.setString(3,request.getParameter("wat_wash"));
			int tot = Integer.parseInt(request.getParameter("gen_check")) + Integer.parseInt(request.getParameter("oil_serv")) + Integer.parseInt(request.getParameter("wat_wash"));
			st.setString(4,Integer.toString(tot));
			st.setString(5,request.getParameter("avail"));
			st.setInt(6,Integer.parseInt(request.getParameter("id")));

			status = st.executeUpdate();
		
			if(status>0)
				return "success";
			else
				return "fail";
	}
	
	
	@RequestMapping("/delete_this_service")
	public @ResponseBody String delete_this_service(HttpServletRequest request) throws SQLException {
		int status=0;
		String id = request.getParameter("id");
		String query1 = "select * from service where id="+id;
		PreparedStatement ps1=(PreparedStatement) con.prepareStatement(query1);
		ResultSet rs = ps1.executeQuery();
		rs.next();
		int user_count = rs.getInt(6);
		if(user_count==0) {
			String query = "delete from service where id="+id;
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			status = ps.executeUpdate();
		}else {
			//String no = "no";
			String query = "update service set visibility ='no' where id = "+id;
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(query);
			status = ps.executeUpdate();
		}
		
		if(status>0)
			return "success";
		else
			return "fail";
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
