<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final Case aCase = session.getAttribute("updateError") == null ? (Case)session.getAttribute("case") : (Case)session.getAttribute("edittedCase");
   final String errorMsg = (String)session.getAttribute("updateError");
   final String disable = (aCase.getStatus().equals("open") || errorMsg != null) ? "" : "disabled = \"disabled\"";
   %>

<%
final String disableHelper = (aCase.getStatus().equals("open") || errorMsg != null) ? "" : ("" +
		"<input type = \"hidden\" name = \"title\"  value = \"" + aCase.getTitle() + "\">" +
		"<input type = \"hidden\" name = \"category\"  value = \"" + aCase.getCategory() + "\">" +
		"<input type = \"hidden\" name = \"location\"  value = \"" + aCase.getLocation() + "\">" +
		"<input type = \"hidden\" name = \"day\"  value = \"" + aCase.getDay() + "\">" +
		"<input type = \"hidden\" name = \"month\"  value = \"" + aCase.getMonth() + "\">" +
		"<input type = \"hidden\" name = \"year\"  value = \"" + aCase.getYear() + "\">" +
		"<input type = \"hidden\" name = \"hours\"  value = \"" + aCase.getHours() + "\">" +
		"<input type = \"hidden\" name = \"mins\"  value = \"" + aCase.getMins() + "\">" +
		"<input type = \"hidden\" name = \"description\"  value = \"" + aCase.getDescription() + "\">" +
"");

%>

<h1>Edit Case</h1>
<% if(user == null){ %>
You are not authorized to edit cases.
Please log in.
<% }else{ %>

<%if(errorMsg != null){ %>
<font color="red"><%=errorMsg%></font>
<br/><br/>
<%}%>

<%if(aCase.getStatus().equals("closed") && errorMsg == null){ %>
<font color="orange">To edit a closed case you must first reopen it.</font>
<br/><br/>
<%}%>

<%
if(errorMsg != null){
	session.setAttribute("updateError", null);
}%>
<div><form action = "Case" method = "get" >
	<%= disableHelper %>
	<input type = "hidden" name = "action" value = "update">
	<input type ="hidden" name = "id" value = "<%= aCase.getId() %>" />
	Title: <input type=text name="title" size=60 value = "<%= aCase.getTitle() %>" <%= disable %>><br/>
	<%if(aCase.getStatus().equals("open")){ %>
	Status: <div><input type=radio name="status" value = 1 checked="checked">Open<br/>
			<input type=radio name="status" value=0>Closed</div><br/>
	<%}else{ %>
	Status: <div><input type=radio name="status" value = 1>Open<br/>
			<input type=radio name="status" value=0 checked="checked">Closed</div><br/>
	<%}%>
	Category: <input type=text name="category" value = "<%= aCase.getCategory() %>" <%= disable %>><br/>
	Location: <input type=text name="location" value = "<%= aCase.getLocation() %>" <%= disable %>><br/>
	
	<%
	String sDate;
	int day = 0;
	int month = 0;
	int year = 0;
	if(!(sDate = aCase.getDate()).equals("")){
		//System.out.println(String.format("-%s.%s.%s-",aCase.getDay(),aCase.getMonth(),aCase.getYear()));
		int day1 = Integer.parseInt(aCase.getDay());
		day = day1 < 32 && day1 > 0 ? day1 : 0;
		
		int month1 = Integer.parseInt(aCase.getMonth());
		month = month1 <13 && month1 > 0 ? month1 : 0;
		
		int year1 = Integer.parseInt(aCase.getYear());
		year = year1 > 0 ? year1 : 0;
	}%>
	Date: <select name="day" <%= disable %>>
	<% for(int i = 1; i < 32; i++){ 
		if(i == day){%>
		<option value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> . <select name = "month"<%= disable %>>
	<% for(int i = 1; i < 13; i++){ 
		if(i == month){%>
		<option value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> . <input type = text name = "year" size = 4 value = "<%=String.format("%04d", year)%>" <%= disable %>>
	<% String sTime = aCase.getTime();
			int hours = 0;
			int mins = 0;
	   if(!sTime.equals("")){
			int hours1 = Integer.parseInt(aCase.getHours());
			int mins1 = Integer.parseInt(aCase.getMins());
			
			hours = hours1 >= 0 && hours1 < 25 ? hours1 : 0;
			mins = mins1 >= 0 && mins1 < 61 ? mins1 : 0;
	   }
	%>
	at <select name="hours" <%= disable %>>
	<% for(int i = 0; i < 24; i++){ 
		if(i == hours){%>
		<option  value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> : <select name="mins" <%= disable %>>
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
	<textarea rows="4" cols="50" name="description" <%= disable %>><%= aCase.getDescription() %></textarea>
	<br/>
	<input type = submit value = "Change">
</form></div>
<div><form action = "Case" method = "get">
	<input type ="hidden" name = id value = "<%= aCase.getId() %>" />
	<input type = submit value = "Cancel">
</form></div>




<% } %>

<%@ include file="Footer.jsp"%>