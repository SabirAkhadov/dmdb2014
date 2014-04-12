<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.NewCaseData"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user");
String type = (String) session.getAttribute("persType");
type = type == null ? "" : type;
String upType = type.isEmpty() ? "" : Character.toUpperCase(type.charAt(0)) + type.substring(1);
int caseID = (int) session.getAttribute("lastCase");
%>

<h1><%=upType%> of Case ID <%= caseID %></h1>

<a href ="Case?id=<%=caseID%>">back</a>
<br/>
<br/>

<% if(session.getAttribute("personsTable") == null){ %>
No such persons of interest registered.
<% }else{ %>
<%= session.getAttribute("personsTable") %>
<% } %>

<%@ include file="Footer.jsp"%>