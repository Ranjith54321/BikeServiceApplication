<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script type="text/javascript">

	function onclickEvent(id){
		$.ajax({
	        type : 'POST',
	        data: { id : id},
	        url : 'delete_this_service',
	        success : function(result) {
	            var str = "success";
	            if(str.localeCompare(result)==0){
	            	alert("success");
	            	location.reload();
	                }
	            else{
	                alert("fail");
	                location.reload();
	            }
	        }
	    });
		}

</script>
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
	<%@page import="com.UserActivity.*,com.Service.ServiceDetails,java.util.*"%>  
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	 
	 <h1 align="center">Service List</h1> 
	 
	 <%  
	List<ServiceDetails> list=User_management.getAllRecords();  
	request.setAttribute("list",list);  
	%>  
	
	
	<div class="holder" align="center">
	
	<table border="1" width="90%">  
	
	<tr><th>Gen_Check</th><th>Oil_Serv</th><th>Wat_Wash</th>  
	<th>Total</th><th>Book</th></tr>  
	<c:forEach items="${list}" var="u">  
	<tr><td style="text-align:center">${u.getGeneral_check()}</td><td style="text-align:center">${u.getOil_service()}</td>  
	<td style="text-align:center">${u.getWater_wash()}</td><td style="text-align:center">${u.getTotal()}</td> 
	<td style="text-align:center">
	<form action="send_book">
	<input type="hidden" name="email" value="${email}">
	<input type="hidden" name="id" value="${u.getService_id()}">
	<button type="submit">Click</button>
	</form></td>  
	<!--
	<td style="text-align:center">
	<button type="submit" id="delete" onclick="onclickEvent('${u.getService_id()}');">Delete</button>
	</td> -->
	</tr>  
	</c:forEach>  
	
	</table>  
	
	</div>
	 
</body>
</html>