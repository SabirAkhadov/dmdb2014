package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a registered in user.
 */
public final class User {

	private final String username;
	private final String email;
	private final String password;
	
	public User(final String username, final String email, final String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public User(final ResultSet rs) throws SQLException {
		// TODO These need to be adapted to your schema
		// TODO Extra properties need to be added
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
}
