package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Case {
	
	private final int id;
	private final java.util.Date date; //I'm not importing it so we can differentiate it from java.sql.Date
	private final String description;
	private final String status;
	private final String location;
	private final String title;
	
	/**
	 Construct a new case.
	 */
	@SuppressWarnings("deprecation")
	public Case(final int id, final java.sql.Date date, final int status, final String location, final java.sql.Time time, final String description, final String title) {
		this.id = id;
		System.out.println("id succeeded");
		
		this.date = new java.util.Date();
		
		if(date != null){
			this.date.setYear(date.getYear());
			this.date.setMonth(date.getMonth());
			this.date.setDate(date.getDay());
		}
		if(time != null)
			this.date.setTime(time.getTime());
		
		this.status = status == 0 ? "closed" : "open";
		
		this.location = location;
		
		this.description = description == null ? "" : description;
		
		this.title = title;
	}
	
	public Case(final ResultSet rs) throws SQLException {
		this(rs.getInt("CaseID"), rs.getDate("date"), rs.getInt("status"),rs.getString("location"), rs.getTime("time"), rs.getString("description"),rs.getString("Title"));
	}

	/**
	 * HINT: In eclipse, use Alt + Shift + S menu and choose:
	 * "Generate Getters and Setters to auto-magically generate
	 * the getters. 
	 */
	public int getId() {
		return id;
	}

	public java.util.Date getDate() {
		return date;
	}

	public String getStatus() {
		return status;
	}

	public String getLocation() {
		return location;
	}
	
	public String getDescription(){
		return description;
	}

	public String getTitle() {
		return title;
	}
}