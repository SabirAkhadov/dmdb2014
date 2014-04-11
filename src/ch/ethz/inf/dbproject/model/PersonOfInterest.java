package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonOfInterest {

	private String Id;
	private String FirstName;
	private String LastName;
	private String BirthDay;
	
	private List <String> concernCaseIds;
	private List <String> concernReason;
	private String notes;
	private String related;
	
	private boolean alive;
	
	public PersonOfInterest (ResultSet rs, ResultSet concern, ResultSet notes, ResultSet related) throws SQLException{
		
		this.Id = (rs.getString("PersID"));
		this.setFirstName(rs.getString("firstname"));
		this.setLastName(rs.getString("lastname"));
		this.setBirthDay(rs.getString("birthday"));
		this.setAlive(rs.getBoolean("alive"));
		
		this.concernCaseIds = new ArrayList <String>();
		this.concernReason = new ArrayList <String>();
		
		while (concern.next()){
			concernReason.add(concern.getString("reason"));
			concernCaseIds.add(concern.getString("concernCaseIds"));
		}
		
		this.notes = "";
		while (notes.next()){
			String s = "<i>" + notes.getString("content") + "</i><br><br>Added by " + notes.getString("name") + " on <br><br>" + notes.getString("timestamp");
			this.notes += s;
		}
		
		this.related = "";
		while (related.next()){
			String s = "First name: " + related.getString("firstname") + "<br>Last name: " + related.getString("lastname") + 
					"<br>Relationship: " +  related.getString("relationship") + 
					"<br><a href = \"PersonOfInterest?id=" + related.getString("PersID2") + "\">View person</a> <br><br>";
			
			this.related +=s;
		}
		
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getBirthDay() {
		return BirthDay;
	}

	public void setBirthDay(String birthDate) {
		BirthDay = birthDate;
	}

	public boolean getAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	//dirty tricks incoming
	public String getConcernCase() {
		String Ids = "";
		if (concernCaseIds != null){
			for (String id : concernCaseIds){
				String reason = concernReason.iterator().next();
				String s = "<a href=\"Case?id=" + id.toString() +  "\">View case</a> <br> Reason for concern: <i>" + reason + "</i><br>";
				Ids += s;
			}
		}
		return Ids;
	}

	public String getNotes() {
		return notes;
	}

	public String getRelated() {
		return related;
	}


}
