# BikeServiceApplication

     -> This application is for owners of Bike service station. 
     -> It helps he owners to list the service they offer. 
     -> Customers can choose one or more services to book.
     -> Customer can register and login using E-mail
    
    
  ## Application Specifications :
    
   ### Bike Station Owner :
    
        - Can able to create / edit / delete all his service and their details
        
        - Can View a list of all bookings (pending, ready for delivery and completed)
        
        - Can View details of each booking
        
        - Mark a booking as ready for delivery
        
        - Mark a booking as completed
        
        - Receive an email whenever a booking is made
        
   ### Customers :
    
          - Can able to register for an account with his email address and mobile number
          
          - Can Book a service at a particular date
          
          - Can see the status of his/her booking
          
          - Can see all his/her previous bookings
          
          - Receive an email as soon as booking is ready for devlivery
          
   ## Technology Stack Used :
   
          FrameWork ->  Spring MVC 
          Front-End -> JSP,JSTL,HTML,CSS,JavaScript(JQuery,AJAX).
          Back-End  -> Java (Programming Language)
          DataBase  -> MySql
          Project-Management-Tool -> Maven
          
   ## DataBase Schema :
    
                mysql> use workshop;
                mysql> show tables;
                +--------------------+
                | Tables_in_workshop |
                +--------------------+
                | booking            |
                | service            |
                | user               |
                +--------------------+
                
                This application has Three tables 
                  
                    1.) Booking - to handle all booking Details and history
                    2.) service - to handle service data
                    3.) user - to handle user data
                    
             In order to handle owner data I make property file it is much convenient than table data
             
   ## To make Database-Tables DataBase Comments :
         
            1.) create Database :
              
                create database workshop;
                
            2.) create Tables :
            
                2.1 ) create service table :
                
                    create table service(id int NOT NULL AUTO_INCREMENT,general_check varchar(5),oil_change varchar(5),water_wash varchar(5),total varchar(10),user_count int(5),visibility varchar(5),availability varchar(5),PRIMARY KEY (id));
                    
                2.2 ) create booking table :
                    
                    create table booking(email varchar(30),vehicle_no varchar(15),service_id int,booking_date varchar(30),delivery_date varchar(30),state varchar(15));
                
                2.3) create user table : 
                
                    create table user(email varchar(30),mobile varchar(15),pass varchar(15));
                    
        Then make changes in peroperites file in the project.       
        
        
  ## Mail Functionality :
  
   Turn Less secure app access in Gmail owner gmail : [by click this ](https://myaccount.google.com/u/1/security)
