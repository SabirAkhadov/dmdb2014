<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.PersonOfInterest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% 


	if (session.getAttribute("PersonEdited") == "true"){
	%>
	<font color="green">Person edited</font>
	<% 
	session.setAttribute("PersonEdited", null);
	}
	if (session.getAttribute("PersonCommented") == "false"){
	%>
		<font color="red">There was an error with adding note.<br>Notes cannot be empty.</font>
	<% 
	session.setAttribute("PersonCommented", null);
	}
	%>
<h1>Person of interest details</h1>

<%=session.getAttribute("personsOfInterest")%>

<% 
//edit
if (user != null) {  
	PersonOfInterest p = (PersonOfInterest)(session.getAttribute("personToEdit"));
%>
	<a href = "AddPersonOfInterest?persRelatedID=<%=p.getId()%>&type=relationship">Add relative</a> <br>
	<a href="PersonEdit?id=<%=p.getId()%>">Edit</a>
	
	<form action="PersonOfInterest" method="get">
		<input type="hidden" name="action" value="addNotes" />
		<input type="hidden" name = "id" value = <%=((PersonOfInterest)(session.getAttribute("personToEdit"))).getId()%> />
		Add notes
		<br />
		<textarea rows="4" cols="50" name="personNotes"></textarea>
		<br />
		<input type="submit" value="Add note" />
	</form>
<%
}
%>
<%@ include file="Footer.jsp"%>
