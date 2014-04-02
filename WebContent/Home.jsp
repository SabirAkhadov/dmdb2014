<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.HomeServlet" %>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>
<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<%@ page import= "java.sql.Connection" %>
<%@ page import= "java.sql.PreparedStatement" %>

<%@ page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>

<%
final User user = (User) session.getAttribute("user");
MySQLConnection inst = MySQLConnection.getInstance();
Connection conn = inst.getConnection();


if (user != null) {
	// There is a user logged in! Display a greeting!
%>
	Welcome back <%=user.getName()%>
<%
} else {
	// No user logged in.%>
Welcome!
<%
}
%>

<form action="Home.jsp" method="post">
<input type="text" name="test">
<input type="submit" value="go">
</form>

<br />
<br />
See all available
<a href="Cases">cases</a>
and
<a href="PersonsOfInterest">persons of interest</a>
.

<%@ include file="Footer.jsp"%>