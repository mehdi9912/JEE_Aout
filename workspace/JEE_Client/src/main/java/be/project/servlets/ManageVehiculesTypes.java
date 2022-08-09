package be.project.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.VehiculeType;

public class ManageVehiculesTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ManageVehiculesTypes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String servletPath = request.getServletPath().substring(1);
			if(servletPath.equals("modifyvehicule") && request.getParameter("id")!= null) {
				VehiculeType vehicule = VehiculeType.getVehicule(request.getParameter("id"));
				request.setAttribute("vehicule", vehicule);
				request.getRequestDispatcher("/WEB-INF/JSP/admin/modifyVehiculeType.jsp").forward(request,response);
				return;
			}
			if(servletPath.equals("deletevehicule") && request.getParameter("id")!= null ) {
				VehiculeType vehicule = VehiculeType.getVehicule(request.getParameter("id"));
				request.setAttribute("vehicule", vehicule);
				request.getRequestDispatcher("/WEB-INF/JSP/admin/deleteVehiculeType.jsp").forward(request,response);
				return;
			}
			if(servletPath.equals("addvehicule")) {
				request.getRequestDispatcher("/WEB-INF/JSP/admin/createVehiculeType.jsp").forward(request,response);
				return;
			}
		}catch(Exception e) {
			System.out.println("Exception dans managevehiculestype doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VehiculeType vehicule;
		String message="";
		try {
			//modify case
			if(request.getServletPath().substring(1).equals("modifyvehicule")) {
				if(request.getParameter("id")!=null && request.getParameter("type")!=null ) {
					int id  =Integer.valueOf(request.getParameter("id"));
					String type= request.getParameter("type");
					vehicule = new VehiculeType(id, type);
					boolean success = vehicule.update();
					if(success) {
						message="Vehicule mis à jour";
					}
					else {
						message="Le vehicule n'a pas été mis à jour";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("vehicules").forward(request, response);
					return;
				}
				response.sendRedirect("vehicules");
			}
			//delete case
			if(request.getServletPath().substring(1).equals("deletevehicule")) {
				boolean confirm = Boolean.valueOf(request.getParameter("confirm"));
				if(confirm && request.getParameter("id")!=null) {
					boolean success = VehiculeType.delete(request.getParameter("id"));
					if(success) {
						message="Vehicule supprimé";
					}
					else {
						message="Le vehicule n'a pas pu être supprimé";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("vehicules").forward(request, response);
					return;
				}
			}
			//create case
			if(request.getServletPath().substring(1).equals("addvehicule")) {
				if(request.getParameter("type")!=null) {
					String type =request.getParameter("type");
					vehicule = new VehiculeType(type);
					boolean success = vehicule.insert();
					if(success) {
						message="Véhicule crée";
					}
					else {
						message="Le véhicule n'a pas être crée";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("vehicules").forward(request, response);
					return;
					}
				}
		}
		catch(Exception e) {
			System.out.println("Exception dans managevehiculestype dopost :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}

}
