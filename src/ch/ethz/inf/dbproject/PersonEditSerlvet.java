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


@WebServlet(description = "Edit person of interest", urlPatterns = { "/PersonEdit" })
public class PersonEditSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PersonEditSerlvet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		final HttpSession session = request.getSession(true);
		DatastoreInterface dsi = new DatastoreInterface();
		User user = (User) session.getAttribute("user");
		final String idString = request.getParameter("id");
		
		this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
	}
}
