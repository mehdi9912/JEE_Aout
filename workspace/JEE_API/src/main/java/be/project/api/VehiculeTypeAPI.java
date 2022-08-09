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

import be.project.models.VehiculeType;

@Path("/vehiculetype")
public class VehiculeTypeAPI extends API{
	
	
	public VehiculeTypeAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVehicule(@PathParam("id") String id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				VehiculeType vehiculeType=VehiculeType.getVehicule(id);
				return Response.status(Status.OK).entity(vehiculeType).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllVehicules( 
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<VehiculeType> vehicules= VehiculeType.getAllVehicules();
				return Response.status(Status.OK).entity(vehicules).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createVehicule(
			@FormParam("type") String type,
			@HeaderParam("key") String key)
	{
		VehiculeType vehicule=null;
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				vehicule = new VehiculeType(type);
				int id =vehicule.insert();
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
	public Response updateVehicule(@PathParam("id") String idString,
			@FormParam("type") String type,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				int id = Integer.valueOf(idString);
				VehiculeType vehicule = new VehiculeType(id, type);
				int updateCode=vehicule.update();
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
	public Response deleteVehicule(@PathParam("id") String id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		boolean success = false;
		if(key!=null) {
			if(key.equals(apiKey)) {
				try {
					success=VehiculeType.delete(id);
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
