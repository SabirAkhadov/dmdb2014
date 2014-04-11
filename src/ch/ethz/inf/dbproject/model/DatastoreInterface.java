package ch.ethz.inf.dbproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.database.MySQLConnection;

/**
 * This class should be the interface between the web application
 * and the database. Keeping all the data-access methods here
 * will be very helpful for part 2 of the project.
 */
public final class DatastoreInterface {

	/**caseConstr extracts and formats all necessary information into the ResultSet such that a case can be constructed from it"**/
	//(rs.getInt("id"), rs.getInt("status"), rs.getString("title"),rs.getString("category"), rs.getString("description"), rs.getString("location"), rs.getDate("date"), rs.getTime("time"))
	private final String caseConstr = "" +
			"SELECT" +
			" cas.caseID AS `id`," +
			" cas.status AS `status`," +
			" cas.title AS `title`," +
			" cat.name AS `category`," +
			" cas.description AS `description`," +
			" cas.location AS `location`," +
			" cas.date AS `date`," +
			" cas.time AS `time`" +
			" FROM cases AS cas" +
			" LEFT JOIN CaseCategory AS cc ON cas.caseID = cc.caseID" +
			" LEFT JOIN category AS cat ON cc.catID = cat.catID" +
			" "; //do not forget final space

	private PreparedStatement caseByID;
	private PreparedStatement caseAll;
	private PreparedStatement caseOpen;
	private PreparedStatement caseClosed;
	private PreparedStatement caseMostRecent;
	private PreparedStatement caseOldestUnresolved;
	private PreparedStatement caseByCategory;
	
	/* be obsolete as we're changing the menu
	private PreparedStatement casePersonal;
	private PreparedStatement caseProperty;
	private PreparedStatement casePersonalOther;
	private PreparedStatement casePropertyOther;
	*/
	
	private Connection sqlConnection;

