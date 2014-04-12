<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.model.NewCaseData"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<% final User user = (User) session.getAttribute("user"); %>
<% final String errorMsg = (String)session.getAttribute("newPersonError");%>

<h1>Add new person of interest</h1>
<% if(user == null){ %>
You are not authorized.
Please log in.
<% }else{ %>

<%if(errorMsg != null){%>
	<font color="red"><%=errorMsg%></font>
	<br/>
<%
	session.setAttribute("newPersonError", null);
}

if (session.getAttribute("newPersonBorn") == "true"){
	%>
	<font color="green">Added a new person</font>
	<br/>
	<%
	session.setAttribute("newPersonBorn", null);
}

int day,month, year;
day = 1;
month = 1; 
year = 1800;

%>

<form action="NewPerson" method="get">
	<input type="hidden" name="action" value="add" /> 
	<table>
		<tr>
			<th>First name</th>
			<td><input type="text" name="firstname" value="" /></td>
		</tr>
		<tr>
			<th>Last name</th>
			<td><input type="text" name="lastname" value="" /></td>
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
			</select> 
			</td>
		</tr>
		
		<tr>
			<th colspan="2">
				<input type="submit" value="Add" />
			</th>
		</tr>
	</table>
</form>



<% }//end else(= user is logged in) %>

<%@ include file="Footer.jsp"%>