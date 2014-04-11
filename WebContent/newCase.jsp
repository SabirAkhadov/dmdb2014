<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); 
	if (session.getAttribute("openCaseStatus") == "true") {
%>
		<h2>Case opened</h2>
	<% 
	session.setAttribute("openCaseStatus", null);
	}
	if (session.getAttribute("openCaseStatus") == "false") {
	%>
		<font  color="red">Case couldn't be opened!</font>	
	<%
	session.setAttribute("openCaseStatus", null);
	}
%>

<h1>Open new case</h1>

<form action="newCase" method="get">
	<input type="hidden" name="action" value="newCase" /> 
	<table>
		<tr>
			<th>Title</th>
			<td><input type="text" name="title" value="" /></td>
		</tr>
		<tr>
			<th>Date</th>
			<td><input type="text" name="date" value="" /></td>
		</tr>
		<tr>
			<th>Time</th>
			<td><input type="text" name="time" value="" /></td>
		</tr>
		<tr>
			<th>Description</th>
			<td><input type="text" name="description" value="" /></td>
		</tr>
		<tr>
			<th>Location</th>
			<td><input type="text" name="location" value="" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="Open Case" />
			</th>
		</tr>
	</table>
</form>

<%@ include file="Footer.jsp"%>
