package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.InfractionType;

public class ManageInfractionsTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ManageInfractionsTypes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String servletPath = request.getServletPath().substring(1);
			if(servletPath.equals("modifyinfraction") && request.getParameter("id") !=null ) {
				InfractionType infraction = InfractionType.getInfraction(request.getParameter("id"));
				request.setAttribute("infraction", infraction);
				request.getRequestDispatcher("/WEB-INF/JSP/admin/modifyInfractionType.jsp").forward(request,response);
				return;
			}
			if(servletPath.equals("deleteinfraction") && request.getParameter("id") !=null  ) {
				InfractionType infraction = InfractionType.getInfraction(request.getParameter("id"));
				request.setAttribute("infraction", infraction);
				request.getRequestDispatcher("/WEB-INF/JSP/admin/deleteInfractionType.jsp").forward(request,response);
				return;
			}
			if(servletPath.equals("addinfraction")) {
				request.getRequestDispatcher("/WEB-INF/JSP/admin/createInfractionType.jsp").forward(request,response);
				return;
			}
		}catch(Exception e) {
			System.out.println("Exception dans manageinfractionstype doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InfractionType infraction;
		String message="";
		try {
			//modify case
			if(request.getServletPath().substring(1).equals("modifyinfraction")) {
				if(request.getParameter("id")!=null && request.getParameter("type")!=null && request.getParameter("amount")!=null  ) {
					int id  =Integer.valueOf(request.getParameter("id"));
					String type= request.getParameter("type");
					double amount= Double.valueOf(request.getParameter("amount"));
					infraction = new InfractionType(id, type, amount);
					boolean success = infraction.update();
					if(success) {
						message="Infraction mise � jour";
					}
					else {
						message="L'infraction n'a pas �t� mise � jour";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("infractions").forward(request, response);
					return;
				}
				response.sendRedirect("infractions");
			}
			//delete case
			if(request.getServletPath().substring(1).equals("deleteinfraction")) {
				boolean confirm = Boolean.valueOf(request.getParameter("confirm"));
				if(confirm && request.getParameter("id")!=null) {
					boolean success = InfractionType.delete(request.getParameter("id"));
					if(success) {
						message="Infraction supprim�";
					}
					else {
						message="L'infraction n'a pas pu �tre supprim�e";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("infractions").forward(request, response);
					return;
				}
			}
			//create case
			if(request.getServletPath().substring(1).equals("addinfraction")) {
				if(request.getParameter("type")!=null) {
					String type =request.getParameter("type");
					double amount= Double.valueOf(request.getParameter("amount"));
					infraction = new InfractionType(type, amount);
					boolean success = infraction.insert();
					if(success) {
						message="Infraction cr�ee";
					}
					else {
						message="L'infraction n'a pas �tre cr�ee";
					}
					request.setAttribute("message", message);
					request.getRequestDispatcher("infractions").forward(request, response);
					return;
					}
				}
		}
		catch(Exception e) {
			System.out.println("Exception dans manageinfractionstype dopost :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}

}
