<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); %>
<% final Case aCase = (Case)session.getAttribute("case"); %>

<h1>Case Details</h1>

<%=session.getAttribute("caseTable")%>

<%
if(user != null) {
	//if user is logged in, he may edit
%>
		<form action="Case" method="get">
			<input type ="hidden" name = action value = "editCase"/>
			<input type ="hidden" name = id value = "<%= aCase.getId() %>" />
			<input type ="hidden" name = user_id value="<%= user.getUserID() %>" />
			
			<input type="submit" value="Edit" />
		</form>
<%		
	}
%>

<br/>
<%
if (user != null) {
	// User is logged in. He can add a comment
%>
	<form action="Case" method="get">
		<input type="hidden" name="action" value="addComment" />
		<input type="hidden" name = "caseID" value = <%= aCase.getId() %> />
		<input type="hidden" name="userID" value="<%= user.getUserID() %>" />
		Add Comment
		<br />
		<textarea rows="4" cols="50" name="comment"></textarea>
		<br />
		<input type="submit" value="Submit" />
	</form>
<%
}
%>

<%=	session.getAttribute("commentTable")%>

<%@ include file="Footer.jsp"%>
