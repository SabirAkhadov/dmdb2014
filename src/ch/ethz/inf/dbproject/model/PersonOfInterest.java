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
	private String notes;
	private String related;
	
	private String alive;
	
	private String type;
	private String sentence;
	private String date;
	private String enddate;
	
	private String reason;
	
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
		
		while (concern.next()){
			String s1 = "";
			s1 =  concern.getString("CaseIDs");
			concernCaseIds.add(s1);
		}
		
		this.notes = "";
		while (notes.next()){
			String s1 = "<i>" + notes.getString("content") + "</i><br><br>Added by " + notes.getString("name") + 
					" on <br><br>" + notes.getString("timestamp") + "<br><br>";
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
					"<br><a href = \"PersonOfInterest?id=" + related.getString("PersID2") + "\">View person</a>" +
					"<br><a href = \"PersonOfInterest?id=" + Id + "&persID2=" +related.getString("PersID2") + "&delete=true&relationship=" + s4 +"\">Delete relationship</a><br><br>";
			
			this.related +=s1;
		}
	}

	public PersonOfInterest (ResultSet rs) throws SQLException{
			
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
		
	}
	
	//This is used for the suspect, concerns, convicted view:
	public PersonOfInterest (ResultSet rs, String setType) throws SQLException{
		
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
		
		//The following commands are for convicted, suspect, concerns display:
		if(setType.equals("convicted")){
			s = rs.getString("type");
			if (s == null){
				s = "unknown";
			}
			this.setType(s);
			
			s = rs.getString("sentence");
			if (s == null){
				s = "unknown";
			}
			this.setSentence(s);
			
			s = rs.getString("date");
			if (s == null){
				s = "unknown";
			}
			this.setDate(s);
			
			s = rs.getString("enddate");
			if (s == null){
				s = "unknown";
			}
			this.setEnddate(s);
		}else if(setType.equals("suspected") || setType.equals("concerns")){
		
			s = rs.getString("reason");
			if (s == null){
				s = "unknown";
			}
			this.setReason(s);
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
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public String getSentence() {
		return this.sentence;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	public String getEnddate() {
		return this.enddate;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}

	//dirty tricks incoming
	public String getConcernCase() {
		String Ids = "";
		if (concernCaseIds != null){
			for (String id : concernCaseIds){
				String s = "<br><a href=\"Case?id=" + id.toString() +  "\">View case</a><br>";
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
