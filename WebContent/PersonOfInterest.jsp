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
<%
}
%>
<%@ include file="Footer.jsp"%>
