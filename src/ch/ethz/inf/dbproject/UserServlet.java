package ch.ethz.inf.dbproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

@WebServlet(description = "Page that displays the user login / logout options.", urlPatterns = { "/User" })
public final class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dsInterface = new DatastoreInterface();

	public final static String SESSION_USER_LOGGED_IN = "userLoggedIn";
	public final static String SESSION_USER_DETAILS = "userDetails";

	public UserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		
		final HttpSession session = request.getSession(true);
		String action = request.getParameter("action");

		User loggedUser = (User) session.getAttribute("user");
		
		if (action != null && action.trim().equals("logout") && loggedUser != null){
			session.invalidate();
		}
		
		if (loggedUser == null) {
			if (action != null && action.trim().equals("login")) {
				final String username = request.getParameter("username");
				final String password = request.getParameter("password");
				
				// Ask the data store interface if it knows this user
				User user = dsInterface.validateUser(username, password);
				if (user != null){
					session.setAttribute("user", user);
					final BeanTableHelper<User> userDetails = new BeanTableHelper<User>("userDetails", "userDetails", User.class);
					userDetails.addBeanColumn(user.getName(), "username");
					userDetails.addBeanColumn(user.getEmail(), "email");
					userDetails.addBeanColumn(user.getPassword(), "password");
					session.setAttribute(SESSION_USER_DETAILS, userDetails);
					
					//TODO show all the cases oppened by user
					/*
					final BeanTableHelper<User> userCases = new BeanTableHelper<User>("userDetails", "userDetails", User.class);
					userDetails.addBeanColumn(user.getName(), "username");
					userDetails.addBeanColumn(user.getEmail(), "email");
					userDetails.addBeanColumn(user.getPassword(), "password");
					session.setAttribute("userCases", userCases);
					*/
					
				}
				else {
					session.setAttribute("error", "login");
				}
			}
			
		} 
		this.getServletContext().getRequestDispatcher("/User.jsp").forward(request, response);

	}

}
