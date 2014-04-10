package ch.ethz.inf.dbproject.model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

	/**categoryConstr extracts and formats all necessary information into the ResultSet such that a category can be constructed from it**/
	private final String categoryConstr = "" +
			"SELECT" +
			" catID," +
			" name" +
			" FROM" +
			" category" +
			" ";

	private PreparedStatement allUsers;

	private PreparedStatement caseByID;
	private PreparedStatement caseAll;
	private PreparedStatement caseOpen;
	private PreparedStatement caseClosed;
	private PreparedStatement caseMostRecent;
	private PreparedStatement caseOldestUnresolved;
	private PreparedStatement caseByCategory;

	private PreparedStatement categoryAll;
	private PreparedStatement categoryByName;
	private PreparedStatement categoryByID;
	private PreparedStatement categoryInsert;

	private PreparedStatement catUnlinkByCaseID;
	private PreparedStatement catLinkToCaseID;

	private PreparedStatement openLog;
	private PreparedStatement closeLog;

	private Connection sqlConnection;

	public DatastoreInterface() {

		this.sqlConnection = MySQLConnection.getInstance().getConnection();

		try {
			allUsers = sqlConnection.prepareStatement("SELECT * FROM User");

			caseByID = sqlConnection.prepareStatement(caseConstr + "WHERE cas.caseid = ?;");
			caseAll = sqlConnection.prepareStatement(caseConstr);
			caseOpen = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 1;");
			caseClosed = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 0;");
			caseMostRecent = sqlConnection.prepareStatement(caseConstr + "ORDER BY cas.date DESC;");
			caseOldestUnresolved = sqlConnection.prepareStatement(caseConstr + "WHERE cas.status = 1 ORDER BY cas.date DESC;");
			caseByCategory = sqlConnection.prepareStatement(caseConstr + "WHERE cat.name = ? ORDER BY date DESC;" );

			categoryAll = sqlConnection.prepareStatement(categoryConstr);
			categoryByName = sqlConnection.prepareStatement(categoryConstr + "WHERE name = ?;");
			categoryByID = sqlConnection.prepareStatement(categoryConstr + "WHERE catID = ?;");
			categoryInsert = sqlConnection.prepareStatement("INSERT INTO category(name) VALUES (?);");

			catUnlinkByCaseID = sqlConnection.prepareStatement("DELETE FROM CaseCategory WHERE CatID = ? AND CaseID = ?;");
			catLinkToCaseID = sqlConnection.prepareStatement("INSERT INTO Casecategory(catID,caseID) VALUES (?,?);");

			openLog = sqlConnection.prepareStatement("INSERT INTO open(UserID,CaseID) VALUES (?,?);");
			closeLog = sqlConnection.prepareStatement("INSERT INTO close(UserID,CaseID) VALUES (?,?);");

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
	/**updates a case in the database
	 * @param original the case as it is stored prior to the update 
	 * @param Updated the case the stored case should be updated to
	 * @param userID the user requesting the update
	 * @return null if succeeds or error message otherwise
	 */
	public String updateCase(Case original, Case update, int userID){
		//(int id, int status, String title, String category, String description, String location, String Date date, String time)

		boolean statusChanged = false;
		boolean categoryChanged = false;
		try{
			int catID = -1;
			if(original.getCategory().toLowerCase().equals(update.getCategory().toLowerCase())){
				if(update.getCategory().equals(""))
					return "Invalid category!";

				categoryByName.setString(1, original.getCategory());
				ResultSet rs =categoryByName.executeQuery();

				if(rs.next())
					catID = (new Category(rs)).getID();
				else
					return "Old category does not exist?! (should be impossible)";
			}else{
				categoryChanged = true;
				catID = insertCategory(update.getCategory());
			}

			if(catID < 0)
				return "There was an error inserting the new category.";

			String query = "UPDATE cases SET" + " ";

			//update title
			if(!original.getTitle().equals(update.getTitle()))
				query += "title = '" + update.getTitle() + "', ";

			//update status;
			if(!original.getStatus().equals(update.getStatus())){
				query += "status = " + (update.getStatus().equals("open") ? 1 : 0) + ", ";
				statusChanged = true;
			}

			//update description
			if(!original.getDescription().equals(update.getDescription()))
				query += "description = '" + update.getDescription().replace("'", "\\'").replace("\"", "\\\"").replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "'" + ", ";

			//update location
			if(!original.getLocation().equals(update.getLocation()))
				query += "location = '" + update.getLocation() + "'" + ", ";

			//update date
			if(!original.getDate().equals(update.getDate())){
				int year = Integer.parseInt(update.getYear());
				int month = Integer.parseInt(update.getMonth());
				int day = Integer.parseInt(update.getDay());

				ArrayList<Integer> monthsWith31Days = new ArrayList<Integer>(){{
					add(1);
					add(3);
					add(5);
					add(7);
					add(8);
					add(10);
					add(12);
				}};

				if(year < 1000 || year > Calendar.getInstance().get(Calendar.YEAR))
					return "Invalid year!\n";
				if(month < 1 || month > 12)
					return "Invalid month!\n";
				if(day < 1 || day > 31)
					return "Invalid day!\n";

				if((day == 31) && !(monthsWith31Days.indexOf(month) >= 0))
					return "That month does not have 31 days.";
				if((month == 2) && (day > 29))
					return "February has at most 29 days.";
				if((month == 2) && (day == 29) && (year % 4 != 0))
					return update.getYear() + " was not a leap year.";

				query += "date = '" + update.getYear() + "-" + update.getMonth() + "-" + update.getDay() + "', ";
			}//end date update

			//update time
			if(!original.getTime().equals(update.getTime())){
				System.out.println(original.getTime() + "!=" + update.getTime());
				int hours = Integer.parseInt(update.getHours());
				int mins = Integer.parseInt(update.getMins());

				if(hours < 0 && hours > 23)
					return "Invalid hours!\n";
				if(mins < 0 && mins > 59)
					return "Invalid minutes!\n";

				query += "time = '" + update.getHours()+":"+update.getMins()+":"+"00'" + ", ";
			}//end update time


			if(query.endsWith(", ")){
				query = query.substring(0,query.length()-2) + " ";
				query += "WHERE caseID = " + original.getId() + " ";

				query += ";";

				System.out.println(query);
				Statement stmt = this.sqlConnection.createStatement();
				stmt.execute(query);
			}


			//log status changes
			if(statusChanged){ //TODO: fix this crap
				if(original.getStatus().equals("open")){
					closeLog.setInt(1, userID);
					closeLog.setInt(2, original.getId());
					closeLog.execute();
				}else{
					openLog.setInt(1, userID);
					openLog.setInt(2, original.getId());
					openLog.execute();
				}

			}

			//update category
			if(categoryChanged){
				categoryByName.setString(1, original.getCategory());
				ResultSet rs = categoryByName.executeQuery();

				int oldCatID = -1;
				if(rs.next()){
					oldCatID = new Category(rs).getID();
					catUnlinkByCaseID.setInt(1, oldCatID);
					catUnlinkByCaseID.setInt(2, original.getId());
					catUnlinkByCaseID.execute();
				}

				catLinkToCaseID.setInt(1, catID);
				catLinkToCaseID.setInt(2, original.getId());
				catLinkToCaseID.execute();
			}

		}catch(Exception ex){
			ex.printStackTrace();
			return "Something went wrong. Please try again and contact us if issue persists.";
		}

		return null;
	}

	/**
	 * inserts a category into the database.
	 * @param name the new category's name
	 * @return the new category's catID
	 */
	private int insertCategory(String name){
		try{
			ResultSet rs;

			categoryByName.setString(1, name);
			rs=categoryByName.executeQuery();
			if(rs.next())
				return rs.getInt("catID");

			categoryInsert.setString(1, name);
			categoryInsert.execute();

			rs=categoryByName.executeQuery();
			if(!rs.next())
				return -1;

			return rs.getInt("catID");
		}catch(Exception ex){
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * Creates a list of all categories currently stored in the database.
	 * @return said list
	 */
	private List<Category> getCategories(){
		try{
			final ResultSet rs = categoryAll.executeQuery();

			final List<Category>categories = new ArrayList<Category>();

			while(rs.next())
				categories.add(new Category(rs));

			return categories;
		}catch(Exception ex){
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
			e.printStackTrace();
		}
		return null;

	}

}
