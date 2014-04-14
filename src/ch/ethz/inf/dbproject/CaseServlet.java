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
import ch.ethz.inf.dbproject.model.User;
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

		User user = (User)session.getAttribute("user");

		String action = request.getParameter("action");

		Case origCase;
		Case eCase = null;

		if(action != null){
			origCase = (Case)session.getAttribute("case");

			if(action.equals("editCase")){
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
			}else if(action.equals("update")){
				if(user == null){
					session.setAttribute("updateError", "You need to log in");
					session.setAttribute("edittedCase", eCase);
					this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
					return;
				}
				//(int id, int status, String title, String category, String description, String location, java.sql.Date date, java.sql.Time time)
				String sYear = (String)request.getParameter("year");
				String sMonth = (String)request.getParameter("month");
				String sDay = (String)request.getParameter("day");
				String sHours = (String)request.getParameter("hours");
				String sMins = (String)request.getParameter("mins");
				
				String sDate, sTime;
				
				if(sYear == null || sYear.isEmpty() || sMonth == null || sMonth.isEmpty() || sDay == null || sDay.isEmpty())
					sDate = null;
				else
					sDate = String.format("%s-%s-%s", sYear,sMonth,sDay);
				
				if(sHours == null || sHours.isEmpty() || sMins == null || sMins.isEmpty())
					sTime = null;
				else
					sTime = String.format("%s:%s", sHours, sMins);
				
				eCase = new Case(Integer.parseInt(request.getParameter("id")) , Integer.parseInt(request.getParameter("status")), request.getParameter("title"), request.getParameter("category"), request.getParameter("description"), request.getParameter("location"), sDate, sTime, null);
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
			}else if (action.equals("addComment")){
				String comment = request.getParameter("comment");
				if(user == null){
					session.setAttribute("caseCommentError", "You need to log in");
					session.setAttribute("newComment", comment == null ? "": comment);
				}

				String error = this.dbInterface.addCommentToCase(comment, Integer.parseInt(request.getParameter("id")), Integer.parseInt(user.getUserID()));

				if(error != null){
					session.setAttribute("caseCommentError", error);
					session.setAttribute("newComment", comment == null ? "": comment);
					this.getServletContext().getRequestDispatcher("/Case.jsp").forward(request, response);
					return;
				}
			}
		}//end action!= null


		try {
			final Case aCase = this.dbInterface.getCaseById(id);
			final List<Comment> commentList = this.dbInterface.getCommentsToCaseByID(id);


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
			table.addBeanColumn("Last Status Change", "lastStatusChange");
			table.addBeanColumn("Category", "category");
			table.addBeanColumn("Location", "location");
			table.addBeanColumn("Date", "date");
			table.addBeanColumn("Time", "time");
			table.addBeanColumn("Description", "description");


			table.addObject(aCase);
			table.setVertical(true);			

			session.setAttribute("caseTable", table);
			session.setAttribute("case", aCase);

			final BeanTableHelper<Comment> commentTable = new BeanTableHelper<Comment>(
					"comments" 		/* The table html id property */,
					"commentTable" /* The table html class property */,
					Comment.class 	/* The class of the objects (rows) that will be displayed */
					);

			commentTable.addBeanColumn("", "username");
			commentTable.addBeanColumn("", "comment");

			commentTable.addObjects(commentList);

			session.setAttribute("commentTable", commentTable);

		} catch (final Exception ex) {
			action = null;
			ex.printStackTrace();
			this.getServletContext().getRequestDispatcher("/Cases.jsp").forward(request, response);
			return;
		}

		if(action != null){
			if(action.equals("editCase")){
				this.getServletContext().getRequestDispatcher(CASE_EDIT).forward(request, response);
				return;
			}else if(action.equals("update")){
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
			}else if(action.equals("addComment")){
				this.getServletContext().getRequestDispatcher("/Case.jsp").forward(request, response);
			}//end action != null
		}else
			this.getServletContext().getRequestDispatcher("/Case.jsp").forward(request, response);
	}
}
