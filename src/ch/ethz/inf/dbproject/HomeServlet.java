package ch.ethz.inf.dbproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class HomePage
 */
@WebServlet(description = "The home page of the project", urlPatterns = { "/Home" })
public final class HomeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final HttpSession session = request.getSession(true);
		
		String action = (String)request.getAttribute("action");
		
		if(action != null && action.equals("newCase"))
			session.setAttribute("homeMsg", "We have successfully created your new case.");
		
        this.getServletContext().getRequestDispatcher("/Home.jsp").forward(request, response);	        
	}
}