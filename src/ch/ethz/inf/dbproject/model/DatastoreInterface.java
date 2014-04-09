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

	private PreparedStatement allUsers;

	private PreparedStatement caseByID;
	private PreparedStatement caseAll;
	private PreparedStatement caseOpen;
	private PreparedStatement caseClosed;
	private PreparedStatement caseMostRecent;
	private PreparedStatement caseOldestUnresolved;
	private PreparedStatement caseByCategory;
	private PreparedStatement casePersonal;
	private PreparedStatement caseProperty;
	private PreparedStatement casePersonalOther;
	private PreparedStatement casePropertyOther;

	private Connection sqlConnection;

	public DatastoreInterface() {

		this.sqlConnection = MySQLConnection.getInstance().getConnection();

		try {
			allUsers = sqlConnection.prepareStatement("SELECT * FROM User");

			caseByID = sqlConnection.prepareStatement(caseConstr + "WHERE id = ?;");
			caseAll = sqlConnection.prepareStatement(caseConstr);
			caseOpen = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 1;");
			caseClosed = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 0;");
			caseMostRecent = sqlConnection.prepareStatement(caseConstr + "ORDER BY cas.date DESC;");
			caseOldestUnresolved = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 1 ORDER BY cas.date DESC;");
			caseByCategory = sqlConnection.prepareStatement(caseConstr + "WHERE cat.name = ? ORDER BY date DESC;" );
			casePersonal = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 0 ORDER BY date DESC;"); //TODO: verify
			caseProperty = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 1 ORDER BY date DESC;"); //TODO: verify
			casePersonalOther = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 0 AND cat.name <> 'assault' ORDER BY date DESC;");
			casePropertyOther = sqlConnection.prepareStatement(caseConstr + "WHERE cat.supercat = 1 AND cat.name <> 'theft' ORDER BY date DESC;");

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

	public final List<User> getAllUsers() {

		/**
		 *this method should returns all users in the database
		 *one can improve it by saving the list and having a flag changed.
		 */
		try {

			allUsers.execute();
			ResultSet rs = allUsers.getResultSet();
			List <User> list = new ArrayList<User>();

			while (rs.next())
			{
				list.add(new User (rs));
			}
			allUsers.close();
			rs.close();
			return list;

		} catch (final SQLException ex) {			
			ex.printStackTrace();
			return null;			
		}
	}

	//insert user to db, return user object
	public final User insertUser(String username, String email, String password) throws SQLException {

		//userID will be auto incremented
		PreparedStatement s = sqlConnection.prepareStatement("INSERT INTO user Values (null, ?, ?, ?)");

		s.setString(1, username);
		s.setString(2, password);
		s.setString(3, email);
		s.execute();
		s.close();

		PreparedStatement us = sqlConnection.prepareStatement("SELECT * FROM user WHERE name = ?");
		us.setString(1, username);
		us.execute();
		ResultSet rs = us.getResultSet();

		return new User(rs);

	}

	public final User validateUser (String name, String password) {

		PreparedStatement s;
		try {
			s = sqlConnection.prepareStatement("SELECT * FROM user WHERE name = ? and password = ?");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
