package ch.ethz.inf.dbproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersonsOfInterest
 */

@WebServlet(description = "Persons of interest.", urlPatterns = { "/PersonsOfInterest" })
public class PersonsOfInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		dsi
		this.getServletContext().getRequestDispatcher("/PersonsOfInterest.jsp").forward(request, response);
	}


}
