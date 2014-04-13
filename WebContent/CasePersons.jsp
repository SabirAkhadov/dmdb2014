<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.NewCaseData"%>
<%@ page import="ch.ethz.inf.dbproject.model.DatastoreInterface"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user");
String type = (String) session.getAttribute("persType");
final String originalType = type == null ? "" : type;
final String caseStatus = (String) session.getAttribute("status");
type = type.equals("suspects") ? (caseStatus.equals("open") ? "suspects" : "perpetrators") : (type.equals("others") ? "other Persons of Interst" : type);
String upType = type.isEmpty() ? "" : Character.toUpperCase(type.charAt(0)) + type.substring(1);
int caseID = (Integer)session.getAttribute("lastCase");
final String PAGE_URL = "http://localhost:8080/IntroDBProject";
final DatastoreInterface dsi = new DatastoreInterface();
final Case c = dsi.getCaseById(caseID);
%>

<h1><%=upType%> of Case <br/><i><%= c.getTitle() %></i></h1>

<a href ="<%=PAGE_URL%>/Case?id=<%=caseID%>">back</a>
<br/>
<br/>

<% if(user != null && caseStatus.equals("open")){ %>
<form action = "AddPersonOfInterest" method = "get" >
	<input type="hidden" name="caseID" value ="<%=caseID%>">
	<input type="hidden" name="type" value ="<%=originalType%>">
	<input type="submit" value="Add New">
</form>
<%}%>

<%
if((String)session.getAttribute("noPersons") != null){ %>
No such persons of interest registered.
<% 
session.setAttribute("noPersons", null);
}else{%>
<%= session.getAttribute("personsTable") %>
<%}%>
<%@ include file="Footer.jsp"%>