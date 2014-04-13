<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.NewCaseData"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final String errorMsg = (String)session.getAttribute("newCaseError"); 
	final NewCaseData nCase = errorMsg == null ? null : (NewCaseData)session.getAttribute("newCase");%>

<h1>Open New Case</h1>
<% if(user == null){ %>
You are not authorized to edit cases.
Please log in.
<% }else{ %>

<%if(errorMsg != null){%>
<font color="red"><%=errorMsg%></font>
<br/>
<%session.setAttribute("newCaseError", null);
}%>

<%
	int day,month, year, hours, mins;
	String title,location,description,category;
	
	if(nCase != null){
		day = nCase.getDay();
		month = nCase.getMonth();
		year = nCase.getYear();
		hours = nCase.getHours();
		mins = nCase.getMins();
		
		title = nCase.getTitle() == null ? "" : nCase.getTitle();
		category = nCase.getCategory() == null ? "" : nCase.getCategory();
		location = nCase.getLocation() == null ? "" : nCase.getLocation();
		description = nCase.getDescription() == null ? "" : nCase.getDescription();
	}else{
		Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1; //attention: Calendar's months are numbered 0 through 11
		year = cal.get(Calendar.YEAR);
		hours = cal.get(Calendar.HOUR_OF_DAY);
		mins = cal.get(Calendar.MINUTE);
		
		title = "";
		category = "";
		location = "";
		description = "";
	}
	%>

<div><form action = "NewCase" method = "get" >
	<input type = "hidden" name = "action" value = "newCase">
	
	Title: <input type=text name="title" size=60 value = "<%=title %>"><br/>
	<input type="hidden" name = "status" value = "open"> <%//actually obsolete. Just putting it here so I don't forget it. %>
	Category: <input type=text name="category" value = "<%=category %>"><br/>
	Location: <input type=text name="location" value = "<%=location %>"><br/>
	
	Date: <select name="day">
	<% for(int i = 1; i < 32; i++){ 
		if(i == day){%>
		<option value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> . <select name = "month">
	<% for(int i = 1; i < 13; i++){ 
		if(i == month){%>
		<option value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> . <input type = text name = "year" size = 4 value = "<%=String.format("%04d", year)%>">
	at <select name="hours">
	<% for(int i = 0; i < 24; i++){ 
		if(i == hours){%>
		<option  value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> : <select name="mins">
	<% for(int i = 0; i < 60; i++){ 
		if(i == mins){%>
		<option value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select>
	<br/>
	Description:<br/>
	<textarea rows="4" cols="50" name="description"><%=description %></textarea>
	<br/>
	<input type = submit value = "Submit">
</form></div>
<div><form action = "Home" method = "get">
	<input type = submit value = "Cancel">
</form></div>




<% }//end else(= user is logged in) %>

<%@ include file="Footer.jsp"%>