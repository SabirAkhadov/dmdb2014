package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a user comment.
 */
public class Comment {

	private final String username;
	private final String comment;
	
	public Comment(final String username, final String comment) {
		this.username = username == null ? "deleted user" : username;
		this.comment = comment == null ? "" : comment; //actually should not be able to be null
	}
	
	public Comment(final ResultSet rs) throws SQLException{
		this(rs.getString("username"), rs.getString("comment"));
	}

	public String getUsername() {
		return username;
	}

	public String getComment() {
		return comment;
	}	
}
