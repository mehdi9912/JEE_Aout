package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.Fine;

public class HomeCollector extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public HomeCollector() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Fine> fines = new ArrayList<Fine>();
			fines= Fine.getAllValidatedFines();
			request.setAttribute("fines", fines);
			request.getRequestDispatcher("/WEB-INF/JSP/collector/homepageCollector.jsp").forward(request, response);
			return;
		}
		catch(Exception e) {
			System.out.println("Exception dans homecollectorservlet doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
