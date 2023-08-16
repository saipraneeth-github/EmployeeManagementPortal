package com.form.register;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ManagerList")
public class ManagerListServlet extends HttpServlet {
	private final String query = "Select * from managers where is_deleted = 0 order by manager_id";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		try{  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			  
			Connection con=DriverManager.getConnection(  
			"jdbc:oracle:thin:@10.11.2.28:1521:bdrdb","paws_practice","cte");  
			  PreparedStatement ps = con.prepareStatement(query);
			  ResultSet rs = ps.executeQuery();
			  pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
				pw.println("<div class='navbar'>");
				pw.println("<a href='EmployeeRegisterServlet'>Add Employee</a>");
				pw.println("<a href='Home.html'>Add Manager</a>");
				pw.println("<a href='EmployeeList'>Employee List</a>");
				pw.println("</div>");
			  pw.println("<table class='emp_list'>");
			  pw.println("<tr>");
			  pw.println("<th>Manager ID</th>");
			  pw.println("<th>First Name</th>");
			  pw.println("<th>Last Name</th>");
			  pw.println("<th>Salary</th>");
			  pw.println("<th>Department Id</th>");
			  pw.println("<th>Edit</th>");
			  pw.println("<th>Delete</th>");
			  pw.println("</tr>");
			  while(rs.next()) {
				  pw.println("<tr>");
				  pw.println("<td>"+rs.getInt(1)+"</td>");
				  pw.println("<td>"+rs.getString(2)+"</td>");
				  pw.println("<td>"+rs.getString(3)+"</td>");
				  pw.println("<td>"+rs.getFloat(4)+"</td>");
				  pw.println("<td>"+rs.getInt(6)+"</td>");
				  pw.println("<td><a href='editScreen?type=manager&MgrId=" + rs.getString(1) + "'>Edit</a></td>");
				  pw.println("<td><a href='deleteurl?type=manager&MgrId="+rs.getString(1)+"'>Delete</a></td>");

				  pw.println("</tr>");
			  }
			con.close();  
			  
			}catch(Exception e){
				e.printStackTrace();
	            PrintWriter pw1 = res.getWriter();
				pw1.println("<h1>" +e.getMessage()+"</h2>"); 
				System.out.println(e);}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
