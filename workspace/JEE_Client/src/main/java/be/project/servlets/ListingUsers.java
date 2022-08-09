package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.User;

public class ListingUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListingUsers() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			ArrayList<User> users = User.getAllUsers();
			request.setAttribute("users", users);
			request.getRequestDispatcher("/WEB-INF/JSP/admin/listingUsers.jsp").forward(request,response);
		}
		catch(Exception e) {
			System.out.println("Exception dans listingUserservlet doget :" +e.getMessage());
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
