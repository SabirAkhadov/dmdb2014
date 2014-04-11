<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<h1>Search for case</h1>

<hr/>

<form method="get" action="Search">
<div>
	<b>By person of interest</b><br>
	First name: <input type="text" name="firstname" /><br>
	Last name: <input type="text" name="lastname" /><br><br>
	
	<b>By case category</b><br>
	Category: <input type="text" name="category" /><br><br>
	
	<b>By conviction</b><br>
	Date: <input type="text" name="conv_date" /> (Format: YYYY-MM-DD)<br>
	Type: <input type="text" name="conv_type" />
	
	<input type="submit" value="Search" title="Search" /><br><br>
	Note: A case must match ALL the entered data. Empty fields get ignored.<br>Use "%" to match any number of unknown characters and "_" to match exactly one unknown character.
</div>
</form>

<hr/>
<%=session.getAttribute("results")%>

<hr/>

<%@ include file="Footer.jsp" %>