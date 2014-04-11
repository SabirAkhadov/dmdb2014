package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.model.NewCaseData;

/**
 * Servlet implementation class newCaseServlet
 */
@WebServlet(description = "open new case", urlPatterns = { "/NewCase" })
public class NewCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	final DatastoreInterface dbInterface;
	
    public NewCaseServlet() {
        super();
        dbInterface = new DatastoreInterface();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		final HttpSession session = request.getSession(true);
		String action = request.getParameter("action");
		
		if(action != null && action.equals("newCase")){
			User user = (User)session.getAttribute("user");
			
			if(user == null){
				session.setAttribute("newCaseError", "You cannot open new cases while you are not logged in.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			
			int day,month, year, hours, mins;
			String title,location,description,category;
			
			ArrayList<Integer> monthsWith31Days = new ArrayList<Integer>(){{
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
			String hoursStr = (String)request.getParameter("hours");
			String minsStr = (String)request.getParameter("mins");
			
			day = dayStr == null ? -1 :Integer.parseInt(dayStr);
			month = monthStr == null ? -1 :Integer.parseInt(monthStr);
			year = yearStr == null ? -1 :Integer.parseInt(yearStr);
			hours = hoursStr == null ? -1 :Integer.parseInt(hoursStr);
			mins = minsStr == null ? -1 : Integer.parseInt(minsStr);
			
			title = ((String)request.getParameter("title")).replace("'", "\\'");//.replace("\"", "\\\"").replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
			location = ((String)request.getParameter("location")).replace("'", "\\'");//.replace("'", "\\'").replace("\"", "\\\"").replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
			category = ((String)request.getParameter("category")).replace("'", "\\'");//.replace("'", "\\'").replace("\"", "\\\"").replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
			description = ((String)request.getParameter("description")).replace("'", "\\'");//.replace("'", "\\'").replace("\"", "\\\"").replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
			
			NewCaseData nCase = new NewCaseData(Integer.parseInt(user.getUserID()), day, month, year, hours, mins, title, category, location, description);
			session.setAttribute("newCase", nCase);
			
			if(year < 1000 || year > Calendar.getInstance().get(Calendar.YEAR)){ //may happen
				session.setAttribute("newCaseError", "Invalid year!");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if(month < 1 || month > 12){ //should never happen
				session.setAttribute("newCaseError", "Invalid month!");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if(day < 1 || day > 31){ //should never happen
				session.setAttribute("newCaseError", "Invalid day!");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}

			if((day == 31) && !(monthsWith31Days.indexOf(month) >= 0)){ //should never happen
				session.setAttribute("newCaseError", "That month does not have 31 days.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if((month == 2) && (day > 29)){ //may happen
				session.setAttribute("newCaseError", "February has at most 29 days.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if((month == 2) && (day == 29) && (year % 4 != 0)){ //may happen
				session.setAttribute("newCaseError", year + " was not a leap year.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			
			if(hours < 0 && hours > 23){ //should never happen
				session.setAttribute("newCaseError", "Invalid hours!");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if(mins < 0 && mins > 59){ //should never happen
				session.setAttribute("newCaseError", "Invalid minutes!");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			
			if(title.isEmpty()){
				session.setAttribute("newCaseError", "You need to enter a case title.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if(category.isEmpty()){
				category = null; //we may not want to specify the category
			}
			if(location.isEmpty()){
				session.setAttribute("newCaseError", "You need to specify where the crime took place.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			if(description.isEmpty()){
				session.setAttribute("newCaseError", "You need to specify what happened.");
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			
			String error = this.dbInterface.insertNewCase(nCase);
			if(error != null){
				session.setAttribute("newCaseError", error);
				this.getServletContext().getRequestDispatcher("/CaseNew.jsp").forward(request, response);
				return;
			}
			
			session.setAttribute("homeMsg", "We have successfully created your new case.");
		}
		this.getServletContext().getRequestDispatcher("/Home").forward(request, response);
	}
}
