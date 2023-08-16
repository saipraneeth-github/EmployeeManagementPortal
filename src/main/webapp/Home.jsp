<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel='stylesheet' type='text/css' href='Employee_List.css'>
</head>

<body>
<div class='navbar'>
	<a href='EmployeeList'>Employees List</a>
	<a href='ManagerList'>Manager List</a>
	<a href='Home.html'>Add Manager</a>
	<a href='SearchServlet'>Manager Tree</a>
	
</div>
<div class="container-fluid card">
<div class="card" style="width: 40rem; margin: 4rem auto;" >
<h2  class="card-header  bg-primary text-white text-center">Employee Registration</h2>
<div class="card-body">
<form action="EmployeeRegisterServlet" method="post">
<table class="table table-hover">
<tr>
<td>First Name</td>
<td><input type="text" name="FirstName" required></td>
</tr>
<tr>
<td>Last Name</td>
<td><input type="text" name="LastName" required></td>
</tr>
<tr>
<td>Salary</td>
<td><input type="text" name="Salary" required></td>
</tr>
<tr>
<td>Manager</td>
 <td>
 <select name="Manager" required>
<option value="">Select Manager</option>
<c:forEach items="${managerNames}" var="managerName">
<option value="${managerName}">${managerName}</option>
</c:forEach>
</select>
</td>
</tr>
<tr>
<td>Department Id</td>
<td>
<select name="DeptId" required>
<option value="">Select Department Id</option>
    <option value="5">5</option>
    <option value="6">6</option>
    <option value="7">7</option>
  </select>
</td>
</tr>
<tr>
<td colspan="2" class="text-center"><input type="submit" value="Register" class ="btn btn-primary">
<input type="reset" name="Cancel" class ="btn btn-secondary"></td>
</tr>

</table>
</form>
</div>
</div>
</div>
</body>
</html>