package ch.ethz.inf.dbproject.model;

public final class NewCaseData {
	
	private final int userID;
	
	private final int day;
	private final int month;
	private final int year;
	private final int hours;
	private final int mins;
	

	private final String title;
	private final String category;
	private final String description;
	private final String location;
	
	public NewCaseData(final int userID, final int day, final int month, final int year, final int hours,
			final int mins, final String title, final String category, final String location, final String description) {
		this.userID = userID;
		this.day = day;
		this.month = month;
		this.year = year;
		this.hours = hours;
		this.mins = mins;
		this.title = title;
		this.category = category;
		this.description = description;
		this.location = location;
	}

	public int getUserID() {
		return userID;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public int getHours() {
		return hours;
	}

	public int getMins() {
		return mins;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}

}
