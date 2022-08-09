package be.project.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Fine;
import be.project.javabeans.InfractionType;
import be.project.javabeans.Insurance;
import be.project.javabeans.Policeman;
import be.project.javabeans.User;
import be.project.javabeans.VehiculeType;


public class CreateFine extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CreateFine() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//recup liste des infractions
			ArrayList<InfractionType> infractions = InfractionType.getAllInfractions();
			request.setAttribute("infractions", infractions);
			request.getRequestDispatcher("/WEB-INF/JSP/policeman/createFine_1.jsp").forward(request,response);
			return;
		}catch(Exception e) {
			System.out.println("Exception dans createFine doGet" + e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String firstname, lastname,message,comment,vehiculeTypeId,licensePlate;
		firstname=lastname=message=comment=vehiculeTypeId=licensePlate="";
		boolean inOrder =true;
		VehiculeType vehicule;
		InfractionType infraction;
		Fine fine;
		ArrayList<Fine> fines = new ArrayList<Fine>();
		try {
			//choix de l'infraction ou cas d'un défaut d'assurance signalé
			if(request.getParameter("infractionType") != null || request.getParameter("infractiontypeid") != null) {
				String infractionTypeId= request.getParameter("infractionType");
				//infractionType recu après le premier formulaire en choissisant le type
				//infractiontypeid envoyé direct si défaut d'assurance
				if(infractionTypeId == null) {
					infractionTypeId=request.getParameter("infractiontypeid");
				}
				//Check si pas une amende successive sur même véhicule;
				if(session.getAttribute("fines")!=null) {
					System.out.println("Amende successive");
					//recupère les infos essentielles pour ne pas devoir les réencoder
					fines=(ArrayList<Fine>)session.getAttribute("fines");
					request.setAttribute("preinfos", fines.get(0));
				}
				infraction = InfractionType.getInfraction(infractionTypeId);
				ArrayList<VehiculeType> vehicules = VehiculeType.getAllVehicules();
				request.setAttribute("infraction", infraction);
				request.setAttribute("vehicules", vehicules);
				request.getRequestDispatcher("/WEB-INF/JSP/policeman/createFine_2.jsp").forward(request, response);
				return;
			}
			//envoi du form complet
			if(request.getParameter("infractionTypeReceive")!=null) {
				String infractionTypeId =request.getParameter("infractionTypeReceive");
				infraction = InfractionType.getInfraction(infractionTypeId);
				if(request.getParameter("comment")!=null && request.getParameter("timestamp")!=null && 
						request.getParameter("vehiculeType")!=null) {
					//set all variables
					comment = request.getParameter("comment");
					String timestampHTML = request.getParameter("timestamp");
					String timestampString = timestampHTML.replace("T", " ");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);	
					
					vehiculeTypeId=request.getParameter("vehiculeType");
					vehicule = VehiculeType.getVehicule(vehiculeTypeId);
					if(request.getParameter("licensePlate")!=null && !request.getParameter("licensePlate").isEmpty()) {
						licensePlate = request.getParameter("licensePlate");
					}
					if(request.getParameter("firstname")!=null && request.getParameter("lastname")!=null) {
						firstname = request.getParameter("firstname") ;
						lastname = request.getParameter("lastname");
					}
					User user = (User)session.getAttribute("connectedUser");
					
					fine = new Fine(timestamp,firstname, lastname, comment, vehicule, infraction, (Policeman)user,licensePlate);
					fine.calculFinePrice();
					boolean success = fine.insert();
					if(success) {
						message="Amende encodée";
						request.setAttribute("message", message);
						//Si plaque renseignée check si pas défaut d'assurance
						if(!licensePlate.isEmpty()) {
							inOrder = Insurance.checkInsurance(licensePlate);
						}
						request.setAttribute("inOrder" , inOrder);
						if(session.getAttribute("fines")!=null) {
							fines=(ArrayList<Fine>)session.getAttribute("fines");
						}
						fines.add(fine);
						session.setAttribute("fines", fines);
						request.getRequestDispatcher("/WEB-INF/JSP/policeman/validateFine.jsp").forward(request, response);
						return;
					}
					else {
						message="L'amende n'a pu pas être encodée";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/JSP/policeman/homepagePoliceman.jsp").forward(request, response);
					return;
				}	
			}
			response.sendRedirect("policeman");
		}catch(Exception e) {
			System.out.println("Exception dans createFine doPost" + e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}	
	}
}
