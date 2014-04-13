<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	
	<head>
	    <link href="style.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Law Enforcement Project</title>
	</head>

	<body>

		<!-- Header -->
		
		<table id="masterTable" cellpadding="0" cellspacing="0">
			<tr>
				<th id="masterHeader" colspan="2">
					<h1>Law Enforcement Project</h1>
					Project by Benjamin, Roger, Sabir 
				</th>
			</tr>
			<tr id="masterContent">
			
				<td id="masterContentMenu">
					
					<div class="menuDiv1"></div>
					<div class="menuDiv1"><a href="Home">Home</a></div>
					<% if (session.getAttribute("user")!= null) {%>
					<div class="menuDiv1"><a href="CaseNew.jsp">New Case</a></div>
					<div class="menuDiv1"><a href="NewPerson">Add person Of interest</a></div>
					<%}%>
					<div class="menuDiv1"><a href="Cases">All cases</a></div>
					<div class="menuDiv2"><a href="Cases?filter=open">Open</a></div>
					<div class="menuDiv2"><a href="Cases?filter=closed">Closed</a></div>
					<div class="menuDiv2"><a href="Cases?filter=recent">Recent</a></div>
					<div class="menuDiv2"><a href="Cases?filter=oldest">Oldest Unsolved</a></div>
					<div class="menuDiv1">Cases by Category:</div>
					<div class="menuDiv2"><a href="Cases?category=assault">Assault</a></div>
					<div class="menuDiv2"><a href="Cases?category=theft">Theft</a></div>
					<div class="menuDiv2"><a href="Cases?category=other">Other</a></div>
					<div class="menuDiv1"><a href="Search?for=cases">Search for Cases</a></div>
					<div class="menuDiv1"><a href="PersonsOfInterest">All Persons of Interest</a></div>
					<div class="menuDiv1"><a href="Search?for=persons">Search for Persons</a></div>
					<div class="menuDiv1"><a href="User">User Profile</a></div>
					
				</td>
				
				<td id="masterContentPlaceholder">
				
		