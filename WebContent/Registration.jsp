<%@page import="ch.ethz.inf.dbproject.UserServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<h2>Register a new user</h2>

<form action="User" method="get"> <!-- this should be register method -->
	<input type="hidden" name="action" value="login" /> 
	<table>
		<tr>
			<th>Name</th>
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
<%@include file = "Footer.jsp" %>
