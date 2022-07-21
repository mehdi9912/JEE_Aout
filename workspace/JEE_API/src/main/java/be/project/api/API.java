package be.project.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ApplicationPath("/api")
public class API extends Application{

	public API() {
		// TODO Auto-generated constructor stub
	}
	protected static String getApiKey() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.lookup("apiKey");
		} catch (NamingException e) {
			return "";
		}
	}
	
	protected static String getBaseUri() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.lookup("baseURI");
		} catch (NamingException e) {
			return "";
		}
	}

}
