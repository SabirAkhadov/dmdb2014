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
import ch.ethz.inf.dbproject.model.User;
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
		final User user = (User) session.getAttribute("user");

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
		
		String type = request.getParameter("type");
		if(type == null){
			this.getServletContext().getRequestDispatcher("/Case").forward(request, response);
			return;
		}
		
		String status = request.getParameter("status");
		if(status == null){
			this.getServletContext().getRequestDispatcher("/Case").forward(request, response);
			return;
		}
		
		//Addition richnerb: Remove person!
		final String req_caseID = request.getParameter("caseID");
		final String req_persID = request.getParameter("persID");
		if(req_persID != null && req_caseID != null){
			this.dbInterface.deletePersonCaseRelation(req_caseID, req_persID, type);
		}
		//end addition
		
		session.setAttribute("lastCase", caseID);
		session.setAttribute("status", status);

		final BeanTableHelper<PersonOfInterest> personsTable = new BeanTableHelper<PersonOfInterest>(
				"persons" 		/* The table html id property */,
				"casesTable" /* The table html class property */,
				PersonOfInterest.class 	/* The class of the objects (rows) that will be displayed */
				);

		personsTable.addBeanColumn("First Name", "FirstName");
		personsTable.addBeanColumn("Last Name", "LastName");
		personsTable.addBeanColumn("Birthdate", "BirthDay");
		personsTable.addBeanColumn("still alive?", "alive");

		List<PersonOfInterest> personList = null;
		if(type.equals("convicts")){
			session.setAttribute("persType", "convicts");
			personsTable.addBeanColumn("Conv. type", "type");//date, enddate, type, sentence
			personsTable.addBeanColumn("Sentence", "sentence");
			personsTable.addBeanColumn("Date", "date");
			personsTable.addBeanColumn("End of sentence", "enddate");
			personList = this.dbInterface.getConvictsByCaseID(caseID);
		}else if (type.equals("victims")){
			session.setAttribute("persType", "victims");
			personList = this.dbInterface.getVictimsByCaseID(caseID);
		}else if(type.equals("suspects")){
			session.setAttribute("persType", "suspects");
			personsTable.addBeanColumn("Reason", "reason");//reason
			personList = this.dbInterface.getSuspectsByCaseID(caseID);
		}else if(type.equals("witnesses")){
			session.setAttribute("persType", "witnesses");
			personList = this.dbInterface.getWitnessesByCaseID(caseID);	
		}else if(type.equals("others")){
			session.setAttribute("persType", "others");
			personsTable.addBeanColumn("Reason", "reason");//reason
			personList = this.dbInterface.getOthersByCaseID(caseID);	
		}else{
			this.getServletContext().getRequestDispatcher("/Case").forward(request, response);
			return;
		}
		
		personsTable.addLinkColumn("",	"View Person"	, "PersonOfInterest?id=", "Id");
		if(user != null && status.equals("open")){
			personsTable.addLinkColumn("", "unlink", "CasePersons?id="+caseIDstr+"&status="+status+"&type="+type+"&caseID="+caseID+"&persID=","Id");
		}
		
		if(personList.isEmpty()){
			session.setAttribute("noPersons", "not null");
		}else{
			personsTable.addObjects(personList);
		}
		
		session.setAttribute("personsTable", personsTable);
		this.getServletContext().getRequestDispatcher("/CasePersons.jsp").forward(request, response);

	}

}
