package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.VehiculeType;


public class ListingVehiculesTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListingVehiculesTypes() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<VehiculeType> vehicules = VehiculeType.getAllVehicules();
			request.setAttribute("vehicules", vehicules);
			request.getRequestDispatcher("/WEB-INF/JSP/admin/listingVehiculesTypes.jsp").forward(request,response);
		}
		catch(Exception e) {
			System.out.println("Exception dans listingVehiculesTypesservlet doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
