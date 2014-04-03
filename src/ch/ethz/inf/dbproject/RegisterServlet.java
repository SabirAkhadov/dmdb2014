package ch.ethz.inf.dbproject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(description = "User registration", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public static String SESSION_USER_REGISTERED = "";
	
	public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);
		DatastoreInterface dsi = new DatastoreInterface();
		try {
			
			List<User> users = new ArrayList<User>();
			users = dsi.getAllUsers();
		
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");

			// check for existing user
			
			//indicates if a user is already in db
			boolean reg = false;
			for (User user : users)
			{
				if (user.getName().equals(username) || user.getEmail().equals(email)) {
					session.setAttribute("error", "register");
					reg = true;
					this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
					break;
				}
			}
			if (reg) {}
			else if (username == "" || email == "" || password == ""){
				
				session.setAttribute("error", "emptyString");
				this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
			}
			else {
			// set attribute User details
			
				final BeanTableHelper<User> userDetails = new BeanTableHelper<User>("userDetails", "userDetails", User.class);
				userDetails.addBeanColumn(username, "username");
				userDetails.addBeanColumn(email, "email");
				userDetails.addBeanColumn(password, "password");

				session.setAttribute(UserServlet.SESSION_USER_DETAILS, userDetails);
				
				User user = new User (username, email, password);
				
				
				//now insert a new user to db
				
				dsi.insertUser(username, email, password);
				
				//TODO implement sending email, if we have time
				
				session.setAttribute("user", user);
				session.setAttribute("error", "none");
				this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
