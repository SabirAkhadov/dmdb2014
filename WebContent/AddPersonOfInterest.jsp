<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<%
final User user = (User) session.getAttribute("user");

String type = request.getParameter("type");
String caseID = request.getParameter("caseID");
String persID = request.getParameter("persID");
String confirm = request.getParameter("confirm");
%>

<% if(user == null){ %>
You are not authorized to edit cases.
Please log in.
<% } else if(type != null && caseID != null && confirm == null){%>
<h1>Add person of interest to Case ID <%= caseID %></h1>

	<%if(persID == null){%>
		Who do you want to add?
		<form method="get" action="AddPersonOfInterest">
		<div>
			<input type="hidden" name="caseID" value="<%=caseID%>"/>
			<input type="hidden" name="type" value="<%=type%>"/>
			First name: <input type="text" name="firstname" /><br>
			Last name: <input type="text" name="lastname" /><br>
			Birthday: <input type="text" name="birthday" /> (Format: YYYY-MM-DD)<br>
			Alive: <select name="alive">
			<option value="">Unknown</option>
			<option value="1">Yes</option>
			<option value="0">No</option></select>
		
			<input type="submit" value="Search" title="Search" /><br><br>
			Note: A person must match ALL the entered data. Empty fields get ignored.<br>Use "%" to match any number of unknown characters and "_" to match exactly one unknown character.
		</div>
		</form>
		
		<hr/>
		<%=session.getAttribute("results")%>
		<hr/>
	<% }else{ %>
		<form method="post" action="AddPersonOfInterest">
		<div>
			<input type="hidden" name="confirm" value="1"/>
			<input type="hidden" name="persID" value="<%=persID%>"/>
			<input type="hidden" name="caseID" value="<%=caseID%>"/>
			<input type="hidden" name="type" value="<%=type%>"/>
			<%if(type.equals("convicts")){//CaseID, PersID, type, sentence, date, enddate%>
			Crime type: <input type="text" name="crime_type"/><br>
			Sentence: <textarea rows="4" cols="50" name="sentence"></textarea><br>
			Date: <input type="text" name="date"/><br>
			End-date: <input type="text" name="enddate"/>
			<%}else if (type.equals("victims")){//CaseID, PersID%>
			
			<%}else if(type.equals("suspects")){//CaseID, PersID, reason%>
			Reason: <textarea rows="4" cols="50" name="reason"></textarea>
			<%}else if(type.equals("witnesses")){//CaseID, PersID%>
			
			<%}else if(type.equals("others")){//CaseID, PersID, reason%>
			Reason: <textarea rows="4" cols="50" name="reason"></textarea>
			<%}%>
			<input type="submit" value="Confirm addition" title="Confirm" />
		</div>
		</form>
	<% } %>

<% }else{ %>
		<%=session.getAttribute("results")%>
<% } %>
<%@ include file="Footer.jsp"%>