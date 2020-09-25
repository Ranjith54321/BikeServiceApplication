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
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script>
	jQuery(document).ready(function($) {

		$("#submit").click(function(){
			var gen_check = $("#gen_check").val();
			var oil_serv = $("#oil_serv").val();
			var wat_wash = $("#wat_wash").val();
			var avail = $("#avail").val();

			$.ajax({
                type : 'POST',
                data: { gen_check : gen_check,
                	oil_serv : oil_serv,
                	wat_wash : wat_wash,
                	avail : avail },
                url : 'add_new_service',
                success : function(result) {
                    var str = "success";
                    if(str.localeCompare(result)==0){
                    	alert("success");
                    	//$('#form').children('input').val('');
                    	document.getElementById("form").reset();
                        }
                    else
                        alert("fail");
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
	<div class="holder">
	<h1>Enter the Data</h1>
	 	 <form id="form"> 
		 	<table>
				<tr><td>General Check Up Price  : </td>
				<td><input type="text" name="gen_check" id="gen_check"></td></tr>
				<tr><td>Oil Service Price : </td>
				<td><input type="text" name="oil_serv"  id="oil_serv"></td></tr>
				<tr><td>Water Wash Price  : </td>
				<td><input type="text" name="wat_wash" id="wat_wash"></td></tr>
			
				<tr><td>Set Availability : </td>
				<td><input type="text" name="avail" placeholder="yes/no" id="avail"></td></tr>
			
				<tr><td colspan="2"><input type="button" id="submit" value="Enter"> </td></tr>	
			</table>
		</form>  
	</div>
</body>
</html>