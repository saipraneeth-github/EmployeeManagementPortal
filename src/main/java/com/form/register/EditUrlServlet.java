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


@WebServlet("/editurl")
public class EditUrlServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		String type = req.getParameter("type");
        int Id = 0;
        String Table_name = "";
        String Table_id = "";
		if (type != null && !type.isEmpty()) {
	        if (type.equals("empp")) {		
	        	Id = Integer.parseInt(req.getParameter("EmpId"));
	            Table_name = "Employees";
	        	Table_id = "Employee_Id";
	        }
	        else if(type.equals("mgr")) {
	        	Id = Integer.parseInt(req.getParameter("MgrId"));
	        	Table_name = "Managers";
	        	Table_id = "Manager_Id";
	        }
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
	    		pw.println("<a href='EmployeeRegisterServlet'>Employee Registration</a>");
	    		pw.println("<a href='ManagerList'>Manager List</a>");
	    		pw.println("<a href='Home.html'>Manager Registration</a>");
	    		pw.println("</div>");
			
	    		CallableStatement rs = con.prepareCall("{ ? = call update_table(?, ?, ?, ?, ?, ?, ?) }");
	    		rs.registerOutParameter(1, Types.VARCHAR);
	    		rs.setString(2, Table_name);
	    		rs.setString(3, Table_id);
	    		rs.setInt(4, Id);
	    		rs.setString(5, FirstName);
	    		rs.setString(6, LastName);
	    		rs.setFloat(7, Salary);
	    		rs.setInt(8, DeptId);
	    		rs.execute();

			
			
			String output = rs.getString(1);
			System.out.println(output);
			pw.println("<h2 class='output'>"+output+"</h2>");
			con.close();  
			  
			}catch(Exception e){
				e.printStackTrace();
	            PrintWriter pw = res.getWriter();
				pw.println("<h1>" +e.getMessage()+"</h2>"); 
				System.out.println(e);
				}  

		}

	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}

