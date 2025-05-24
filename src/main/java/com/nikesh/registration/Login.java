package com.nikesh.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

 
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "singh";  
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		 String uemail= request.getParameter("username");
		 String upassword= request.getParameter("password");
		 HttpSession session= request.getSession();
		 RequestDispatcher dispatcher=null;
		 try {
				Class.forName("org.postgresql.Driver");
				Connection connection= DriverManager.getConnection(URL,USER,PASSWORD);
				PreparedStatement pStatement=connection.prepareStatement("select * from userdetails where useremail = ? and userpassword = ?");
				pStatement.setString(1, uemail);	
				pStatement.setString(2, upassword);		
			
				ResultSet rs = pStatement.executeQuery();
				if(rs.next()) {
					session.setAttribute("name", rs); 
					dispatcher = request.getRequestDispatcher("index.jsp");
				}else {
					
					request.setAttribute("status", "failed");
					dispatcher = request.getRequestDispatcher("login.jsp");
					
				}
				dispatcher.forward(request, response);
			}catch (Exception e) {
				e.printStackTrace(); 
			}
	
	}

}
