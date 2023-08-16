package com.form.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	boolean managerdisplayed = false;
	 final String query ="SELECT  m.first_name || ' ' || m.last_name,d.department_name, e.first_name, e.last_name, ed.department_name " +
            "FROM employees e " +
            "INNER JOIN department ed ON e.department_id = ed.department_id " +
            "INNER JOIN managers m ON e.manager_name = m.first_name || ' ' || m.last_name " +
            "INNER JOIN department d ON m.department_id = d.department_id " +
            "WHERE m.first_name || ' ' || m.last_name = ?";
	private List<String> getManagerNames() throws ClassNotFoundException, SQLException {
	     List<String> managerNames = new ArrayList<>();
	     try (Connection con = econnection()) {
	         String sql = "SELECT first_name || ' ' || last_name AS manager_name FROM managers";
	         try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
	             while (rs.next()) {
	                 String manager_name = rs.getString("manager_name");
	                 managerNames.add(manager_name);
	             }
	         }
	     }
	     return managerNames;
	 }

	 private Connection econnection() throws SQLException, ClassNotFoundException {
	     Class.forName("oracle.jdbc.driver.OracleDriver");
	     return DriverManager.getConnection("jdbc:oracle:thin:@10.11.2.28:1521:bdrdb", "paws_practice", "cte");
	 }
	 protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	     List<String> managerNames;
	     try {
	         managerNames = getManagerNames();
	         req.setAttribute("managerNames", managerNames);
	         RequestDispatcher dispatcher = req.getRequestDispatcher("Search.jsp");
	         dispatcher.forward(req, res);
	     } catch (ClassNotFoundException | SQLException e) {
	         e.printStackTrace();
	     }
	 }
	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	     res.setContentType("text/html");
	     PrintWriter pw = res.getWriter();
	     String managerName = req.getParameter("Manager");
	     try (Connection con = econnection()) {
	       PreparedStatement pstmt = con.prepareStatement(query);
		            pstmt.setString(1, managerName);
		            ResultSet rs = pstmt.executeQuery();
	     pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
	     pw.println("<div class='navbar'>");
	     pw.println("<a href='EmployeeList'>Employee List</a>");
	     pw.println("<a href='ManagerList'>Manager List</a>");
	     pw.println("<a href='Home.html'>Add Manager</a>");
	     pw.println("</div>");	     
		 pw.println("</div>");
		  pw.println("<table class='emp_list'>");
		  pw.println("<tr>");
		  pw.println("<th>Manager Name</th>");
		  pw.println("<th>Department Name</th>");
		  pw.println("<th>Employee First Name</th>");
		  pw.println("<th>Employee Last Name</th>");
		  //pw.println("<th>Manager Name</th>");			  
		  pw.println("<th>Employee Department Name</th>");
		  pw.println("</tr>");
		  boolean no_data_found = false;
		                while (rs.next()) {
		                pw.println("<tr>");
		                pw.println("<td>"+rs.getString(1)+"</td>");
		      		     pw.println("<td>"+rs.getString(2)+"</td>");
		  				  pw.println("<td>"+rs.getString(3)+"</td>");
		  				  pw.println("<td>"+rs.getString(4)+"</td>");
		  				  pw.println("<td>"+rs.getString(5)+"</td>");
		  				  //pw.println("<td>"+rs.getString(6)+"</td>");
						  pw.println("</tr>");  
 
		                }
		                
		              
		                con.close();
	         
	     } catch (Exception e) {
	         e.printStackTrace();
	         pw.println("<h1>" + e.getMessage() + "</h2>");
	         System.out.println(e);
	     }

	     pw.println("<a href='SearchServlet'>Go back</a>");
	 }


}
