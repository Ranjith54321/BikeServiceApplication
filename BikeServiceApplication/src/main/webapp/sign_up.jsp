<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<!DOCTYPE html>    
<html>    
<head>    
    <title>Sign Up Page</title>    
    <style>
    	body  
{  
    margin: 0;  
    padding: 0;  
    background-color:#6abadeba;  
    font-family: 'Arial';  
}  
.login{  
        width: 382px;  
        overflow: hidden;  
        margin: auto;  
        margin: 20 0 0 450px;  
        padding: 80px;  
        background: #23463f;  
        border-radius: 15px ;  
          
}  
h2{  
    text-align: center;  
    color: #277582;  
    padding: 20px;  
}  
label{  
    color: #08ffd1;  
    font-size: 17px;  
}  
#Uname{  
    width: 300px;  
    height: 30px;  
    border: none;  
    border-radius: 3px;  
    padding-left: 8px;  
}  
#Pass{  
    width: 300px;  
    height: 30px;  
    border: none;  
    border-radius: 3px;  
    padding-left: 8px;  
      
}  
#log{  
    width: 300px;  
    height: 30px;  
    border: none;  
    border-radius: 17px;  
    padding-left: 7px;  
    color: blue;  
  
  
}  
span{  
    color: white;  
    font-size: 17px;  
}  
a{  
    float: right;  
    background-color: grey;  
}
    </style>

</head>    
<body>    
    <h2>Register to Login</h2><br>    
    <div class="login">    
    <form action="adduser" method="post">    
        <label><b>Email Id   <br>  
        </b>    
        </label>    
        <input type="text" name="mail" id="Uname" placeholder="Email-Id">    
        <br><br>    
        <label><b>Mobile Number   
        </b>    
        </label>    
        <input type="text" name="number" id="Uname" placeholder="Mobile Number">    
        <br><br>
        <label><b>Password     
        </b>    
        </label>    
        <input type="Password" name="user_pass" id="Pass" placeholder="Password">    
        <br><br>    
        <input type="submit" name="log" id="log" value="LogIn"> 
        <br><br>       
    </form>     
</div>    
</body>    
</html>  