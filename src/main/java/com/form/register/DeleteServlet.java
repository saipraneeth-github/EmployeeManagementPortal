package com.form.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			
			res.setContentType("text/html");
			PrintWriter pw = res.getWriter();
			 pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
			 pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
			pw.println("<div class='navbar'>");
			pw.println("<a href='EmployeeList'>Employees List</a>");
			pw.println("</div>");
			String type = req.getParameter("type");
	        int Id = 0;
	        String Table_name = "";
	        String Table_id = "";
			if (type != null && !type.isEmpty()) {
		        if (type.equals("employee")) {		
		        	Id = Integer.parseInt(req.getParameter("EmpId"));
		            Table_name = "Employees";
		        	Table_id = "Employee_Id";
		        }
		        else if(type.equals("manager")) {
		        	Id = Integer.parseInt(req.getParameter("MgrId"));
		        	Table_name = "Managers";
		        	Table_id = "Manager_Id";
		        }
			try{  
				Class.forName("oracle.jdbc.driver.OracleDriver");  
				  
				Connection con=DriverManager.getConnection(  
				"jdbc:oracle:thin:@10.11.2.28:1521:bdrdb","paws_practice","cte");  
				CallableStatement rs = con.prepareCall("{ call update_is_deleted(?,?,?,?) }");
				  rs.setInt(1, Id);
				  rs.setString(2,Table_name);
				  rs.setString(3, Table_id);
				  rs.registerOutParameter(4, Types.VARCHAR);
				  rs.executeUpdate();
					String output = rs.getString(4);
					System.out.println(output);
					pw.println("<h2 class='output'>"+output+"</h2>"); 
				 

				
				con.close();  
				  
				}catch(Exception e){
					e.printStackTrace();
		            PrintWriter pw1 = res.getWriter();
					pw1.println("<h1 text_align:center>" +e.getMessage()+"</h2>"); 
					System.out.println(e);}
			}
			
		}
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doGet(req,res);
		}
}
