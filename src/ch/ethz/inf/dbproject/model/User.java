package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a registered in user.
 */
public final class User {

	private final String userID;
	private final String username;
	private final String email;
	private final String password;
	
	
	//consider deleting this constructor
	public User(final String userID, final String username, final String email, final String password) {
		this.userID = userID;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public User(final ResultSet rs) throws SQLException {
		this.userID = rs.getString("userid");
		this.username = rs.getString("name");
		this.email = rs.getString("email");
		this.password  = rs.getString("password");
	}
	
	public String getUsername() {
		return username;
	}

	public String getName() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUserID() {
		return userID;
	}	
}
