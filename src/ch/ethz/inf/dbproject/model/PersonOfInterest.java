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
	
	private boolean alive;
	
	public PersonOfInterest (ResultSet rs, ResultSet concernIds) throws SQLException{
		this.Id = (rs.getString("PersID"));
		this.setFirstName(rs.getString("firstname"));
		this.setLastName(rs.getString("lastname"));
		this.setBirthDay(rs.getString("birthday"));
		this.setAlive(rs.getBoolean("alive"));
		this.concernCaseIds = new ArrayList <String>();
		while (concernIds.next()){
			concernCaseIds.add(concernIds.getString("concernCaseIds"));
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
	public String getConcernCaseIds() {
		String Ids = "";
		if (concernCaseIds != null){
			for (String id : concernCaseIds){
				String s = "<a href=\"Case?id=" + id.toString() +  "\">View case</a><br>";
				Ids += s;
			}
		}
		return Ids;
	}

}
