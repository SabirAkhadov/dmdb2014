package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a category of project (i.e. Theft, Assault...) 
 */
public final class Category {

	private final int id;
	private final String name;

	public Category(final int id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Category(final ResultSet rs) throws SQLException{
		this(rs.getInt("catID"), rs.getString("name"));
	}

	public int getID() {
		return id;
	}

	public final String getName() {
		return name;
	}
	
}
