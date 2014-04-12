package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;

@WebServlet(description = "Add new person of interest.", urlPatterns = { "/NewPerson" })
public final class NewPersonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public NewPersonServlet() {
		super();
	}

	protected final void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		

		final DatastoreInterface dsi = new DatastoreInterface();
		final HttpSession session = request.getSession(true);
		String action = request.getParameter("action");

		if(action != null && action.equals("add")){
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String alive = request.getParameter("alive");
			
			ArrayList<Integer> monthsWith31Days = new ArrayList<Integer>(){
				private static final long serialVersionUID = 1L;
			{
				add(1);
				add(3);
				add(5);
				add(7);
				add(8);
				add(10);
				add(12);
			}};
			
			String dayStr = (String)request.getParameter("day");
			String monthStr = (String)request.getParameter("month");
			String yearStr = (String)request.getParameter("year");
			
			int day = dayStr == null ? -1 :Integer.parseInt(dayStr);
			int month = monthStr == null ? -1 :Integer.parseInt(monthStr);
			int year = yearStr == null ? -1 :Integer.parseInt(yearStr);

			if(year < 1000 || year > Calendar.getInstance().get(Calendar.YEAR)){ //may happen
				session.setAttribute("newPersonError", "Invalid year!");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			if(month < 1 || month > 12){ //should never happen
				session.setAttribute("newPersonError", "Invalid month!");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			if(day < 1 || day > 31){ //should never happen
				session.setAttribute("newPersonError", "Invalid day!");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}

			if((day == 31) && !(monthsWith31Days.indexOf(month) >= 0)){ //should never happen
				session.setAttribute("newPersonError", "This month does not have 31 days.");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			if((month == 2) && (day > 29)){ //may happen
				session.setAttribute("newPersonError", "February has at most 29 days.");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			if((month == 2) && (day == 29) && ((((year % 4 != 0) || (year % 100 == 0)) && (year % 400 != 0)))){ //may happen
				session.setAttribute("newPersonError", year + " was not a leap year.");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			
			if(firstname.isEmpty()){
				session.setAttribute("newPersonError", "You need to enter the first name.");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			if(lastname.isEmpty()){
				session.setAttribute("newPersonError", "You need to enter the last name.");
				this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
				return;
			}
			
			String birthday = year + "-" + month + "-" + day;
			
			if (dsi.insertPerson(firstname, lastname, birthday, alive)){
				session.setAttribute("newPersonBorn", "true");
			}
			else{
				session.setAttribute("newPersonError", "Could not add a new person");
			}
			
		}
		
		this.getServletContext().getRequestDispatcher("/NewPerson.jsp").forward(request, response);
		
}
}
