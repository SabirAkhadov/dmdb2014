package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonOfInterest {

	private String FirstName;
	private String LastName;
	private String BirthDate;
	private String alive;
	
	public PersonOfInterest (ResultSet rs) throws SQLException{
		this.FirstName = rs.getString("firstname");
		this.LastName = rs.getString("lastname");
		this.BirthDate = rs.getString("birthdate");
		this.alive = rs.getString("alive");
	}
}
