package be.project.servlets;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;
import be.project.javabeans.Admin;
import be.project.javabeans.Collector;
import be.project.javabeans.Policeman;
import be.project.javabeans.Chief;


public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String apiKey;
       
   
    public AuthenticationServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	apiKey=getApiKey();
    }
    
    private String getApiKey() {
    	Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.getEnvironment().get("apiKey");
		}
		catch (NamingException e) {
			return "";
		}
    }
    
    private boolean allFieldsAreFilled(String param1, String param2) {
    	if(param1!=null && !param1.isEmpty() && !param1.equals("") && param2!=null && !param2.isEmpty() && !param2.equals("")) {
    		return true;
    	}
    	return false;
    }
    
    private boolean serialNumberIsValid(String serialNumber) {
    	if(serialNumber != null && serialNumber.length() == 8) {
    		String serialIdentifier = serialNumber.substring(0,2).toLowerCase();
    		if(serialIdentifier.equals("ad") || serialIdentifier.equals("po") || serialIdentifier.equals("ch")|| serialIdentifier.equals("co")) {
    			return true;
    		}
    	}
		return false;	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//Check si présence d'un identifiant de session
		if(session!=null && !session.isNew()) {
			//recup le user connecté + renvoi vers la bonne servlet
			User user = (User)session.getAttribute("connectedUser");
			if(user !=null && user.serialNumberIsValid()) {
				if(user instanceof Admin) {
					response.sendRedirect("admin");
					return;
				}
				if(user instanceof Collector) {
					response.sendRedirect("collector");
					return;
				}
				if(user instanceof Policeman) {
					response.sendRedirect("policeman");
					return;
				}
				if(user instanceof Chief) {
					response.sendRedirect("chief");
					return;
				}
			}
		}
		//si pas de user connecté renvoi vers le formulaire de connexion
		request.getRequestDispatcher("/WEB-INF/JSP/authentication.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		request.setAttribute("error", null);
		String errors="";
		String serialNumber=request.getParameter("serialNumber");
		String password=request.getParameter("password");
		if(!allFieldsAreFilled(serialNumber, password)){
			errors="Tous les champs doivent être remplis!";
		}
		else if(!serialNumberIsValid(serialNumber)){
			errors="Entrez un matricule correct ! Exemple : po267804";
		}else {
			try {
				serialNumber = serialNumber.toUpperCase();
				boolean loginSuccess=User.login(serialNumber, password);
				if(loginSuccess) {
					User connectedUser=User.getUser(serialNumber);
					if(connectedUser!=null) {
						//création d'une nouvelle session
						HttpSession session=request.getSession(true);
						if(!session.isNew()) {
							session.invalidate();
							session=request.getSession();
						}
						session.setAttribute("apiKey", apiKey);
						context.setAttribute("idsession", session.getId());
						if(connectedUser instanceof Admin) {
							Admin user=(Admin)connectedUser;
							session.setAttribute("connectedUser", user);
							response.sendRedirect("admin");
						}
						if(connectedUser instanceof Collector) {
							Collector user=(Collector)connectedUser;
							session.setAttribute("connectedUser", user);
							response.sendRedirect("collector");
						}
						if(connectedUser instanceof Policeman) {
							Policeman user=(Policeman)connectedUser;
							session.setAttribute("connectedUser", user);
							response.sendRedirect("policeman");
						}
						if(connectedUser instanceof Chief) {
							Chief user=(Chief)connectedUser;
							session.setAttribute("connectedUser", user);
							response.sendRedirect("chief");
						}
					}
				}else {
					errors+="Identifiant ou mot de passe incorrect";
				}
				
			}catch(Exception e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
				return;
			}
		}
		if(!errors.equals("")) {
			request.setAttribute("error", errors);
			doGet(request,response);
		}
	}
}
