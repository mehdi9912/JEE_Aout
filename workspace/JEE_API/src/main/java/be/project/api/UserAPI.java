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

import be.project.models.Chief;
import be.project.models.Collector;
import be.project.models.PoliceArea;
import be.project.models.Policeman;
import be.project.models.User;

@Path("/user")
public class UserAPI extends API{

	public UserAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers( 
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<User> users=User.getAllUsers();
				return Response.status(Status.OK).entity(users).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("serialNumber") String serialNumber, 
			@FormParam("password") String password) {
		String responseJSON;
		boolean success= User.login(serialNumber, password);
		if(success) {
			responseJSON="{\"connected\":\"true\"}";
			String apiKey=getApiKey();
			return Response.status(Status.OK)
					.header("api-key", apiKey)
					.entity(responseJSON).build();
		}else {
			return Response.status(Status.OK).entity("{\"error\":\"login failed\"}").build();
		}
		
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("password") String password,
			@FormParam("police_area_id") int policeAreaId,
			@FormParam("chief_id") String chief_serialNumber,
			@FormParam("userType") String userType,
			@HeaderParam("key") String key)
	{
		User user=null;
		PoliceArea policeArea = new PoliceArea();
		policeArea.setId(policeAreaId);
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				if(userType.equals("PO")) {
					Chief chief = new Chief();
					chief.setSerialNumber(chief_serialNumber);
					Policeman policeman = new Policeman(firstname,lastname, password, policeArea, chief);
					user= policeman;
				}
				String userSerialNumber=user.insertUser();
				if(!userSerialNumber.isEmpty()) {
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
	@Path("/update/{serialNumber}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateUser(@PathParam("serialNumber") String serialNumber,
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("password") String password,
			@FormParam("police_area_id") int policeAreaId,
			@FormParam("chief_id") String chief_serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			
			try {
				User user = User.getCorrectInstanceOfUser(serialNumber);
				user.setFirstname(firstname);
				user.setLastname(lastname);
				PoliceArea policeArea = new PoliceArea();
				policeArea.setId(policeAreaId);
				if(password !=null && !password.isEmpty()) {
					user.setPassword(password);
				}
				else {
					user.setPassword("");
				}
				if(user instanceof Policeman) {
					Chief chief = new Chief();
					chief.setSerialNumber(chief_serialNumber);
					((Policeman) user).setChief(chief);
					((Policeman) user).setPoliceArea(policeArea);
				}
				if(user instanceof Collector) {
					((Collector) user).setPoliceArea(policeArea);			
				}
				if(user instanceof Chief) {
					((Chief) user).setPoliceArea(policeArea);
				}
				int updateCode=user.update();
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
	@Path("{serialNumber}")
	public Response deleteUser(@PathParam("serialNumber") String serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		boolean success = false;
		if(key!=null) {
			if(key.equals(apiKey)) {
				try {
					success=User.delete(serialNumber);
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

