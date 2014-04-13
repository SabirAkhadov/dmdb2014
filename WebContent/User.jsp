<%@ page import="ch.ethz.inf.dbproject.UserServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<h2>Your Account</h2>

<% 
if (session.getAttribute("user") != null) {
	// User is logged in. Display the details:
%>
<%= session.getAttribute(UserServlet.SESSION_USER_DETAILS) %>
	<br>
	<a href="ChangeData">Change user data</a>
	
	<h2> Cases managed by user</h2>
<%= session.getAttribute("userCases") %>

<%

} else {
	//User not logged in
	if (session.getAttribute("error")!= null && session.getAttribute("error").equals("login")) {
		%>
			<font  color="red">Wrong name or password!</font>		
				<br>
			<%	
				session.setAttribute("error", null);
			}	
			%>


	<form action="User" method="post">
	<input type="hidden" name="action" value="login" />
	<table>
		<tr>
			<th>Username</th>
			<td><input type="text" name="username" value="" /></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="Login" />
			</th>
			<th>
				<a href="Register">Register</a>
			</th>
		</tr>
	</table>
	</form>

<%
}
%>

<%@ include file="Footer.jsp" %>