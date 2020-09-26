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
	    var vehicle_no = $("#vehicle_no").val();
	    var state = $("#select-state").val();
		$.ajax({
            type : 'POST',
            data: { vehicle_no : vehicle_no,
                state : state
				 },
            url : 'update_state',
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
	<%@page import="com.OwnerActivity.FullDetails,com.OwnerActivity.Ownerprocess"%>
	
	<%
	FullDetails u= (FullDetails)request.getAttribute("fullDetail_list");
	%>  
	<h1 align="center">Full Vehicle Details</h1>  
		<div class="holder">
		<table>
			<tr><td>Vehicle Number : </td> 
			<td><input type="text" name="gen_check" value="<%= u.getVehicle_no()%>" id="gen_check" readonly></td></tr> <!-- ${u.getVehicle_no()} -->
			<tr><td>Oil Service Price : </td>
			<td><input type="text" name="gen_check" value="<%= u.getOil_serv()%>" id="gen_check" readonly></td></tr> <!-- ${u.getOil_serv()} -->
			<tr><td>Water Wash Price : </td>
			<td><input type="text" name="gen_check" value="<%= u.getWat_wash()%>" id="gen_check" readonly></td></tr> <!-- ${u.getWat_wash()} -->
			<tr><td>General Check Price  : </td>
			<td><input type="text" name="gen_check" value="<%= u.getGen_check()%>" id="gen_check" readonly></td></tr><!--${u.getGen_check()}  -->
			<tr><td>Total Amount : </td>
			<td><input type="text" name="gen_check" value="<%= u.getTotal()%>" id="gen_check" readonly></td></tr><!-- ${u.getTotal()} -->
			<tr><td>Customer Phone Number : </td>
			<td><input type="text" name="gen_check" value="<%= u.getPhone()%>" id="gen_check" readonly></td></tr><!-- ${u.getPhone()} -->
			<tr><td>Customer Email Id : </td>
			<td><input type="text" name="gen_check" value="<%= u.getEmail()%>" id="gen_check" readonly></td></tr><!-- ${u.getEmail()} -->
			<tr><td>Vehicle Booking Date : </td>
			<td><input type="text" name="gen_check" value="<%= u.getBook_date()%>" id="gen_check" readonly></td></tr><!-- ${u.getBook_date()} -->
			<tr><td>Current State Of Service : </td>
			<td><input type="text" name="state" value="<%= u.getState()%>" id="state" readonly></td></tr>
			
			<tr><td>Update The State Of Service : </td>  <!-- if it is not working then use above input tag remove readonly and change the id in above JS code -->
			<td><select name="select-state" id="select-state">
			<option value="Pending">Pending</option>
			<option value="Ready">Ready For Delivery</option>
			<option value="Completed">Completed</option>
			</select></td></tr>
			
			<tr><td><input type="hidden" name="vehicle_no" value="<%= u.getVehicle_no()%>" id="vehicle_no"></d>
			<td colspan="2"><input type="button" id="submit" value="Update"> </td></tr>
		</table>
	</div>
</body>
</html>
