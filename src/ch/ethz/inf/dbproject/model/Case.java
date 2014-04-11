package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Case {
	
	private final int id;
	private final String date;
	private String year;
	private String month;
	private String day;
	private final String time;
	private String hours;
	private String mins;
	private final String description;
	private final String status;
	private final String lastStatusChange;
	private final String location;
	private final String title;
	private final String category;
	
	/**
	 Construct a new case.
	 */
	public Case(final int id, final int status, final String title, final String category, final String description, final String location, final String date, final String time, final String lastStatusChange){
		this.id = id;
		
		this.date = (date == null) ? "" : formatDate(date);
		
		this.time = (time == null) ? "" : formatTime(time);
		
		this.status = status == 0 ? "closed" : "open";
		
		this.lastStatusChange = lastStatusChange == null ? "" : lastStatusChange;
		
		this.location = location == null ? "" : location;
		
		this.description = description == null ? "" : description;
		
		this.title = title == null ? "" : title;
		
		this.category = category == null ? "" : category;
	}
	
	public Case(final ResultSet rs) throws SQLException {
		this(rs.getInt("id"), rs.getInt("status"), rs.getString("title"),rs.getString("category"), rs.getString("description"), rs.getString("location"), rs.getString("date"), rs.getString("time"), null);
	}
	
	private String formatDate(String sDate){
		String[] arr = sDate.split("\\-");
		this.year = arr[0];
		this.month = arr[1];
		this.day = arr[2];
		
		return String.format("%s.%s.%s", day,month,year);
	}
	
	private String formatTime(String sTime){
		String[] arr = sTime.split("\\:");
		this.hours = arr[0];
		this.mins = arr[1];
		
		return String.format("%s:%s", hours,mins);
	}

	/**
	 * HINT: In eclipse, use Alt + Shift + S menu and choose:
	 * "Generate Getters and Setters to auto-magically generate
	 * the getters. 
	 */
	public int getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}

	public String getYear() {
		return date.equals("") ? "" : year;
	}

	public String getMonth() {
		return date.equals("") ? "" : month;
	}

	public String getDay() {
		return date.equals("") ? "" : day;
	}

	public String getTime() {
		return time;
	}

	public String getHours() {
		return time.equals("") ? "" : hours;
	}

	public String getMins() {
		return time.equals("") ? "" : mins;
	}

	public String getStatus() {
		return status;
	}

	public String getLastStatusChange() {
		return lastStatusChange;
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