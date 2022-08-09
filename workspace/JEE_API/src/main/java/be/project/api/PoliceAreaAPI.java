package be.project.api;


import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.PoliceArea;

@Path("/policearea")
public class PoliceAreaAPI extends API {

	public PoliceAreaAPI() {
		// TODO Auto-generated constructor stub
	}
	
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPoliceAreas( 
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<PoliceArea> policeAreas=PoliceArea.getAllPoliceAreas();
				return Response.status(Status.OK).entity(policeAreas).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
}