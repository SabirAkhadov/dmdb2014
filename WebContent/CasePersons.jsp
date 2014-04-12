<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.NewCaseData"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user");
String type = (String) session.getAttribute("persType");
type = type == null ? "" : type;
final String fType = type;
String upType = type.isEmpty() ? "" : Character.toUpperCase(type.charAt(0)) + type.substring(1);
int caseID = (Integer)session.getAttribute("lastCase");
final String PAGE_URL = "http://localhost:8080/IntroDBProject";
%>

<h1><%=upType%> of Case ID <%= caseID %></h1>

<a href ="<%=PAGE_URL%>/Case?id=<%=caseID%>">back</a>
<br/>
<br/>

<% if(user != null){ %>
<form action = "AddPersonOfInterest" method = "get" >
	<input type="hidden" name="caseID" value ="">
	<input type="hidden" name="type" value = "<%=fType%>">
	<input type="submit" value="Add New">
</form>
}
<%
if((String)session.getAttribute("noPersons") != null){ %>
No such persons of interest registered.
<% 
session.setAttribute("noPersons", null);
}else{%>
<%= session.getAttribute("personsTable") %>
<%}%>
<%@ include file="Footer.jsp"%>