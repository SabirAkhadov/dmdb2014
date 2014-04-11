<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); %>
<% final Case aCase = (Case)session.getAttribute("case"); %>

<h1>Person of interest details</h1>

<%=session.getAttribute("personsOfInterest")%>

<%@ include file="Footer.jsp"%>
