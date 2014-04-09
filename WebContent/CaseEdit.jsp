<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.Case"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); %>
<% final Case aCase = (Case)session.getAttribute("case"); %>

<h1>Edit Case</h1>
<% if(user == null){ %>
You are not authorized to edit cases.
Please log in.
<% }else{ %>

<div><form action = "Case" method = "get" >
	<input type = hidden name = action value = "update">
	<input type ="hidden" name = id value = "<%= aCase.getId() %>" />
	Title: <input type=text name=title size=60 value = "<%= aCase.getTitle() %>"><br/>
	<%if(aCase.getStatus().equals("open")){ %>
	Status: <div><input type=radio name=status value = 1 checked="checked">Open<br/>
			<input type=radio name=status value=0>Closed</div><br/>
	<%}else{ %>
	Status: <div><input type=radio name=status value = 1>Open<br/>
			<input type=radio name=status value=0 checked="checked">Closed</div><br/>
	<%}%>
	Category: <input type=text name=category value = "<%= aCase.getCategory() %>"><br/> <%//TODO: change this to a selection %> 
	Location: <input type=text name=location value = "<%= aCase.getLocation() %>"><br/>
	
	<%
	String sDate;
	int day = 0;
	int month = 0;
	int year = 0;
	if(!(sDate = aCase.getDate()).equals("")){ 
		String[] date = sDate.split("\\.");
		int day1 = Integer.parseInt(date[0]);
		day = day1 < 32 && day1 > 0 ? day1 : 0;
		
		int month1 = Integer.parseInt(date[1]);
		month = month1 <13 && month1 > 0 ? month1 : 0;
		
		int year1 = Integer.parseInt(date[2]);
		year = year1 > 0 ? year1 : 0;
	}%>
	Date: <select name=day>
	<% for(int i = 1; i < 32; i++){ 
		if(i == day){%>
		<option selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> . <select name = month>
	<% for(int i = 1; i < 13; i++){ 
		if(i == day){%>
		<option selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> . <input type = text name = year size = 4 value = "<%=String.format("%04d", year)%>">
	<% String sTime = aCase.getTime();
			int hours = 0;
			int mins = 0;
	   if(!sTime.equals("")){
			String[] time = sTime.split("\\:");
			int hours1 = Integer.parseInt(time[0]);
			int mins1 = Integer.parseInt(time[1]);
			
			hours = hours1 >= 0 && hours1 < 25 ? hours1 : 0;
			mins = mins1 >= 0 && mins1 < 61 ? mins1 : 0;
	   }
	%>
	at <select name=hours>
	<% for(int i = 0; i < 24; i++){ 
		if(i == hours){%>
		<option selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select> : <select name=mins>
	<% for(int i = 0; i < 60; i++){ 
		if(i == mins){%>
		<option selected = selected><%=String.format("%02d", i)%></option>
		<%}else{%>
		<option><%=String.format("%02d", i)%></option>
		<%}//end if%>
	<%}//end for%>
	</select>
	<br/>
	Description:<br/>
	<textarea rows="4" cols="50" name="description"><%= aCase.getDescription() %></textarea>
	<br/>
	<input type = submit value = "Change">
</form></div>
<div><form action = "Case" method = "get">
	<input type ="hidden" name = id value = "<%= aCase.getId() %>" />
	<input type = submit value = "Cancel">
</form></div>




<% } %>

<%@ include file="Footer.jsp"%>