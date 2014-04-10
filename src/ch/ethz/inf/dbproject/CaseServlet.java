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

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import ch.ethz.inf.dbproject.model.Conviction;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Case;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class of Case Details Page
 */
@WebServlet(description = "Displays a specific case.", urlPatterns = { "/Case" })
public final class CaseServlet extends HttpServlet {

	private final String CASE_EDIT = "/CaseEdit.jsp";

	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dbInterface = new DatastoreInterface();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaseServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);


		final String idString = request.getParameter("id");
		if (idString == null) {
			this.getServletContext().getRequestDispatcher("/Cases").forward(request, response);
			return;
		}

		final Integer id = Integer.parseInt(idString);

		if((User)session.getAttribute("user") == null) //TODO: make sure login is working again and remove this
			session.setAttribute("user", this.dbInterface.validateUser("Roger", "123"));
		
		User user = (User)session.getAttribute("user");

		String action = request.getParameter("action");

		Case origCase;
		Case eCase = null;

		if(action != null){
			origCase = (Case)session.getAttribute("case");

			switch(action){
			case "editCase":
				if(user == null){
					this.getServletContext().getRequestDispatcher("/Cases").forward(request, response);
					action = null;
					return;
				}
				if(origCase != null && origCase.getId() == id){
					this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
					action = null;
					return;
				}
				break;
			case "update":
				if(user == null){
					session.setAttribute("updateError", "You need to log in");
					session.setAttribute("edittedCase", eCase);
					this.getServletContext().getRequestDispatcher("CASE_EDIT").forward(request, response);
					return;
				}
				//(int id, int status, String title, String category, String description, String location, java.sql.Date date, java.sql.Time time)
				eCase = new Case(Integer.parseInt(request.getParameter("id")) , Integer.parseInt(request.getParameter("status")), request.getParameter("title"), request.getParameter("category"), request.getParameter("description"), request.getParameter("location"), String.format("%s-%s-%s", request.getParameter("year"),request.getParameter("month"),request.getParameter("day")), String.format("%s:%s", request.getParameter("hours"), request.getParameter("mins")));
				if(origCase != null && origCase.getId() == eCase.getId()){
					String error = this.dbInterface.updateCase(origCase, eCase, Integer.parseInt(user.getUserID()));
					if(error != null){
						session.setAttribute("updateError", error);
						session.setAttribute("edittedCase", eCase);
						this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
						return;
					}
					action = null;
				}
				break;
			default:
				break;
			}


		}


		try {
			final Case aCase = this.dbInterface.getCaseById(id);


			/*******************************************************
			 * Construct a table to present all properties of a case
			 *******************************************************/
			final BeanTableHelper<Case> table = new BeanTableHelper<Case>(
					"cases" 		/* The table html id property */,
					"casesTable" /* The table html class property */,
					Case.class 	/* The class of the objects (rows) that will be displayed */
					);

			// Add columns to the new table			
			table.addBeanColumn("Title", "title");
			table.addBeanColumn("Status", "status");
			table.addBeanColumn("Category", "category");
			table.addBeanColumn("Location", "location");
			table.addBeanColumn("Date", "date");
			table.addBeanColumn("Time", "time");
			table.addBeanColumn("Description", "description");


			table.addObject(aCase);
			table.setVertical(true);			

			session.setAttribute("caseTable", table);
			session.setAttribute("case", aCase);

		} catch (final Exception ex) {
			action = null;
			ex.printStackTrace();
			this.getServletContext().getRequestDispatcher("/Cases.jsp").forward(request, response);
			return;
		}

		if(action != null){
			switch(action){
			case "editCase":
				this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
				return;
			case "update":
				if(eCase == null){ //should not happen
					session.setAttribute("updateError", "Unexpected Error - Please try again.");
					this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
					return;
				}
				String error = this.dbInterface.updateCase((Case)session.getAttribute("case"), eCase, Integer.parseInt(user.getUserID()));
				if(error != null){
					session.setAttribute("updateError", error);
					session.setAttribute("edittedCase", eCase);
					this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
				}
				return;
			default:
				break;
			}
		}else
			this.getServletContext().getRequestDispatcher("/Case.jsp").forward(request, response);
	}
}
