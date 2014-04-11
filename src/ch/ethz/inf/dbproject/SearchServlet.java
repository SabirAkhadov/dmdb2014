package ch.ethz.inf.dbproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.Case;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class Search
 */
@WebServlet(description = "The search page for cases", urlPatterns = { "/Search" })
public final class SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dbInterface = new DatastoreInterface();
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final HttpSession session = request.getSession(true);
		
		/*******************************************************
		 * Construct a table to present all our search results
		 *******************************************************/
		final BeanTableHelper<Case> table = new BeanTableHelper<Case>(
				"cases", 		//The table html id property 
				"casesTable", //The table html class property
				Case.class 	//The class of the objects (rows) that will bedisplayed
		);

		// Add columns to the new table

		table.addBeanColumn("Title", "title");
		table.addBeanColumn("Status", "status");
		table.addBeanColumn("Category", "category");
		table.addBeanColumn("Location", "location");
		table.addBeanColumn("Date", "date");

		/*
		 * Column 4: This is a special column. It adds a link to view the
		 * Case. We need to pass the case identifier to the url.
		 */
		table.addLinkColumn(""	/* The header. We will leave it empty */,
				"View Case" 	/* What should be displayed in every row */,
				"Case?id=" 	/* This is the base url. The final url will be composed from the concatenation of this and the parameter below */, 
				"id" 			/* For every case displayed, the ID will be retrieved and will be attached to the url base above */);

		// Pass the table to the session. This will allow the respective jsp page to display the table.
		session.setAttribute("results", table);

		final String firstname = request.getParameter("firstname");
		final String lastname = request.getParameter("lastname");
		final String category = request.getParameter("category");
		final String conv_date = request.getParameter("conv_date");
		final String conv_type = request.getParameter("conv_type");
		
		if(firstname != null && lastname != null && category != null && conv_date != null && conv_type != null){
			//TODO: Query writing, correct dbInterface function.
			table.addObjects(this.dbInterface.searchForCases(firstname, lastname, category, conv_date, conv_type));
		}
		
		//*********This is obsolete TODO code.
		// The filter parameter defines what to show on the cases page
		//final String filter = request.getParameter("filter");

		//if (filter != null) {
		//if(filter.equals("description")) {
		//final String name = request.getParameter("name");
		//table.addObjects(this.dbInterface.searchByName(name));
		//************end of obsolete TODO code.

		// Finally, proceed to the Seaech.jsp page which will render the search results
        this.getServletContext().getRequestDispatcher("/Search.jsp").forward(request, response);	        
	}
}