<%@ page import="ch.ethz.inf.dbproject.RegisterServlet" %>
<%@ page import="ch.ethz.inf.dbproject.UserServlet" %>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>


<%
if (session.getAttribute("error")!= null && session.getAttribute("error").equals("none")){
	%>
	<h2> You successfully registered a new User.</h2>
<%	
	session.setAttribute("error", null);
}
else {
	if (session.getAttribute("error")!= null && session.getAttribute("error").equals("emptyString")) {
%>
	<font  color="red">Empty String!</font>		
		<br>
	<%	
		session.setAttribute("error", null);
	}	
	%>
<% 
	if (session.getAttribute("error")!= null && session.getAttribute("error").equals("register")){
%>
	<font  color="red">The name is already registered!</font>	
	<br>	
	<%	
		session.setAttribute("error", null);
	}	
	%>
<h2>Register a new user</h2>


<form action="Register" method="post">
	<input type="hidden" name="action" value="login" /> 
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
				<input type="submit" value="Register" />
			</th>
		</tr>
	</table>
</form>

<%
} // else
%>
<%@include file = "Footer.jsp" %>
