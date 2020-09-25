<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">
    	body  
{  
    margin: 0;  
    padding: 0;  
    background-color:#6abadeba;  
    font-family: 'Arial';  
}
body {
background-color:#6abadeba;

}
</style>
</head>
<body>
<body>
	<%@page import="com.OwnerActivity.Ownerprocess,com.OwnerActivity.SimpleDetails,java.util.*"%>  
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	 
	 <h1 align="center">Service List</h1> 
	 
		 <%
	 		List<SimpleDetails> list = Ownerprocess.getSimplerecords();  
 	 	 	request.setAttribute("list",list);
 	 	 %>  
	
	<div class="holder" align="center">
	
	<table border="1" width="90%">  
	
	<tr><th>Vehicle Number</th><th>Booking Date</th><th>State</th><th>More Details</th></tr>  
	<c:forEach items="${list}" var="u">  
	<tr><td style="text-align:center">${u.getVehicle_no()}</td><td style="text-align:center">${u.getBooking_date()}</td><td style="text-align:center">${u.getState()}</td>   
	
	<td style="text-align:center">
	<form action="more_details">
	<input type="hidden" name="vehicle_no" value="${u.getVehicle_no()}">
	<input type="hidden" name="id" value="${u.getService_id()}">
	<button type="submit">Click</button>
	</form></td>  
	</tr>  
	</c:forEach>  
	
	</table>  
	
	</div>
	 
</body>
</html>