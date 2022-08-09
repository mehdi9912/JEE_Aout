package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.FineStatus;
import be.project.javabeans.Fine;


public class FineDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FineDetails() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if(request.getParameter("id")!=null){
				String fineId = (String)request.getParameter("id");
				Fine fine = Fine.getFine(fineId);
				request.setAttribute("fine", fine);
				session.setAttribute("fine", fine);
				request.getRequestDispatcher("/WEB-INF/JSP/fineDetails.jsp").forward(request, response);
				return;
			}
			response.sendRedirect("chief");
		}catch(Exception e) {
			System.out.println("Exception dans finedetails doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String message="";
			if(request.getParameter("fineid")!=null) {
				int fineId = Integer.valueOf(request.getParameter("fineid"));
				Fine fine = (Fine)session.getAttribute("fine");
				//validated fine status
				if(request.getParameter("action")!=null && request.getParameter("action").equals("valide")) {
					if(fineId == fine.getFineId()) {
						fine.setStatus(FineStatus.validated);
						boolean success=fine.changeStatusToValidated();
						if(success) {
							message="L'amende a bien été validée";
						}else {
							message="L'amende n'a pas pu être validée";
						}
						request.setAttribute("message", message);
						request.getRequestDispatcher("chief").forward(request, response);
						return;
					}
				}else {
					if(fineId == fine.getFineId()) {
						boolean success=Fine.delete(String.valueOf(fineId));
						if(success) {
							message="L'amende a bien été supprimée";
						}else {
							message="L'amende n'a pas pu être supprimée";
						}
						request.setAttribute("message", message);
						request.getRequestDispatcher("chief").forward(request, response);
						return;
					}
				}
				response.sendRedirect("chief");

			}
			doGet(request, response);
		}
		catch(Exception e) {
			System.out.println("Exception dans finedetails doPost :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}
}
