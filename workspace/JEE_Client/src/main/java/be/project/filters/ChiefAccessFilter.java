package be.project.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Admin;
import be.project.javabeans.Chief;
import be.project.javabeans.User;
import be.project.javabeans.Collector;


public class ChiefAccessFilter implements Filter {

	private FilterConfig filterConfig;
	
    public ChiefAccessFilter() {
        // TODO Auto-generated constructor stub
    }

	public void init(FilterConfig fConfig) throws ServletException {
		Filter.super.init(fConfig);
		this.filterConfig=fConfig;
	}
	
	public void destroy() {
		Filter.super.destroy();
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest)request;
		HttpSession session = req.getSession(false);
	    HttpServletResponse res = (HttpServletResponse)response;
		User user =(User)session.getAttribute("connectedUser");
		String path = req.getServletPath().substring(1);
		if(user instanceof Chief || (path.equals("infractions") && user instanceof Admin) || (path.equals("fine") && user instanceof Collector)) {
			try {
				chain.doFilter(request, response);
			}
			catch(Exception ex) {
				System.out.println("Error in the " + this.filterConfig.getFilterName() + " filter!");
				System.out.println("Error : "+ ex.getMessage());
			}
		}
		else {
			System.out.println("Le filtre " + this.filterConfig.getFilterName() + " a bloqu� la requ�te � la servlet --> Vous devez �tre chef de bridage pour acc�der � cette ressource");
			res.sendRedirect("connexion");	
		}
	}


}
