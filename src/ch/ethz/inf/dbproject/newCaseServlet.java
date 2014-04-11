package ch.ethz.inf.dbproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;

/**
 * Servlet implementation class newCaseServlet
 */
@WebServlet(description = "open new case", urlPatterns = { "/newCase" })
public class newCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public newCaseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final DatastoreInterface dsi = new DatastoreInterface();
		final HttpSession session = request.getSession(true);
		String action = request.getParameter("action");
		
		if (action != null && action.trim().equals("newCase")) {
			if (dsi.openCase(request.getParameter("title"), request.getParameter("date"), 
					request.getParameter("time"), request.getParameter("description"), request.getParameter("location"))) {
				session.setAttribute("openCaseStatus", "true");
			}
			else{
				session.setAttribute("openCaseStatus", "false");
			}
			
		}
		this.getServletContext().getRequestDispatcher("/newCase.jsp").forward(request, response);
		
	}

}
