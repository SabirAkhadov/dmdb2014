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
import ch.ethz.inf.dbproject.model.PersonOfInterest;


@WebServlet(description = "Edit person of interest", urlPatterns = { "/PersonEdit" })
public class PersonEditSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PersonEditSerlvet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		final HttpSession session = request.getSession(true);
		DatastoreInterface dsi = new DatastoreInterface();
		String action = request.getParameter("action");

		if(action != null && action.equals("editPerson")){
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String alive = request.getParameter("alive");
			if (alive == null){
				alive = "0";
			}
			
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
				session.setAttribute("editPersonError", "Invalid year!");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			if(month < 1 || month > 12){ //should never happen
				session.setAttribute("editPersonError", "Invalid month!");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			if(day < 1 || day > 31){ //should never happen
				session.setAttribute("editPersonError", "Invalid day!");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}

			if((day == 31) && !(monthsWith31Days.indexOf(month) >= 0)){ //should never happen
				session.setAttribute("editPersonError", "This month does not have 31 days.");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			if((month == 2) && (day > 29)){ //may happen
				session.setAttribute("editPersonError", "February has at most 29 days.");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			if((month == 2) && (day == 29) && ((((year % 4 != 0) || (year % 100 == 0)) && (year % 400 != 0)))){ //may happen
				session.setAttribute("editPersonError", year + " was not a leap year.");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			
			if(firstname.isEmpty()){
				session.setAttribute("editPersonError", "You need to enter the first name.");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			if(lastname.isEmpty()){
				session.setAttribute("editPersonError", "You need to enter the last name.");
				this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
				return;
			}
			
			String birthday = year + "-" + month + "-" + day;
			
			if (dsi.editPerson(firstname, lastname, birthday, alive, ((PersonOfInterest)(session.getAttribute("personToEdit"))).getId())){
				session.setAttribute("PersonEdited", "true");
				PersonOfInterest p = (PersonOfInterest)session.getAttribute("personToEdit");
				

				request.setAttribute("id", p.getId());
				response.sendRedirect("/IntroDBProject/PersonOfInterest?id="+p.getId());
				//this.getServletContext().getRequestDispatcher("/PersonOfInterest").forward(request, response);
				return;
			}
			else{
				session.setAttribute("editPersonError", "Could not edit this person");
			}
			
		}
		
		this.getServletContext().getRequestDispatcher("/PersonEdit.jsp").forward(request, response);
	}
}
