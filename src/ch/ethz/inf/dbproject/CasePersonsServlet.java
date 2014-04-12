package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Case;
import ch.ethz.inf.dbproject.model.PersonOfInterest;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

@WebServlet(description = "View Persons of Interest linked to a Case", urlPatterns = {"/CasePersons"})
public final class CasePersonsServlet extends  HttpServlet {

	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dbInterface;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CasePersonsServlet() {
		super();
		this.dbInterface = new DatastoreInterface();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException {

		final HttpSession session = request.getSession(true);

		String type = request.getParameter("type");

		if(type == null){
			this.getServletContext().getRequestDispatcher("/Case").forward(request, response);
			return;
		}

		String caseIDstr = request.getParameter("id");
		int caseID = -1;
		if(caseIDstr == null){
			this.getServletContext().getRequestDispatcher("/Home").forward(request, response);
			return;
		}else{
			caseID = Integer.parseInt(caseIDstr);
		}
		
		if(caseID < 0){
			this.getServletContext().getRequestDispatcher("/Home").forward(request, response);
			return;
		}
		
		session.setAttribute("lastCase", caseID);

		final BeanTableHelper<PersonOfInterest> personsTable = new BeanTableHelper<PersonOfInterest>(
				"persons" 		/* The table html id property */,
				"caseTable" /* The table html class property */,
				PersonOfInterest.class 	/* The class of the objects (rows) that will be displayed */
				);

		personsTable.addBeanColumn("First Name", "firstName");
		personsTable.addBeanColumn("Last Name", "lastName");
		personsTable.addBeanColumn("Birthdate", "birthDay");
		personsTable.addBeanColumn("still alive?", "alive");
		personsTable.addLinkColumn("",	"View Person"	, "PersonOfInterest?id=", "Id");

		List<PersonOfInterest> personList = null;
		if(type.equals("convicts")){
			session.setAttribute("persType", "convicts");
			personList = this.dbInterface.getConvictsByCaseID(caseID);
		}else if (type.equals("victims")){
			session.setAttribute("persType", "victims");
			personList = this.dbInterface.getVictimsByCaseID(caseID);
		}else if(type.equals("suspects")){
			session.setAttribute("persType", "suspects");
			personList = this.dbInterface.getSuspectsByCaseID(caseID);
		}else if(type.equals("witnesses")){
			session.setAttribute("persType", "witnesses");
			personList = this.dbInterface.getWitnessesByCaseID(caseID);	
		}else if(type.equals("others")){
			session.setAttribute("persType", "others");
			personList = this.dbInterface.getOthersByCaseID(caseID);	
		}else{
			this.getServletContext().getRequestDispatcher("/Case").forward(request, response);
			return;
		}
		
		if(personList.isEmpty()){
			session.setAttribute("noPersons", "not null");
		}else{
			session.setAttribute("noPersons", null);
		}
		
		session.setAttribute("personsTable", personsTable);
		this.getServletContext().getRequestDispatcher("/CasePersons.jsp").forward(request, response);

	}

}
