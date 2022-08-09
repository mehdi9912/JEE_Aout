package be.project.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.InfractionType;

@Path("/infractiontype")
public class InfractionTypeAPI extends API {
	
	public InfractionTypeAPI() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfraction(@PathParam("id") String id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				InfractionType infraction=InfractionType.getInfraction(id);
				return Response.status(Status.OK).entity(infraction).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInfractions( 
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<InfractionType> infractions=InfractionType.getAllInfractions();
				return Response.status(Status.OK).entity(infractions).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createInfraction(
			@FormParam("type") String type,
			@FormParam("amount") double amount,
			@HeaderParam("key") String key)
	{
		InfractionType infractionType=null;
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				infractionType = new InfractionType(type, amount);
				int id =infractionType.insert();
				if(id!=0) {
					return Response
							.status(Status.CREATED)
							.build();
				}else {
					return Response.status(Status.SERVICE_UNAVAILABLE).build();
				}
		
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
			}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	

	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateInfraction(@PathParam("id") String idString,
			@FormParam("type") String type,
			@FormParam("amount") double amount,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				int id = Integer.valueOf(idString);
				InfractionType infractionType = new InfractionType(id, type, amount);
				int updateCode=infractionType.update();
				if(updateCode==0){
					return Response.status(Status.NO_CONTENT).build();
				}else {
					return Response.status(Status.OK).build();
				}
			} catch (Exception e) {
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
			}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteInfraction(@PathParam("id") String id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		boolean success = false;
		if(key!=null) {
			if(key.equals(apiKey)) {
				try {
					success=InfractionType.delete(id);
					if(success){
						return Response.status(Status.NO_CONTENT).build();
					}else {
						return Response.status(Status.OK).build();
					}
				} catch (Exception e) {
					return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
				}
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
}
