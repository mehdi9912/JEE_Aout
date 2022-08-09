package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.InfractionType;

public class ListingInfractionsTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListingInfractionsTypes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<InfractionType> infractions = InfractionType.getAllInfractions();
			request.setAttribute("infractions", infractions);
			request.getRequestDispatcher("/WEB-INF/JSP/admin/listingInfractionsTypes.jsp").forward(request,response);
		}
		catch(Exception e) {
			System.out.println("Exception listingInfractionsTypesservlet doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
