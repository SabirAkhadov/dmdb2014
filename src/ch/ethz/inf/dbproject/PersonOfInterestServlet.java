//comment

package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Case;
import ch.ethz.inf.dbproject.model.PersonOfInterest;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class of Case Details Page
 */
@WebServlet(description = "Displays a specific person.", urlPatterns = { "/PersonOfInterest" })
public final class PersonOfInterestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);

		final DatastoreInterface dsi = new DatastoreInterface();

		final String idString = request.getParameter("id");
		
		if (idString == null) {
			this.getServletContext().getRequestDispatcher("/PersonsOfInterest").forward(request, response);
			return;
		}
		
		User user = (User)session.getAttribute("user");
		String action = request.getParameter("action");
		if(action != null && action.equals("addNotes")){
			String notes = request.getParameter("personNotes");
			if (!dsi.addNotesToPerson(notes, idString, user.getUserID())){
				session.setAttribute("PersonCommented", "false");
			}
		}
		
		
		final BeanTableHelper<PersonOfInterest> personTable = new BeanTableHelper<PersonOfInterest>(
				"person" 		/* The table html id property */,
				"casesTable" /* The table html class property */,
				PersonOfInterest.class 	/* The class of the objects (rows) that will be displayed */
				);

		// Add columns to the new table			
		personTable.addBeanColumn("First name", "firstName");
		personTable.addBeanColumn("Last name", "lastName");
		personTable.addBeanColumn("Birthday", "birthDay");
		personTable.addBeanColumn("Is Alive?", "alive");
		personTable.addBeanColumn("Involved in case", "concernCase");
		personTable.addBeanColumn("Notes", "notes");
		personTable.addBeanColumn("Related to", "related");
		session.setAttribute("personsOfInterest", personTable);

		PersonOfInterest aPerson = dsi.getPersonById(idString);
		personTable.addObject(aPerson);
		personTable.setVertical(true);			
		session.setAttribute("personToEdit", aPerson);
		this.getServletContext().getRequestDispatcher("/PersonOfInterest.jsp").forward(request, response);
	}
}