	public DatastoreInterface() {

		this.sqlConnection = MySQLConnection.getInstance().getConnection();

		try {
			caseByID = sqlConnection.prepareStatement(caseConstr + "WHERE cas.caseid = ?;");
			caseAll = sqlConnection.prepareStatement(caseConstr);
			caseOpen = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 1;");
			caseClosed = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 0;");
			caseMostRecent = sqlConnection.prepareStatement(caseConstr + "ORDER BY cas.date DESC;");
			caseOldestUnresolved = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 1 ORDER BY cas.date DESC;");
			caseByCategory = sqlConnection.prepareStatement(caseConstr + "WHERE cat.name = ? ORDER BY date DESC;" );
			
			/* will be obsolete as we're changing the menu
			casePersonal = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 0 ORDER BY date DESC;"); //TODO: verify
			caseProperty = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 1 ORDER BY date DESC;"); //TODO: verify
			casePersonalOther = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 0 AND cat.name <> 'assault' ORDER BY date DESC;");
			casePropertyOther = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 1 AND cat.name <> 'theft' ORDER BY date DESC;");
			*/

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public final Case getCaseById(final int id) {

		try{
			caseByID.setInt(1, id);
			final ResultSet rs = caseByID.executeQuery();

			if(rs.next())
				return new Case(rs);

			return null;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}

	}

	public final List<Case> getAllCases() {

		try {

			final ResultSet rs = caseAll.executeQuery();

			final List<Case> cases = new ArrayList<Case>(); 
			while (rs.next()) {
				cases.add(new Case(rs));
			}

			rs.close();

			return cases;

		} catch (final SQLException ex) {			
			ex.printStackTrace();
			return null;			
		}
	}
	public final List<Case> getOpenCases(){
		try{
			final ResultSet rs = caseOpen.executeQuery();

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public final List<Case> getClosedCases(){
		try{
			final ResultSet rs = caseClosed.executeQuery();

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public final List<Case> getMostRecentCases(){
		try{
			final ResultSet rs = caseMostRecent.executeQuery();

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public final List<Case> getOldestUnsolvedCases(){
		try{
			final ResultSet rs = caseOldestUnresolved.executeQuery();

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public final List<Case> getCasesByCategory(String category){
		try{
			final ResultSet rs;
			switch(category){
			/*will be obsolete as we're changing the menu
			case "personal":
				rs = casePersonal.executeQuery();
				break;
			case "property":
				rs = caseProperty.executeQuery();
				break;
			case "otherProp":
				rs = casePropertyOther.executeQuery();
				break;
			case "otherPers":
				rs = casePersonalOther.executeQuery();
				break;
			*/
			default:
				caseByCategory.setString(1, category);
				rs = caseByCategory.executeQuery();
				break;
			}
			
			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Case> getOpenUserCases (User user) {
		
		List <Case> cases = new ArrayList <Case>();
		try {
			PreparedStatement caseIDs  = sqlConnection.prepareStatement("SELECT CaseID From open WHERE UserID = ?");
			caseIDs.setString(1, user.getUserID());
			caseIDs.execute();
			ResultSet rs = caseIDs.getResultSet();
			while (rs.next())
			{
				cases.add(getCaseById(rs.getInt(1)));
			}
			return cases;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Case> getCloseUserCases (User user) {
		
		List <Case> cases = new ArrayList <Case>();
		try {
			PreparedStatement caseIDs  = sqlConnection.prepareStatement("SELECT CaseID From close WHERE UserID = ?");
			caseIDs.setString(1, user.getUserID());
			caseIDs.execute();
			ResultSet rs = caseIDs.getResultSet();
			
			while (rs.next())
			{
				cases.add(getCaseById(rs.getInt(1)));
			}
			return cases;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**updates a case in the database
	 * @param original the case as it is stored prior to the update 
	 * @param Updated the case the stored case should be updated to
	 * @param userID the user requesting the update
	 * @return null if succeeds or error message otherwise
	 */
	public String updateCase(Case original, Case Updated, int userID){
		
		
		
		
		
		return "I am testing an error.";
	}

	
	public final boolean openCase (String title, String date, String time, String description, String location) {

		PreparedStatement s;
		
		try {
			s = sqlConnection.prepareStatement("INSERT INTO cases Values (null, ?, 1, ?, ? ,?, ?)");
			s.setString(1, date);
			s.setString(2, location);
			s.setString(3, time);
			s.setString(4, description);
			s.setString(5, title);
			s.execute();
			
			//TODO insert a new entry in casecategory
			// also make a user choose a category
			//consider using trigger for auto inserting in category table and open table
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//insert user to db, return user object
	public final User insertUser(String username, String email, String password) {

		PreparedStatement s;
		try {
			s = sqlConnection.prepareStatement("INSERT INTO user Values (null, ?, ?, ?)");
		
		s.setString(1, username);
		s.setString(2, password);
		s.setString(3, email);
		s.execute();
		s.close();

		final PreparedStatement us = sqlConnection.prepareStatement("SELECT * FROM user WHERE name = ?");

		us.setString(1, username);
		us.execute();

		final ResultSet rs = us.getResultSet();
		if (rs.next()) {
			return new User(rs);
		}
		else {
			return null;
		}
		} catch (SQLException e) { 
			e.addSuppressed(new Throwable());
			e.printStackTrace();
			return null;
		}
	}

	public final User validateUser (String name, String password) {

		PreparedStatement s;
		try {

			// collation argument depends on server character set. Here we have utf8mb4.
			s = sqlConnection.prepareStatement("SELECT * FROM user WHERE name = ? and password = ? COLLATE utf8mb4_bin");

			s.setString(1, name);
			s.setString(2, password);
			s.execute();
			ResultSet rs = s.getResultSet();
			if (rs.next()) {
				return new User (rs);
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public final User changeData (User user, String username, String email, String password){
		
		PreparedStatement s;
		PreparedStatement t;
		try {
			s = sqlConnection.prepareStatement("UPDATE user SET name = ?, password = ?, email = ? " +
					"WHERE UserID = ?");
			
			s.setString(1, username);
			s.setString(2, password);
			s.setString(3, email);
			s.setString(4, user.getUserID());
			
			s.execute();
			
			t = sqlConnection.prepareStatement("SELECT * FROM user WHERE name = ?");
			t.setString(1, username);
			t.execute();
			ResultSet rs = t.getResultSet();
			if (rs.next()) {
				return new User (rs);
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
