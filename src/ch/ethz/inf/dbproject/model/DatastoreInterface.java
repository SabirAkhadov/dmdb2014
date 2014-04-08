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

	private PreparedStatement allUsers;

	private Connection sqlConnection;

	public DatastoreInterface() {

		this.sqlConnection = MySQLConnection.getInstance().getConnection();

		try {
			allUsers = sqlConnection.prepareStatement("SELECT * FROM User");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public final Case getCaseById(final int id) {

		try{
			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM cases WHERE caseID = " + id + ";");

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

			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM  cases;");

			final List<Case> cases = new ArrayList<Case>(); 
			while (rs.next()) {
				cases.add(new Case(rs));
			}

			rs.close();
			stmt.close();

			return cases;

		} catch (final SQLException ex) {			
			ex.printStackTrace();
			return null;			
		}
	}

	public final List<Case> getOpenCases(){
		try{
			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM cases WHERE status = 1;");

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();
			stmt.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public final List<Case> getClosedCases(){
		try{
			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM cases WHERE status = 0;");

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();
			stmt.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public final List<Case> getMostRecentCases(){
		try{
			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM cases ORDER BY date DESC;");

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();
			stmt.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public final List<Case> getOldestUnsolvedCases(){
		try{
			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("SELECT * FROM cases WHERE status = 1 ORDER BY date DESC;");

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();
			stmt.close();

			return cases;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public final List<Case> getCasesByCategory(String category){
		try{
			final java.sql.Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("" +
					"SELECT cas.caseID, cas.date, cas.status, cas.location, cas.time, cas.description, cas.title" +
					" FROM cases AS cas" +
					" INNER JOIN CaseCategory AS cc ON cas.caseID = cc.caseID" +
					" INNER JOIN category AS cat ON cc.catID = cat.catID" +
					" WHERE cat.name = '" + category + "'" +
					" ORDER BY date DESC;");

			final List<Case> cases = new ArrayList<Case>();
			while(rs.next())
				cases.add(new Case(rs));

			rs.close();
			stmt.close();

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
