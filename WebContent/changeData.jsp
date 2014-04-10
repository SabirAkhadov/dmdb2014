<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.HomeServlet" %>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>
<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<%@ page import= "java.sql.Connection" %>
<%@ page import= "java.sql.PreparedStatement" %>

<%@ page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>

<%
final User user = (User) session.getAttribute("user");
if (user != null) {
	if (session.getAttribute ("ChangeError") == "true") {
		%>
		<font  color="red">Could not change data!</font>	
	<br>	
	<%
		session.setAttribute("ChangeError", null);
	}
%>

<h2>Change user Data</h2>

<form action="changeData" method="post">
	<input type="hidden" name="action" value="changeData" /> 
	<table>
		<tr>
			<th>User Name</th>
			<td><input type="text" name="username" value="" /></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><input type="text" name="email" value="" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="change" />
			</th>
		</tr>
	</table>
</form>

<%} else {
%>
<h2>You have to be logged in.</h2>

<%} 
%>
<%@ include file="Footer.jsp"%>