package com.form.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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

@WebServlet("/EmployeeRegisterServlet")
public class EmployeeRegistrationServlet extends HttpServlet {

 protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
     List<String> managerNames;
     try {
         managerNames = getManagerNames();
         req.setAttribute("managerNames", managerNames);
         RequestDispatcher dispatcher = req.getRequestDispatcher("Home.jsp");
         dispatcher.forward(req, res);
     } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
     }
 }

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

 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
     res.setContentType("text/html");
     PrintWriter pw = res.getWriter();
     pw.println("<link rel='stylesheet' type='text/css' href='Employee_List.css'>");
     pw.println("<div class='navbar'>");
     pw.println("<a href='EmployeeList'>Employee List</a>");
     pw.println("<a href='ManagerList'>Manager List</a>");
     pw.println("<a href='Home.html'>Add Manager</a>");
     pw.println("<a href='SearchServlet'>Manager Tree</a>");

     pw.println("</div>");

     String FirstName = req.getParameter("FirstName");
     String LastName = req.getParameter("LastName");
     float Salary = Float.parseFloat(req.getParameter("Salary"));
     String Manager = req.getParameter("Manager");
     int DeptId = Integer.parseInt(req.getParameter("DeptId"));

     try (Connection con = econnection()) {
         CallableStatement rs = con.prepareCall("{call add_employee(?,?,?,?,?,?) }");
         rs.setString(1, FirstName);
         rs.setString(2, LastName);
         rs.setFloat(3, Salary);
         rs.setString(4, Manager);
         rs.setInt(5, DeptId);
         rs.registerOutParameter(6, Types.VARCHAR);
         rs.execute();

         String output = rs.getString(6);
         System.out.println(output);
         pw.println("<h2 class='output'>" + output + "</h2>");
     } catch (Exception e) {
         e.printStackTrace();
         pw.println("<h1>" + e.getMessage() + "</h2>");
         System.out.println(e);
     }

     pw.println("<a href='EmployeeRegisterServlet'>Go back</a>");
     pw.println("<br>");
 }
}
