package com.nikesh.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "singh";
    RequestDispatcher dispatcher=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out= response.getWriter();
		 out.print("working");
		 String userName=request.getParameter("name");
		 String uemail=request.getParameter("email");
		 String upassword=request.getParameter("pass");
		 String ucontact=request.getParameter("contact");
		 out.println(userName);
		 out.println(uemail);
		 out.println(upassword);
		 out.println(ucontact);
		 
		 // connection stablish
		 Connection connection = null;
		 try {
			 Class.forName("org.postgresql.Driver");
			 connection = DriverManager.getConnection(URL, USER, PASSWORD); 
		   
			 if (connection != null) {
	                System.out.println("Connected to PostgreSQL database!");
	               // PreparedStatement pst= connection.prepareStatement("create table userdetails(id INT PRIMARY KEY,username varchar(50),useremail varchar(30),userpassword varchar(30),usercontact varchar(15))");	         
	                PreparedStatement pst = connection.prepareStatement(
	                	    "INSERT INTO userdetails(id, username, useremail, userpassword, usercontact) VALUES (?, ?, ?, ?, ?)"
	                	);	                pst.setInt(1, 4);
	                pst.setString(2, userName);
	                pst.setString(3, uemail);
	                pst.setString(4, upassword);
	                pst.setString(5, ucontact);
	                int execute = pst.executeUpdate(); 
	                dispatcher=request.getRequestDispatcher("registration.jsp");
	                if(execute>0) {
	                	request.setAttribute("status", "success");
	        	       System.out.println("data updated  success");
	                }
	                else {
	                	request.setAttribute("status", "failed");
		        	       System.out.println("data updated  failed");
					}
	          dispatcher.forward(request, response);
			 } else {
	                System.out.println("Failed to make connection!");
	            }
		 }catch (Exception e) {
			 System.out.println("Failed while connection stablished"+e); 
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
