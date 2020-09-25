<%@page import="com.Service.ServiceDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit User Form</title>
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
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script type="text/javascript">

jQuery(document).ready(function($) {

	$("#submit").click(function(){
		var email = $("#email").val();
		var id = $("#id").val();
		var no = $("#no").val();
		$.ajax({
            type : 'POST',
            data: { email : email,
                id : id,
            	no : no
            	         	 },
            url : 'addBookTable',
            success : function(result) {
                var str = "success";
                if(str.localeCompare(result)==0){
                	alert("successfully updated");
                	//$('#form').children('input').val('');
                	//document.getElementById("form").reset();
                    }
                else
                    alert("fail to update");
            }
        });
		
	});

});

</script>
<style type="text/css">

    	body  
{  
    margin: 0;  
    padding: 0;  
    background-color:#6abadeba;  
    font-family: 'Arial';  
}
</style>
</head>
<body>
	<%@page import="com.Service.ServiceDetails,com.UserActivity.User_management"%>
	
	<%
	//int id= (int)request.getAttribute("id");   
	ServiceDetails u= (ServiceDetails)request.getAttribute("serviceDetail_obj");
	%>  
	<h1 align="center">Book A Service</h1>  
	<div class="holder">
	<input type="hidden" name="email" value="${email}" id="email"> 
	<input type="hidden" name="id" value="<%=u.getService_id() %>" id="id"> 
 	<table align="center">
	
	<tr><td>General Check : </td>
	<td><input type="text" name="gen_check" value="<%= u.getGeneral_check()%>" id="gen_check" readonly></td></tr>
	<tr><td>Oil Service : </td>
	<td><input type="text" name="oil_serv" value="<%= u.getOil_service()%>" id="oil_serv" readonly></td></tr>
	<tr><td>Water Wash : </td>
	<td><input type="text" name="wat_wash" value="<%= u.getWater_wash()%>" id="wat_wash" readonly></td></tr>
	<tr><td>Enter Vehicle Number : </td>
	<td><input type="text" name="no"  id="no"></td></tr>
	
	<tr><td colspan="2"><input type="button" id="submit" value="Enter"> </td></tr>
	</table>
	</div>
</body>
</html>