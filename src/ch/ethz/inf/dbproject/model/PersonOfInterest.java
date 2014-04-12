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
	
	private String alive;
	
	public PersonOfInterest (ResultSet rs, ResultSet concern, ResultSet notes, ResultSet related) throws SQLException{
		
		this.Id = (rs.getString("PersID"));
		String s = rs.getString("firstname");
		if (s == null){
			s = "unknown";
		}
		this.setFirstName(s);
		
		s = rs.getString("lastname");
		if (s == null){
			s = "unknown";
		}
		this.setLastName(s);
		
		s = rs.getString("birthday");
		if (s == null){
			s = "unknown";
		}
		this.setBirthDay(s);
		
		s = rs.getString("alive");
		if (s == null){
			s = "unknown";
		}
		if (s.equals("1")){
			s = "yes";
		}
		if (s.equals("0")){
			s = "no";
		}
		this.setAlive(s);
		
		this.concernCaseIds = new ArrayList <String>();
		this.concernReason = new ArrayList <String>();
		
		while (concern.next()){
			String s1 = concern.getString("reason");
			if (s1 == null){
				s1 = "unknown";
			}
			concernReason.add(s1);

			s1 =  concern.getString("concernCaseIds");
			if (s1 == null){
				s1 = "unknown";
			}
			concernCaseIds.add(s1);
		}
		
		this.notes = "";
		while (notes.next()){
			String s1 = "<i>" + notes.getString("content") + "</i><br><br>Added by " + notes.getString("name") + " on <br><br>" + notes.getString("timestamp");
			this.notes += s1;
		}
		
		this.related = "";
		while (related.next()){
			String s2 = related.getString("firstname");
			if (s2 == null){
				s2 = "unknown";
			}
			String s3 = related.getString("lastname");
			if (s3 == null){
				s3 = "unknown";
			}
			
			String s4 = related.getString("relationship");
			if (s4 == null){
				s4 = "unknown";
			}
			
			String s1 = "First name: " + s2 + "<br>Last name: " + s3 + 
					"<br>Relationship: " +  s4 + 
					"<br><a href = \"PersonOfInterest?id=" + related.getString("PersID2") + "\">View person</a> <br><br>";
			
			this.related +=s1;
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

	public String getAlive() {
		return alive;
	}

	public void setAlive(String s) {
		this.alive = s;
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
