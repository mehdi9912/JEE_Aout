package be.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Admin;

@Path("/admin")
public class AdminAPI extends API {

	public AdminAPI() {
		// TODO Auto-generated constructor stub
	}
	
	
	@GET
	@Path("{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdmin(@PathParam("serialNumber") String serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				Admin admin=Admin.getAdmin(serialNumber);
				return Response.status(Status.OK).entity(admin).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
}