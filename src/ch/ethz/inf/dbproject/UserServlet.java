package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.Case;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

@WebServlet(description = "Page that displays the user login / logout options and user details.", urlPatterns = { "/User" })
public final class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dsInterface = new DatastoreInterface();

	public final static String SESSION_USER_DETAILS = "userDetails";

	public UserServlet() {
		super();
	}

	protected final void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		
		final HttpSession session = request.getSession(true);
		String action = request.getParameter("action");

		User user = (User) session.getAttribute("user");
		
		if (action != null && action.trim().equals("logout") && user != null){
			user = null;
			session.invalidate();
		}	
		if (user != null)
		{
			updateUserTables (user, session);
		}
		this.getServletContext().getRequestDispatcher("/User.jsp").forward(request, response);
		
	}
	
	protected final void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		final HttpSession session = request.getSession(true);
		String action = request.getParameter("action");

		User loggedUser = (User) session.getAttribute("user");
		
		if (loggedUser == null) {
			if (action != null && action.trim().equals("login")) {
				final String username = request.getParameter("username");
				final String password = request.getParameter("password");
				
				// Ask the data store interface if it knows this user
				User user = dsInterface.validateUser(username, password);
				if (user != null){
					session.setAttribute("user", user);
					updateUserTables (user, session);
				}
				else {
					session.setAttribute("error", "login");
				}
			}
		}
		this.getServletContext().getRequestDispatcher("/User.jsp").forward(request, response);
	}
	
	private void updateUserTables (User user, HttpSession session){
		
		List<Case> cases = dsInterface.getUserCases(user);
		//display user info
		final BeanTableHelper<User> userDetails = new BeanTableHelper<User>("userDetails", "userDetails", User.class);
		userDetails.addBeanColumn("Username", "Name");
		userDetails.addBeanColumn("Email", "Email");
		//userDetails.addBeanColumn("Password", "Password"); don't display password
		userDetails.addObject(user);
		session.setAttribute(SESSION_USER_DETAILS, userDetails.generateHtmlCode());
		
		//display cases opened by user
		final BeanTableHelper<Case> oCasesTable = new BeanTableHelper<Case>("cases", "casesTable", Case.class);
		oCasesTable.addBeanColumn("Title", "Title");
		oCasesTable.addBeanColumn("Category", "Category");
		oCasesTable.addBeanColumn("Date", "Date");
		oCasesTable.addBeanColumn("Time", "Time");
		oCasesTable.addBeanColumn("Location", "Location");
		oCasesTable.addBeanColumn("Status", "Status");
		oCasesTable.addBeanColumn("last change", "lastStatusChange");
		oCasesTable.addLinkColumn("", "View Case" ,"Case?id=", "id");

		oCasesTable.addObjects(cases);
		session.setAttribute("userCases", oCasesTable);
	}

}
