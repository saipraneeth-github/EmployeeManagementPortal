package com.form.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterForm extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		String FirstName = req.getParameter("FirstName");
		String LastName = req.getParameter("LastName");
		float Salary = Float.parseFloat(req.getParameter("Salary"));
	     int DeptId = Integer.parseInt(req.getParameter("DeptId"));
		
		try{  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			  
			Connection con=DriverManager.getConnection(  
			"jdbc:oracle:thin:@10.11.2.28:1521:bdrdb","paws_practice","cte");  
			  
			Statement stmt=con.createStatement();  
			PrintWriter pw = res.getWriter();
	   		 pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
	    		pw.println("<div class='navbar'>");
	    		pw.println("<a href='EmployeeList'>Employee List</a>");
	    		pw.println("<a href='ManagerList'>Manager List</a>");

	    		pw.println("</div>");
			
			CallableStatement rs = con.prepareCall("{ call add_manager(?,?,?,?,?) }");
			rs.setString(1, FirstName);
			rs.setString(2, LastName);
			rs.setFloat(3, Salary);
			rs.setInt(4,DeptId );
			rs.registerOutParameter(5, Types.VARCHAR);
			rs.execute();
			
			String output = rs.getString(5);
			System.out.println(output);
			pw.println("<h2 class='output'>"+output+"</h2>");
			con.close();  
			  
			}catch(Exception e){
				e.printStackTrace();
	            PrintWriter pw = res.getWriter();
				pw.println("<h1>" +e.getMessage()+"</h2>"); 
				System.out.println(e);}  
		PrintWriter pw = res.getWriter();
		pw.println("<a href='Home.html'>Go back</a>");
		pw.println("<br>");

		
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
