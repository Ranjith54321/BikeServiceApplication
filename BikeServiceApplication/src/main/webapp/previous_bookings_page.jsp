<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">



@import url(https://fonts.googleapis.com/css?family=Open+Sans:400,600,700);

/* General Buttons */
button {
  width: 150px;
  height: 50px;
  background: linear-gradient(to bottom, #4eb5e5 0%,#389ed5 100%); /* W3C */
  border: none;
  border-radius: 5px;
  position: relative;
  border-bottom: 4px solid #2b8bc6;
  color: #fbfbfb;
  font-weight: 600;
  font-family: 'Open Sans', sans-serif;
  text-shadow: 1px 1px 1px rgba(0,0,0,.4);
  font-size: 15px;
  text-align: left;
  text-indent: 5px;
  box-shadow: 0px 3px 0px 0px rgba(0,0,0,.2);
  cursor: pointer;

/* Just for presentation */  
  display: block;
  margin: 0 auto;
  margin-bottom: 20px;
}
button:active {
  box-shadow: 0px 2px 0px 0px rgba(0,0,0,.2);
  top: 1px;
}

button:after {
  content: "";
  width: 0;
  height: 0;
  display: block;
  border-top: 20px solid #187dbc;
  border-bottom: 20px solid #187dbc;
  border-left: 16px solid transparent;
  border-right: 20px solid #187dbc;
  position: absolute;
  opacity: 0.6; 
  right: 0;
  top: 0;
  border-radius: 0 5px 5px 0;  
}



/* Presentation stuff */
.holder {
  width: 400px;
  background: #efefef;
  padding: 30px 10px;
  box-sizing: border-box;
  margin: 0 auto;
  margin-top: 20px; 
  text-align: center; 

}

h1 {
  font: 400 16px 'Open Sans';
  text-transform: uppercase;
  color: #999;
  text-shadow: 1px 1px 1px #fff;
  margin-bottom: 30px;
} 

body {
 background-image: url("https://images.unsplash.com/photo-1525207106105-b340f7384b30?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
 background-size: cover;
 background-attachment: fixed;
}


</style>
</head>
<body>
	<%@page import="com.UserActivity.User_management,com.UserActivity.BookDetail,com.Service.ServiceDetails,java.util.*"%>  
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
	<%  
	String pattern = "='completed'";
	String mail = (String)request.getAttribute("email");
	System.out.println("jsp print "+mail);
	List<BookDetail> list=User_management.getAllRecords(mail,pattern);  
	request.setAttribute("list",list);  
	%>  

	
	<div class="holder">
	 <c:forEach items="${list}" var="u"> 
		<table>
			<tr><td>Vehicle No : </td>
			<td>${u.getVehicle_no()}</td></tr>
			<tr><td>Booking Date : </td> 
			<td>${u.getBook_date()}</td></tr>
			<tr><td>Delivery Date : </td> 
			<td>${u.getDelivery_date()}</td></tr>
			<tr><td>Total Amount : </td>
			<td>${u.getTotal()}</td></tr>
			<tr><td>Current State : </td> 
			<td>${u.getCurr_state()}</td></tr>
			</table>
			<br><br>
			</c:forEach> 		   			
	</div>
	

</body>
</html>