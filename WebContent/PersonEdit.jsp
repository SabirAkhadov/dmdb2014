<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.PersonOfInterest"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final String errorMsg = (String)session.getAttribute("editPersonError"); 
	PersonOfInterest p = (PersonOfInterest)(session.getAttribute("personToEdit"));%>


<%
if (errorMsg!= null){
	
%>
	<font color="red"><%=errorMsg%></font>
<% 
	session.setAttribute("editPersonError", null);
}
	if (user == null){
	%>
	<h3> You have to be logged in.</h3>
<%	
}
else {
	int day,month, year;
	if (p.getBirthDay() != "unknown"){
		String [] birthday = p.getBirthDay().split("-");
		day = Integer.parseInt(birthday[2]);
		month = Integer.parseInt(birthday[1]);
		year = Integer.parseInt(birthday[0]);
	}
	else{
		day = 1;
		month = 1; 
		year = 1800;
	}
%>
<h1>Edit person of interest</h1>

<form action="PersonEdit" method="get">
	<input type="hidden" name="action" value="editPerson" /> 
	<table>
		<tr>
			<th>First name</th>
			<td><input type="text" name="firstname" value="<%=p.getFirstName() %>" /></td>
		</tr>
		<tr>
			<th>Last name</th>
			<td><input type="text" name="lastname" value="<%=p.getLastName() %>" /></td>
		</tr>
		<tr>
			<th>Birthday</th>
			<td><select name="day">
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
			</select> . <select name = "year">
			<% for(int i = 1900; i <= Calendar.getInstance().get(Calendar.YEAR); i++){
				if(i == year){%>
				<option value=<%=String.format("%02d", i)%> selected = selected><%=String.format("%02d", i)%></option>
				<%}else{%>
				<option value=<%=String.format("%02d", i)%>><%=String.format("%02d", i)%></option>
				<%}//end if%>
			<%}//end for%>
			</select> 
			</td>
		</tr>
		<tr>
			<th>Is this Person alive?</th>
			<td>
			<select name = "alive">
				<option value="1" selected = selected>yes</option>
				<option value="0">no</option>
				<option value="">unknown</option>
			</select> 
			</td>
		</tr>
		<tr>
			<th >
				<input type="submit" value="Edit" />
			</th>
		</tr>
	</table>
</form>

<%
}
%>
<%@ include file="Footer.jsp"%>