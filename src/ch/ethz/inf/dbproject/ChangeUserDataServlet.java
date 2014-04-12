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


@WebServlet(description = "Chage User data", urlPatterns = { "/ChangeData" })
public class ChangeUserDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUserDataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher("/ChangeData.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final HttpSession session = request.getSession(true);
		DatastoreInterface dsi = new DatastoreInterface();
		User user = (User) session.getAttribute("user");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		if (username.equals("") | password.equals("") |email.equals(""))
		{
			session.setAttribute("ChangeError", "true");
			this.getServletContext().getRequestDispatcher("/ChangeData.jsp").forward(request, response);
		}
		else {
			User newUser = dsi.changeData(user, username, email, password);
			if (newUser == null){
				session.setAttribute("ChangeError", "true");
	
				this.getServletContext().getRequestDispatcher("/ChangeData.jsp").forward(request, response);
			}
			else {
				session.setAttribute("user", null);
				session.setAttribute("ChangeError", "none");
				this.getServletContext().getRequestDispatcher("/ChangeData.jsp").forward(request, response);
			}
		}
	}

}
