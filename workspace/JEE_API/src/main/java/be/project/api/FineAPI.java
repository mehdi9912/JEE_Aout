package be.project.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import be.project.models.Policeman;
import be.project.enumerations.FineStatus;
import be.project.models.Fine;
import be.project.models.InfractionType;
import be.project.models.VehiculeType;

@Path("/fine")
public class FineAPI extends API {
	
	public FineAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFine(@PathParam("id") String id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				Fine fine=Fine.getFine(id);
				return Response.status(Status.OK).entity(fine).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("/supervised/{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFinesBySupervisedAgents(@PathParam("serialNumber") String chiefSerialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<Fine> fines = Fine.getAllFinesBySupervisedAgents(chiefSerialNumber);
				return Response.status(Status.OK).entity(fines).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("/validated/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllValidatedFines(
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<Fine> fines = Fine.getAllValidatedFines();
				return Response.status(Status.OK).entity(fines).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createFine(
			@FormParam("infractionId") int infractionId,
			@FormParam("vehiculeId") int vehiculeId,
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("comment") String comment,
			@FormParam("licensePlate") String licensePlate,
			@FormParam("policemanSerialNumber") String policemanSerialNumber,
			@FormParam("timestamp") String timestamp,
			@FormParam("totalPrice") double totalPrice,
			@HeaderParam("key") String key)
	{
		Fine fine=null;
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);
				Policeman user = new Policeman();
				user.setSerialNumber(policemanSerialNumber);
				InfractionType infraction = new InfractionType();
				infraction.setId(infractionId);
				VehiculeType vehicule = new VehiculeType();
				vehicule.setId(vehiculeId);
				fine = new Fine(dateTime, totalPrice, firstname, lastname, comment, vehicule, infraction, user,licensePlate);
				int id = fine.insert();
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
	@Path("/updatestatus/{fineid}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateStatus(@PathParam("fineid") int fineId,
			@FormParam("status") String status,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		int updateCode;
		if(key.equals(apiKey)) {
			try {
				Fine fine = new Fine();
				fine.setFineId(fineId);
				fine.setStatus(FineStatus.valueOf(status));
				updateCode = fine.updateStatus();
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
	public Response deleteFine(@PathParam("id") String id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		boolean success = false;
		if(key!=null) {
			if(key.equals(apiKey)) {
				try {
					success=Fine.delete(id);
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
