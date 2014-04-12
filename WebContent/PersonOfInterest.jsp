<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.PersonOfInterest"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); 


	if (session.getAttribute("PersonEdited") == "true"){
	%>
		
	<font color="green">Person edited</font>
	<% 
	session.setAttribute("PersonEdited", null);
	}
	%>
<h1>Person of interest details</h1>

<%=session.getAttribute("personsOfInterest")%>

<% 
//edit
if (user != null) {  
%>
	<a href="PersonEdit?id=<%=((PersonOfInterest)(session.getAttribute("personToEdit"))).getId()%>">Edit</a>
	
	<form action="PersonOfInterest" method="get">
		<input type="hidden" name="action" value="addNotes" />
		Add notes
		<br />
		<textarea rows="4" cols="50" name="personNotes"></textarea>
		<br />
		<input type="submit" value="Submit" />
	</form>
<%
}
%>
<%@ include file="Footer.jsp"%>
