<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<%
final User user = (User) session.getAttribute("user");

String type = (String) session.getAttribute("persType");
type = type == null ? "" : type;
final String fType = type;
int caseID = (Integer)session.getAttribute("lastCase");
%>

<h1>Add person of interest to Case ID <%= caseID %></h1>
<% if(user == null){ %>
You are not authorized to edit cases.
Please log in.
<% }else{ %>


<% }//end else(= user is logged in) %>

<%@ include file="Footer.jsp"%>