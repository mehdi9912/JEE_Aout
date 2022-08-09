package be.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Insurance;


@Path("/insurance")
public class InsuranceAPI extends API {
	
	public InsuranceAPI() {
		// TODO Auto-generated constructor stub
	}
	
	
	@GET
	@Path("{licensePlate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInsuranceOrder(@PathParam("licensePlate") String licensePlate,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		String jsonResponse;
		if(key!=null) {
			if(key.equals(apiKey)) {
				boolean inOrder=Insurance.getInsuranceOrder(licensePlate);
				if(!inOrder) {
					jsonResponse = "{\"inOrder\":\"false\"}";
				}else {
					jsonResponse = "{\"inOrder\":\"true\"}";
				}
				return Response.status(Status.OK).entity(jsonResponse).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

}
