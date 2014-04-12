<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); %>
<% final String errorMsg = (String)session.getAttribute("personEditError"); %>

<%if (user == null){
	%>
	<h3> You have to be logged in.</h3>
<%	
}
else {
%>
<h1>Edit person of interest</h1>

<form action="PersonEdit" method="get">
	<input type="hidden" name="action" value="editPerson" /> 
	<table>
		<tr>
			<th>First name</th>
			<td><input type="text" name="firstname" value="" /></td>
		</tr>
		<tr>
			<th>Last name</th>
			<td><input type="text" name="lastname" value="" /></td>
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

<%
}
%>
<%@ include file="Footer.jsp"%>