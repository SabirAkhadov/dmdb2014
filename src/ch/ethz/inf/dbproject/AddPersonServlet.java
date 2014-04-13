package ch.ethz.inf.dbproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.PersonOfInterest;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;
import ch.ethz.inf.dbproject.model.User;

/**
 * Servlet implementation class AddPersonServlet
 */
@WebServlet("/AddPersonOfInterest")
public class AddPersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dbInterface = new DatastoreInterface();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPersonServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession(true);
		
		session.setAttribute("results", "");
		final User user = (User) session.getAttribute("user");
		if(user != null) {
		
			final String firstname = request.getParameter("firstname");
			final String lastname = request.getParameter("lastname");
			final String birthday = request.getParameter("birthday");
			final String alive = request.getParameter("alive");
			
			final String type = request.getParameter("type");
			final String caseID = request.getParameter("caseID");
			final String persID = request.getParameter("persID"); //this person will be searched for
			final String persRelatedID = request.getParameter("persRelatedID"); // to this person we add relative
	
			if(type != null && caseID != null && persID == null && firstname != null && lastname != null && birthday != null && alive != null){
				final BeanTableHelper<PersonOfInterest> table = new BeanTableHelper<PersonOfInterest>("personsOfInterest", 
						"casesTable", PersonOfInterest.class);
		
				// Add columns to the new table
		
				table.addBeanColumn("First name", "firstName");
				table.addBeanColumn("Last name", "lastName");
				table.addBeanColumn("Birthday", "birthDay");
				table.addBeanColumn("Is Alive?", "alive");
				table.addLinkColumn("",	"View Person"	, "PersonOfInterest?id=", "Id");
				table.addLinkColumn("",	"Select Person"	, "AddPersonOfInterest?caseID="+caseID+"&type="+type+"&persID=", "Id");
		
				session.setAttribute("results", table);
			
				table.addObjects(this.dbInterface.searchForPersons(firstname, lastname, birthday, alive));
			}
			
			if(type != null && persRelatedID != null && persID == null && firstname != null && lastname != null && birthday != null && alive != null){
				final BeanTableHelper<PersonOfInterest> table = new BeanTableHelper<PersonOfInterest>("personsOfInterest", 
						"casesTable", PersonOfInterest.class);
				
				// Add columns to the new table
				
				table.addBeanColumn("First name", "firstName");
				table.addBeanColumn("Last name", "lastName");
				table.addBeanColumn("Birthday", "birthDay");
				table.addBeanColumn("Is Alive?", "alive");
				table.addLinkColumn("",	"View Person"	, "PersonOfInterest?id=", "Id");
				table.addLinkColumn("",	"Select Person"	, "AddPersonOfInterest?persRelatedID="+persRelatedID+"&type="+type+"&persID=", "Id");
				
				session.setAttribute("results", table);
				
				table.addObjects(this.dbInterface.searchForPersons(firstname, lastname, birthday, alive));
			}
		}
		this.getServletContext().getRequestDispatcher("/AddPersonOfInterest.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession(true);
		
		session.setAttribute("results", "Invalid query.");
		final User user = (User) session.getAttribute("user");
		
		final String type = request.getParameter("type");
		final String caseID = request.getParameter("caseID");
		final String persID = request.getParameter("persID"); // for this person we search
		final String persRelatedID = request.getParameter("persRelatedID"); // to this person we add relative
		
		final String reason = request.getParameter("reason");
		final String crime_type = request.getParameter("crime_type");
		final String sentence = request.getParameter("sentence");
		final String date = request.getParameter("date");
		final String enddate = request.getParameter("enddate");
		final String relationship = request.getParameter("relationship");
		
		if(user != null) {

			String db_response = "Invalid query.";
			
			if(caseID != null && persID != null){
				
				if(type.equals("convicts") && crime_type != null && sentence != null && date != null && enddate != null){//CaseID, PersID, type, sentence, date, enddate
					db_response = this.dbInterface.insertPersonCaseRelation(caseID, persID, type, crime_type, sentence, date, enddate);
				}else if (type.equals("victims")){//CaseID, PersID
					db_response = this.dbInterface.insertPersonCaseRelation(caseID, persID, type);
				}else if(type.equals("suspects") && reason != null){//CaseID, PersID, reason
					db_response = this.dbInterface.insertPersonCaseRelation(caseID, persID, type, reason);
				}else if(type.equals("witnesses")){//CaseID, PersID
					db_response = this.dbInterface.insertPersonCaseRelation(caseID, persID, type);
				}else if(type.equals("others") && reason != null){//CaseID, PersID, reason
					db_response = this.dbInterface.insertPersonCaseRelation(caseID, persID, type, reason);
				}
			}
			
			if (persRelatedID != null && persID != null){
				
				if(type.equals("relationship") && relationship != null){
					if (relationship.isEmpty()){
						db_response = "Please fill in relationship";
					}
					else{
						db_response = this.dbInterface.insertPersonPersonRelation(persRelatedID, persID, relationship);
					}
				}
			}
			session.setAttribute("results", db_response);
		}
		this.getServletContext().getRequestDispatcher("/AddPersonOfInterest.jsp").forward(request, response);
	}

}
