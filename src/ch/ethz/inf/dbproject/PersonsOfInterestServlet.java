package ch.ethz.inf.dbproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.PersonOfInterest;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class PersonsOfInterest
 */

@WebServlet(description = "Persons of interest.", urlPatterns = { "/PersonsOfInterest" })
public class PersonsOfInterestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		final HttpSession session = request.getSession(true);
		final DatastoreInterface dsi = new DatastoreInterface();
		final BeanTableHelper<PersonOfInterest> table = new BeanTableHelper<PersonOfInterest>("personsOfInterest", 
				"casesTable", PersonOfInterest.class);

		// Add columns to the new table

		table.addBeanColumn("First name", "firstName");
		table.addBeanColumn("Last name", "lastName");
		table.addBeanColumn("Birthday", "birthDay");
		table.addBeanColumn("Is Alive?", "alive");
		table.addLinkColumn("",	"View Person"	, "PersonOfInterest?id=", "Id");

		session.setAttribute("personsOfInterest", table);
		
		table.addObjects(dsi.getAllPersonsOfInterest());
		
		this.getServletContext().getRequestDispatcher("/PersonsOfInterest.jsp").forward(request, response);
	}


}
