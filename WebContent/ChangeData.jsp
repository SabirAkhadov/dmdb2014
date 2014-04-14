<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.HomeServlet" %>
<%@ page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>
<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<%@ page import= "java.sql.Connection" %>
<%@ page import= "java.sql.PreparedStatement" %>

<%@ page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>

<%
if (user != null) {
	
	if (session.getAttribute("ChangeError") == "none") {
		%>
		<h2> Data changed successfully.</h2>
	<%
	session.setAttribute("ChangeError", null);
	return;
	}
	
	if (session.getAttribute ("ChangeError") == "true") {
		%>
		<font  color="red">Could not change data!</font>
		<font  color="red">No empty Strings or existing user names!</font>
			
	<br>	
	<%
		session.setAttribute("ChangeError", null);
	}
%>

<h2>Change user Data</h2>

<form action="ChangeData" method="post">
	<input type="hidden" name="action" value="changeData" /> 
	<table>
		<tr>
			<th>User Name</th>
			<td><input type="text" name="username" value="<%=user.getName() %>" /></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><input type="text" name="email" value="<%=user.getEmail() %>" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="change" />
			</th>
		</tr>
	</table>
</form>

<%} 
else {
%>
<h2>You have to be logged in.</h2>

<%} 
%>
<%@ include file="Footer.jsp"%>