<%@page import="ch.ethz.inf.dbproject.model.User"%>
<%@page import="ch.ethz.inf.dbproject.HomeServlet"%>
<%@page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>

<%@page import="ch.ethz.inf.dbproject.database.MySQLConnection"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="Header.jsp"%>

<%
final User user = (User) session.getAttribute(UserManagement.SESSION_USER);

MySQLConnection inst = MySQLConnection.getInstance();
Connection conn = inst.getConnection();

Statement s = conn.createStatement();
ResultSet r;

//s.execute("CREATE TABLE Testtable (Name varchar(30));");
s.execute("INSERT INTO Testtable (Name) VALUES (\"Gutknecht\");");
r = s.getResultSet();
if(r != null){
	while(r.next()){
		r.getString(1);
	}

	r.close();
}

if (user != null) {
	// There is a user logged in! Display a greeting!
%>
	Welcome back <%=user.getName()%>
<%
} else {
	// No user logged in.%>
Welcome!
<%
}
%>
<%=request.getParameter("test")%>
<form action="Home.jsp" method="post">
<input type="text" name="test">
<input type="submit" value="go">
</form>


<br /><br />
See all available <a href="Cases">cases</a> and <a href="PersonsOfInterest">persons of interest</a>.

	conn.createStatement().execute("INSERT INTO testtable(name) VALUES ('dmdb2014')");
	
/*	try{
			PreparedStatement s = conn.prepareStatement(
					"INSERT INTO testtable(name) VALUES ('?')"		
			);

			s.setString(1, "2014's dmdb project");
			s.executeQuery();
		}catch(Exception ex){
			ex.printStackTrace();
	}*/
%>
<br />
<br />
See all available
<a href="Cases">cases</a>
and
<a href="PersonsOfInterest">persons of interest</a>
.

<%@ include file="Footer.jsp"%>