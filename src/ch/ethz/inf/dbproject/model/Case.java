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
	private final String category;
	
	private final boolean hasDate;
	private final boolean hasTime;
	
	/**
	 Construct a new case.
	 */
	@SuppressWarnings("deprecation")
	public Case(final int id, final int status, final String title, final String category, final String description, final String location, final java.sql.Date date, final java.sql.Time time){
		this.id = id;
		
		this.date = new java.util.Date();
		
		if(this.hasDate = (date != null)){
			this.date.setYear(date.getYear());
			this.date.setMonth(date.getMonth());
			this.date.setDate(date.getDay());
		}
		if(this.hasTime = (time != null))
			this.date.setTime(time.getTime());
		
		this.status = status == 0 ? "closed" : "open";
		
		this.location = location == null ? "" : location;
		
		this.description = description == null ? "" : description;
		
		this.title = title == null ? "" : title;
		
		this.category = category == null ? "" : category;
	}
	
	public Case(final ResultSet rs) throws SQLException {
		this(rs.getInt("id"), rs.getInt("status"), rs.getString("title"),rs.getString("category"), rs.getString("description"), rs.getString("location"), rs.getDate("date"), rs.getTime("time"));
	}

	/**
	 * HINT: In eclipse, use Alt + Shift + S menu and choose:
	 * "Generate Getters and Setters to auto-magically generate
	 * the getters. 
	 */
	public int getId() {
		return id;
	}

	@SuppressWarnings("deprecation")
	public String getDate() {
		return hasDate ? ("" + date.getDay() + "."+date.getMonth()+"."+date.getYear()) : "";
	}
	
	@SuppressWarnings("deprecation")
	public String getTime(){
		return hasTime ? ("" + date.getHours() + ":" + date.getMinutes()) : "";
	}

	public String getStatus() {
		return status;
	}

	public String getLocation() {
		return location == null ? "" : location;
	}
	
	public String getDescription(){
		return description == null ? "" : description;
	}

	public String getTitle() {
		return title;
	}
	
	public String getCategory(){
		return category;
	}
}