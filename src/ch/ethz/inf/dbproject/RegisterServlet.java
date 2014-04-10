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
	
	public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);
		DatastoreInterface dsi = new DatastoreInterface();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		if (username == "" || email == "" || password == ""){
			
			session.setAttribute("error", "emptyString");
			this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
		}
		else {
			User user = dsi.insertUser(username, email, password);
			
			//TODO implement sending email, if we have time
			
			if (user == null){
				session.setAttribute("error", "register");
				this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
			}
				else {
				session.setAttribute("user", user);
				session.setAttribute("error", "none");
				this.getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
			}
		}
			
	}

}
