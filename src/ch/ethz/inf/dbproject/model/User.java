package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a registered user.
 */
public final class User {

	private final String userID;
	private final String username;
	private final String email;
	private final String password;
	
	
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
