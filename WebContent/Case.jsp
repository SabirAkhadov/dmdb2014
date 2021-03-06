<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final Case aCase = (Case)session.getAttribute("case"); 
   final String PAGE_URL = "http://localhost:8080/IntroDBProject";%>

<h1>Case Details</h1>
<%
String errorMsg = (String)session.getAttribute("caseCommentError");
if(errorMsg != null){
%>
<font color="red"><%=errorMsg%></font>
<br /><br />
<%
session.setAttribute("caseCommentError", null);
}
%>
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
<br />
<a href="<%=PAGE_URL%>/CasePersons?id=<%=aCase.getId()%>&status=<%=aCase.getStatus()%>&type=victims">Victims</a><br/>
<a href="<%=PAGE_URL%>/CasePersons?id=<%=aCase.getId()%>&status=<%=aCase.getStatus()%>&type=witnesses">Witnesses</a><br/>
<a href="<%=PAGE_URL%>/CasePersons?id=<%=aCase.getId()%>&status=<%=aCase.getStatus()%>&type=suspects"><%=aCase.getStatus().equals("open") ? "Suspects" : "Perpetrators"%></a><br/>
<a href="<%=PAGE_URL%>/CasePersons?id=<%=aCase.getId()%>&status=<%=aCase.getStatus()%>&type=convicts">Convicts</a><br/>
<a href="<%=PAGE_URL%>/CasePersons?id=<%=aCase.getId()%>&status=<%=aCase.getStatus()%>&type=others">Other Persons of Interest</a><br/>
<br/>
<%
if (user != null) {
	// User is logged in. He can add a comment
%>
	<form action="Case" method="get">
		<input type="hidden" name="action" value="addComment" />
		<input type="hidden" name = "id" value = <%= aCase.getId() %> />
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
Comments:<br/>
<%=	session.getAttribute("commentTable")%>

<%@ include file="Footer.jsp"%>
