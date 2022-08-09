package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Chief;
import be.project.javabeans.Fine;


public class HomeChief extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public HomeChief() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			Chief chief =(Chief)session.getAttribute("connectedUser");
			ArrayList<Fine> fines = new ArrayList<Fine>();
			fines= Fine.getAllFinesBySupervisedAgents(chief.getSerialNumber());
			
			request.setAttribute("fines", fines);
			request.getRequestDispatcher("/WEB-INF/JSP/chief/homepageChief.jsp").forward(request, response);
		}
		catch(Exception e) {
			System.out.println("Exception dans homechiefservlet doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
