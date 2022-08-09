package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.Chief;
import be.project.javabeans.Collector;
import be.project.javabeans.PoliceArea;
import be.project.javabeans.Policeman;
import be.project.javabeans.User;


public class ManageUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ManageUsers() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String servletPath = request.getServletPath().substring(1);
			String serialNumber = request.getParameter("serialnumber");
			if(servletPath.equals("modifyuser") && serialNumber!= null ) {
				User user = User.getUser(serialNumber);
				ArrayList<Chief> chiefs = Chief.getAllChiefs();
				ArrayList<PoliceArea> policeAreas = PoliceArea.getAllPoliceAreas();
				request.setAttribute("user", user);
				request.setAttribute("policeareas", policeAreas);
				request.setAttribute("chiefs", chiefs);
				request.getRequestDispatcher("/WEB-INF/JSP/admin/modifyUser.jsp").forward(request,response);
				return;
			}
			if(servletPath.equals("deleteuser") && serialNumber!= null ) {
				User user = User.getUser(serialNumber);
				request.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/JSP/admin/deleteUser.jsp").forward(request,response);
				return;
			}
			if(servletPath.equals("adduser")) {
				request.getRequestDispatcher("/WEB-INF/JSP/admin/createUser_1.jsp").forward(request,response);
				return;
			}
		}catch(Exception e) {
			System.out.println("Exception dans manageuser doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message="";
		String password ="";
		String firstname ="";
		String lastname="";
		String chiefSerialNumber = "";
		int policeareaId;
		User user;
		try {
			//modify case
			if(request.getServletPath().substring(1).equals("modifyuser")) {
				if(request.getParameter("serialNumber")!=null) {
					String userSerialNumber = request.getParameter("serialNumber");
					user = User.getCorrectInstanceOfUser(userSerialNumber);
					if(request.getParameter("firstname")!=null && request.getParameter("lastname")!=null &&
							request.getParameter("policearea")!=null) {
						firstname = request.getParameter("firstname");
						lastname = request.getParameter("lastname");
						policeareaId = Integer.valueOf(request.getParameter("policearea"));
						PoliceArea policeArea = new PoliceArea();
						policeArea.setId(policeareaId);
						if(request.getParameter("check_password")!= null && request.getParameter("check_password").equals("on") &&
							 request.getParameter("password")!=null && !request.getParameter("password").isEmpty()) {
							 password = request.getParameter("password");
						}
						if(user instanceof Policeman) {
							chiefSerialNumber = request.getParameter("chief");
							Chief chief = new Chief();
							chief.setSerialNumber(chiefSerialNumber);
							Policeman policeman = new Policeman(userSerialNumber,firstname,lastname, password, policeArea, chief);
							user=(Policeman) policeman;
						}
						if(user instanceof Chief) {
							Chief chief = new Chief(userSerialNumber,firstname,lastname, password, policeArea);
							user= (Chief) chief;
						}
						if(user instanceof Collector) {
							Collector collector = new Collector(userSerialNumber,firstname,lastname, password, policeArea);	
							user = (Collector) collector;
						}
						boolean success = user.updateUser();
						if(success) {
							message="Utilisateur mis à jour";
						}
						else {
							message="L'utilisateur n'a pas été mis à jour";
						}
						request.setAttribute("message", message);
						request.getRequestDispatcher("users").forward(request, response);
						return;
					}
				}
				response.sendRedirect("users");
			}
			//delete case
			if(request.getServletPath().substring(1).equals("deleteuser")) {
				boolean confirm = Boolean.valueOf(request.getParameter("confirm"));
				if(confirm && request.getParameter("serialNumber")!=null) {
					boolean success = User.deleteUser(request.getParameter("serialNumber"));
					if(success) {
						message="Utilisateur supprimé";
					}
					else {
						message="L'utilisateur n'a pas pu être supprimé";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("users").forward(request, response);
					return;
				}
			}
			//create case
			if(request.getServletPath().substring(1).equals("adduser")) {
				//Choix du type d'user
				if(request.getParameter("userType") != null) {
					String userType =request.getParameter("userType");
					if(userType.equals("policeman"));
					{
						ArrayList<Chief> chiefs = Chief.getAllChiefs();
						request.setAttribute("chiefs", chiefs);
					}
					ArrayList<PoliceArea> policeAreas = PoliceArea.getAllPoliceAreas();
					request.setAttribute("policeareas", policeAreas);
					request.setAttribute("userType", userType);
					request.getRequestDispatcher("/WEB-INF/JSP/admin/createUser_2.jsp").forward(request, response);
					return;
				}
				//envoi du form complet
				if(request.getParameter("userTypeReceive")!=null) {
					String userType =request.getParameter("userTypeReceive");
					if(request.getParameter("firstname")!=null && request.getParameter("lastname")!=null &&
							request.getParameter("policearea")!=null &&
							request.getParameter("password")!=null) {
						user = null;
						firstname = request.getParameter("firstname");
						lastname = request.getParameter("lastname");
						policeareaId = Integer.valueOf(request.getParameter("policearea"));
						PoliceArea policeArea = new PoliceArea();
						policeArea.setId(policeareaId);
						password = request.getParameter("password");
						if(userType.equals("policeman")) {
							chiefSerialNumber = request.getParameter("chief");
							Chief chief = new Chief();
							chief.setSerialNumber(chiefSerialNumber);
							Policeman policeman = new Policeman(firstname,lastname, password, policeArea, chief);
							user=(Policeman) policeman;
						}
						if(userType.equals("chief")) {
							Chief chief = new Chief(firstname,lastname, password, policeArea);
							user= (Chief) chief;
						}
						if(userType.equals("collector")) {
							Collector collector = new Collector(firstname,lastname, password, policeArea);	
							user = (Collector) collector;
						}
						boolean success = user.insertUser();
						if(success) {
							message="Utilisateur crée";
						}
						else {
							message="L'utilisateur n'a pas pu être créée";
						}
						request.setAttribute("message", message);
						request.getRequestDispatcher("users").forward(request, response);
						return;
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans manageuser dopost :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}
}
