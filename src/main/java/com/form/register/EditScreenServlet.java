package com.form.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private final String query = "Select First_Name,Last_Name,Salary,Department_Id from employees where EMPLOYEE_ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		String type = req.getParameter("type");
		if (type != null && !type.isEmpty()) {
	        if (type.equals("employee")) {
	            int EmpId = Integer.parseInt(req.getParameter("EmpId"));
	        	try(Connection con= econnection()){  
	  			  PreparedStatement ps = con.prepareStatement("Select First_Name,Last_Name,Salary,Department_Id from employees where EMPLOYEE_ID=?");
	  			  ps.setInt(1, EmpId);
	  			  ResultSet rs = ps.executeQuery();
	  			 if(rs.next()) {
	  			 pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
	  			 pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
	  			pw.println("<div class='navbar'>");
	  			pw.println("<a href='EmployeeList'>Employees List</a>");
	  			pw.println("</div>");
	  			pw.println("<div class='container-fluid card'>");
	  			pw.println("<div class='card' style='width: 40rem; margin: 4rem auto;'>");
	  			pw.println("<h2  class='card-header  bg-primary text-white text-center'>Edit Employee</h2>");
	  			pw.println("<div class='card-body'>");
	  			pw.println("<form action='editurl?type=emp&EmpId="+EmpId+"' method='post'>");
	  			pw.println("<table class='table table-hover'>");
	  			pw.println("<tr>");
	  			pw.println("<td>First Name</td>");
	  			pw.println("<td><input type='text' name='FirstName' value='"+rs.getString(1)+"'></td>");
	  			pw.println("</tr>");
	  			pw.println("<tr>");
	  			pw.println("<td>Last Name</td>");
	  			pw.println("<td><input type='text' name='LastName' value='"+rs.getString(2)+"'></td>");
	  			pw.println("</tr>");
	  			pw.println("<tr>");
	  			pw.println("<td>Salary</td>");
	  			pw.println("<td><input type='text' name='Salary' value='"+rs.getFloat(3)+"'></td>");
	  			pw.println("</tr>");
	  			pw.println("<tr>");
	  			pw.println("<td>Department Id</td>");
	  			pw.println("<td><input type='text' name='DeptId' value='"+rs.getInt(4)+"'></td>");
	  			  pw.println("</tr>");
	  			  pw.println("<tr>");
	  			  pw.println("<td colspan='2' class='text-center'><input type='submit' value='Edit' class ='btn btn-primary'> <input type='reset' name='Cancel' class ='btn btn-secondary'></td>");
	  			  pw.println("</tr>");
	  			  pw.println("</table>");
	  			  pw.println("</form>");
	  			 }
	  			 else {
	  				 pw.println("No employee found with ID: " + EmpId);			
	  				 }
	  			con.close();  
	  			  
	  			}
	        	catch(Exception e){
					e.printStackTrace();
		            PrintWriter pw1 = res.getWriter();
					pw1.println("<h1>" +e.getMessage()+"</h2>"); 
					System.out.println(e);}
			
		}
	        else if (type.equals("manager")) {
	            int MgrId = Integer.parseInt(req.getParameter("MgrId"));
	        	try(Connection con= econnection()){  
	  			  PreparedStatement ps = con.prepareStatement("Select First_Name,Last_Name,Salary,Department_Id from managers where Manager_Id=?");
	  			  ps.setInt(1, MgrId);
	  			  ResultSet rs = ps.executeQuery();
	  			 if(rs.next()) {
	  			 pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
	  			 pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
	  			pw.println("<div class='navbar'>");
	  			pw.println("<a href='ManagerList'>Manager List</a>");
	  			pw.println("</div>");
	  			pw.println("<div class='container-fluid card'>");
	  			pw.println("<div class='card' style='width: 40rem; margin: 4rem auto;'>");
	  			pw.println("<h2  class='card-header  bg-primary text-white text-center'>Edit Manager</h2>");
	  			pw.println("<div class='card-body'>");
	  			pw.println("<form action='editurl?type=mgr&MgrId="+MgrId+"' method='post'>");
	  			pw.println("<table class='table table-hover'>");
	  			pw.println("<tr>");
	  			pw.println("<td>First Name</td>");
	  			pw.println("<td><input type='text' name='FirstName' value='"+rs.getString(1)+"'></td>");
	  			pw.println("</tr>");
	  			pw.println("<tr>");
	  			pw.println("<td>Last Name</td>");
	  			pw.println("<td><input type='text' name='LastName' value='"+rs.getString(2)+"'></td>");
	  			pw.println("</tr>");
	  			pw.println("<tr>");
	  			pw.println("<td>Salary</td>");
	  			pw.println("<td><input type='text' name='Salary' value='"+rs.getFloat(3)+"'></td>");
	  			pw.println("</tr>");
	  			pw.println("<tr>");
	  			pw.println("<td>Department Id</td>");
	  			pw.println("<td><input type='text' name='DeptId' value='"+rs.getInt(4)+"'></td>");
	  			  pw.println("</tr>");
	  			  pw.println("<tr>");
	  			  pw.println("<td colspan='2' class='text-center'><input type='submit' value='Edit' class ='btn btn-primary'> <input type='reset' name='Cancel' class ='btn btn-secondary'></td>");
	  			  pw.println("</tr>");
	  			  pw.println("</table>");
	  			  pw.println("</form>");
	  			 }
	  			 else {
	  				 pw.println("No employee found with ID: " + MgrId);			
	  				 }
	  			con.close();  
	  			  
	  			}
	        	catch(Exception e){
					e.printStackTrace();
		            PrintWriter pw1 = res.getWriter();
					pw1.println("<h1>" +e.getMessage()+"</h2>"); 
					System.out.println(e);}
			
		}
		}
	 }

	 private Connection econnection() throws SQLException, ClassNotFoundException {
	     Class.forName("oracle.jdbc.driver.OracleDriver");
	     return DriverManager.getConnection("jdbc:oracle:thin:@10.11.2.28:1521:bdrdb", "paws_practice", "cte");
	 }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
