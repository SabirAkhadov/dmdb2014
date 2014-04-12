<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); %>

<h1>Person of interest details</h1>

<%=session.getAttribute("personsOfInterest")%>

<% 
//edit
if (user != null) {  
%>
	<a href="PersonEdit?id=<%=session.getAttribute("personID")%>">Edit</a>
<%
}
%>
<%@ include file="Footer.jsp"%>
